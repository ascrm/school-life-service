package com.school.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 通用响应结果封装类
 * 
 * @Author: ascrm
 * @Date: 2025/3/16
 */
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private Integer code;
    private String message;
    private T data;
    private Boolean success;
    
    /**
     * 创建成功结果
     * @param data 数据
     * @param <T> 数据类型
     * @return 成功结果
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>()
                .setCode(200)
                .setMessage("操作成功")
                .setData(data)
                .setSuccess(true);
    }
    
    /**
     * 创建成功结果
     * @param message 成功消息
     * @param data 数据
     * @param <T> 数据类型
     * @return 成功结果
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<T>()
                .setCode(200)
                .setMessage(message)
                .setData(data)
                .setSuccess(true);
    }
    
    /**
     * 创建失败结果
     * @param message 失败消息
     * @param <T> 数据类型
     * @return 失败结果
     */
    public static <T> Result<T> fail(String message) {
        return new Result<T>()
                .setCode(500)
                .setMessage(message)
                .setData(null)
                .setSuccess(false);
    }
    
    /**
     * 创建失败结果
     * @param code 错误码
     * @param message 失败消息
     * @param <T> 数据类型
     * @return 失败结果
     */
    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<T>()
                .setCode(code)
                .setMessage(message)
                .setData(null)
                .setSuccess(false);
    }
    
    /**
     * 参数验证失败
     * @param message 失败消息
     * @param <T> 数据类型
     * @return 失败结果
     */
    public static <T> Result<T> validateFailed(String message) {
        return new Result<T>()
                .setCode(400)
                .setMessage(message)
                .setData(null)
                .setSuccess(false);
    }
    
    /**
     * 未授权结果
     * @param <T> 数据类型
     * @return 未授权结果
     */
    public static <T> Result<T> unauthorized() {
        return new Result<T>()
                .setCode(401)
                .setMessage("暂未登录或token已过期")
                .setData(null)
                .setSuccess(false);
    }
    
    /**
     * 没有操作权限
     * @param <T> 数据类型
     * @return 没有权限结果
     */
    public static <T> Result<T> forbidden() {
        return new Result<T>()
                .setCode(403)
                .setMessage("没有相关操作权限")
                .setData(null)
                .setSuccess(false);
    }
}
