package com.hongniu.modulelogin.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.modulelogin.R;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.ItemView;

/**
 * @data 2019/3/3
 * @Author PING
 * @Description 绑定银行卡操作
 */
@Route(path = ArouterParamLogin.activity_login_bind_blank_card)
public class LoginBindBlankCardActivity extends BaseActivity implements View.OnClickListener {
    ItemView itemName;//姓名
    ItemView itemPhone;//手机号
    ItemView itemBlankCardNum;//银行卡号
    ItemView itemIDCard;//身份证号
    private Button btSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_bind_blank_card);
        setToolbarTitle("绑定银行卡");
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        itemName = findViewById(R.id.item_name);//姓名
        itemPhone = findViewById(R.id.item_phone);//手机号
        itemBlankCardNum = findViewById(R.id.item_blank_card_num);//银行卡号
        itemIDCard = findViewById(R.id.item_id_card_num);//身份证号
        btSum = findViewById(R.id.bt_sum);
    }

    @Override
    protected void initListener() {
        super.initListener();
        btSum.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.bt_sum){
            if (check()) {
                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).setText("操作成功");
            }
        }
    }

    private boolean check() {
        if (TextUtils.isEmpty(itemName.getTextCenter())) {
            showAleart(itemName.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemPhone.getTextCenter())) {
            showAleart(itemPhone.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemBlankCardNum.getTextCenter())) {
            showAleart(itemBlankCardNum.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemIDCard.getTextCenter())) {
            showAleart(itemIDCard.getTextCenterHide());
            return false;
        }
        return true;
    }
}
