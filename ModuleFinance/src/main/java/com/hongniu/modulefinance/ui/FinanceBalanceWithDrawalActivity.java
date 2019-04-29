package com.hongniu.modulefinance.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.PayInforBeans;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.baselibrary.widget.PayPasswordKeyBord;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.net.HttpFinanceFactory;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.PointLengthFilter;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.dialog.BottomAlertDialog;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.BottomAlertBuilder;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;

import java.util.List;

/**
 * 余额提现界面
 */
@Route(path = ArouterParamsFinance.activity_finance_balance_with_drawal)
public class FinanceBalanceWithDrawalActivity extends BaseActivity implements View.OnClickListener, TextWatcher, PayPasswordKeyBord.PayKeyBordListener {

    private ImageView imgPayIcon;
    private TextView tvPayWay;
    private TextView tvPayAccount;
    private TextView tvWithDrawale;//可提现余额
    private TextView tvWithdrawaleAll;//全部提现
    private EditText etBalance;//全部提现
    private Button btSum;
    private ConstraintLayout conPay;
    private String withdrawal;//提现金额
    PayPasswordKeyBord passwordDialog;
    private String blankNumber;
    private String bankID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_balance_with_drawal);
        blankNumber = getIntent().getStringExtra(Param.TYPE);
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
        passwordDialog = new PayPasswordKeyBord(this);
        passwordDialog.setPayDes("提现金额");

    }


    @Override
    protected void initData() {
        super.initData();
        etBalance.setFilters(new InputFilter[]{new PointLengthFilter()});
        etBalance.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        withdrawal = getIntent().getStringExtra(Param.TRAN);
        withdrawal = TextUtils.isEmpty(withdrawal) ? "0" : withdrawal;
        tvWithDrawale.setText(String.format(getString(R.string.wallet_balance_account_num), withdrawal));

        initPayWay(blankNumber);
    }


    private void initPayWay(String blankNumber) {
        if (blankNumber == null) {
            return;
        }

        tvPayAccount.setVisibility(View.VISIBLE);
        imgPayIcon.setImageResource(R.mipmap.icon_ylzf_40);
        tvPayWay.setText("银联提现");
        tvPayAccount.setText(blankNumber);
    }


    @Override
    protected void initListener() {
        super.initListener();
        btSum.setOnClickListener(this);
        tvWithdrawaleAll.setOnClickListener(this);
        etBalance.addTextChangedListener(this);
        conPay.setOnClickListener(this);
        passwordDialog.sePaytListener(this);
        passwordDialog.setProgressListener(this);

        HttpAppFactory.queryMyCards()
                .subscribe(new NetObserver<List<PayInforBeans>>(this) {
                    @Override
                    public void doOnSuccess(List<PayInforBeans> data) {
                        if (!CommonUtils.isEmptyCollection(data)) {
                            bankID = data.get(0).getId();
                            tvPayAccount.setText(data.get(0).getCardNo() + "");
                        }
                    }
                })
        ;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected boolean getUseEventBus() {
        return true;
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
            if (blankNumber != null) {
                if (Utils.querySetPassword()) {
                    final String trim = etBalance.getText().toString().trim();
                    passwordDialog.setPayCount(trim);
                    passwordDialog.show();
                } else {
                    hasNoPassword(null);
                }
            } else {
                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("请选择提现方式");
            }

        } else if (i == R.id.tv_withdrawal_all) {
            etBalance.setText(withdrawal);
            etBalance.setSelection(etBalance.getText().toString().length());

        } else if (i == R.id.con_pay_way) {//选择支付方式
            new BottomAlertBuilder()
                    .setDialogTitle("确认要解绑银行卡吗？")
                    .setTopClickListener(new DialogControl.OnButtonTopClickListener() {
                        @Override
                        public void onTopClick(View view, DialogControl.IBottomDialog dialog) {
                            dialog.dismiss();
                            deletedCard();
                        }

                    })
                    .setBottomClickListener(new DialogControl.OnButtonBottomClickListener() {
                        @Override
                        public void onBottomClick(View view, DialogControl.IBottomDialog dialog) {
                            dialog.dismiss();
                        }

                    })
                    .creatDialog(new BottomAlertDialog(mContext)).show();

        }
    }

    private void deletedCard() {
        HttpFinanceFactory.deleadCard(bankID).subscribe(new NetObserver<Object>(this) {
            @Override
            public void doOnSuccess(Object data) {
                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show("解绑成功");
                ArouterUtils.getInstance().builder(ArouterParamsApp.activity_main).navigation(mContext);
            }
        });
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
     * @param count
     * @param passWord
     */
    @Override
    public void onInputPassWordSuccess(DialogControl.IDialog dialog, String count, String
            passWord) {
        dialog.dismiss();
        //ID应该是服务器返回，不过此时由于只有银行卡，因此写死
        HttpFinanceFactory.withdraw(count, passWord, bankID)
                .subscribe(new NetObserver<String>(this) {
                    @Override
                    public void doOnSuccess(String data) {
                        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });

    }

    /**
     * 忘记密码
     *
     * @param dialog
     */
    @Override
    public void onForgetPassowrd(DialogControl.IDialog dialog) {
        dialog.dismiss();
        ArouterUtils.getInstance()
                .builder(ArouterParamLogin.activity_login_forget_pass)
                .navigation(mContext);

    }

    /**
     * 从未设置过密码
     *
     * @param dialog
     */
    @Override
    public void hasNoPassword(DialogControl.IDialog dialog) {

        creatDialog("使用余额支付前，必须设置泓牛支付密码", null, "取消", "去设置")
                .setLeftClickListener(new DialogControl.OnButtonLeftClickListener() {
                    @Override
                    public void onLeftClick(View view, DialogControl.ICenterDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        dialog.dismiss();
                        ArouterUtils.getInstance()
                                .builder(ArouterParamLogin.activity_login_forget_pass)
                                .withInt(Param.TRAN, 1)
                                .navigation(mContext);
                    }
                })
                .creatDialog(new CenterAlertDialog(mContext))
                .show();


    }

    private CenterAlertBuilder creatDialog(String title, String content, String btleft, String btRight) {
        return Utils.creatDialog(mContext, title, content, btleft, btRight);
    }


}
