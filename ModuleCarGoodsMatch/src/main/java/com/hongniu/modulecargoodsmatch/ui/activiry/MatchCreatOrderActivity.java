package com.hongniu.modulecargoodsmatch.ui.activiry;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterParamsMatch;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.baselibrary.utils.PermissionUtils;
import com.hongniu.baselibrary.utils.PickerDialogUtils;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.MatchCarPreInforBean;
import com.hongniu.modulecargoodsmatch.entity.MatchCreatGoodsSourceParams;
import com.hongniu.modulecargoodsmatch.net.HttpMatchFactory;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.ItemView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @data 2019/5/19
 * @Author PING
 * @Description 创建车货匹配订单
 */
@Deprecated
@Route(path = ArouterParamsMatch.activity_match_creat_order)
public class MatchCreatOrderActivity extends BaseActivity implements View.OnClickListener, OnTimeSelectListener, OnOptionsSelectListener {
    private MatchCarPreInforBean preCarInfor;//车辆预加载信息

    private ItemView itemStartTime;         //发货时间
    private ItemView itemStartLocation;     //发货地点
    private ItemView itemEndLocation;       //收货地点
    private ItemView itemCargoName;          //货物名称
    private ItemView itemStartCarNum;          //货物名称
    private ItemView itemPrice;                 //运费
    private ItemView itemWeight;                 //货物重量
    private ItemView itemSize;                 //货物体积
    private ItemView itemCarSize;                 //车辆宽高
    private ItemView itemCarType;                 //车辆类型
    private EditText etRemark;                 //备注
    private TimePickerView timePickerView;
    private Button btNext;
    MatchCreatGoodsSourceParams params;
    private OptionsPickerView pickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_creat_order);
        setToolbarTitle("发布货源信息");

        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        itemStartTime = findViewById(R.id.item_start_time);
        itemStartLocation = findViewById(R.id.item_start_loaction);
        itemEndLocation = findViewById(R.id.item_end_loaction);
        itemStartCarNum = findViewById(R.id.item_start_car_num);
        itemCargoName = findViewById(R.id.item_cargo_name);
        itemPrice = findViewById(R.id.item_price);
        itemWeight = findViewById(R.id.item_weight);
        itemSize = findViewById(R.id.item_size);
        btNext = findViewById(R.id.bt_entry);
        etRemark = findViewById(R.id.et_remark);
        itemCarSize = findViewById(R.id.item_car_size);
        itemCarType = findViewById(R.id.item_car_type);


        Calendar startDate = Calendar.getInstance();

        Calendar endDate = Calendar.getInstance();
        endDate.set(startDate.get(Calendar.YEAR) + 2, 12, 31);
        timePickerView = PickerDialogUtils.creatTimePicker(mContext, this, new boolean[]{true, true, true, false, false, false})
                .setRangDate(startDate, endDate)
                .build();
        timePickerView.setKeyBackCancelable(false);//系统返回键监听屏蔽掉

        pickerDialog = PickerDialogUtils.initPickerDialog(mContext, this);

    }

    @Override
    protected void initData() {
        super.initData();
        params = new MatchCreatGoodsSourceParams();
        querPreloadInfor();
    }

    private void querPreloadInfor() {
        HttpMatchFactory
                .queryGoodCarInfor()
                .subscribe(new NetObserver<MatchCarPreInforBean>(this) {

                    @Override
                    public void doOnSuccess(MatchCarPreInforBean data) {
                        preCarInfor = data;
                    }
                })
        ;
    }

    @Override
    protected void initListener() {
        super.initListener();
        itemStartTime.setOnClickListener(this);
        itemStartLocation.setOnClickListener(this);
        itemEndLocation.setOnClickListener(this);
        btNext.setOnClickListener(this);
        itemCarSize.setOnClickListener(this);
        itemCarType.setOnClickListener(this);
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
        if (id == R.id.item_start_time) {
            timePickerView.show();
        } else if (id == R.id.item_start_loaction) {
            PermissionUtils.applyMap(this, new PermissionUtils.onApplyPermission() {
                @Override
                public void hasPermission(List<String> granted, boolean isAll) {
                    ArouterUtils.getInstance().builder(ArouterParamOrder.activity_map_loaction).withBoolean(Param.TRAN, true).navigation(mContext);
                }

                @Override
                public void noPermission(List<String> denied, boolean quick) {
                }
            });
        } else if (id == R.id.item_end_loaction) {
            PermissionUtils.applyMap(this, new PermissionUtils.onApplyPermission() {
                @Override
                public void hasPermission(List<String> granted, boolean isAll) {
                    ArouterUtils.getInstance().builder(ArouterParamOrder.activity_map_loaction).withBoolean(Param.TRAN, false).navigation(mContext);
                }

                @Override
                public void noPermission(List<String> denied, boolean quick) {
                }
            });
        } else if (id == R.id.bt_entry) {
            if (check()) {
                HttpMatchFactory.creatGoodSour(getParams())
                        .subscribe(new NetObserver<Object>(this) {
                            @Override
                            public void doOnSuccess(Object data) {
                                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                                ArouterUtils.getInstance().builder(ArouterParamsMatch.activity_match_my_record)
                                        .navigation(mContext);
                                finish();
                            }
                        });
                ;
            }
        } else if (id == R.id.item_car_type) {
            if (preCarInfor == null) {
                querPreloadInfor();
            } else {
                pickerDialog.setPicker(preCarInfor.getCarType());
                pickerDialog.show(v);
            }
        } else if (id == R.id.item_car_size) {
            if (preCarInfor == null) {
                querPreloadInfor();
            } else {
                pickerDialog.setPicker(preCarInfor.getCarLength());
                pickerDialog.show(v);
            }
        }
    }

    private MatchCreatGoodsSourceParams getParams() {
        params.startTime = itemStartTime.getTextCenter();
        params.departNum = itemStartCarNum.getTextCenter();
        params.goodName = itemCargoName.getTextCenter();
        params.goodVolume = itemSize.getTextCenter();
        params.goodWeight = itemWeight.getTextCenter();
        params.freightAmount = itemPrice.getTextCenter();
        params.carLength = itemCarSize.getTextCenter();
        params.carType = itemCarType.getTextCenter();
        params.remark = etRemark.getText().toString();

        return params;
    }

    private boolean check() {
        if (TextUtils.isEmpty(itemStartTime.getTextCenter())) {
            showAleart(itemStartTime.getTextCenterHide());
            return false;
        }
        ;
        if (TextUtils.isEmpty(itemStartLocation.getTextCenter())) {
            showAleart(itemStartLocation.getTextCenterHide());
            return false;
        }
        ;
        if (TextUtils.isEmpty(itemEndLocation.getTextCenter())) {
            showAleart(itemEndLocation.getTextCenterHide());
            return false;
        }


        if (TextUtils.isEmpty(itemStartCarNum.getTextCenter())) {
            showAleart(itemStartCarNum.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemCargoName.getTextCenter())) {
            showAleart(itemCargoName.getTextCenterHide());
            return false;
        }
        ;
        if (TextUtils.isEmpty(itemSize.getTextCenter())) {
            showAleart(itemSize.getTextCenterHide());
            return false;
        }
        ;
        if (TextUtils.isEmpty(itemWeight.getTextCenter())) {
            showAleart(itemWeight.getTextCenterHide());
            return false;
        }
        ;
        if (TextUtils.isEmpty(itemPrice.getTextCenter())) {
            showAleart(itemPrice.getTextCenterHide());
            return false;
        }
        ;
        return true;
    }

    @Override
    public void onTimeSelect(Date date, View v) {
        itemStartTime.setTextCenter(ConvertUtils.formatTime(date, "yyyy-MM-dd"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartEvent(Event.StartLoactionEvent startLoactionEvent) {
        if (startLoactionEvent != null && startLoactionEvent.t != null) {
            String title = Utils.dealPioPlace(startLoactionEvent.t);
            itemStartLocation.setTextCenter(title);
            params.setStartPlaceY(startLoactionEvent.t.getLatLonPoint().getLatitude() + "");
            params.setStartPlaceX(startLoactionEvent.t.getLatLonPoint().getLongitude() + "");
            params.setStartPlaceInfo(title);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEndEvent(Event.EndLoactionEvent endLoactionEvent) {
        if (endLoactionEvent != null && endLoactionEvent.t != null) {
            String title = Utils.dealPioPlace(endLoactionEvent.t);
            itemEndLocation.setTextCenter(title);
            params.setDestinationY(endLoactionEvent.t.getLatLonPoint().getLatitude() + "");
            params.setDestinationX(endLoactionEvent.t.getLatLonPoint().getLongitude() + "");
            params.setDestinationInfo(title);
        }
    }

    /**
     * 选择器被选择的时候
     *
     * @param options1
     * @param options2
     * @param options3
     * @param v
     */
    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        if (v == itemCarSize) {
            itemCarSize.setTextCenter(preCarInfor.getCarLength().get(options1));
        } else if (v == itemCarType) {
            itemCarType.setTextCenter(preCarInfor.getCarType().get(options1));

        }
    }
}
