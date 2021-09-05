package com.casualclub.prehistoric.wrapper;

/**
 * @author px
 */
public class RestResponse {

    private RestResponse() {
    }

    /**
     * wrapper response
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
     * wrapper response
     * @param code code
     * @param message message
     * @param <T> t
     * @return t
     */
    public static <T> Wrapper<T> wrap(int code, String message) {
        return new Wrapper<>(code, message);
    }


    /**
     * wrap
     * @param exception exception
     * @param <T> t
     * @return t
     */
    public static <T> Wrapper<T> wrap(Exception exception) {
        return wrap(Wrapper.ERROR_CODE, exception.getMessage());
    }

    /**
     * success response
     * @return t
     */
    public static <T> Wrapper<T> success() {
        return new Wrapper<>();
    }

    /**
     * success response
     * @param data data
     * @param <T> t
     * @return wrapper<t>
     */
    public static <T> Wrapper<T> success(T data) {
        return wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, data);
    }

    /**
     * error response
     * @return error wrapper
     */
    public static <T> Wrapper<T> error() {
        return wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
    }

    /**
     * error response
     * @param message error message
     * @return error wrapper
     */
    public static <T> Wrapper<T> error(String message) {
        return wrap(Wrapper.ERROR_CODE, message);
    }

    /**
     * unauthorized response
     * @return unauthorized wrapper
     */
    public static <T> Wrapper<T> unauthorized() {
        return wrap(Wrapper.UNAUTHOR_ERROR_CODE, Wrapper.UNAUTHOR_ERROR_MESSAGE);
    }

    /**
     * unauthorized response
     * @param message unauthorized message
     * @return unauthorized wrapper
     */
    public static <T> Wrapper<T> unauthorized(String message) {
        return wrap(Wrapper.UNAUTHOR_ERROR_CODE, message);
    }

    /**
     * forbidden response
     * @return forbidden wrapper
     */
    public static <T> Wrapper<T> forbidden() {
        return wrap(Wrapper.FORBIDDEN_ERROR_CODE, Wrapper.FORBIDDEN_ERROR_MESSAGE);
    }

    /**
     * forbidden response
     * @param message forbidden message
     * @return forbidden wrapper
     */
    public static <T> Wrapper<T> forbidden(String message) {
        return wrap(Wrapper.FORBIDDEN_ERROR_CODE, message);
    }

}