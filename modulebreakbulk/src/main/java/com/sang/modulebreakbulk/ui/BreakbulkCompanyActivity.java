package com.sang.modulebreakbulk.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.hongniu.baselibrary.arouter.ArouterParamsBreakbulk;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.utils.PermissionUtils;
import com.sang.common.entity.Citys;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.sang.common.entity.NewAreaBean;
import com.sang.common.event.BusFactory;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.JLog;
import com.sang.common.utils.LoginUtils;
import com.hongniu.modulebreakbulk.R;
import com.sang.common.imgload.ImageLoader;
import com.sang.common.net.rx.BaseObserver;
import com.sang.common.net.rx.RxUtils;
import com.sang.common.recycleview.RecycleViewSupportEmpty;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.SharedPreferencesUtils;
import com.sang.common.widget.SwitchTextLayout;
import com.sang.common.widget.popu.BasePopu;
import com.sang.common.widget.popu.OrderCitysPop;
import com.sang.common.widget.popu.OrderMainPop;
import com.sang.common.widget.popu.inter.OnPopuDismissListener;
import com.sang.modulebreakbulk.entity.BreakbulkCompanyInfoBean;
import com.sang.modulebreakbulk.entity.BreakbulkCompanyInfoParam;
import com.sang.modulebreakbulk.net.HttpBreakFactory;
import com.sang.thirdlibrary.chact.ChactHelper;
import com.sang.thirdlibrary.map.LoactionUtils;
import com.sang.thirdlibrary.map.utils.MapConverUtils;
import com.sang.thirdlibrary.map.utils.SingleLoaction;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @data 2019/9/22
 * @Author PING
 * @Description 零担发货选择物流公司页面
 */
