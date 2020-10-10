package com.hongniu.freight.ui.fragment;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.BillInfoSearchParams;

/**
 * 作者：  on 2020/2/12.
 * 月账单收入数据
 */
@Route(path = ArouterParamApp.fragment_bill_month_income)
public class BillMonthIncomeFragment extends CompanyBaseFragment implements BillMonthChildFragment.OnBillListener {
    private TextView tvExpendNumber;
    private TextView tvCount;

    BillInfoSearchParams params;
    private BillMonthChildFragment trackFragment;

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.mnlm_fragment_bill_month_income, null);
        tvExpendNumber = inflate.findViewById(R.id.tv_expend_number);
        tvCount = inflate.findViewById(R.id.tv_count);
        return inflate;

    }

    @Override
    protected void initData() {
        super.initData();
        tvCount.setText("0");
        tvExpendNumber.setText(getNumberSpan("0"));
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        trackFragment = (BillMonthChildFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_bill_month_child).navigation();
        transaction.add(R.id.content, trackFragment);
        transaction.commit();
        upDate();
    }

    @Override
    protected void initListener() {
        super.initListener();
        trackFragment.setBillListener(this);
    }

    @Override
    public void setBundle(Bundle bundle) {
        super.setBundle(bundle);

        upDate();
    }

   private void upDate(){
       Bundle bundle = getBundle();
       if (bundle != null) {
           params = bundle.getParcelable(Param.TRAN);
           if (params != null) {
               params.setFeeType(0);
               params.setFinanceType(2);
           }
           if (trackFragment!=null) {
               trackFragment.setBundle(bundle);
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
    public void showInfo(double totle, int count) {
        tvCount.setText(ConvertUtils.changeFloat(totle,2));
        tvExpendNumber.setText(getNumberSpan(count+""));
    }
}
