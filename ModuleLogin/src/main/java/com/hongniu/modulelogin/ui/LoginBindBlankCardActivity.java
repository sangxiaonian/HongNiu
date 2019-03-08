package com.hongniu.modulelogin.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.entity.LoginBean;
import com.hongniu.baselibrary.entity.LoginPersonInfor;
import com.hongniu.baselibrary.entity.QueryBlankInforsBean;
import com.hongniu.baselibrary.utils.PickerDialogUtils;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulelogin.R;
import com.hongniu.modulelogin.entity.LoginBlindBlankParams;
import com.hongniu.modulelogin.net.HttpLoginFactory;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.ItemView;

import java.util.List;

/**
 * @data 2019/3/3
 * @Author PING
 * @Description 绑定银行卡操作
 */
@Route(path = ArouterParamLogin.activity_login_bind_blank_card)
public class LoginBindBlankCardActivity extends BaseActivity implements View.OnClickListener, OnOptionsSelectListener {
    ItemView itemName;//姓名
    ItemView itemPhone;//手机号
    ItemView itemBlankCardNum;//银行卡号
    ItemView itemIDCard;//身份证号
    TextView tvHuaAleart;//身份证号
    private Button btSum;
    private ItemView itemBlank;
    private List<QueryBlankInforsBean> blanksInfors;//银行卡信息
    private OptionsPickerView<QueryBlankInforsBean> pickDialog;
    private String blankID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_bind_blank_card);
        setToolbarTitle("绑定银行卡");
        initView();
        initData();
        initListener();
        queryBlank();
    }

    @Override
    protected void initView() {
        super.initView();
        itemName = findViewById(R.id.item_name);//姓名
        itemBlank = findViewById(R.id.item_blank);
        tvHuaAleart = findViewById(R.id.tv_hua_aleart);

        itemPhone = findViewById(R.id.item_phone);//手机号
        itemBlankCardNum = findViewById(R.id.item_blank_card_num);//银行卡号
        itemIDCard = findViewById(R.id.item_id_card_num);//身份证号
        btSum = findViewById(R.id.bt_sum);
        pickDialog = PickerDialogUtils.creatPickerDialog(mContext, this)
                .setTitleText("请选择银行")
                .setSubmitColor(Color.parseColor("#48BAF3"))
                .build();

    }

    @Override
    protected void initData() {
        super.initData();
        LoginPersonInfor personInfor = Utils.getPersonInfor();
        itemName.setTextCenter(personInfor.getContact());
        itemPhone.setTextCenter(personInfor.getMobile());
        itemIDCard.setTextCenter(personInfor.getIdnumber());
        tvHuaAleart.setVisibility(View.GONE);
        itemName.setEnabled(false);
        itemName.setEditable(false);
        itemPhone.setEnabled(false);
        itemPhone.setEditable(false);
        itemIDCard.setEnabled(false);
        itemIDCard.setEditable(false);

    }

    @Override
    protected void initListener() {
        super.initListener();
        btSum.setOnClickListener(this);
        itemBlank.setOnClickListener(this);
    }

    private void queryBlank() {
        HttpLoginFactory.queryBlanks()
                .subscribe(new NetObserver<List<QueryBlankInforsBean>>(this) {
                    @Override
                    public void doOnSuccess(List<QueryBlankInforsBean> data) {
                        blanksInfors = data;
                        pickDialog.setPicker(blanksInfors);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        DeviceUtils.closeSoft(this);
        if (v.getId() == R.id.bt_sum) {
            if (check()) {
                LoginBlindBlankParams params = new LoginBlindBlankParams();
                params.setType("1");
                params.setBankId(blankID);
                params.setCardNo(itemBlankCardNum.getTextCenter());
                params.setAccountName(itemName.getTextCenter());
                params.setIdnumber(itemIDCard.getTextCenter());
                params.setLinkAccountType("0");
                HttpLoginFactory.bindBlanks(params).subscribe(new NetObserver<String>(this) {
                    @Override
                    public void doOnSuccess(String data) {
                        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                        finish();
                    }
                });
            }
        } else if (v.getId() == R.id.item_blank) {
            if (blanksInfors != null) {
                pickDialog.show();
            } else {
                queryBlank();
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
        if (TextUtils.isEmpty(itemBlank.getTextCenter())) {
            showAleart(itemBlank.getTextCenterHide());
            return false;
        }
        return true;
    }

    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        itemBlank.setTextCenter(blanksInfors.get(options1).getName());
        blankID = blanksInfors.get(options1).getId();
    }
}
