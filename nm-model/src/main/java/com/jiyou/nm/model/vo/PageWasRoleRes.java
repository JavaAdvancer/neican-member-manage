package com.jiyou.nm.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PageWasRoleRes {

    private Integer id;


    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 描述
     */
    private String roleDes;

    /**
     * 状态 1-启用 0-禁用
     */
    private Integer state;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


}
