package com.hongniu.modulelogin.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.modulelogin.R;
import com.hongniu.modulelogin.entity.LoginEvent;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.ItemView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 车辆新增、修改界面
 */
@Route(path = ArouterParamLogin.activity_car_infor)
public class LoginCarInforActivity extends BaseActivity implements View.OnClickListener {

    private ItemView itemCarType;//车辆类型
    private ItemView itemCarNum;//车牌号
    private ItemView itemCarOwner;//车主姓名
    private ItemView itemCarPhone;//车主手机号

    private Button button;
    private boolean isAdd;//是否是添加车辆

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_car_infor);
        initView();
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

        } else {
            setToolbarTitle(getString(R.string.login_modification_car));
            itemCarType.setTextCenter("豪华法拉利");
            itemCarNum.setTextCenter("沪A9999");
            itemCarOwner.setTextCenter("男神一号");
            itemCarPhone.setTextCenter("15555555555");
        }
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
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show(R.string.login_add_car_success);

                }else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show(R.string.login_add_car_modification);
                }
            }


        } else if (i == R.id.item_car_type) {
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show(R.string.login_add_car_success);


        }
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
}
