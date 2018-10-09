package com.hongniu.modulefinance.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.modulefinance.R;
import com.sang.common.utils.ToastUtils;

/**
 * 余额提现界面
 */
@Route(path = ArouterParamsFinance.activity_finance_balance_with_drawal)
public class FinanceBalanceWithDrawalActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private ImageView imgPayIcon;
    private TextView tvPayWay;
    private TextView tvPayAccount;
    private TextView tvWithDrawale;//可提现余额
    private TextView tvWithdrawaleAll;//全部提现
    private EditText etBalance;//全部提现
    private Button btSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_balance_with_drawal);
        setToolbarTitle(getString(R.string.wallet_balance_withdrawal_title));
        initView();
        initData();
        initListener();

    }

    @Override
    protected void initView() {
        super.initView();
        imgPayIcon = findViewById(R.id.img);
        tvPayWay = findViewById(R.id.tv_pay_way);
        tvPayAccount = findViewById(R.id.tv_pay_account);
        tvWithDrawale = findViewById(R.id.tv_balance);
        tvWithdrawaleAll = findViewById(R.id.tv_withdrawal_all);
        etBalance = findViewById(R.id.et_balance);
        btSum = findViewById(R.id.bt_sum);
    }


    @Override
    protected void initData() {
        super.initData();
        imgPayIcon.setImageResource(R.mipmap.icon_ylzf_40);
        tvPayWay.setText("中国工商银行");
        tvPayAccount.setText("尾号 4889 储蓄卡");
        tvWithDrawale.setText(String.format(getString(R.string.wallet_balance_account_num), "1200"));
    }

    @Override
    protected void initListener() {
        super.initListener();
        btSum.setOnClickListener(this);
        tvWithdrawaleAll.setOnClickListener(this);
        etBalance.addTextChangedListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bt_sum) {
            ToastUtils.getInstance().show("立即提现");

        } else if (i == R.id.tv_withdrawal_all) {
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("全部提现");
            etBalance.setText("1200");
            etBalance.setSelection(etBalance.getText().toString().length());

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        btSum.setEnabled(!TextUtils.isEmpty(etBalance.getText().toString().trim()));
    }
}
