package com.hongniu.baselibrary.utils;

import java.util.Collection;

/**
 * 作者： ${PING} on 2019/5/31.
 */
public class BaseUtils {
    /**
     * 判断传入的数据是否是null或者空集合
     *
     * @param c
     * @return true null 或者为空
     */
    public static boolean isCollectionsEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }
}
