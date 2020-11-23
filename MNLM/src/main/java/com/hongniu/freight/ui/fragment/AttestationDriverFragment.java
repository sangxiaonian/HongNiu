package com.hongniu.freight.ui.fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamMNLM;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.widget.ItemTextView;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.VerifyInfoBean;
import com.hongniu.freight.entity.VerifyPersonParams;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.Utils;
import com.hongniu.freight.widget.ImageInforView;

/**
 * 作者：  on 2020/2/24.
 * 司机身份认证
 */
@Route(path = ArouterParamMNLM.fragment_attestation_driver)
public class AttestationDriverFragment extends AttestationBaseFragment implements ItemTextView.OnCenterChangeListener, View.OnClickListener, ImageInforView.UpLoadFinishListener {
    private ItemTextView item_name;//姓名
    private ItemTextView item_id_card;//身份证号码
    private ItemTextView item_email;//邮箱
    private ItemTextView item_number;//从业资格证编号
    private ImageInforView img_driver;//驾照
    private ImageInforView img_driver1;//驾照
    private ImageInforView img_qualification;//从业资格证
    private ImageInforView img_id_card_front;//身份证正面
    private ImageInforView img_id_card_back;//身份证反面


    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.mnlm_fragment_attestation_driver, null);
        item_name = inflate.findViewById(R.id.item_name);
        img_driver1 = inflate.findViewById(R.id.img_driver1);
        item_id_card = inflate.findViewById(R.id.item_id_card);
        item_email = inflate.findViewById(R.id.item_email);
        bt_sum = inflate.findViewById(R.id.bt_sum);
        img_driver = inflate.findViewById(R.id.img_driver);
        item_number = inflate.findViewById(R.id.item_number);
        img_qualification = inflate.findViewById(R.id.img_qualification);
        root = inflate;
        img_id_card_front = inflate.findViewById(R.id.img_id_card_front);
        img_id_card_back = inflate.findViewById(R.id.img_id_card_back);
        return inflate;
    }


    @Override
    protected void initData() {
        super.initData();
        img_driver.setAttached(this);
        img_driver1.setAttached(this);
        img_qualification.setAttached(this);
        img_id_card_front.setAttached(this);
        img_id_card_back.setAttached(this);
  img_driver.setUpLoadFinishListener(this);
        img_driver1.setUpLoadFinishListener(this);
        img_qualification.setUpLoadFinishListener(this);
        img_id_card_front.setUpLoadFinishListener(this);
        img_id_card_back.setUpLoadFinishListener(this);

        img_driver.setType(12);
        img_driver1.setType(12);
        img_qualification.setType(15);
        img_id_card_front.setType(8);
        img_id_card_back.setType(8);
        check(false);
    }

    @Override
    protected void initInfo(VerifyInfoBean verifyInfoBean) {
        VerifyPersonParams idnumIdentity = verifyInfoBean.getDriver();
        if (idnumIdentity != null) {
            item_email.setTextCenter(idnumIdentity.getEmail());
            item_id_card.setTextCenter(idnumIdentity.getIdnumber());
            item_number.setTextCenter(idnumIdentity.getInputQcNum());
            item_name.setTextCenter(idnumIdentity.getName());
            img_driver.setImageInfo(idnumIdentity.getFaceDLImageUrl());
            img_driver1.setImageInfo(idnumIdentity.getBackDLImageUrl());
            img_qualification.setImageInfo(idnumIdentity.getQualificationCertificateImageUrl());
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
        item_number.setOnCenterChangeListener(this);
        bt_sum.setOnClickListener(this);

    }

    private boolean check(boolean showAlert) {
        Utils.setButton(bt_sum, false);
        if (!check(item_name, showAlert)
                || !check(item_id_card, showAlert)
                || !check(item_email, showAlert)
//                || !check(item_number, showAlert)

        ) {
            return false;
        }


        if (img_driver.getState() != 3) {

            if (showAlert) {
                ToastUtils.getInstance().show(getAlert(img_driver.getState(), "驾照主页"));
            }
            return false;
        }
        if (img_driver1.getState() != 3) {

            if (showAlert) {
                ToastUtils.getInstance().show(getAlert(img_driver1.getState(), "驾照副页"));
            }
            return false;
        }
//        if (img_qualification.getState() != 3) {
//
//            if (showAlert) {
//                ToastUtils.getInstance().show(getAlert(img_qualification.getState(), "从业资格证"));
//            }
//            return false;
//        }
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
                params.setEmail(item_email.getTextCenter());
                params.setIdnumber(item_id_card.getTextCenter());
                params.setName(item_name.getTextCenter());
                params.setInputQcNum(item_number.getTextCenter());
                params.setFaceDLImageUrl(img_driver.getImgInfo().getPath());
                params.setBackDLImageUrl(img_driver1.getImgInfo().getPath());
                if (img_qualification.getImgInfo()!=null) {
                    params.setQualificationCertificateImageUrl(img_qualification.getImgInfo().getPath());
                }
                params.setIdnumberFaceImageUrl(img_id_card_front.getImgInfo().getPath());
                params.setIdnumberBackImageUrl(img_id_card_back.getImgInfo().getPath());

                HttpAppFactory.verifyDriver(params)
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


    @Override
    public void onLoadFinish() {
        check(false);
    }
}
