package com.hongniu.modulelogin.ui;

import android.app.TaskStackBuilder;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CarTypeBean;
import com.hongniu.modulelogin.R;
import com.hongniu.modulelogin.entity.LoginCarInforBean;
import com.hongniu.modulelogin.entity.LoginCarListBean;
import com.hongniu.modulelogin.entity.LoginEvent;
import com.hongniu.modulelogin.net.HttpLoginFactory;
import com.sang.common.event.BusFactory;
import com.sang.common.recycleview.RecycleViewSupportEmpty;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

@Route(path = ArouterParamLogin.activity_car_list)
public class LoginCarListActivity extends BaseActivity implements View.OnClickListener {

    private RecycleViewSupportEmpty recyclerView;
    private  List<LoginCarInforBean> datas;
    private int currentPage=1;
    private XAdapter<LoginCarInforBean> adapter;


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

//        recyclerView.setEmptyView(R.mipmap.icon_zwcl_240,"暂无车辆");

    }


    @Override
    protected void initData() {
        super.initData();
        datas=new ArrayList<>();


          adapter =new XAdapter<LoginCarInforBean>(mContext,datas) {
            @Override
            public BaseHolder<LoginCarInforBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<LoginCarInforBean>(mContext,parent,R.layout.login_item_car_list){
                    @Override
                    public void initView(View itemView, int position, final LoginCarInforBean data) {
                        super.initView(itemView, position, data);
                        TextView tvCarNum = itemView.findViewById(R.id.tv_car_num);//车牌号
                        TextView tvCarType= itemView.findViewById(R.id.tv_cat_type);//车辆类型
                        TextView tvOwner= itemView.findViewById(R.id.tv_owner);//车主电话信息

                        tvCarNum.setText(data.getCarNumber()==null?"":data.getCarNumber());
                        tvCarType.setText(data.getCartypename()==null?"":data.getCartypename());
                        tvOwner.setText(data.getContactName()==null?"":data.getContactName());


                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LoginEvent.CarEvent carEvent = new LoginEvent.CarEvent(1);
                                carEvent.bean=data;
                                BusFactory.getBus().postSticky(carEvent);
                                ArouterUtils.getInstance().builder(ArouterParamLogin.activity_car_infor) .navigation(mContext);
                            }
                        });
                    }
                };
            }
        };

        recyclerView.setAdapter(adapter);

        upCarLists();


    }

    @Override
    protected boolean getUseEventBus() {
        return true;
    }

    @Subscribe(  threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginEvent.UpdateEvent event) {
        if (event!=null){
            upCarLists();
        }
    }

    public void upCarLists(){
        HttpLoginFactory.getCarList(currentPage)
                .subscribe(new NetObserver<LoginCarListBean>(this) {


                    @Override
                    public void doOnSuccess(LoginCarListBean data) {
                        List<LoginCarInforBean> list = data.getList();
                        datas.clear();
                        if (list!=null){
                            datas.addAll(list);
                        }
                        adapter.notifyDataSetChanged();
                    }




                });

    }


        @Override
    public void onClick(View v) {
        //添加车辆
        BusFactory.getBus().postSticky(new LoginEvent.CarEvent(0));
        ArouterUtils.getInstance().builder(ArouterParamLogin.activity_car_infor) .navigation(mContext);
    }


}
