package com.fy.companylibrary.config;

/**
 * 作者：  on 2019/10/29.
 */
public class Param {


//    public static final String debugUrl = "http://47.104.130.110:9080/";
    public static final String releaseUrl = "https://wlhyapi.xn--yh-0h3f6pk58c0xd.com/";
    public static final String debugUrl = "https://wlhyapi.xn--yh-0h3f6pk58c0xd.com/";
    public static final long IMAGESIZE = 1024 * 1024 * 5;
    public static final float INSURANCE = 0.00015f;//保费计算费率
    public static final String UMENGTOKEN = "UMENGTOKEN";

    public static String baseUrl;


    /**
     * 华夏银行签约流程
     */
    public static final String HUAXIA ="https://api.hongniudai.cn/static/huaxia_process.html";

    //App本身秘钥
    public static final String key = "85274113a1ce1c39";
    public static final String AppKey = "c33fbf23b76246bf8ee4a3d00b621e03";
    public static final String AppSecret = "a7735d245d4241ff9f94-a3ecf7b5fedc";

    public static final int PAGE_SIZE = 20;
    public static final int SUCCESS_FLAG = 200;//返回数据成功的标记
    public static final String TRAN = "TRAN";
    public static final String TYPE = "TYPE";
    //合同
   public static final String  hongniu_agreement = "https://api.hongniudai.cn/static/wlhydemocontract.html";

   //保险
   public static final String  insurance_notify = "https://api.hongniudai.cn/static/wlhydemopolicy.html";
   //许可协议
   public static final String  agreement = "https://api.hongniudai.cn/static/wlhydemoagreement.html";

  public static final String h_insurance_notify = "https://api.hongniudai.cn/static/wlhydemonotice.html";
  public static final String h_insurance_polic = "https://api.hongniudai.cn/static/wlhydemopolicy.html";

    //隐私协议
    public static final String  hongniu_privacy = "https://api.hongniudai.cn/static/wlhyprotocol.html";
    //网络货运介绍
    public static final String  insurance_polic = "https://api.hongniudai.cn/static/wlhydemoservice.html";

    /**
     * 确认到达时候，距离目的地的最小距离 单位 千米
     */
    public static final int ENTRY_MIN = 5000;
    public static String LOGIN="login";//登录信息
    public static String MY="my";//个人信息

    public static String LOGIN_MNLM="login_mnlm";//登录信息
    public static String MY_MNLM="my_mnlm";//个人信息

    public static String ID ="ID";
}
