package com.hongniu.moduleorder.control;

import android.text.SpannableStringBuilder;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PayOrderInfor;
import com.hongniu.baselibrary.entity.PolicyCaculParam;
import com.hongniu.baselibrary.entity.WalletDetail;
import com.hongniu.baselibrary.entity.OrderInsuranceInforBean;
import com.hongniu.moduleorder.entity.OrderInsuranceParam;
import com.fy.androidlibrary.net.listener.TaskControl;
import com.sang.thirdlibrary.pay.entiy.PayBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2018/10/29.
 */
public class OrderPayControl {

    public interface IOrderPayView {

        /**
         * 根据传入的数值初始化界面数据
         *
         * @param event
         */
        void setTranDate(PayOrderInfor event);

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
         *
         * @param builder
         */
        void showPayCount(SpannableStringBuilder builder);


        /**
         * 开始支付并调往支付界面
         */
        void jumpToPay(PayBean data, int payType, boolean buyInsurance, String orderId);

        /**
         * 线下支付，且无
         */
        void jumpToMain();

        /**
         * 支付完成，开始购买保险
         *
         * @param price
         * @param orderNum
         * @param orderID
         */
        void jumpToInsurance(String price, String orderNum, String orderID);

        /**
         * 单独购买保险却尚未选择保险
         *
         * @param title
         */
        void noChoiceInsurance(String title);

        /**
         * 是否默认选中微信，余额是否充足
         *
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
         *
         * @param money
         */
        void showPasswordDialog(double money);

        /**
         * 没置过支付密码
         */
        void hasNoSetPassword();

        /**
         * 初始化支付方式，有充足的余额的情况，查询完成之后直接选择余额支付，否则选择微信支付
         */
        void initPayWay();

        /**
         * 显示被保险人信息
         *
         * @param name   个人名称或者公司名称
         * @param number 个人身份照明或者公司税号
         */
        void showInsruanceUserInfor(String name, String number);

        /**
         * 显示被保险人信息列表
         *
         * @param data
         */
        void showInsruanceUserInforDialog(List<OrderInsuranceInforBean> data);

        /**
         * 是否有企业支付权限
         *
         * @param companyPayPermission true 有
         */
        void showCompanyInfor(boolean companyPayPermission);

        /**
         * 显示是否为申请支付
         *
         * @param showApplyCompanyPay true 是
         */
        void showHasCompanyApply(boolean showApplyCompanyPay);

        /**
         * 调往实名认证界面进行实名认证
         *
         * @param s
         */
        void realNameAuthentication(String s);

        /**
         * 切换支付方式，判断是否显示收货人信息
         *
         * @param payWays
         */
        void switChconsignee(int payWays);

        /**
         * @param msg
         */
        void showError(String msg);

        /**
         * 更改支付方式UI
         *
         * @param payType
         * @param payRole
         */
        void changePayType(int payType, int payRole);
    }

    public interface IOrderPayPresent {

        /**
         * 储存其他界面传入的参数
         *
         * @param event
         * @param listener
         */
        void saveTranDate(PayOrderInfor event, TaskControl.OnTaskListener listener);


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
         *
         * @param consigneeName
         * @param consigneePhone
         * @param listener
         */
        void pay(String consigneeName, String consigneePhone, TaskControl.OnTaskListener listener);

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
         *
         * @param name
         * @param phone
         * @param passWord
         * @param listener
         */
        void setPayPassoword(String name, String phone, String passWord, TaskControl.OnTaskListener listener);

        /**
         * 显示被保险人信息
         *
         * @param listener
         */
        void showInsurancDialog(TaskControl.OnTaskListener listener);

        /**
         * 选中保险人信息
         *
         * @param position 位置
         * @param bean     被选中的保险人信息
         */
        void onSelectInsurancUserInfro(int position, OrderInsuranceInforBean bean);

        /**
         * 查询被保险人列表
         *
         * @param
         * @param listenre
         */
        void queryInsurance(String id, TaskControl.OnTaskListener listenre);

        /**
         * 删除指定的被保险人
         *
         * @param id
         * @param listenre
         */
        void deletedInsurance(String id, TaskControl.OnTaskListener listenre);

        /**
         * 选中个人支付
         */
        void onChoicePersonPay();

