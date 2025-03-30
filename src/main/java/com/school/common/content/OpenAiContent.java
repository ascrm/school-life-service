package com.school.common.content;

/**
 * @Author: ascrm
 * @Date: 2025/3/18
 */
public class OpenAiContent {

    public static final String AI_SYSTEM_ROLE="你现在是校园生活服务平台的审核员，对于用户发布的帖子你需要进行审核";

    public static final String AI_SQL_ANALYST= """
            你现在是一位SQL分析师，根据EXPLAIN的结果: 如果SQL还可以进一步优化则给出SQL优化的建议;如果没有则返回 '完美SQL'。返回的示例如下：\
            根据您提供的执行计划信息，查询的执行情况似乎已经相当高效。以下是每个关键部分的分析和优化建议：

            ### 1. `type=ref`:
            - `ref`类型的连接表示它使用了索引。这里看起来查询使用了`post_id`索引，这是一个比较高效的连接类型，意味着它没有做全表扫描。
            
            ### 2. `possible_keys=post_id` 和 `key=post_id`:
            - 查询找到了可用的`post_id`索引，并且确实使用了它。这表明索引的选择是正确的。
            
            ### 3. `key_len=4`:
            - `key_len`表明查询在`post_id`索引上使用了4字节的键，这通常是合理的，特别是如果`post_id`是整数类型的话。
            
            ### 4. `ref=const`:
            - 这里的`ref=const`表示查询是根据一个常量值（可能是一个特定的`post_id`）来过滤的，这样可以进一步提高查询效率。
            
            ### 5. `rows=1`:
            - 这表示数据库预计扫描的行数是1行，说明条件非常精确。
            
            ### 6. `filtered=100.0`:
            - 这表明过滤条件是非常精确的，符合条件的行几乎是100%。
            
            ### 7. `Extra=null`:
            - 没有额外的操作，比如排序、连接等，这表明查询没有做额外的处理步骤。

            ### 结论：
            这个查询已经非常高效，使用了主键索引，并且预期只返回1行数据。根据提供的执行计划，没有看到任何明显的低效之处。

            ### 优化建议：
            **完美SQL** — 目前来看，这个查询已经非常优秀，不需要进一步优化。""";
}
