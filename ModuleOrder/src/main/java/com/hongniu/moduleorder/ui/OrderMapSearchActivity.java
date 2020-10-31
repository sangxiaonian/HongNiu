package com.hongniu.moduleorder.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiSearch;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.moduleorder.R;
import com.sang.common.recycleview.inter.OnItemClickListener;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.hongniu.baselibrary.ui.adapter.MapSearchAdapter;
import com.fy.androidlibrary.event.BusFactory;
import com.sang.common.recycleview.adapter.XAdapter;
import com.fy.androidlibrary.utils.DeviceUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class OrderMapSearchActivity extends RefrushActivity<PoiItem> implements OnItemClickListener<PoiItem> {
    private EditText etSearch;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!TextUtils.isEmpty(etSearch.getText().toString().trim())) {
                queryData(true);

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_map_search);
        setToolbarTitle("");
        isFirst = false;
        initView();
        initData();
        initListener();

    }

    @Override
    protected void initView() {
        super.initView();
        etSearch = findViewById(R.id.et_search);
    }


    @Override
    protected void initListener() {
        super.initListener();
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String trim = etSearch.getText().toString().trim();
                    if (!CollectionUtils.isEmpty(datas)&&!TextUtils.isEmpty(trim)){
                        OrderEvent.SearchPioItem searchPioItem = new OrderEvent.SearchPioItem(datas.get(0));
                        searchPioItem.key= trim;
                        BusFactory.getBus().post(searchPioItem);
                        DeviceUtils.hideSoft(etSearch);
                        onBackPressed();
                    }
                }
                return true;
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, 200);
            }
        });
    }

    @Override
    protected Observable<CommonBean<PageBean<PoiItem>>> getListDatas() {
        PoiSearch.Query query = new PoiSearch.Query(etSearch.getText().toString().trim(), "", "");
//keyWord表示搜索字符串，
//第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
//cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(Param.PAGE_SIZE);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);//设置查询页码
        query.setCityLimit(true);
        query.requireSubPois(true);
        PoiSearch poiSearch = new PoiSearch(mContext, query);
        return HttpOrderFactory.searchPio(poiSearch);
    }

    protected void queryData(final boolean isClear) {
        if (isClear) {
            refresh.loadmoreFinished(true);
            currentPage = 1;
        }
        if (TextUtils.isEmpty(etSearch.getText().toString().trim())) {
            refresh.finishRefresh(500);
            return;
        }
        getListDatas()
                .subscribe(new NetObserver<PageBean<PoiItem>>(this) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (isFirst) {
                            isFirst = false;
                            super.onSubscribe(d);
                        } else {
                            disposable = d;
                        }
                    }

                    @Override
                    public void doOnSuccess(PageBean<PoiItem> data) {
                        if (isClear && data != null && !CollectionUtils.isEmpty(data.getList())) {
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


    @Override
    protected XAdapter<PoiItem> getAdapter(List<PoiItem> datas) {

        return new MapSearchAdapter(mContext, datas).setClickListener(this);

    }

    /**
     * 条目被点击
     *
     * @param position
     * @param poiItem
     */
    @Override
    public void onItemClick(int position, PoiItem poiItem) {
        OrderEvent.SearchPioItem searchPioItem = new OrderEvent.SearchPioItem(datas.get(0));
        searchPioItem.key= poiItem.getTitle();
        BusFactory.getBus().post(searchPioItem);
        DeviceUtils.hideSoft(etSearch);
        onBackPressed();

    }
}
