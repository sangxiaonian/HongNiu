package com.hongniu.moduleorder.control;

import android.text.SpannableStringBuilder;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.WalletDetail;
import com.sang.common.net.listener.TaskControl;
import com.sang.thirdlibrary.pay.entiy.PayBean;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2018/10/29.
 */
public class OrderPayControl {

    public interface IOrderPayView {

        /**
         * 根据传入的数值初始化界面数据
         *
         * @param money    订单金额
         * @param orderID  订单id
         * @param orderNum 订单号
         */
        void setTranDate(float money, String orderID, String orderNum);

        /**
         * 如果是单独购买保险功能，显示购买保险界面
         */
        void showBuyInsurance();

        /**
         * 如果是支付订单界面，显示支付订单界面
         */
        void showPayOrder();

        /**
         * 显示货物价格和保费信息
         *
         * @param cargoPrice
         * @param data
         */
        void showCargoInfor(String cargoPrice, String data);

        /**
         * 显示不购买保险时候的界面
         */
        void showNoInsurance();

        /**
         * 选择是否是线上支付方式
         *
         * @param onLinePay true 线上支付
         */
        void showOnLinePays(boolean onLinePay);

        /**
         * 显示金额
         * @param builder
         */
        void showPayCount(SpannableStringBuilder builder);


        /**
         * 开始支付并调往支付界面
         */
        void jumpToPay(PayBean data, int payType);

        /**
         * 线下支付，且无
         */
        void jumpToMain();

        /**
         * 支付完成，开始购买保险
         * @param price
         * @param orderNum
         * @param orderID
         */
        void jumpToInsurance(String price, String orderNum, String orderID);

        /**
         * 单独购买保险却尚未选择保险
         */
        void noChoiceInsurance();

        /**
         * 是否默认选中微信，余额是否充足
         * @param isEnough true 余额充足
         * @param payType
         */
        void isHasEnoughBalance(boolean isEnough, int payType);

        /**
         * 选中余额支付，但是余额不足
         */
        void showNoEnoughBalance();

        /**
         * 选中余额支付
         */
        void onSelectYuePay();

        /**
         * 显示支付密码键盘
         * @param money
         */
        void showPasswordDialog(double money);

        /**
         * 没置过支付密码
         */
        void hasNoSetPassword();
    }

    public interface IOrderPayPresent {

        /**
         * 储存其他界面传入的参数
         *  @param insurance 是否是购买保险界面
         * @param money     金额
         * @param orderID   订单ID
         * @param orderNum  订单号
         * @param listener
         */
        void saveTranDate(boolean insurance, float money, String orderID, String orderNum, TaskControl.OnTaskListener listener);

        /**
         * 线上支付被点击
         */
        void onLineClick();


        /**
         * 线下支付被点击，切换为线下支付
         */
        void onOffLineClick();

        /**
         * 切换线上线下支付
         */
        void switchOnlinePay();


        /**
         * 储存货物价格并计算保费
         *
         * @param cargoPrice
         * @param listener
         */
        void saveCargoInfor(String cargoPrice, TaskControl.OnTaskListener listener);

        /**
         * 取消保险，清空保险信息
         */
        void clearInsurance();

        /**
         * 选择支付方式 	支付方式(0微信,1银联,2线下支付 3 支付宝 4余额支付
         *
         * @param payType
         */
        void setPayType(int payType);

        /**
         * 支付
         * @param listener
         */
        void pay(TaskControl.OnTaskListener listener);

        /**
         * 支付成功
         */
        void paySucccessed();

        /**
         * 当选择余额支付的时候
         */
        void onChoiceYuePay();

        /**
         * 支付密码输入完成
         * @param passWord
         * @param listener
         */
        void setPayPassoword(String passWord, TaskControl.OnTaskListener listener);
    }

    public interface IOrderPayMode {
        /**
         * 储存其他界面传入的参数
         *
         * @param insurance 是否是购买保险界面
         * @param money     金额
         * @param orderID   订单ID
         * @param orderNum  订单号
         */
        void saveTranDate(boolean insurance, float money, String orderID, String orderNum);

        /**
         * 查询账户余额
         */
        Observable<CommonBean<WalletDetail>> queryAccount();

        /**
         * 储存钱包账号信息
         * @param data
         */
        void setAccountInfor(WalletDetail data);
        /**
         * 获取订单ID
         *
         * @return
         */
        String getOrderId();

        /**
         * 储存货物价格和保费价格
         *
         * @param cargoPrice 货物价格
         * @param data       保费价格
         */
        void saveCargoPrice(String cargoPrice, String data);

        /**
         * 取消购买保险，情况保险信息
         */
        void clearInsuranceInfor();

        /**
         * 支付方式(0微信,1银联,2线下支付 3 支付宝 4余额支付
         *
         * @param payType
         */
        void savePayType(int payType);

        /**
         * 根据货物价值查询保费
         *
         * @param cargoPrice
         */
        Observable<CommonBean<String>> queryInstance(String cargoPrice);

        /**
         * 储存是否是线上支付
         *
         * @param isOnLine
         */
        void saveOnLinePay(boolean isOnLine);

        /**
         * 是否要显示支付方式
         *
         * @return
         */
        boolean showPayWays();

        /**
         * 获取订单运费金额
         * @return
         */
        double getMoney();

        boolean isInsurance();

        boolean isShowPriceAboutInsurance();

        /**
         * 获取保费金额
         * @return
         */
        double getInsurancePrice();

        /**
         * 开始支付
         * @param passWord
         */
        Observable<CommonBean<PayBean>> getPayParams(String passWord);


        int getPayType();

        boolean isBuyInsurance();

        /**
         * 获取订单号
         * @return
         */
        String getOrderNum();

        /**
         * 获取货物金额
         * @return
         */
        String getCargoPrice();


        /**
         * 账户余额是否充足
         * @return
         */
        boolean isHasEnoughBalance();
    }

}
