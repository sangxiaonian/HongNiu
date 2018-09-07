package com.hongniu.moduleorder.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiSearch;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.sang.common.event.BusFactory;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.DeviceUtils;

import java.util.List;

import io.reactivex.Observable;

public class OrderMapSearchActivity extends RefrushActivity<PoiItem> {
    private EditText etSearch;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!TextUtils.isEmpty(etSearch.getText().toString().trim())){
                queryData(true);

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_map_search);
        setToolbarTitle("");
        isFirst=false;
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
                    queryData(true);
                    DeviceUtils.hideSoft(etSearch);
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
                handler.sendEmptyMessageDelayed(0,200);
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
        PoiSearch poiSearch = new PoiSearch(mContext, query);

        return HttpOrderFactory.searchPio(poiSearch);
    }

    @Override
    protected XAdapter<PoiItem> getAdapter(List<PoiItem> datas) {
        return new XAdapter<PoiItem>(mContext, datas) {
            @Override
            public BaseHolder<PoiItem> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<PoiItem>(context, parent, R.layout.map_select_item) {
                    @Override
                    public void initView(View itemView, final int position, final PoiItem data) {
                        super.initView(itemView, position, data);
                        TextView tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                        TextView tvDes = (TextView) itemView.findViewById(R.id.tv_des);
                        final ImageView img = itemView.findViewById(R.id.img);
                        img.setVisibility(View.GONE);
                        tvDes.setText(data.getSnippet());
                        tvTitle.setText(data.getTitle());
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                img.setVisibility(View.VISIBLE);
                                BusFactory.getBus().post(new OrderEvent.SearchPioItem(data));
                                onBackPressed();
                            }
                        });
                    }
                };
            }
        };
    }
}
