package com.jiyou.nm.common.config;

import lombok.Data;

/**
 * 描述：
 * 作者： 张恒同
 * 时间： 2019-08-19   09:47
 */
@Data
public class TicketInfo {

    private Long  id;

    private String code;

    private Integer type;

    private Integer templateId;

    private Integer companyId;

}
