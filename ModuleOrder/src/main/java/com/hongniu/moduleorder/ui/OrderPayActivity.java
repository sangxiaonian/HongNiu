package com.hongniu.moduleorder.ui;

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
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.moduleorder.R;
import com.sang.common.utils.ToastUtils;

/**
 * 订单支付界面
 */
@Route(path = ArouterParamOrder.activity_order_pay)
public class OrderPayActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

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

    private boolean onLine = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay);
        setToolbarDarkTitle("支付订单");
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
    }

    @Override
    protected void initData() {
        super.initData();
        tvOrder.setText("订单号D888888888");
        tvPrice.setText("￥1200");
        tvPayAll.setText("￥1200元");
    }

    @Override
    protected void initListener() {
        super.initListener();
        btBuy.setOnClickListener(this);
        btPay.setOnClickListener(this);
        rlWechact.setOnClickListener(this);
        rg.setOnCheckedChangeListener(this);

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_online) {//线上支付
            onLine = true;

            tvPayAll.setText("￥1200元");
            rlWechact.setVisibility(View.VISIBLE);
        } else if (checkedId == R.id.rb_offline) {//线下支付
            onLine = false;
            rlWechact.setVisibility(View.GONE);
            tvPayAll.setText("￥0元");
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
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("购买保险");
        } else if (i == R.id.rl_wechact) {//选择微信支付
            checkbox.setChecked(!checkbox.isChecked());
        }else if (i==R.id.bt_pay){//支付订单
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("支付订单");
        }
    }
}
