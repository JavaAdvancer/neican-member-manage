package com.jiyou.nm.wechatapi.config.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiyou.nm.common.exception.BusinessException;
import com.jiyou.nm.common.exception.ErrorResult;
import com.jiyou.nm.common.exception.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientResponseException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * ExceptionHandle
 * 统一异常处理类
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle {

    /**
     * 描述 : objectMapper
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 描述 : 构造错误响应对象
     *
     * @param throwable 异常
     * @return 错误响应对象
     */
    public static ErrorResult buildError(Throwable throwable) {
        ErrorResult error = new ErrorResult();
        error.setType(throwable.getClass().getName());
        error.setMessage(ExceptionUtils.getMessage(throwable));
        error.setDate(new Date());
        return error;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RestResponse exceptionHandler(HttpServletRequest request, Exception ex) {
        ex.printStackTrace();
        //异常处理
        ErrorResult errorResult = buildError(ex);
        //400
        RestResponse<String> restResponse = new RestResponse<>(HttpStatus.BAD_REQUEST.value(), errorResult);
        if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            restResponse.setCode(businessException.getCode());
            restResponse.setMessage(businessException.getMessage());
            //rest请求异常
        } else if (ex instanceof RestClientResponseException) {
            try {
                RestClientResponseException restClientResponseException = (RestClientResponseException) ex;
                String data = restClientResponseException.getResponseBodyAsString();
                if (StringUtils.isNotBlank(data)) {
                    RestResponse<String> child = objectMapper.readValue(data, objectMapper.getTypeFactory().constructParametricType(RestResponse.class, String.class));
                    errorResult.setChild(child);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //参数校验异常
        } else if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
            StringBuffer msg = new StringBuffer();
            BindingResult result = methodArgumentNotValidException.getBindingResult();
            if (result.hasErrors()) {
                List<ObjectError> errors = result.getAllErrors();
                errors.forEach(p -> {
                    FieldError fieldError = (FieldError) p;
                    log.error("Data check failure : object{" + fieldError.getObjectName() + "},field{" + fieldError.getField() +
                            "},errorMessage{" + fieldError.getDefaultMessage() + "}");
                    //msg.append(fieldError.getDefaultMessage()).append(";");
                });
                FieldError fieldError = (FieldError) errors.get(0);
                msg.append(fieldError.getField()).append(fieldError.getDefaultMessage());
            }
            restResponse.setCode("400");
            restResponse.setMessage(msg.toString());
        } else if (ex instanceof BindException) {
            BindException bindException = (BindException) ex;
            StringBuffer msg = new StringBuffer();
            BindingResult result = bindException.getBindingResult();
            if (result.hasErrors()) {
                List<ObjectError> errors = result.getAllErrors();
                errors.forEach(p -> {
                    FieldError fieldError = (FieldError) p;
                    log.error("Data check failure : object{" + fieldError.getObjectName() + "},field{" + fieldError.getField() +
                            "},errorMessage{" + fieldError.getDefaultMessage() + "}");
                    //msg.append(fieldError.getField()).append(fieldError.getDefaultMessage()).append(";");
                });
                FieldError fieldError = (FieldError) errors.get(0);
                msg.append(fieldError.getField()).append(fieldError.getDefaultMessage());

            }
            restResponse.setCode("400");
            restResponse.setMessage(msg.toString());
        }else if(ex instanceof UnauthorizedException){
            String msg = ex.getMessage();
            log.error("用户无权限：{}", msg);
            restResponse.setCode(Integer.toString(HttpStatus.FORBIDDEN.value()));
            restResponse.setMessage("用户无权限");
        } else {
            restResponse.setMessage(ex.getMessage());
        }
        log.error(restResponse.getId(), ex);
        return restResponse;
    }
}
