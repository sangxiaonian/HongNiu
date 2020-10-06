package com.hongniu.supply.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.ModuleBaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.supply.R;
import com.sang.common.widget.ColorImageView;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.List;

import static com.hongniu.baselibrary.arouter.ArouterParamsApp.activity_way_bill;

/**
 * @data 2019/8/23
 * @Author PING
 * @Description 二维码扫描页面
 */
@Route(path = ArouterParamsApp.activity_qrcode)
public class QRCodeActivity extends ModuleBaseActivity implements View.OnClickListener, CodeUtils.AnalyzeCallback {

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
            tvToolbarTitle.setTextColor(getResources().getColor(R.color.white));
            if (imgToolbarLeft != null) {
                imgToolbarLeft.setImageResource(R.mipmap.icon_back_w_36);
            }
            tvToolbarTitle.setText(title == null ? "" : title);
        }
        StatusBarCompat.setTranslucent(getWindow(), true);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_left && type != 1) {
            type = 1;
            XXPermissions.with(this)
                    .permission(Permission.CAMERA) //支持请求安装权限和悬浮窗权限
                    .request(new OnPermission() {
                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            CaptureFragment captureFragment = new CaptureFragment();
                            // 为二维码扫描界面设置定制化界面
                            CodeUtils.setFragmentArgs(captureFragment, R.layout.fragment_scan);
                            captureFragment.setAnalyzeCallback(QRCodeActivity.this);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();

                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            llRight.performClick();
                        }
                    });
            changeState(type);
        } else if (v.getId() == R.id.ll_right && type != 2) {
            type = 2;
            Fragment handFragment = (Fragment) ArouterUtils.getInstance().builder(ArouterParamsApp.fragment_hand_input).navigation(mContext);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, handFragment).commit();
            changeState(type);
        }
    }

    private void changeState(int type) {
        tvLeft.setTextColor(getResources().getColor(type == 1 ? R.color.tool_right : R.color.white));
        imgLeft.setCurrentColor(getResources().getColor(type == 1 ? R.color.tool_right : R.color.white));
        tvRight.setTextColor(getResources().getColor(type == 2 ? R.color.tool_right : R.color.white));
        imgRight.setCurrentColor(getResources().getColor(type == 2 ? R.color.tool_right : R.color.white));
    }

    @Override
    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
         ArouterUtils.getInstance().builder(activity_way_bill).withString(Param.TRAN,result).navigation(this);
         finish();
    }

    @Override
    public void onAnalyzeFailed() {

    }
}
