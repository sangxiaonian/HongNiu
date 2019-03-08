package com.hongniu.modulefinance.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.WalletDetail;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.entity.QueryBindHuaInforsBean;
import com.hongniu.modulefinance.entity.QuerySubAccStateBean;
import com.hongniu.modulefinance.net.HttpFinanceFactory;
import com.hongniu.modulefinance.widget.RechargeInforDialog;
import com.sang.common.net.error.NetException;
import com.sang.common.net.rx.BaseObserver;
import com.sang.common.utils.ToastUtils;
import com.sang.common.utils.errorcrushhelper.CrashHelper;

import io.reactivex.Observable;

/**
 * 余额详情
 */
@Route(path = ArouterParamsFinance.activity_finance_balance)
public class FinanceBalanceActivity extends BaseActivity implements View.OnClickListener, RechargeInforDialog.OnEntryClickListener {

    private TextView tvBanlaceWithdrawal;//待入账提现
    private TextView tvBanlace;//账户余额
    private Button btSum;//提现
    private Button btRecharge;//充值
    WalletDetail walletDetail;
    private boolean isVir;//是否实名认证
    private QueryBindHuaInforsBean countInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_balance);
        setToolbarTitle("账户余额");
        initView();
        initData();
        initListener();
        queryCountInfor();
    }

    @Override
    protected void initView() {
        super.initView();
        tvBanlace = findViewById(R.id.tv_balance);
        btRecharge = findViewById(R.id.bt_recharge);
        tvBanlaceWithdrawal = findViewById(R.id.tv_balance_withdrawal);
        btSum = findViewById(R.id.bt_sum);
    }

    @Override
    protected void initData() {
        super.initData();
        walletDetail = getIntent().getParcelableExtra(Param.TRAN);
        tvBanlace.setText(String.format("%1$s", walletDetail == null ? "0" : walletDetail.getAvailableBalance()));
        tvBanlaceWithdrawal.setText(String.format(getString(R.string.wallet_balance_wait_entry), walletDetail == null ? "0" : walletDetail.getTobeCreditedBalance()));
    }

    @Override
    protected void initListener() {
        super.initListener();
        btSum.setOnClickListener(this);
        btRecharge.setOnClickListener(this);

    }

    private void queryCountInfor() {
        Observable.concat(HttpFinanceFactory.queryHuaCards()
                , HttpFinanceFactory.querySubAcc())
                .subscribe(new BaseObserver<CommonBean<? extends Object>>(this) {
                    @Override
                    public void onNext(CommonBean<?> result) {
                        super.onNext(result);
                        if (result.getCode() != 200) {
                            onError(new NetException(result.getCode(), result.getMsg()));
                        } else {
                            Object data = result.getData();
                            if (data != null) {
                                if (data instanceof QuerySubAccStateBean) {
                                    isVir = ((QuerySubAccStateBean) data).subAccStatus;
                                } else if (data instanceof QueryBindHuaInforsBean) {
                                    countInfor = (QueryBindHuaInforsBean) data;
                                }
                            }
                        }
                    }


                });
    }


    @Override
    protected void showAleart(String msg) {
        ToastUtils.getInstance().show(msg);
    }

    @Override
    public void onClick(View v) {

        //实名认证并且绑定银行卡情况下，可以充值和体现
        if (countInfor != null && isVir) {
            if (v.getId() == R.id.bt_sum) {
                ArouterUtils
                        .getInstance()
                        .builder(ArouterParamsFinance.activity_finance_balance_with_drawal)
                        .withString(Param.TRAN, walletDetail == null ? "0" : walletDetail.getAvailableBalance())
                        .navigation(this, 1);
            } else {
                RechargeInforDialog inforDialog = new RechargeInforDialog(mContext);
                inforDialog.setClickListener(this);
                inforDialog.show();
            }
        } else {
//            if (!isVir) {
//                //如果没有实名认证
//                ArouterUtils
//                        .getInstance()
//                        .builder(ArouterParamLogin.activity_person_infor)
//                        .navigation(this);
//            } else
                if (countInfor == null) {
                //如果没有绑定银行卡
                ArouterUtils
                        .getInstance()
                        .builder(ArouterParamLogin.activity_login_bind_blank_card)
                        .navigation(this);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClickEntry(String msg) {
        Utils.copyToPlate(mContext, msg);
    }
}
