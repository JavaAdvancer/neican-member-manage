package com.jiyou.nm.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UpdateWasRoleReq implements Serializable {


    private static final long serialVersionUID = 4444198048299071071L;


    private Integer id;

    /**
     * 角色名称
     */
    @NotBlank
    private String roleName;

    /**
     * 描述
     */
    private String roleDes;

}
