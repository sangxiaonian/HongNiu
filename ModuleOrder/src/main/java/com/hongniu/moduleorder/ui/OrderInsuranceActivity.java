package com.hongniu.moduleorder.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CreatInsuranceBean;
import com.hongniu.moduleorder.R;
import com.hongniu.baselibrary.entity.OrderCreatBean;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.widget.ColorProgress;

/**
 * 保单生成中界面
 */
@Route(path = ArouterParamOrder.activity_insurance_creat)
public class OrderInsuranceActivity extends BaseActivity {

    ColorProgress progress;

    private long DELAYED = 500;
    private int random;

    private final int SUCCESS = 2;
    private final int FAIL = 1;
    public OrderCreatBean insruance;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                random += ConvertUtils.getRandom(7, 11);
                if (random > 99) {
                    random = 99;
                    sendEmptyMessageDelayed(ConvertUtils.getRandom(1, 3), DELAYED);
                }
                progress.setCurrentValue(random);
            } else if (msg.what == SUCCESS) {
                progress.setCurrentValue(100);
                ArouterUtils.getInstance().builder(ArouterParamOrder.activity_insurance_creat_result)
                        .withParcelable(Param.TRAN,insruance)
                        .navigation(mContext);
                finish();
            } else if (msg.what == FAIL){
                ArouterUtils.getInstance()
                        .builder(ArouterParamOrder.activity_insurance_creat_result)
                        .navigation(mContext);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_insurance);
        setToolbarTitle("");
        progress = findViewById(R.id.color_progress);
        CreatInsuranceBean insuranceBean = getIntent().getParcelableExtra(Param.TRAN);
        if (insuranceBean!=null) {
            handler.sendEmptyMessageDelayed(0, 200);

            HttpOrderFactory.creatInsurance(insuranceBean)
                    .subscribe(new NetObserver<OrderCreatBean>(null) {

                        @Override
                        public void doOnSuccess(OrderCreatBean data) {
                             insruance=data;
                            handler.sendEmptyMessage(SUCCESS);
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            handler.sendEmptyMessage(FAIL);
                        }
                    });
        }else {
            handler.sendEmptyMessage(FAIL);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
        handler.removeMessages(SUCCESS);
        handler.removeMessages(FAIL);
    }

    @Override
    public void onBackPressed() {

    }
}
