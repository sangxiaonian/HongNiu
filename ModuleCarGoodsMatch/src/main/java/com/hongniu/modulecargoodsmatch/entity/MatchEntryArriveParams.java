package com.hongniu.modulecargoodsmatch.entity;

import java.util.List;

/**
 * 作者：  on 2019/11/3.
 *
 */
public class MatchEntryArriveParams {
    //确认送达
  private String  id	;//true	number	订单id
  private String  deliveryMark	;//true	string	送达备注
  private List<String> imageUrls	;//false	arrary	图片url列表(相对路径)
  private String content;//false	arrary	图片url列表(相对路径)
  private String serviceScore;//false	arrary	图片url列表(相对路径)

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(String serviceScore) {
        this.serviceScore = serviceScore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeliveryMark() {
        return deliveryMark;
    }

    public void setDeliveryMark(String deliveryMark) {
        this.deliveryMark = deliveryMark;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
