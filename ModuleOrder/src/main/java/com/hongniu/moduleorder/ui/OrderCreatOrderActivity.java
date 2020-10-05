package com.hongniu.moduleorder.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.androidlibrary.utils.CommonUtils;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.PayOrderInfor;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.baselibrary.utils.PermissionUtils;
import com.hongniu.baselibrary.utils.PickerDialogUtils;
import com.hongniu.baselibrary.utils.PictureSelectorUtils;
import com.hongniu.baselibrary.utils.UpLoadImageUtils;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;
import com.hongniu.moduleorder.R;
import com.sang.common.recycleview.inter.OnItemClickListener;
import com.hongniu.baselibrary.widget.adapter.OnItemDeletedClickListener;
import com.hongniu.moduleorder.control.OrderCreatControl;
import com.hongniu.moduleorder.entity.OrderCarNumbean;
import com.hongniu.baselibrary.entity.OrderCreatParamBean;
import com.hongniu.moduleorder.entity.OrderDriverPhoneBean;
import com.hongniu.moduleorder.entity.OrderInsuranceParam;
import com.hongniu.moduleorder.present.OrderCreataPresenter;
import com.hongniu.baselibrary.widget.adapter.PicAdapter;
import com.hongniu.moduleorder.widget.CarNumPop;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.fy.androidlibrary.event.BusFactory;
import com.sang.common.recycleview.holder.PeakHolder;
import com.hongniu.baselibrary.entity.CommonBean;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.toast.ToastUtils;
import com.sang.common.widget.ItemView;
import com.sang.common.widget.dialog.BottomAlertDialog;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.BottomAlertBuilder;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 创建订单
 */
@Route(path = ArouterParamOrder.activity_order_create)
public class OrderCreatOrderActivity extends BaseActivity implements OrderCreatControl.IOrderCreataView, View.OnClickListener, OnTimeSelectListener, CarNumPop.onItemClickListener, OnItemDeletedClickListener<LocalMedia>, OnItemClickListener<LocalMedia>, UpLoadImageUtils.OnUpLoadListener {
    OrderCreatControl.IOrderCreataPresenter presenter;

