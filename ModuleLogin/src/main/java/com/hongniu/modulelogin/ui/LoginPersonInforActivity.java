package com.hongniu.modulelogin.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.baselibrary.utils.PickerDialogUtils;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulelogin.LoginUtils;
import com.hongniu.modulelogin.R;
import com.hongniu.modulelogin.entity.AreaBeans;
import com.hongniu.modulelogin.entity.LoginAreaBean;
import com.hongniu.baselibrary.entity.LoginPersonInfor;
import com.hongniu.modulelogin.net.HttpLoginFactory;
import com.sang.common.event.BusFactory;
import com.sang.common.net.rx.BaseObserver;
import com.sang.common.net.rx.RxUtils;
import com.sang.common.utils.SharedPreferencesUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.ItemView;

/**
 * 个人资料
 */
@Route(path = ArouterParamLogin.activity_person_infor)
public class LoginPersonInforActivity extends BaseActivity implements View.OnClickListener, OnOptionsSelectListener {

    private ItemView itemName;
    private ItemView itemIdcard;
    private ItemView itemEmail;
    private ItemView itemAddress;
    private ItemView itemAddressDetail;
    private Button btSave;
    public LoginPersonInfor personInfor;
    private OptionsPickerView pickDialog;
    public AreaBeans areabean;//所有的区域选择


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
        pickDialog = PickerDialogUtils.creatPickerDialog(mContext, this)
                .setTitleText("选择地区")
                .setSubmitColor(Color.parseColor("#48BAF3"))
                .build();
    }


    @Override
    protected void initData() {
        super.initData();

        LoginPersonInfor personInfor = Utils.getPersonInfor();
        if (personInfor==null){
            HttpLoginFactory.getPersonInfor().subscribe(new NetObserver<LoginPersonInfor>(this) {

                @Override
                public void doOnSuccess(LoginPersonInfor data) {
                    initInfor(data);
                }
            });
        }else {
            initInfor(personInfor);
        }

    }

    private void initInfor(LoginPersonInfor data) {
        if (data != null) {
            LoginPersonInforActivity.this.personInfor = data;
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
            LoginPersonInforActivity.this.personInfor = new LoginPersonInfor();
        }
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
                                //更新个人信息
                                Utils.savePersonInfor(personInfor);
                                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                                BusFactory.getBus().post(new Event.UpPerson());
                                finish();
                            }
                        });
            }

        } else if (i == R.id.item_address) {
            if (areabean==null) {
                LoginUtils.getAreas(mContext)
                        .compose(RxUtils.<AreaBeans>getSchedulersObservableTransformer())
                        .subscribe(new BaseObserver<AreaBeans>(this) {

                            @Override
                            public void onNext(AreaBeans result) {
                                super.onNext(result);
                                areabean = result;
                                pickDialog.setPicker(areabean.getProvinces(), areabean.getCityBean(), areabean.getDistrict());
                                pickDialog.show();
                            }
                        });
            }else {
                pickDialog.show();
            }


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
        if (TextUtils.isEmpty(itemIdcard.getTextCenter())) {
            showAleart(itemIdcard.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemEmail.getTextCenter())) {
            showAleart(itemEmail.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemAddress.getTextCenter())) {
            showAleart(itemAddress.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemAddressDetail.getTextCenter())) {
            showAleart(itemAddressDetail.getTextCenterHide());
            return false;
        }

        return true;
    }


    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        if (areabean != null) {
            StringBuffer buffer = new StringBuffer();
            LoginAreaBean provinces = areabean.getProvinces().get(options1).getInfor();
            LoginAreaBean city;
            if (areabean.getCityBean().size()>options1&&areabean.getCityBean().get(options1).size()>options2) {
                city = areabean.getCityBean().get(options1).get(options2).getInfor();
            }else {
                city=new LoginAreaBean();
            }
            LoginAreaBean district ;
            if (areabean.getDistrict().size()>options1&&areabean.getDistrict().get(options1).size()>options2
                    &&areabean.getDistrict().get(options1).get(options2).size()>options3){
                  district = areabean.getDistrict().get(options1).get(options2).get(options3).getInfor();
            }else {
                district=new LoginAreaBean();
            }

            buffer.append(provinces.getAreaName())
                    .append(city.getAreaName())
                    .append(district.getAreaName());
            itemAddress.setTextCenter(buffer.toString());
            personInfor.setProvinceId(provinces.getAreaId() + "");
            personInfor.setProvince(provinces.getAreaName());
            personInfor.setCityId(city.getAreaId() + "");
            personInfor.setCity(city.getAreaName());
            personInfor.setDistrictId(district.getAreaId() +"");
            personInfor.setDistrict(district.getAreaName());


        }
    }
}
