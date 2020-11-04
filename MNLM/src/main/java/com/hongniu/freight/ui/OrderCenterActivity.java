package com.hongniu.freight.ui;

import android.os.Bundle;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.fy.companylibrary.config.ArouterParamMNLM;
import com.fy.companylibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.fy.companylibrary.ui.RefrushActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.ui.holder.ReceiveOrderHolder;
import com.hongniu.freight.ui.holder.order.XOrderButtonClick;

import java.util.List;

import io.reactivex.Observable;

/**
 * @data 2020/2/10
 * @Author PING
 * @Description 我要接单
 */
@Route(path = ArouterParamMNLM.activity_order_center)
public class OrderCenterActivity extends RefrushActivity<OrderInfoBean> implements XOrderButtonClick.NextStepListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnlm_activity_order_center);
        setWhitToolBar("我要接单");
        initView();
        initData();
        initListener();
        queryData(true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        queryData(true);
    }

    /**
     * 获取空界面
     *
     * @param parent
     * @return
     */
    @Override
    protected PeakHolder getEmptyHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected Observable<CommonBean<PageBean<OrderInfoBean>>> getListDatas() {

        return HttpAppFactory.queryOwnerOrderList(currentPage, Param.PAGE_SIZE);
    }

    @Override
    protected XAdapter<OrderInfoBean> getAdapter(List<OrderInfoBean> datas) {
        return new XAdapter<OrderInfoBean>(mContext, datas) {
            @Override
            public BaseHolder<OrderInfoBean> initHolder(ViewGroup parent, int viewType) {
                return new ReceiveOrderHolder(mContext,parent,RoleOrder.CARRIER);
            }
        };
    }

    /**
     * 再进行取消等操作完成之后,刷新界面
     */
    @Override
    public void doUpdate() {
        queryData(true);
    }
}
