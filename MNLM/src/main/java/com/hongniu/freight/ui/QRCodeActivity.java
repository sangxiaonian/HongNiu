package com.hongniu.freight.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.widget.ColorImageView;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.fy.androidlibrary.utils.permission.PermissionUtils;
import com.githang.statusbar.StatusBarCompat;

import com.hongniu.freight.R;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;


/**
 * @data 2019/8/23
 * @Author PING
 * @Description 二维码扫描页面
 */
@Route(path = ArouterParamApp.activity_qrcode)
public class QRCodeActivity extends CompanyBaseActivity implements View.OnClickListener, CodeUtils.AnalyzeCallback {

    private ViewGroup llLeft;
    private ViewGroup llRight;
    private TextView tvLeft, tvRight;
    private ColorImageView imgLeft, imgRight;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        init("运单状态查询");
        initView();
        initData();
        initListener();
        llLeft.performClick();

    }

    @Override
    protected void initView() {
        super.initView();
        llLeft = findViewById(R.id.ll_left);
        llRight = findViewById(R.id.ll_right);
        tvLeft = findViewById(R.id.tv_left);
        tvRight = findViewById(R.id.tv_right);
        imgLeft = findViewById(R.id.img_left);
        imgRight = findViewById(R.id.img_right);
    }

    @Override
    protected void initListener() {
        super.initListener();
        llLeft.setOnClickListener(this);
        llRight.setOnClickListener(this);
    }

    protected void init(String title) {
        if (tvToolbarTitle != null) {
            if (tool != null) {
                tool.setBackgroundColor(getResources().getColor(R.color.transparent));
            }
            tvToolbarTitle.setTextColor(Color.WHITE);
            if (imgToolbarLeft != null) {
                imgToolbarLeft.setImageResource(R.drawable.picture_icon_back);
            }
            tvToolbarTitle.setText(title == null ? "" : title);
        }
        StatusBarCompat.setTranslucent(getWindow(), true);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_left && type != 1) {
            type = 1;
            PermissionUtils.applyCamera(this, new PermissionUtils.onApplyPermission() {
                @Override
                public void hasPermission() {
                    CaptureFragment captureFragment = new CaptureFragment();
                    // 为二维码扫描界面设置定制化界面
                    CodeUtils.setFragmentArgs(captureFragment, R.layout.fragment_scan);
                    captureFragment.setAnalyzeCallback(QRCodeActivity.this);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();

                }

                @Override
                public void noPermission() {

                }
            });

            changeState(type);
        } else if (v.getId() == R.id.ll_right && type != 2) {
            type = 2;
            CompanyBaseFragment handFragment = (CompanyBaseFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_hand_input).navigation(mContext);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, handFragment).commit();
            changeState(type);
        }
    }

    private void changeState(int type) {
        tvLeft.setTextColor(getResources().getColor(type == 1 ? R.color.color_of_ff3a30 : R.color.color_of_ffffff));
        imgLeft.setCurrentColor(getResources().getColor(type == 1 ? R.color.color_of_ff3a30 : R.color.color_of_ffffff));
        tvRight.setTextColor(getResources().getColor(type == 2 ? R.color.color_of_ff3a30 : R.color.color_of_ffffff));
        imgRight.setCurrentColor(getResources().getColor(type == 2 ? R.color.color_of_ff3a30 : R.color.color_of_ffffff));
    }

    @Override
    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
         ArouterUtils.getInstance().builder(ArouterParamApp.activity_way_bill).withString(Param.TRAN,result).navigation(this);
         finish();
    }

    @Override
    public void onAnalyzeFailed() {

    }
}
