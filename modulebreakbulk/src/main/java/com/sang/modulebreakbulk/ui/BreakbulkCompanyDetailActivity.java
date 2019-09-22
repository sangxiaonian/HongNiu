package com.sang.modulebreakbulk.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsBreakbulk;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.modulebreakbulk.R;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.modulebreakbulk.entity.BreakbulkCompanyInfoBean;
import com.sang.thirdlibrary.chact.ChactHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 *@data  2019/9/22
 *@Author PING
 *@Description
 * 零担发货物流公司详情页面
 *
 */
@Route(path = ArouterParamsBreakbulk.activity_breakbulk_company_detail)
public class BreakbulkCompanyDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvName;
    private TextView tvAddress;
    private TextView tvContact;
    private TextView tvPhone;
    private TextView tvTel;
    private TextView tvLine;
    private TextView tvPrice;
    private TextView tvCall;
    private TextView tvChat;
    private TextView tvSend;
    BreakbulkCompanyInfoBean infoBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakbulk_company_detail);
        setToolbarTitle("物流公司详情");
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        tvName=findViewById(R.id.tv_name);
        tvAddress=findViewById(R.id.tv_address);
        tvContact=findViewById(R.id.tv_contact_name);
        tvPhone=findViewById(R.id.tv_phone);
        tvTel=findViewById(R.id.tv_tel);
        tvLine=findViewById(R.id.tv_line);
        tvPrice=findViewById(R.id.tv_price);
        tvCall=findViewById(R.id.tv_call);
        tvChat=findViewById(R.id.tv_chat);
        tvSend=findViewById(R.id.tv_send);
    }

    @Override
    protected void initData() {
        super.initData();
          infoBean = getIntent().getParcelableExtra(Param.TRAN);
          if (infoBean!=null){
              tvName.append((TextUtils.isEmpty(infoBean.getCompanyname())?"":infoBean.getCompanyname()));
              tvAddress.append((TextUtils.isEmpty(infoBean.getWorkaddress())?"":infoBean.getWorkaddress()));
              tvContact.append((TextUtils.isEmpty(infoBean.getContact())?"":infoBean.getContact()));
              tvPhone.append((TextUtils.isEmpty(infoBean.getContactPhone())?"":infoBean.getContactPhone()));
              tvTel.append((TextUtils.isEmpty(infoBean.getTel())?"":infoBean.getTel()));
              tvLine.append((TextUtils.isEmpty(infoBean.getTransportLine())?"":infoBean.getTransportLine()));
              tvPrice.append((TextUtils.isEmpty(infoBean.getChargeDirections())?"":infoBean.getChargeDirections()));
          }
    }

    @Override
    protected void initListener() {
        super.initListener();
        tvCall.setOnClickListener(this);
        tvChat.setOnClickListener(this);
        tvSend.setOnClickListener(this);
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.tv_call){
            if (infoBean!=null) {
                CommonUtils.toDial(mContext, infoBean.getContactPhone());
            }
        }else if (v.getId()==R.id.tv_chat){
            if (infoBean!=null) {
                ChactHelper.getHelper().startPriver(mContext, infoBean.getId(), infoBean.getContact());
            }
        }else if (v.getId()==R.id.tv_send){
            ArouterUtils.getInstance()
                    .builder(ArouterParamsBreakbulk.activity_breakbulk_consignment_creat_activity)
                    .withString(Param.TRAN,infoBean.getId())
                    .navigation(mContext);
        }
    }
}
