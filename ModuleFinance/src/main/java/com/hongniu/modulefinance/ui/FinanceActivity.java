package com.hongniu.modulefinance.ui;

import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.baselibrary.utils.PickerDialogUtils;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.event.FinanceEvent;
import com.hongniu.modulefinance.ui.fragment.FinanceExpendFragment;
import com.hongniu.modulefinance.ui.fragment.FinanceIncomeFragment;
import com.fy.androidlibrary.event.BusFactory;
import com.sang.common.widget.SwitchTextLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 财务界面
 */
@Route(path = ArouterParamsFinance.activity_finance_activity)
public class FinanceActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, OnTimeSelectListener, SwitchTextLayout.OnSwitchListener, OnDismissListener {


    private FinanceExpendFragment expendFragment;
    private FinanceIncomeFragment incomeFragment;
    private BaseFragment currentFragment;
    private SwitchTextLayout switcTime;
    private RadioGroup rg;
    private RadioButton rbLeft;
    private RadioButton rbRight;
    private TimePickerView timePickerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        setToolbarRedTitle(getString(R.string.finance));
        initView();
        initData();
        initListener();
        rbLeft.performClick();

        onTimeSelect(new Date(), null);

    }

    @Override
    protected void initView() {
        super.initView();
        switcTime = findViewById(R.id.switch_title);
        rg = findViewById(R.id.rg);
        rbLeft = findViewById(R.id.rb_left);
        rbRight = findViewById(R.id.rb_right);
        setToolbarSrcRight(R.mipmap.icon_search_w_36);
        timePickerView = PickerDialogUtils.initTimePicker(mContext, this, new boolean[]{true, true, false, false, false, false});
        timePickerView.setOnDismissListener(this);

    }

    @Override
    protected void initListener() {
        super.initListener();
        rg.setOnCheckedChangeListener(this);
        setToolbarRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArouterUtils.getInstance().builder(ArouterParamsFinance.activity_finance_search).navigation(mContext);
            }
        });
        switcTime.setListener(this);

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }
        if (checkedId == R.id.rb_left) {//支出
            if (expendFragment == null) {
                expendFragment = new FinanceExpendFragment();
                fragmentTransaction.add(R.id.content, expendFragment);
            } else {
                fragmentTransaction.show(expendFragment);
            }
            currentFragment = expendFragment;
        } else if (checkedId == R.id.rb_right) {//收入
            if (incomeFragment == null) {
                incomeFragment = new FinanceIncomeFragment();
                fragmentTransaction.add(R.id.content, incomeFragment);
            } else {
                fragmentTransaction.show(incomeFragment);
            }
            currentFragment = incomeFragment;
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onTimeSelect(Date date, View v) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        String data = format.format(date);
        switcTime.setTitle(data);
        BusFactory.getBus().postSticky(new FinanceEvent.SelectMonthEvent(date));
    }

    @Override
    public void onOpen(SwitchTextLayout switchTextLayout, View view) {
        timePickerView.show();
    }

    @Override
    public void onClose(SwitchTextLayout switchTextLayout, View view) {
        timePickerView.dismiss();
    }

    @Override
    public void onDismiss(Object o) {
        switcTime.closeSwitch();
    }
}
