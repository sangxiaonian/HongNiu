package com.hongniu.freight.ui;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.hongniu.baselibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.fy.companylibrary.ui.RefrushActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.BillInfoBean;
import com.hongniu.freight.entity.BillInfoListBean;
import com.hongniu.freight.entity.BillInfoSearchParams;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.ui.holder.BillHolder;
import com.hongniu.freight.ui.holder.EmptyHolder;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;


/**
 * @data 2020/2/13
 * @Author PING
 * @Description 账单搜索
 */
@Route(path = ArouterParamApp.activity_bill_search)
public class BillSearchActivity extends RefrushActivity<BillInfoListBean> implements View.OnClickListener, SearchTitleView.OnSearchClickListener {
    private SearchTitleView searchTitleView;
    private TextView tv_cancel;
    BillInfoSearchParams params = new BillInfoSearchParams();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnlm_activity_bill_search);
        setWhitToolBar("");
        params=getIntent() .getParcelableExtra(Param.TRAN);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        tv_cancel = findViewById(R.id.tv_cancel);
        searchTitleView = findViewById(R.id.search);
    }

    @Override
    protected void initListener() {
        super.initListener();
        tv_cancel.setOnClickListener(this);
        searchTitleView.setOnSearchClickListener(this);
    }

    /**
     * 获取空数据
     *
     * @param parent
     * @return
     */
    @Override
    protected PeakHolder getEmptyHolder(ViewGroup parent) {
        EmptyHolder emptyHolder = new EmptyHolder(mContext, parent);
        emptyHolder.setEmptyMsg("未找到相应账单");
        return emptyHolder;
    }

    @Override
    protected Observable<CommonBean<PageBean<BillInfoListBean>>> getListDatas() {
        params.setPageNum(currentPage);
        params.setCarNo(TextUtils.isEmpty(searchTitleView.getTitle())?null:searchTitleView.getTitle());
        return  HttpAppFactory.searchAccountList(params)
                    .map(new Function<CommonBean<BillInfoBean>, CommonBean<PageBean<BillInfoListBean>>>() {
                        @Override
                        public CommonBean<PageBean<BillInfoListBean>> apply(CommonBean<BillInfoBean> billInfoBeanCommonBean) throws Exception {
                            CommonBean<PageBean<BillInfoListBean>> commonBean=new CommonBean<>();
                            commonBean.setCode(billInfoBeanCommonBean.getCode());
                            commonBean.setMsg(billInfoBeanCommonBean.getMsg());
                            if (commonBean.getCode()==Param.SUCCESS_FLAG) {
                                PageBean<BillInfoListBean> bean = new PageBean<>();
                                bean.setList(billInfoBeanCommonBean.getData().getList());
                                commonBean.setData(bean);
                            }

                            return commonBean;
                        }
                    })
                ;
    }

    @Override
    public void onTaskSuccess() {
        super.onTaskSuccess();
        showNoMore();
    }

    @Override
    protected XAdapter<BillInfoListBean> getAdapter(List<BillInfoListBean> datas) {
        return new XAdapter<BillInfoListBean>(mContext, datas) {
            @Override
            public BaseHolder<BillInfoListBean> initHolder(ViewGroup parent, int viewType) {
                return new BillHolder(mContext, parent);
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
        if (v.getId() == R.id.tv_cancel) {
            finish();
        }
    }

    @Override
    public void onSearch(String msg) {
        queryData(true,true);

    }
}
