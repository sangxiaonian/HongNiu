package com.hongniu.modulelogin.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.modulelogin.R;
import com.hongniu.modulelogin.entity.LoginPersonInfor;
import com.hongniu.modulelogin.net.HttpLoginFactory;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.ItemView;

/**
 * 个人资料
 */
@Route(path = ArouterParamLogin.activity_person_infor)
public class LoginPersonInforActivity extends BaseActivity implements View.OnClickListener {

    private ItemView itemName;
    private ItemView itemIdcard;
    private ItemView itemEmail;
    private ItemView itemAddress;
    private ItemView itemAddressDetail;
    private Button btSave;
    public LoginPersonInfor personInfor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_person_infor);
        setToolbarTitle(getString(R.string.login_person));
        initView();
        initData();
        initListener();


    }

    @Override
    protected void initView() {
        super.initView();
        itemName = findViewById(R.id.item_name);
        itemIdcard = findViewById(R.id.item_idcard);
        itemEmail = findViewById(R.id.item_email);
        itemAddress = findViewById(R.id.item_address);
        itemAddressDetail = findViewById(R.id.item_address_detail);
        btSave = findViewById(R.id.bt_save);

    }


    @Override
    protected void initData() {
        super.initData();
        HttpLoginFactory.getPersonInfor().subscribe(new NetObserver<LoginPersonInfor>(this) {

            @Override
            public void doOnSuccess(LoginPersonInfor data) {
                if (data != null) {
                    personInfor = data;
                    itemName.setTextCenter(data.getContact() == null ? "" : data.getContact());
                    itemIdcard.setTextCenter(data.getIdnumber() == null ? "" : data.getIdnumber());
                    itemEmail.setTextCenter(data.getEmail() == null ? "" : data.getEmail());
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(data.getProvince() == null ? "" : data.getProvince());
                    buffer.append(data.getCity() == null ? "" : data.getCity());
                    buffer.append(data.getDistrict() == null ? "" : data.getDistrict());
                    itemAddress.setTextCenter(buffer.toString());
                    itemAddressDetail.setTextCenter(data.getAddress() == null ? "" : data.getAddress());
                } else {
                    personInfor = new LoginPersonInfor();
                }
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        btSave.setOnClickListener(this);
        itemAddress.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bt_save) {
            if (check()) {
                getValues();
                HttpLoginFactory.changePersonInfor(personInfor)
                        .subscribe(new NetObserver<String>(this) {
                            @Override
                            public void doOnSuccess(String data) {
                                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                                finish();
                            }
                        });
            }

        } else if (i == R.id.item_address) {
        }
    }

    private void getValues() {
        if (personInfor == null) {
            personInfor = new LoginPersonInfor();
        }
        personInfor.setContact(itemName.getTextCenter());
        personInfor.setIdnumber(itemIdcard.getTextCenter());
        personInfor.setEmail(itemEmail.getTextCenter());
        personInfor.setAddress(itemAddressDetail.getTextCenter());

    }


    public boolean check() {
        if (TextUtils.isEmpty(itemName.getTextCenter())) {
            showAleart(itemName.getTextCenterHide());
            return false;
        }
        ;
        if (TextUtils.isEmpty(itemIdcard.getTextCenter())) {
            showAleart(itemIdcard.getTextCenterHide());
            return false;
        }
        ;
        if (TextUtils.isEmpty(itemEmail.getTextCenter())) {
            showAleart(itemEmail.getTextCenterHide());
            return false;
        }
        ;
//        if (TextUtils.isEmpty(itemAddress.getTextCenter())) {
//            showAleart(itemAddress.getTextCenterHide());
//            return false;
//        }
        ;
        if (TextUtils.isEmpty(itemAddressDetail.getTextCenter())) {
            showAleart(itemAddressDetail.getTextCenterHide());
            return false;
        }
        ;

        return true;
    }


}
