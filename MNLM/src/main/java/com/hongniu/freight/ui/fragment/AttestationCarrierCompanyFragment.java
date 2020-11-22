package com.hongniu.freight.ui.fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.companylibrary.config.ArouterParamMNLM;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.widget.ItemTextView;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.VerifyCompanyParams;
import com.hongniu.freight.entity.VerifyInfoBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.Utils;
import com.hongniu.freight.widget.ImageInforView;

/**
 * 作者：  on 2020/2/24.
 * 公司承运人身份认证第二部
 */
@Route(path = ArouterParamMNLM.fragment_attestation_carrier_company)
public class AttestationCarrierCompanyFragment extends AttestationBaseFragment implements View.OnClickListener, ItemTextView.OnCenterChangeListener {

    private ItemTextView item_company_name;//姓名
    private ItemTextView item_company_address;//姓名
    private ItemTextView item_name;//姓名
    private ItemTextView item_id_card;//身份证号码
    private ItemTextView item_road_transport_permit;//道路运输许可证

    private ItemTextView item_phone;//电话号码
    private ItemTextView item_email;//邮箱
    private ImageInforView img_driver;//道路运输许可证
    private ImageInforView img_business_license;//营业执照
    private ImageInforView img_id_card_front;//身份证正面
    private ImageInforView img_id_card_back;//身份证反面


    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.mnlm_fragment_attestation_carrier_company, null);
        img_id_card_front = inflate.findViewById(R.id.img_id_card_front);
        img_id_card_back = inflate.findViewById(R.id.img_id_card_back);
        item_company_name = inflate.findViewById(R.id.item_company_name);
        item_company_address = inflate.findViewById(R.id.item_company_address);
        item_road_transport_permit = inflate.findViewById(R.id.item_road_transport_permit);
        item_name = inflate.findViewById(R.id.item_name);
        item_id_card = inflate.findViewById(R.id.item_id_card);
        item_phone = inflate.findViewById(R.id.item_phone);
        item_email = inflate.findViewById(R.id.item_email);
        bt_sum = inflate.findViewById(R.id.bt_sum);
        img_driver = inflate.findViewById(R.id.img_driver);
        img_business_license = inflate.findViewById(R.id.img_business_license);
        root = inflate;
        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();

        img_id_card_front.setType(8);
        img_id_card_back.setType(8);
        img_driver.setType(13);
        img_business_license.setType(7);
        img_id_card_front.setAttached(this);
        img_id_card_back.setAttached(this);
        img_driver.setAttached(this);
        img_business_license.setAttached(this);
        check(false);


    }

    @Override
    protected void initListener() {
        super.initListener();
//        img_driver.setOnClickListener(this);
//        img_business_license.setOnClickListener(this);
        bt_sum.setOnClickListener(this);
        item_company_name.setOnCenterChangeListener(this);
        item_road_transport_permit.setOnCenterChangeListener(this);
        item_company_address.setOnCenterChangeListener(this);
        item_name.setOnCenterChangeListener(this);
        item_id_card.setOnCenterChangeListener(this);
        item_phone.setOnCenterChangeListener(this);
        item_email.setOnCenterChangeListener(this);


//        img_driver.setOnClickListener(null);
//        img_business_license.setOnClickListener(null);

    }

    @Override
    protected void initInfo(VerifyInfoBean verifyInfoBean) {
        VerifyCompanyParams identity = verifyInfoBean.getCompanyCarrier();
        //身份认证信息
        if (identity != null) {
            item_company_address.setTextCenter(identity.getCompanyAddress());
            item_company_name.setTextCenter(identity.getCompanyName());
            item_email.setTextCenter(identity.getContactEmail());
            item_name.setTextCenter(identity.getCompanyName());
            item_id_card.setTextCenter(identity.getIdnumber());
            item_road_transport_permit.setTextCenter(identity.getInputRtpNum());
            item_phone.setTextCenter(identity.getContactMobile());
            img_business_license.setImageInfo(identity.getBusinessLicenseImageUrl());
            img_driver.setImageInfo(identity.getRoadTransportPermitImageUrl());
            img_id_card_back.setImageInfo(identity.getIdnumberBackImageUrl());
            img_id_card_front.setImageInfo(identity.getIdnumberFaceImageUrl());

        }
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_sum) {
            if (check(true)) {
                VerifyCompanyParams params = new VerifyCompanyParams();
                params.setCompanyAddress(item_company_address.getTextCenter());
                params.setCompanyName(item_company_name.getTextCenter());
                params.setContactName(item_name.getTextCenter());
                params.setContactEmail(item_email.getTextCenter());
                params.setContactMobile(item_phone.getTextCenter());
                params.setInputRtpNum(item_road_transport_permit.getTextCenter());
                params.setBusinessLicenseImageUrl(img_business_license.getImgInfo().getPath());
                params.setRoadTransportPermitImageUrl(img_driver.getImgInfo().getPath());
                params.setContactMobile(item_phone.getTextCenter());
                params.setIdnumber(item_id_card.getTextCenter());
                params.setName(item_name.getTextCenter());

                params.setIdnumberFaceImageUrl(img_id_card_front.getImgInfo().getPath());
                params.setIdnumberBackImageUrl(img_id_card_back.getImgInfo().getPath());

                HttpAppFactory.verifyCarrierCompany(params)
                        .subscribe(new NetObserver<String>(this) {
                            @Override
                            public void doOnSuccess(String s) {
                                super.doOnSuccess(s);
                                Utils.jump2FaceAttestation(getContext());
                            }
                        });

            }
        }
    }


    private boolean check(boolean showAlert) {
        Utils.setButton(bt_sum, false);
        if (!check(item_company_name, showAlert)
                || !check(item_company_address, showAlert)
                || !check(item_name, showAlert)
                || !check(item_phone, showAlert)
                || !check(item_email, showAlert)
                || !check(item_road_transport_permit, showAlert)


        ) {
            return false;
        }

        int driverState = img_driver.getState();
        if (driverState != 3) {
            if (showAlert) {
                ToastUtils.getInstance().show(getAlert(driverState, "道路运输许可证"));
            }
            return false;
        }
        int img_business_licenseState = img_business_license.getState();
        if (img_business_licenseState != 3) {

            if (showAlert) {
                ToastUtils.getInstance().show(getAlert(img_business_licenseState, "营业执照"));
            }
            return false;
        }
        if (img_id_card_front.getState() != 3) {

            if (showAlert) {
                ToastUtils.getInstance().show(getAlert(img_id_card_front.getState(), "身份证正面"));
            }
            return false;
        }
        if (img_id_card_back.getState() != 3) {

            if (showAlert) {
                ToastUtils.getInstance().show(getAlert(img_id_card_back.getState(), "身份证反面"));
            }
            return false;
        }
        Utils.setButton(bt_sum, true);
        return true;
    }


    private boolean check(ItemTextView itemTextView, boolean showAlert) {
        if (TextUtils.isEmpty(itemTextView.getTextCenter())) {
            if (showAlert) {
                ToastUtils.getInstance().show(itemTextView.getTextCenterHide());
            }
            return false;
        }

        return true;
    }

    @Override
    public void onCenterChange(String msg) {
        check(false);
    }


}
