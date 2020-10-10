package com.hongniu.freight.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.fy.androidlibrary.imgload.ImageLoader;
import com.fy.androidlibrary.net.rx.BaseObserver;
import com.fy.androidlibrary.net.rx.RxUtils;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.widget.ItemTextView;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.Citys;
import com.hongniu.freight.entity.InsuranceInfoBean;
import com.hongniu.freight.entity.LoginCreatInsuredBean;
import com.hongniu.freight.entity.NewAreaBean;
import com.hongniu.freight.entity.UpImgData;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.PickerDialogUtils;
import com.hongniu.freight.utils.Utils;
import com.sang.thirdlibrary.picture.PictureClient;
import com.sang.thirdlibrary.picture.utils.PicUtils;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * 新增投保人信息
 * 此页面有新增修改两种类型 参数type决定 0 新增，1修改
 * 修改页面时候需要传入投保人信息参数
 */
@Route(path = ArouterParamApp.activity_insured_info)
public class InsureanceInfodActivity extends CompanyBaseActivity implements View.OnClickListener, OnOptionsSelectListener {

    private ItemTextView itemType;
    private ItemTextView itemName;
    private ItemTextView itemIdcard;
    private ItemTextView itemEmail;
    private ItemTextView itemAddress;
    private ItemTextView itemAddressDetail;
    private ImageView image;//营业执照
    private View llImg;

    private Button btSave;
    public LoginCreatInsuredBean creatInsuredBean = new LoginCreatInsuredBean();
    private OptionsPickerView pickDialog;
    private OptionsPickerView<String> typeDialog;
    public Citys areabean;//所有的区域选择
    private String headPath;

