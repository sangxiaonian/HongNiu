package com.hongniu.freight.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.widget.SearchTitleView;
import com.fy.androidlibrary.widget.editext.SearchTextWatcher;
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.fy.companylibrary.ui.RefrushActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.CarInfoBean;
import com.hongniu.freight.entity.PageSearchParams;
import com.hongniu.freight.net.HttpAppFactory;

import java.util.List;

import io.reactivex.Observable;

/**
 * @data 2020/2/13
 * @Author PING
 * @Description 搜索车辆信息 type=1 自己的车辆
 */
@Route(path = ArouterParamApp.activity_search_car)
public class SearchCarActivity extends RefrushActivity<CarInfoBean> implements SearchTextWatcher.SearchTextChangeListener {

    private SearchTitleView searchTitleView;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_car);
        setWhitToolBar("选择派送车辆");
          type = getIntent().getIntExtra(Param.TYPE, 0);
        initView();
        initData();
        initListener();
        queryData(true);
    }

    @Override
    protected void initView() {
        super.initView();
        searchTitleView = findViewById(R.id.search);
    }

    @Override
    protected void initListener() {
        super.initListener();
        searchTitleView.setSearchTextChangeListener(this);
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
    protected Observable<CommonBean<PageBean<CarInfoBean>>> getListDatas() {
        if (type==1){
            PageSearchParams param = new PageSearchParams();
            param.setPageNum(currentPage);
            param.setSearchText(searchTitleView.getTitle());
            param.setCarStatus("1");
            return HttpAppFactory.queryCarList(param);
        }else {
            return HttpAppFactory.queryAllCarList(currentPage, searchTitleView.getTitle());
        }

    }

    @Override
    protected XAdapter<CarInfoBean> getAdapter(List<CarInfoBean> datas) {
        return new XAdapter<CarInfoBean>(mContext, datas) {
            @Override
            public BaseHolder<CarInfoBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<CarInfoBean>(context, parent, R.layout.item_search_car) {
                    @Override
                    public void initView(View itemView, int position, final CarInfoBean data) {
                        super.initView(itemView, position, data);
                        TextView tv_title = itemView.findViewById(R.id.tv_car_info);
                        TextView tv_car_type = itemView.findViewById(R.id.tv_car_type);
                        TextView tv_phone = itemView.findViewById(R.id.tv_phone);

                        tv_car_type.setText(data.getCarType());
                        tv_phone.setText(data.getMobile());

                        tv_title.setText(String.format("%s-%s%s", data.getName(), data.getCarNumber(),data.getTransportStatus()==1?"(运输中)":""));
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.putExtra(Param.TRAN, data);
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                            }
                        });

                    }
                };
            }
        };
    }


    @Override
    public void onSearchTextChange(String msg) {
        queryData(true);

    }
}
