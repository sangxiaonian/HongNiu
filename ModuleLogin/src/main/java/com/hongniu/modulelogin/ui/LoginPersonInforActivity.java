package com.hongniu.modulelogin.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.LoginPersonInfor;
import com.hongniu.baselibrary.entity.UpImgData;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.utils.PickerDialogUtils;
import com.hongniu.baselibrary.utils.PictureSelectorUtils;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulelogin.R;
import com.hongniu.modulelogin.entity.HttpMedia;
import com.hongniu.modulelogin.net.HttpLoginFactory;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.sang.common.entity.Citys;
import com.sang.common.entity.NewAreaBean;
import com.sang.common.event.BusFactory;
import com.sang.common.imgload.ImageLoader;
import com.sang.common.net.rx.BaseObserver;
import com.sang.common.net.rx.RxUtils;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.LoginUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.ItemView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function5;

/**
 * 个人资料
 */
@Route(path = ArouterParamLogin.activity_person_infor)
public class LoginPersonInforActivity extends BaseActivity implements View.OnClickListener, OnOptionsSelectListener {

    private TextView tvAleart;
    private ItemView itemName;
    private ItemView itemIdcard;
    private ItemView itemEmail;
    private ItemView itemAddress;
    private ItemView itemAddressDetail;
    private ImageView imageHead;
    private Button btSave;
    public LoginPersonInfor personInfor;
    private OptionsPickerView pickDialog;
    public Citys areabean;//所有的区域选择

    private int type;//0 默认实名认证 1司机认证

