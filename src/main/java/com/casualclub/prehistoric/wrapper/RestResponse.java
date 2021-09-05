package com.casualclub.prehistoric.wrapper;

/**
 * @author px
 */
public class RestResponse {

    private RestResponse() {
    }

    /**
     * wrap
     * @param code code
     * @param message message
     * @param data data
     * @param <T> t
     * @return t
     */
    public static <T> Wrapper<T> wrap(int code, String message, T data) {
        return new Wrapper<>(code, message, data);
    }

    /**
     * wrap
     * @param code code
     * @param message message
     * @param <T> t
     * @return t
     */
    public static <T> Wrapper<T> wrap(int code, String message) {
        return new Wrapper<>(code, message, null);
    }

    /**
     * wrap
     * @param code code
     * @param <T> t
     * @return t
     */
    public static <T> Wrapper<T> wrap(int code) {
        return new Wrapper<>(code, null);
    }

    /**
     * wrap
     * @param exception exception
     * @param <T> t
     * @return t
     */
    public static <T> Wrapper<T> wrap(Exception exception) {
        return new Wrapper<>(Wrapper.ERROR_CODE, exception.getMessage());
    }

    /**
     * success
     * @param data data
     * @param <T> t
     * @return t
     */
    @SafeVarargs
    public static <T> Wrapper<T> success(T... data) {
        if (data.length > 0) {
            return new Wrapper<>(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, data[0]);
        }
        return new Wrapper<>();
    }

    /**
     * error
     * @param message message
     * @param <T> t
     * @return t
     */
    public static <T> Wrapper<T> error(String... message) {
        return new Wrapper<>(Wrapper.ERROR_CODE, message.length > 0 ? message[0] : Wrapper.ERROR_MESSAGE);
    }

    /**
     * unauthorized
     * @param message message
     * @param <T> t
     * @return t
     */
    public static <T> Wrapper<T> unauthorized(String... message) {
        return new Wrapper<>(401, message.length > 0 ? message[0] : "您当前没有登录!");
    }

    /**
     * forbidden
     * @param message message
     * @param <T> t
     * @return t
     */
    public static <T> Wrapper<T> forbidden(String... message) {
        return new Wrapper<>(403, message.length > 0 ? message[0] : "您当前没有权限!");
    }

}