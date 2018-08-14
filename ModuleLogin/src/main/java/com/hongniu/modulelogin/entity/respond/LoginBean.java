package com.hongniu.modulelogin.entity.respond;

/**
 * 作者： ${PING} on 2018/8/14.
 * 登录后返回的个人信息
 */
public class LoginBean {


    /**
     * true	string	与codetype对应
     */
    private String  usercode	;

    /**
     * true	string	openid或token，openid对应微信端登陆，token对应app端登陆
     */
    private String  codetype;
    /**
     true	string	头像
     */
   private String  logo	;
    /**
     * true	string	用户昵称
     */
   private String  nickName	;
    /**
     * false	string	性别,1男,2女,0未知
     */
   private String  gender	;
    /**
     * 详细地址 false	string
     */
   private String  address	;
















}
