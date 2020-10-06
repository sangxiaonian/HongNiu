package com.hongniu.modulecargoodsmatch.ui.activiry;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsMatch;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.ModuleBaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.modulecargoodsmatch.R;

/**
 *@data  2019/5/12
 *@Author PING
 *@Description
 *
 * 车货匹配我的记录
 */
@Route(path = ArouterParamsMatch.activity_match_my_record)
public class MatchMyRecordActivity extends ModuleBaseActivity implements RadioGroup.OnCheckedChangeListener {

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
                releastFragment= (Fragment) ArouterUtils.getInstance().builder(ArouterParamsMatch.fragment_match_my_record).navigation();
                Bundle bundle=new Bundle();
                bundle.putInt(Param.TYPE,2);
                releastFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.content,releastFragment);
            }else {
                fragmentTransaction.show(releastFragment);
            }
            currentFragment=releastFragment;

        }else {
            if (participateFragment==null){
                participateFragment= (Fragment) ArouterUtils.getInstance().builder(ArouterParamsMatch.fragment_match_my_join).navigation();
                fragmentTransaction.add(R.id.content,participateFragment);
            }else {
                fragmentTransaction.show(participateFragment);
            }
            currentFragment=participateFragment;

        }
        fragmentTransaction.commit();
    }
}