        /**
         * 选中企业支付
         */
        void onChoiceCompanyPay();

        /**
         * 查询钱包信息
         *
         * @param listener
         */
        void queryWallInfor(TaskControl.OnTaskListener listener);

        /**
         * 设置支付方式
         *
         * @param payWay 付款方式 0 现付（线上支付） 1回付 2到付（线下支付）
         */
        void setPayWay(int payWay);

        /**
         * 储存已知的保险信息
         * @param event
         */
        void saveInsurance(OrderInsuranceParam event);

        /**
         * 获取保险信息
         * @return
         */
        PolicyCaculParam getPolicyInfo();

        void savePloicyInfo(PolicyCaculParam parcelableExtra);
    }

    public interface IOrderPayMode {
        /**
         * 储存其他界面传入的参数
         *
         * @param event
         */
        void saveTranDate(PayOrderInfor event);

        /**
         * 查询账户余额
         */
        Observable<CommonBean<WalletDetail>> queryAccount();

        /**
         * 储存钱包账号信息
         *
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
         * 设置支付方式
         *
         * @param payWay
         */
        void savePayWays(int payWay);

        /**
         * 是否要显示支付方式
         *
         * @return
         */
        boolean showPayWays();

        /**
         * 获取订单运费金额
         *
         * @return
         */
        double getMoney();

        boolean isInsurance();

        boolean isShowPriceAboutInsurance();

        /**
         * 获取保费金额
         *
         * @return
         */
        double getInsurancePrice();

        /**
         * 开始支付
         *
         * @param passWord
         * @param consigneeName
         * @param consigneePhone
         */
        Observable<CommonBean<PayBean>> getPayParams(String passWord, String consigneeName, String consigneePhone);


        /**
         * 0微信,1银联,2线下支付 3 支付宝 4余额支付
         *
         * @return 0微信, 1银联, 2线下支付 3 支付宝 4余额支付
         */
        int getPayType();

        /**
         * 是否购买保险
         *
         * @return true 购买保险了
         */
        boolean isBuyInsurance();

        /**
         * 获取订单号
         *
         * @return
         */
        String getOrderNum();

        /**
         * 获取货物金额
         *
         * @return
         */
        String getCargoPrice();


        /**
         * 账户余额是否充足
         *
         * @return
         */
        boolean isHasEnoughBalance();

        /**
         * 个人账户余额是否充足
         *
         * @return
         */
        boolean isPreHasEnoughBalance();

        /**
         * 企业账户余额是否充足
         *
         * @return
         */
        boolean isComHasEnoughBalance();


        /**
         * 查询被保险人信息
         */
        Observable<CommonBean<List<OrderInsuranceInforBean>>> queryInsuranceInfor();

        /**
         * 储存被保险人联系信息
         *
         * @param data
         */
        void saveInsruancUserInfor(List<OrderInsuranceInforBean> data);

        /**
         * 获取当前选中的被保险人信息数据
         *
         * @return
         */
        OrderInsuranceInforBean getCurrentInsuranceUserInfor();

        /**
         * 储存当前被选中的被保险人信息
         *
         * @param currentInsuranceUserInfor
         */
        void saveSelectInsuranceInfor(OrderInsuranceInforBean currentInsuranceUserInfor);

        /**
         * 储存当前支付类型
         *
         * @param roleType 1 企业支付 2个人支付
         */
        void savePayRole(int roleType);

        /**
         * 显示是否需要申请支付
         *
         * @return true 申请企业支付
         */
        boolean showApplyCompanyPay();

        /**
         * 是否需要进行实名认证
         *
         * @return
         */
        boolean isRealNameAuthentication();

        /**
         * 是否需要输入支付密码
         *
         * @return true 需要 目前仅有余额支付个人账户和企业支付有权限的时候需要显示
         */
        boolean needPassword();

        /**
         * 是否需要进行数据的初始化
         *
         * @return true 需要
         */
        boolean isInit();


        /**
         * 获取选择的支付方式
         *
         * @return
         */
        int getPayWays();

        /**
         * 获取企业支付和余额支付方式
         *
         * @return
         */
        int getPayRole();

        PolicyCaculParam getPolicyParams();

        void savePloicyInfo(PolicyCaculParam parcelableExtra);
    }

}
