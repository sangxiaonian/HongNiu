package com.hongniu.moduleorder.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CreatInsuranceBean;
import com.hongniu.baselibrary.entity.QueryOrderStateBean;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.moduleorder.R;
import com.fy.androidlibrary.imgload.ImageLoader;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;

import org.greenrobot.eventbus.EventBus;

/**
 * @data 2018/10/29
 * @Author PING
 * @Description 查询投保结果界面
 */
@Route(path = ArouterParamOrder.activity_order_query_insurance)
public class OrderQueryInsuranceActivity extends BaseActivity {

    private int count = 3;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            query();

        }
    };
    private CreatInsuranceBean insuranceBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_query_insurance);
        setToolbarTitle("等待付款");
        setToolbarSrcLeft(0);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        ImageView img = findViewById(R.id.img);
        ImageLoader.getLoader().load(this, img, R.raw.listloading);
    }

    @Override
    protected void initData() {
        super.initData();
        insuranceBean = getIntent().getParcelableExtra(Param.TRAN);
        query();
    }


    public void query() {
        HttpAppFactory.queryOrderState(insuranceBean.getOrderID())
                .subscribe(new NetObserver<QueryOrderStateBean>(null) {
                    @Override
                    public void doOnSuccess(QueryOrderStateBean data) {
                        if (data.isHavePolicy()) {
                            ArouterUtils.getInstance().builder(ArouterParamOrder.activity_insurance_creat_result)
                                    .withSerializable(Param.TRAN, data)
                                    .navigation(mContext);

                            if (!TextUtils.isEmpty(data.getRedPacket())&&!TextUtils.equals("0.00",data.getRedPacket())){
                                EventBus.getDefault().post( data.getRedPacket()+"元");
                            }

                        } else {
                            if (count <= 0) {
                                ArouterUtils.getInstance()
                                        .builder(ArouterParamOrder.activity_insurance_creat_result)
                                        .withString(Param.TRAN, "保单生成失败，请联系客服")
                                        .navigation(mContext);
                            } else {
                                handler.sendEmptyMessageDelayed(0, (4 - count) * 2 * 1000);
                            }
                            count--;
                        }
                    }
                });
    }

    @Override
    protected void showAleart(String msg) {
        showAleart(msg, new DialogControl.OnButtonRightClickListener() {
            @Override
            public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                dialog.dismiss();
                ArouterUtils.getInstance()
                        .builder(ArouterParamOrder.activity_insurance_creat_result)
                        .withString(Param.TRAN, "保单生成失败，请联系客服")
                        .navigation(mContext);
            }
        });
    }

    @Override
    protected void showAleart(String msg, final DialogControl.OnButtonRightClickListener listener) {
        if (alertDialog==null){
            alertDialog = new CenterAlertDialog(mContext);

        }
        new CenterAlertBuilder()
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        if (listener!=null){
                            listener.onRightClick(view,dialog);
                        }
                        dialog.dismiss();
                    }

                })
                .hideBtLeft()
                .hideContent()
                .setCanceledOnTouchOutside(false)
                .setCancelable(false)
                .setDialogTitle(msg)
                .creatDialog(alertDialog)
                .show();
    }

    @Override
    public void onBackPressed() {

    }
}
