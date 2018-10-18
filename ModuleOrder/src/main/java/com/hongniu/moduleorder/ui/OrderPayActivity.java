package com.hongniu.moduleorder.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CreatInsuranceBean;
import com.hongniu.baselibrary.entity.H5Config;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.entity.OrderParamBean;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.hongniu.moduleorder.widget.dialog.BuyInsuranceDialog;
import com.hongniu.moduleorder.widget.dialog.InsuranceNoticeDialog;
import com.sang.common.event.BusFactory;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.thirdlibrary.pay.PayClient;
import com.sang.thirdlibrary.pay.PayConfig;
import com.sang.thirdlibrary.pay.control.PayControl;
import com.sang.thirdlibrary.pay.entiy.PayBean;
import com.sang.thirdlibrary.pay.entiy.PayType;

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
public class OrderPayActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, BuyInsuranceDialog.OnBuyInsuranceClickListener, DialogControl.OnButtonBottomClickListener {

    private TextView tvOrder;//订单号
    private ViewGroup btBuy;//购买保险
    private TextView tvPrice;//运费总额
    private RadioGroup rg;//支付方式
    private RadioButton rbOnline;//线上支付
    private RadioButton rbOffline;//线下支付
    private ViewGroup rlWechact;//微信支付
    private ImageView cbWechat;//选择是否微信支付
    private ViewGroup rlAli;//支付宝

    private ImageView cbAli;//选择是否支付宝支付
    private ViewGroup rlUnion;//银联支付
    private ImageView cbUnion;//选择是银联支付

    private ViewGroup rlYue;//余额支付
    private ImageView cbYue;//选择余额支付


    private Button btPay;//支付订单
    private TextView tvPayAll;//支付总额


    private ViewGroup conInsurance;//保险条目
    private ViewGroup payOnline;//线上支付的支付方式
    private ViewGroup rl_tran;//运费显示条目
    private TextView tv_insurance_price;//保险金额
    private TextView tv_cargo_price;//货物金额
    private TextView bt_cancle_insurance;//取消保险
    private TextView tv_change_cargo_price;//更改货物金额
    private TextView tv_des;//订单描述


    private boolean onLine = true;
    private boolean buyInsurance;//是否购买保险
    private float insurancePrice;//保费
    private float cargoPrice;//货物金额
    private float tranPrice;//运费


    private BuyInsuranceDialog buyInsuranceDialog;
    private InsuranceNoticeDialog noticeDialog;

