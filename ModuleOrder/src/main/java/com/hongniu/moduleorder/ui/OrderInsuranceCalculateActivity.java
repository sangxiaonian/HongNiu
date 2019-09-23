package com.hongniu.moduleorder.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.entity.OrderInsuranceParam;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.ItemView;

import io.reactivex.disposables.Disposable;

/**
 * @data 2019/9/23
 * @Author PING
 * @Description 保费计算页面
 */
@Route(path = ArouterParamOrder.activity_order_insurance_calculate)
public class OrderInsuranceCalculateActivity extends BaseActivity implements View.OnClickListener {

    private ItemView itemName;
    private ItemView itemPrice;
    private ItemView itemInsurancePrice;


    private Button btNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_insurance_calculate);
        setToolbarTitle("保费计算");
        initView();
        initData();
        initListener();

    }

    @Override
    protected void initView() {
        super.initView();
        itemName = findViewById(R.id.item_cargo_name);
        itemPrice = findViewById(R.id.item_cargo_price);
        itemInsurancePrice = findViewById(R.id.item_insurance_price);
        btNext = findViewById(R.id.bt_next);
    }

    @Override
    protected void initListener() {
        super.initListener();
        btNext.setOnClickListener(this);
        itemPrice.getEtCenter().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String price = s.toString();
                if (TextUtils.isEmpty(price)){
                    if (itemInsurancePrice!=null){
                        itemInsurancePrice.setTextCenter("");
                    }
                }else {
                    try {
                        double v = Double.parseDouble(price) * 0.00015;
                        String result = ConvertUtils.changeFloat(v, 2);
                        if (itemInsurancePrice!=null){
                            itemInsurancePrice.setTextCenter(result);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void jump() {
        OrderInsuranceParam param = new OrderInsuranceParam();
        param.setCargoName(itemName.getTextCenter());
        param.setPrice(itemPrice.getTextCenter());
        param.setInsurancePrice(itemInsurancePrice.getTextCenter());
        ArouterUtils.getInstance()
                .builder(ArouterParamOrder.activity_order_create)
                .withInt(Param.TYPE, 3)
                .withParcelable(Param.TRAN, param)
                .navigation(this);
        finish();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (check()) {
            jump();
        }

    }

    private boolean check() {
        if (TextUtils.isEmpty(itemName.getTextCenter())) {
            ToastUtils.getInstance().show(itemName.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemPrice.getTextCenter())) {
            ToastUtils.getInstance().show(itemPrice.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemInsurancePrice.getTextCenter())) {
            ToastUtils.getInstance().show(itemInsurancePrice.getTextCenterHide());
            return false;
        }
        return true;
    }

}
