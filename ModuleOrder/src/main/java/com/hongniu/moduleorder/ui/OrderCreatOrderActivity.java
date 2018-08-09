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
import com.bigkoo.pickerview.TimePickerView;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.utils.PickerDialogUtils;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.widget.CarNumPop;
import com.sang.common.event.BusFactory;
import com.sang.common.utils.JLog;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.ItemView;
import com.sang.common.widget.dialog.BottomAlertDialog;
import com.sang.common.widget.dialog.builder.BottomAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 创建订单
 */
@Route(path = ArouterParamOrder.activity_order_create)
public class OrderCreatOrderActivity extends BaseActivity implements View.OnClickListener, TimePickerView.OnTimeSelectListener {

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String mark = "沪";
            List<String> datas = new ArrayList<>();
            datas.add("沪A1245855");
            datas.add("沪A245855");
            datas.add("沪A45855");
            datas.add("沪A5855");
            datas.add("沪A1245855");
            datas.add("沪A1245855");
            pop.upData(mark,datas);
            pop.show(itemCarNum);
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

    private CarNumPop pop;

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
        btSave = findViewById(R.id.bt_entry);
        timePickerView = PickerDialogUtils.initTimePicker(mContext, this, new boolean[]{true, true, true, false, false, false});
        pop = new CarNumPop(mContext);

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
                if (!TextUtils.isEmpty(itemCarNum.getTextCenter())) {
                    handler.sendEmptyMessageDelayed(0, 300);
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.item_start_time) {
            timePickerView.show();
        } else if (id == R.id.item_start_loaction) {
            ArouterUtils.getInstance().builder(ArouterParamOrder.activity_map_loaction).withBoolean(Param.TRAN, true).navigation(mContext);
        } else if (id == R.id.item_end_loaction) {
            ArouterUtils.getInstance().builder(ArouterParamOrder.activity_map_loaction).withBoolean(Param.TRAN, false).navigation(mContext);

        }else if (id==R.id.bt_entry){
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
            finish();
        }
    }

    @Override
    public void onTimeSelect(Date date, View v) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        String data = format.format(date);
        itemStartTime.setTextCenter(data);
    }

    @Override
    protected boolean getUseEventBus() {
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartEvent(OrderEvent.StartLoactionEvent startLoactionEvent) {
        if (startLoactionEvent != null && startLoactionEvent.t != null) {
            itemStartLocation.setTextCenter(startLoactionEvent.t.getTitle());
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEndEvent(OrderEvent.EndLoactionEvent endLoactionEvent) {
        if (endLoactionEvent != null && endLoactionEvent.t != null) {
            itemEndLocation.setTextCenter(endLoactionEvent.t.getTitle());
        }
    }

    @Override
    public void onBackPressed() {
        new BottomAlertBuilder()
                .setDialogTitle( "确认要放弃下单吗？")
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
}
