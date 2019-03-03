package com.hongniu.modulefinance.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.entity.NiuOfAccountBean;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @data 2019/3/3
 * @Author PING
 * @Description 牛贝收益明细界面
 */
@Route(path = ArouterParamsFinance.activity_finance_niu_detail)
public class FinanceNiuDetailActivity extends RefrushActivity<NiuOfAccountBean> {
    private TextView tvEarning;//我的收益
    private TextView tvLastEarning;//昨日收益

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_niu_detail);
        initView();
        initData();
        initListener();
        queryData(true);
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbarRedTitle("牛贝收益明细");
        tvEarning = findViewById(R.id.tv_earning);
        tvLastEarning = findViewById(R.id.tv_last_earning);


    }

    @Override
    protected void initData() {
        super.initData();
        tvEarning.setText("1738.00");
        tvLastEarning.setText("89.00");


    }

    @Override
    protected Observable<CommonBean<PageBean<NiuOfAccountBean>>> getListDatas() {

        CommonBean<PageBean<NiuOfAccountBean>> commonBean = new CommonBean<>();
        PageBean<NiuOfAccountBean> pageBean = new PageBean<>();
        commonBean.setData(pageBean);
        commonBean.setCode(200);
        ArrayList<NiuOfAccountBean> niuOfAccountBeans = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            niuOfAccountBeans.add(new NiuOfAccountBean());
        }

        pageBean.setList(niuOfAccountBeans);
        return Observable.just(commonBean);
    }

    @Override
    protected XAdapter<NiuOfAccountBean> getAdapter(List<NiuOfAccountBean> datas) {
        return new XAdapter<NiuOfAccountBean>(mContext, datas) {
            @Override
            public BaseHolder<NiuOfAccountBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<NiuOfAccountBean>(mContext, parent, R.layout.finance_item_my_niu_detail) {
                    @Override
                    public void initView(View itemView, int position, final NiuOfAccountBean data) {
                        super.initView(itemView, position, data);
                        //名字
                        TextView tvTime = itemView.findViewById(R.id.tv_time);
                        //收益
                        TextView tvEarning = itemView.findViewById(R.id.tv_earning);

                    }
                };
            }
        };
    }
}
