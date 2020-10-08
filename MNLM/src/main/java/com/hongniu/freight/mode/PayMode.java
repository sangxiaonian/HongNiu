package com.hongniu.freight.mode;

import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.hongniu.freight.config.PayType;
import com.hongniu.freight.control.PayControl;
import com.hongniu.freight.entity.AccountDetailBean;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.entity.QueryPayInfoParams;
import com.hongniu.freight.net.HttpAppFactory;
import com.sang.thirdlibrary.pay.PayConfig;
import com.sang.thirdlibrary.pay.PayInfoBean;
import com.sang.thirdlibrary.pay.wechat.WeChatAppPay;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/3/5.
 */
public class PayMode implements PayControl.IPayMode {
    private String id;
    private OrderInfoBean orderInfo;
    private AccountDetailBean accountInfo;
    private PayType payType;//切换支付方式
    private int type;//付款类型 支付业务类型(1订单支付2补款运费支付3补购保险支付)

    /**
     * 储存订单id
     *
     * @param id
     * @param type
     */
    @Override
    public void saveInfo(String id, int type) {
        this.id = id;
        this.type = type;
    }

    /**
     * 查询订单详情
     *
     * @return
     */
    @Override
    public Observable<CommonBean<OrderInfoBean>> queryOrderDetail() {
        return HttpAppFactory.queryOrderDetail(id);
    }

    /**
     * 查询账户详情
     *
     * @return
     */
    @Override
    public Observable<CommonBean<AccountDetailBean>> queryAccountDetails() {
        return HttpAppFactory.queryAccountDetails();
    }

    /**
     * 储存订单信息
     *
     * @param data
     */
    @Override
    public void saveOrderInfo(OrderInfoBean data) {
        this.orderInfo = data;
    }

    @Override
    public OrderInfoBean getOrderInfo() {
        return orderInfo;
    }

    /**
     * 储存账户信息
     *
     * @param data
     */
    @Override
    public void saveAccountInfo(AccountDetailBean data) {
        this.accountInfo = data;
    }

    /**
     * 获取订单价格
     *
     * @return
     */
    @Override
    public float getOrderPrice() {
        float pay = 0;
        if (orderInfo != null) {
            if (type == 1) {//运费支付
                if (orderInfo.getInsurance() == 1&&orderInfo.getPayPolicyState()==0) {
                    pay = (float) (orderInfo.getPolicyMoney() + orderInfo.getMoney());
                } else {
                    pay = (float) orderInfo.getMoney();
                }
            } else if (type == 2) {//补运费支付
                pay = (float) orderInfo.getBalanceMoney();
            } else if (type == 3) {//补购保险
                pay = (float) orderInfo.getPolicyMoney();
            }
        }
        return pay;
    }

    /**
     * 获取订单价格
     *
     * @return
     */
    @Override
    public String getOrderPriceInfo() {
        return "￥" + ConvertUtils.changeFloat(getOrderPrice(), 2);
    }

    /**
     * @return 价格详情
     */
    @Override
    public String getPriceDetail() {
        String des = "";
        if (orderInfo != null) {

            if (type == 1) {//运费支付
                if (orderInfo.getInsurance() == 1&&orderInfo.getPayPolicyState()==0) {
                    des = String.format("运费：%s\t\t保费：%s", ConvertUtils.changeFloat(orderInfo.getMoney(), 2), ConvertUtils.changeFloat(orderInfo.getPolicyMoney(), 2));
                } else {
                    des = String.format("运费：%s", ConvertUtils.changeFloat(orderInfo.getMoney(), 2));
                }
            } else if (type == 2) {//补运费支付
                des = String.format("运费差额：%s", ConvertUtils.changeFloat(orderInfo.getBalanceMoney(), 2));
            } else if (type == 3) {//补购保险
                des = String.format("保费：%s", ConvertUtils.changeFloat(orderInfo.getPolicyMoney(), 2));

            }
        }
        return des;
    }

    /**
     * 切换支付方式
     *
     * @param type 0 余额支付 1企业支付 2微信支付 3支付宝 4银联
     */
    @Override
    public void switchPay(PayType type) {
        this.payType = type;
    }

    /**
     * 获取支付方式
     *
     * @return
     */
    @Override
    public PayType getPayWay() {
        return payType;
    }

    /**
     * 是否需要设置密码
     *
     * @return true 需要
     */
    @Override
    public boolean isSetPassWord() {
        return accountInfo != null && !accountInfo.isSetPassWord();
    }

    /**
     * 查找支付信息
     *
     * @param passWord
     * @return
     */
    @Override
    public Observable<CommonBean<PayInfoBean>> queryPayInfo(String passWord) {
        QueryPayInfoParams payInfoParams = getPayInfo();

        if (passWord != null) {
            payInfoParams.setPayPassword(ConvertUtils.MD5(passWord));
        }
        return HttpAppFactory.queryPayInfo(payInfoParams);
    }

    /**
     * 获取需要的支付数据
     *
     * @return
     */
    @Override
    public QueryPayInfoParams getPayInfo() {
        QueryPayInfoParams payInfoParams = new QueryPayInfoParams();
        payInfoParams.setPaybusiness(type);
        payInfoParams.setType(payType);
        payInfoParams.setOrderId(id);
        payInfoParams.setPayType(payType.getPayType());
        payInfoParams.setOrderNum(orderInfo != null ? orderInfo.getOrderNum() : "");
        //TODO appid待定
        payInfoParams.setAppid(PayConfig.weChatAppid);
        return payInfoParams;
    }

    /**
     * 获取支付类型
     *
     * @return
     */
    @Override
    public int getType() {
        return type;
    }
}
