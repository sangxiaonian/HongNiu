package com.hongniu.modulefinance.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.entity.NiuOfAccountBean;
import com.hongniu.modulefinance.net.HttpFinanceFactory;
import com.hongniu.modulefinance.ui.adapter.FinanceCarOrderDetailAdapter;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.widget.SwitchTextLayout;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;

/**
 * 车辆订单明细页面
 */
@Route(path = ArouterParamsFinance.activity_finance_car_order_detail)
public class FinanceCarOrderDetailActivity extends RefrushActivity<NiuOfAccountBean> {

    private String title;
    private SwitchTextLayout switchTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_car_order_detail);
        title = getIntent().getStringExtra(Param.TRAN);
        setToolbarTitle(title);
        initView();
        initData();
        initListener();
    }


    @Override
    protected void initView() {
        super.initView();
        switchTime=findViewById(R.id.switch_time);

    }

    @Override
    protected void initData() {
        super.initData();
        switchTime.setTitle(  ConvertUtils.formatTime(new Date().getTime(),"yyyy年MM月"));
        queryData(true);
    }

    @Override
    protected Observable<CommonBean<PageBean<NiuOfAccountBean>>> getListDatas() {
        return HttpFinanceFactory.gueryNiuList(currentPage, 1);
    }

    @Override
    protected XAdapter<NiuOfAccountBean> getAdapter(List<NiuOfAccountBean> datas) {
        return new FinanceCarOrderDetailAdapter(this, datas);
    }
}
