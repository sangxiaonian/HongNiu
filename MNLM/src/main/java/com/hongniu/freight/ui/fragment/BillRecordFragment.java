package com.hongniu.freight.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.utils.CommonUtils;
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.fy.companylibrary.ui.RefrushFragmet;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.AccountFlowParams;
import com.hongniu.freight.entity.BillInfoListBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.ui.holder.EmptyHolder;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/12.
 * <p>
 * 账单余额明细和带入张明细数据
 */
@Route(path = ArouterParamApp.fragment_bill_month)
public class BillRecordFragment extends RefrushFragmet<BillInfoListBean> {

    private int type;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        type = getBundle().getInt(Param.TYPE, 1);
        queryData(true);
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.mnlm_fragment_bill_month, null);
        return inflate;

    }

    /**
     * 获取空数据
     *
     * @param parent
     * @return
     */
    @Override
    protected PeakHolder getEmptyHolder(ViewGroup parent) {
        return new EmptyHolder(mContext, parent);
    }

    AccountFlowParams params = new AccountFlowParams();

    @Override
    protected Observable<CommonBean<PageBean<BillInfoListBean>>> getListDatas() {
        params.setPageNum(currentPage);
        params.setFlowtype(type);
        return HttpAppFactory.queryAccountFlows(params);
    }

    @Override
    protected XAdapter<BillInfoListBean> getAdapter(List<BillInfoListBean> datas) {
        return new XAdapter<BillInfoListBean>(mContext, datas) {
            @Override
            public BaseHolder<BillInfoListBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<BillInfoListBean>(context, parent, R.layout.mnlm_item_bill) {
                    @Override
                    public void initView(View itemView, int position, BillInfoListBean data) {
                        super.initView(itemView, position, data);
                        TextView tvCount = itemView.findViewById(R.id.tv_count);
                        TextView tvStatus = itemView.findViewById(R.id.tv_statute);
                        TextView tv_des = itemView.findViewById(R.id.tv_des);
                        TextView tv_time = itemView.findViewById(R.id.tv_time);
                        CommonUtils.setText(tvStatus,data.getTitle());
                        CommonUtils.setText(tv_des,data.getSubtitle());
                        CommonUtils.setText(tv_time,"支付时间:"+data.getCreateTime());
                        CommonUtils.setText(tvCount,data.getAmtStr());
                    }
                };
            }
        };
    }
}
