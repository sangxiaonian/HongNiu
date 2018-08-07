package com.hongniu.modulefinance.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.modulefinance.R;

/**
 * 财务界面
 */
@Route(path = ArouterParamsFinance.activity_finance_activity)
public class FinanceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        setToolbarDarkTitle(getString(R.string.finance));
    }
}
