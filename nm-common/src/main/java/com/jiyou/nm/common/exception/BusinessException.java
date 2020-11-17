package com.jiyou.nm.common.exception;

import lombok.Data;

import java.util.Formatter;

/**
 * 业务异常类
 */
@Data
public class BusinessException extends RuntimeException {

    private String code = "400";

    private String message;

    public BusinessException() {
    }

    public BusinessException(String code, String message){
        this.code = code;
        this.message = message;
    }
    public BusinessException(String message){
        this.message = message;
    }
    public BusinessException(String message,Object... args){
        this.message = new Formatter().format(message, args).toString();
    }
    public static BusinessException createArgs(String message,Object... args){
        BusinessException businessException = new BusinessException();
        businessException.setMessage(new Formatter().format(message, args).toString());
        return businessException;
    }
    public static BusinessException createArgsOfCodeAndMsg(String code,String message,Object... args){
        BusinessException businessException = new BusinessException();
        businessException.setCode(code);
        businessException.setMessage(new Formatter().format(message, args).toString());
        return businessException;
    }


}
