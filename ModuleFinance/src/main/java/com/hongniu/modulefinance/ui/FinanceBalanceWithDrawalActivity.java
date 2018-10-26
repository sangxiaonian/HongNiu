package com.hongniu.modulefinance.ui;

import android.app.Activity;
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
import com.hongniu.modulefinance.widget.AccountDialog;
import com.hongniu.modulefinance.widget.CreatAccountDialog;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.PointLengthFilter;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.dialog.PasswordDialog;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.thirdlibrary.pay.wechat.WeChatAppPay;

import java.util.List;

/**
 * 余额提现界面
 */
@Route(path = ArouterParamsFinance.activity_finance_balance_with_drawal)
public class FinanceBalanceWithDrawalActivity extends BaseActivity implements View.OnClickListener, TextWatcher, AccountDialog.OnDialogClickListener, CreatAccountDialog.OnAddNewPayWayListener, PayPasswordKeyBord.PayKeyBordListener {

    private ImageView imgPayIcon;
    private TextView tvPayWay;
    private TextView tvPayAccount;
    private TextView tvWithDrawale;//可提现余额
    private TextView tvWithdrawaleAll;//全部提现
    private EditText etBalance;//全部提现
    private Button btSum;
    private ConstraintLayout conPay;
    private String withdrawal;//提现金额
    AccountDialog accountDialog;
    PayPasswordKeyBord passwordDialog;
    private CreatAccountDialog creatAccountDialog;

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
        passwordDialog = new PayPasswordKeyBord(this);


        creatAccountDialog = new CreatAccountDialog(mContext);
    }


    @Override
    protected void initData() {
        super.initData();
        etBalance.setFilters(new InputFilter[]{new PointLengthFilter()});
        etBalance.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        withdrawal = getIntent().getStringExtra(Param.TRAN);
        withdrawal = TextUtils.isEmpty(withdrawal) ? "0" : withdrawal;
        tvWithDrawale.setText(String.format(getString(R.string.wallet_balance_account_num), withdrawal));
        HttpAppFactory.queryMyCards()
                .subscribe(new NetObserver<List<PayInforBeans>>(this) {
                    @Override
                    public void doOnSuccess(List<PayInforBeans> data) {
                        if (!CommonUtils.isEmptyCollection(data)) {
                            PayInforBeans def = null;
                            for (PayInforBeans datum : data) {
                                if (datum.getIsDefault() == 1) {
                                    def = datum;
                                    break;
                                }
                            }
                            def = (def == null) ? data.get(0) : def;
                            initPayWay(def);

                        }
                    }
                })
        ;


    }


    private void initPayWay(PayInforBeans def) {
        if (def == null) {
            return;
        }
        if (def.getType() == 0) {//微信
            imgPayIcon.setImageResource(R.mipmap.icon_wechat_40);
            tvPayWay.setText(getString(R.string.wallet_balance_withDrawal_weiChat));
            tvPayAccount.setText(String.format(getString(R.string.account), def.getWxNickName()) == null ? "" : def.getWxNickName());
        } else if (def.getType() == 1) {//银行卡
            imgPayIcon.setImageResource(R.mipmap.icon_ylzf_40);
            tvPayWay.setText(def.getBankName() == null ? "" : def.getBankName());
            if (def.getCardNo() != null && def.getCardNo().length() > 4) {
                tvPayAccount.setText(String.format(getString(R.string.wallet_balance_withdrawal_card_num), def.getCardNo().substring(0, 4)));
            } else {
                tvPayAccount.setText(String.format(getString(R.string.wallet_balance_withdrawal_card_num), ""));
            }

        } else if (def.getType() == 3) {//支付宝
            imgPayIcon.setImageResource(R.mipmap.icon_zfb_40);
            tvPayWay.setText(getString(R.string.wallet_balance_withDrawal_ali));
            tvPayAccount.setText(String.format(getString(R.string.account), def.getWxNickName()) == null ? "" : def.getWxNickName());
        }
    }


    @Override
    protected void initListener() {
        super.initListener();
        btSum.setOnClickListener(this);
        tvWithdrawaleAll.setOnClickListener(this);
        etBalance.addTextChangedListener(this);
        conPay.setOnClickListener(this);
        accountDialog.setListener(this);
        passwordDialog.sePaytListener(this);
        passwordDialog.setProgressListener(this);
        creatAccountDialog.setListener(this);

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
            final String trim = etBalance.getText().toString().trim();
            passwordDialog.setPayCount(trim);
            passwordDialog.show();

        } else if (i == R.id.tv_withdrawal_all) {
            etBalance.setText(withdrawal);
            etBalance.setSelection(etBalance.getText().toString().length());

        } else if (i == R.id.con_pay_way) {//选择支付方式
            HttpAppFactory.queryMyCards()
                    .subscribe(new NetObserver<List<PayInforBeans>>(this) {
                        @Override
                        public void doOnSuccess(List<PayInforBeans> data) {
                            if (!CommonUtils.isEmptyCollection(data)) {
                                accountDialog.setData(data);
                                accountDialog.show();
                            }
                        }
                    })
            ;


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
    public void onChoice(DialogControl.IDialog dialog, int position, PayInforBeans bean) {
        initPayWay(bean);
        dialog.dismiss();
    }

    @Override
    public void onAddClick(DialogControl.IDialog dialog) {
        dialog.dismiss();
        creatAccountDialog.show();

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
        dialog.dismiss();
        ArouterUtils.getInstance()
                .builder(ArouterParamLogin.activity_sms_verify)
                .withInt(Param.VERTYPE, 1)
                .withString(Param.TRAN, Utils.getLoginInfor().getMobile())
                .navigation(mContext);

    }

    /**
     * 从未设置过密码
     *
     * @param dialog
     */
    @Override
    public void hasNoPassword(DialogControl.IDialog dialog) {
        HttpAppFactory.getSmsCode(Utils.getLoginInfor().getMobile())
                .subscribe(new NetObserver<String>(this) {
                    @Override
                    public void doOnSuccess(String data) {
                        ArouterUtils.getInstance()
                                .builder(ArouterParamLogin.activity_sms_verify)
                                .withInt(Param.VERTYPE, 1)
                                .withString(Param.TRAN, Utils.getLoginInfor().getMobile())
                                .navigation(mContext);
                    }
                });
    }

    @Override
    public void onAddUnipay(DialogControl.IDialog dialog) {
        dialog.dismiss();
        ArouterUtils.getInstance().builder(ArouterParamLogin.activity_login_add_blank_card).navigation((Activity) mContext, 1);
    }

    @Override
    public void onAddWechat(DialogControl.IDialog dialog) {
        dialog.dismiss();
        WeChatAppPay.jumpToXia(mContext, false);
    }

    @Override
    public void onAddAli(DialogControl.IDialog dialog) {
        dialog.dismiss();
    }
}
