package com.school.interceptor;

import com.school.common.properties.SqlIndexCheckProperties;
import com.school.utils.AnsiColorUtils;
import com.school.utils.OpenAiUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static com.school.common.content.AnsiColorContent.ANSI_CYAN;
import static com.school.common.content.AnsiColorContent.ANSI_YELLOW;
import static com.school.common.content.OpenAiContent.AI_SQL_ANALYST;

@Slf4j
@Component
@Intercepts({
    @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class SqlIndexInterceptor implements Interceptor {

    @Resource
    private SqlIndexCheckProperties properties;
    
    @Resource
    private OpenAiUtil openAiUtil;

    @Resource
    private AnsiColorUtils ansiLogUtils;
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (properties == null || !properties.isEnabled()) {
            return invocation.proceed();
        }
        
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        
        // 获取SQL语句和参数
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        Object parameterObject = boundSql.getParameterObject();
        List<Object> parameters = boundSql.getParameterMappings().stream()
                .map(param -> {
                    try {
                        if (parameterObject == null) {
                            return null;
                        }
                        if (parameterObject instanceof Map) {
                            return ((Map<?, ?>) parameterObject).get(param.getProperty());
                        }
                        // 处理基本类型参数
                        if (boundSql.getParameterMappings().size() == 1) {
                            return parameterObject;
                        }
                        // 使用反射获取参数值
                        return new PropertyDescriptor(param.getProperty(),
                                parameterObject.getClass()).getReadMethod()
                                .invoke(parameterObject);
                    } catch (Exception e) {
                        return "?";
                    }
                })
                .collect(Collectors.toList());

        // 替换SQL中的占位符
        String actualSql = replacePlaceholders(sql, parameters);
        
        // 获取数据库连接
        Connection connection = (Connection) invocation.getArgs()[0];
        
        // 检查SQL是否使用了索引
        checkSqlIndex(connection, actualSql);
        
        // 执行原始方法
        return invocation.proceed();
    }

    private String replacePlaceholders(String sql, List<Object> parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return sql;
        }

        // 为了避免参数中包含特殊字符，对参数进行处理
        String[] parts = sql.split("\\?");
        StringBuilder finalSql = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            finalSql.append(parts[i]);
            if (i < parameters.size()) {
                Object param = parameters.get(i);
                switch (param) {
                    case null -> finalSql.append("NULL");
                    case String ignored -> finalSql.append("'").append(escapeString(param.toString())).append("'");
                    case LocalDateTime localDateTime ->
                            finalSql.append("'").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                    .format(param)).append("'");
                    default -> finalSql.append(param);
                }
            }
        }

        return finalSql.toString();
    }

    private String escapeString(String str) {
        return str.replace("'", "''");
    }

    private void checkSqlIndex(Connection connection, String sql) {
        if (!sql.trim().toLowerCase().startsWith("select")) {
            return;
        }
        
        try {
            String explainSql = "EXPLAIN " + sql;
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(explainSql)) {
                
                if (rs.next()) {
                    String type = rs.getString("type");
                    String key = rs.getString("key");
                    boolean hasKey = key != null && !key.isEmpty();

                    // 使用蓝色显示分隔线
                    log.info(ansiLogUtils.withColor(ANSI_YELLOW ,"*************************SQL详情*************************\n"));

                    // 根据是否使用索引使用不同颜色
                    if ("ALL".equals(type) || !hasKey) {
                        log.info("SQL未使用索引 - type: {}, key: {}, SQL: {}", type, key, sql.replaceAll("\\s+", " "));
                    } else {
                        log.info("SQL使用了索引 - type: {}, key: {}, SQL: {}", type, key, sql.replaceAll("\\s+", " "));
                    }
                    
                    // 可以记录更多执行计划信息
                    if (log.isDebugEnabled() && properties.isIncludeFullExplain()) {
                        StringBuilder explain = new StringBuilder("执行计划详情: ");
                        int columnCount = rs.getMetaData().getColumnCount();
                        for (int i = 1; i <= columnCount; i++) {
                            String columnName = rs.getMetaData().getColumnName(i);
                            explain.append(columnName).append("=").append(rs.getString(i)).append(", ");
                        }

                        String sqlOptimizationSuggestion = openAiUtil.generation(AI_SQL_ANALYST, explain.toString());

                        log.debug(explain.toString());
                        log.info("SQL优化建议: {}", sqlOptimizationSuggestion);
                        log.info(ansiLogUtils.withColor(ANSI_CYAN, "SQL优化建议:\n {}"), sqlOptimizationSuggestion);
                    }
                }
            }
        } catch (SQLException e) {
            log.error("分析SQL执行计划失败: {}", sql, e);
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 可以从外部配置获取属性
    }
} 