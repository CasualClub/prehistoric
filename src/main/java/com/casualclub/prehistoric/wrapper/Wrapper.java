package com.casualclub.prehistoric.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author px
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Wrapper<T> implements Serializable {

    /**
     * 成功码.
     */
    static final int SUCCESS_CODE = 200;

    /**
     * 成功信息.
     */
    static final String SUCCESS_MESSAGE = "操作成功";

    /**
     * 错误码.
     */
    static final int ERROR_CODE = 500;

    /**
     * 错误信息.
     */
    static final String ERROR_MESSAGE = "系统异常";

    /**
     * 状态码
     */
    private int code;

    /**
     * 信息
     */
    private String message;

    /**
     * 数据结果
     */
    private T result;

    public Wrapper() {
        this(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public Wrapper(int code, String message) {
        this(code, message, null);
    }

    public Wrapper(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    @JsonIgnore
    public boolean success() {
        return SUCCESS_CODE == this.code;
    }

    @JsonIgnore
    public boolean error() {
        return !success();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getResult() {
        return result;
    }
}