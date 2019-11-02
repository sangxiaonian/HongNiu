package com.hongniu.modulecargoodsmatch.ui.activiry;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsMatch;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.modulecargoodsmatch.R;
/**
 *@data  2019/10/27
 *@Author PING
 *@Description
 *
 * 我的车货匹配订单
*/
@Route(path = ArouterParamsMatch.activity_match_my_order)
public class MatchMyOrderActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup group;
    private RadioButton btLeft;
    private RadioButton btRight;
    private Fragment currentFragment, findFragment, recevingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_my_order);
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
            if (findFragment ==null){
                findFragment = (Fragment) ArouterUtils.getInstance().builder(ArouterParamsMatch.fragment_match_driver_order_receiving).navigation();
                Bundle bundle=new Bundle();
                bundle.putInt(Param.TYPE,1);
                findFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.content, findFragment);
            }else {
                fragmentTransaction.show(findFragment);
            }
            currentFragment= findFragment;

        }else {
            if (recevingFragment ==null){
                recevingFragment = (Fragment) ArouterUtils.getInstance().builder(ArouterParamsMatch.fragment_match_driver_order_receiving).navigation();
                Bundle bundle=new Bundle();
                bundle.putInt(Param.TYPE,2);
                recevingFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.content, recevingFragment);
            }else {
                fragmentTransaction.show(recevingFragment);
            }
            currentFragment= recevingFragment;

        }
        fragmentTransaction.commit();
    }
}
