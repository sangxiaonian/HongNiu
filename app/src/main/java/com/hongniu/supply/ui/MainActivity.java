package com.hongniu.supply.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.supply.R;
import com.sang.common.imgload.ImageLoader;
@Route(path = ArouterParamsApp.activity_main)
public class MainActivity extends BaseActivity implements View.OnClickListener {

    ViewGroup tab1;
    ViewGroup tab2;
    ViewGroup tab3;
    ViewGroup tab4;
    ViewGroup tab5;

    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;

    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;
    ImageView img5;


    Fragment homeFragment, orderFragment, addFragment, messageFragment, meFragment, currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        tab4 = findViewById(R.id.tab4);
        tab5 = findViewById(R.id.tab5);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);

    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);
        tab5.setOnClickListener(this);


    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab1:
            case R.id.tab2:
            case R.id.tab3:
            case R.id.tab4:
            case R.id.tab5:
                changeTabeState(v.getId());
                break;
        }
    }

    private void changeTabeState(int id) {
        tv1.setTextColor(getResources().getColor(id == R.id.tv1 ? R.color.color_tabe_select : R.color.color_tabe_unselect));
        tv2.setTextColor(getResources().getColor(id == R.id.tv2 ? R.color.color_tabe_select : R.color.color_tabe_unselect));
        tv3.setTextColor(getResources().getColor(id == R.id.tv3 ? R.color.color_tabe_select : R.color.color_tabe_unselect));
        tv4.setTextColor(getResources().getColor(id == R.id.tv4 ? R.color.color_tabe_select : R.color.color_tabe_unselect));
        tv5.setTextColor(getResources().getColor(id == R.id.tv5 ? R.color.color_tabe_select : R.color.color_tabe_unselect));
        ImageLoader.getLoader().load(mContext, img1, id == R.id.img1 ? R.mipmap.icon_home_selected_46 : R.mipmap.icon_home_unselected_46);
        ImageLoader.getLoader().load(mContext, img2, id == R.id.img2 ? R.mipmap.icon_gz_selected_46 : R.mipmap.icon_gz_unselected_46);
        ImageLoader.getLoader().load(mContext, img3, id == R.id.img3 ? R.mipmap.icon_add_721 : R.mipmap.icon_add_72);
        ImageLoader.getLoader().load(mContext, img4, id == R.id.img4 ? R.mipmap.icon_message_selected_46 : R.mipmap.icon_message_unselected_46);
        ImageLoader.getLoader().load(mContext, img5, id == R.id.img5 ? R.mipmap.icon_me_selected_46 : R.mipmap.icon_me_unselected_46);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }
        switch (id) {
            case R.id.tab1:
                if (homeFragment == null) {
                    homeFragment = (Fragment) ArouterUtils.getInstance().builder(ArouterParamOrder.fragment_order_main).navigation();
                    fragmentTransaction.add(R.id.content, homeFragment);
                } else {
                    fragmentTransaction.show(homeFragment);
                }
                currentFragment = homeFragment;
                break;
            case R.id.tab2:
                if (orderFragment == null) {
                    orderFragment = (Fragment) ArouterUtils.getInstance().builder(ArouterParamOrder.fragment_order_main).navigation();
                    fragmentTransaction.add(R.id.content, orderFragment);
                } else {
                    fragmentTransaction.show(orderFragment);
                }
                currentFragment = orderFragment;
                break;
            case R.id.tab3:

                if (addFragment == null) {
                    addFragment = (Fragment) ArouterUtils.getInstance().builder(ArouterParamOrder.fragment_order_main).navigation();
                    fragmentTransaction.add(R.id.content, addFragment);
                } else {
                    fragmentTransaction.show(addFragment);
                }

                currentFragment = addFragment;
                break;
            case R.id.tab4:
                if (messageFragment == null) {
                    messageFragment = (Fragment) ArouterUtils.getInstance().builder(ArouterParamOrder.fragment_order_main).navigation();
                    fragmentTransaction.add(R.id.content, messageFragment);
                } else {
                    fragmentTransaction.show(messageFragment);
                }

                currentFragment = messageFragment;

                break;
            case R.id.tab5:
                if (meFragment == null) {
                    meFragment = (Fragment) ArouterUtils.getInstance().builder(ArouterParamOrder.fragment_order_main).navigation();
                    fragmentTransaction.add(R.id.content, meFragment);
                } else {
                    fragmentTransaction.show(meFragment);
                }

                currentFragment = meFragment;

                break;

        }

        fragmentTransaction.commitAllowingStateLoss();


    }


}
