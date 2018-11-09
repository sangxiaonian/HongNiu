package com.hongniu.modulefinance.entity;

import com.hongniu.baselibrary.entity.PagerParambean;

/**
 * 作者： ${桑小年} on 2018/10/28.
 * 努力，为梦长留
 */
public class CareNumPageBean extends PagerParambean {
    public String carNum;

    public CareNumPageBean(int currentPage, String carNumber) {
        super(currentPage);
        this.carNum = carNumber;
    }


}
