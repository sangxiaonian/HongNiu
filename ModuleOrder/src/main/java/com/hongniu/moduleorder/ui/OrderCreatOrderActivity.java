package com.hongniu.moduleorder.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.hongniu.baselibrary.entity.UpImgData;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.baselibrary.utils.PermissionUtils;
import com.hongniu.baselibrary.utils.PickerDialogUtils;
import com.hongniu.baselibrary.utils.PictureSelectorUtils;
import com.hongniu.baselibrary.utils.UpLoadImageUtils;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.baselibrary.widget.order.CommonOrderUtils;
import com.hongniu.baselibrary.widget.order.OrderDetailItem;
import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OnItemClickListener;
import com.hongniu.moduleorder.control.OnItemDeletedClickListener;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.entity.OrderCarNumbean;
import com.hongniu.moduleorder.entity.OrderCreatParamBean;
import com.hongniu.moduleorder.entity.OrderDriverPhoneBean;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.hongniu.moduleorder.ui.adapter.PicAdapter;
import com.hongniu.moduleorder.widget.CarNumPop;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.sang.common.event.BusFactory;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.ItemView;
import com.sang.common.widget.dialog.BottomAlertDialog;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.BottomAlertBuilder;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 创建订单
 */
@Route(path = ArouterParamOrder.activity_order_create)
public class OrderCreatOrderActivity extends BaseActivity implements View.OnClickListener, OnTimeSelectListener, CarNumPop.onItemClickListener, OnItemDeletedClickListener<LocalMedia>, OnItemClickListener<LocalMedia>, UpLoadImageUtils.OnUpLoadListener {


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {
                getCarNum();
            } else {
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

    private RecyclerView rv;
    private CarNumPop<OrderCarNumbean> pop;
    private OrderCreatParamBean paramBean = new OrderCreatParamBean();
    List<OrderCarNumbean> carNumbeans = new ArrayList<>();
    PicAdapter adapter;
    List<LocalMedia> pics;
    //是否是修改订单
    private boolean changeOrder;
    private OrderDetailBean orderDetailBean;

    UpLoadImageUtils imageUtils = new UpLoadImageUtils(Param.GOODS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_creat_order);
        setToolbarTitle(getString(R.string.order_create_order));
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
        itemCargoSize = findViewById(R.id.item_cargo_size);
        itemCargoWeight = findViewById(R.id.item_cargo_weight);
        itemPrice = findViewById(R.id.item_price);
        itemCarNum = findViewById(R.id.item_car_num);
        itemCarPhone = findViewById(R.id.item_car_phone);
        itemCarName = findViewById(R.id.item_car_name);
        itemDriverName = findViewById(R.id.item_driver_name);
        itemDriverPhone = findViewById(R.id.item_driver_phone);
        btSave = findViewById(R.id.bt_entry);
//        timePickerView = PickerDialogUtils.initTimePicker(mContext, this, new boolean[]{true, true, true, false, false, false});
        rv = findViewById(R.id.rv_pic);

        Calendar startDate = Calendar.getInstance();

        Calendar endDate = Calendar.getInstance();
        endDate.set(startDate.get(Calendar.YEAR) + 2, 12, 31);
        timePickerView = PickerDialogUtils.creatTimePicker(mContext, this, new boolean[]{true, true, true, false, false, false})
                .setRangDate(startDate, endDate)
                .build();
        timePickerView.setKeyBackCancelable(false);//系统返回键监听屏蔽掉


        pop = new CarNumPop<OrderCarNumbean>(mContext);

    }

