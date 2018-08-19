package com.hongniu.modulelogin.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CarTypeBean;
import com.hongniu.baselibrary.utils.PickerDialogUtils;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulelogin.R;
import com.hongniu.modulelogin.entity.LoginCarInforBean;
import com.hongniu.modulelogin.entity.LoginEvent;
import com.hongniu.modulelogin.net.HttpLoginFactory;
import com.sang.common.event.BusFactory;
import com.sang.common.utils.SharedPreferencesUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.ItemView;
import com.sang.common.widget.dialog.BottomAlertDialog;
import com.sang.common.widget.dialog.builder.BottomAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

/**
 * 车辆新增、修改界面
 */
@Route(path = ArouterParamLogin.activity_car_infor)
public class LoginCarInforActivity extends BaseActivity implements View.OnClickListener, OnOptionsSelectListener {

    private ItemView itemCarType;//车辆类型
    private ItemView itemCarNum;//车牌号
    private ItemView itemCarOwner;//车主姓名
    private ItemView itemCarPhone;//车主手机号

    private Button button;
    private boolean isAdd;//是否是添加车辆
    private OptionsPickerBuilder pickerDialog;
    private LoginCarInforBean carBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_car_infor);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        itemCarType = findViewById(R.id.item_car_type);
        itemCarNum = findViewById(R.id.item_car_num);
        itemCarOwner = findViewById(R.id.item_car_owner);
        itemCarPhone = findViewById(R.id.item_car_phone);
        button = findViewById(R.id.bt_save);
        pickerDialog = PickerDialogUtils.creatPickerDialog(mContext, this).setTitleText(getString(R.string.login_car_select));
        tvToolbarRight.setTextColor(getResources().getColor(R.color.tool_right));


    }

    private List<CarTypeBean> cars;

    @Override
    protected void initData() {
        super.initData();
        cars = new ArrayList<>();

        String string = SharedPreferencesUtils.getInstance().getString(Param.CAR_TYPE);
        if (!TextUtils.isEmpty(string)) {
            List<CarTypeBean> o = new Gson().fromJson(string,
                    new TypeToken<List<CarTypeBean>>() {
                    }.getType());
            if (o != null) {
                cars.addAll(o);
            }
        }


    }

    @Override
    protected void initListener() {
        super.initListener();
        button.setOnClickListener(this);
        itemCarType.setOnClickListener(this);
    }

    @Override
    protected boolean getUseEventBus() {
        return true;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginEvent.CarEvent event) {
        this.isAdd = event.type == 0;
        if (event.type == 0) {
            setToolbarTitle(getString(R.string.login_add_car));
            carBean = new LoginCarInforBean();
        } else {
            setToolbarTitle(getString(R.string.login_modification_car));
            carBean = event.bean;
            itemCarNum.setTextCenter(carBean.getCarNumber() == null ? "" : carBean.getCarNumber());
            itemCarType.setTextCenter(carBean.getCartypename() == null ? "" : carBean.getCartypename());
            itemCarOwner.setTextCenter(carBean.getContactName() == null ? "" : carBean.getContactName());
            itemCarPhone.setTextCenter(carBean.getContactMobile() == null ? "" : carBean.getContactMobile());
        }

        if (!isAdd) {
            setToolbarSrcRight(getString(R.string.deleted));
            setToolbarRightClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new BottomAlertBuilder()
                            .setTopClickListener(new DialogControl.OnButtonTopClickListener() {
                                @Override
                                public void onTopClick(View view, DialogControl.IBottomDialog dialog) {
                                    dialog.dismiss();
                                    deletedCar();
                                }
                            })
                            .setDialogTitle(getString(R.string.login_car_entry_deleted))
                            .creatDialog(new BottomAlertDialog(mContext))
                            .show();

                }
            });
        }
        BusFactory.getBus().removeStickyEvent(event);
    }

    //删除车辆
    private void deletedCar() {
        HttpLoginFactory.deletedCar(carBean.getId())
                .subscribe(new NetObserver<String>(this) {
                    @Override
                    public void doOnSuccess(String data) {
                        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show(R.string.deleted_success);
                        BusFactory.getBus().post(new LoginEvent.UpdateEvent());
                        finish();

                    }
                });
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(final View v) {
        int i = v.getId();
        if (i == R.id.bt_save) {
            if (check()) {
                if (isAdd) {//新增车辆
                    HttpLoginFactory.addCar(getValue())
                            .subscribe(new NetObserver<ResponseBody>(this) {
                                @Override
                                public void doOnSuccess(ResponseBody data) {
                                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show(R.string.login_add_car_success);
                                    BusFactory.getBus().post(new LoginEvent.UpdateEvent());
                                    finish();

                                }
                            });
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show(R.string.login_add_car_modification);
                    finish();

                }
            }


        } else if (i == R.id.item_car_type) {

            if (!cars.isEmpty()) {
                OptionsPickerView build = pickerDialog.build();
                build.setPicker(cars);
                build.show(v);
            } else {
                HttpLoginFactory.getCarType()
                        .subscribe(new NetObserver<List<CarTypeBean>>(this) {
                            @Override
                            public void doOnSuccess(List<CarTypeBean> data) {
                                cars.clear();
                                cars.addAll(data);
                                OptionsPickerView build = pickerDialog.build();
                                build.setPicker(cars);
                                build.show(v);
                            }
                        });
            }


        }
    }

    private LoginCarInforBean getValue() {
        carBean.setCarNumber(itemCarNum.getTextCenter());
        carBean.setContactMobile(itemCarPhone.getTextCenter());
        carBean.setContactName(itemCarOwner.getTextCenter());
        carBean.setCarOwnerId(Utils.getPgetPersonInfor().getId() + "");
        return carBean;
    }

    private boolean check() {
        if (TextUtils.isEmpty(itemCarType.getTextCenter())) {
            showAleart(itemCarType.getTextCenterHide());
            return false;
        }

        if (TextUtils.isEmpty(itemCarNum.getTextCenter())) {
            showAleart(itemCarNum.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemCarOwner.getTextCenter())) {
            showAleart(itemCarOwner.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemCarPhone.getTextCenter())) {
            showAleart(itemCarPhone.getTextCenterHide());
            return false;
        }
        return true;
    }

    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        if (v instanceof ItemView) {
            CarTypeBean carTypeBean = cars.get(options1);
            ((ItemView) v).setTextCenter(carTypeBean.getCarType());
            carBean.setCarType(carTypeBean.getId());
        }
    }
}
