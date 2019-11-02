package com.hongniu.modulecargoodsmatch.ui.activiry;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiSearch;
import com.githang.statusbar.StatusBarCompat;
import com.hongniu.baselibrary.arouter.ArouterParamsMatch;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.ui.adapter.MapSearchAdapter;
import com.hongniu.baselibrary.ui.fragment.MapPointFrament;
import com.hongniu.baselibrary.utils.SearchTextWatcher;
import com.hongniu.modulecargoodsmatch.R;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.inter.OnItemClickListener;
import com.sang.common.utils.DeviceUtils;

import java.util.List;

import io.reactivex.Observable;

@Route(path = ArouterParamsMatch.activity_match_map)
public class MatchMapActivity extends RefrushActivity<PoiItem> implements View.OnClickListener, OnItemClickListener<PoiItem>, View.OnFocusChangeListener, SearchTextWatcher.SearchTextChangeListener {

    private MapPointFrament frament;
    private EditText etSearch;
    private ViewGroup btBack;
    private ViewGroup btCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_map);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.color_tran), true);
        StatusBarCompat.setTranslucent(getWindow(), true);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();

        etSearch = findViewById(R.id.et_search);
        btBack = findViewById(R.id.bt_back);
        btCancle = findViewById(R.id.bt_cancel);
        frament = new MapPointFrament();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, frament)
                .commit();
    }

    @Override
    protected void initListener() {
        super.initListener();
        btBack.setOnClickListener(this);
        btCancle.setOnClickListener(this);
        etSearch.setOnFocusChangeListener(this);
        etSearch.addTextChangedListener(new SearchTextWatcher(this));
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_cancel) {
            etSearch.setText("");
        } else if (v.getId() == R.id.bt_back) {
            finish();
        }
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
        return HttpAppFactory.searchPio(poiSearch);
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
        DeviceUtils.closeSoft(this);
        etSearch.clearFocus();
        frament.moveToPoint(poiItem);

    }

    /**
     * Called when the focus state of a view has changed.
     *
     * @param v        The view whose state has changed.
     * @param hasFocus The new focus state of v.
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        refresh.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onSearchTextChange(String msg) {
        if (TextUtils.isEmpty(msg)) {
            datas.clear();
            adapter.notifyDataSetChanged();
        } else {
            queryData(true);
        }
    }
}
