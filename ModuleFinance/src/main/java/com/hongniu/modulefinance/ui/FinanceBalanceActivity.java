package com.hongniu.modulefinance.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.modulefinance.R;
import com.hongniu.baselibrary.entity.WalletDetail;

/**
 * 余额详情
 */
@Route(path = ArouterParamsFinance.activity_finance_balance)
public class FinanceBalanceActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvBanlaceWithdrawal;//待入账提现
    private TextView tvBanlace;//账户余额
    private Button btSum;//提现
    WalletDetail walletDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_balance);
        setToolbarTitle(getString(R.string.wallet_title));
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        tvBanlace = findViewById(R.id.tv_balance);
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
    }


    @Override
    public void onClick(View v) {

        ArouterUtils
                .getInstance()
                .builder(ArouterParamsFinance.activity_finance_balance_with_drawal)
                .withString(Param.TRAN, walletDetail == null ? "0" : walletDetail.getAvailableBalance())
                .navigation(this,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
