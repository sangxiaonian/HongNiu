package com.hongniu.freight.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamMNLM;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.config.PayType;
import com.hongniu.freight.control.PayControl;
import com.hongniu.freight.entity.AccountDetailBean;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.entity.QueryPayInfoParams;
import com.hongniu.freight.presenter.PayPresenter;
import com.hongniu.freight.widget.DialogComment;
import com.hongniu.freight.widget.PayWayView;
import com.sang.thirdlibrary.pay.PasswordDialog;
import com.sang.thirdlibrary.pay.entiy.PayBean;

/**
 * @data 2020/3/5
 * @Author PING
 * @Description 支付页面
 * 支付业务类型(1订单支付2补款运费支付3补购保险支付)
 */
@Route(path = ArouterParamMNLM.activity_pay)
public class PayActivity extends CompanyBaseActivity implements PayControl.IPayView, View.OnClickListener, PayWayView.PayWayChangeListener, PasswordDialog.OnPasswordDialogListener {

    private TextView tv_count;
    private TextView tv_count_detail;
    private TextView bu_sum;
    private PayWayView payview;
    PayControl.IPayPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnlm_activity_pay);
        String id = getIntent().getStringExtra(Param.TRAN);
        int type = getIntent().getIntExtra(Param.TYPE,1);
        setWhitToolBar("订单支付");
        presenter = new PayPresenter(this);
        presenter.saveInfo(id,type);
        initView();
        initData();
        initListener();
        presenter.queryInfo(this);
    }

    @Override
    protected void initView() {
        super.initView();
        tv_count = findViewById(R.id.tv_count);
        tv_count_detail = findViewById(R.id.tv_count_detail);
        bu_sum = findViewById(R.id.bt_sum);
        payview = findViewById(R.id.payview);
    }

    @Override
    protected void initData() {
        super.initData();

    }


    @Override
    protected void initListener() {
        super.initListener();
        bu_sum.setOnClickListener(this);
        payview.setPayWayChangeListener(this);
    }

    /**
     * 展示价格信息
     *
     * @param orderPrice  订单需要支付的价格
     * @param priceDetail 价格详情
     */
    @Override
    public void showPriceInfo(String orderPrice, String priceDetail) {
        tv_count.setText(String.format("%s", orderPrice));
        tv_count_detail.setText(priceDetail);
    }

    /**
     * @param data
     * @param orderPrice
     * @param payWay
     */
    @Override
    public void showAccountInfo(AccountDetailBean data, float orderPrice, PayType payWay) {
        payview.setAccountInfo(data, orderPrice);
        payview.setCurrentPayType(payWay);
    }

    @Override
    public void showError(String alert) {
        ToastUtils.getInstance().show(alert);
    }

    @Override
    public void switchPay(PayType type) {
        if (type==PayType.COMPANY_APPLY){
            bu_sum.setText("申请支付");
        }else {
            bu_sum.setText("支付订单");

        }
    }

    /**
     * 开始支付
     *
     * @param payWay 支付方式
     * @param payInfoBean
     */
    @Override
    public void startPay(QueryPayInfoParams payWay, PayBean payInfoBean) {

        ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_waite_pay)
                .withParcelable(Param.TRAN, payWay)
                .withParcelable(Param.TYPE, payInfoBean)
                .navigation((Activity) mContext, 1)
        ;
    }

    PasswordDialog passwordDialog;

    /**
     * 余额支付时弹出密码弹窗
     *
     * @param orderPrice
     */
    @Override
    public void showPassDialog(float orderPrice) {
        if (passwordDialog == null) {
            passwordDialog = new PasswordDialog(mContext);
            passwordDialog.setListener(this);
        }
        passwordDialog.setCount(ConvertUtils.changeFloat(orderPrice, 2));
        passwordDialog.show();
    }

    /**
     * 设置支付密码
     */
    @Override
    public void showSetPassWord() {
        new DialogComment.Builder()
                .setBtLeft("取消")
                .setBtRight("去设置")
                .setDialogTitle(" 使用余额支付前，必须设置木牛流马支付密码")
                .hideContent()
                .setRightClickListener(new DialogComment.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, Dialog dialog) {
                        dialog.dismiss();
                        ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_forget_pass)
                                .withInt(Param.TRAN, 0)
                                .navigation((Activity) mContext, 2);
                    }
                })
                .creatDialog(mContext)
                .show();
        ;


    }

    /**
     * 支付成功
     *
     * @param orderInfo
     */
    @Override
    public void jump2Succes(OrderInfoBean orderInfo) {
        ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_pay_result)
                .withParcelable(Param.TRAN, orderInfo)
                .navigation(mContext);
       finishWithSuccess();
    }

    /**
     * 支付成功
     */
    @Override
    public void finishWithSuccess() {
        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show("支付成功");
        finish();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_sum) {
            presenter.clickPay(this);
        }
    }

    /**
     * 支付方式发生改变
     *
     * @param type 0 余额支付 1企业支付 2微信支付 3支付宝 4银联
     */
    @Override
    public void onPayChange(PayType type) {
        presenter.switchPay(type);
    }

    /**
     * 取消支付
     *
     * @param dialog
     */
    @Override
    public void onCancle(Dialog dialog) {
        dialog.dismiss();
    }

    /**
     * 密码输入完成
     *
     * @param dialog
     * @param count
     * @param passWord
     */
    @Override
    public void onInputPassWordSuccess(Dialog dialog, String count, String passWord) {
        dialog.dismiss();
        presenter.balancePay(passWord, this);
    }

    /**
     * 忘记密码
     *
     * @param dialog
     */
    @Override
    public void onForgetPassowrd(Dialog dialog) {
        dialog.dismiss();
        ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_forget_pass)
                .withInt(Param.TRAN, 1)
                .navigation(this, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            presenter.paySuccess();

        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            presenter.queryInfo(this);
        }
    }
}
