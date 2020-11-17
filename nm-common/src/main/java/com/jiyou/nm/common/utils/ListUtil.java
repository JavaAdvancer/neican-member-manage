package com.jiyou.nm.common.utils;

import java.util.List;

public class ListUtil {
    public static boolean isEmpty(List list) {
        return null == list || list.size() == 0;
    }

    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }
}
