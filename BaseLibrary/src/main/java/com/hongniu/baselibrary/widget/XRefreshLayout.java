package com.hongniu.baselibrary.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * Created by danyic on 2017/9/8.
 */

public class XRefreshLayout extends SmartRefreshLayout {
    public RecycleFooter recycleFooter;
    public XRefreshLayout(Context context) {
        super(context);
        initUI();
    }

    public XRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }

    public XRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI();
    }

    private void initUI() {
        recycleFooter = new RecycleFooter(getContext());
        setRefreshHeader(new RecycleHeader(getContext()));
        setRefreshFooter(recycleFooter);
        setHeaderHeight(50);
        setFooterHeight(40);


    }

    public void loadmoreFinished(boolean isFinish){
        finishLoadMore();
        recycleFooter.loadmoreFinished(!isFinish);
//        setEnableLoadMore(isFinish);
        setNoMoreData(!isFinish);//1.0.5 以上
//        finishLoadmore();
//        setLoadmoreFinish(true);//1.0.4 及一下版本

    }

    public void hideLoadFinish(){
        recycleFooter.hideLoadFinish();
    }
}
