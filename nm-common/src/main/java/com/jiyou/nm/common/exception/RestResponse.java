package com.jiyou.nm.common.exception;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.UUID;

/**
 * 描述 : rest响应对象
 **/
@Data
@ToString
public class RestResponse<T> implements Serializable {

    /**
     * 描述 : id
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 响应ID
     */
    private String id = UUID.randomUUID().toString();

    /**
     * 描述 : 状态码(业务定义)
     */
    private String code = Integer.toString(HttpStatus.OK.value());

    /**
     * 描述 : 状态码描述(业务定义)
     */
    private String message = HttpStatus.OK.getReasonPhrase();

    /**
     * 描述 : 结果集(泛型)
     */
    private T result = null; //NOSONAR

    /**
     * 描述 : 错误
     */
    private ErrorResult error = null;

    /**
     * 描述 : 构造函数
     */
    public RestResponse() {
        super();
    }

    /**
     * 描述 : 构造函数
     *
     * @param result 结果集(泛型)
     */
    public RestResponse(T result) {
        super();
        this.result = result;
    }

    /**
     * 描述 : 构造函数
     *
     * @param code  http状态
     * @param error 错误
     */
    public RestResponse(Integer code, ErrorResult error) {
        super();
        this.code = Integer.toString(code);
        this.error = error;
    }

    /**
     * 描述 : 构造函数
     *
     * @param code    状态码(业务定义)
     * @param message 状态码描述(业务定义)
     * @param error   错误
     */
    public RestResponse(String code, String message, ErrorResult error) {
        super();
        this.code = code;
        this.message = message;
        this.error = error;
    }

    /**
     * 描述 : 构造函数
     *
     * @param code    状态码(业务定义)
     * @param message 状态码描述(业务定义)
     */
    public RestResponse(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    /**
     * 描述 : 构造函数
     *
     * @param code    状态码(业务定义)
     * @param message 状态码描述(业务定义)
     * @param result  结果集(泛型)
     */
    public RestResponse(String code, String message, T result) {
        super();
        this.code = code;
        this.message = message;
        this.result = result;
    }
    //静态构造器开始

    /**
     * 构造成功返回结果-只包含提示信息
     *
     * @param message
     * @return
     */
    public static RestResponse success(String message) {
        RestResponse<String> restResponse = new RestResponse<>();
        restResponse.setMessage(message);
        return restResponse;
    }

    /**
     * 构造成功返回结果-只包含默认提示信息
     *
     * @return
     */
    public static RestResponse success() {
        RestResponse<String> restResponse = new RestResponse<>();
        restResponse.setMessage("OK");
        return restResponse;
    }

    /**
     * 构造成功返回结果-单个提示信息或者结果
     *
     * @param result
     * @param singleStrResult
     * @return
     */
    public static RestResponse success(String result, Boolean singleStrResult) {
        RestResponse<String> restResponse = new RestResponse<>();
        restResponse.setMessage(result);
        if (singleStrResult) {
            restResponse.setResult(result);
        }
        return restResponse;
    }

    /**
     * 构造成功返回结果-结果
     *
     * @param result
     * @return
     */
    public static <T> RestResponse success(T result) {
        RestResponse<T> restResponse = new RestResponse<>();
        restResponse.setResult(result);
        return restResponse;
    }

    /**
     * 构造成功返回结果-提示信息和结果
     *
     * @param message
     * @param result
     * @return
     */
    public static <T> RestResponse success(String message, T result) {
        RestResponse<T> restResponse = new RestResponse<>();
        restResponse.setMessage(message);
        restResponse.setResult(result);
        return restResponse;
    }

    /**
     * 构造失败返回结果-提示信息
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> RestResponse error(String message) {
        RestResponse<T> restResponse = new RestResponse<>();
        restResponse.setMessage(message);
        restResponse.setCode(Integer.toString(HttpStatus.BAD_REQUEST.value()));
        return restResponse;
    }

    /**
     * 构造失败返回结果-提示信息和错误码
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> RestResponse error(String code, String message) {
        RestResponse<T> restResponse = new RestResponse<>();
        restResponse.setMessage(message);
        restResponse.setCode(code);
        return restResponse;
    }
    //静态构造器结束

    public String toJSONStr() {
        return JSONObject.toJSONString(this);
    }
}
