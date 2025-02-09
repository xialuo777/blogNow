package com.blog.exception;

import com.blog.enums.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

/**
 * @Author z
 * @ClassName BusinessException
 * @Description 业务异常类
 * 例如：登录功能,账号不存在或密码错误时,可抛出一个业务异常,自定义异常信息
 * @Time 2024-08-23 16:13
 */
@Data
public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 异常对应的返回码
     */
    private static Integer code;

    /**
     * 异常对应的描述信息
     */
    private static String message;

    private static Throwable throwable;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.message = String.format("%s %s", message, cause.getMessage());
    }

    public BusinessException(int code, String message, Throwable throwable) {
        super(message);
        this.code = code;
        this.message = message;
        this.throwable = throwable;
    }

    public BusinessException(ErrorCode resError) {
        this(resError.getCode(), resError.getMessage(), null);
    }

    public BusinessException(ErrorCode resError, Throwable throwable) {
        this(resError.getCode(), resError.getMessage(), throwable);
    }

    public BusinessException(ErrorCode resError, Object... args) {
        super(resError.getMessage());
        String message = resError.getMessage();
        try {
            message =
                    String.format("%s %s", resError.getMessage(), OBJECT_MAPPER.writeValueAsString(args));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.message = message;
        this.code = resError.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}