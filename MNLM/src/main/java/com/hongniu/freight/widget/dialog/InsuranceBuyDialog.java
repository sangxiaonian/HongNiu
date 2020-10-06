package com.hongniu.freight.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.widget.editext.SearchTextWatcher;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.widget.ItemTextView;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.H5Config;
import com.hongniu.freight.entity.InsuranceInfoBean;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.ui.QueryInsurancePriceParams;
import com.hongniu.freight.widget.dialog.inter.DialogControl;


/**
 * 作者： ${桑小年} on 2018/12/1.
 * 购买保险
 */
public class InsuranceBuyDialog implements DialogControl.IDialog, View.OnClickListener, SearchTextWatcher.SearchTextChangeListener {
    private   TextView tv_agreement_insurance;
    private View imgCancel;
    private View btSum;
    private Dialog dialog;
    ItemTextView item_cargo_price;
    ItemTextView item_insurance_name;

    OnInsuranceBuyListener onInsuranceBuyListener;
    private OrderInfoBean orderInfo;
    private InsuranceInfoBean insuranceInfo;

    QueryInsurancePriceParams params;

    public InsuranceBuyDialog(@NonNull Context context) {
        params = new QueryInsurancePriceParams();
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_insurance_buy, null);
        imgCancel = inflate.findViewById(R.id.img_cancel);
        item_cargo_price = inflate.findViewById(R.id.item_cargo_price);
        item_insurance_name = inflate.findViewById(R.id.item_insurance_name);
        tv_agreement_insurance = inflate.findViewById(R.id.tv_agreement_insurance);
        btSum = inflate.findViewById(R.id.bt_sum);
        initListener();
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(inflate);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.dip2px(context, 360));
        dialog.getWindow().setWindowAnimations(R.style.dialog_ani);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
    }

    private void initListener() {
        item_cargo_price.getEtCenter().addTextChangedListener(new SearchTextWatcher(this));
        imgCancel.setOnClickListener(this);
        item_insurance_name.setOnClickListener(this);
        tv_agreement_insurance.setOnClickListener(this);
        btSum.setOnClickListener(this);

    }

    public void setOnInsuranceBuyListener(OnInsuranceBuyListener onInsuranceBuyListener) {
        this.onInsuranceBuyListener = onInsuranceBuyListener;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_cancel) {
            dialog.dismiss();
        } else if (v.getId() == R.id.item_insurance_name) {
            if (onInsuranceBuyListener != null) {
                onInsuranceBuyListener.onChoiceInsurance(this);
            }

        }else if (v.getId() == R.id.tv_agreement_insurance) {
            H5Config h5Config = new H5Config("木牛流马(远恒)货运综合险", Param.insurance_notify, true);
            ArouterUtils.getInstance().builder(ArouterParamApp.activity_h5).withSerializable(Param.TRAN, h5Config).navigation();
        } else if (v.getId() == R.id.bt_sum) {
            if (TextUtils.isEmpty(item_cargo_price.getTextCenter())) {
                ToastUtils.getInstance().show("请输入货物价值");
                return;
            }
            if (TextUtils.isEmpty(item_insurance_name.getTextCenter()) || insuranceInfo == null) {
                ToastUtils.getInstance().show("请选择被保险人");
                return;
            }
            if (onInsuranceBuyListener != null) {
                onInsuranceBuyListener.onEntryClick(this, item_cargo_price.getTextCenter(), orderInfo, insuranceInfo);
            }

        }
    }

    @Override
    public void setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
    }

    @Override
    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
    }

    @Override
    public void show() {
        dialog.show();
    }

    @Override
    public boolean isShowing() {
        return dialog.isShowing();
    }

    @Override
    public void dismiss() {
        dialog.dismiss();
    }

    @Override
    public void onSearchTextChange(String msg) {
        //价格发生改变
        float price = 0;
        try {
            price = Float.parseFloat(msg) * Param.INSURANCE;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        params.setGoodPrice(msg);

        HttpAppFactory.queryInstancePrice(params)
                .subscribe(new NetObserver<String>(null) {
                    @Override
                    public void doOnSuccess(String s) {
                        super.doOnSuccess(s);
                        item_cargo_price.setTextRight(String.format("保费 %s元", s));

                    }
                });

        item_cargo_price.setTextRight(String.format("保费 %s元", ConvertUtils.changeFloat(price, 2)));
    }

    public void setInsuranceInfo(InsuranceInfoBean def) {
        this.insuranceInfo = def;
        if (insuranceInfo != null) {
            item_insurance_name.setTextCenter(insuranceInfo.getUsername());
        }

    }

    public void setOrderInfo(OrderInfoBean bean) {
        this.orderInfo = bean;
        if (orderInfo != null) {
            params.setDestinationLat(orderInfo.getDestinationLat());
            params.setDestinationLon(orderInfo.getDestinationLon());
            params.setStartPlaceLat(orderInfo.getDestinationLat());
            params.setStartPlaceLon(orderInfo.getStartPlaceLon());
        }
    }

    public interface OnInsuranceBuyListener {
        /**
         * 选择被保险人
         *
         * @param insuranceBuyDialog
         */
        void onChoiceInsurance(InsuranceBuyDialog insuranceBuyDialog);

        /**
         * 点击确认按钮
         *
         * @param insuranceBuyDialog
         * @param price
         * @param orderInfo
         * @param insuranceInfo
         */
        void onEntryClick(InsuranceBuyDialog insuranceBuyDialog, String price, OrderInfoBean orderInfo, InsuranceInfoBean insuranceInfo);

    }
}
