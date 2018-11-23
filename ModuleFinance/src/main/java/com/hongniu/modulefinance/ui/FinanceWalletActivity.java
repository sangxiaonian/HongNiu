package com.hongniu.modulefinance.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.WalletDetail;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.control.FinanceWalletControl;
import com.hongniu.modulefinance.net.HttpFinanceFactory;
import com.hongniu.modulefinance.present.WalletPresenter;

/**
 * 我的钱包首页
 */
@Route(path = ArouterParamsFinance.activity_finance_wallet)
public class FinanceWalletActivity extends BaseActivity implements FinanceWalletControl.IWalletView, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private FinanceWalletControl.IWalletPresent present;
    private TextView tvBalanceOfAccount;//账户余额
    private TextView tvBalanceOfUnentry;//待入账余额
    private ConstraintLayout conBalance;//余额部分按钮

    private RadioGroup rg;//牛贝待入账余额
    private RadioButton rbLeft;//余额明细
    private RadioButton rbRight;//待入账明细

    private Fragment blankFrangmet;//余额明细
    private Fragment currentFrament;//余额明细
    private Fragment unEntryFrangmet;//待入账明细

    private WalletDetail walletDetail;//账户数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_wallet);
        setToolbarRedTitle(getString(R.string.wallet_title));
        setToolbarSrcRight(getString(R.string.finance));
        present = new WalletPresenter(this);
        initView();
        initData();
        initListener();
        rbLeft.performClick();

    }

    @Override
    protected void initView() {
        super.initView();
        tvBalanceOfAccount = findViewById(R.id.tv_balance_of_account);//账户余额
        tvBalanceOfUnentry = findViewById(R.id.tv_balance_of_unentry);//待入账余额
        conBalance = findViewById(R.id.con_balance);//余额部分按钮

        rg = findViewById(R.id.rg);
        rbRight = findViewById(R.id.rb_right);
        rbLeft = findViewById(R.id.rb_left);
    }

    @Override
    protected void initData() {
        super.initData();
        tvBalanceOfAccount.setText(String.format(getString(R.string.money_symbol_des), "0"));
        tvBalanceOfUnentry.setText(String.format(getString(R.string.wallet_balance_wait_enty), "0"));

    }

    @Override
    protected void onStart() {
        super.onStart();
        upInfor();
    }

    private void upInfor(){
        HttpFinanceFactory.queryAccountdetails()
                .subscribe(new NetObserver<WalletDetail>(this) {
                    @Override
                    public void doOnSuccess(WalletDetail data) {
                        Utils.setPassword(data.isSetPassWord());
                        walletDetail=data;
                        tvBalanceOfAccount.setText(String.format(getString(R.string.money_symbol_des), data.getAvailableBalance()));
                        tvBalanceOfUnentry.setText(String.format(getString(R.string.wallet_balance_wait_enty), data.getTobeCreditedBalance()));

                    }
                });
    }



    @Override
    protected void initListener() {
        super.initListener();
        setToolbarRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArouterUtils.getInstance().builder(ArouterParamsFinance.activity_finance_activity).navigation(mContext);
            }
        });

        conBalance.setOnClickListener(this);
        rg.setOnCheckedChangeListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.con_balance) {
            ArouterUtils.getInstance()
                    .builder(ArouterParamsFinance.activity_finance_balance)
                    .withParcelable(Param.TRAN,walletDetail)
                    .navigation(this);
        }
    }

    /**
     * <p>Called when the checked radio button has changed. When the
     * selection is cleared, checkedId is -1.</p>
     *
     * @param group     the group in which the checked radio button has changed
     * @param checkedId the unique identifier of the newly checked radio button
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (currentFrament != null) {
            fragmentTransaction.hide(currentFrament);
        }
        if (checkedId == rbLeft.getId()) {
            if (blankFrangmet == null) {
                blankFrangmet = (Fragment) ArouterUtils.getInstance().builder(ArouterParamsFinance.fragment_finance_wallet).navigation(this);
                Bundle bundle = new Bundle();
                bundle.putInt(Param.TRAN, 1);
                blankFrangmet.setArguments(bundle);
                fragmentTransaction.add(R.id.content, blankFrangmet);
            } else {
                fragmentTransaction.show(blankFrangmet);
            }

            currentFrament = blankFrangmet;
        } else {
            if (unEntryFrangmet == null) {
                unEntryFrangmet = (Fragment) ArouterUtils.getInstance().builder(ArouterParamsFinance.fragment_finance_wallet).navigation(this);
                Bundle bundle = new Bundle();
                bundle.putInt(Param.TRAN, 2);
                unEntryFrangmet.setArguments(bundle);
                fragmentTransaction.add(R.id.content, unEntryFrangmet);
            } else {
                fragmentTransaction.show(unEntryFrangmet);
            }
            currentFrament = unEntryFrangmet;

        }
        fragmentTransaction.commitAllowingStateLoss();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            upInfor();
        }

    }
}
