package com.hongniu.modulelogin.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.modulelogin.R;
import com.hongniu.modulelogin.entity.LoginEvent;
import com.sang.common.event.BusFactory;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;

import java.util.ArrayList;
import java.util.List;

@Route(path = ArouterParamLogin.activity_car_list)
public class LoginCarListActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private List<String> datas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__car_list);
        setToolbarTitle(getString(R.string.car_title));
        initView();
        initData();

    }

    @Override
    protected void initView() {
        super.initView();
        findViewById(R.id.bt_save).setOnClickListener(this);
        recyclerView=findViewById(R.id.rv);
        LinearLayoutManager manager =new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }


    @Override
    protected void initData() {
        super.initData();
        datas=new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            datas.add("");
        }
        XAdapter<String> adapter =new XAdapter<String>(mContext,datas) {
            @Override
            public BaseHolder<String> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<String>(mContext,parent,R.layout.login_item_car_list){
                    @Override
                    public void initView(View itemView, int position, String data) {
                        super.initView(itemView, position, data);
                        TextView tvCarNum = itemView.findViewById(R.id.tv_car_num);//车牌号
                        TextView tvCarType= itemView.findViewById(R.id.tv_cat_type);//车辆类型
                        TextView tvOwner= itemView.findViewById(R.id.tv_owner);//车主电话信息

                        tvCarNum.setText("沪A999999");
                        tvCarType.setText("豪华法拉利");
                        tvOwner.setText("男神1号");


                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BusFactory.getBus().postSticky(new LoginEvent.CarEvent(1));
                                ArouterUtils.getInstance().builder(ArouterParamLogin.activity_car_infor) .navigation(mContext);
                            }
                        });
                    }
                };
            }
        };

        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onClick(View v) {
        //添加车辆
        BusFactory.getBus().postSticky(new LoginEvent.CarEvent(0));
        ArouterUtils.getInstance().builder(ArouterParamLogin.activity_car_infor) .navigation(mContext);
    }
}
