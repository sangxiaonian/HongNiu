package com.hongniu.modulecargoodsmatch.entity;

import com.hongniu.baselibrary.entity.PagerParambean;

/**
 * 作者： ${PING} on 2019/5/21.
 */
public class MatchGrapDetailParams extends PagerParambean {

    public String goodsSourceId;
    public String carId;
    public String robAmount;

    public MatchGrapDetailParams(int currentPage) {
        super(currentPage);
    }

}
