package com.jiyou.nm.common.utils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lbh
 * @date 2019/9/25 15:01
 * @desc 公共方法
 *
 *  注意：
 *  1.hv()表示是否有值
 *  2.eq()表示相等
 *  3.pe()表示打印异常（应该设置一个开关打开异常打印方便程序部署）
 *  4.t*()表示转换成相应的类型
 *
 */
public final class AirUtils {

    private AirUtils() {
        throw new AssertionError();
    }

    /**
     * 是否有值 : hasValue
     *
     * @param rs
     * @return
     */
    public static boolean hv(String rs) {
        return rs != null && rs.length() > 0;
    }

    /**
     * 是否有值 : hasValue
     *
     * @param rs
     * @return
     */
    public static boolean hv(Integer rs) {
        return rs != null && rs != 0;
    }

    /**
     * 是否有值 : hasValue 0返回true
     *
     * @param rs
     * @return
     */
    public static boolean hv2(Integer rs) {
        return rs != null;
    }
    /**
     * 是否有值 : hasValue
     *
     * @param rs
     * @return
     */
    public static boolean hv(Double rs) {
        return rs != null && rs != 0d;
    }

    /**
     * 是否有值 : hasValue
     *
     * @param rs
     * @return
     */
    public static boolean hv(Date rs) {
        return rs != null;
    }

    /**
     * 是否有值 : hasValue
     *
     * @param rs
     * @return
     */
    public static boolean hv(Long rs) {
        return rs != null && rs != 0L;
    }

    /**
     * 是否有值 : hasValue
     *
     * @param str
     * @return
     */
    public static boolean hv(String[] str) {
        return str != null && str.length > 0;
    }

    /**
     * 是否有值 : hasValue
     *
     * <h1>注意：如果list的第一个元素是null那么返回false</h1>
     *
     * @param list
     * @return
     */
    public static boolean hv(List<?> list) {
        if (list != null && list.size() > 0) {
            if (hv(list.get(0))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否有值 : hasValue
     *
     * @param obj
     * @return
     */
    public static boolean hv(Object obj) {
        return obj != null;
    }

    /***
     * Collcetion了类型是否有值
     * @param coll
     * @return
     */
    public static boolean hv(Collection coll) {
        return coll != null && !coll.isEmpty();
    }

    /**
     * map是否有值 : hasValue
     *
     * @param obj
     * @return
     */
    public static boolean hv(Map obj) {
        return null != obj && !obj.isEmpty();
    }

    /**
     * 是否有值 : hasValue
     *
     * @param obj
     * @return
     */
    public static boolean hv(Object[] obj) {
        return obj != null && obj.length > 0;
    }

    /**
     * 是否有值 : hasValue
     *
     * <h1>注意：该方法主要用于判断多个参数同时不为null时才用</h1> <h2>
     * 用法:Scm.hv(obj1,obj2,obj3,...,args)</h2>
     *
     * @param obj  参数1
     * @param args 参数列表
     * @return
     */
    public static boolean hv(Object obj, Object... args) {
        if (!hv(obj)) {
            return false;
        }
        for (Object arg : args) {
            if (!hv(arg)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 其中一个参数是否有值 : oneHasValue
     *
     * @param args 参数列表
     * @return
     */
    public static boolean oneHv(Object... args) {
        for (Object arg : args) {
            if (arg instanceof String) { // 如果类型是字符串特殊处理
                if (hv(AirUtils.ts(arg))) {
                    return true;
                }
            } else {
                if (hv(arg)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否相等 : equals
     *
     * <h1>注意：src,dest其中一个值不为null</h1> <h2>用法:Scm.eq(null,1); Scm.eq(1,2);
     * Scm.eq(2,null);等</h2>
     *
     * @param src  源字符串
     * @param dest 目标字符串
     * @return
     */
    public static boolean eq(Object src, Object dest) {
        if (src == null && dest == null) {
            return true;
        }
        return hv(src) ? src.equals(dest) : dest.equals(src);
    }

    /**
     * 转换成String : toString
     *
     * @param obj
     * @return
     */
    public static String ts(Object obj) {
        return hv(obj) ? String.valueOf(obj) : null;
    }

    /**
     * 转换成String : toString
     *
     * @param rs
     * @return
     */
    public static String ts(String rs) {
        return rs == null ? "" : rs;
    }

    /**
     * SQL拼接中单引号处理 : singleQuoteMark
     *
     * @param rs
     * @return
     */
    public static String sqm(String rs) {
        return replace(rs, "'", "''");
    }

    /**
     * 字符串替换
     * <p>
     * 注意：不需要判断rs == null
     *
     * @param rs          原字符串
     * @param target      需要替换的内容
     * @param replacement 替换成的内容
     * @return
     */
    public static String replace(String rs, CharSequence target, CharSequence replacement) {
        return rs == null ? "" : rs.replace(target, replacement);
    }

    /**
     * 转换成Integer : toInteger
     *
     * @param rs
     * @return
     */
    public static Integer ti(String rs) {
        return hv(rs) ? Integer.parseInt(rs) : null;
    }

    /**
     * 转换成有效的Integer类型
     *
     * @param rs
     * @return
     */
    public static Integer ti(Integer rs) {
        return hv(rs) ? rs : 0;
    }

    /**
     * 转换成Integer : toInteger
     *
     * @param rs
     * @return
     */
    public static Integer ti(Long rs) {
        return hv(rs) ? rs.intValue() : null;
    }

    /**
     * 转换成Double : toDouble
     *
     * @param rs
     * @return
     */
    public static Double td(String rs) {
        return hv(rs) ? Double.parseDouble(rs) : null;
    }

    /**
     * 转换成有效的Double类型 : toAmount
     *
     * @param rs
     * @return
     */
    public static Double ta(Double rs) {
        return hv(rs) ? rs : 0.00;
    }

    /**
     * 转换成有效的BigDecimal类型 : toAmount
     *
     * @param rs
     * @return
     */
    public static BigDecimal ta(BigDecimal rs) {
        return hv(rs) ? rs : BigDecimal.ZERO;
    }

    /**
     * 转换成有效的Double类型 : toAmount
     *
     * @param rs
     * @return
     * @add xiaofeng 2010-12-9
     */
    public static Double ta(Long rs) {
        return hv(rs) ? Double.parseDouble(AirUtils.ts(rs)) : 0.00;
    }

    /**
     * 转换成Long : toLong
     *
     * @param str
     * @return
     */
    public static Long tl(String str) {
        return hv(str) ? Long.parseLong(str) : null;
    }

    /**
     * 转换成Long : toLong
     *
     * @param i
     * @return
     */
    public static Long tl(Integer i, Long dec) {
        return hv(i) ? Long.valueOf(i) : dec;
    }

    /**
     * 转换成Long : toLong
     *
     * @param i
     * @return
     */
    public static Long tl(Integer i) {
        return hv(i) ? Long.valueOf(i) : null;
    }

    /**
     * 转换成有效的Long类型 : toAmount
     *
     * @param rs
     * @return
     */
    public static Long tl(Long rs) {
        return hv(rs) ? rs : 0;
    }

    /**
     * Exception输出 ：printStackTrace
     *
     * @param e
     * @return
     */
    public static void pe(Exception e) {
        e.printStackTrace();
    }

}
