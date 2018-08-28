package com.hongniu.modulelogin.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.modulelogin.R;
import com.hongniu.modulelogin.entity.LoginCarInforBean;
import com.hongniu.modulelogin.entity.LoginEvent;
import com.hongniu.modulelogin.net.HttpLoginFactory;
import com.sang.common.event.BusFactory;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.Observable;

@Route(path = ArouterParamLogin.activity_car_list)
public class LoginCarListActivity extends RefrushActivity<LoginCarInforBean> implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__car_list);
        setToolbarTitle(getString(R.string.car_title));
        initView();
        initData();
        queryData(true);

    }

    @Override
    protected void initView() {
        super.initView();
        findViewById(R.id.bt_save).setOnClickListener(this);
    }


    @Override
    protected XAdapter<LoginCarInforBean> getAdapter(List<LoginCarInforBean> datas) {
        return new XAdapter<LoginCarInforBean>(mContext, datas) {
            @Override
            public BaseHolder<LoginCarInforBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<LoginCarInforBean>(mContext, parent, R.layout.login_item_car_list) {
                    @Override
                    public void initView(View itemView, int position, final LoginCarInforBean data) {
                        super.initView(itemView, position, data);
                        TextView tvCarNum = itemView.findViewById(R.id.tv_car_num);//车牌号
                        TextView tvCarType = itemView.findViewById(R.id.tv_cat_type);//车辆类型
                        TextView tvOwner = itemView.findViewById(R.id.tv_owner);//车主电话信息

                        tvCarNum.setText(data.getCarNumber() == null ? "" : data.getCarNumber());
                        tvCarType.setText(data.getCartypename() == null ? "" : data.getCartypename());
                        tvOwner.setText(data.getContactName() == null ? "" : data.getContactName());


                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LoginEvent.CarEvent carEvent = new LoginEvent.CarEvent(1);
                                carEvent.bean = data;
                                BusFactory.getBus().postSticky(carEvent);
                                ArouterUtils.getInstance().builder(ArouterParamLogin.activity_car_infor).navigation(mContext);
                            }
                        });
                    }
                };
            }
        };
    }

    @Override
    protected Observable<CommonBean<PageBean<LoginCarInforBean>>> getListDatas() {
        return HttpLoginFactory.getCarList(currentPage);
    }

    @Override
    protected boolean getUseEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginEvent.UpdateEvent event) {
        if (event != null) {
           queryData(true);
        }
    }

     
    @Override
    public void onClick(View v) {
        //添加车辆
        BusFactory.getBus().postSticky(new LoginEvent.CarEvent(0));
        ArouterUtils.getInstance().builder(ArouterParamLogin.activity_car_infor).navigation(mContext);
    }


}
