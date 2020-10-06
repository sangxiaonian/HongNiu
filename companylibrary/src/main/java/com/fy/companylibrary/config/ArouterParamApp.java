package com.fy.companylibrary.config;

/**
 * 作者：  on 2019/10/30.
 * module 中使用到的Arouter 路径
 * <p>
 * 命名时注意参考已有命名方式，/module/路径
 * <p>
 * 为了避免重复路径以 module+activity/fragment开头 ，注意不要有重复，重复会引起异常，难以排查
 * <p>
 * 新建Module时候，需要重新创建一个配置，要求和此配置类似
 */
public class ArouterParamApp {

    public static final String activity_login = "/app/activity_login";
    /**
     * 身份认证选择角色
     */
    public static final String activity_attestation_select_role = "/app/activity_attestation_select_role";
    /**
     * 身份认证第二部
     */
    public static final String activity_attestation_role_activity = "/app/activity_attestation_role_activity";
    /**
     * 人脸识别
     */
    public static final String activity_attestation_face = "/app/activity_attestation_face";
    /**
     * 公司承运人身份认证第二部
     */
    public static final String fragment_attestation_carrier_company = "/app/fragment_attestation_carrier_company";
    /**
     * 个人承运人身份认证第二部
     */
    public static final String fragment_attestation_carrier_personal = "/app/fragment_attestation_carrier_personal";
    /**
     * 司机身份认证
     */
    public static final String fragment_attestation_driver = "/app/fragment_attestation_driver";
    /**
     * 公司托运人身份认证第二部
     */
    public static final String fragment_attestation_shipper_company = "/app/fragment_attestation_shipper_company";
    /**
     * 个人托运人身份认证第二部
     */
    public static final String fragment_attestation_shipper_personal = "/app/fragment_attestation_shipper_personal";

    public static final String fragment_home = "/app/fragment_home";
    public static final String fragment_home_type = "/app/fragment_home_type";
    public static final String activity_main = "/app/activity_main";
    public static final String activity_my_order = "/app/activity_my_order";
    public static final String fragment_my_order = "/app/fragment_my_order";
    //意见反馈
    public static final String activity_face_back = "/app/activity_face_back";
    /**
     * 个人中心
     */
    public static final String fragment_person_center = "/app/fragment_person_center";
    /**
     * 搜索订单
     */
    public static final String activity_search_order = "/app/activity_search_order";
    /**
     * 首页订单数据
     */
    public static final String fragment_order_home = "/app/fragment_order_home";
    /**
     * 订单详情也
     */
    public static final String activity_order_detail = "/app/activity_order_detail";
    /**
     * 发布找车
     */
    public static final String activity_order_find_car = "/app/activity_order_find_car";
    /**
     * 立即派车
     */
    public static final String activity_assign_order = "/app/activity_assign_order";
    /**
     * 评价
     */
    public static final String activity_appraise = "/app/activity_appraise";
    /**
     * 实际承运人我要接单
     */
    public static final String activity_order_receive = "/app/activity_order_receive";
    /**
     * 立刻派单搜索除车辆
     */
    public static final String activity_search_car = "/app/activity_search_car";

    public static final String activity_h5 = "/app/activity_h5";

    /**
     * 月账单
     */
    public static final String activity_bill_month = "/app/activity_bill_month";
    /**
     * 账单搜索
     */
    public static final String activity_bill_search = "/app/activity_bill_search";
    /**
     * 月账单支出
     */
    public static final String fragment_bill_month_expend = "/app/fragment_bill_month_expend";
    public static final String fragment_bill_month_child = "/app/fragment_bill_month_child";
    /**
     * 月账单收入
     */
    public static final String fragment_bill_month_income = "/app/fragment_bill_month_income";
    /**
     * 余额
     */
    public static final String activity_balance = "/app/activity_balance";
    /**
     * 余额提现
     */
    public static final String activity_balance_pending = "/app/activity_balance_pending";
    /**
     * 关于我们
     */
    public static final String activity_about_us = "/app/activity_about_us";
    /**
     * 我的车辆信息
     */
    public static final String activity_my_car_list = "/app/activity_my_car_list";
    /**
     * 新增修改车辆
     */
    public static final String activity_car_add_modify = "/app/activity_car_add_modify";
    /**
     * 我要接单
     */
    public static final String activity_order_center = "/app/activity_order_center";
    /**
     * 查看轨迹
     */
    public static final String activity_map_check_path = "/app/activity_map_check_path";


    /**
     * 接单中心
     */
    public static final String activity_order_receiving = "/app/activity_order_receiving";
    /**
     * 我的金库
     */
    public static final String activity_my_coffers = "/app/activity_my_coffers";
    /**
     * 账单余额明细和带入张明细数据
     */
    public static final String fragment_bill_month = "/app/fragment_bill_month";
    /**
     * 我要发货 创建/修改订单
     */
    public static final String activity_order_create = "/app/activity_order_create";
    /**
     * 我要发货选择地址
     */
    public static final String activity_map_search = "/app/activity_map_search";
    /**
     * 支付订单页面
     */
    public static final String activity_pay = "/app/activity_pay";
    /**
     * 支付成功
     */
    public static final String activity_pay_result = "/app/activity_pay_result";
    /**
     * 更新设置支付密码
     */
    public static final String activity_forget_pass = "/app/activity_forget_pass";

    /**
     * 支付完成之后,查询支付结果
     */
    public static final String activity_waite_pay = "/app/activity_waite_pay";
    /**
     * 被保险人相关信息
     */
    public static final String activity_insured_info = "/app/activity_insured_info";
    /**
     * 绑定银行卡
     */
    public static final String activity_bind_blank_card = "/app/activity_bind_blank_card";
    /**
     * 查看/上传回单
     */
    public static final String activity_order_up_receipt = "/app/activity_order_up_receipt";
    /**
     * 图片预览
     */
    public static final String activity_preview_image = "/app/activity_preview_image";

    /**
     * 二维码扫描
     */
    public static final String activity_qrcode = "/app/activity_qrcode";
    public static final String activity_way_bill = "/app/activity_way_bill";
    public static final String fragment_hand_input = "/app/fragment_hand_input";

}
