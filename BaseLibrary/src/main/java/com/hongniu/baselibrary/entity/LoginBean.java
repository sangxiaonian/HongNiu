package com.hongniu.baselibrary.entity;

/**
 * 作者： ${PING} on 2018/8/14.
 * 登录后返回的个人信息
 */
public class LoginBean {


    /**
     * id : 251
     * mobile : 15515871516
     * registerTime : 1534310738000
     * openId :
     * token : b1358b252c2246a4b60bfd0a410b5f9d
     * type : 2
     * state : null
     * logo : null
     * contact : null
     * nickname : null
     * gender : null
     * remarks : null
     */

    private String id;//用户id,也是车主ID
    private String mobile;//用户手机号
    private long registerTime;//
    private String openId;//微信的openid
    private String token;//本次登录的token，有有效期
    private int type;//用户类型。无实际作用。
    private int state;//状态
    private String logo;//微信登录时用户图像的地址
    private String contact;//联系方式
    private String nickname;//昵称
    private String gender;//性别
    private String remarks;//备注

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
