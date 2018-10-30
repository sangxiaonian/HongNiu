package com.hongniu.baselibrary.config;

/**
 * 作者： ${桑小年} on 2018/7/31.
 * 努力，为梦长留
 */
public class Param {
    public static final boolean isDebug = true;



    public static final String weChatAppid = "wxa9d4be10effd4626";
    public static String url;

    /**
     * 泓牛协议
     */
    public static String hongniu_agreement;
    public static String festivity_invity_notify;
    //保险条款
    public static String insurance_polic;
    //投保须知
    public static String insurance_notify;
    /**
     * 最大图片数
     */
    public static int IMAGECOUNT=10;

    static {
        if (isDebug) {
//            url = "http://47.104.130.110:8080/";
            url = "http://test.wxshare.hongniudai.cn/";
//            url = "http://b09daa47.ngrok.io/";
        } else {
            url = "https://api.hongniudai.cn/";
        }
        hongniu_agreement = url + "static/html/service.html";
        insurance_notify = url + "static/html/notice.html";
        insurance_polic = url + "static/html/insurance_2009.html";
        festivity_invity_notify = url + "static/index.html#/invite_rule";
    }


    /**
     * 分页查找每页数据数量
     */
    public static final int PAGE_SIZE = 20;
    /**
     * 确认到达时候，距离目的地的最小距离 单位 千米
     */
    public static final int ENTRY_MIN = 5000;
    //回单
    public static final int REEIVE = 2;
    //货单
    public static final int GOODS= 1;


    //    AppKey: c33fbf23b76246bf8ee4a3d00b621e03
//    AppSecret: a7735d245d4241ff9f94-a3ecf7b5fedc
    //秘钥
    public static final String key = "85274113a1ce1c39";
    public static final String AppKey = "c33fbf23b76246bf8ee4a3d00b621e03";
    public static final String AppSecret = "a7735d245d4241ff9f94-a3ecf7b5fedc";

    public static final String TRAN = "tran";
    public static final String TITLE = "TITLE";
    public static final String VERTYPE = "VERTYPE";//验证码使用类型


    /**
     * 是否已经展示新手引导页面
     */
    public static final String showGuideActicity = "showGuideActicity";


    //新手引导遮罩
    public static final String ORDER_MAIN_TITLE_GUIDE = "ORDER_MAIN_TITLE_GUIDE";//角色切换
    public static final String ORDER_MAIN_FINANCE_GUIDE = "ORDER_MAIN_FINANCE_GUIDE";//财务按钮按钮
    public static final String FINACNE_HISTOGRAM = "FINACNE_HISTOGRAM";//财务柱状图
    //登陆信息储存key
    public static final String LOGIN_ONFOR = "LOGIN_ONFOR";
    //个人信息
    public static final String PERSON_ONFOR = "PERSON_ONFOR";
    //是否有设置过密码
    public static final String HASPAYPASSWORD = "HASPAYPASSWORD";
    /**
     * 退出登录
     */
    public static final String LOGIN_OUT = "LOGIN_OUT";
    /**
     * 车辆类型
     */
    public static final String CAR_TYPE = "CAR_TYPE";

}
