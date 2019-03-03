package com.hongniu.modulefinance.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulefinance.R;
import com.hongniu.baselibrary.entity.WalletDetail;
import com.hongniu.modulefinance.widget.RechargeInforDialog;

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
    private boolean canRecharge;//是否可以充值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_balance);
        setToolbarTitle("账户余额");
        initView();
        initData();
        initListener();
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


    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.bt_sum) {
            ArouterUtils
                    .getInstance()
                    .builder(ArouterParamsFinance.activity_finance_balance_with_drawal)
                    .withString(Param.TRAN, walletDetail == null ? "0" : walletDetail.getAvailableBalance())
                    .navigation(this,1);
        }else {
            if (!canRecharge) {
                ArouterUtils
                        .getInstance()
                        .builder(ArouterParamLogin.activity_login_bind_blank_card)
                        .navigation(this);
                canRecharge=true;
            }else {
                RechargeInforDialog inforDialog=new RechargeInforDialog(mContext);
                inforDialog.setClickListener(this);
                inforDialog.show();
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
        Utils.copyToPlate(mContext,msg);
    }
}
