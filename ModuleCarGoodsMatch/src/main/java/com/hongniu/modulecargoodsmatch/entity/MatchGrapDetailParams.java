package com.hongniu.modulecargoodsmatch.entity;

import com.hongniu.baselibrary.entity.PagerParambean;

/**
 * 作者： ${PING} on 2019/5/21.
 */
public class MatchGrapDetailParams extends PagerParambean {

    public String goodsSourceId;
    public String carId;
    public String robAmount;
    public String robId;//接单ID
    public int status;//抢单状态(0(生成)待接单1(已支付)待确认2已确认3已失效4已完成)

    public MatchGrapDetailParams(int currentPage) {
        super(currentPage);
    }

}
