package com.hongniu.modulelogin.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.LoginPersonInfor;
import com.hongniu.baselibrary.entity.UpImgData;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.utils.PickerDialogUtils;
import com.hongniu.baselibrary.utils.PictureSelectorUtils;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulelogin.LoginUtils;
import com.hongniu.modulelogin.R;
import com.hongniu.modulelogin.entity.Citys;
import com.hongniu.modulelogin.entity.NewAreaBean;
import com.hongniu.modulelogin.net.HttpLoginFactory;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.sang.common.event.BusFactory;
import com.sang.common.imgload.ImageLoader;
import com.sang.common.net.error.NetException;
import com.sang.common.net.rx.BaseObserver;
import com.sang.common.net.rx.RxUtils;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.ItemView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

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
    private String headPath;


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
            ImageLoader.getLoader().load(mContext,imageHead,data.getLogoPath());
            itemName.setEnabled(!data.getSubAccStatus());
            itemIdcard.setEnabled(!data.getSubAccStatus());
            itemEmail.setEnabled(!data.getSubAccStatus());

            tvAleart.setVisibility(data.getSubAccStatus()?View.VISIBLE:View.GONE);

            if (data.getSubAccStatus()){
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
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        DeviceUtils.hideSoft(getCurrentFocus());
        if (i == R.id.bt_save) {
            if (check()) {
                getValues();
                Observable<CommonBean<String>> observable;
                if (headPath!=null){

                    final List<String> list=new ArrayList<>();
                    list.add(headPath);


                    observable=   Observable.just(headPath)
                            .map(new Function<String, List<String>>() {
                                @Override
                                public List<String> apply(String s) throws Exception {
                                    List<String> list=new ArrayList<>();
                                    list.add(s);
                                    return list;
                                }
                            })
                            .flatMap(new Function<List<String>, ObservableSource<String>>() {
                                @Override
                                public ObservableSource<String> apply(List<String> strings) throws Exception {
                                    return HttpAppFactory.upImage(4,strings)
                                            .map(new Function<CommonBean<List<UpImgData>>, CommonBean<List<UpImgData>>>() {
                                                @Override
                                                public CommonBean<List<UpImgData>> apply(CommonBean<List<UpImgData>> listCommonBean) throws Exception {
                                                    if (listCommonBean.getCode()!=200){
                                                        throw new NetException(listCommonBean.getCode(),listCommonBean.getMsg());
                                                    }
                                                    return listCommonBean;
                                                }
                                            })
                                            .map(new Function<CommonBean<List<UpImgData>>, String>() {
                                                @Override
                                                public String apply(CommonBean<List<UpImgData>> listCommonBean) throws Exception {
                                                    if (CommonUtils.isEmptyCollection(listCommonBean.getData())){
                                                        return "";
                                                    }else {
                                                       return listCommonBean.getData().get(0).getPath();
                                                    }
                                                }
                                            })
                                            ;
                                }
                            })
                            .flatMap(new Function<String, ObservableSource<CommonBean<String>>>() {
                                @Override
                                public ObservableSource<CommonBean<String>> apply(String s) throws Exception {
                                    personInfor.setLogo(s);
                                    return  HttpLoginFactory.changePersonInfor(personInfor);
                                }
                            });
                }else {
                      observable = HttpLoginFactory.changePersonInfor(personInfor);
                }
                observable
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


        }else  if (i==R.id.img_head){
            PictureSelectorUtils.showHeadPicture(this );
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
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (!CommonUtils.isEmptyCollection(selectList)){
                        LocalMedia media = selectList.get(0);
                        if (media.isCut()){
                            headPath=media.getCutPath();
                            ImageLoader.getLoader().skipMemoryCache().loadHeaed(mContext,imageHead,media.getCutPath());
                        }
                    }
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

                    break;
            }
        }
    }

}