    private List<String> types;
    private int type;//当前页面功能类型 0 新增 1编辑
    InsuranceInfoBean insuranceInforBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnlm_activity_insured_info);
        setWhitToolBar("被保险人信息");
        initView();
        initData();
        initListener();
        changeType((insuranceInforBean == null || insuranceInforBean.getInsuredType() <= 0) ? 0 : insuranceInforBean.getInsuredType() - 1);
    }

    @Override
    protected void initView() {
        super.initView();
        itemType = findViewById(R.id.item_type);
        itemName = findViewById(R.id.item_name);
        itemIdcard = findViewById(R.id.item_idcard);
        itemEmail = findViewById(R.id.item_email);
        itemAddress = findViewById(R.id.item_address);
        itemAddressDetail = findViewById(R.id.item_address_detail);
        llImg = findViewById(R.id.ll_img);
        btSave = findViewById(R.id.bt_save);
        image = findViewById(R.id.img_path);
        pickDialog = PickerDialogUtils.creatPickerDialog(mContext, this)
                .setTitleText("选择地区")
                .setSubmitColor(Color.parseColor("#48BAF3"))
                .build();
        typeDialog = PickerDialogUtils.creatPickerDialog(mContext, this)
                .setTitleText("请选择被保险人身份")
                .setSubmitColor(Color.parseColor("#48BAF3"))
                .build();
    }


    @Override
    protected void initData() {
        super.initData();
        types = Arrays.asList(getResources().getStringArray(R.array.insured_type));
        typeDialog.setPicker(types);
        type = getIntent().getIntExtra(Param.TYPE, 0);
        if (type == 1) {

            insuranceInforBean = getIntent().getParcelableExtra(Param.TRAN);
            //此处为修改房源，对房源数据进行赋值
            creatInsuredBean.setId(insuranceInforBean.getId());
            creatInsuredBean.setUsername(insuranceInforBean.getUsername());
            creatInsuredBean.setIdnumber(insuranceInforBean.getIdnumber());
            creatInsuredBean.setCompanyName(insuranceInforBean.getCompanyName());
            creatInsuredBean.setCompanyCreditCode(insuranceInforBean.getCompanyCreditCode());
            creatInsuredBean.setImageUrl(insuranceInforBean.getImageUrl());
            creatInsuredBean.setEmail(insuranceInforBean.getEmail());
            creatInsuredBean.setProvinceId(insuranceInforBean.getProvinceId());
            creatInsuredBean.setProvince(insuranceInforBean.getProvince());
            creatInsuredBean.setCityId(insuranceInforBean.getCityId());
            creatInsuredBean.setCity(insuranceInforBean.getCity());
            creatInsuredBean.setDistrictId(insuranceInforBean.getDistrictId());
            creatInsuredBean.setDistrict(insuranceInforBean.getDistrict());
            creatInsuredBean.setDistrict(insuranceInforBean.getDistrict());
            creatInsuredBean.setAddress(insuranceInforBean.getAddress());
            creatInsuredBean.setInsuredType(insuranceInforBean.getInsuredType());

            itemType.setEnabled(false);
            try {
                itemType.setTextCenter(types.get(insuranceInforBean.getInsuredType() - 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
            StringBuilder buffer = new StringBuilder();
            buffer.append(creatInsuredBean.getProvince() == null ? "" : creatInsuredBean.getProvince())
                    .append(creatInsuredBean.getCity() == null ? "" : creatInsuredBean.getCity())
                    .append(creatInsuredBean.getDistrict() == null ? "" : creatInsuredBean.getDistrict());

            itemAddress.setTextCenter(buffer.toString());
            itemAddressDetail.setTextCenter(creatInsuredBean.getAddress());
            itemEmail.setTextCenter(creatInsuredBean.getEmail());
            itemIdcard.setTextCenter(insuranceInforBean.getInsuredType() == 2 ? insuranceInforBean.getCompanyCreditCode() : insuranceInforBean.getIdnumber());
            itemName.setTextCenter(insuranceInforBean.getInsuredType() == 2 ? insuranceInforBean.getCompanyName() : insuranceInforBean.getUsername());

            if (creatInsuredBean.getInsuredType() == 2 && !TextUtils.isEmpty(insuranceInforBean.getAbsoluteImageUrl())) {
                ImageLoader.getLoader().load(mContext, image, insuranceInforBean.getAbsoluteImageUrl());

            }
            setToolbarMsgRight("删除");
            tvToolbarRight.setTextColor(getResources().getColor(R.color.color_of_ff3a30));
            setToolbarRightClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HttpAppFactory.deletedInsuredInfor(insuranceInforBean.getId())
                            .subscribe(new NetObserver<String>(InsureanceInfodActivity.this) {
                                @Override
                                public void doOnSuccess(String data) {
                                    finishSuccess(null);
                                }
                            });
                }
            });

        }

    }


    @Override
    protected void initListener() {
        super.initListener();
        btSave.setOnClickListener(this);
        itemAddress.setOnClickListener(this);
        image.setOnClickListener(this);
        itemType.setOnClickListener(this);
    }


    @Override
    public void onClick(final View v) {
        int i = v.getId();
        DeviceUtils.hideSoft(getCurrentFocus());
        if (i == R.id.bt_save) {
            if (check()) {
                getValues();
                Observable<CommonBean<LoginCreatInsuredBean>> observable;
                if (headPath != null && creatInsuredBean.getInsuredType() == 2) {
                    final List<String> list = new ArrayList<>();
                    list.add(headPath);
                    observable = Observable.just(headPath)

                            .flatMap(new Function<String, ObservableSource<String>>() {
                                @Override
                                public ObservableSource<String> apply(String strings) throws Exception {
                                    return HttpAppFactory.upImage(7, strings, null)
                                            .map(new Function<UpImgData, String>() {
                                                @Override
                                                public String apply(UpImgData upImgData) throws Exception {
                                                    return upImgData.getPath();
                                                }
                                            })


                                            ;
                                }
                            })
                            .flatMap(new Function<String, Observable<CommonBean<LoginCreatInsuredBean>>>() {
                                @Override
                                public Observable<CommonBean<LoginCreatInsuredBean>> apply(String s) throws Exception {
                                    creatInsuredBean.setImageUrl(s);
                                    if (type == 0) {
                                        return HttpAppFactory.creatInsuredInfor(creatInsuredBean);
                                    } else {
                                        return HttpAppFactory.upInsuredInfor(creatInsuredBean);

                                    }
                                }
                            });
                } else {
                    if (type == 0) {
                        observable = HttpAppFactory.creatInsuredInfor(creatInsuredBean);
                    } else {
                        observable = HttpAppFactory.upInsuredInfor(creatInsuredBean);

                    }
                }
                observable
                        .subscribe(new NetObserver<LoginCreatInsuredBean>(this) {
                            @Override
                            public void doOnSuccess(LoginCreatInsuredBean data) {

                                finishSuccess(data);
                            }
                        });
            }

        } else if (i == R.id.item_address) {
            if (areabean == null) {
                Utils.getNewAreas(mContext)
                        .compose(RxUtils.<Citys>getSchedulersObservableTransformer())
                        .subscribe(new BaseObserver<Citys>(this) {
                            @Override
                            public void onNext(Citys citys) {
                                areabean = citys;
                                pickDialog.setPicker(areabean.getShengs(), areabean.getShis(), areabean.getQuyus());
                                pickDialog.show(v);
                            }


                        });

            } else {
                pickDialog.show(v);
            }


        } else if (i == R.id.img_path) {
            new PictureClient().startPhoto(this, 1, null, new OnResultCallbackListener() {
                @Override
                public void onResult(List<LocalMedia> result) {
                    if (!CollectionUtils.isEmpty(result)) {
                        LocalMedia media = result.get(0);
                        headPath = PicUtils.getPath(media);
                        ImageLoader.getLoader().skipMemoryCache().load(mContext, image, headPath);
                    }
                }
            });
        } else if (i == R.id.item_type) {//选择身份
            typeDialog.show(v);
        }
    }

    private void finishSuccess(LoginCreatInsuredBean data) {
        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
        Intent intent = new Intent();
        if (data != null) {
            intent.putExtra(Param.TRAN, data.getId());
            setResult(100, intent);
        } else {
            intent.putExtra(Param.TRAN, "");
            setResult(101, intent);
        }
        finish();
    }

    private void getValues() {

        if (creatInsuredBean.getInsuredType() == 1) {
            creatInsuredBean.setUsername(itemName.getTextCenter());
            creatInsuredBean.setIdnumber(itemIdcard.getTextCenter());
        } else if (creatInsuredBean.getInsuredType() == 2) {
            creatInsuredBean.setCompanyName(itemName.getTextCenter());
            creatInsuredBean.setCompanyCreditCode(itemIdcard.getTextCenter());
        }
        creatInsuredBean.setEmail(itemEmail.getTextCenter());
        creatInsuredBean.setAddress(itemAddressDetail.getTextCenter());

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

    private void showAleart(String textCenterHide) {
        ToastUtils.getInstance().show(textCenterHide);
    }


    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        if (areabean != null && v.getId() == R.id.item_address) {
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
            creatInsuredBean.setProvinceId(provinces.getId() + "");
            creatInsuredBean.setProvince(provinces.getName());
            creatInsuredBean.setCityId(city.getId() + "");
            creatInsuredBean.setCity(city.getName());
            creatInsuredBean.setDistrictId(district.getId() + "");
            creatInsuredBean.setDistrict(district.getName());
        } else if (v.getId() == R.id.item_type) {
            changeType(options1);

        }
    }

    /**
     * 更改当前身份信息
     *
     * @param options1
     */
    private void changeType(int options1) {
        itemType.setTextCenter(types.get(options1));

        creatInsuredBean.setInsuredType(options1 + 1);

        itemName.setTextLeft(options1 == 0 ? "姓名" : "企业名称");
        itemName.setTextCenterHide(options1 == 0 ? "请输入您的姓名" : "请输入企业名称");

        itemIdcard.setTextLeft(options1 == 0 ? "身份证号" : "企业编码");
        itemIdcard.setTextCenterHide(options1 == 0 ? "请输入您的身份证号" : "请输入统一社会信用代码或税号");

        llImg.setVisibility(options1 == 0 ? View.GONE : View.VISIBLE);
    }


}

