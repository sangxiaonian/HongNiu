package com.hongniu.modulefinance.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.ui.fragment.FinanceExpendFragment;
import com.hongniu.modulefinance.ui.fragment.FinanceIncomeFragment;
import com.sang.common.utils.JLog;
import com.sang.common.widget.SwitchTextLayout;

/**
 * 财务界面
 */
@Route(path = ArouterParamsFinance.activity_finance_activity)
public class FinanceActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {



    private FinanceExpendFragment expendFragment;
    private FinanceIncomeFragment incomeFragment;
    private BaseFragment currentFragment;
    private SwitchTextLayout switcTime;
    private RadioGroup rg;
    private RadioButton rbLeft;
    private RadioButton rbRight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        setToolbarDarkTitle(getString(R.string.finance));
        initView();
        initData();
        initListener();
        rbLeft.performClick();
    }

    @Override
    protected void initView() {
        super.initView();
        switcTime=findViewById(R.id.switch_title);
        rg=findViewById(R.id.rg);
        rbLeft=findViewById(R.id.rb_left);
        rbRight=findViewById(R.id.rb_right);
    }

    @Override
    protected void initListener() {
        super.initListener();
        rg.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment!=null){
            fragmentTransaction.hide(currentFragment);
        }
        if (checkedId==R.id.rb_left){//支出
            if (expendFragment==null){
                expendFragment=new FinanceExpendFragment();
                fragmentTransaction.add(R.id.content,expendFragment);
            }else {
                fragmentTransaction.show(expendFragment);
            }
            currentFragment=expendFragment;
        }else if (checkedId==R.id.rb_right){//收入
            if (incomeFragment==null){
                incomeFragment=new FinanceIncomeFragment();
                fragmentTransaction.add(R.id.content,incomeFragment);
            }else {
                fragmentTransaction.show(incomeFragment);
            }
            currentFragment=incomeFragment;
        }
        fragmentTransaction.commit();
    }
}
