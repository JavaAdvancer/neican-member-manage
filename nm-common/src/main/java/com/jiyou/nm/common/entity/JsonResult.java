package com.jiyou.nm.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 作者： 张恒同
 * 时间： 2018/11/6   10:19
 * 描述：
 */
public class JsonResult<T> {

    public static final int SUCCESS = ResultCode.SUCCESS;            //成功处理/返回
    public static final int ERROR = ResultCode.ERROR;            //出现异常-业务异常
    public static final int ERROR_TOKEN = ResultCode.ERROR_TOKEN;      //Token失效
    public static final int ERROR_EXCEPTION = ResultCode.ERROR_EXCEPTION;  //未知异常
    public static final int ERROR_AUTH_EXCEPTION = ResultCode.ERROR_EXCEPTION;  //未知异常

    int code = SUCCESS;
    String des = "";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data;

    public static JsonResult Ok(){
        return new JsonResult();
    }

    public static JsonResult Ok(String des){
        return new JsonResult(SUCCESS, des, null);
    }

    public static <T> JsonResult Ok(String des, T data){
        return new JsonResult(SUCCESS, des, data);
    }

    public static <T> JsonResult Ok(T data){
        return new JsonResult(SUCCESS, "success", data);
    }

    public static JsonResult error(String des){
        return new JsonResult(des);
    }

    public static JsonResult errorToken(){
        return new JsonResult(ERROR_TOKEN, "token失效");
    }

    public static JsonResult errorException(){
        return new JsonResult(ERROR_EXCEPTION, "发生异常错误");
    }

    public static JsonResult errorException(Exception e){
        return new JsonResult(ERROR_EXCEPTION, "发生异常错误 : " + e.getStackTrace());
    }

    public JsonResult() {
        this.code = SUCCESS;
        this.des = "success";
    }

    public JsonResult(String des) {
        this.des = des;
        this.code = ERROR;
    }

    public JsonResult(int rc, String des){
        this.code = rc;
        this.des = des;
        this.data = null;
    }

    public JsonResult(int rc, String des, T data){
        this.code = rc;
        this.des = des;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int rc) {
        this.code = rc;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public interface ResultCode{
        /**
         * 成功处理/返回
         */
        public static final int SUCCESS=0;
        /**
         * 出现异常-业务异常
         */
        public static final int ERROR=1;
        /**
         *Token失效
         */
        public static final int ERROR_TOKEN=2;
        /**
         * 未知异常
         */
        public static final int ERROR_EXCEPTION=-1;

        /**
         * 未知异常
         */
        public static final int ERROR_AUTH_EXCEPTION=1001;
    }

}

