package com.hongniu.freight.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.net.rx.BaseObserver;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.widget.autoline.AutoLineLayout;
import com.fy.androidlibrary.widget.autoline.helper.AutoLayoutHelper;
import com.fy.androidlibrary.widget.autoline.helper.AutoTagAdapter;
import com.fy.androidlibrary.widget.autoline.inter.AutoSingleSelectListener;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.AccountDetailBean;
import com.hongniu.freight.entity.CoffersInfoBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.ui.adapter.AutoTagSingleAdapter;

import org.apache.http.params.HttpParams;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @data 2020/2/12
 * @Author PING
 * @Description 我的金库
 */
@Route(path = ArouterParamApp.activity_my_coffers)
public class MyCoffersActivity extends CompanyBaseActivity implements AutoSingleSelectListener<String>, View.OnClickListener {
    AutoLineLayout autoLineLayout;//标签
    TextView tv_balance_of_account;//余额
    TextView tv_balance_of_unentry;//待入账金额
    TextView tv_month_bill;//月账单
    private AutoTagAdapter<String> tagAdapter;

    private CompanyBaseFragment currentFragment, balanceFragment, waiteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coffers);
        setWhitToolBar("我的金库");
        initView();
        initData();
        initListener();
        switchFragment(0);
        queryInfo();
    }

    private void queryInfo() {
        HttpAppFactory.queryAccountDetails()
                .subscribe(new NetObserver<AccountDetailBean>(null){
                    @Override
                    public void doOnSuccess(AccountDetailBean accountDetailBean) {
                        super.doOnSuccess(accountDetailBean);

                        tv_balance_of_account.setText(ConvertUtils.changeFloat(accountDetailBean.getAvailableBalance(),2));
                        tv_balance_of_unentry.setText(ConvertUtils.changeFloat(accountDetailBean.getTobeCreditedBalance(),2)+"元");
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        queryInfo();
    }

    @Override
    protected void initView() {
        super.initView();

        autoLineLayout = findViewById(R.id.auto_layout);
        tv_balance_of_account = findViewById(R.id.tv_balance_of_account);
        tv_balance_of_unentry = findViewById(R.id.tv_balance_of_unentry);
        tv_month_bill = findViewById(R.id.tv_month_bill);


    }

    @Override
    protected void initData() {
        super.initData();
        List<String> list = new ArrayList<>();
        list.add("余额明细");
        list.add("待入账明细");

        tagAdapter = new AutoTagSingleAdapter(mContext);
        tagAdapter.setDatas(list);
        tagAdapter.setSingleSelectedListener(this);
        tagAdapter.setSelectsPosition(0);
        AutoLayoutHelper helper = new AutoLayoutHelper();
        helper.setType(0);
        helper.sethGap(DeviceUtils.dip2px(mContext, 25));
        autoLineLayout.setLayoutHelper(helper);
        autoLineLayout.setAdapter(tagAdapter);

    }

    @Override
    protected void initListener() {
        super.initListener();
        tv_month_bill.setOnClickListener(this);
        tv_balance_of_account.setOnClickListener(this);
    }

    @Override
    public void onAutoSingleSelected(int position, String s, boolean check) {

        switchFragment(position);

    }

    private void switchFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        if (position == 0) {
            if (balanceFragment == null) {
                balanceFragment = (CompanyBaseFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_bill_month).navigation();
                transaction.add(R.id.content, balanceFragment);
                Bundle bundle = new Bundle();
                bundle.putInt(Param.TYPE, 1);
                balanceFragment.setBundle(bundle);
            } else {
                transaction.show(balanceFragment);
            }

            currentFragment = balanceFragment;
        } else {
            if (waiteFragment == null) {
                waiteFragment = (CompanyBaseFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_bill_month).navigation();
                transaction.add(R.id.content, waiteFragment);
                Bundle bundle = new Bundle();
                bundle.putInt(Param.TYPE, 2);
                waiteFragment.setBundle(bundle);
            } else {
                transaction.show(waiteFragment);
            }

            currentFragment = waiteFragment;
        }
        transaction.commit();
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_month_bill) {
            ArouterUtils.getInstance().builder(ArouterParamApp.activity_bill_month)
                    .navigation(mContext);
        } else if (v.getId() == R.id.tv_balance_of_account) {
            ArouterUtils.getInstance().builder(ArouterParamApp.activity_balance)
                    .navigation(mContext);

        }
    }
}
