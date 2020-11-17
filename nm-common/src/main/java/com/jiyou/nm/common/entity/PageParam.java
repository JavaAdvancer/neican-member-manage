package com.jiyou.nm.common.entity;

import lombok.Data;

/**
 * 分页参数
 */
@Data
public class PageParam {

    private Integer page=1;
    private Integer limit=10;

    private String sortCode;//排序字段
    private String sortRole = "desc";//desc asc


}
