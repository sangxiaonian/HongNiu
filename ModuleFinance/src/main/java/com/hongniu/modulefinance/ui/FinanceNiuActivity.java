package com.hongniu.modulefinance.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulefinance.R;
import com.hongniu.baselibrary.entity.WalletDetail;
import com.hongniu.modulefinance.net.HttpFinanceFactory;

/**
 * 牛贝账户
 */
@Route(path = ArouterParamsFinance.activity_finance_niu)
public class FinanceNiuActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private TextView tvNiu;
    private TextView tvNiuUnEntry;
    private RadioGroup rg;
    private RadioButton rbLeft;
    private RadioButton rbRight;

    private Fragment blankFrangmet;//已入账
    private Fragment currentFrament;
    private Fragment unEntryFrangmet;//待入账明细
    private WalletDetail walletHomeDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_niu);
        setToolbarTitle(getString(R.string.wallet_niu_title));
        initView();
        initData();
        initListener();
        rbLeft.performClick();
    }

    @Override
    protected void initView() {
        super.initView();
        rg = findViewById(R.id.rg);
        rbRight = findViewById(R.id.rb_right);
        rbLeft = findViewById(R.id.rb_left);
        tvNiu = findViewById(R.id.tv_niu_count);
        tvNiuUnEntry = findViewById(R.id.tv_niu_of_unentry);
    }

    @Override
    protected void initData() {
        super.initData();
        walletHomeDetail = getIntent().getParcelableExtra(Param.TRAN);

        if (walletHomeDetail==null){
            HttpFinanceFactory.queryAccountdetails()
                    .subscribe(new NetObserver<WalletDetail>(this) {
                        @Override
                        public void doOnSuccess(WalletDetail data) {
                            Utils.setPassword(data.isSetPassWord());
                            walletHomeDetail=data;
                            tvNiu.setText(String.format(getResources().getString(R.string.wallet_niu_of_account), walletHomeDetail == null ? "0" : walletHomeDetail.getAvailableIntegral()));
                            tvNiuUnEntry.setText(String.format(getResources().getString(R.string.wallet_niu_unentry_count), walletHomeDetail == null ? "0" : walletHomeDetail.getTobeCreditedIntegral()));

                        }
                    });
        }else {
            tvNiu.setText(String.format(getResources().getString(R.string.wallet_niu_of_account), walletHomeDetail == null ? "0" : walletHomeDetail.getAvailableIntegral()));
            tvNiuUnEntry.setText(String.format(getResources().getString(R.string.wallet_niu_unentry_count), walletHomeDetail == null ? "0" : walletHomeDetail.getTobeCreditedIntegral()));

        }

    }

    @Override
    protected void initListener() {
        super.initListener();
        rg.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (currentFrament != null) {
            fragmentTransaction.hide(currentFrament);
        }
        if (checkedId == rbLeft.getId()) {
            if (blankFrangmet == null) {
                blankFrangmet = (Fragment) ArouterUtils.getInstance().builder(ArouterParamsFinance.fragment_finance_niu).navigation(this);
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
                unEntryFrangmet = (Fragment) ArouterUtils.getInstance().builder(ArouterParamsFinance.fragment_finance_niu).navigation(this);
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
}
