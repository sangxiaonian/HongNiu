package com.hongniu.freight.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.widget.ItemTextView;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.CarTypeBean;
import com.hongniu.freight.entity.OrderFindCarParams;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.PickerDialogUtils;
import com.hongniu.freight.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @data 2020/3/8
 * @Author PING
 * @Description 发布找车
 */
@Route(path = ArouterParamApp.activity_order_find_car)
public class OrderFindCarActivity extends CompanyBaseActivity implements View.OnClickListener, OnOptionsSelectListener, ItemTextView.OnCenterChangeListener {

    private ItemTextView item_car_type;
    private ItemTextView item_price;
    private TextView bt_sum;
    List<CarTypeBean> carBeans;
    private OptionsPickerView pickerView;
    private CarTypeBean carTypeBean;//选中车辆信息
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_find_car);
        orderId=getIntent().getStringExtra(Param.TRAN);
        setWhitToolBar("");
        initView();
        initData();
        initListener();
        check(false);
    }

    @Override
    protected void initView() {
        super.initView();
        item_car_type = findViewById(R.id.item_car_type);
        item_price = findViewById(R.id.item_price);
        bt_sum = findViewById(R.id.bt_sum);
        pickerView = PickerDialogUtils.initPickerDialog(mContext, this);
    }

    @Override
    protected void initData() {
        super.initData();
        carBeans =new ArrayList<>();
    }

    @Override
    protected void initListener() {
        super.initListener();
        item_car_type.setOnClickListener(this);
        bt_sum.setOnClickListener(this);
        item_car_type.setOnCenterChangeListener(this);
        item_price.setOnCenterChangeListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.item_car_type) {

            if (CollectionUtils.isEmpty(carBeans)) {
                HttpAppFactory.queryCarTypeList()
                        .subscribe(new NetObserver<List<CarTypeBean>>(this) {
                            @Override
                            public void doOnSuccess(List<CarTypeBean> carTypeBeans) {
                                super.doOnSuccess(carTypeBeans);
                                carBeans.clear();
                                carBeans.addAll(carTypeBeans);
                                pickerView.setPicker(carBeans);
                                pickerView.show();
                            }
                        });
            } else {
                pickerView.show();
            }
        } else if (v.getId() == R.id.bt_sum) {
            if (check(true)) {
                OrderFindCarParams params=new OrderFindCarParams();
                params.setCartypeId(carTypeBean.getId());
                params.setRealMoney(item_price.getTextCenter());
                params.setOrderId(orderId);

                HttpAppFactory.orderFindCarInfo(params)
                        .subscribe(new NetObserver<Object>(this){
                            @Override
                            public void doOnSuccess(Object o) {
                                super.doOnSuccess(o);
                                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                                setResult(Activity.RESULT_OK);
                                finish();
                            }
                        });

            }
        }
    }

    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        carTypeBean = carBeans.get(options1);
        item_car_type.setTextCenter(carTypeBean.getCarType());
    }

    private boolean check(boolean showAlert) {
        Utils.setButton(bt_sum, false);
        if (TextUtils.isEmpty(item_car_type.getTextCenter())) {
            if (showAlert) {
                showErrorAlert(0, item_car_type.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_price.getTextCenter())) {
            if (showAlert) {
                showErrorAlert(0, item_price.getTextCenterHide());
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
}
