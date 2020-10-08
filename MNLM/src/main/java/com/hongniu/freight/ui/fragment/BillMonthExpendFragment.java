package com.hongniu.freight.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.widget.autoline.AutoLineLayout;
import com.fy.androidlibrary.widget.autoline.helper.AutoLayoutHelper;
import com.fy.androidlibrary.widget.autoline.inter.AutoSingleSelectListener;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.BillInfoSearchParams;
import com.hongniu.freight.ui.adapter.AutoTagSingleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：  on 2020/2/12.
 * 月账单支出数据
 */
@Route(path = ArouterParamApp.fragment_bill_month_expend)
public class BillMonthExpendFragment extends CompanyBaseFragment implements AutoSingleSelectListener<String>, BillMonthChildFragment.OnBillListener {
    private AutoLineLayout autoLineLayout;
    private TextView tvExpendNumber;
    private TextView tvCount;
    private AutoTagSingleAdapter tagAdapter;
    private BillMonthChildFragment currentFragment, trackFragment, insuranceFragment;
    BillInfoSearchParams   paramsInsurance=new BillInfoSearchParams();
    BillInfoSearchParams  paramsYun =new BillInfoSearchParams();

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_bill_month_expend, null);
        autoLineLayout = inflate.findViewById(R.id.auto_layout);
        tvExpendNumber = inflate.findViewById(R.id.tv_expend_number);
        tvCount = inflate.findViewById(R.id.tv_count);
        return inflate;

    }

    @Override
    protected void initData() {
        super.initData();
        paramsYun=new BillInfoSearchParams();
        paramsInsurance=new BillInfoSearchParams();
        paramsYun.setFinanceType(1);
        paramsYun.setFeeType(1);
        paramsInsurance.setFinanceType(1);
        paramsInsurance.setFeeType(2);


        List<String> list = new ArrayList<>();
        list.add("运费");
        list.add("保费");
        tagAdapter = new AutoTagSingleAdapter(mContext);
        tagAdapter.setDatas(list);
        tagAdapter.setSingleSelectedListener(this);
        tagAdapter.setSelectsPosition(0);
        AutoLayoutHelper helper = new AutoLayoutHelper();
        helper.setType(1);
        helper.setColumn(2);
        helper.sethGap(DeviceUtils.dip2px(mContext, 25));
        autoLineLayout.setLayoutHelper(helper);
        autoLineLayout.setAdapter(tagAdapter);

        tvCount.setText("0");
        tvExpendNumber.setText(getNumberSpan("0"));
        upDate();
        switchFragment(0);
    }

    @Override
    public void setBundle(Bundle bundle) {
        super.setBundle(bundle);
        upDate();
    }

    private void upDate() {
        Bundle bundle = getBundle();
        if (bundle != null) {
            BillInfoSearchParams  params = bundle.getParcelable(Param.TRAN);
            if (params != null) {
                paramsYun.setMonth(params.getMonth());
                paramsYun.setYear(params.getYear());
                paramsInsurance.setMonth(params.getMonth());
                paramsInsurance.setYear(params.getYear());

                if (insuranceFragment!=null) {
                    Bundle bundle1 = new Bundle();
                    bundle.putParcelable(Param.TRAN,paramsInsurance);
                    insuranceFragment.setBundle(bundle1);
                }
                if (trackFragment!=null) {
                    Bundle bundle2 = new Bundle();
                    bundle.putParcelable(Param.TRAN,paramsYun);
                    trackFragment.setBundle(bundle2);
                }

            }
        }
    }

    private SpannableStringBuilder getNumberSpan(String count) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("共支出")
                .append(count)
                .append("笔,合计");
        builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_of_fa8c16))
                , 3, 3 + count.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return builder;

    }


    @Override
    public void onAutoSingleSelected(int position, String s, boolean check) {
        switchFragment(position);
    }

    private void switchFragment(int position) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        if (position == 0) {
            if (trackFragment == null) {
                trackFragment = (BillMonthChildFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_bill_month_child).navigation();
                trackFragment.setBillListener(this);
                transaction.add(R.id.content, trackFragment);

            } else {
                transaction.show(trackFragment);
            }
            Bundle bundle = new Bundle();
            bundle.putInt(Param.TYPE, 1);
            bundle.putParcelable(Param.TRAN,paramsYun);
            trackFragment.setBundle(bundle);
            currentFragment = trackFragment;
        } else {
            if (insuranceFragment == null) {
                insuranceFragment = (BillMonthChildFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_bill_month_child).navigation();
                insuranceFragment.setBillListener(this);
                transaction.add(R.id.content, insuranceFragment);

            } else {
                transaction.show(insuranceFragment);
            }
            Bundle bundle = new Bundle();
            bundle.putInt(Param.TYPE, 2);
            bundle.putParcelable(Param.TRAN,paramsInsurance);
            insuranceFragment.setBundle(bundle);
            currentFragment = insuranceFragment;
        }
        transaction.commit();
    }

    @Override
    public void showInfo(double totle, int count) {
        tvCount.setText(ConvertUtils.changeFloat(totle,2));
        tvExpendNumber.setText(getNumberSpan(count+""));
    }
}
