package com.hongniu.modulecargoodsmatch.ui.activiry;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParams;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.widget.dialog.ListDialog;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.MatchCarInfor;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.ToastUtils;


import java.util.ArrayList;

/**
 * @data 2019/5/12
 * @Author PING
 * @Description 我要抢单页面
 */
@Route(path = ArouterParams.activity_match_grap_single)
public class MatchGrapSingleActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvCarNum;
    private TextView tvCarType;
    private EditText etPrice;
    private Button btSubmit;
    private ViewGroup llCar;
    private ListDialog<MatchCarInfor> dialog;
    private XAdapter<MatchCarInfor> adapter;
    private ArrayList<MatchCarInfor> datas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_grap_single);
        setToolbarTitle("XX的车货匹配");
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        dialog = new ListDialog<>();
        dialog.setAdapter(adapter = new XAdapter<MatchCarInfor>(mContext, datas = new ArrayList<>()) {


            @Override
            public BaseHolder<MatchCarInfor> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<MatchCarInfor>(mContext, parent, R.layout.match_item_grap_single) {
                    @Override
                    public void initView(View itemView, int position, MatchCarInfor data) {
                        super.initView(itemView, position, data);
                        TextView tvCarNum = itemView.findViewById(R.id.tv_car_num);
                        TextView tvCarType = itemView.findViewById(R.id.tv_car_type);
                        tvCarNum.setText("沪A12345");
                        tvCarType.setText("大货车");
                    }
                };
            }
        });
        tvCarNum = findViewById(R.id.tv_car_num);
        tvCarType = findViewById(R.id.tv_car_type);
        btSubmit = findViewById(R.id.bt_sum);
        etPrice = findViewById(R.id.et_price);
        llCar = findViewById(R.id.ll_car);

        for (int i = 0; i < 10; i++) {
            datas.add(new MatchCarInfor());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initData() {
        super.initData();
        tvCarNum.setText("沪A12234");
        tvCarType.setText("后八轮");
        dialog.setTitle("选择抢单车辆");
        dialog.setDescribe(null);
    }

    @Override
    protected void initListener() {
        super.initListener();
        btSubmit.setOnClickListener(this);
        llCar.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_sum) {
            ToastUtils.getInstance().show("提交");
        }else if (v.getId()==R.id.ll_car){
            dialog.show(getSupportFragmentManager(),"");
        }
    }
}
