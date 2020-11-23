package com.hongniu.moduleorder.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.ModuleBaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.QueryOrderStateBean;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.utils.OrderUtils;
import com.fy.androidlibrary.imgload.ImageLoader;
import com.fy.androidlibrary.toast.ToastUtils;


/**
 * 订单创建结果 传入boolean 参数 Param.tran true 成功，false 失败
 */
@Route(path = ArouterParamOrder.activity_insurance_creat_result)
public class OrderInsuranceResultActivity extends ModuleBaseActivity implements View.OnClickListener {

    private boolean success;
    private Button btCheck,btFinish;
    private TextView tvInsurance,tvInsuranceState;
    QueryOrderStateBean insurance;
    String error;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_insurance_result);
        setToolbarTitle("");
        try {
            insurance = (QueryOrderStateBean) getIntent().getSerializableExtra(Param.TRAN);
        }catch (Exception e){
            e.printStackTrace();
        }
        success= insurance!=null;

        if (!success){
            error=getIntent().getStringExtra(Param.TRAN);
        }

        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        btCheck=findViewById(R.id.bt_check);
        img=findViewById(R.id.img_result);
        btFinish=findViewById(R.id.bt_finish);
        tvInsurance=findViewById(R.id.tv_insurance);
        tvInsuranceState=findViewById(R.id.tv_insurance_state);
    }

    @Override
    protected void initData() {
        super.initData();
        btCheck.setVisibility(success? View.VISIBLE:View.GONE);
        tvInsurance.setVisibility(success? View.VISIBLE:View.GONE);

        if (insurance!=null){
            tvInsurance.setText("保单号"+insurance.getPolicyNo());
        }
        tvInsuranceState.setText(success?getString(R.string.order_insurance_creat_success):((TextUtils.isEmpty(error))?getString(R.string.order_insurance_creat_fail):error));
        btFinish.setText(success?R.string.finish:R.string.order_insurance_return_home);
        ImageLoader.getLoader().load(mContext,img,success?R.mipmap.icon_cgts_260:R.mipmap.insurance_fail);

    }

    @Override
    protected void initListener() {
        btCheck.setOnClickListener(this);
        btFinish.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bt_finish) {
            ArouterUtils.getInstance().builder(ArouterParamsApp.activity_main).navigation(mContext);
        } else if (i == R.id.bt_check) {
            OrderUtils.scanPDf(this,insurance.getDownloadUrl());
        }
    }

    @Override
    public void onBackPressed() {
        ArouterUtils.getInstance().builder(ArouterParamsApp.activity_main).navigation(mContext);
    }
}
