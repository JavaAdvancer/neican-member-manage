package com.jiyou.nm.common.entity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

@Data
public class PageReq {

    /**
     * 页码，从1开始
     */
    private int pageNum=1;
    /**
     * 页面大小
     */
    private int pageSize=10;
    /**
     * 排序字段
     */
    private String sortField;
    /**
     * 排序规则
     */
    private String sortDirect;

    public String getOrderSql(String defaultField, String defaultDirect) {
        StringBuilder orderSql = StrUtil.builder("");
        if (StrUtil.isNotBlank(this.sortField)) {
            orderSql.append(this.sortField);
        }else{
            if(StrUtil.isBlank(defaultField)){
                return "";
            }else{
                orderSql.append(defaultField);
                if (StrUtil.isNotBlank(defaultDirect)) {
                    orderSql.append(" ");
                    orderSql.append(defaultDirect);
                }
            }
        }
        if (StrUtil.isNotBlank(this.sortDirect)) {
            orderSql.append(" ");
            orderSql.append(this.sortDirect);
        }
        return orderSql.toString();
    }

    /**
     * 排序列表
     */
    // private List<Order> orders;

    /**
     * 获取order by sql 不包含order by
     *
     * @return
     */
//    public String getOrderSql() {
//        StringBuilder orderSql = StrUtil.builder("");
//        if (CollUtil.isNotEmpty(this.orders)) {
//            for (int i = 0; i < this.orders.size(); i++) {
//                Order order = this.orders.get(i);
//                orderSql.append(order.getField()).append(" ").append(null == order.getDirection() ? "" : order.getDirection());
//                if (i < this.orders.size() - 1) {
//                    orderSql.append(",");
//                }
//            }
//        }
//        return orderSql.toString();
//    }


    @Data
    public static class Order {
        private String field;
        private String direction;
    }


}
