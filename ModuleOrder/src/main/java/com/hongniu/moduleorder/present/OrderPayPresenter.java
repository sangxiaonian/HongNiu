package com.hongniu.moduleorder.present;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.entity.WalletDetail;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderPayControl;
import com.hongniu.moduleorder.mode.OrderPayMode;
import com.sang.common.net.listener.TaskControl;
import com.sang.common.utils.ConvertUtils;
import com.sang.thirdlibrary.pay.entiy.PayBean;

/**
 * 作者： ${PING} on 2018/10/29.
 */
public class OrderPayPresenter implements OrderPayControl.IOrderPayPresent {
    private Context context;
    private OrderPayControl.IOrderPayMode mode;
    private OrderPayControl.IOrderPayView view;

    public OrderPayPresenter(Context context, OrderPayControl.IOrderPayView view) {
        this.view = view;
        this.context = context;
        mode = new OrderPayMode();
    }

    /**
     * 储存其他界面传入的参数
     *
     * @param insurance 是否是购买保险界面
     * @param money     金额
     * @param orderID   订单ID
     * @param orderNum  订单号
     * @param listener
     */
    @Override
    public void saveTranDate(boolean insurance, final float money, String orderID, String orderNum, TaskControl.OnTaskListener listener) {
        mode.saveTranDate(insurance, money, orderID, orderNum);
        view.setTranDate(money, orderID, orderNum);
        if (insurance) {//如果是购买保险
            view.showBuyInsurance();
        } else {
            view.showPayOrder();
        }

        mode.queryAccount()
                .subscribe(new NetObserver<WalletDetail>(listener) {
                    @Override
                    public void doOnSuccess(WalletDetail data) {
                        mode.setAccountInfor(data);
                        view.isHasEnoughBalance(mode.isHasEnoughBalance(), mode.getPayType());

                    }
                })
        ;


    }

    /**
     * 线上支付被点击
     */
    @Override
    public void onLineClick() {
        mode.saveOnLinePay(true);
        switchOnlinePay();
    }


    /**
     * 线下支付被点击，切换为线下支付
     */
    @Override
    public void onOffLineClick() {
        mode.saveOnLinePay(false);
        switchOnlinePay();
    }

    /**
     * 切换线上线下支付，同时会收到是否购买保险的影响，此处可以处理金额
     */
    @Override
    public void switchOnlinePay() {
        //如果是线下支付，购买保险的话需要显示支付方式
        view.showOnLinePays(mode.showPayWays());

        //切换之后，更改金额

        String tranPrice = "￥" + ConvertUtils.changeFloat(mode.getMoney(), 2);


        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(tranPrice);

        if (mode.isShowPriceAboutInsurance()) {
            builder.append("（含保费" + ConvertUtils.changeFloat(mode.getInsurancePrice(), 2) + "元）");
        }
        ForegroundColorSpan span = new ForegroundColorSpan(context.getResources().getColor(R.color.color_light));
        builder.setSpan(span, 0, tranPrice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        view.showPayCount(builder);
        //判断是否需要切换支付方式
        view.isHasEnoughBalance(mode.isHasEnoughBalance(), mode.getPayType());


    }

    /**
     * 储存货物价格并计算保费
     *
     * @param cargoPrice
     * @param listener
     */
    @Override
    public void saveCargoInfor(final String cargoPrice, TaskControl.OnTaskListener listener) {
        mode.queryInstance(cargoPrice)
                .subscribe(new NetObserver<String>(listener) {
                    @Override
                    public void doOnSuccess(String data) {
                        try {
                            mode.saveCargoPrice(TextUtils.isEmpty(cargoPrice) ? "0" : cargoPrice, TextUtils.isEmpty(data) ? "0" : data);
                            float cargoPrices = Float.parseFloat(cargoPrice);
                            float insurancePrice = Float.parseFloat(data);
                            //显示保险金额和货物金额
                            view.showCargoInfor("￥" + cargoPrices, "￥" + insurancePrice);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //保费更改之后，切换支付方式显示
                        switchOnlinePay();

                    }
                });
    }

    /**
     * 取消保险，清空保险信息
     */
    @Override
    public void clearInsurance() {
        mode.clearInsuranceInfor();
        view.showNoInsurance();
        switchOnlinePay();

    }

    /**
     * 选择支付方式 	支付方式(0微信,1银联,2线下支付 3 支付宝 4余额支付
     *
     * @param payType
     */
    @Override
    public void setPayType(int payType) {
        mode.savePayType(payType);
    }

    /**
     * 支付
     *
     * @param listener
     */
    @Override
    public void pay(TaskControl.OnTaskListener listener) {
        if (mode.isInsurance() && !mode.isBuyInsurance()) {
            view.noChoiceInsurance();
        } else {
            if (mode.getPayType() == 4) {//余额支付
                if (Utils.querySetPassword()) {//设置过支付密码
                    view.showPasswordDialog(mode.getMoney());
                } else {
                    view.hasNoSetPassword();
                }
            } else {
                mode.getPayParams(null)
                        .subscribe(new NetObserver<PayBean>(listener) {
                            @Override
                            public void doOnSuccess(PayBean data) {
                                view.jumpToPay(data, mode.getPayType());
                            }
                        });
            }


        }


    }

    /**
     * 支付成功
     */
    @Override
    public void paySucccessed() {
        if (mode.isBuyInsurance()) {
            //调往保险购买界面
            view.jumpToInsurance(mode.getCargoPrice(), mode.getOrderNum(),mode.getOrderId());

        } else {
            view.jumpToMain();

        }
    }

    /**
     * 当选择余额支付的时候
     */
    @Override
    public void onChoiceYuePay() {
        if (!mode.isHasEnoughBalance()) {
            view.showNoEnoughBalance();
        } else {
            view.onSelectYuePay();
        }
    }

    /**
     * 支付密码输入完成
     *
     * @param passWord
     * @param listener
     */
    @Override
    public void setPayPassoword(String passWord, TaskControl.OnTaskListener listener) {
        mode.getPayParams(passWord)
                .subscribe(new NetObserver<PayBean>(listener) {
                    @Override
                    public void doOnSuccess(PayBean data) {
                        view.jumpToPay(data, mode.getPayType());
                    }
                });
    }
}
