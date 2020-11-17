package com.jiyou.nm.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WasUserLoginReq {

    /**
     * 用户名
     */
    @NotBlank
    private String username;

    /**
     * 密码
     */
    @NotBlank
    private String password;


    /**
     * 记住登陆
     */
    private boolean rememberMe = false;
}
