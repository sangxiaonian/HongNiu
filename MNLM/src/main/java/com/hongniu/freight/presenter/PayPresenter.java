package com.hongniu.freight.presenter;

import com.fy.androidlibrary.net.listener.TaskControl;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.net.NetObserver;
import com.hongniu.freight.config.PayType;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.control.PayControl;
import com.hongniu.freight.entity.AccountDetailBean;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.mode.PayMode;
import com.hongniu.freight.utils.InfoUtils;
import com.hongniu.thirdlibrary.pay.entity.PayInfoBean;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

/**
 * 作者：  on 2020/3/5.
 */
public class PayPresenter implements PayControl.IPayPresenter {
    PayControl.IPayView view;
    PayControl.IPayMode mode;

    public PayPresenter(PayControl.IPayView view) {
        this.view = view;
        mode = new PayMode();
    }

    /**
     * 储存订单id
     *
     * @param id
     * @param type
     */
    @Override
    public void saveInfo(String id, int type) {
        mode.saveInfo(id,type);
    }

    /**
     * 查询订单和账户信息
     *
     * @param listener
     */
    @Override
    public void queryInfo(TaskControl.OnTaskListener listener) {
        Observable.zip(
                mode.queryOrderDetail(),
                mode.queryAccountDetails(),
                new BiFunction<CommonBean<OrderInfoBean>, CommonBean<AccountDetailBean>, CommonBean<AccountDetailBean>>() {
                    @Override
                    public CommonBean<AccountDetailBean> apply(CommonBean<OrderInfoBean> pageBeanCommonBean, CommonBean<AccountDetailBean> accountDetailBeanCommonBean) throws Exception {
                        if (pageBeanCommonBean.getCode() == Param.SUCCESS_FLAG) {
                            mode.saveOrderInfo(pageBeanCommonBean.getData());
                            view.showPriceInfo(mode.getOrderPriceInfo(), mode.getPriceDetail());
                        }
                        if (accountDetailBeanCommonBean.getCode() == Param.SUCCESS_FLAG) {
                            mode.saveAccountInfo(accountDetailBeanCommonBean.getData());
                            view.showAccountInfo(accountDetailBeanCommonBean.getData(), mode.getOrderPrice(),mode.getPayWay());
                        }
                        return accountDetailBeanCommonBean;
                    }
                })
                .subscribe(new NetObserver<AccountDetailBean>(listener));

    }

    /**
     * 切换支付方式
     *
     * @param type 0 余额支付 1企业支付 2微信支付 3支付宝 4银联
     */
    @Override
    public void switchPay(PayType type) {
        mode.switchPay(type);
        view.switchPay(type);
    }

    /**
     * 开始支付
     *
     * @param listener
     */
    @Override
    public void clickPay(TaskControl.OnTaskListener listener) {
        PayType payWay = mode.getPayWay();
        if (payWay == null) {
            view.showError("请选择支付方式");
        } else if (payWay==PayType.BALANCE||payWay==PayType.COMPANY) {
            if (mode.isSetPassWord()) {
                view.showSetPassWord();
            }else {
                view.showPassDialog(mode.getOrderPrice());
            }
        } else {
            mode.queryPayInfo(null)
                    .subscribe(new NetObserver<PayInfoBean>(listener) {
                        @Override
                        public void doOnSuccess(PayInfoBean payInfoBean) {
                            super.doOnSuccess(payInfoBean);
                            view.startPay(mode.getPayInfo(), payInfoBean);
                        }
                    })
            ;
        }
    }

    /**
     * 密码支付
     *
     * @param passWord
     * @param listener
     */
    @Override
    public void balancePay(String passWord, TaskControl.OnTaskListener listener) {
        mode.queryPayInfo(passWord)
                .subscribe(new NetObserver<PayInfoBean>(listener) {
                    @Override
                    public void doOnSuccess(PayInfoBean payInfoBean) {
                        super.doOnSuccess(payInfoBean);
                        view.startPay(mode.getPayInfo(),payInfoBean);
                    }
                });
    }

    /**
     * 支付成功
     */
    @Override
    public void paySuccess() {
        int type = mode.getType();
        if (type==1 ){
            if (InfoUtils.getRole(InfoUtils.getMyInfo())== Role.PLATFORM){
                view.jump2Succes(mode.getOrderInfo());
            }else {
                view.finishWithSuccess();
            }
        }else if (type==2){
            view.finishWithSuccess();
        }else if (type==3){
            view.finishWithSuccess();
        }
    }
}
