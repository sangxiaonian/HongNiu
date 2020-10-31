package com.fy.companylibrary.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fy.androidlibrary.net.rx.BaseObserver;
import com.fy.androidlibrary.net.rx.RxUtils;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.androidlibrary.widget.recycle.EmptyRecycleView;
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.fy.companylibrary.R;
import com.fy.companylibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.fy.companylibrary.net.NetObserver;
import com.hongniu.baselibrary.widget.XRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 作者： ${PING} on 2018/8/21.
 */
public abstract class RefrushFragmet<T> extends CompanyBaseFragment implements OnRefreshListener, OnLoadMoreListener {
    protected XRefreshLayout refresh;
    protected int currentPage = 1;
    protected boolean isFirst = true;
    protected List<T> datas = new ArrayList<>();
    protected XAdapter<T> adapter;
    protected EmptyRecycleView rv;

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
    protected void initData() {
        super.initData();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter = getAdapter(datas));
        rv.setEmptyHolder(getEmptyHolder(rv));
        adapter.notifyDataSetChanged();
    }

    /**
     * 获取空数据
     * @param parent
     * @return
     */
    protected abstract PeakHolder getEmptyHolder(ViewGroup parent);

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        queryData(true);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        queryData(false);

    }


    public void queryData(final boolean isClear, boolean showLoading) {
        isFirst = showLoading;
        queryData(isClear);
    }

    protected void queryData(final boolean isClear) {
        if (isClear) {
            refresh.loadmoreFinished(true);
            currentPage = 1;
        }
        getListDatas()
                .subscribe(new NetObserver<PageBean<T>>(this) {

                    @Override
                    public void onSubscribe(Disposable d) {
                        if (isFirst) {
                            isFirst = false;
                            onTaskStart(d);
                        }

                    }

                    @Override
                    public void doOnSuccess(PageBean<T> data) {
                        super.doOnSuccess(data);
                        if (isClear) {
                            datas.clear();
                        }
                        if (data != null && !CollectionUtils.isEmpty(data.getList())) {
                            currentPage++;
                            datas.addAll(data.getList());
                            if (data.getList().size() < Param.PAGE_SIZE) {
                                showNoMore();
                            }
                        } else {
                            showNoMore();
                        }
                        adapter.notifyDataSetChanged();
                    }



                });
    }

    protected abstract Observable<CommonBean<PageBean<T>>> getListDatas();

    protected abstract XAdapter<T> getAdapter(List<T> datas);


    @Override
    public void onTaskStart(Disposable d) {
        super.onTaskStart(d);

    }

    @Override
    public void onTaskSuccess() {
        super.onTaskSuccess();
        refresh.finishLoadMore();
        refresh.finishRefresh();
    }

    @Override
    public void onTaskFail(Throwable e, int code, String msg) {
        super.onTaskFail(e, code, msg);
        refresh.finishLoadMore();
        refresh.finishRefresh();
    }

    /**
     * 显示没有更多数据了
     */
    public void showNoMore() {
        Observable.just(1)
                .delay(200, TimeUnit.MILLISECONDS)
                .compose(RxUtils.<Integer>getSchedulersObservableTransformer())
                .subscribe(new BaseObserver<Integer>(null) {
                    @Override
                    public void onNext(Integer result) {
                        super.onNext(result);
                        if (refresh != null && !isDetached()) {
                            refresh.loadmoreFinished(false);
                        }

                    }

                });
    }
}
