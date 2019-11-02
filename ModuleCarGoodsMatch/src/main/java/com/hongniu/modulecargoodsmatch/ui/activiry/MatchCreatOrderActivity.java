package com.hongniu.modulecargoodsmatch.ui.activiry;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsMatch;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.MatchCarPreInforBean;
import com.hongniu.modulecargoodsmatch.entity.MatchCreateOrderParams;
import com.hongniu.modulecargoodsmatch.net.HttpMatchFactory;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.widget.ItemView;

/**
 * @data 2019/5/19
 * @Author PING
 * @Description 创建车货匹配订单
 */
@Route(path = ArouterParamsMatch.activity_match_creat_order)
public class MatchCreatOrderActivity extends BaseActivity implements View.OnClickListener {
    private MatchCarPreInforBean preCarInfor;//车辆预加载信息

    private ItemView item_name;
    private ItemView item_phone;
    private ItemView item_remark;
    private ItemView item_car_type;
    private ItemView item_start_price;
    private ItemView item_price;
    private ItemView item_forecast_price;
    private TextView tv_start_address;
    private TextView tv_start_address_dess;
    private TextView tv_end_address;
    private TextView tv_end_address_dess;
    private TextView tv_time;

    private Button btNext;
    private MatchCreateOrderParams params;//参数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_creat_order);
        setToolbarTitle("确定订单");
        params = getIntent().getParcelableExtra(Param.TRAN);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        item_name = findViewById(R.id.item_name);
        item_phone = findViewById(R.id.item_phone);
        item_remark = findViewById(R.id.item_remark);
        item_car_type = findViewById(R.id.item_car_type);
        item_start_price = findViewById(R.id.item_start_price);
        item_price = findViewById(R.id.item_price);
        item_forecast_price = findViewById(R.id.item_forecast_price);
        btNext = findViewById(R.id.bt_entry);
        tv_start_address=findViewById(R.id.tv_start_address);
        tv_start_address_dess=findViewById(R.id.tv_start_address_dess);
        tv_end_address=findViewById(R.id.tv_end_address);
        tv_end_address_dess=findViewById(R.id.tv_end_address_dess);
        tv_time=findViewById(R.id.tv_time);
    }

    @Override
    protected void initData() {
        super.initData();
//        item_car_type.setTextCenter(params.getCartype());
//        item_car_type.setTextCenter(params.getCartype());


        querPreloadInfor();
    }

    private void querPreloadInfor() {
        HttpMatchFactory
                .queryGoodCarInfor()
                .subscribe(new NetObserver<MatchCarPreInforBean>(this) {

                    @Override
                    public void doOnSuccess(MatchCarPreInforBean data) {
                        preCarInfor = data;
                    }
                })
        ;
    }

    @Override
    protected void initListener() {
        super.initListener();
        btNext.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        DeviceUtils.closeSoft(this);
        int id = v.getId();
        if (id == R.id.bt_entry) {
            if (check()) {

            }
        }
    }


    private boolean check() {

        return true;
    }


}
