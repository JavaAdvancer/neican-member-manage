package com.jiyou.nm.common.entity;

import java.util.List;

/**
 * 树形数据实体接口
 *
 * @author CHQ
 * @create 2018-05-08 13:16
 **/
public interface TreeEntity<E> {
    /**
     * 主键
     *
     * @return
     */
    Integer getId();

    /**
     * 父ID
     *
     * @return
     */
    Integer getParentId();

    /**
     * 子集
     *
     * @param childList
     */
    void setChildList(List<E> childList);
}
