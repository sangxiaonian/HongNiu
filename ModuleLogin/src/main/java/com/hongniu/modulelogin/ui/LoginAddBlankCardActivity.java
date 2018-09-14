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
import com.sang.common.event.BusFactory;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.ItemView;

/**
 * 绑定银行卡
 */
@Route(path = ArouterParamLogin.activity_login_add_blank_card)
public class LoginAddBlankCardActivity extends BaseActivity implements View.OnClickListener {

    ItemView itemBlankName;
    ItemView itemBlankAddress;//开户行
    ItemView itemBlankCardNum;//开户行
    ItemView itemOwnerName;//持卡人姓名
    private Button btSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_add_blank_card);
        setToolbarTitle("添加收款方式");
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        itemBlankName = findViewById(R.id.item_blank_name);
        itemBlankAddress = findViewById(R.id.item_blank_address);
        itemOwnerName = findViewById(R.id.item_owner_name);
        itemBlankCardNum = findViewById(R.id.item_blank_card_num);
        btSum = findViewById(R.id.bt_sum);

    }

    @Override
    protected void initListener() {
        super.initListener();
        btSum.setOnClickListener(this);
    }

    private boolean check() {
        if (TextUtils.isEmpty(itemBlankName.getTextCenter())) {
            showAleart(itemBlankName.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemBlankAddress.getTextCenter())) {
            showAleart(itemBlankAddress.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemOwnerName.getTextCenter())) {
            showAleart(itemOwnerName.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemBlankCardNum.getTextCenter())) {
            showAleart(itemBlankCardNum.getTextCenterHide());
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bt_sum) {
            if (check()){
                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show(R.string.login_add_car_success);
                finish();
            }

        }
    }
}
