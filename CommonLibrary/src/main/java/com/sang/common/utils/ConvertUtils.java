package com.sang.common.utils;

import java.util.Random;

/**
 * 作者： ${PING} on 2018/8/6.
 */
public class ConvertUtils {

    public static int getRandom(int start,int end){
        return start+new Random().nextInt(end-start+1);
    }

}
