package com.hongniu.modulefinance.ui;

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
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.control.FinanceWalletControl;
import com.hongniu.modulefinance.present.WalletPresenter;
import com.sang.common.utils.ToastUtils;

/**
 * 我的钱包首页
 */
@Route(path = ArouterParamsFinance.activity_finance_wallet)
public class FinanceWalletActivity extends BaseActivity implements FinanceWalletControl.IWalletView, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private FinanceWalletControl.IWalletPresent present;
    private TextView tvBalanceOfAccount;//账户余额
    private TextView tvBalanceOfUnentry;//待入账余额
    private ConstraintLayout conBalance;//余额部分按钮
    private LinearLayout llNiu;//牛币条目
    private TextView tvNiuBalanceOfAccount;//牛币余额
    private TextView tvNiuBalanceOfUnentry;//牛币待入账余额
    private RadioGroup rg;//牛币待入账余额
    private RadioButton rbLeft;//余额明细
    private RadioButton rbRight;//待入账明细

     private Fragment blankFrangmet;//余额明细
     private Fragment currentFrament;//余额明细
     private Fragment unEntryFrangmet;//待入账明细
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_wallet);
        setToolbarDarkTitle("我的钱包");
        setToolbarSrcRight("财务");
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
        llNiu = findViewById(R.id.ll_niu);//牛币条目
        tvNiuBalanceOfAccount = findViewById(R.id.tv_niu_balance_of_count);//牛币余额
        tvNiuBalanceOfUnentry = findViewById(R.id.tv_niu_balance_of_unentry);//牛币待入账余额
        rg = findViewById(R.id.rg);
        rbRight = findViewById(R.id.rb_right);
        rbLeft = findViewById(R.id.rb_left);
    }

    @Override
    protected void initData() {
        super.initData();
        tvBalanceOfAccount.setText(getString(R.string.money_symbol) + "1800.00");
        tvBalanceOfUnentry.setText("( 您有" + 300 + "元待入账金额 )");
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("您有");
        final int start = builder.length();
        builder.append("123");
        final int end = builder.length();
        builder.append("个牛币");
        ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.color_light));
        builder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvNiuBalanceOfAccount.setText(builder);
        tvNiuBalanceOfUnentry.setText(30 + "牛币待入账");




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
        llNiu.setOnClickListener(this);
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
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("账户余额");


        } else if (i == R.id.ll_niu) {


            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("牛币账户");
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
        if (currentFrament!=null){
            fragmentTransaction.hide(currentFrament);
        }
        if (checkedId == rbLeft.getId()) {
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("账户余额");
            if (blankFrangmet==null) {
                blankFrangmet = (Fragment) ArouterUtils.getInstance().builder(ArouterParamsFinance.fragment_finance_wallet).navigation(this);
                fragmentTransaction.add(R.id.content,blankFrangmet);
            }else {
                fragmentTransaction.show(blankFrangmet);
            }

            currentFrament=blankFrangmet;
        } else   {
            if (unEntryFrangmet==null){
                unEntryFrangmet= (Fragment) ArouterUtils.getInstance().builder(ArouterParamsFinance.fragment_finance_wallet).navigation(this);
                fragmentTransaction.add(R.id.content,unEntryFrangmet);
            }else {
                fragmentTransaction.show(unEntryFrangmet);
            }
            currentFrament=unEntryFrangmet;

            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("牛币账户");
        }
        fragmentTransaction.commitAllowingStateLoss();


    }
}
