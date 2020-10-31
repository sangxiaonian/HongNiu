package com.hongniu.freight.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.net.error.NetException;
import com.fy.androidlibrary.net.rx.BaseObserver;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.CommonUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.hongniu.baselibrary.entity.CommonBean;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.AccountDetailBean;
import com.hongniu.freight.entity.QueryBindHuaInforsBean;
import com.hongniu.freight.entity.QueryVeriBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.widget.dialog.RechargeInforDialog;

import io.reactivex.Observable;

/**
 * @data 2020/2/13
 * @Author PING
 * @Description 余额
 */
@Route(path = ArouterParamApp.activity_balance)
public class BalanceActivity extends CompanyBaseActivity implements View.OnClickListener, RechargeInforDialog.OnEntryClickListener {

    private TextView tv_balance;
    private TextView tv_balance_pending;
    private TextView tv_recharge;
    private TextView tv_withdraw;
    private AccountDetailBean accountInf;//提现
    RechargeInforDialog inforDialog;
    private QueryBindHuaInforsBean countInfor;//开户行信息
    private boolean isV;//是否开通华夏银行子账号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnlm_activity_balance);
        setWhitToolBar("账户余额");
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        tv_balance = findViewById(R.id.tv_balance);
        tv_balance_pending = findViewById(R.id.tv_balance_pending);
        tv_recharge = findViewById(R.id.tv_recharge);
        tv_withdraw = findViewById(R.id.tv_withdraw);
        inforDialog = new RechargeInforDialog(this);
    }

    @Override
    protected void initData() {
        super.initData();
        tv_balance.setText("0");
        tv_balance_pending.setText(String.format("你有 %s 元入账后可提现", "0"));
    }

    @Override
    protected void initListener() {
        super.initListener();
        tv_recharge.setOnClickListener(this);
        tv_withdraw.setOnClickListener(this);
        inforDialog.setClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        queryAccountDetail();
    }

    public void queryAccountDetail() {

        Observable.concat(
                HttpAppFactory.querySubAcc()//查询是否已经实名认证
                ,HttpAppFactory.queryAccountDetails()//查询开户信息
                ,HttpAppFactory.queryRechargeInfo()//查询华夏银行开户信息

        )
                .subscribe(new BaseObserver<CommonBean<? extends Object>>(this) {
                    @Override
                    public void onNext(CommonBean<?> result) {
                        super.onNext(result);
                        if (result.getCode() != 200) {
                            onError(new NetException(result.getCode(), result.getMsg()));
                        } else {
                            Object data = result.getData();
                            if (data != null) {
                                if (data instanceof QueryVeriBean) {
                                    isV = ((QueryVeriBean) data).isSubAccStatus();
                                } else if (data instanceof AccountDetailBean) {
                                    accountInf = (AccountDetailBean) data;
                                    tv_balance.setText(ConvertUtils.changeFloat(accountInf.getAvailableBalance(), 2));
                                    tv_balance_pending.setText(String.format("你有 %s 元入账后可提现", ConvertUtils.changeFloat(accountInf.getTobeCreditedBalance(), 2)));
                                } else if (data instanceof QueryBindHuaInforsBean) {
                                    countInfor = (QueryBindHuaInforsBean) data;
                                }
                            }
                        }
                    }


                });



    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {



        if (R.id.tv_recharge == v.getId()) {
            if (check()){
                inforDialog.setInfor(countInfor);
                inforDialog.show();
            }

        } else if (R.id.tv_withdraw == v.getId()) {
            if (check()){
                ArouterUtils.getInstance().builder(ArouterParamApp.activity_balance_pending)
                        .navigation(mContext);
            }
        }
    }

    private boolean check(){
        boolean result=false;
        if (isV) {
            if (countInfor==null){
                //跳转到绑卡
                ArouterUtils
                        .getInstance()
                        .builder(ArouterParamApp.activity_bind_blank_card)
                        .navigation(this,1);
            }else {
                result=true;

            }
        } else {
            // TODO 实名认证界面
            ToastUtils.getInstance().show("实名认证");
        }
        return result;
    }


    @Override
    public void onClickEntry(String msg) {
        if (countInfor != null) {
            CommonUtils.copyToPlate(mContext, countInfor.getOthBankPayeeSubAcc());
            ToastUtils.getInstance().show("复制账号成功");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK){
            queryAccountDetail();
        }
    }
}
