package com.hongniu.freight.control;

import com.fy.androidlibrary.net.listener.TaskControl;
import com.fy.companylibrary.entity.CommonBean;
import com.hongniu.freight.config.PayType;
import com.hongniu.freight.entity.AccountDetailBean;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.entity.QueryPayInfoParams;
import com.sang.thirdlibrary.pay.PayInfoBean;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/3/5.
 */
public class PayControl {
    public interface IPayView{
        /**
         * 展示价格信息
         * @param orderPrice  订单需要支付的价格
         * @param priceDetail 价格详情
         */
        void showPriceInfo(String orderPrice, String priceDetail);

        /**
         * 显示账户信息
         * @param data
         * @param orderPrice
         * @param payWay
         */
        void showAccountInfo(AccountDetailBean data, float orderPrice, PayType payWay);

        void showError(String alert);
        /**
         * 切换支付方式
         * @param type
         */
        void switchPay(PayType type);
        /**
         * 开始支付
         * @param payWay 支付方式
         * @param payInfoBean
         */
        void startPay(QueryPayInfoParams payWay, PayInfoBean payInfoBean);

        /**
         * 余额支付时弹出密码弹窗
         * @param orderPrice
         */
        void showPassDialog(float orderPrice);

        /**
         * 设置支付密码
         */
        void showSetPassWord();


        /**
         * 支付成功
         * @param orderInfo
         */
        void jump2Succes(OrderInfoBean orderInfo);

        /**
         * 支付成功
         */
        void finishWithSuccess();


    }
    public interface IPayPresenter{
        /**
         * 储存订单id
         * @param id
         * @param type
         */
        void saveInfo(String id, int type);

        /**
         * 查询订单和账户信息
         * @param listener
         */
        void queryInfo(TaskControl.OnTaskListener listener);
        /**
         * 切换支付方式
         *
         * @param type 0 余额支付 1企业支付 2微信支付 3支付宝 4银联
         */
        void switchPay(PayType type);
        /**
         * 开始支付
         * @param listener
         */
        void clickPay(TaskControl.OnTaskListener listener);

        /**
         * 密码支付
         * @param passWord
         * @param listener
         */
        void balancePay(String passWord, TaskControl.OnTaskListener listener);

        /**
         * 支付成功
         */
        void paySuccess();
    }
    public interface IPayMode{
        /**
         * 储存订单id
         * @param id
         * @param type
         */
        void saveInfo(String id, int type);


        /**
         * 查询订单详情
         * @return
         */
        Observable<CommonBean<OrderInfoBean>> queryOrderDetail();

        /**
         * 查询账户详情
         * @return
         */
        Observable<CommonBean<AccountDetailBean>> queryAccountDetails();

        /**
         * 储存订单信息
         * @param data
         */
        void saveOrderInfo(OrderInfoBean data);
        OrderInfoBean getOrderInfo();

        /**
         * 储存账户信息
         * @param data
         */
        void saveAccountInfo(AccountDetailBean data);

        /**
         * 获取订单价格
         * @return
         */
        float getOrderPrice();
  /**
         * 获取订单价格
         * @return
         */
        String getOrderPriceInfo();

        /**
         *
         * @return 价格详情
         */
        String getPriceDetail();

        /**
         * 切换支付方式
         *
         * @param type 0 余额支付 1企业支付 2微信支付 3支付宝 4银联
         */
        void switchPay(PayType type);

        /**
         * 获取支付方式
         * @return
         */
        PayType getPayWay();

        /**
         * 是否需要设置密码
         * @return true 需要
         */
        boolean isSetPassWord();
        /**
         * 查找支付信息
         * @return
         * @param passWord
         */
        Observable<CommonBean<PayInfoBean>> queryPayInfo(String passWord);


        /**
         * 获取需要的支付数据
         * @return
         */
        QueryPayInfoParams getPayInfo();

        /**
         * 获取支付类型
         * @return
         */
        int getType();
    }
}
