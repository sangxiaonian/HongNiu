package com.hongniu.supply.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CloseActivityEvent;
import com.hongniu.baselibrary.entity.QueryPayPassword;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.baselibrary.widget.dialog.UpDialog;
import com.hongniu.moduleorder.entity.VersionBean;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.hongniu.supply.R;
import com.sang.common.event.BusFactory;
import com.sang.common.imgload.ImageLoader;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.JLog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;

@Route(path = ArouterParamsApp.activity_main)
public class MainActivity extends BaseActivity implements View.OnClickListener {

    ViewGroup tab1;
    ViewGroup tab2;
    ViewGroup tab3;
    ViewGroup tab4;
    ViewGroup tab5;

    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;

    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;
    ImageView img5;


    Fragment homeFragment, orderFragment, messageFragment, meFragment, currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
        tab1.performClick();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String extra = intent.getStringExtra(Param.TRAN);
        if (extra != null && extra.equals(Param.LOGIN_OUT)) {
            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_login).navigation(mContext);
            finish();
        }
    }

    @Override
    protected boolean reciveClose() {
        return false;
    }

    @Override
    protected void initView() {
        super.initView();
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        tab4 = findViewById(R.id.tab4);
        tab5 = findViewById(R.id.tab5);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);

    }

    @Override
    protected void initData() {
        super.initData();

        //检查版本更新
        checkVersion();
    }


    @Override
    protected void onStart() {
        super.onStart();
        BusFactory.getBus().post(new CloseActivityEvent());
        HttpAppFactory.queryPayPassword()
                .subscribe(new NetObserver<QueryPayPassword>(null) {
                    @Override
                    public void doOnSuccess(QueryPayPassword data) {
                        Utils.setPassword(data.isSetPassWord());
                    }
                });
    }

    /**
     * 检查版本号，确定是否需要更新
     */
    private void checkVersion() {

        HttpOrderFactory.checkVersion()

                .subscribe(new NetObserver<VersionBean>(null) {
                    @Override
                    public void doOnSuccess(VersionBean data) {
                        try {
                            if (data != null && !TextUtils.isEmpty(data.getVersionCode())) {
                                String versionName = DeviceUtils.getVersionName(mContext);
                                String current = versionName.replace(".", "").replace("-debug", "");
                                String needUpdata = data.getVersionCode().replace(".", "");
                                if (Integer.parseInt(current) < Integer.parseInt(needUpdata)) {
                                    showUpAleart(data.getVersionName());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    /**
     * 显示强制更新接口
     */
    public void showUpAleart(String msg) {
        UpDialog alertDialog = new UpDialog(mContext);
        new CenterAlertBuilder()
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        CommonUtils.launchAppDetail(mContext);
                    }

                })
                .hideBtLeft()
                .hideContent()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setDialogTitle(msg)
                .setBtRight("立即更新")
                .creatDialog(alertDialog)
                .show();
    }

    @Override
    protected void initListener() {
        super.initListener();
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);
        tab5.setOnClickListener(this);


    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab1:
            case R.id.tab2:
            case R.id.tab4:
            case R.id.tab5:
                changeTabeState(v.getId());
                break;
            case R.id.tab3:
                ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_create).navigation(this);
                break;
        }
    }

    private void changeTabeState(int id) {
        tv1.setTextColor(getResources().getColor(id == R.id.tab1 ? R.color.color_tabe_select : R.color.color_tabe_unselect));
        tv2.setTextColor(getResources().getColor(id == R.id.tab2 ? R.color.color_tabe_select : R.color.color_tabe_unselect));
        tv4.setTextColor(getResources().getColor(id == R.id.tab4  ? R.color.color_tabe_select : R.color.color_tabe_unselect));
        tv5.setTextColor(getResources().getColor(id == R.id.tab5 ? R.color.color_tabe_select : R.color.color_tabe_unselect));
        img1.setImageResource(id == R.id.tab1 ? R.mipmap.icon_home_selected_46 : R.mipmap.icon_home_unselected_46);
        img2.setImageResource(id == R.id.tab2 ? R.mipmap.icon_gz_selected_46 : R.mipmap.icon_gz_unselected_46);
        img4.setImageResource(id == R.id.tab4 ? R.mipmap.icon_message_selected_46 : R.mipmap.icon_message_unselected_46);
        img5.setImageResource(id == R.id.tab5 ? R.mipmap.icon_me_selected_46 : R.mipmap.icon_me_unselected_46);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }
        switch (id) {
            case R.id.tab1:
                if (homeFragment == null) {

                    homeFragment = (Fragment) ArouterUtils.getInstance().builder(ArouterParamsApp.fragment_home_fragment).navigation(mContext);
                    fragmentTransaction.add(R.id.content, homeFragment);
                } else {
                    fragmentTransaction.show(homeFragment);
                }
                currentFragment = homeFragment;
                break;
            case R.id.tab2:
                if (orderFragment == null) {
                    orderFragment = (Fragment) ArouterUtils.getInstance().builder(ArouterParamOrder.fragment_order_main).navigation(mContext);
                    fragmentTransaction.add(R.id.content, orderFragment);
                } else {
                    fragmentTransaction.show(orderFragment);
                }
                currentFragment = orderFragment;
                break;

            case R.id.tab4:
                if (messageFragment == null) {
                    messageFragment = (Fragment) ArouterUtils.getInstance().builder(ArouterParamOrder.fragment_order_main).navigation(mContext);
                    fragmentTransaction.add(R.id.content, messageFragment);
                } else {
                    fragmentTransaction.show(messageFragment);
                }

                currentFragment = messageFragment;

                break;
            case R.id.tab5:
                if (meFragment == null) {
                    meFragment = (Fragment) ArouterUtils.getInstance().builder(ArouterParamLogin.fragment_login_my).navigation(mContext);
                    fragmentTransaction.add(R.id.content, meFragment);
                } else {
                    fragmentTransaction.show(meFragment);
                }

                currentFragment = meFragment;

                break;

        }

        fragmentTransaction.commitAllowingStateLoss();


    }


}
