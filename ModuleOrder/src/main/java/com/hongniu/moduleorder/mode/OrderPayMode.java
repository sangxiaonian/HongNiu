package com.hongniu.moduleorder.mode;

import android.text.TextUtils;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PayOrderInfor;
import com.hongniu.baselibrary.entity.WalletDetail;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.moduleorder.control.OrderPayControl;
import com.hongniu.baselibrary.entity.OrderInsuranceInforBean;
import com.hongniu.baselibrary.entity.PayParam;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.ConvertUtils;
import com.sang.thirdlibrary.pay.PayConfig;
import com.sang.thirdlibrary.pay.entiy.PayBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2018/10/29.
 */
public class OrderPayMode implements OrderPayControl.IOrderPayMode {

//    private boolean insurance;//是否是购买保险界面
//    private float money;//订单金额,就是运费
//    private String orderID;//订单id
//    private String orderNum;//订单号
    private float insurancePrice;
    private float cargoPrice;
    private boolean buyInsurance;//是否需要购买保险
    private int payType;//选择支付方式
//    private boolean isOnLine;//true 线上支付，false 线下支付
    private WalletDetail wallletInfor;
    private List<OrderInsuranceInforBean> insurancUserInfr;
    OrderInsuranceInforBean currentInsurancInfor;//当前选中的保险人信息
    private int roleType;//1 企业支付 2个人支付
    private int payWay;//付款方式 0 现付（线上支付） 1回付 2到付（线下支付）
    private PayOrderInfor orderInfor=new PayOrderInfor();//订单相关信息

    /**
     * 储存其他界面传入的参数
     *  @param event
     *
     */
    @Override
    public void saveTranDate(PayOrderInfor event) {
        this.orderInfor=event;
//        this.insurance = insurance;
//        this.money = money;
//        this.orderID = orderID;
//        this.orderNum = orderNum;
    }

    /**
     * 查询账户余额
     */
    @Override
    public Observable<CommonBean<WalletDetail>> queryAccount() {
      return HttpAppFactory.queryAccountdetails();
    }

    /**
     * 储存钱包账号信息
     *
     * @param data
     */
    @Override
    public void setAccountInfor(WalletDetail data) {
        this.wallletInfor=data;
    }

    /**
     * 获取订单ID
     *
     * @return
     */
    @Override
    public String getOrderId() {
        return orderInfor==null?"":orderInfor.orderID;
    }


