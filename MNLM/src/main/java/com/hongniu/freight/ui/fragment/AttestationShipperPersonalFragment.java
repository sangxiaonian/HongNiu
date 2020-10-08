package com.hongniu.freight.ui.fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.widget.ItemTextView;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.VerifyPersonParams;
import com.hongniu.freight.entity.VerifyIdNumIdentityBean;
import com.hongniu.freight.entity.VerifyInfoBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.Utils;
import com.hongniu.freight.widget.ImageInforView;

/**
 * 作者：  on 2020/2/24.
 * 个人托运人身份认证第二部
 */
@Route(path = ArouterParamApp.fragment_attestation_shipper_personal)
public class AttestationShipperPersonalFragment  extends AttestationBaseFragment implements View.OnClickListener, ItemTextView.OnCenterChangeListener {


    private ItemTextView item_name;//姓名
    private ItemTextView item_id_card;//身份证号码
    private ItemTextView item_email;//邮箱

    private ImageInforView img_id_card_front;//身份证正面
    private ImageInforView img_id_card_back;//身份证反面

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_attestation_shipper_personal, null);
        root = inflate;
        item_name = inflate.findViewById(R.id.item_name);
        item_id_card = inflate.findViewById(R.id.item_id_card);
        item_email = inflate.findViewById(R.id.item_email);
        bt_sum = inflate.findViewById(R.id.bt_sum);
        img_id_card_front = inflate.findViewById(R.id.img_id_card_front);
        img_id_card_back = inflate.findViewById(R.id.img_id_card_back);
        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();
        img_id_card_front.setType(8);
        img_id_card_back.setType(8);

        img_id_card_front.setAttached(this);
        img_id_card_back.setAttached(this);

        check(false);

    }

    @Override
    protected void initInfo(VerifyInfoBean verifyInfoBean) {
        VerifyPersonParams idnumIdentity = verifyInfoBean.getPersonShipper();
        if (idnumIdentity != null) {
            item_email.setTextCenter(idnumIdentity.getEmail());
            item_id_card.setTextCenter(idnumIdentity.getIdnumber());
            item_name.setTextCenter(idnumIdentity.getName());
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
        bt_sum.setOnClickListener(this);
    }

    private boolean check(boolean showAlert) {
        Utils.setButton(bt_sum, false);
        if (!check(item_name, showAlert)
                || !check(item_id_card, showAlert)
                || !check(item_email, showAlert)

        ) {
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
                params.setIdnumberFaceImageUrl(img_id_card_front.getImgInfo().getPath());
                params.setIdnumberBackImageUrl(img_id_card_back.getImgInfo().getPath());

                HttpAppFactory.verifyShipperPersonal(params)
                        .subscribe(new NetObserver<String>(this) {
                            @Override
                            public void doOnSuccess(String s) {
                                super.doOnSuccess(s);
                                ArouterUtils.getInstance().builder(ArouterParamApp.activity_attestation_face)
                                        .navigation(getContext());
                            }
                        })
                ;

            }
        }
    }

    @Override
    public void onCenterChange(String msg) {
        check(false);
    }
}
