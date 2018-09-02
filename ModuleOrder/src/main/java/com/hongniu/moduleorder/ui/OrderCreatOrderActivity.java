package com.hongniu.moduleorder.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.baselibrary.utils.PermissionUtils;
import com.hongniu.baselibrary.utils.PickerDialogUtils;
import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.entity.OrderCarNumbean;
import com.hongniu.moduleorder.entity.OrderCreatParamBean;
import com.hongniu.moduleorder.entity.OrderDriverPhoneBean;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.hongniu.moduleorder.widget.CarNumPop;
import com.sang.common.event.BusFactory;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.widget.ItemView;
import com.sang.common.widget.dialog.BottomAlertDialog;
import com.sang.common.widget.dialog.builder.BottomAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 创建订单
 */
@Route(path = ArouterParamOrder.activity_order_create)
public class OrderCreatOrderActivity extends BaseActivity implements View.OnClickListener, OnTimeSelectListener, CarNumPop.onItemClickListener{


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what==0) {
                getCarNum();
            }else {
                getDriverInfor();
            }
        }
    };


    private ItemView itemStartTime;         //发货时间
    private ItemView itemStartLocation;     //发货地点
    private ItemView itemEndLocation;       //收货地点
    private ItemView itemStartCarNum;       //发车编号
    private ItemView itemCargoName;          //货物名称
    private ItemView itemCargoSize;         //货物面积
    private ItemView itemCargoWeight;         //货物重量
    private ItemView itemPrice;                 //运费
    private ItemView itemCarNum;         //车牌号
    private ItemView itemCarPhone;         //车主手机号
    private ItemView itemCarName;         //车主姓名
    private ItemView itemDriverName;         //司机姓名
    private ItemView itemDriverPhone;         //司机手机
    private TimePickerView timePickerView;

    private Button btSave;

    private CarNumPop<OrderCarNumbean> pop;
    private CarNumPop<OrderDriverPhoneBean> driverPop;
    private OrderCreatParamBean paramBean = new OrderCreatParamBean();
    List<OrderCarNumbean> carNumbeans = new ArrayList<>();
    List<OrderDriverPhoneBean> driverBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_creat_order);
        setToolbarTitle(getString(R.string.order_create_order));
        initView();
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
        itemCargoSize = findViewById(R.id.item_cargo_size);
        itemCargoWeight = findViewById(R.id.item_cargo_weight);
        itemPrice = findViewById(R.id.item_price);
        itemCarNum = findViewById(R.id.item_car_num);
        itemCarPhone = findViewById(R.id.item_car_phone);
        itemCarName = findViewById(R.id.item_car_name);
        itemDriverName = findViewById(R.id.item_driver_name);
        itemDriverPhone = findViewById(R.id.item_driver_phone);
        btSave = findViewById(R.id.bt_entry);
        timePickerView = PickerDialogUtils.initTimePicker(mContext, this, new boolean[]{true, true, true, false, false, false});
        pop = new CarNumPop<OrderCarNumbean>(mContext);
        driverPop = new CarNumPop<OrderDriverPhoneBean>(mContext);

    }

    @Override
    protected void initListener() {
        super.initListener();
        itemStartTime.setOnClickListener(this);
        itemStartLocation.setOnClickListener(this);
        itemEndLocation.setOnClickListener(this);
        btSave.setOnClickListener(this);
        itemCarNum.getEtCenter().addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.removeMessages(0);
                handler.removeMessages(1);
                if (!TextUtils.isEmpty(itemCarNum.getTextCenter()) && show) {
                    handler.sendEmptyMessageDelayed(0, 300);
                }
                show = true;
            }
        });
        itemDriverPhone.getEtCenter().addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.removeMessages(1);
                handler.removeMessages(0);
                if (CommonUtils.isPhone(itemDriverPhone.getTextCenter())&&show) {
                    handler.sendEmptyMessageDelayed(1, 300);
                }
                show = true;
            }
        });
        pop.setListener(this);
        driverPop.setListener(this);

    }

    private Disposable carDisposable;

    /**
     * 获取牌相关信息
     */
    private void getCarNum() {
        String carNum = itemCarNum.getTextCenter();
        if (carDisposable != null) {
            carDisposable.dispose();
        }
        if (TextUtils.isEmpty(carNum)) {
            return;
        }
        HttpOrderFactory.getCarNum(carNum)
                .subscribe(new NetObserver<List<OrderCarNumbean>>(null) {
                    @Override
                    public void doOnSuccess(List<OrderCarNumbean> data) {
                        carNumbeans.clear();
                        carNumbeans.addAll(data);
                        pop.upData(itemCarNum.getTextCenter(), carNumbeans);
                        pop.show(itemCarNum);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        carDisposable = d;
                    }


                });
    }

    /**
     * 获取牌相关信息
     */
    private void getDriverInfor() {
        String carNum = itemDriverPhone.getTextCenter();
        if (carDisposable != null) {
            carDisposable.dispose();
        }
        if (TextUtils.isEmpty(carNum)) {
            return;
        }
        HttpOrderFactory.getDriverPhone(carNum)
                .subscribe(new NetObserver<List<OrderDriverPhoneBean>>(null) {
                    @Override
                    public void doOnSuccess(List<OrderDriverPhoneBean> data) {
                        if (data!=null&&!data.isEmpty()) {
                            OrderDriverPhoneBean bean = data.get(0);
                            show=false;
                            itemDriverPhone.setTextCenter(bean.getMobile());
                            itemDriverName.setTextCenter(bean.getContact());
                            itemDriverName.getEtCenter().requestFocus();
                        }
//                        driverBeans.clear();
//                        driverBeans.addAll(data);
//
//                        driverPop.upData(itemDriverPhone.getTextCenter(), driverBeans);
//                        driverPop.show(itemDriverPhone);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        carDisposable = d;
                    }


                });
    }

    @Override
    public void onClick(View v) {
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
                getValue();
                HttpOrderFactory.creatOrder(paramBean)
                        .subscribe(new NetObserver<OrderDetailBean>(this) {
                            @Override
                            public void doOnSuccess(OrderDetailBean data) {

                                OrderEvent.PayOrder payOrder = new OrderEvent.PayOrder();
                                payOrder.insurance = false;
                                payOrder.money = Float.parseFloat(paramBean.getMoney());
                                payOrder.orderID = data.getId();
                                payOrder.orderNum = data.getOrderNum();
                                BusFactory.getBus().postSticky(payOrder);
                                ArouterUtils.getInstance()
                                        .builder(ArouterParamOrder.activity_order_pay)
                                        .navigation(mContext);

                                BusFactory.getBus().postSticky(new Event.UpRoale(OrderDetailItemControl.RoleState.CARGO_OWNER));
                                finish();

                            }
                        });

            }
        }
    }

    private void getValue() {

        paramBean.setDepartNum(itemStartCarNum.getTextCenter());
        paramBean.setGoodName(itemCargoName.getTextCenter());
        paramBean.setGoodVolume(itemCargoSize.getTextCenter());
        paramBean.setGoodWeight(itemCargoWeight.getTextCenter());
        paramBean.setMoney(itemPrice.getTextCenter());
        paramBean.setCarNum(itemCarNum.getTextCenter());
        paramBean.setOwnerMobile(itemCarPhone.getTextCenter());
        paramBean.setOwnerName(itemCarName.getTextCenter());
        paramBean.setDriverName(itemDriverName.getTextCenter());
        paramBean.setDriverMobile(itemDriverPhone.getTextCenter());

    }

    @Override
    public void onTimeSelect(Date date, View v) {

        itemStartTime.setTextCenter(ConvertUtils.formatTime(date, "yyyy年MM月dd日"));
        paramBean.setDeliveryDate(ConvertUtils.formatTime(date, "yyyy-MM-dd"));

    }

    @Override
    protected boolean getUseEventBus() {
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartEvent(OrderEvent.StartLoactionEvent startLoactionEvent) {
        if (startLoactionEvent != null && startLoactionEvent.t != null) {
            itemStartLocation.setTextCenter(startLoactionEvent.t.getTitle());
            paramBean.setStartLatitude( startLoactionEvent.t.getLatLonPoint().getLatitude());
            paramBean.setStartLongitude( startLoactionEvent.t.getLatLonPoint().getLongitude());
            paramBean.setStartPlaceInfo(startLoactionEvent.t.getTitle());
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEndEvent(OrderEvent.EndLoactionEvent endLoactionEvent) {
        if (endLoactionEvent != null && endLoactionEvent.t != null) {
            itemEndLocation.setTextCenter(endLoactionEvent.t.getTitle());
            paramBean.setDestinationLatitude( endLoactionEvent.t.getLatLonPoint().getLatitude());
            paramBean.setDestinationLongitude( endLoactionEvent.t.getLatLonPoint().getLongitude());
            paramBean.setDestinationInfo(endLoactionEvent.t.getTitle());
        }
    }

    @Override
    public void onBackPressed() {
        new BottomAlertBuilder()
                .setDialogTitle("确认要放弃下单吗？")
                .setTopClickListener(new DialogControl.OnButtonTopClickListener() {
                    @Override
                    public void onTopClick(View view, DialogControl.IBottomDialog dialog) {
                        dialog.dismiss();
                        finish();
                    }

                })
                .setBottomClickListener(new DialogControl.OnButtonBottomClickListener() {
                    @Override
                    public void onBottomClick(View view, DialogControl.IBottomDialog dialog) {
                        dialog.dismiss();

                    }

                }).creatDialog(new BottomAlertDialog(mContext)).show();

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
        ;
        if (TextUtils.isEmpty(itemStartCarNum.getTextCenter())) {
            showAleart(itemStartCarNum.getTextCenterHide());
            return false;
        }
        ;
        if (TextUtils.isEmpty(itemCargoName.getTextCenter())) {
            showAleart(itemCargoName.getTextCenterHide());
            return false;
        }
        ;
        if (TextUtils.isEmpty(itemCargoSize.getTextCenter())) {
            showAleart(itemCargoSize.getTextCenterHide());
            return false;
        }
        ;
        if (TextUtils.isEmpty(itemCargoWeight.getTextCenter())) {
            showAleart(itemCargoWeight.getTextCenterHide());
            return false;
        }
        ;
        if (TextUtils.isEmpty(itemPrice.getTextCenter())) {
            showAleart(itemPrice.getTextCenterHide());
            return false;
        }



        if (TextUtils.isEmpty(itemCarNum.getTextCenter())) {
            showAleart(itemCarNum.getTextCenterHide());
            return false;
        }else if (!  CommonUtils.carNumMatches(itemCarNum.getTextCenter())){
            showAleart(getString(R.string.carnum_error));
            return false;
        }
        ;
        if (TextUtils.isEmpty(itemCarPhone.getTextCenter())) {
            showAleart(itemCarPhone.getTextCenterHide());
            return false;
        }else if (!  CommonUtils.isPhone(itemCarPhone.getTextCenter())){
            showAleart(getString(R.string.phone_error));
            return false;
        }
        ;
        if (TextUtils.isEmpty(itemCarName.getTextCenter())) {
            showAleart(itemCarName.getTextCenterHide());
            return false;
        }
        ;
        if (TextUtils.isEmpty(itemDriverName.getTextCenter())) {
            showAleart(itemDriverName.getTextCenterHide());
            return false;
        }
        ;
        if (TextUtils.isEmpty(itemDriverPhone.getTextCenter())) {
            showAleart(itemDriverPhone.getTextCenterHide());
            return false;
        }else if (!  CommonUtils.isPhone(itemDriverPhone.getTextCenter())){
            showAleart(getString(R.string.phone_error));
            return false;
        }
        ;

        return true;


    }


    private boolean show = true;

    @Override
    public void onItemClick(View tragetView, int position, Object data) {
        pop.dismiss();
        driverPop.dismiss();
        show = false;
        if (tragetView!=null){
            if (tragetView.getId()==R.id.item_car_num&&data instanceof OrderCarNumbean){
                OrderCarNumbean bean= (OrderCarNumbean) data;
                itemCarNum.setTextCenter(bean.getCarNumber());
                itemCarPhone.setTextCenter(bean.getContactMobile());
                itemCarName.setTextCenter(bean.getContactName());
            }else if (tragetView.getId()==R.id.item_driver_phone&&data instanceof OrderDriverPhoneBean){
                OrderDriverPhoneBean bean= (OrderDriverPhoneBean) data;
                itemDriverPhone.setTextCenter(bean.getMobile());
                itemDriverName.setTextCenter(bean.getContact());
            }
        }

    }

    @Override
    public void onDissmiss() {
        show = false;
    }

}
