package com.hongniu.modulelogin.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.model.AMapCarInfo;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CarTypeBean;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.utils.PickerDialogUtils;
import com.hongniu.modulelogin.R;
import com.hongniu.modulelogin.entity.LoginCarInforBean;
import com.hongniu.modulelogin.net.HttpLoginFactory;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.widget.ItemView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;


/**
 * @data 2018/12/6  货车导航车辆添加界面
 * @Author PING
 * @Description 货车导航时候，需要填写修改的货车信息
 */
@Route(path = ArouterParamLogin.activity_login_truck_infor)
public class LoginTruckInforActivity extends BaseActivity implements View.OnClickListener, OnOptionsSelectListener {

    private ItemView itemCarNumber;   //车牌号
    private ItemView itemCarType;    //车辆类型
    private ItemView itemCarWeightCount;///总重量
    private ItemView itemCarWeight;//核定载重
    private ItemView itemCarLength;//车场
    private ItemView itemCarWidth;//车款
    private ItemView itemCarHeight;//车高
    private ItemView itemCarAxle;//轴数

    private Button bt;
    private String carNumber;

    public List<CarTypeBean> carTypes;//车辆类型
    private OptionsPickerView<CarTypeBean> typeDialog;

    Poi start;
    Poi end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_truck_infor);

        initView();
        initData();
        initListener();

    }

    @Override
    protected void initView() {
        super.initView();
        itemCarNumber = findViewById(R.id.item_car_number);
        itemCarType = findViewById(R.id.item_car_type);
        itemCarWeightCount = findViewById(R.id.item_car_count_weight);
        itemCarWeight = findViewById(R.id.item_car_weight);
        itemCarLength = findViewById(R.id.item_car_length);
        itemCarWidth = findViewById(R.id.item_car_width);
        itemCarHeight = findViewById(R.id.item_car_height);
        itemCarAxle = findViewById(R.id.item_car_axle_count);
        bt = findViewById(R.id.bt_save);


        typeDialog = PickerDialogUtils.creatPickerDialog(mContext, this)
                .setTitleText("请选择被车辆类型")
                .setSubmitColor(Color.parseColor("#48BAF3"))
                .build();
    }

    @Override
    protected void initData() {
        super.initData();
        setToolbarTitle("货车导航车辆添加");

        carNumber = getIntent().getStringExtra(Param.TRAN);
        start=getIntent().getParcelableExtra("start");
        end=getIntent().getParcelableExtra("end");

        itemCarNumber.setTextCenter(carNumber);
        itemCarNumber.setEnabled(false);




        Observable.zip(HttpLoginFactory.queyTruckTypes(), HttpLoginFactory.queyCarDetailInfor(carNumber), new BiFunction<CommonBean<List<CarTypeBean>>, CommonBean<LoginCarInforBean>, CommonBean<LoginCarInforBean>>() {
            @Override
            public CommonBean<LoginCarInforBean> apply(CommonBean<List<CarTypeBean>> listCommonBean, CommonBean<LoginCarInforBean> loginCarInforBeanCommonBean) throws Exception {
                carTypes=listCommonBean.getData();
                typeDialog.setPicker(carTypes);
                return loginCarInforBeanCommonBean;
            }
        })
        .subscribe(new NetObserver<LoginCarInforBean>(this) {
            @Override
            public void doOnSuccess(LoginCarInforBean data) {
                itemCarNumber.setTextCenter(data.getCarNumber());

                if (!CommonUtils.isEmptyCollection(carTypes)){
                    for (CarTypeBean carType : carTypes) {
                        if (carType.getId().equalsIgnoreCase(data.getVehicleSize())){
                            itemCarType.setTextCenter(carType.getCarType());
                            break;
                        }
                    }
                }

                itemCarWeightCount.setTextCenter(data.getVehicleLoad());
                itemCarWeight.setTextCenter(data.getVehicleWeight());
                itemCarLength.setTextCenter(data.getVehicleLength());
                itemCarWidth.setTextCenter(data.getVehicleWidth());
                itemCarHeight.setTextCenter(data.getVehicleHeight());
                itemCarAxle.setTextCenter(data.getVehicleAxis());
                itemCarWeightCount.getEtCenter().requestFocus();
            }
        })
        ;



    }

    @Override
    protected void initListener() {
        super.initListener();
        bt.setOnClickListener(this);
        itemCarType.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_save) {

            if (check()){
                final LoginCarInforBean vaule = getVaule();
                HttpLoginFactory.upTruckInfor(vaule)
                    .subscribe(new NetObserver<String>(this) {
                        @Override
                        public void doOnSuccess(String data) {

                            AMapCarInfo aMapCarInfo = new AMapCarInfo();
                            aMapCarInfo.setCarType(vaule.getCarType());//设置车辆类型，0小车，1货车
                            aMapCarInfo.setCarNumber(vaule.getCarNumber());//设置车辆的车牌号码. 如:京DFZ239,京ABZ239
//                            aMapCarInfo.setVehicleSize("4");// * 设置货车的等级
                            aMapCarInfo.setVehicleLoad(vaule.getVehicleWeight());//设置货车的载重，单位：吨。
                            aMapCarInfo.setVehicleWeight(vaule.getVehicleLoad());//设置货车的自重
                            aMapCarInfo.setVehicleLength(vaule.getVehicleLength());//  * 设置货车的最大长度，单位：米。
                            aMapCarInfo.setVehicleWidth(vaule.getVehicleWidth());//设置货车的最大宽度，单位：米。 如:1.8，1.5等等。
                            aMapCarInfo.setVehicleHeight(vaule.getVehicleHeight());//设置货车的高度，单位：米。
                            aMapCarInfo.setVehicleAxis(vaule.getVehicleAxis());//设置货车的轴数
                            aMapCarInfo.setVehicleLoadSwitch(true);//设置车辆的载重是否参与算路
                            aMapCarInfo.setRestriction(true);//设置是否躲避车辆限行。

                            AmapNaviParams naviParams = new AmapNaviParams(start, null, end, AmapNaviType.DRIVER);
                            naviParams.setCarInfo(aMapCarInfo);

                            AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), naviParams, null);
                            finish();


                        }
                    })
                ;



            }

        } else if (v.getId() == R.id.item_car_type) {
            DeviceUtils.closeSoft(this);
            if (CommonUtils.isEmptyCollection(carTypes)) {
                HttpLoginFactory.queyTruckTypes( )
                        .subscribe(new NetObserver<List<CarTypeBean>>(this) {

                            @Override
                            public void doOnSuccess(List<CarTypeBean> data) {
                                carTypes=data;
                                typeDialog.setPicker(carTypes);
                                typeDialog.show();
                            }
                        });
            }else {
                typeDialog.show();
            }

        }
    }


    private LoginCarInforBean getVaule(){
        LoginCarInforBean bean=new LoginCarInforBean();
        bean.setCarNumber(itemCarNumber.getTextCenter());
        if (!CommonUtils.isEmptyCollection(carTypes)){
            for (CarTypeBean carType : carTypes) {
                if (carType.getCarType().equalsIgnoreCase(itemCarType.getTextCenter())){
                    bean.setVehicleSize(carType.getId());
                    break;
                }
            }
        }
        bean.setVehicleLoad(itemCarWeightCount.getTextCenter());
        bean.setVehicleWeight(itemCarWeight.getTextCenter());
        bean.setVehicleLength(itemCarLength.getTextCenter());
        bean.setVehicleWidth(itemCarWidth.getTextCenter());
        bean.setVehicleHeight(itemCarHeight.getTextCenter());
        bean.setVehicleAxis(itemCarAxle.getTextCenter());
        return bean;
    }







    public boolean check() {
        if (TextUtils.isEmpty(itemCarNumber.getTextCenter())) {
            showAleart(itemCarNumber.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemCarType.getTextCenter())) {
            showAleart(itemCarType.getTextCenterHide());
            return false;
        }

        if (TextUtils.isEmpty(itemCarWeightCount.getTextCenter())) {
            showAleart(itemCarWeightCount.getTextCenterHide());
            return false;
        }

        if (TextUtils.isEmpty(itemCarWeight.getTextCenter())) {
            showAleart(itemCarWeight.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemCarLength.getTextCenter())) {
            showAleart(itemCarLength.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemCarWidth.getTextCenter())) {
            showAleart(itemCarWidth.getTextCenterHide());
            return false;
        }    if (TextUtils.isEmpty(itemCarHeight.getTextCenter())) {
            showAleart(itemCarHeight.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemCarAxle.getTextCenter())) {
            showAleart(itemCarAxle.getTextCenterHide());
            return false;
        }


        return true;
    }


    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        itemCarType.setTextCenter(carTypes.get(options1).getCarType());
        itemCarWeightCount.getEtCenter().requestFocus();
    }
}
