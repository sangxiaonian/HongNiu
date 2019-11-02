package com.hongniu.modulecargoodsmatch.ui.activiry;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsMatch;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.MatchCarTypeInfoBean;
import com.hongniu.modulecargoodsmatch.entity.MatchCountFareBean;
import com.hongniu.modulecargoodsmatch.entity.MatchCreatOrderParams;
import com.hongniu.modulecargoodsmatch.entity.MatchOrderTranMapBean;
import com.hongniu.modulecargoodsmatch.entity.MatchQueryCountFareParam;
import com.hongniu.modulecargoodsmatch.entity.TranMapBean;
import com.hongniu.modulecargoodsmatch.net.HttpMatchFactory;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.ItemView;

/**
 * @data 2019/5/19
 * @Author PING
 * @Description 创建车货匹配订单
 */
@Route(path = ArouterParamsMatch.activity_match_creat_order)
public class MatchCreatOrderActivity extends BaseActivity implements View.OnClickListener {


    MatchCreatOrderParams creatOrderParams = new MatchCreatOrderParams();

    private ItemView item_name;
    private ItemView item_phone;
    private ItemView item_remark;
    private ItemView item_car_type;
    private ItemView item_start_price;
    private ItemView item_price;
    private ItemView item_forecast_price;
    private TextView tv_start_address;
    private TextView tv_start_address_dess;
    private TextView tv_end_address;
    private TextView tv_end_address_dess;
    private TextView tv_time;

    private Button btNext;
    private MatchOrderTranMapBean params;//参数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_creat_order);
        setToolbarTitle("确定订单");
        params = getIntent().getParcelableExtra(Param.TRAN);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        item_name = findViewById(R.id.item_name);
        item_phone = findViewById(R.id.item_phone);
        item_remark = findViewById(R.id.item_remark);
        item_car_type = findViewById(R.id.item_car_type);
        item_start_price = findViewById(R.id.item_start_price);
        item_price = findViewById(R.id.item_price);
        item_forecast_price = findViewById(R.id.item_forecast_price);
        btNext = findViewById(R.id.bt_entry);
        tv_start_address = findViewById(R.id.tv_start_address);
        tv_start_address_dess = findViewById(R.id.tv_start_address_dess);
        tv_end_address = findViewById(R.id.tv_end_address);
        tv_end_address_dess = findViewById(R.id.tv_end_address_dess);
        tv_time = findViewById(R.id.tv_time);
    }

    @Override
    protected void initData() {
        super.initData();
        if (params != null) {
            if (!TextUtils.isEmpty(params.getTime())) {
                tv_time.setText(params.getTime());
            }
            TranMapBean start = params.getStart();
            if (start != null && start.getPoiItem() != null) {
                String placeInfor = Utils.dealPioPlace(start.getPoiItem());
                tv_start_address.setText(start.getPoiItem().getTitle());
                tv_start_address.setTextColor(getResources().getColor(R.color.color_title_dark));
                tv_start_address_dess.setText(placeInfor);
                tv_start_address_dess.setVisibility(View.VISIBLE);
                item_name.setTextCenter(start.getName());
                item_phone.setTextCenter(start.getPhone());

                creatOrderParams.setStartPlaceLat(params.getStart().getPoiItem().getLatLonPoint().getLatitude() + "");
                creatOrderParams.setStartPlaceLon(params.getStart().getPoiItem().getLatLonPoint().getLongitude() + "");
                creatOrderParams.setStartPlaceInfo(placeInfor + (TextUtils.isEmpty(start.getAddress()) ? "" : start.getAddress()));


            }

            //发货
            TranMapBean end = params.getEnd();
            if (end != null && end.getPoiItem() != null) {
                String placeInfor = Utils.dealPioPlace(start.getPoiItem());
                tv_end_address.setText(start.getPoiItem().getTitle());
                tv_end_address.setTextColor(getResources().getColor(R.color.color_title_dark));
                tv_end_address_dess.setText(placeInfor);
                tv_end_address_dess.setVisibility(View.VISIBLE);

                creatOrderParams.setDestinationInfo(placeInfor + (TextUtils.isEmpty(end.getAddress()) ? "" : end.getAddress()));
                creatOrderParams.setDestinationLat(params.getEnd().getPoiItem().getLatLonPoint().getLatitude() + "");
                creatOrderParams.setDestinationLon(params.getEnd().getPoiItem().getLatLonPoint().getLongitude() + "");
                creatOrderParams.setReceiverName(TextUtils.isEmpty(end.getName())?null:end.getName());
                creatOrderParams.setReceiverMobile(TextUtils.isEmpty(end.getPhone())?null:end.getPhone());

            }

            MatchCarTypeInfoBean carTypeInfoBean = params.getCarTypeInfoBean();
            if (carTypeInfoBean != null) {
                item_car_type.setTextCenter(carTypeInfoBean.getCarType());
                item_start_price.setTextCenter(ConvertUtils.changeFloat(carTypeInfoBean.getStartPrice(), 2) + "元");
                item_price.setTextCenter(ConvertUtils.changeFloat(carTypeInfoBean.getExceedMileagePrice(), 2) + "元");

                creatOrderParams.setCartypeId(carTypeInfoBean.getCarType());

            }
            creatOrderParams.setDepartureTime(params.getTime());

            queryCardInfor(params);

        }

    }

    private void queryCardInfor(MatchOrderTranMapBean params) {
        MatchQueryCountFareParam bean = new MatchQueryCountFareParam();
        bean.setCartypeId(params.getCarTypeInfoBean().getId());
        bean.setStartPlaceLat(params.getStart().getPoiItem().getLatLonPoint().getLatitude() + "");
        bean.setStartPlaceLon(params.getStart().getPoiItem().getLatLonPoint().getLongitude() + "");
        bean.setDestinationLat(params.getEnd().getPoiItem().getLatLonPoint().getLatitude() + "");
        bean.setDestinationLon(params.getEnd().getPoiItem().getLatLonPoint().getLongitude() + "");
        HttpMatchFactory
                .queryCountFare(bean)
                .subscribe(new NetObserver<MatchCountFareBean>(this) {

                    @Override
                    public void doOnSuccess(MatchCountFareBean data) {
                        item_forecast_price.setTextCenter(data.getEstimateFare());
                    }
                })
        ;
    }

    @Override
    protected void initListener() {
        super.initListener();
        btNext.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        DeviceUtils.closeSoft(this);
        int id = v.getId();
        if (id == R.id.bt_entry) {
            creatOrderParams.setShipperName(TextUtils.isEmpty(item_name.getTextCenter())?"":item_name.getTextCenter());
            creatOrderParams.setShipperMobile(TextUtils.isEmpty(item_phone.getTextCenter())?"":item_phone.getTextCenter());
            creatOrderParams.setRemark(TextUtils.isEmpty(item_remark.getTextCenter())?"":item_remark.getTextCenter());

            HttpMatchFactory.matchCreatOrder(creatOrderParams)
                .subscribe(new NetObserver<MatchCreatOrderParams>(this) {
                    @Override
                    public void doOnSuccess(MatchCreatOrderParams data) {
                        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                        ArouterUtils.getInstance().builder(ArouterParamsMatch.activity_match_my_order).navigation(mContext);
                        finish();
                    }
                })
            ;

        }
    }




}
