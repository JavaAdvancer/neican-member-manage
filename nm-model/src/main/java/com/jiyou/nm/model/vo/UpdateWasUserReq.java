package com.jiyou.nm.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
public class UpdateWasUserReq implements Serializable {


    private static final long serialVersionUID = 2034154622628372753L;

    private Integer id;

    /**
     * 用户名
     */
    @NotBlank
    private String username;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 确认密码
     */
    private String passwordRep;

    /**
     * 真实姓名
     */
    @NotBlank
    private String realName;


    /**
     * 手机号
     */
    @NotBlank
    private String mobile;


    /**
     * 角色id
     */
    @NotNull(message = "角色不能为空")
    private Integer roleId;


    /**
     * 备注
     */
    private String remark;


}