    @Override
    protected void initData() {
        super.initData();
        GridLayoutManager manager = new GridLayoutManager(mContext, 4);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        pics = new ArrayList<>();
        adapter = new PicAdapter(mContext, pics);
        adapter.setDeletedClickListener(this);
        adapter.addFoot(new PeakHolder(mContext, rv, R.layout.order_item_creat_order_img_foot) {
            @Override
            public void initView(int position) {
                super.initView(position);
                getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (pics.size() >= Param.IMAGECOUNT) {
                            ToastUtils.getInstance().show("已达到图片最大数量");
                        } else {
                            PictureSelectorUtils.showPicture((Activity) mContext, pics);
                        }
                    }
                });
            }
        });
        rv.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        imageUtils.setOnUpLoadListener(this);
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
                if (CommonUtils.isPhone(itemDriverPhone.getTextCenter()) && show) {
                    handler.sendEmptyMessageDelayed(1, 300);
                }
                show = true;
            }
        });
        pop.setListener(this);

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
                        if (data != null && !data.isEmpty()) {
                            OrderDriverPhoneBean bean = data.get(0);
                            show = false;
                            itemDriverPhone.setTextCenter(bean.getMobile());
                            itemDriverName.setTextCenter(bean.getContact());
                        }
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
        DeviceUtils.closeSoft(this);
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
                if (imageUtils.isFinish()) {
                    // 如果没有更改过图片，则不上传
                    upDate();
                } else {
                    creatDialog(imageUtils.unFinishCount() + "张图片上传中", "是否放弃上传？", "确定放弃", "继续上传")
                            .setLeftClickListener(new DialogControl.OnButtonLeftClickListener() {
                                @Override
                                public void onLeftClick(View view, DialogControl.ICenterDialog dialog) {
                                    dialog.dismiss();
                                    upDate();
                                }
                            })
                            .creatDialog(new CenterAlertDialog(mContext))
                            .show();
                }

            }
        }
    }

    /**
     * 向服务器提交数据
     */
    private void upDate() {
        List<String> result = imageUtils.getResult();
        if (result.size() == 0 && !CommonUtils.isEmptyCollection(pics)) {
            result = null;
        }
        if (!changeOrder) {
            HttpOrderFactory.creatOrder(result, paramBean)
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
        } else {
            HttpOrderFactory.changeOrder(result, paramBean)
                    .subscribe(new NetObserver<OrderDetailBean>(this) {
                        @Override
                        public void doOnSuccess(OrderDetailBean data) {
                            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                            finish();
                        }
                    });
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
        if (changeOrder) { //如果是修改订单界面,清除不可修改部分的内容
            if (!itemStartTime.isEnabled()) {//不可更改
                paramBean.setDeliveryDate(null);
            }
            if (!itemStartLocation.isEnabled()) {//发货地点不可更改
                paramBean.setStartLatitude(null);
                paramBean.setStartLongitude(null);
                paramBean.setStartPlaceInfo(null);
            }
            if (!itemEndLocation.isEnabled()) {//收货地点不可更改
                paramBean.setDestinationLatitude(null);
                paramBean.setDestinationLongitude(null);
                paramBean.setDestinationInfo(null);
            }
            if (!itemStartCarNum.isEnabled()) {//发货编号不可更改
                paramBean.setDepartNum(null);
            }
            if (!itemCargoName.isEnabled()) {//货物名称
                paramBean.setGoodName(null);

            }
            if (!itemCargoSize.isEnabled()) {//货物体积
                paramBean.setGoodVolume(null);
            }
            if (!itemCargoWeight.isEnabled()) {//货物重量
                paramBean.setGoodWeight(null);
            }
            if (!itemPrice.isEnabled()) {//运费
                paramBean.setMoney(null);
            }
            if (!itemCarNum.isEnabled()) {//运费
                paramBean.setCarNum(null);
            }
            if (!itemCarPhone.isEnabled()) {//运费
                paramBean.setOwnerMobile(null);
            }
            if (!itemCarName.isEnabled()) {//运费
                paramBean.setOwnerName(null);
            }
            if (!itemDriverName.isEnabled()) {//运费
                paramBean.setDriverName(null);
            }
            if (!itemDriverPhone.isEnabled()) {//运费
                paramBean.setDriverMobile(null);
            }

        }
    }

    @Override
    public void onTimeSelect(Date date, View v) {

        itemStartTime.setTextCenter(ConvertUtils.formatTime(date, "yyyy-MM-dd"));
        paramBean.setDeliveryDate(ConvertUtils.formatTime(date, "yyyy-MM-dd"));

    }

    @Override
    protected boolean getUseEventBus() {
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartEvent(OrderEvent.StartLoactionEvent startLoactionEvent) {
        if (startLoactionEvent != null && startLoactionEvent.t != null) {
            String title= Utils.dealPioPlace(startLoactionEvent.t);
            itemStartLocation.setTextCenter(title);
            paramBean.setStartLatitude(startLoactionEvent.t.getLatLonPoint().getLatitude()+"");
            paramBean.setStartLongitude(startLoactionEvent.t.getLatLonPoint().getLongitude()+"");
            paramBean.setStartPlaceInfo(title);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEndEvent(OrderEvent.EndLoactionEvent endLoactionEvent) {
        if (endLoactionEvent != null && endLoactionEvent.t != null) {
            String title= Utils.dealPioPlace(endLoactionEvent.t);
            itemEndLocation.setTextCenter(title);
            paramBean.setDestinationLatitude(endLoactionEvent.t.getLatLonPoint().getLatitude()+"");
            paramBean.setDestinationLongitude(endLoactionEvent.t.getLatLonPoint().getLongitude()+"");
            paramBean.setDestinationInfo(title);
        }
    }

    @Override
    public void onBackPressed() {
        new BottomAlertBuilder()
                .setDialogTitle(changeOrder?"确认要放弃修改吗":"确认要放弃下单吗？")
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
        }
//        else if (!CommonUtils.carNumMatches(itemCarNum.getTextCenter())) {
//            showAleart(getString(R.string.carnum_error));
//            return false;
//        }
        ;
        if (TextUtils.isEmpty(itemCarPhone.getTextCenter())) {
            showAleart(itemCarPhone.getTextCenterHide());
            return false;
        } else if (!CommonUtils.isPhone(itemCarPhone.getTextCenter())) {
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
        } else if (!CommonUtils.isPhone(itemDriverPhone.getTextCenter())) {
            showAleart(getString(R.string.phone_error));
            return false;
        }


        return true;


    }


    private boolean show = true;

    @Override
    public void onItemClick(View tragetView, int position, Object data) {
        pop.dismiss();
        show = false;
        if (tragetView != null) {
            if (tragetView.getId() == R.id.item_car_num && data instanceof OrderCarNumbean) {
                OrderCarNumbean bean = (OrderCarNumbean) data;
                itemCarNum.setTextCenter(bean.getCarNumber());
                itemCarPhone.setTextCenter(bean.getContactMobile());
                itemCarName.setTextCenter(bean.getContactName());
            } else if (tragetView.getId() == R.id.item_driver_phone && data instanceof OrderDriverPhoneBean) {
                OrderDriverPhoneBean bean = (OrderDriverPhoneBean) data;
                itemDriverPhone.setTextCenter(bean.getMobile());
                itemDriverName.setTextCenter(bean.getContact());
            }
        }

    }

    @Override
    public void onDissmiss() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    pics.clear();
                    pics.addAll(selectList);
                    imageUtils.upList(pics);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    /**
     * 修改订单信息
     *
     * @param event
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OrderEvent.ChangeOrder event) {
        changeOrder = true;
        if (event != null && event.orderID != null) {
            HttpOrderFactory.queryOrderDetail(event.orderID)
                    .subscribe(new NetObserver<OrderDetailBean>(this) {
                        @Override
                        public void doOnSuccess(OrderDetailBean data) {
                            initValue(data);
                        }
                    });
        }
        BusFactory.getBus().removeStickyEvent(event);
    }

    /**
     * 删除条目被点击
     *
     * @param position
     * @param localMedia
     */
    @Override
    public void onItemDeletedClick(final int position, LocalMedia localMedia) {
        pics.remove(position);
        adapter.notifyItemDeleted(position);
        imageUtils.upList(pics);
    }

    private void initValue(OrderDetailBean orderDetailBean) {
        setToolbarTitle(getString(R.string.order_change));
        btSave.setText(R.string.order_entry_change);
        if (orderDetailBean == null) {
            return;
        }
        this.orderDetailBean = orderDetailBean;
        paramBean.setId(orderDetailBean.getId());
        paramBean.setDepartNum(orderDetailBean.getDepartNum());
        paramBean.setStartLatitude(orderDetailBean.getStartLatitude()+"");
        paramBean.setStartLongitude(orderDetailBean.getStartLongitude()+"");
        paramBean.setStartPlaceInfo(orderDetailBean.getStartPlaceInfo()+"");
        paramBean.setDestinationLatitude(orderDetailBean.getDestinationLatitude()+"");
        paramBean.setDestinationLongitude(orderDetailBean.getDestinationLongitude()+"");
        paramBean.setDestinationInfo(orderDetailBean.getDestinationInfo());

        String timeDes = orderDetailBean.getDeliveryDate();
        try {
            timeDes = ConvertUtils.formatString(orderDetailBean.getDeliveryDate(),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        }


        paramBean.setDeliveryDate(timeDes);
        paramBean.setGoodName(orderDetailBean.getGoodName());
        paramBean.setGoodVolume(orderDetailBean.getGoodVolume());
        paramBean.setGoodWeight(orderDetailBean.getGoodWeight());
        paramBean.setMoney(orderDetailBean.getMoney());
        paramBean.setCarNum(orderDetailBean.getCarNum());
        paramBean.setOwnerMobile(orderDetailBean.getOwnerMobile());
        paramBean.setOwnerName(orderDetailBean.getOwnerName());
        paramBean.setDriverName(orderDetailBean.getDriverName());
        paramBean.setDriverMobile(orderDetailBean.getDriverMobile());


        itemStartTime.setTextCenter(paramBean.getDeliveryDate());
        itemStartLocation.setTextCenter(paramBean.getStartPlaceInfo());
        itemEndLocation.setTextCenter(paramBean.getDestinationInfo());
        itemStartCarNum.setTextCenter(paramBean.getDepartNum());
        itemCargoName.setTextCenter(paramBean.getGoodName());
        itemCargoSize.setTextCenter(paramBean.getGoodVolume());
        itemCargoWeight.setTextCenter(paramBean.getGoodWeight());
        itemPrice.setTextCenter(paramBean.getMoney());

        show=false;
        itemCarNum.setTextCenter(paramBean.getCarNum());
        itemCarPhone.setTextCenter(paramBean.getOwnerMobile());
        itemCarName.setTextCenter(paramBean.getOwnerName());

        show=false;
        itemDriverName.setTextCenter(paramBean.getDriverName());
        itemDriverPhone.setTextCenter(paramBean.getDriverMobile());

        dealImgURl(orderDetailBean);
        adapter.notifyDataSetChanged();
        imageUtils.upList(pics);
        //更改是否可以修改订单
        changeOrderByState(orderDetailBean.getOrderState(), CommonOrderUtils.isPayOnLine(orderDetailBean.getPayWay()), orderDetailBean.isInsurance());
    }

    /**
     * 根据订单状态判断是否可以修改订单
     *
     * @param orderState  订单状态
     * @param payOnLine   true 线上支付
     * @param isInsurance true 已经购买保险
     */
    private void changeOrderByState(OrderDetailItemControl.OrderState orderState, boolean payOnLine, boolean isInsurance) {
        switch (orderState) {

            case WAITE_PAY://待支付,所有选项都可以修改
                break;
            case WAITE_START://待发车 ，根据保险和支付方式分为4中情况
                if (isInsurance) {//购买保险情况
                    //当订单状态为待发车，买保险且已购线上支付运费：只能修改：司机信息、货单图片；
                    if (payOnLine) {
                        itemStartTime.setEnabled(false);
                        itemStartLocation.setEnabled(false);
                        itemEndLocation.setEnabled(false);
                        itemStartCarNum.setEnabled(false);
                        itemCargoName.setEnabled(false);
                        itemCargoSize.setEnabled(false);
                        itemCargoWeight.setEnabled(false);
                        itemPrice.setEnabled(false);
                        itemCarNum.setEnabled(false);
                        itemCarPhone.setEnabled(false);
                        itemCarName.setEnabled(false);
                    } else {
                        //当订单状态为待发车，已购买保险且线下支付运费：只能修改：司机信息、货单图片，运费
                        itemStartTime.setEnabled(false);
                        itemStartLocation.setEnabled(false);
                        itemEndLocation.setEnabled(false);
                        itemStartCarNum.setEnabled(false);
                        itemCargoName.setEnabled(false);
                        itemCargoSize.setEnabled(false);
                        itemCargoWeight.setEnabled(false);
                        itemCarNum.setEnabled(false);
                        itemCarPhone.setEnabled(false);
                        itemCarName.setEnabled(false);
                        itemDriverName.setEnabled(true);
                        itemDriverPhone.setEnabled(true);
                    }

                } else {
                    if (payOnLine) {//线上支付
                        itemPrice.setEnabled(false);
                        //车主信息
                        itemCarNum.setEnabled(false);
                        itemCarPhone.setEnabled(false);
                        itemCarName.setEnabled(false);
                    }

                }


                break;
            case IN_TRANSIT://运输中
            case HAS_ARRIVED://已到达
            case RECEIPT://已收货
            case REFUND://退款
            case UNKNOW://未知状态
            default:
                //全部不可以修改
                itemStartTime.setEnabled(false);
                itemStartLocation.setEnabled(false);
                itemEndLocation.setEnabled(false);
                itemStartCarNum.setEnabled(false);
                itemCargoName.setEnabled(false);
                itemCargoSize.setEnabled(false);
                itemCargoWeight.setEnabled(false);
                itemPrice.setEnabled(false);
                itemCarNum.setEnabled(false);
                itemCarPhone.setEnabled(false);
                itemCarName.setEnabled(false);
                itemDriverName.setEnabled(false);
                itemDriverPhone.setEnabled(false);
                adapter.setDeletedClickListener(null);
                adapter.setOnItemClickListener(null);
                adapter.removeFoot(0);
                break;

        }

    }


    private void dealImgURl(OrderDetailBean orderDetailBean) {
        if (orderDetailBean != null && !CommonUtils.isEmptyCollection(orderDetailBean.getGoodsImages())) {
            for (UpImgData imagesBean : orderDetailBean.getGoodsImages()) {
                LocalMedia media = new LocalMedia();
                media.setPath(imagesBean.getAbsolutePath());
                media.setRelativePath(imagesBean.getPath());
                pics.add(media);
            }
        }
        imageUtils.upList(pics);
    }

    /**
     * 图片条目被点击
     *
     * @param position
     * @param localMedia
     */
    @Override
    public void onItemClick(int position, LocalMedia localMedia) {

    }

    @Override
    public void onUpLoadFail(int failCount) {

        creatDialog("图片上传失败", "有" + failCount + "张图片上传失败，是否重新上传？", "放弃上传", "重新上传")
                .setLeftClickListener(new DialogControl.OnButtonLeftClickListener() {
                    @Override
                    public void onLeftClick(View view, DialogControl.ICenterDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        dialog.dismiss();
                        imageUtils.reUpLoad();
                    }
                })
                .creatDialog(new CenterAlertDialog(mContext))
                .show();
    }

    private CenterAlertBuilder creatDialog(String title, String content, String btleft, String btRight) {
           return Utils.creatDialog(mContext,title,content,btleft,btRight);
    }
}
