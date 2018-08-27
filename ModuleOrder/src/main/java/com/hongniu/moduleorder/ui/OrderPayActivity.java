package com.hongniu.moduleorder.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.entity.CreatInsuranceBean;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.entity.OrderParamBean;
import com.hongniu.moduleorder.entity.WxPayBean;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.hongniu.moduleorder.widget.dialog.BuyInsuranceDialog;
import com.sang.common.event.BusFactory;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.thirdlibrary.pay.PayConfig;
import com.sang.thirdlibrary.pay.wechat.WeChatAppPay;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 订单支付界面
 * <p>
 * 此界面逻辑稍显复杂，在此处进行简单说明：
 * 共分为2大类：1 支付方式：线上、线下  2 是否购买保险
 * <p>
 * 线上支付：
 * 判断是否有购买保险，如果有，支付金额为保险和运费 无则仅仅支付运费
 * <p>
 * 线下支付：
 * 判断是否有购买保险，如果有， 保险，此时必须选择微信支付，支付金额为保险费用
 * 不购买保险，则直接显示完成订单
 */
@Route(path = ArouterParamOrder.activity_order_pay)
public class OrderPayActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, BuyInsuranceDialog.OnBuyInsuranceClickListener {

    private TextView tvOrder;//订单号
    private ViewGroup btBuy;//购买保险
    private TextView tvPrice;//运费总额
    private RadioGroup rg;//支付方式
    private RadioButton rbOnline;//线上支付
    private RadioButton rbOffline;//线下支付
    private ViewGroup rlWechact;//微信支付
    private CheckBox checkbox;//选择是否微信支付
    private Button btPay;//支付订单
    private TextView tvPayAll;//支付总额

    private ViewGroup conInsurance;//保险条目
    private ViewGroup rl_tran;//运费显示条目
    private TextView tv_insurance_price;//保险金额
    private TextView tv_cargo_price;//货物金额
    private TextView bt_cancle_insurance;//取消保险
    private TextView tv_change_cargo_price;//更改货物金额


    private boolean onLine = true;
    private boolean buyInsurance;//是否购买保险
    private float insurancePrice;//保费
    private float cargoPrice;//货物金额
    private float tranPrice;//运费


    private BuyInsuranceDialog buyInsuranceDialog;
//    private InsuranceNoticeDialog noticeDialog;

    //是否为单独的购买保险界面
    private boolean isInsurance;
    private String orderID = "";//订单号
    private String orderNum = "";//订单号


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay);
        setToolbarDarkTitle(getString(R.string.order_pay));
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        tvOrder = findViewById(R.id.tv_order);
        btBuy = findViewById(R.id.con_buy);
        rg = findViewById(R.id.rg);
        rbOnline = findViewById(R.id.rb_online);
        rbOffline = findViewById(R.id.rb_offline);
        rlWechact = findViewById(R.id.rl_wechact);
        checkbox = findViewById(R.id.checkbox);
        btPay = findViewById(R.id.bt_pay);
        tvPayAll = findViewById(R.id.tv_pay_all);
        tvPrice = findViewById(R.id.tv_price);
        conInsurance = findViewById(R.id.con_insurance);
        tv_insurance_price = findViewById(R.id.tv_insurance_price);
        tv_cargo_price = findViewById(R.id.tv_cargo_price);
        bt_cancle_insurance = findViewById(R.id.bt_cancle_insurance);
        tv_change_cargo_price = findViewById(R.id.tv_change_cargo_price);
        rl_tran = findViewById(R.id.rl_tran);


        buyInsuranceDialog = new BuyInsuranceDialog(mContext);

