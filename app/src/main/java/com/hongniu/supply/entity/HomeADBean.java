package com.hongniu.supply.entity;

/**
 * 作者： ${PING} on 2018/11/29.
 */
public class HomeADBean {


    /**
     * subtitle : 副标题副标题副标题副标题副标题副标题
     * link : http://localhost:8087/hongniuManager/hongniu/activity/preview/1
     * title : 标题标题
     * picture : https://dev-hongniu.oss-cn-qingdao.aliyuncs.com/images/activity%5C2018/11%5C05154285538989216image/jpeg?Expires=1543563640&OSSAccessKeyId=LTAI9F6JMK14eAcU&Signature=nAi8A65WzKm5BGPcBRZKt%2BFquVY%3D
     */

    private String subtitle;
    private String link;
    private String title;
    private String picture;

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
