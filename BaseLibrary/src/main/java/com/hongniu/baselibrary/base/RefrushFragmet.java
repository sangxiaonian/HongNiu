package com.hongniu.baselibrary.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hongniu.baselibrary.R;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.widget.XRefreshLayout;
import com.sang.common.recycleview.adapter.XAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 作者： ${PING} on 2018/8/21.
 */
public abstract class RefrushFragmet<T> extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {
    protected XRefreshLayout refresh;
    protected int currentPage = 1;
    protected boolean isFirst = true;
    protected List<T> datas = new ArrayList<>();
    protected XAdapter<T> adapter;
    protected RecyclerView rv;
    private boolean hasNoMore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initView(inflater);
        refresh = view.findViewById(R.id.refresh);
        rv = view.findViewById(R.id.rv);
        if (refresh != null) {
            refresh.setOnRefreshListener(this);
            refresh.setOnLoadMoreListener(this);
        }


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queryData(true);
    }

    @Override
    protected void initData() {
        super.initData();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter = getAdapter(datas));

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

        refresh.loadmoreFinished(false);
        queryData(true);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (!hasNoMore) {
            queryData(false);
        } else {
            refresh.finishLoadMore();
        }
    }


    protected void queryData(final boolean isClear) {
        if (isClear) {
            currentPage = 1;
            hasNoMore = false;
        }
        getListDatas()
                .subscribe(new NetObserver<PageBean<T>>(this) {
                    @Override
                    public void doOnSuccess(PageBean<T> data) {
                        if (isClear) {
                            datas.clear();
                        }
                        currentPage++;
                        if (data != null && data.getList() != null) {
                            datas.addAll(data.getList());
                            if (data.getList().size() < Param.PAGE_SIZE) {
                                showNoMore();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    protected abstract Observable<CommonBean<PageBean<T>>> getListDatas();

    protected abstract XAdapter<T> getAdapter(List<T> datas);


    @Override
    public void onTaskStart(Disposable d) {
        this.d = d;
        if (isFirst) {
            isFirst = false;
            super.onTaskStart(d);
        }
    }

    @Override
    public void onTaskSuccess() {
        super.onTaskSuccess();
        refresh.finishLoadMore();
        refresh.finishRefresh();
    }

    /**
     * 显示没有更多数据了
     */
    public void showNoMore() {

        hasNoMore = true;

        refresh.loadmoreFinished(true);
    }
}
