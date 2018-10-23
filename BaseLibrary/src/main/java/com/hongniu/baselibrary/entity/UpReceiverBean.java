package com.hongniu.baselibrary.entity;

import java.util.List;

/**
 * 作者： ${PING} on 2018/10/23.
 * 上传回单
 */
public class UpReceiverBean {

    /**
     * true	number	订单ID
     */
    private String orderId	;
    /**
     * false	string	备注
     */
    private String remark	;
    /**
     * false	arrary	图片url数组(增量)
     */
    private List<String> imageUrls	;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
