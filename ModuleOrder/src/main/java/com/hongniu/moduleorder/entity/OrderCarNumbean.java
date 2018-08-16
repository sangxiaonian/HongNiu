package com.hongniu.moduleorder.entity;

/**
 * 作者： ${PING} on 2018/8/16.
 * 创建订单时候车牌号联想
 */
public class OrderCarNumbean {
    /**
     * true	string	车辆id
     */
    private String id	    ;
    /**
     * true	string	车牌号
     */
    private String carNumber	;
    /**
     * true	string	车架号
     */
    private String carCode	;
    /**
     * true	string	司机姓名
     */
    private String ownerName	;
    /**
     * 	true	string	司机手机号
     */
    private String ownerPhone;
    /**
     * true	string	车主id
     */
    private String userId	;
    /**
     * 	true	string	添加时间
     */
    private String createTime;
    /**
     * true	true	是否删除
     */
    private String del	    ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.del = del;
    }

    @Override
    public String toString() {
        return carNumber==null?"":carNumber;
    }

}
