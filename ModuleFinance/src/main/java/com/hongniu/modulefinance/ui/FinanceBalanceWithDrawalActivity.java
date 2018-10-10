package com.hongniu.modulefinance.ui;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.entity.AccountInforBean;
import com.hongniu.modulefinance.widget.AccountDialog;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.dialog.PasswordDialog;
import com.sang.common.widget.dialog.inter.DialogControl;

import java.util.ArrayList;
import java.util.List;

/**
 * 余额提现界面
 */
@Route(path = ArouterParamsFinance.activity_finance_balance_with_drawal)
public class FinanceBalanceWithDrawalActivity extends BaseActivity implements View.OnClickListener, TextWatcher, AccountDialog.OnDialogClickListener, PasswordDialog.OnPasswordDialogListener {

    private ImageView imgPayIcon;
    private TextView tvPayWay;
    private TextView tvPayAccount;
    private TextView tvWithDrawale;//可提现余额
    private TextView tvWithdrawaleAll;//全部提现
    private EditText etBalance;//全部提现
    private Button btSum;
    private ConstraintLayout conPay;
    private float withdrawal;//提现金额
    AccountDialog accountDialog;
    PasswordDialog passwordDialog;

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
        conPay = findViewById(R.id.con_pay_way);
        accountDialog = new AccountDialog(this);
        passwordDialog = new PasswordDialog(this);
    }


    @Override
    protected void initData() {
        super.initData();
        withdrawal = 1500.25f;
        imgPayIcon.setImageResource(R.mipmap.icon_ylzf_40);
        tvPayWay.setText("中国工商银行");
        tvPayAccount.setText("尾号 4889 储蓄卡");
        tvWithDrawale.setText(String.format(getString(R.string.wallet_balance_account_num), withdrawal + ""));
    }

    @Override
    protected void initListener() {
        super.initListener();
        btSum.setOnClickListener(this);
        tvWithdrawaleAll.setOnClickListener(this);
        etBalance.addTextChangedListener(this);
        conPay.setOnClickListener(this);
        accountDialog.setListener(this);
        passwordDialog.setListener(this);
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
            passwordDialog.setCount(withdrawal + "");
            passwordDialog.show();
        } else if (i == R.id.tv_withdrawal_all) {
            etBalance.setText(withdrawal + "");
            etBalance.setSelection(etBalance.getText().toString().length());

        } else if (i == R.id.con_pay_way) {//选择支付方式
            List<AccountInforBean> datas = new ArrayList<>();
            datas.add(new AccountInforBean());
            datas.add(new AccountInforBean());
            datas.add(new AccountInforBean());
            datas.add(new AccountInforBean());
            accountDialog.setData(datas);
            accountDialog.show();

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

    @Override
    public void onChoice(DialogControl.IDialog dialog, int position, AccountInforBean bean) {
        dialog.dismiss();
    }

    @Override
    public void onAddClick(DialogControl.IDialog dialog) {
        ArouterUtils.getInstance().builder(ArouterParamLogin.activity_pay_ways).navigation(this);
    }

    /**
     * 取消支付
     *
     * @param dialog
     */
    @Override
    public void onCancle(DialogControl.IDialog dialog) {
        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("取消支付");
        dialog.dismiss();
    }

    /**
     * 密码输入完成
     *
     * @param dialog
     * @param passWord
     */
    @Override
    public void onInputPassWordSuccess(DialogControl.IDialog dialog, String passWord) {
        dialog.dismiss();
        ToastUtils.getInstance().show("提现成功");

    }

    /**
     * 忘记密码
     *
     * @param dialog
     */
    @Override
    public void onForgetPassowrd(DialogControl.IDialog dialog) {
        ArouterUtils.getInstance()
                .builder(ArouterParamLogin.activity_sms_verify)
                .withInt(Param.VERTYPE,1)
                .withString(Param.TRAN, Utils.getLoginInfor().getMobile())
                .navigation(mContext);
//        HttpAppFactory.getSmsCode(Utils.getLoginInfor().getMobile())
//                .subscribe(new NetObserver<String>(this) {
//                    @Override
//                    public void doOnSuccess(String data) {
//                        ArouterUtils.getInstance()
//                                .builder(ArouterParamLogin.activity_sms_verify)
//                                .withInt(Param.VERTYPE,1)
//                                .withString(Param.TRAN, Utils.getLoginInfor().getMobile())
//                                .navigation(mContext);
//                    }
//                });

    }
}
