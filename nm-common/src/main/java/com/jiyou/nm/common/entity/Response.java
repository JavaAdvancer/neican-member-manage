package com.jiyou.nm.common.entity;

import java.io.Serializable;

/**
 * @author : cpc
 * @date : 2017-09-14
 **/
public class Response<T> implements Serializable {
    private static final long serialVersionUID = 6138979130005367537L;

    private boolean success = true;
    private T result;

    private String error;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setResult(T result) {
        this.success = true;
        this.result = result;
    }


    public T getResult() {
        return result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.success = false;
        this.error = error;
    }

    public static <T> Response<T> ok(T data) {
        Response<T> resp = new Response<T>();
        resp.setResult(data);
        return resp;
    }

    public static <T> Response<T> ok() {
        return Response.ok(null);
    }

    public static <T> Response<T> fail(String error) {
        Response<T> resp = new Response<T>();
        resp.setError(error);
        return resp;
    }
}

