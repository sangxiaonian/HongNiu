package com.hongniu.modulelogin.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： ${PING} on 2018/9/12.
 */
public class Citys {

    List<NewAreaBean> shengs=new ArrayList<>();
    List<List<NewAreaBean>> shis=new ArrayList<>();
    List<List<List<NewAreaBean>>> quyus=new ArrayList<>();

    public List<NewAreaBean> getShengs() {
        return shengs;
    }

    public void setShengs(List<NewAreaBean> shengs) {
        this.shengs = shengs;
    }

    public List<List<NewAreaBean>> getShis() {
        return shis;
    }

    public void setShis(List<List<NewAreaBean>> shis) {
        this.shis = shis;
    }

    public List<List<List<NewAreaBean>>> getQuyus() {
        return quyus;
    }

    public void setQuyus(List<List<List<NewAreaBean>>> quyus) {
        this.quyus = quyus;
    }

}
