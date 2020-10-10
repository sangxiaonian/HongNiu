package com.hongniu.freight.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.widget.SearchTitleView;
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.fy.companylibrary.ui.RefrushActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.entity.QueryOrderListBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.ui.holder.order.OrderHolderBuider;
import com.hongniu.freight.ui.holder.order.XOrderButtonClick;

import java.util.List;

import io.reactivex.Observable;


/**
 *@data  2020/2/7
 *@Author PING
 *@Description
 *
 * 订单搜索
 *
 */
@Route(path = ArouterParamApp.activity_search_order)
public class SearchOrderActivity extends RefrushActivity<OrderInfoBean> implements XOrderButtonClick.NextStepListener, View.OnClickListener, SearchTitleView.OnSearchClickListener {

    private RoleOrder role;
    private TextView tv_cancel;
    private SearchTitleView searchTitleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnlm_activity_search_order);
        setWhitToolBar("");
        role= (RoleOrder) getIntent().getSerializableExtra(Param.TRAN);
        initView();
        initData();
        initListener();

    }

    @Override
    protected void initView() {
        super.initView();
        tv_cancel=findViewById(R.id.tv_cancel);
        searchTitleView=findViewById(R.id.search);
    }

    @Override
    protected void initListener() {
        super.initListener();
        tv_cancel.setOnClickListener(this);
        searchTitleView.setOnSearchClickListener(this);
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
        QueryOrderListBean bean=new QueryOrderListBean();
        bean.setPageSize(Param.PAGE_SIZE);
        bean.setPageNum(currentPage);
        bean.setUserType(role.getType());
        bean.setSearchText(searchTitleView.getTitle());
        return HttpAppFactory.queryOrderList(bean);
    }

    @Override
    protected XAdapter<OrderInfoBean> getAdapter(List<OrderInfoBean> datas) {
        return new XAdapter<OrderInfoBean>(mContext, datas) {
            @Override
            public BaseHolder<OrderInfoBean> initHolder(ViewGroup parent, int viewType) {
                XOrderButtonClick xOrderButtonClick = new XOrderButtonClick(mContext);
                xOrderButtonClick.setType(role);
                return new OrderHolderBuider(mContext)
                        .setParent(parent)
                        .setType(role)
                        .setOrderButtonClickListener(xOrderButtonClick)
                        .build()
                        ;
            }
        };
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.tv_cancel){
            finish();
        }
    }

    @Override
    public void onSearch(String msg) {
        queryData(true);
    }

    /**
     * 再进行取消等操作完成之后,刷新界面
     */
    @Override
    public void doUpdate() {
        queryData(true,true);
    }
}
