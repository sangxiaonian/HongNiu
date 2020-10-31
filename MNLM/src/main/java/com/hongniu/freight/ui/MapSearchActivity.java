package com.hongniu.freight.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiSearch;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.widget.editext.SearchTextWatcher;
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.fy.androidlibrary.widget.recycle.control.RecycleControl;
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.fy.androidlibrary.widget.recycle.utils.XAdapterDataObserver;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.fy.companylibrary.ui.RefrushActivity;
import com.fy.companylibrary.widget.ItemTextView;
import com.githang.statusbar.StatusBarCompat;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.TranMapBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.ui.adapter.MapSearchAdapter;
import com.hongniu.freight.ui.fragment.MapPointFragment;
import com.hongniu.freight.utils.Utils;
import com.sang.thirdlibrary.map.MapUtils;

import java.util.List;

import io.reactivex.Observable;

/**
 * @data 2020/2/17
 * @Author PING
 * @Description 下单页面发货和收货信息
 */
@Route(path = ArouterParamApp.activity_map_search)
public class MapSearchActivity extends RefrushActivity<PoiItem> implements View.OnClickListener, MapPointFragment.OnMapPointChangeListener, SearchTextWatcher.SearchTextChangeListener, RecycleControl.OnItemClickListener<PoiItem>, MapUtils.OnMapChangeListener, ItemTextView.OnCenterChangeListener {
    private MapPointFragment frament;
    private EditText etSearch;
    private ItemTextView item_address;
    private ItemTextView item_name;
    private ItemTextView item_phone;
    private ViewGroup btBack;
    private ViewGroup btCancle;
    private ViewGroup ll_bottom;
    private TextView bt_sum;
    private TextView tv;


    private TranMapBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnlm_activity_map_search);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.color_of_tran), true);
        StatusBarCompat.setTranslucent(getWindow(), true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        bean = new TranMapBean();
        initView();

        initData();
        initListener();
        boolean isEnd = getIntent().getBooleanExtra(Param.TRAN, false);
        if (isEnd) {
            etSearch.setHint("在哪儿收货");
            item_name.setTextCenterHide("收货人姓名");
            item_name.setTextLeft("收货人");
            item_phone.setTextCenterHide("收货人电话");
            tv.setText("填写收货信息");
        } else {
            item_name.setTextLeft("发货人");
            tv.setText("填写发货信息");
            item_phone.setTextCenterHide("发货人姓名");
            item_phone.setTextCenterHide("发货人电话");
            etSearch.setHint("从哪儿发货");

        }
        etSearch.clearFocus();
        check(false);
    }

    @Override
    protected void initView() {
        super.initView();
        ll_bottom = findViewById(R.id.ll_bottom);
        tv = findViewById(R.id.tv);
        item_address = findViewById(R.id.item_address);
        item_name = findViewById(R.id.item_name);
        item_phone = findViewById(R.id.item_phone);
        etSearch = findViewById(R.id.et_search);
        btBack = findViewById(R.id.bt_back);
        btCancle = findViewById(R.id.bt_cancel);
        bt_sum = findViewById(R.id.bt_sum);
        frament = new MapPointFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, frament)
                .commit()
        ;
        refresh.setVisibility(View.GONE);
    }


    @Override
    protected void initListener() {
        super.initListener();
        btBack.setOnClickListener(this);
        bt_sum.setOnClickListener(this);
        btCancle.setOnClickListener(this);

        etSearch.addTextChangedListener(new SearchTextWatcher(this));


        item_address.setOnCenterChangeListener(this);
        item_name.setOnCenterChangeListener(this);
        item_phone.setOnCenterChangeListener(this);

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
        } else if (v.getId() == R.id.bt_sum) {
            if (check(true)) {
                Intent intent = new Intent();

                String phone = item_phone.getTextCenter().trim();
                String address = TextUtils.isEmpty(item_address.getTextCenter()) ? "" : item_address.getTextCenter();
                bean.setAddressDetail(Utils.dealPioPlace(bean.getPoiItem()) + address);
                bean.setAddress(address);
                bean.setName(TextUtils.isEmpty(item_name.getTextCenter()) ? null : item_name.getTextCenter());
                bean.setPhone(TextUtils.isEmpty(phone) ? null : phone);
                intent.putExtra(Param.TRAN, bean);
                setResult(0, intent);
                finish();
            }
        }
    }

    private boolean check(boolean show) {
        Utils.setButton(bt_sum, false);
        if (bean.getPoiItem() == null) {
            if (show) {
                ToastUtils.getInstance().show("请选择地址信息");
            }
            return false;
        }

//        if (TextUtils.isEmpty(item_address.getTextCenter())) {
//            if (show) {
//                ToastUtils.getInstance().show("请填写门牌号");
//            }
//            return false;
//        }
        if (TextUtils.isEmpty(item_name.getTextCenter())) {
            if (show) {
                ToastUtils.getInstance().show("请填写" + item_name.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_phone.getTextCenter())) {
            if (show) {
                ToastUtils.getInstance().show("请填写" + item_phone.getTextCenterHide());
            }
            return false;
        }
        Utils.setButton(bt_sum, true);

        return true;
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
        XAdapter<PoiItem> xAdapter = new MapSearchAdapter(mContext, datas).setClickListener(this);
        xAdapter.registerAdapterDataObserver(new XAdapterDataObserver() {
            @Override
            public void onChanged() {
                refresh.setVisibility(adapter.getItemCount() == 0 ? View.GONE : View.VISIBLE);
                if (ll_bottom != null)
                    ll_bottom.setVisibility(adapter.getItemCount() != 0 ? View.GONE : View.VISIBLE);
            }
        });
        return xAdapter;
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
        refresh.setVisibility(View.GONE);
        frament.moveToPoint(poiItem);
        check(false);

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

    /**
     * 地图选中地点更改
     *
     * @param poiItem
     */
    @Override
    public void onMapPointChange(PoiItem poiItem) {
        bean.setPoiItem(poiItem);
        etSearch.setText("");
    }

    @Override
    public void onBackPressed() {
        if (refresh.getVisibility() == View.VISIBLE) {
            etSearch.clearFocus();
            refresh.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 定位改变监听
     *
     * @param latitude
     * @param longitude
     */
    @Override
    public void loactionChangeListener(double latitude, double longitude) {

    }

    boolean scroll;

    /**
     * 地图移动变化
     *
     * @param latitude
     * @param longitude
     */
    @Override
    public void onCameraChange(double latitude, double longitude) {

        if (!scroll) {
            ll_bottom.animate().translationY(ll_bottom.getHeight()).start();
        }
        scroll = true;
    }

    /**
     * 地图移动完成
     *
     * @param latitude
     * @param longitude
     */
    @Override
    public void onCameraChangeFinish(double latitude, double longitude) {
        ll_bottom.animate().translationY(0).start();
        scroll = false;
    }

    @Override
    public void onCenterChange(String msg) {
        check(false);
    }


}
