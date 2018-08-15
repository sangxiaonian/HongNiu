package com.hongniu.modulelogin.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.modulelogin.R;
import com.hongniu.modulelogin.entity.respond.LoginPersonInfor;
import com.hongniu.modulelogin.net.HttpLoginFactory;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.ItemView;

import java.util.List;

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
                    personInfor=data;

                    itemName.setTextCenter(data.getContact() == null ? "" : data.getContact());
                    itemIdcard.setTextCenter(data.getIdnumber() == null ? "" : data.getIdnumber());
                    itemEmail.setTextCenter(data.getEmail() == null ? "" : data.getEmail());

                    StringBuffer buffer = new StringBuffer();
                    buffer.append(data.getProvince() == null ? "" : data.getProvince());
                    buffer.append(data.getCity() == null ? "" : data.getCity());
                    buffer.append(data.getDistrict() == null ? "" : data.getDistrict());
                    itemAddress.setTextCenter(buffer.toString());
                    itemAddressDetail.setTextCenter(data.getAddress() == null ? "" : data.getAddress());
                }else {
                    personInfor=new LoginPersonInfor();
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
            if (check()){
                getValues();
            }

            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
            finish();

        } else if (i == R.id.item_address) {
        }
    }

    private void getValues() {
        if (personInfor==null){
            personInfor=new LoginPersonInfor();
        }
        //TODO 工作到这里暂停，提交数据

    }


    public boolean check() {
        return getAllChildViews(getWindow().getDecorView());
    }

    private boolean getAllChildViews(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                if (viewchild instanceof ItemView) {
                    if (TextUtils.isEmpty(((ItemView) viewchild).getTextCenter())) {
                        showAleart(((ItemView) viewchild).getTextCenterHide());
                        return false;
                    }
                }
                getAllChildViews(viewchild);
                //再次 调用本身（递归）
            }
        }
        return true;
    }


}
