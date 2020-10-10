package com.hongniu.freight.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.fy.androidlibrary.widget.span.RoundedBackgroundSpan;
import com.hongniu.baselibrary.arouter.ArouterUtils;
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
 * @data 2020/2/10
 * @Author PING
 * @Description 我的车辆
 */
@Route(path = ArouterParamApp.activity_my_car_list)
public class CarListActivity extends RefrushActivity<CarInfoBean> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnlm_activity_my_car_list);
        setWhitToolBar("我的车辆");
        initView();
        initData();
        initListener();
        queryData(true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        queryData(true);
    }

    @Override
    protected void initData() {
        super.initData();
        adapter.addHeard(new PeakHolder(mContext, rv, R.layout.mnlm_item_car_foot) {
            @Override
            public void initView(View itemView, int position) {
                super.initView(itemView, position);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArouterUtils.getInstance().builder(ArouterParamApp.activity_car_add_modify)
                                .navigation(mContext);
                    }
                });
            }
        });
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
        PageSearchParams param = new PageSearchParams();
        param.setPageNum(currentPage);
        return HttpAppFactory.queryCarList(param);
    }

    @Override
    protected XAdapter<CarInfoBean> getAdapter(List<CarInfoBean> datas) {
        return new XAdapter<CarInfoBean>(mContext, datas) {
            @Override
            public BaseHolder<CarInfoBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<CarInfoBean>(context, parent, R.layout.mnlm_item_car) {
                    @Override
                    public void initView(View itemView, int position, final CarInfoBean data) {
                        super.initView(itemView, position, data);
                        TextView tv_title = itemView.findViewById(R.id.tv_title);
                        TextView tv_car_type = itemView.findViewById(R.id.tv_car_type);
                        TextView tv_state = itemView.findViewById(R.id.tv_state);

                        SpannableStringBuilder builder = new SpannableStringBuilder();
                        builder.append(data.getCarNumber());
                        int start = builder.length();

                        tv_state.setText("查看信息");
                        tv_state.setTextColor(context.getResources().getColor(R.color.color_of_040000) );
                        int status = data.getIdentityStatus();
                        RoundedBackgroundSpan span;
                        if (status == 4) {
                            builder.append("已认证");
                            span=getSpan(context
                                    , context.getResources().getColor(R.color.color_of_aaabaf), context.getResources().getColor(R.color.color_of_14_ffffff));

                        } else if (status == 5) {
                            builder.append("审核不通过");
                            span=  getSpan(context
                                    , context.getResources().getColor(R.color.color_of_ffffff), context.getResources().getColor(R.color.color_of_fa8c16));
                            tv_state.setText("重新认证");
                            tv_state.setTextColor(context.getResources().getColor(R.color.color_of_e50000) );

                        } else {
                            builder.append("审核中");
                            span=getSpan(context
                                    , context.getResources().getColor(R.color.color_of_ffffff), context.getResources().getColor(R.color.color_of_e50000));
                        }
                        int end = builder.length();
                        builder.setSpan(span, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                        tv_title.setText(builder);
                        tv_car_type.setText(data.getCarType());

                        tv_state.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                ToastUtils.getInstance().show("测试信息");
                                ArouterUtils.getInstance()
                                        .builder(ArouterParamApp.activity_car_add_modify)
                                        .withParcelable(Param.TRAN,data)
                                        .navigation(mContext);
                            }
                        });

                    }
                };
            }
        };
    }

    public RoundedBackgroundSpan getSpan(Context context, int color, int backColor) {
        RoundedBackgroundSpan span = new RoundedBackgroundSpan(mContext);
        span.setBackgroundColor(backColor);
        span.setTextColor(color);
        span.setCornerRadius(DeviceUtils.dip2px(context, 1));
        span.setTextSize(DeviceUtils.dip2px(context, 11));
        span.setPadding(DeviceUtils.dip2px(context, 4), 0, DeviceUtils.dip2px(context, 4), 0);
        span.setMargin(DeviceUtils.dip2px(context, 5), 0, 0, 0);
        span.setBordColor(Color.TRANSPARENT);
        return span;
    }
}
