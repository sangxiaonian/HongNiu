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
import com.hongniu.freight.entity.VerifyInfoBean;
import com.hongniu.freight.entity.VerifyPersonParams;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.Utils;
import com.hongniu.freight.widget.ImageInforView;

/**
 * 作者：  on 2020/2/24.
 * 个人承运人身份认证第二部
 */
@Route(path = ArouterParamMNLM.fragment_attestation_carrier_personal)
public class AttestationCarrierPersonalFragment extends AttestationBaseFragment implements ItemTextView.OnCenterChangeListener, View.OnClickListener {

    private ItemTextView item_name;//姓名
    private ItemTextView item_id_card;//身份证号码
    private ItemTextView item_email;//邮箱
    private ItemTextView item_road_transport_permit;//道路运输许可证
    private ImageInforView img_driver;//道路运输许可证
    private ImageInforView img_qualification;//挂靠协议


    private ImageInforView img_id_card_front;//身份证正面
    private ImageInforView img_id_card_back;//身份证反面


    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.mnlm_fragment_attestation_carrier_personal, null);
        root = inflate;
        item_name = inflate.findViewById(R.id.item_name);
        item_id_card = inflate.findViewById(R.id.item_id_card);
        item_email = inflate.findViewById(R.id.item_email);
        item_road_transport_permit = inflate.findViewById(R.id.item_road_transport_permit);
        bt_sum = inflate.findViewById(R.id.bt_sum);
        img_driver = inflate.findViewById(R.id.img_driver);
        img_qualification = inflate.findViewById(R.id.img_qualification);

        img_id_card_front = inflate.findViewById(R.id.img_id_card_front);
        img_id_card_back = inflate.findViewById(R.id.img_id_card_back);
        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();

        img_id_card_front.setType(8);
        img_id_card_back.setType(8);
        img_driver.setType(13);
        img_qualification.setType(14);

        img_id_card_front.setAttached(this);
        img_id_card_back.setAttached(this);
        img_driver.setAttached(this);
        img_qualification.setAttached(this);

        check(false);
    }

    @Override
    protected void initInfo(VerifyInfoBean verifyInfoBean) {
        VerifyPersonParams idnumIdentity = verifyInfoBean.getPersonCarrier();
        if (idnumIdentity != null) {
            item_email.setTextCenter(idnumIdentity.getEmail());
            item_id_card.setTextCenter(idnumIdentity.getIdnumber());
            item_name.setTextCenter(idnumIdentity.getName());
            item_road_transport_permit.setTextCenter(idnumIdentity.getInputRtpNum());
            img_driver.setImageInfo(idnumIdentity.getRoadTransportPermitImageUrl());
            img_qualification.setImageInfo(idnumIdentity.getAffiliationAgreementImageUrl());
            img_id_card_back.setImageInfo(idnumIdentity.getIdnumberBackImageUrl());
            img_id_card_front.setImageInfo(idnumIdentity.getIdnumberFaceImageUrl());

        }

    }

    @Override
    protected void initListener() {
        super.initListener();
        item_name.setOnCenterChangeListener(this);
        item_id_card.setOnCenterChangeListener(this);
        item_email.setOnCenterChangeListener(this);
        item_road_transport_permit.setOnCenterChangeListener(this);
        bt_sum.setOnClickListener(this);
    }

    private boolean check(boolean showAlert) {
        Utils.setButton(bt_sum, false);
        if (!check(item_name, showAlert)
                || !check(item_id_card, showAlert)
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
        int img_business_licenseState = img_qualification.getState();
        if (img_business_licenseState != 3) {

            if (showAlert) {
                ToastUtils.getInstance().show(getAlert(img_business_licenseState, "挂靠协议"));
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_sum) {
            if (check(true)) {
                VerifyPersonParams params = new VerifyPersonParams();
                params.setName(item_name.getTextCenter());
                params.setIdnumber(item_id_card.getTextCenter());
                params.setEmail(item_email.getTextCenter());
                params.setInputRtpNum(item_road_transport_permit.getTextCenter());
                params.setAffiliationAgreementImageUrl(img_qualification.getImgInfo().getPath());
                params.setRoadTransportPermitImageUrl(img_driver.getImgInfo().getPath());
                params.setIdnumberFaceImageUrl(img_id_card_front.getImgInfo().getPath());
                params.setIdnumberBackImageUrl(img_id_card_back.getImgInfo().getPath());

                HttpAppFactory.verifyCarrierPerson(params)
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

    @Override
    public void onCenterChange(String msg) {
        check(false);
    }


}
