package com.hongniu.baselibrary.config;

import com.hongniu.baselibrary.BuildConfig;

/**
 * 作者： ${桑小年} on 2018/7/31.
 * 努力，为梦长留
 */
public class Param {
    public static final boolean isDebug = BuildConfig.debugMode;
    public static final String UMENG = "UMENG";
    //    public static final boolean isDebug = false;
    public static String url;
    /**
     * 泓牛协议
     */
    public static String hongniu_agreement;
    public static String festivity_invity_notify;
    public static String hongniu_user_guide;//用户手册
    public static String home_top_bg;//用户手册
    //保险条款
    public static String insurance_polic;
    //投保须知
    public static String insurance_notify;
    //隐私协议
    public static String privacy="https://api.hongniudai.cn/static/html/privacy.html";

    static {
        if (isDebug) {
            url = "http://47.104.130.110:8030/hongniu/";
        } else {
            url = "https://api.hongniudai.cn/hongniu/";
        }
        hongniu_agreement = "https://api.hongniudai.cn/static/html/service.html";
        insurance_notify = "https://api.hongniudai.cn/static/html/notice.html";
        insurance_polic = "https://api.hongniudai.cn/static/html/insurance_2009.html";
        festivity_invity_notify = "https://api.hongniudai.cn/static/index.html#/invite_rule";
        hongniu_user_guide =   "https://api.hongniudai.cn/static/html/usermanual.html";
        home_top_bg =   "https://statichongniu.oss-cn-qingdao.aliyuncs.com/wlhy.jpg";
    }


    public static final String weChatAppid = "wxa9d4be10effd4626";


    /**
     * 最大图片数
     */
    public static int IMAGECOUNT=10;

    /**
     * 是否可以货车导航
     */
    public static final String CANTRUCK="CANTRUCK";
    public static final String CANTRUCKINFOR="CANTRUCKINFOR";


    /**
     * 牛人保
     */
    public static final String NIURENBAO ="https://api.hongniudai.cn/static/html/policyintroduction.html";

   /**
     * 牛贝规则
     */
    public static final String NIUBEI ="https://api.hongniudai.cn/static/cow_rule.html";
    /**
     * 华夏银行签约流程
     */
    public static final String HUAXIA ="https://api.hongniudai.cn/static/huaxia_process.html";
 /**
     * 华夏银行签约流程
     */
    public static final String CASHDEPOSIT ="https://api.hongniudai.cn/static/recognizance.html";


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
    //新车货匹配
    public static final int NEWMATCH= 10;


    //    AppKey: c33fbf23b76246bf8ee4a3d00b621e03
//    AppSecret: a7735d245d4241ff9f94-a3ecf7b5fedc
    //秘钥
    public static final String key = "85274113a1ce1c39";
    public static final String AppKey = "c33fbf23b76246bf8ee4a3d00b621e03";
    public static final String AppSecret = "a7735d245d4241ff9f94-a3ecf7b5fedc";

    public static final String TRAN = "tran";
    public static final String TYPE = "TYPE";
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
    //是否显示保险相关信息
    public static final String SHOW_INSURANCE = "SHOW_INSURANCE";

    //当前选中的物流公司所在城市
    public static String COMPANYCITY="COMPANYCIT";

    /**
     * 车货匹配页面，记录上次选择的标记
     */
    public static final String MATCHTYPE = "MATCHTYPE";

}
