package com.hongniu.freight.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.utils.InfoUtils;
import com.hongniu.thirdlibrary.verify.VerifyTokenBeans;
import com.hongniu.freight.net.HttpAppFactory;
import com.fy.androidlibrary.utils.permission.PermissionUtils;
import com.hongniu.thirdlibrary.verify.VerifyClient;

/**
 *@data  2020/3/1
 *@Author PING
 *@Description
 * 人脸识别
 *
 */
@Route(path = ArouterParamApp.activity_attestation_face)
public class AttestationFaceActivity extends CompanyBaseActivity implements View.OnClickListener, PermissionUtils.onApplyPermission, VerifyClient.OnVerifyListener {

    private TextView btSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attestation_face);
        setWhitToolBar("人脸识别 ");
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        btSum=findViewById(R.id.bt_sum);
    }

    @Override
    protected void initListener() {
        super.initListener();
        btSum.setOnClickListener(this);
        HttpAppFactory.queryMyInfo().subscribe();

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.bt_sum){
            PermissionUtils.applyCamera(this,this);
        }
    }

    @Override
    public void hasPermission() {
        HttpAppFactory.getVerifyToken()
                .subscribe(new NetObserver<VerifyTokenBeans>(this){
                    @Override
                    public void doOnSuccess(VerifyTokenBeans verifyTokenBeans) {
                        super.doOnSuccess(verifyTokenBeans);
                        if (verifyTokenBeans.getIsAuthen()==1){
                            ArouterUtils.getInstance().builder(ArouterParamApp.activity_main)
                                    .navigation(mContext);
                        }else {
                            verifyTokenBeans.setUserID(InfoUtils.getLoginInfo().getId());
                            VerifyClient.getInstance().startVerify(mContext, verifyTokenBeans, AttestationFaceActivity.this);
                        }

                    }
                });
    }

    @Override
    public void noPermission() {

    }

    /**
     * 认证成功
     */
    @Override
    public void onVerifySuccess() {
        HttpAppFactory.uploadResult(1)
                .subscribe(new NetObserver<Object>(this){
                    @Override
                    public void doOnSuccess(Object o) {
                        super.doOnSuccess(o);
                        ArouterUtils.getInstance().builder(ArouterParamApp.activity_main)
                                .navigation(mContext);
                    }
                });

    }

    /**
     * 认证失败
     */
    @Override
    public void onVerifyFail() {
        HttpAppFactory.uploadResult(0)
                .subscribe(new NetObserver<Object>(this){
                    @Override
                    public void doOnSuccess(Object o) {
                        super.doOnSuccess(o);
                        ArouterUtils.getInstance().builder(ArouterParamApp.activity_main)
                                .navigation(mContext);
                    }
                });
    }

    /**
     * 取消认证
     */
    @Override
    public void onVerifyCancel() {

    }
}