    /**
     * 储存货物价格和保费价格
     *
     * @param cargoPrice     货物价格
     * @param insurancePrice 保费价格
     */
    @Override
    public void saveCargoPrice(String cargoPrice, String insurancePrice) {
        buyInsurance = true;
        try {
            this.cargoPrice = Float.parseFloat(cargoPrice);
            this.insurancePrice = Float.parseFloat(insurancePrice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消购买保险，情况保险信息
     */
    @Override
    public void clearInsuranceInfor() {
        buyInsurance = false;
        this.cargoPrice = 0;
        this.insurancePrice = 0;
    }

    /**
     * 支付方式(0微信,1银联,2线下支付 3 支付宝 4余额支付
     *
     * @param payType
     */
    @Override
    public void savePayType(int payType) {
        this.payType = payType;
    }

    /**
     * 根据货物价值查询保费
     *
     * @param cargoPrice
     */
    @Override
    public Observable<CommonBean<String>> queryInstance(String cargoPrice) {
        return HttpOrderFactory.queryInstancePrice(cargoPrice, getOrderId());
    }

    /**
     * 设置支付方式
     *
     * @param payWay
     */
    @Override
    public void savePayWays(int payWay) {
        this.payWay=payWay;
    }



    /**
     * 是否要显示支付方式
     *
     * @return 如果用户购买保险、单独购买保险，选择线上支付的方式 需要显示支付方式
     */
    @Override
    public boolean showPayWays() {
        return isInsurance()|| buyInsurance || payWay==0;
    }

    /**
     * 获取订单运费金额和保费的总金额,如果是单独购买保险界面，则返回的是保费
     *
     * @return
     */
    @Override
    public double getMoney() {

        if (isInsurance()) {//单独购买保险
            return insurancePrice;
        }else {
            float tranFee=0;
            if (payWay==0) {//线上支付
                tranFee=orderInfor==null?0:orderInfor.money;
            }
            return buyInsurance?(tranFee + insurancePrice):tranFee;
        }
    }

    /**
     * 是否是单独购买保费界面
     *
     * @return true 是
     */
    @Override
    public boolean isInsurance() {
        return orderInfor != null && orderInfor.insurance;
    }

    /**
     * 在支付总额上面，是否需要显示保费
     *
     * @return true 如果不是单独购买保险界面，并且线上支付且购买保险的情况下，才显示包含保费 n元的字样
     */
    @Override
    public boolean isShowPriceAboutInsurance() {
        return !isInsurance()&& payWay==0 && buyInsurance;
    }

    /**
     * 获取保费金额
     *
     * @return
     */
    @Override
    public double getInsurancePrice() {
        return insurancePrice;
    }

    /**
     * 开始支付
     * @param passWord
     * @param consigneeName
     * @param consigneePhone
     */
    @Override
    public Observable<CommonBean<PayBean>> getPayParams(String passWord, String consigneeName, String consigneePhone) {
        return HttpOrderFactory.payOrderOffLine(creatBuyParams(consigneeName,consigneePhone,passWord,payWay==0, !isInsurance(), isBuyInsurance()));
    }

    //获取支付方式
    @Override
    public int getPayType() {
        return (payWay==0 || buyInsurance) ? payType : 2;
    }


    /**
     * 是否购买保险
     * @return  true 购买了
     */
    @Override
    public boolean isBuyInsurance() {
        return buyInsurance;
    }

    /**
     * 获取订单号
     *
     * @return
     */
    @Override
    public String getOrderNum() {
        return orderInfor==null?"":orderInfor.orderNum;
    }

    /**
     * 获取货物金额
     *
     * @return
     */
    @Override
    public String getCargoPrice() {
        return cargoPrice+"";
    }

    /**
     * 账户余额是否充足
     *
     * @return
     */
    @Override
    public boolean isHasEnoughBalance() {
        if (roleType==2){
            return wallletInfor!=null&&Float.parseFloat(wallletInfor.getAvailableBalance())>=getMoney();
        }else if (roleType==1){
            return wallletInfor!=null&& wallletInfor.getCompanyAvailableBalance()>=getMoney();
        }else {
            return true;
        }
    }

    @Override
    public Observable<CommonBean<List<OrderInsuranceInforBean>>> queryInsuranceInfor() {

       return HttpOrderFactory.querInsruancUserInfor();
    }

    /**
     * 储存被保险人联系信息
     *
     * @param data
     */
    @Override
    public void saveInsruancUserInfor(List<OrderInsuranceInforBean> data) {
        this.insurancUserInfr=data;
        //如果当前没有选中，就默认选中默认数据
        if (!CommonUtils.isEmptyCollection(insurancUserInfr)&&currentInsurancInfor==null){

            for (OrderInsuranceInforBean orderInsuranceInforBean : insurancUserInfr) {
                if (orderInsuranceInforBean.isIsDefault()){
                    currentInsurancInfor=orderInsuranceInforBean;
                    break;
                }
            }
            //如果没有默认数据，就选中第最后一个
            if (currentInsurancInfor==null){
                currentInsurancInfor=insurancUserInfr.get(insurancUserInfr.size()-1);
            }
        }else {
            currentInsurancInfor=null;
        }
    }

    /**
     * 获取当前选中的被保险人信息数据
     *
     * @return
     */
    @Override
    public OrderInsuranceInforBean getCurrentInsuranceUserInfor() {
        return currentInsurancInfor;
    }

    /**
     * 储存当前被选中的被保险人信息
     *
     * @param currentInsuranceUserInfor
     */
    @Override
    public void saveSelectInsuranceInfor(OrderInsuranceInforBean currentInsuranceUserInfor) {
        currentInsurancInfor=currentInsuranceUserInfor;
    }

    /**
     * 储存当前支付类型
     *
     * @param roleType 1 企业支付 2个人支付
     */
    @Override
    public void savePayRole(int roleType) {
        this.roleType=roleType;
    }

    /**
     * 显示是否需要申请支付，
     *
     * @return true 申请企业支付，余额支付并且是线下支付切没有权限的时候显示
     */
    @Override
    public boolean showApplyCompanyPay() {
        return roleType==1&&payType==4&&wallletInfor!=null&&wallletInfor.getType()==2;
    }

    /**
     * 是否需要进行实名认证
     *
     * @return
     */
    @Override
    public boolean isRealNameAuthentication() {
        return wallletInfor==null||!wallletInfor.isSubAccStatus();
    }

    /**
     * 是否需要输入支付密码
     *
     * @return true 需要 目前仅有余额支付个人账户和企业支付有权限的时候需要显示
     *                  同时如果购买保险的时候也需要
     */
    @Override
    public boolean needPassword() {
        return (isBuyInsurance()||payWay==0)&&
        payType==4&&(roleType==2||(roleType==1&&wallletInfor!=null&&wallletInfor.getType()==3));
    }

    /**
     * 是否需要进行数据的初始化
     *
     * @return true 需要
     */
    @Override
    public boolean isInit() {
        return payType==0||roleType==0;
    }

    /**
     * 获取选择的支付方式
     *
     * @return
     */
    @Override
    public int getPayWays() {
        return payWay;
    }

    /**
     *
     *
     * @param consigneeName
     * @param consigneePhone
     * @param passWord   余额支付时候需要支付密码
     * @param onLine     线上线下支付方式
     * @param hasFrenght 是否支付运费（单独购买保险的时候不支付运费）
     * @param policy     是否购买保险
     * @return
     */
    private PayParam creatBuyParams(String consigneeName, String consigneePhone, String passWord, boolean onLine, boolean hasFrenght, boolean policy) {
        PayParam bean = new PayParam();
        bean.setPaybusiness(1);//订单支付，固定值
        bean.setOrderNum(getOrderNum());
        if (!TextUtils.isEmpty(passWord)){
            bean.setPayPassword(ConvertUtils.MD5(passWord));
        }
        bean.setHasFreight(hasFrenght);
        bean.setHasPolicy(policy);
        bean.setAppid(PayConfig.weChatAppid);
        bean.setOnlinePay(onLine);
        //线上支付或者购买保险的时候使用选中的支付方式，线下支付一律为2
//        0微信支付1银联支付2线下支付3支付宝支付4余额支付 5 企业支付
        if (payType==4&&roleType==1){
            bean.setPayType(5);
        }else {
            bean.setPayType((onLine || policy) ? payType : 2);
        }
        bean.setFreightPayClass(payWay+1);
        if (payWay==1){
            bean.setReceiptName(consigneeName);
            bean.setReceiptMobile(consigneePhone);
        }
        if (buyInsurance&&currentInsurancInfor!=null){
            bean.setInsuranceUserId(currentInsurancInfor.getId());
        }


        return bean;

    }


}
