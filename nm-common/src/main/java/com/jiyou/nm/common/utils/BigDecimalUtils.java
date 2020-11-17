package com.jiyou.nm.common.utils;

import java.math.BigDecimal;

/**
 * @Author: mhj
 * @Desc:
 * @Date: 2018/6/29 8:55
 */
public class BigDecimalUtils {

    public static BigDecimal valueOf(Double value) {
        return null != value ? BigDecimal.valueOf(value) : null;
    }
}
