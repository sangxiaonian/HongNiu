package com.hongniu.modulelogin.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.OptionsPickerView;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.utils.PickerDialogUtils;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulelogin.R;
import com.hongniu.modulelogin.entity.LoginAddCarBean;
import com.hongniu.modulelogin.entity.LoginEvent;
import com.hongniu.modulelogin.net.HttpLoginFactory;
import com.sang.common.event.BusFactory;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.ItemView;
import com.sang.common.widget.dialog.BottomAlertDialog;
import com.sang.common.widget.dialog.builder.BottomAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 车辆新增、修改界面
 */
@Route(path = ArouterParamLogin.activity_car_infor)
public class LoginCarInforActivity extends BaseActivity implements View.OnClickListener, OptionsPickerView.OnOptionsSelectListener {

    private ItemView itemCarType;//车辆类型
    private ItemView itemCarNum;//车牌号
    private ItemView itemCarOwner;//车主姓名
    private ItemView itemCarPhone;//车主手机号

    private Button button;
    private boolean isAdd;//是否是添加车辆
    private OptionsPickerView.Builder pickerDialog;
    private LoginAddCarBean carBean;

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

    private List<String> cars;

    @Override
    protected void initData() {
        super.initData();
        cars = new ArrayList<>();
        cars.add("特种车");
        cars.add("小货车");
        cars.add("大货车");
        cars.add("法拉利");
        cars.add("保时捷");
        cars.add("布加迪威龙");


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
            carBean = new LoginAddCarBean();
        } else {
            setToolbarTitle(getString(R.string.login_modification_car));
            itemCarType.setTextCenter("豪华法拉利");
            itemCarNum.setTextCenter("沪A9999");
            itemCarOwner.setTextCenter("男神一号");
            itemCarPhone.setTextCenter("15555555555");
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
                                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show(getString(R.string.deleted_success));
                                    finish();
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bt_save) {
            if (check()) {
                if (isAdd) {
                    HttpLoginFactory.addCar(getValue())
                            .subscribe(new NetObserver<String>(this) {
                                @Override
                                public void doOnSuccess(String data) {
                                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show(R.string.login_add_car_success);
                                    finish();

                                }
                            });
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show(R.string.login_add_car_modification);
                    finish();

                }
            }


        } else if (i == R.id.item_car_type) {
            OptionsPickerView build = pickerDialog.build();
            build.setPicker(cars);
            build.show(v);

        }
    }

    private LoginAddCarBean getValue() {
        carBean.setCarCode("1");
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
            ((ItemView) v).setTextCenter(cars.get(options1));
        }
    }
}
