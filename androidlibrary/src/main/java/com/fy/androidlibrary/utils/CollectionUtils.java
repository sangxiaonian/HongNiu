package com.fy.androidlibrary.utils;

import java.util.Collection;
import java.util.Map;

/**
 * 作者：  on 2019/11/1.
 */
public class CollectionUtils {
    public static boolean isEmpty(Collection collection){
        return collection==null||collection.isEmpty();
    }
    public static boolean isEmpty(Map map){
        return map==null||map.isEmpty();
    }
}
