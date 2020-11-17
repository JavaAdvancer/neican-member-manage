package com.jiyou.nm.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class UpdateRoleMenuReq {

    private Integer roleId;

    private List<Integer> menuIdList;
}
