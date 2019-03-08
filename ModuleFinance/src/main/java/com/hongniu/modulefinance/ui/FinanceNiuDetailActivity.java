package com.hongniu.modulefinance.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.entity.FinanceQueryNiuDetailBean;
import com.hongniu.modulefinance.entity.NiuOfAccountBean;
import com.hongniu.modulefinance.net.HttpFinanceFactory;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;

import java.util.List;

import io.reactivex.Observable;

/**
 * @data 2019/3/3
 * @Author PING
 * @Description 牛贝收益明细界面
 */
@Route(path = ArouterParamsFinance.activity_finance_niu_detail)
public class FinanceNiuDetailActivity extends RefrushActivity<FinanceQueryNiuDetailBean> {
    private TextView tvEarning;//我的收益
    private TextView tvLastEarning;//昨日收益
    NiuOfAccountBean infor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_niu_detail);
        infor=getIntent().getParcelableExtra(Param.TRAN);

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
        tvEarning.setText((infor==null|| TextUtils.isEmpty(infor.getTotalAmt()))?"0":infor.getTotalAmt());
        tvLastEarning.setText((infor==null|| TextUtils.isEmpty(infor.getYesterdayAmt()))?"0":infor.getYesterdayAmt());


    }

    @Override
    protected Observable<CommonBean<PageBean<FinanceQueryNiuDetailBean>>> getListDatas() {

        return HttpFinanceFactory.queryNiuOrderDetails(currentPage,(infor==null|| TextUtils.isEmpty(infor.getId()))?"0":infor.getId());
    }

    @Override
    protected XAdapter<FinanceQueryNiuDetailBean> getAdapter(List<FinanceQueryNiuDetailBean> datas) {
        return new XAdapter<com.hongniu.modulefinance.entity.FinanceQueryNiuDetailBean>(mContext, datas) {
            @Override
            public BaseHolder<FinanceQueryNiuDetailBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<FinanceQueryNiuDetailBean>(mContext, parent, R.layout.finance_item_my_niu_detail) {
                    @Override
                    public void initView(View itemView, int position, final FinanceQueryNiuDetailBean data) {
                        super.initView(itemView, position, data);
                        //名字
                        TextView tvTime = itemView.findViewById(R.id.tv_time);
                        //收益
                        TextView tvEarning = itemView.findViewById(R.id.tv_earning);
                        tvTime.setText(data.getDatestr()==null?"":data.getDatestr());
                        tvEarning.setText(String.valueOf(data.getAmount()));

                    }
                };
            }
        };
    }
}