@Route(path = ArouterParamsBreakbulk.activity_breakbulk_company)
public class BreakbulkCompanyActivity extends RefrushActivity<BreakbulkCompanyInfoBean> implements SwitchTextLayout.OnSwitchListener, OnPopuDismissListener, OrderMainPop.OnPopuClickListener, OrderCitysPop.OnCityClickListener, AMapLocationListener {
    private SwitchTextLayout switchLeft;
    private SwitchTextLayout switchRight;
    OrderMainPop<String> popOrder;//排序
    OrderCitysPop popCitys;//城市选择
    private List<String> orders;
    private Citys areabean;//城市数据
    private BreakbulkCompanyInfoParam breakbulkCompanyInfoParam;
    private SingleLoaction singleLoaction;
    private AMapLocation aMapLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakbulk_company);
        setToolbarTitle("选择物流公司");
        setToolbarSrcRight("发货记录");
        setToolbarRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArouterUtils.getInstance()
                        .builder(ArouterParamsBreakbulk.activity_breakbulk_consignment_record)
                        .navigation(mContext);
            }
        });
        initView();
        initData();
        initListener();

        refresh.hideLoadFinish();

        if (!TextUtils.isEmpty(breakbulkCompanyInfoParam.getCity())) {
            switchLeft.setTitle(breakbulkCompanyInfoParam.getCity());
            queryData(true);
        } else {
            //开启定位
            startLoaction();
        }
    }

    @Override
    protected void initView() {
        super.initView();
        switchLeft = findViewById(R.id.switch_left);
        switchRight = findViewById(R.id.switch_right);
        popOrder = new OrderMainPop<>(mContext);
        popCitys = new OrderCitysPop(mContext);
    }

    @Override
    protected void initData() {
        super.initData();
        orders = Arrays.asList(getResources().getStringArray(R.array.breakbulk_company_order));
        popOrder.upDatas(orders);
        breakbulkCompanyInfoParam = new BreakbulkCompanyInfoParam();
        //记住初始城市
        breakbulkCompanyInfoParam.setCity(SharedPreferencesUtils.getInstance().getString(Param.COMPANYCITY));
        singleLoaction = new SingleLoaction();
        singleLoaction.setLocationListener(this);

    }

    @Override
    protected void initListener() {
        super.initListener();
        switchRight.setListener(this);
        switchLeft.setListener(this);
        popOrder.setOnDismissListener(this);
        popOrder.setListener(this);
        popCitys.setListener(this);

    }

    @Override
    protected Observable<CommonBean<PageBean<BreakbulkCompanyInfoBean>>> getListDatas() {
        return HttpBreakFactory.queryBreakbulkCompany(breakbulkCompanyInfoParam);
    }

    @Override
    protected XAdapter<BreakbulkCompanyInfoBean> getAdapter(List<BreakbulkCompanyInfoBean> datas) {
        if (rv instanceof RecycleViewSupportEmpty) {
            ((RecycleViewSupportEmpty) rv).setEmptyView(R.mipmap.icon_zwdd_260, "该城市正在对接中，敬请期待...");
        }
        return new XAdapter<BreakbulkCompanyInfoBean>(mContext, datas) {
            @Override
            public BaseHolder<BreakbulkCompanyInfoBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<BreakbulkCompanyInfoBean>(mContext, parent, R.layout.item_breakbulk_company) {
                    @Override
                    public void initView(View itemView, int position, final BreakbulkCompanyInfoBean data) {
                        super.initView(itemView, position, data);
                        ImageView img = itemView.findViewById(R.id.img);
                        ImageView img_phone = itemView.findViewById(R.id.img_phone);
                        ImageView img_chat = itemView.findViewById(R.id.img_chat);
                        TextView tv_point = itemView.findViewById(R.id.tv_point);
                        TextView tv_title = itemView.findViewById(R.id.tv_title);
                        TextView tv_address = itemView.findViewById(R.id.tv_address);

                        tv_title.setText(data.getCompanyname() == null ? "" : data.getCompanyname());
                        tv_address.setText(data.getWorkaddress() == null ? "" : data.getWorkaddress());
                        tv_point.setText(data.getTransportLine() == null ? "" : data.getTransportLine());
                        ImageLoader.getLoader().load(mContext, img, data.getRef1());
                        img_phone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CommonUtils.toDial(mContext, data.getContactPhone());
                            }
                        });
                        img_chat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ChactHelper.getHelper().startPriver(mContext, data.getId(), data.getContact());
                            }
                        });
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArouterUtils.getInstance()
                                        .builder(ArouterParamsBreakbulk.activity_breakbulk_company_detail)
                                        .withParcelable(Param.TRAN, data)
                                        .navigation(mContext);
                            }
                        });

                    }
                };
            }
        };
    }

    @Override
    public void onOpen(SwitchTextLayout switchTextLayout, View view) {
        changeState(switchTextLayout, true);
    }

    @Override
    public void onClose(SwitchTextLayout switchTextLayout, View view) {
        changeState(switchTextLayout, false);
    }


    private void changeState(final View view, boolean open) {
        if (view.getId() == R.id.switch_left) {
            switchLeft.setSelect(true);
            switchRight.setSelect(false);
            switchRight.closeSwitch();
            if (open) {
                if (areabean == null) {
                    LoginUtils.getNewAreas(mContext)
                            .compose(RxUtils.<Citys>getSchedulersObservableTransformer())
                            .subscribe(new BaseObserver<Citys>(this) {
                                @Override
                                public void onNext(Citys citys) {
                                    areabean = citys;
                                    popCitys.setProvince(citys.getShengs(), citys.getShis());
                                    popCitys.setCitys(citys.getShis());
                                    popCitys.show(view);
                                }


                            });

                } else {
                    popCitys.show(view);
                }
            } else {
                popCitys.dismiss();
            }

            popOrder.dismiss();

        } else if (view.getId() == R.id.switch_right) {
            switchRight.setSelect(true);
            switchLeft.setSelect(false);
            switchLeft.closeSwitch();
            popCitys.dismiss();
            if (open) {
                popOrder.show(view);
            } else {
                popOrder.dismiss();

            }

        }
    }

    /**
     * Popu dimiss 监听
     *
     * @param popu   当前popu
     * @param target 目标View
     */
    @Override
    public void onPopuDismsss(BasePopu popu, View target) {

    }

    @Override
    public void onPopuClick(OrderMainPop pop, View target, int position) {
        pop.dismiss();
        switchRight.closeSwitch();
        switchRight.setTitle(orders.get(position));
        breakbulkCompanyInfoParam.setSortType(String.valueOf(position + 1));
        if (position == 3 && TextUtils.isEmpty(breakbulkCompanyInfoParam.getDestination())) {
            startLoaction();
        } else {
            queryData(true);
        }
    }

    @Override
    public void onSelectCity(OrderCitysPop pop, View target, NewAreaBean data, int position) {
        pop.dismiss();
        switchLeft.closeSwitch();
        switchLeft.setTitle(data.getName());
        breakbulkCompanyInfoParam.setCity(data.getName());
        SharedPreferencesUtils.getInstance().putString(Param.COMPANYCITY, data.getName());
        queryData(true);
    }

    private void startLoaction() {

        PermissionUtils.applyMap((Activity) mContext, new PermissionUtils.onApplyPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean isAll) {
                if (!DeviceUtils.isOpenGps(mContext)) {
                    showAleart("为了能提供更好的服务，请打开GPS后重试");
                }
                showLoad();
                singleLoaction.startLoaction(mContext);

            }

            @Override
            public void noPermission(List<String> denied, boolean quick) {
            }
        });


    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        JLog.i("--------------------" + aMapLocation.getCity());
        this.aMapLocation = aMapLocation;
        breakbulkCompanyInfoParam.setDestination(aMapLocation.getLongitude() + "," + aMapLocation.getLatitude());
        if (TextUtils.isEmpty(breakbulkCompanyInfoParam.getCity())) {
            breakbulkCompanyInfoParam.setCity(aMapLocation.getCity());
            switchLeft.setTitle(breakbulkCompanyInfoParam.getCity());
        }
        queryData(true);

    }
}