    //1 2 3 4 分别代表 驾驶证正反面 主负页
    private ViewGroup llDriver1;
    private ViewGroup llDriver2;
    private ViewGroup llCard1;
    private ViewGroup llCard2;
    private ViewGroup llCard3;
    private ViewGroup llCard4;
    private ImageView imgCard1;
    private ImageView imgCard2;
    private ImageView imgCard3;
    private ImageView imgCard4;
    private HttpMedia media1;
    private HttpMedia media2;
    private HttpMedia media3;
    private HttpMedia media4;
    private HttpMedia headMedia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_person_infor);
        setToolbarTitle(getString(R.string.login_person));
        type = getIntent().getIntExtra(Param.TYPE, 0);
        initView();
        initData();
        initListener();


    }

    @Override
    protected void initView() {
        super.initView();
        llDriver1 = findViewById(R.id.ll_driver1);
        llDriver2 = findViewById(R.id.ll_driver2);
        llCard1 = findViewById(R.id.ll_card1);
        llCard2 = findViewById(R.id.ll_card2);
        llCard3 = findViewById(R.id.ll_card3);
        llCard4 = findViewById(R.id.ll_card4);
        imgCard1 = findViewById(R.id.img_card1);
        imgCard2 = findViewById(R.id.img_card2);
        imgCard3 = findViewById(R.id.img_card3);
        imgCard4 = findViewById(R.id.img_card4);
        tvAleart = findViewById(R.id.tv_aleart);
        itemName = findViewById(R.id.item_name);
        itemIdcard = findViewById(R.id.item_idcard);
        itemEmail = findViewById(R.id.item_email);
        itemAddress = findViewById(R.id.item_address);
        itemAddressDetail = findViewById(R.id.item_address_detail);
        btSave = findViewById(R.id.bt_save);
        imageHead = findViewById(R.id.img_head);
        pickDialog = PickerDialogUtils.creatPickerDialog(mContext, this)
                .setTitleText("选择地区")
                .setSubmitColor(Color.parseColor("#48BAF3"))
                .build();
    }


    @Override
    protected void initData() {
        super.initData();
        HttpLoginFactory.getPersonInfor().subscribe(new NetObserver<LoginPersonInfor>(this) {
            @Override
            public void doOnSuccess(LoginPersonInfor data) {
                initInfor(data);
            }
        });

        llDriver1.setVisibility(type == 0 ? View.GONE : View.VISIBLE);
        llDriver2.setVisibility(type == 0 ? View.GONE : View.VISIBLE);


    }

    private void initInfor(LoginPersonInfor data) {
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
            ImageLoader.getLoader().load(mContext, imageHead, data.getLogoPath());
            itemName.setEnabled(!data.getSubAccStatus());
            itemIdcard.setEnabled(!data.getSubAccStatus());
            itemEmail.setEnabled(!data.getSubAccStatus());
            tvAleart.setText(data.getSubAccStatus() ? getString(R.string.login_real_name) : getString(R.string.login_unreal_name));
            if (data.getSubAccStatus()) {
                itemAddressDetail.getEtCenter().requestFocus();
            }
        } else {
            personInfor = new LoginPersonInfor();
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        btSave.setOnClickListener(this);
        itemAddress.setOnClickListener(this);
        imageHead.setOnClickListener(this);
        imgCard1.setOnClickListener(this);
        imgCard2.setOnClickListener(this);
        imgCard3.setOnClickListener(this);
        imgCard4.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        DeviceUtils.hideSoft(getCurrentFocus());
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
                                setResult(2);
                                finish();
                            }
                        });
            }

        } else if (i == R.id.item_address) {
            if (areabean == null) {
                LoginUtils.getNewAreas(mContext)
                        .compose(RxUtils.<Citys>getSchedulersObservableTransformer())
                        .subscribe(new BaseObserver<Citys>(this) {
                            @Override
                            public void onNext(Citys citys) {
                                areabean = citys;
                                pickDialog.setPicker(areabean.getShengs(), areabean.getShis(), areabean.getQuyus());
                                pickDialog.show();
                            }


                        });

            } else {
                pickDialog.show();
            }
        } else if (i == R.id.img_head) {
            PictureSelectorUtils.showHeadPicture(this);
        } else if (v.getId() == R.id.img_card1) {
            PictureSelectorUtils.showOnePicture(this, null, 1);
        } else if (v.getId() == R.id.img_card2) {
            PictureSelectorUtils.showOnePicture(this, null, 2);
        } else if (v.getId() == R.id.img_card3) {
            PictureSelectorUtils.showOnePicture(this, null, 3);
        } else if (v.getId() == R.id.img_card4) {
            PictureSelectorUtils.showOnePicture(this, null, 4);
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
        if (headMedia!=null&&headMedia.getState()==1){
            personInfor.setLogo(headMedia.getPath());
        }
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
//        else if (!CommonUtils.isIdCard(itemIdcard.getTextCenter())) {
//            showAleart(getString(R.string.login_person_idcard_error));
//            return false;
//        }
        if (TextUtils.isEmpty(itemEmail.getTextCenter())) {
            showAleart(itemEmail.getTextCenterHide());
            return false;
        }
//        else if (!CommonUtils.isEmail(itemEmail.getTextCenter())) {
//            showAleart(getString(R.string.login_person_email_error));
//            return false;
//        }
        if (TextUtils.isEmpty(itemAddress.getTextCenter())) {
            showAleart(itemAddress.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemAddressDetail.getTextCenter())) {
            showAleart(itemAddressDetail.getTextCenterHide());
            return false;
        }

        if (headMedia == null) {

        }else if (headMedia.getState()==0){
            showAleart("头像正在上传");
            return false;
        }else if (headMedia.getState()==2){
            showAleart("头像上传失败");
            return false;
        }

        if (type == 1) {
            if (media1 == null) {
                showAleart("行驶证正面尚未上传");
                return false;
            }else if (media1.getState()==0){
                showAleart("行驶证正面正在上传");
                return false;
            }else if (media1.getState()==2){
                showAleart("行驶证正面上传失败");
                return false;
            }
            if (media2 == null) {
                showAleart("行驶证反面尚未上传");
                return false;
            }else if (media2.getState()==0){
                showAleart("行驶证反面正在上传");
                return false;
            }else if (media2.getState()==2){
                showAleart("行驶证反面上传失败");
                return false;
            }
            if (media3 == null) {
                showAleart("行驶证正页尚未上传");
                return false;
            }else if (media3.getState()==0){
                showAleart("行驶证正页正在上传");
                return false;
            }else if (media3.getState()==2){
                showAleart("行驶证正页上传失败");
                return false;
            }
            if (media4 == null) {
                showAleart("行驶证副页尚未上传");
                return false;
            }else if (media4.getState()==0){
                showAleart("行驶证副页正在上传");
                return false;
            }else if (media4.getState()==2){
                showAleart("行驶证副页上传失败");
                return false;
            }
        }

        return true;
    }


    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        if (areabean != null) {
            StringBuffer buffer = new StringBuffer();
            NewAreaBean provinces = areabean.getShengs().get(options1);
            NewAreaBean city;
            if (areabean.getShis().size() > options1 && areabean.getShis().get(options1).size() > options2) {
                city = areabean.getShis().get(options1).get(options2);
            } else {
                city = new NewAreaBean();
            }
            NewAreaBean district;
            if (areabean.getQuyus().size() > options1 && areabean.getQuyus().get(options1).size() > options2
                    && areabean.getQuyus().get(options1).get(options2).size() > options3) {
                district = areabean.getQuyus().get(options1).get(options2).get(options3);
            } else {
                district = new NewAreaBean();
            }

            buffer.append(provinces.getName() == null ? "" : provinces.getName())
                    .append(city.getName() == null ? "" : city.getName())
                    .append(district.getName() == null ? "" : district.getName());

            itemAddress.setTextCenter(buffer.toString());
            personInfor.setProvinceId(provinces.getId() + "");
            personInfor.setProvince(provinces.getName());
            personInfor.setCityId(city.getId() + "");
            personInfor.setCity(city.getName());
            personInfor.setDistrictId(district.getId() + "");
            personInfor.setDistrict(district.getName());


        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
        HttpMedia media = null;
        LocalMedia result = null;
        if (!CommonUtils.isEmptyCollection(selectList)) {
            media = new HttpMedia();
            result = selectList.get(0);
            media.setPath(result.getPath());

        }
        if (resultCode == RESULT_OK && media != null) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    if (result.isCut()) {
                        media.setPath(result.getCutPath());
                        ImageLoader.getLoader().skipMemoryCache().loadHeaed(mContext, imageHead, media.getPath());
                    }
                    headMedia = media;
                    break;
                case 1:
                    media1 = media;
                    llCard1.setVisibility(View.GONE);
                    ImageLoader.getLoader().load(mContext, imgCard1, media.getPath());
                    break;
                case 2:
                    llCard2.setVisibility(View.GONE);
                    media2 = media;
                    ImageLoader.getLoader().load(mContext, imgCard2, media.getPath());
                    break;
                case 3:
                    llCard3.setVisibility(View.GONE);
                    media3 = media;
                    ImageLoader.getLoader().load(mContext, imgCard3, media.getPath());
                    break;
                case 4:
                    llCard4.setVisibility(View.GONE);
                    media4 = media;
                    ImageLoader.getLoader().load(mContext, imgCard4, media.getPath());
                    break;
            }
            creatImgUpLoad(media);

        }
    }


    private void creatImgUpLoad(final HttpMedia media) {
        if (!TextUtils.isEmpty(media.getPath())) {
            List<String> list = new ArrayList<>();
            list.add(media.getPath());
            HttpAppFactory
                    .upImage(4, list)
                    .subscribe(new NetObserver<List<UpImgData>>(null) {
                        @Override
                        public void doOnSuccess(List<UpImgData> data) {
                            if (!CommonUtils.isEmptyCollection(data)) {
                                media.setPath(data.get(0).getPath());
                                media.setState(1);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            media.setState(2);
                        }
                    });

        }
    }
}

