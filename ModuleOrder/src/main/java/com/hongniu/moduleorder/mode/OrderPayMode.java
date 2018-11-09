package com.hongniu.moduleorder.mode;

import android.text.TextUtils;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.WalletDetail;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.moduleorder.control.OrderPayControl;
import com.hongniu.moduleorder.entity.OrderParamBean;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.sang.common.utils.ConvertUtils;
import com.sang.thirdlibrary.pay.PayConfig;
import com.sang.thirdlibrary.pay.entiy.PayBean;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2018/10/29.
 */
public class OrderPayMode implements OrderPayControl.IOrderPayMode {

    private boolean insurance;//是否是购买保险界面
    private float money;//订单金额,就是运费
    private String orderID;//订单id
    private String orderNum;//订单号
    private float insurancePrice;
    private float cargoPrice;
    private boolean buyInsurance;//是否需要购买保险
    private int payType;//选择支付方式
    private boolean isOnLine;//true 线上支付，false 线下支付
    private WalletDetail wallletInfor;

    /**
     * 储存其他界面传入的参数
     *
     * @param insurance 是否是购买保险界面
     * @param money     金额
     * @param orderID   订单ID
     * @param orderNum  订单号
     */
    @Override
    public void saveTranDate(boolean insurance, float money, String orderID, String orderNum) {
        this.insurance = insurance;
        this.money = money;
        this.orderID = orderID;
        this.orderNum = orderNum;
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
        return orderID;
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
     * 储存是否是线上支付
     *
     * @param isOnLine
     */
    @Override
    public void saveOnLinePay(boolean isOnLine) {
        this.isOnLine = isOnLine;
    }

    /**
     * 是否要显示支付方式
     *
     * @return 如果用户购买保险、单独购买保险，选择线上支付的方式 需要显示支付方式
     */
    @Override
    public boolean showPayWays() {
        return insurance || buyInsurance || isOnLine;
    }

    /**
     * 获取订单运费金额和保费的总金额,如果是单独购买保险界面，则返回的是保费
     *
     * @return
     */
    @Override
    public double getMoney() {

        if (insurance) {//单独购买保险
            return insurancePrice;
        }else {
            float tranFee=0;
            if (isOnLine) {//线上支付
                tranFee=money;
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
        return insurance;
    }

    /**
     * 在支付总额上面，是否需要显示保费
     *
     * @return true 如果不是单独购买保险界面，并且线上支付且购买保险的情况下，才显示包含保费 n元的字样
     */
    @Override
    public boolean isShowPriceAboutInsurance() {
        return !insurance && isOnLine && buyInsurance;
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
     */
    @Override
    public Observable<CommonBean<PayBean>> getPayParams(String passWord) {
        return HttpOrderFactory.payOrderOffLine(creatBuyParams(passWord,isOnLine, !insurance, buyInsurance));
    }

    //获取支付方式
    @Override
    public int getPayType() {
        return (isOnLine || buyInsurance) ? payType : 2;
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
        return orderNum;
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
        return wallletInfor!=null&&Float.parseFloat(wallletInfor.getAvailableBalance())>=getMoney();
    }

    /**
     *
     * @param passWord   余额支付时候需要支付密码
     * @param onLine     线上线下支付方式
     * @param hasFrenght 是否支付运费（单独购买保险的时候不支付运费）
     * @param policy     是否购买保险
     * @return
     */
    private OrderParamBean creatBuyParams(String passWord, boolean onLine, boolean hasFrenght, boolean policy) {
        OrderParamBean bean = new OrderParamBean();
        bean.setOrderNum(orderNum);
        if (!TextUtils.isEmpty(passWord)){
            bean.setPayPassword(ConvertUtils.MD5(passWord));
        }
        bean.setHasFreight(hasFrenght);
        bean.setHasPolicy(policy);
        bean.setAppid(PayConfig.weChatAppid);
        bean.setOnlinePay(onLine);
        //线上支付或者购买保险的时候使用选中的支付方式，线下支付一律为2
//        0微信支付1银联支付2线下支付3支付宝支付
        bean.setPayType((onLine || policy) ? payType : 2);
        return bean;

    }


}
