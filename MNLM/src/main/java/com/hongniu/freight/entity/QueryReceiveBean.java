package com.hongniu.freight.entity;


import com.hongniu.thirdlibrary.picture.ImageInforBean;

import java.util.List;

/**
 * 作者： ${PING} on 2018/10/23.
 * 查看回单
 */
public class QueryReceiveBean {


    /**
     * remark : 测试
     * images : [{"id":1,"imageUrl":null},{"id":2,"imageUrl":null}]
     */

    private String remark;
    private String orderId;
    private List<UpImgData> images;
    private List<String> imageUrls;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<UpImgData> getImages() {
        return images;
    }

    public void setImages(List<UpImgData> images) {
        this.images = images;
    }


}
