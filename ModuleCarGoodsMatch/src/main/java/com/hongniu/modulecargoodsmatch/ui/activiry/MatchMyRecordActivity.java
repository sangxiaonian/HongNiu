package com.hongniu.modulecargoodsmatch.ui.activiry;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParams;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.GoodsOwnerInforBean;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 *@data  2019/5/12
 *@Author PING
 *@Description
 *
 * 车货匹配我的记录
 */

@Route(path = ArouterParams.activity_match_my_record)
public class MatchMyRecordActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup group;
    private RadioButton btLeft;
    private RadioButton btRight;
    private Fragment currentFragment,releastFragment,participateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_my_record);
        setToolbarTitle("我的记录");
        initView();
        initData();
        initListener();
        btLeft.performClick();
    }

    @Override
    protected void initView() {
        super.initView();
        group=findViewById(R.id.rg);
        btLeft=findViewById(R.id.rb_left);
        btRight=findViewById(R.id.rb_right);
    }

    @Override
    protected void initListener() {
        super.initListener();
        group.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment!=null){
            fragmentTransaction.hide(currentFragment);
        }
        if (checkedId==R.id.rb_left) {
            if (releastFragment==null){
                releastFragment= (Fragment) ArouterUtils.getInstance().builder(ArouterParams.fragment_match_my_record).navigation();

                fragmentTransaction.add(R.id.content,releastFragment);
            }else {
                fragmentTransaction.show(releastFragment);
            }
            currentFragment=releastFragment;

        }else {
            if (participateFragment==null){
                participateFragment= (Fragment) ArouterUtils.getInstance().builder(ArouterParams.fragment_match_my_join).navigation();
                fragmentTransaction.add(R.id.content,participateFragment);
            }else {
                fragmentTransaction.show(participateFragment);
            }
            currentFragment=participateFragment;

        }
        fragmentTransaction.commit();
    }
}