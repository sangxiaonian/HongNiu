package com.hongniu.modulefinance.entity;

import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.PagerParambean;

/**
 * 作者： ${桑小年} on 2018/10/28.
 * 努力，为梦长留
 */
public class CareNumPageBean extends PagerParambean {
    public String userId;

    public CareNumPageBean(int currentPage, String userId) {
        super(currentPage);
        this.userId = userId;
    }


}
