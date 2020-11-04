package com.hongniu.freight.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.imgload.ImageLoader;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.companylibrary.config.ArouterParamMNLM;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hedgehog.ratingbar.RatingBar;
import com.hongniu.freight.R;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.entity.AppraiseParams;
import com.hongniu.freight.entity.QueryAppraiseInfo;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.Utils;

/**
 * @data 2020/3/13
 * @Author PING
 * @Description 评价
 */
@Route(path = ArouterParamMNLM.activity_appraise)
public class AppraiseActivity extends CompanyBaseActivity implements View.OnClickListener {

    String id;
    RoleOrder role;
    private ViewGroup rl_platform;//平台
    private ImageView img_platform;//平台
    private TextView tv_platform;//平台
    private RatingBar view_start_platform;//平台
    private ViewGroup rl_shipper;//托运人
    private ImageView img_shipper;//托运人
    private TextView tv_shipper;//托运人
    private RatingBar view_start_shipper;//托运人
    private ViewGroup rl_carrier;//承运人
    private ImageView img_carrier;//承运人
    private TextView tv_carrier;//承运人
    private RatingBar view_start_carrier;//承运人
    private ViewGroup rl_driver;//司机
    private ImageView img_driver;//司机
    private TextView tv_driver;//司机
    private RatingBar view_start_driver;//司机
    private TextView bt_sum;//提交按钮
    private EditText et;//评价内容

    AppraiseParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnlm_activity_appraise);
        setWhitToolBar("评价");
        id = getIntent().getStringExtra(Param.TRAN);
        role = (RoleOrder) getIntent().getSerializableExtra(Param.TYPE);
        initView();
        initData();
        initListener();
        queryInfo();
    }

    @Override
    protected void initView() {
        super.initView();
        rl_platform = findViewById(R.id.rl_platform);
        img_platform = findViewById(R.id.img_platform);
        tv_platform = findViewById(R.id.tv_platform);
        view_start_platform = findViewById(R.id.view_start_platform);
        rl_shipper = findViewById(R.id.rl_shipper);
        img_shipper = findViewById(R.id.img_shipper);
        tv_shipper = findViewById(R.id.tv_shipper);
        view_start_shipper = findViewById(R.id.view_start_shipper);
        rl_carrier = findViewById(R.id.rl_carrier);
        img_carrier = findViewById(R.id.img_carrier);
        tv_carrier = findViewById(R.id.tv_carrier);
        view_start_carrier = findViewById(R.id.view_start_carrier);
        rl_driver = findViewById(R.id.rl_driver);
        img_driver = findViewById(R.id.img_driver);
        tv_driver = findViewById(R.id.tv_driver);
        view_start_driver = findViewById(R.id.view_start_driver);
        bt_sum = findViewById(R.id.bt_sum);
        et = findViewById(R.id.et);
    }

    @Override
    protected void initData() {
        super.initData();
        initRole(role);
        params = new AppraiseParams();
    }

    @Override
    protected void initListener() {
        super.initListener();
        view_start_platform.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                params.setStaffScore((int) RatingCount);
                check(false);
            }
        });
        view_start_carrier.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                params.setOwnerScore((int) RatingCount);
                check(false);

            }
        });
        view_start_driver.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                params.setDriverScore((int) RatingCount);
                check(false);

            }
        });
        view_start_shipper.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                params.setGoodsScore((int) RatingCount);
                check(false);

            }
        });

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                check(false);
            }
        });

        bt_sum.setOnClickListener(this);
    }

    private void queryInfo() {
        HttpAppFactory.queryAppraise(id)
                .subscribe(new NetObserver<QueryAppraiseInfo>(this) {
                    @Override
                    public void doOnSuccess(QueryAppraiseInfo queryAppraiseInfo) {
                        super.doOnSuccess(queryAppraiseInfo);
                        initInfo(queryAppraiseInfo);
                    }
                });
    }

    private void initInfo(QueryAppraiseInfo infoBean) {
        ImageLoader.getLoader().loadHeaed(mContext, img_platform, infoBean.getStaffLogo());
        tv_platform.setText(String.format("平台员工: %s", infoBean.getStaffName()));
        ImageLoader.getLoader().loadHeaed(mContext, img_shipper, infoBean.getUserLogo());
        tv_shipper.setText(String.format("托运人: %s", infoBean.getUserName()));
        ImageLoader.getLoader().loadHeaed(mContext, img_carrier, infoBean.getOwnerLogo());
        tv_carrier.setText(String.format("承运人: %s", infoBean.getOwnerName()));
        ImageLoader.getLoader().loadHeaed(mContext, img_driver, infoBean.getDriverLogo());
        tv_driver.setText(String.format("司机: %s", infoBean.getDriverName()));
    }

    /**
     * 根据角色更ＵＩ
     *
     * @param role
     */
    private void initRole(RoleOrder role) {
        rl_shipper.setVisibility(role == RoleOrder.SHIPPER ? View.GONE : View.VISIBLE);
        rl_platform.setVisibility(role == RoleOrder.PLATFORM ? View.GONE : View.VISIBLE);
        rl_carrier.setVisibility(role == RoleOrder.CARRIER ? View.GONE : View.VISIBLE);
        rl_driver.setVisibility(role == RoleOrder.DRIVER ? View.GONE : View.VISIBLE);
    }


    private boolean check(boolean show) {
        Utils.setButton(bt_sum, false);
        if (view_start_platform.isShown() && params.getStaffScore() == 0) {
            if (show) {
                ToastUtils.getInstance().show("请评价平台");
            }
            return false;
        }
        if (view_start_shipper.isShown() && params.getGoodsScore() == 0) {
            if (show) {
                ToastUtils.getInstance().show("请评价托运人");
            }
            return false;
        }
        if (view_start_carrier.isShown() && params.getOwnerScore() == 0) {
            if (show) {
                ToastUtils.getInstance().show("请评价承运人");
            }
            return false;
        }
        if (view_start_driver.isShown() && params.getDriverScore() == 0) {
            if (show) {
                ToastUtils.getInstance().show("请评价司机");
            }
            return false;
        }
        if (TextUtils.isEmpty(et.getText().toString().trim())) {
            if (show) {
                ToastUtils.getInstance().show(et.getHint().toString());
            }
            return false;
        }
        Utils.setButton(bt_sum, true);

        return true;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.bt_sum){
            if (check(true)) {
                params.setOrderId(id);
                params.setUserType(role.getType());
                HttpAppFactory.appraise(params)
                        .subscribe(new NetObserver<Object>(this){
                            @Override
                            public void doOnSuccess(Object o) {
                                super.doOnSuccess(o);
                                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                                setResult(Activity.RESULT_OK);
                                finish();
                            }
                        });
            }
        }
    }
}
