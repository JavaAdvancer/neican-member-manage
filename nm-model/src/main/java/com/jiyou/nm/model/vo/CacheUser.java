package com.jiyou.nm.model.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


/**
 * 缓存用户信息
 */
@Data
@Builder
public class CacheUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer sex;

    private String realName;

    private String username;

    private String token;
}