//        noticeDialog = new InsuranceNoticeDialog(mContext);
    }

    @Override
    protected void initData() {
        super.initData();
        tvOrder.setText("订单号" + orderNum);
        tvPrice.setText("￥" + tranPrice);
    }

    @Override
    protected boolean getUseEventBus() {
        return true;
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OrderEvent.PayOrder event) {
        if (event != null) {
            //此处判断是否是购买保险
            isInsurance = event.insurance;
            tranPrice = event.money;
            orderID = event.orderID;
            orderNum = event.orderNum;

            if (isInsurance) {//如果是购买保险
                switchPayLine(false);
                rl_tran.setVisibility(View.GONE);
            } else {
                rl_tran.setVisibility(View.VISIBLE);
                switchPayLine(true);
            }
            switchToBuyInsurance(true);
            rbOnline.performClick();
            initData();

        }
        BusFactory.getBus().removeStickyEvent(event);
    }

    /**
     * 线上支付时候，支付保险和运费
     * 线下支付则不需要支付运费，如果选择保险，则需要支付保险，否则不需要
     */
    private void setTvPayAll() {
        if (onLine) {
            tvPayAll.setText("￥" + (tranPrice + insurancePrice) + "元");
        } else {
            tvPayAll.setText("￥" + (insurancePrice) + "元");
        }
        switchPay();
    }

    /**
     * 线上支付时候，支付保险和运费，显示支付订单
     * 线下支付则不需要支付运费，直接显示 完成订单
     * ，如果选择保险，则需要支付保险 支付订单
     */
    public void switchPay() {
        if (onLine) {
            btPay.setText("支付订单");
        } else {
            if (buyInsurance) {//如果线下支付，购买保险
                btPay.setText("支付订单");
            } else {
                btPay.setText("完成订单");
            }
        }
    }

    /**
     * 切换到卖保险条目
     *
     * @param buy true 没有购买保险
     */
    private void switchToBuyInsurance(boolean buy) {

        buyInsurance = !buy;
        if (buy) {
            conInsurance.setVisibility(View.GONE);
            btBuy.setVisibility(View.VISIBLE);
            insurancePrice = 0;
            cargoPrice = 0;
        } else {
            conInsurance.setVisibility(View.VISIBLE);
            btBuy.setVisibility(View.GONE);
            tv_cargo_price.setText("货物金额" + cargoPrice + "元");
            tv_insurance_price.setText("￥" + insurancePrice + "元");
        }
        setTvPayAll();
        switchPayLine(onLine);


    }


    /**
     * 切换线上线下支付方式
     *
     * @param line true 线上支付
     */
    public void switchPayLine(boolean line) {
        if (line) {
            onLine = true;
            rlWechact.setVisibility(View.VISIBLE);
        } else {
            onLine = false;
            if (buyInsurance) {//如果此时购买保险，则显示购买方式
                rlWechact.setVisibility(View.VISIBLE);
            } else {
                rlWechact.setVisibility(View.GONE);
            }
        }
        setTvPayAll();

    }


    @Override
    protected void initListener() {
        super.initListener();
        btBuy.setOnClickListener(this);
        btPay.setOnClickListener(this);
        rlWechact.setOnClickListener(this);
        rg.setOnCheckedChangeListener(this);
        bt_cancle_insurance.setOnClickListener(this);
        tv_change_cargo_price.setOnClickListener(this);
//        noticeDialog.setOnBottomClickListener(this);
        buyInsuranceDialog.setListener(this);


    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_online) {//线上支付

            switchPayLine(true);

        } else if (checkedId == R.id.rb_offline) {//线下支付

            switchPayLine(false);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.con_buy) {//购买保险

            if (Utils.checkInfor()) {
                buyInsuranceDialog.show();
            }else {
                showAleart("购买保险前，请先完善个人信息", new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        ArouterUtils.getInstance().builder(ArouterParamLogin.activity_person_infor).navigation(mContext);
                    }
                });
            }



        } else if (i == R.id.rl_wechact) {//选择微信支付
            checkbox.setChecked(!checkbox.isChecked());
        } else if (i == R.id.bt_pay) {//支付订单
            if (!isInsurance) {//订单支付界面
                if (onLine) {//线上支付

                    if (checkbox.isChecked()) {
                        OrderParamBean bean;
                        if (buyInsurance) {//购买保险
                            bean = creatBuyParams(true, true, true);
                        } else {//不购买保险
                            bean = creatBuyParams(true, true, false);
                        }

                        HttpOrderFactory.payOrderOffLine(bean)
                                .subscribe(new NetObserver<WxPayBean>(this) {
                                    @Override
                                    public void doOnSuccess(WxPayBean data) {
                                        startWeChatPay(data, buyInsurance);

                                    }
                                });
                    } else {
                        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("请选择支付方式");

                    }

                } else {//线下支付
                    if (buyInsurance) {//购买保险
                        if (checkbox.isChecked()) {
                            HttpOrderFactory.payOrderOffLine(creatBuyParams(false, true, true))
                                    .subscribe(new NetObserver<WxPayBean>(this) {
                                        @Override
                                        public void doOnSuccess(WxPayBean data) {
                                            startWeChatPay(data, true);

                                        }
                                    });

                        } else {
                            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("请选择支付方式");
                        }

                    } else {//不购买保险
                        HttpOrderFactory.payOrderOffLine(creatBuyParams(false, true, false))
                                .subscribe(new NetObserver<WxPayBean>(this) {
                                    @Override
                                    public void doOnSuccess(WxPayBean data) {
                                        finish();
                                        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                                    }
                                });
                    }
                }
            } else {//保险购买界面
                if (checkbox.isChecked()) {
                    //单独购买保险
                    if (cargoPrice > 0) {
                        HttpOrderFactory.payOrderOffLine(creatBuyParams(true, false, true))
                                .subscribe(new NetObserver<WxPayBean>(this) {
                                    @Override
                                    public void doOnSuccess(WxPayBean data) {
                                        startWeChatPay(data, true);
                                    }
                                });
                    } else {
                        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("请设置保险金额");
                    }
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("请选择支付方式");

                }

            }

        } else if (i == R.id.bt_cancle_insurance) {//取消保险
            switchToBuyInsurance(true);

        } else if (i == R.id.tv_change_cargo_price) {//修改保险金额
            buyInsuranceDialog.show();
        }
    }

    /**
     * 吊起微信支付
     *
     * @param data
     * @param isCreatInsurance
     */
    private void startWeChatPay(WxPayBean data, boolean isCreatInsurance) {
        CreatInsuranceBean creatInsuranceBean = new CreatInsuranceBean();
        creatInsuranceBean.setGoodsValue(cargoPrice + "");
        creatInsuranceBean.setOrderNum(orderNum);
        Event.CraetInsurance insurance = new Event.CraetInsurance(creatInsuranceBean);
        insurance.isCreatInsurance = isCreatInsurance;
        BusFactory.getBus().postSticky(insurance);
        WeChatAppPay.pay(mContext, data.getPartnerId(), data.getPrePayId(), data.getPrepay_id()
                , data.getNonceStr(), data.getTimeStamp(), data.getPaySign()
        );
    }


    /**
     * 创建订单数据
     *
     * @param onLine     是否是线上
     * @param hasFrenght 是否支付运费
     * @param policy     是否购买保险
     * @return
     */
    private OrderParamBean creatBuyParams(boolean onLine, boolean hasFrenght, boolean policy) {
        OrderParamBean bean = new OrderParamBean();
        bean.setOrderNum(orderNum);
        bean.setHasFreight(hasFrenght);
        bean.setHasPolicy(policy);
        bean.setAppid(PayConfig.weChatAppid);
        bean.setOnlinePay(onLine);
        return bean;

    }

    @Override
    public void entryClick(Dialog dialog, boolean checked, String cargoPrice) {
        try {
            this.cargoPrice = Float.parseFloat(cargoPrice);
            HttpOrderFactory.queryInstancePrice(cargoPrice, orderID)
                    .subscribe(new NetObserver<String>(this) {
                        @Override
                        public void doOnSuccess(String data) {
                            insurancePrice = Float.parseFloat(data);
                            switchToBuyInsurance(false);
                        }
                    });
            ;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        dialog.dismiss();
    }

    @Override
    public void noticeClick(BuyInsuranceDialog buyInsuranceDialog, boolean checked) {
//        buyInsuranceDialog.dismiss();
        buyInsuranceDialog.setReadInsurance(true);
//        noticeDialog.show();
    }


    @Override
    public void onBackPressed() {
        creatDialog("确认要离开支付？", "您的订单已创建，离开后可在订单页继续支付", "确定离开", "继续支付")
                .setLeftClickListener(new DialogControl.OnButtonLeftClickListener() {
                    @Override
                    public void onLeftClick(View view, DialogControl.ICenterDialog dialog) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .creatDialog(new CenterAlertDialog(mContext))
                .show();
    }

    private CenterAlertBuilder creatDialog(String title, String content, String btleft, String btRight) {
        return new CenterAlertBuilder()
                .setDialogTitle(title)
                .setDialogContent(content)
                .setBtLeft(btleft)
                .setBtRight(btRight)
                .setBtLeftColor(getResources().getColor(R.color.color_title_dark))
                .setBtRightColor(getResources().getColor(R.color.color_white))
                .setBtRightBgRes(R.drawable.shape_f06f28);
    }
}