    private boolean select;//是否选中代收货款


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                presenter.queryCarInfor(itemCarNum.getTextCenter());
            } else if (msg.what == 1) {
                presenter.queryDriverInfor(itemDriverName.getTextCenter());
            } else if (msg.what == 2) {
                presenter.queryConsighee(itemConsigneeName.getTextCenter());
            }
        }
    };


    private ItemView itemStartTime;         //发货时间
    private ItemView itemStartLocation;     //发货地点
    private ItemView itemEndLocation;       //收货地点
    private ItemView itemStartCarNum;       //发车编号
    private ItemView itemCargoName;          //货物名称
    private ItemView itemPrice;                 //运费
    private ItemView itemWeight;                 //货物重量
    private ItemView itemSize;                 //货物体积
    private ItemView itemCarNum;         //车牌号
    private ItemView itemCarPhone;         //车主手机号
    private ItemView itemCarName;         //车主姓名
    private ItemView itemDriverName;         //司机姓名
    private ItemView itemDriverPhone;         //司机手机
    private TimePickerView timePickerView;
    private ViewGroup rlDai;//代收货款
    private ImageView imgCheck;//代收货款
    private ItemView itemCargoPrice;//货款金额
    private ItemView itemConsigneeName;//收貨人姓名
    private ItemView itemConsigneePhone;//收货人手机

    private Button btSave;

    private RecyclerView rv;
    private CarNumPop<OrderCarNumbean> pop;
    private CarNumPop<OrderDriverPhoneBean> popDriver;
    private CarNumPop<OrderDriverPhoneBean> popReceive;
    //    private OrderCreatParamBean paramBean = new OrderCreatParamBean();
    PicAdapter adapter;
    List<LocalMedia> pics;
    //是否是修改订单

    UpLoadImageUtils imageUtils = new UpLoadImageUtils(Param.GOODS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_creat_order);
        setToolbarTitle(getString(R.string.order_create_order));
        presenter = new OrderCreataPresenter(this);
        initView();
        initData();
        initListener();
        presenter.saveInsuranceInfo((OrderInsuranceParam) getIntent().getParcelableExtra(Param.TRAN));
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
        itemCarNum = findViewById(R.id.item_car_num);
        itemCarPhone = findViewById(R.id.item_car_phone);
        itemCarName = findViewById(R.id.item_car_name);
        itemDriverName = findViewById(R.id.item_driver_name);
        itemDriverPhone = findViewById(R.id.item_driver_phone);
        btSave = findViewById(R.id.bt_entry);
        rv = findViewById(R.id.rv_pic);
        rlDai = findViewById(R.id.rl_dai);
        imgCheck = findViewById(R.id.img_check);
        itemCargoPrice = findViewById(R.id.item_cargo_price);
        itemConsigneeName = findViewById(R.id.item_consignee_name);
        itemConsigneePhone = findViewById(R.id.item_consignee_phone);
        itemWeight = findViewById(R.id.item_weight);
        itemSize = findViewById(R.id.item_size);

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.set(startDate.get(Calendar.YEAR) + 2, 12, 31);
        timePickerView = PickerDialogUtils.creatTimePicker(mContext, this, new boolean[]{true, true, true, false, false, false})
                .setRangDate(startDate, endDate)
                .build();
        timePickerView.setKeyBackCancelable(false);//系统返回键监听屏蔽掉
        pop = new CarNumPop<>(mContext);
        popDriver = new CarNumPop<>(mContext);
        popReceive = new CarNumPop<>(mContext);

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
                            PictureSelectorUtils.showPicture((AppCompatActivity) mContext, pics);
                        }
                    }
                });
            }
        });
        rv.setAdapter(adapter);
        //默认情况下，不选中代收货款信息
        switchCargo(false);
    }

    @Override
    protected void initListener() {
        super.initListener();
        imageUtils.setOnUpLoadListener(this);
        itemStartTime.setOnClickListener(this);
        itemStartLocation.setOnClickListener(this);
        itemEndLocation.setOnClickListener(this);
        btSave.setOnClickListener(this);
        rlDai.setOnClickListener(this);
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
                handler.removeMessages(2);
                if (!TextUtils.isEmpty(itemCarNum.getTextCenter()) && show) {
                    handler.sendEmptyMessageDelayed(0, 300);
                }
                show = true;
            }
        });
        itemDriverName.getEtCenter().addTextChangedListener(new TextWatcher() {

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
                handler.removeMessages(2);
                if (!TextUtils.isEmpty(itemDriverName.getTextCenter()) && show) {
                    handler.sendEmptyMessageDelayed(1, 300);
                }
                show = true;
            }
        });
        itemConsigneeName.getEtCenter().addTextChangedListener(new TextWatcher() {

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
                handler.removeMessages(2);
                if (!TextUtils.isEmpty(itemConsigneeName.getTextCenter()) && show) {
                    handler.sendEmptyMessageDelayed(2, 300);
                }
                show = true;
            }
        });


        pop.setListener(this);
        popDriver.setListener(this);
        popReceive.setListener(this);

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
                if (imageUtils.isFinish()) {
                    // 如果没有更改过图片，则不上传
                    submit();
                } else {
                    creatDialog(imageUtils.unFinishCount() + "张图片上传中", "是否放弃上传？", "确定放弃", "继续上传")
                            .setLeftClickListener(new DialogControl.OnButtonLeftClickListener() {
                                @Override
                                public void onLeftClick(View view, DialogControl.ICenterDialog dialog) {
                                    dialog.dismiss();
                                    submit();
                                }
                            })
                            .creatDialog(new CenterAlertDialog(mContext))
                            .show();
                }

            }
        } else if (id == R.id.rl_dai) {
            switchCargo(!select);
        }
    }

    private void submit() {
        List<String> result = imageUtils.getResult();
        if (result.size() == 0 && CollectionUtils.isEmpty(pics)) {
            result = null;
        }
        presenter.submit(result, this);
    }

    /**
     * 切换是否选中代收货款相关信息
     *
     * @param select
     */
    private void switchCargo(boolean select) {
        this.select = select;
        imgCheck.setImageResource(select ? R.mipmap.icon_xz_36 : R.mipmap.icon_wxz_36);
        itemCargoPrice.setVisibility(select ? View.VISIBLE : View.GONE);
        itemConsigneeName.setVisibility(select ? View.VISIBLE : View.GONE);
        itemConsigneePhone.setVisibility(select ? View.VISIBLE : View.GONE);
    }


    @Override
    public void getValue(OrderCreatParamBean paramBean) {
        paramBean.setDepartNum(itemStartCarNum.getTextCenter());
        paramBean.setGoodName(itemCargoName.getTextCenter());
        paramBean.setMoney(itemPrice.getTextCenter());
        paramBean.setCarNum(itemCarNum.getTextCenter());
        paramBean.setOwnerMobile(itemCarPhone.getTextCenter());
        paramBean.setOwnerName(itemCarName.getTextCenter());
        paramBean.setDriverName(itemDriverName.getTextCenter());
        paramBean.setDriverMobile(itemDriverPhone.getTextCenter());
        paramBean.setDriverMobile(itemDriverPhone.getTextCenter());
        paramBean.setDeliveryDate(itemStartTime.getTextCenter());
        paramBean.setGoodWeight(itemWeight.getTextCenter());
        paramBean.setGoodVolume(itemSize.getTextCenter());

        paramBean.setReplaceState(select ? 1 : 0);
        if (select) {
            try {
                paramBean.setPaymentAmount(TextUtils.isEmpty(itemCargoPrice.getTextCenter()) ? 0 : Float.parseFloat(itemCargoPrice.getTextCenter()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            paramBean.setReceiptName(itemConsigneeName.getTextCenter());
            paramBean.setReceiptMobile(itemConsigneePhone.getTextCenter());
        }

        if (!itemWeight.isEnabled()) {//不可更改
            paramBean.setGoodWeight(null);
        }
        if (!itemSize.isEnabled()) {//不可更改
            paramBean.setGoodVolume(null);
        }
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

    @Override
    public void showFinishAleart(String s) {
        new BottomAlertBuilder()
                .setDialogTitle(s)
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

    @Override
    public void showConsigneePop(List<OrderDriverPhoneBean> data) {
        popReceive.upData(itemConsigneeName.getTextCenter(), data);
        popReceive.show(itemConsigneeName);
    }

    /**
     * 展示货物名称
     *
     * @param cargoName
     * @param price
     */
    @Override
    public void showCargoName(String cargoName, String price) {
        itemCargoName.setTextCenter(cargoName);
        setToolbarTitle("完善信息");

    }

    @Override
    public void onTimeSelect(Date date, View v) {

        itemStartTime.setTextCenter(ConvertUtils.formatTime(date, "yyyy-MM-dd"));


    }

    @Override
    protected boolean getUseEventBus() {
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartEvent(Event.StartLoactionEvent startLoactionEvent) {
        if (startLoactionEvent != null && startLoactionEvent.t != null) {
            String title = Utils.dealPioPlace(startLoactionEvent.t);
            itemStartLocation.setTextCenter(title);
            presenter.changeStartPlaceInfor(startLoactionEvent.t);

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEndEvent(Event.EndLoactionEvent endLoactionEvent) {
        if (endLoactionEvent != null && endLoactionEvent.t != null) {
            String title = Utils.dealPioPlace(endLoactionEvent.t);
            itemEndLocation.setTextCenter(title);
            presenter.changeEndPlaceInfor(endLoactionEvent.t);

        }
    }

    @Override
    public void onBackPressed() {

        presenter.onBacePress();


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


        ;
        if (TextUtils.isEmpty(itemPrice.getTextCenter())) {
            showAleart(itemPrice.getTextCenterHide());
            return false;
        }


        if (TextUtils.isEmpty(itemCarNum.getTextCenter())) {
            showAleart(itemCarNum.getTextCenterHide());
            return false;
        }

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

        if (select) {
            if (TextUtils.isEmpty(itemCargoPrice.getTextCenter())) {
                showAleart(itemCargoPrice.getTextCenterHide());
                return false;
            }
            ;
            if (TextUtils.isEmpty(itemConsigneeName.getTextCenter())) {
                showAleart(itemConsigneeName.getTextCenterHide());
                return false;
            }
            ;
            if (TextUtils.isEmpty(itemConsigneePhone.getTextCenter())) {
                showAleart(itemConsigneePhone.getTextCenterHide());
                return false;
            } else if (!CommonUtils.isPhone(itemConsigneePhone.getTextCenter())) {
                showAleart(getString(R.string.phone_error));
                return false;
            }
            ;
        }

        return true;


    }


    private boolean show = true;

    @Override
    public void onItemClick(View tragetView, int position, Object data) {
        pop.dismiss();
        popDriver.dismiss();
        popReceive.dismiss();
        show = false;
        if (tragetView != null) {
            if (tragetView.getId() == R.id.item_car_num && data instanceof OrderCarNumbean) {
                OrderCarNumbean bean = (OrderCarNumbean) data;
                itemCarNum.setTextCenter(bean.getCarNumber());
                itemCarPhone.setTextCenter(bean.getContactMobile());
                itemCarName.setTextCenter(bean.getContactName());
            } else if (tragetView.getId() == R.id.item_driver_name && data instanceof OrderDriverPhoneBean) {
                OrderDriverPhoneBean bean = (OrderDriverPhoneBean) data;
                itemDriverPhone.setTextCenter(bean.getMobile());
                itemDriverName.setTextCenter(bean.getContact());
            } else if (tragetView.getId() == R.id.item_consignee_name && data instanceof OrderDriverPhoneBean) {
                OrderDriverPhoneBean bean = (OrderDriverPhoneBean) data;
                itemConsigneePhone.setTextCenter(bean.getMobile());
                itemConsigneeName.setTextCenter(bean.getContact());
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
    public void onMessageEvent(Event.ChangeOrder event) {
        if (event != null && event.orderID != null) {
            presenter.changeType(1, mContext);
            presenter.queryOrderInfor(event.orderID, this);

        }
        BusFactory.getBus().removeStickyEvent(event);
    }


    /**
     * 车货匹配订单下单
     *
     * @param event
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageMatchEvent(OrderCreatParamBean event) {
        if (event != null) {
            presenter.changeType(2, mContext);
            presenter.saveInfor(event);
        }
        EventBus.getDefault().removeStickyEvent(event);
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
        return Utils.creatDialog(mContext, title, content, btleft, btRight);
    }

    /**
     * 跟进类型更改标题
     *
     * @param title
     * @param bt
     */
    @Override
    public void changeTitle(String title, String bt) {
        setToolbarTitle(title);
        if (bt != null) {
            btSave.setText(bt);
        }
    }

    /**
     * 显示车辆信息
     *
     * @param data
     */
    @Override
    public void showCarPop(List<OrderCarNumbean> data) {
        pop.upData(itemCarNum.getTextCenter(), data);
        pop.show(itemCarNum);
    }

    /**
     * 显示司机信息
     *
     * @param data
     */
    @Override
    public void showDriverPop(List<OrderDriverPhoneBean> data) {
        popDriver.upData(itemDriverName.getTextCenter(), data);
        popDriver.show(itemDriverName);
    }

    /**
     * 根据数据初始化页面信息
     *
     * @param paramBean
     */
    @Override
    public void showInfor(OrderCreatParamBean paramBean) {
        itemStartTime.setTextCenter(paramBean.getDeliveryDate());
        itemStartLocation.setTextCenter(paramBean.getStartPlaceInfo());
        itemEndLocation.setTextCenter(paramBean.getDestinationInfo());
        itemStartCarNum.setTextCenter(paramBean.getDepartNum());
        itemCargoName.setTextCenter(paramBean.getGoodName());
        itemPrice.setTextCenter(paramBean.getMoney());
        itemWeight.setTextCenter(paramBean.getGoodWeight());
        itemSize.setTextCenter(paramBean.getGoodVolume());
        itemCargoPrice.setTextCenter(paramBean.getPaymentAmount() + "");
        show = false;
        itemConsigneeName.setTextCenter(paramBean.getReceiptName());
        itemConsigneePhone.setTextCenter(paramBean.getReceiptMobile());

        switchCargo(paramBean.getReplaceState() == 1);

        show = false;
        itemCarNum.setTextCenter(paramBean.getCarNum());
        itemCarPhone.setTextCenter(paramBean.getOwnerMobile());
        itemCarName.setTextCenter(paramBean.getOwnerName());

        show = false;
        itemDriverName.setTextCenter(paramBean.getDriverName());
        itemDriverPhone.setTextCenter(paramBean.getDriverMobile());
    }

    /**
     * 展示所有的图片信息
     *
     * @param imageInfors
     */
    @Override
    public void showImageInfors(List<LocalMedia> imageInfors) {
        pics.addAll(imageInfors);
        adapter.notifyDataSetChanged();
        imageUtils.upList(pics);
    }

    /**
     * 根据订单状态修更改item能否修改
     *
     * @param orderState
     * @param payOnLine
     * @param insurance
     */
    @Override
    public void changeEnableByOrder(OrderDetailItemControl.OrderState orderState, boolean payOnLine, boolean insurance) {
        switch (orderState) {

            case WAITE_PAY://待支付,所有选项都可以修改
            case PAY_REFUSE://申请被拒绝,所有选项都可以修改
                break;
            case WAITE_START://待发车 ，根据保险和支付方式分为4中情况
                if (insurance) {//购买保险情况
                    //当订单状态为待发车，买保险且已购线上支付运费：只能修改：司机信息、货单图片；
                    if (payOnLine) {
                        itemStartTime.setEnabled(false);
                        itemStartLocation.setEnabled(false);
                        itemEndLocation.setEnabled(false);
                        itemStartCarNum.setEnabled(false);
                        itemCargoName.setEnabled(false);
                        itemPrice.setEnabled(false);
                        itemWeight.setEnabled(false);
                        itemSize.setEnabled(false);
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
                        itemCarNum.setEnabled(false);
                        itemWeight.setEnabled(false);
                        itemSize.setEnabled(false);
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
                itemWeight.setEnabled(false);
                itemSize.setEnabled(false);
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

    /**
     * 新增修改订单成功
     *
     * @param data
     * @param type
     * @param insuranceInfo
     */
    @Override
    public void finishSuccess(OrderDetailBean data, int type, OrderInsuranceParam insuranceInfo) {
        if (type == 1) {
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
            finish();
        } else {
            PayOrderInfor payOrder = new PayOrderInfor();
            payOrder.insurance = false;
            payOrder.money = Float.parseFloat(itemPrice.getTextCenter());
            payOrder.orderID = data.getId();
            payOrder.orderNum = data.getOrderNum();
            if (select) {
                payOrder.receiptMobile = itemConsigneePhone.getTextCenter();
                payOrder.receiptName = itemConsigneeName.getTextCenter();
            }
            BusFactory.getBus().postSticky(payOrder);
            ArouterUtils.getInstance()
                    .builder(ArouterParamOrder.activity_order_pay)
                    .navigation(mContext);
            BusFactory.getBus().postSticky(new Event.UpRoale(OrderDetailItemControl.RoleState.CARGO_OWNER));
            if ( insuranceInfo != null) {
                //对于牛人保
                BusFactory.getBus().postSticky(insuranceInfo);
            }
            finish();
        }
    }
}
