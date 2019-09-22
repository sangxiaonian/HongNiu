package com.hongniu.moduleorder.present;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.entity.PayOrderInfor;
import com.hongniu.baselibrary.entity.WalletDetail;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderPayControl;
import com.hongniu.baselibrary.entity.OrderInsuranceInforBean;
import com.hongniu.moduleorder.entity.OrderInsuranceParam;
import com.hongniu.moduleorder.mode.OrderPayMode;
import com.sang.common.net.listener.TaskControl;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.thirdlibrary.pay.entiy.PayBean;

import java.util.List;

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
     * 储存其他界面传入的参数，同时做一些初始化的操作
     * @param event
     * @param listener
     */
    @Override
    public void saveTranDate(PayOrderInfor event, TaskControl.OnTaskListener listener) {
        mode.saveTranDate(event );
        view.setTranDate(event );
        if (mode.isInsurance()) {//如果是购买保险
            view.showBuyInsurance();
        } else {
            view.showPayOrder();
        }



      queryWallInfor(listener);
        //查询被保险人信息
        mode.queryInsuranceInfor()
                .subscribe(new NetObserver<List<OrderInsuranceInforBean>>(null) {
                    @Override
                    public void doOnSuccess(List<OrderInsuranceInforBean> data) {
                        mode.saveInsruancUserInfor(data);
                        OrderInsuranceInforBean currentInsuranceUserInfor = mode.getCurrentInsuranceUserInfor();
                        showInsurance(currentInsuranceUserInfor);
                    }
                });
        ;

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
                            view.showCargoInfor("货物金额" + cargoPrices+"元", "￥" + insurancePrice);
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
        view.changePayType(mode.getPayType(),mode.getPayRole());
        //每次更改支付方式都需要判断一次
        view.showHasCompanyApply(mode.showApplyCompanyPay());
    }

    /**
     * 支付
     *
     * @param consigneeName
     * @param consigneePhone
     * @param listener
     */
    @Override
    public void pay(String consigneeName, String consigneePhone, TaskControl.OnTaskListener listener) {

        if (mode.getPayWays()==1) {
            //对于回付，必填
            if (TextUtils.isEmpty(consigneeName)||TextUtils.isEmpty(consigneePhone)){
                view.showError("请输入正确的收货人姓名和手机号");
                return;
            }

        }
        //单独购买保险界面，却没有购买保险，
        if ((mode.isInsurance() && !mode.isBuyInsurance())) {
            view.noChoiceInsurance("请选择投保金额");

//            或者购买保险却没有选择被保险人信息
        } else if (mode.isBuyInsurance() && mode.getCurrentInsuranceUserInfor() == null) {
            view.noChoiceInsurance("请选择被保险人信息");
        } else if (mode.isRealNameAuthentication()) {
            //如果是余额支付，切没有实名认证，则调往实名认证界面
            view.realNameAuthentication("使用余额支付前，请先进行实名认证");
        } else  {
            if ( mode.needPassword()) {//余额支付
                if (Utils.querySetPassword()) {//设置过支付密码
                    view.showPasswordDialog(mode.getMoney());
                } else {
                    view.hasNoSetPassword();
                }
            } else {
                mode.getPayParams(null,consigneeName,consigneePhone)
                        .subscribe(new NetObserver<PayBean>(listener) {
                            @Override
                            public void doOnSuccess(PayBean data) {
                                view.jumpToPay(data, mode.getPayType(), mode.isBuyInsurance(), mode.getOrderId());
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
            view.jumpToInsurance(mode.getCargoPrice(), mode.getOrderNum(), mode.getOrderId());

        } else {
            view.jumpToMain();

        }
    }

    /**
     * 当选择余额支付的时候
     */
    @Override
    public void onChoiceYuePay() {
        //企业和个人余额都不足
        if (!mode.isComHasEnoughBalance()&&!mode.isPreHasEnoughBalance()) {
            view.showNoEnoughBalance();
        } else {
            view.onSelectYuePay();
        }
    }

    /**
     * 支付密码输入完成
     *
     * @param name
     * @param phone
     * @param passWord
     * @param listener
     */
    @Override
    public void setPayPassoword(String name, String phone, String passWord, TaskControl.OnTaskListener listener) {
        mode.getPayParams(passWord, name, phone)
                .subscribe(new NetObserver<PayBean>(listener) {
                    @Override
                    public void doOnSuccess(PayBean data) {
                        view.jumpToPay(data, mode.getPayType(), mode.isBuyInsurance(), mode.getOrderId());
                    }
                });
    }

    /**
     * 显示被保险人信息
     *
     * @param listener
     */
    @Override
    public void showInsurancDialog(TaskControl.OnTaskListener listener) {
        //查询被保险人信息
        mode.queryInsuranceInfor()
                .subscribe(new NetObserver<List<OrderInsuranceInforBean>>(listener) {
                    @Override
                    public void doOnSuccess(List<OrderInsuranceInforBean> data) {
                        mode.saveInsruancUserInfor(data);
                        view.showInsruanceUserInforDialog(data);
                    }
                });
        ;
    }

    /**
     * 选中保险人信息
     *
     * @param position                  位置
     * @param currentInsuranceUserInfor 被选中的保险人信息
     */
    @Override
    public void onSelectInsurancUserInfro(int position, OrderInsuranceInforBean currentInsuranceUserInfor) {

        showInsurance(currentInsuranceUserInfor);
    }

    /**
     * 查询被保险人列表
     *
     * @param id
     * @param listenre
     */
    @Override
    public void queryInsurance(final String id, TaskControl.OnTaskListener listenre) {
        mode.queryInsuranceInfor()
                .subscribe(new NetObserver<List<OrderInsuranceInforBean>>(listenre) {
                    @Override
                    public void doOnSuccess(List<OrderInsuranceInforBean> data) {
                        if (!TextUtils.isEmpty(id)) {
                            for (OrderInsuranceInforBean datum : data) {
                                if (id.equals(datum.getId())) {
                                    mode.saveInsruancUserInfor(data);
                                    showInsurance(datum);
                                }
                            }
                        }


                    }
                });
    }

    /**
     * 删除指定的被保险人
     *
     * @param id
     * @param listenre
     */
    @Override
    public void deletedInsurance(final String id, TaskControl.OnTaskListener listenre) {
        mode.queryInsuranceInfor()
                .subscribe(new NetObserver<List<OrderInsuranceInforBean>>(listenre) {
                    @Override
                    public void doOnSuccess(List<OrderInsuranceInforBean> data) {
                        OrderInsuranceInforBean currentInsuranceUserInfor = mode.getCurrentInsuranceUserInfor();
                        if (currentInsuranceUserInfor != null && currentInsuranceUserInfor.getId().equalsIgnoreCase(id)) {
                            mode.saveSelectInsuranceInfor(data.get(0));
                        }
                        mode.saveInsruancUserInfor(data);
                        showInsurance(mode.getCurrentInsuranceUserInfor());
                    }
                });
    }

    /**
     * 选中个人支付
     */
    @Override
    public void onChoicePersonPay() {
        mode.savePayRole(2);
        if (!mode.isComHasEnoughBalance()&&!mode.isPreHasEnoughBalance()){
            //如果企业余额和个人余额都不足,切换支付方式
            view.isHasEnoughBalance(mode.isComHasEnoughBalance(), mode.getPayType());
        }else if (mode.isComHasEnoughBalance()&&!mode.isPreHasEnoughBalance()){
            //如果企业余额充足，切换成企业支付方式
            ToastUtils.getInstance().show("个人余额不足");
            mode.savePayRole(1);
            setPayType(4);
        }
    }

    /**
     * 选中企业支付
     */
    @Override
    public void onChoiceCompanyPay() {
        mode.savePayRole(1);
        if (!mode.isComHasEnoughBalance()&&!mode.isPreHasEnoughBalance()){
            //如果企业余额和个人余额都不足,切换支付方式
            view.isHasEnoughBalance(mode.isComHasEnoughBalance(), mode.getPayType());
        }else if (!mode.isComHasEnoughBalance()&&mode.isPreHasEnoughBalance()){
            //如果个人余额充足，切换成个人支付方式
            ToastUtils.getInstance().show("企业余额不足");
            mode.savePayRole(2);
            setPayType(4);
        }


    }

    /**
     * 查询钱包信息
     * @param listener
     */
    @Override
    public void queryWallInfor(TaskControl.OnTaskListener listener) {
        //查询账户余额信息
        mode.queryAccount()
                .subscribe(new NetObserver<WalletDetail>(listener) {
                    @Override
                    public void doOnSuccess(WalletDetail data) {
                        mode.setAccountInfor(data);
                        view.showCompanyInfor(data.isCompanyPayPermission());
                        //有充足的余额的情况，查询完成之后直接选择余额支付
                        if (mode.isInit()) {
                            view.initPayWay();
                        }


                    }
                })
        ;
    }

    /**
     * 设置支付方式
     *
     * @param payWay 付款方式 0 现付（线上支付） 1回付 2到付（线下支付）
     */
    @Override
    public void setPayWay(int payWay) {
        mode.savePayWays(payWay);
        switchOnlinePay();
        view.switChconsignee(mode.getPayWays());
    }

    /**
     * 储存已知的保险信息
     *
     * @param event
     */
    @Override
    public void saveInsurance(OrderInsuranceParam event) {
        try {
            mode.saveCargoPrice(TextUtils.isEmpty(event.getPrice()) ? "0" : event.getPrice(), TextUtils.isEmpty(event.getInsurancePrice()) ? "0" : event.getInsurancePrice());
            float cargoPrices = Float.parseFloat(event.getPrice());
            float insurancePrice = Float.parseFloat(event.getInsurancePrice());
            //显示保险金额和货物金额
            view.showCargoInfor("货物金额" + cargoPrices+"元", "￥" + insurancePrice);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //保费更改之后，切换支付方式显示
        switchOnlinePay();
    }

    private void showInsurance(OrderInsuranceInforBean currentInsuranceUserInfor) {
        if (currentInsuranceUserInfor != null) {
            int insuredType = currentInsuranceUserInfor.getInsuredType();
            String title = "";
            String number = "";
            if (insuredType == 1) {
                title = currentInsuranceUserInfor.getUsername() == null ? "" : currentInsuranceUserInfor.getUsername();
                number = currentInsuranceUserInfor.getIdnumber() == null ? "" : currentInsuranceUserInfor.getIdnumber();
            } else if (insuredType == 2) {
                title = currentInsuranceUserInfor.getCompanyName() == null ? "" : currentInsuranceUserInfor.getCompanyName();
                number = currentInsuranceUserInfor.getCompanyCreditCode() == null ? "" : currentInsuranceUserInfor.getCompanyCreditCode();

            }
            mode.saveSelectInsuranceInfor(currentInsuranceUserInfor);
            view.showInsruanceUserInfor(title, number);

        }
    }
}