    //是否为单独的购买保险界面
    private boolean isInsurance;
    private String orderID = "";//订单号
    private String orderNum = "";//订单号
    private PayType payType;
    private PayClient payClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay);
        setToolbarDarkTitle(getString(R.string.order_pay));
        initView();
        initData();
        initListener();
        //默认选中微信支付
        rlWechact.performClick();
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
        cbWechat = findViewById(R.id.checkbox);
        btPay = findViewById(R.id.bt_pay);
        tvPayAll = findViewById(R.id.tv_pay_all);
        tvPrice = findViewById(R.id.tv_price);
        conInsurance = findViewById(R.id.con_insurance);
        tv_insurance_price = findViewById(R.id.tv_insurance_price);
        tv_cargo_price = findViewById(R.id.tv_cargo_price);
        bt_cancle_insurance = findViewById(R.id.bt_cancle_insurance);
        tv_change_cargo_price = findViewById(R.id.tv_change_cargo_price);
        rl_tran = findViewById(R.id.rl_tran);
        tv_des = findViewById(R.id.tv_des);
        rlAli = findViewById(R.id.rl_ali);
        cbAli = findViewById(R.id.ali_box);
        rlUnion = findViewById(R.id.rl_union);
        cbUnion = findViewById(R.id.union_box);

        rlYue = findViewById(R.id.rl_yue);
        cbYue = findViewById(R.id.cb_yue);

        payOnline = findViewById(R.id.pay_online);

        buyInsuranceDialog = new BuyInsuranceDialog(mContext);

        noticeDialog = new InsuranceNoticeDialog(mContext);
    }

    @Override
    protected void initData() {
        super.initData();
        tvOrder.setText("订单号" + orderNum);
        tvPrice.setText("￥" + tranPrice);
        buyInsuranceDialog.setOrderID(orderID);
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
                tv_des.setText(R.string.order_pay_insruance_buy_des);
                setToolbarDarkTitle(getString(R.string.order_pay_insruance_pay));
                rl_tran.setVisibility(View.GONE);
            } else {
                setToolbarDarkTitle(getString(R.string.order_pay));
                tv_des.setText(R.string.order_pay_success_driver_start);
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
        float price;

        if (onLine) {
            price = tranPrice + insurancePrice;
        } else {
            price = insurancePrice;
        }
        String tranPrice = "￥" + ConvertUtils.changeFloat(price, 2);


        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(tranPrice);
        if (!isInsurance && onLine && buyInsurance) {
            builder.append("（含保费" + ConvertUtils.changeFloat(insurancePrice, 2) + "元）");
        }
        ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.color_light));
        builder.setSpan(span, 0, tranPrice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPayAll.setText(builder);
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
            payOnline.setVisibility(View.VISIBLE);
        } else {
            onLine = false;
            if (buyInsurance) {//如果此时购买保险，则显示购买方式
                payOnline.setVisibility(View.VISIBLE);
            } else {
                payOnline.setVisibility(View.GONE);
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
        noticeDialog.setOnBottomClickListener(this);
        buyInsuranceDialog.setListener(this);
        rlAli.setOnClickListener(this);
        rlUnion.setOnClickListener(this);
        rlYue.setOnClickListener(this);
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
            } else {
                showAleart("购买保险前，请先完善个人信息", new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        ArouterUtils.getInstance().builder(ArouterParamLogin.activity_person_infor).navigation(mContext);
                    }
                });
            }


        } else if (i == R.id.rl_wechact) {//选择微信支付
            changePayType(PayType.WECHAT);
        } else if (i == R.id.rl_ali) {//选择支付宝
            //支付宝暂未接入，无效的支付方式
            changePayType(PayType.ALI);

        } else if (i == R.id.rl_union) {//选择银联
            changePayType(PayType.UNIONPAY);

        } else if (i == R.id.rl_yue) {//其他支付
            changePayType(PayType.OTHER);
        } else if (i == R.id.bt_pay) {//支付订单

            if (!isInsurance) {//订单支付界面
                if (onLine) {//线上支付

                    if (checkPayType()) {
                        OrderParamBean bean;
                        if (buyInsurance) {//购买保险
                            bean = creatBuyParams(true, true, true);
                        } else {//不购买保险
                            bean = creatBuyParams(true, true, false);
                        }
                        pay(bean);
                    } else {
                        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("请选择支付方式");

                    }

                } else {//线下支付
                    if (buyInsurance) {//购买保险
                        if (checkPayType()) {
                            pay(creatBuyParams(false, true, true));
                        } else {
                            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("请选择支付方式");
                        }
                    } else {//不购买保险
                        HttpOrderFactory.payOrderOffLine(creatBuyParams(false, true, false))
                                .subscribe(new NetObserver<PayBean>(this) {
                                    @Override
                                    public void doOnSuccess(PayBean data) {
                                        finish();
                                        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                                    }
                                });
                    }
                }
            } else {//保险购买界面
                if (checkPayType()) {
                    //单独购买保险
                    if (cargoPrice > 0) {
                        buyInsurance = true;
                        pay(creatBuyParams(true, false, true));
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

    //检测支付方式
    private boolean checkPayType() {
        return payType != null;
    }

    //更改支付方式
    private void changePayType(PayType payType) {
        this.payType = payType;
        cbWechat.setImageResource(payType == PayType.WECHAT ? R.mipmap.icon_xz_36 : R.mipmap.icon_wxz_36);
        cbAli.setImageResource(payType == PayType.ALI ? R.mipmap.icon_xz_36 : R.mipmap.icon_wxz_36);
        cbUnion.setImageResource(payType == PayType.UNIONPAY ? R.mipmap.icon_xz_36 : R.mipmap.icon_wxz_36);
        cbYue.setImageResource(payType == PayType.OTHER ? R.mipmap.icon_xz_36 : R.mipmap.icon_wxz_36);
    }

    private void pay(OrderParamBean bean) {
        if (payType != PayType.OTHER) {
            HttpOrderFactory.payOrderOffLine(bean)
                    .subscribe(new NetObserver<PayBean>(this) {
                        @Override
                        public void doOnSuccess(PayBean data) {
                            startWeChatPay(data);
                        }
                    });
        } else {
            ToastUtils.getInstance().show("余额支付");
        }
    }


    /**
     * 吊起微信支付
     *
     * @param data
     */
    private void startWeChatPay(PayBean data) {
        PayControl.IPayClient client = null;
//        if (payType == 0) {
//            CreatInsuranceBean creatInsuranceBean = new CreatInsuranceBean();
//            creatInsuranceBean.setGoodsValue(cargoPrice + "");
//            creatInsuranceBean.setOrderNum(orderNum);
//            Event.CraetInsurance insurance = new Event.CraetInsurance(creatInsuranceBean);
//            insurance.isCreatInsurance = isCreatInsurance;
//            BusFactory.getBus().postSticky(insurance);
//            client = new WeChatAppPay();
//        } else if (payType == 1) {
//            client = new UnionPayClient();
//        } else {
//            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("无效支付方式");
//        }
//

        payClient = new PayClient(payType);
        //此处用来区分正式和测试环境，设置debug为true的时候使用测试环境，默认为正式环境
//        payClient.setDebug(Param.isDebug);
        payClient.pay(this, data);
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
        //线上支付或者购买保险的时候使用选中的支付方式，线下支付一律为2
//        0微信支付1银联支付2线下支付3支付宝支付
        bean.setPayType(2);
        switch (payType) {
            case WECHAT:
                bean.setPayType((onLine || policy) ? 0 : 2);
                break;
            case UNIONPAY:
                bean.setPayType((onLine || policy) ? 1 : 2);
                break;
            case ALI:
                bean.setPayType((onLine || policy) ? 3 : 2);
                break;
        }
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
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        dialog.dismiss();
    }

    @Override
    public void noticeClick(BuyInsuranceDialog buyInsuranceDialog, boolean checked, int i) {
        H5Config h5Config;
        if (i == 0) {
            h5Config = new H5Config(getString(R.string.order_insruance_police), Param.insurance_polic, true);
        } else {
            h5Config = new H5Config(getString(R.string.order_insruance_notify), Param.insurance_notify, true);
        }
        ArouterUtils.getInstance().builder(ArouterParamsApp.activity_h5).withSerializable(Param.TRAN, h5Config).navigation(this);

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

    @Override
    public void onBottomClick(View view, DialogControl.IBottomDialog dialog) {
        noticeDialog.dismiss();
        buyInsuranceDialog.setReadInsurance(true);
        buyInsuranceDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            int payResult = data.getIntExtra("payResult", 0);
            String msg = "";

            switch (payResult) {
                case 1000://成功
                    msg = "支付成功！";
                    if (buyInsurance) {

                        CreatInsuranceBean creatInsuranceBean = new CreatInsuranceBean();
                        creatInsuranceBean.setGoodsValue(cargoPrice + "");
                        creatInsuranceBean.setOrderNum(orderNum);

                        ArouterUtils.getInstance().builder(ArouterParamOrder.activity_insurance_creat)
                                .withParcelable(Param.TRAN, creatInsuranceBean)
                                .navigation(this);
                    } else {
                        ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_main)
                                .navigation(this);
                    }
                    break;
                case 2000://失败
                    msg = "支付失败！";

                    break;
                case 3000://取消
                    msg = "取消支付";

                    break;

            }
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show(msg);

        }


    }
}
