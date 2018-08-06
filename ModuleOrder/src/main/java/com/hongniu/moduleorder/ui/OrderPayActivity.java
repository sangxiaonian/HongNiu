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
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.widget.dialog.BuyInsuranceDialog;
import com.hongniu.moduleorder.widget.dialog.InsuranceNoticeDialog;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.dialog.inter.DialogControl;

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
    private CheckBox checkbox;//选择是否微信支付
    private Button btPay;//支付订单
    private TextView tvPayAll;//支付总额

    private ViewGroup conInsurance;//保险条目
    private TextView tv_insurance_price;//保险金额
    private TextView tv_cargo_price;//货物金额
    private TextView bt_cancle_insurance;//取消保险
    private TextView tv_change_cargo_price;//更改货物金额


    private boolean onLine = true;
    private boolean buyInsurance;//是否购买保险
    private float insurancePrice;//保费
    private float cargoPrice;//货物金额

    //TODO 此时为默认显示的运费，正式环境发布前需要置为 0
    private float tranPrice = 100;//运费


    private BuyInsuranceDialog buyInsuranceDialog;
    private InsuranceNoticeDialog noticeDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay);
        setToolbarDarkTitle(getString(R.string.order_pay));
        initView();
        initData();
        initListener();
        rbOnline.performClick();
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


        buyInsuranceDialog = new BuyInsuranceDialog(mContext);

        noticeDialog = new InsuranceNoticeDialog(mContext);
    }

    @Override
    protected void initData() {
        super.initData();
        switchToBuyInsurance(true);
        tvOrder.setText("订单号D888888888");
        tvPrice.setText("￥" + tranPrice);
        setTvPayAll();
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
        noticeDialog.setOnBottomClickListener(this);
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
            buyInsuranceDialog.show();

        } else if (i == R.id.rl_wechact) {//选择微信支付
            checkbox.setChecked(!checkbox.isChecked());
        } else if (i == R.id.bt_pay) {//支付订单

            if (onLine || buyInsurance) {//线上支付或者购买保险时候，需要使用微信付款
                if (checkbox.isChecked()) {
                    //TODO 此时调用支付，支付完成之后方可跳转到生成保单界面，调试情况下，直接跳转
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("支付订单");
                    ArouterUtils.getInstance().builder(ArouterParamOrder.activity_insurance_creat).navigation(mContext);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("请选择支付方式");

                }
            } else {//直接完成订单
                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("完成订单");
                ArouterUtils.getInstance().builder(ArouterParamOrder.activity_insurance_creat).navigation(mContext);
            }

        } else if (i == R.id.bt_cancle_insurance) {//取消保险
            switchToBuyInsurance(true);
        } else if (i == R.id.tv_change_cargo_price) {//修改保险金额
            buyInsuranceDialog.show();
        }
    }

    @Override
    public void entryClick(Dialog dialog, boolean checked, String price, String cargoPrice) {
        try {
            this.cargoPrice = Float.parseFloat(cargoPrice);
            this.insurancePrice = Float.parseFloat(price);
            switchToBuyInsurance(false);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        dialog.dismiss();
    }

    @Override
    public void noticeClick(BuyInsuranceDialog buyInsuranceDialog, boolean checked, String price) {
        buyInsuranceDialog.dismiss();
        noticeDialog.show();
    }

    @Override
    public void onBottomClick(View view, DialogControl.IBottomDialog dialog) {
        noticeDialog.dismiss();
        buyInsuranceDialog.setReadInsurance(true);
        buyInsuranceDialog.show();
    }
}
