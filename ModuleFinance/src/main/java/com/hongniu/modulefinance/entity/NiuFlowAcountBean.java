package com.hongniu.modulefinance.entity;

import com.hongniu.baselibrary.entity.PageBean;

/**
 * 作者： ${桑小年} on 2018/10/28.
 * 努力，为梦长留
 */
public class NiuFlowAcountBean {


    private PageBean<NiuOfAccountBean> integralFlows = new PageBean<>();
    private PageBean<NiuOfAccountBean> tobeIntegralFlows = new PageBean<>();

    public PageBean<NiuOfAccountBean> getTobeIntegralFlows() {
        return tobeIntegralFlows;
    }

    public void setTobeIntegralFlows(PageBean<NiuOfAccountBean> tobeIntegralFlows) {
        this.tobeIntegralFlows = tobeIntegralFlows;
    }

    public PageBean<NiuOfAccountBean> getIntegralFlows() {
        return integralFlows;
    }

    public void setIntegralFlows(PageBean<NiuOfAccountBean> integralFlows) {
        this.integralFlows = integralFlows;
    }
}
