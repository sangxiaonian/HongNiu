package com.hongniu.baselibrary.entity;

import com.hongniu.baselibrary.config.Param;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class PagerParambean {
    public int pageNum = 1;//默认1
    public int pageSize = Param.PAGE_SIZE;//默认20
    public PagerParambean(int currentPage) {
        this.pageNum=currentPage;
    }
}
