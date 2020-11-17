package com.jiyou.nm.model.vo;

import lombok.Data;

@Data
public class PageWasUserRes {

    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别  1-男， 2-女， 9-其他
     */
    private Integer sex;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 头像图片地址
     */
    private String portrait;

    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 角色名称
     */
    private String roleName;


    /**
     * 用户状态 1-启用 0-禁用
     */
    private Integer state;


    /**
     * 备注
     */
    private String remark;


}
