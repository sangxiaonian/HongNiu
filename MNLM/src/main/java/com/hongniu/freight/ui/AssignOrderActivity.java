package com.hongniu.freight.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.widget.editext.SearchTextWatcher;
import com.fy.androidlibrary.widget.span.CenterAlignImageSpan;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.widget.ItemTextView;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.CarInfoBean;
import com.hongniu.freight.entity.OrderDispathCarParams;
import com.hongniu.freight.entity.OrderDriverPhoneBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.Utils;
import com.hongniu.freight.widget.CarNumPop;

import java.util.List;

/**
 * @data 2020/2/13
 * @Author PING
 * @Description 立即派车
 */
@Route(path = ArouterParamApp.activity_assign_order)
public class AssignOrderActivity extends CompanyBaseActivity implements View.OnClickListener, ItemTextView.OnCenterChangeListener, SearchTextWatcher.SearchTextChangeListener, CarNumPop.onItemClickListener<OrderDriverPhoneBean> {
    private TextView bt_sum;
    private ItemTextView item_price;
    private ItemTextView item_car_type;
    private ItemTextView item_driver_phone;
    private ItemTextView item_driver_name;
    private CarInfoBean carInfo;
    private String orderID;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            queryDriverInfor(item_driver_name.getTextCenter());

        }
    };
    private CarNumPop<OrderDriverPhoneBean> popDriver;


    private boolean show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_order);
        orderID = getIntent().getStringExtra(Param.TRAN);
        setWhitToolBar("");
        initView();
        initData();
        initListener();
        check(false);
    }

    @Override
    protected void initView() {
        super.initView();
        bt_sum = findViewById(R.id.bt_sum);
        item_price = findViewById(R.id.item_price);
        item_car_type = findViewById(R.id.item_car_type);
        item_driver_phone = findViewById(R.id.item_driver_phone);
        item_driver_name = findViewById(R.id.item_driver_name);
        popDriver = new CarNumPop<>(this);
    }

    @Override
    protected void initListener() {
        super.initListener();
        bt_sum.setOnClickListener(this);
        item_car_type.setOnClickListener(this);
        item_price.setOnCenterChangeListener(this);
        item_car_type.setOnCenterChangeListener(this);
        item_driver_phone.setOnCenterChangeListener(this);
        item_driver_name.setOnCenterChangeListener(this);
        item_driver_name.getEtCenter().addTextChangedListener(new SearchTextWatcher(this));
        popDriver.setListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && data != null) {
            carInfo = data.getParcelableExtra(Param.TRAN);
            if (carInfo != null) {
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append(TextUtils.isEmpty(carInfo.getCarType()) ? "" : carInfo.getCarType())
                        .append("\n")
                        .append(TextUtils.isEmpty(carInfo.getName()) ? "" : carInfo.getName())

                ;
                builder.append("\t");
                int lineStart = builder.length();
                int lineEnd = builder.length() + 1;
                builder.append(" ");
                builder.append("\t");
                builder.setSpan(new CenterAlignImageSpan(this, R.drawable.ovl_line_v), lineStart, lineEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                builder
                        .append(TextUtils.isEmpty(carInfo.getMobile()) ? "" : carInfo.getMobile());

                item_car_type.getEtCenter().setText(builder);
            } else {
                item_car_type.setTextCenter("");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_sum) {
            if (check(true)) {

                OrderDispathCarParams params = new OrderDispathCarParams();
                params.setCarId(carInfo.getId());
                params.setDriverMobile(item_driver_phone.getTextCenter());
                params.setDriverName(item_driver_name.getTextCenter());
                params.setRealMoney(item_price.getTextCenter());
                params.setOrderId(orderID);
                HttpAppFactory.orderDispathCar(params)
                        .subscribe(new NetObserver<Object>(this) {
                            @Override
                            public void doOnSuccess(Object o) {
                                super.doOnSuccess(o);
                                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                                setResult(Activity.RESULT_OK);
                                finish();
                            }
                        });


            }
        } else if (v.getId() == R.id.item_car_type) {
            ArouterUtils.getInstance().builder(ArouterParamApp.activity_search_car)
                    .navigation((Activity) mContext, 1);
        }

    }

    private boolean check(boolean showAlert) {
        Utils.setButton(bt_sum, false);
        if (TextUtils.isEmpty(item_price.getTextCenter())) {
            if (showAlert) {
                ToastUtils.getInstance().show(item_price.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_car_type.getTextCenter())) {
            if (showAlert) {
                ToastUtils.getInstance().show(item_car_type.getTextCenterHide());
            }
            return false;
        }

        if (TextUtils.isEmpty(item_driver_name.getTextCenter())) {
            if (showAlert) {
                ToastUtils.getInstance().show(item_driver_name.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_driver_phone.getTextCenter())) {
            if (showAlert) {
                ToastUtils.getInstance().show(item_driver_phone.getTextCenterHide());
            }
            return false;
        }
        Utils.setButton(bt_sum, true);

        return true;
    }

    @Override
    public void onCenterChange(String msg) {
        check(false);
    }

    @Override
    public void onSearchTextChange(String msg) {
        handler.removeMessages(0);
        if (!TextUtils.isEmpty(msg) && show) {
            handler.sendEmptyMessageDelayed(0, 300);
        }
        show = true;

    }

    private void queryDriverInfor(String textCenter) {
        HttpAppFactory.getDriverPhone(textCenter)
                .subscribe(new NetObserver<List<OrderDriverPhoneBean>>(null) {
                    @Override
                    public void doOnSuccess(List<OrderDriverPhoneBean> orderDriverPhoneBeans) {
                        super.doOnSuccess(orderDriverPhoneBeans);
                        popDriver.upData(item_driver_name.getTextCenter(), orderDriverPhoneBeans);
                        popDriver.show(item_driver_name);
                    }
                });
    }

    @Override
    public void onItemClick(View tragetView, int position, OrderDriverPhoneBean data) {
        popDriver.dismiss();
        show = false;
        if (tragetView != null && data != null) {
            item_driver_phone.setTextCenter(data.getMobile());
            item_driver_name.setTextCenter(data.getContact());
        }
    }

    @Override
    public void onDissmiss() {

    }
}
