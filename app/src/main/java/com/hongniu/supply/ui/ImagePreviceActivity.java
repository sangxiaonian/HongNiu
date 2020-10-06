package com.hongniu.supply.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;


import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.base.ModuleBaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.moduleorder.ui.adapter.SimpleFragmentAdapter;
import com.hongniu.supply.R;
import com.luck.picture.lib.widget.PreviewViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片预览
 */
@Route(path = ArouterParamsApp.activity_img_previce)
public class ImagePreviceActivity extends ModuleBaseActivity implements ViewPager.OnPageChangeListener {


    private PreviewViewPager viewPager;

    private List<String> lists = new ArrayList<>();
    private int currentPosition;
    private SimpleFragmentAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_previce);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        viewPager = findViewById(R.id.preview_pager);
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        currentPosition = intent.getIntExtra(Param.TYPE, 0);
        ArrayList<String> extra = intent.getStringArrayListExtra(Param.TRAN);
        if (!CollectionUtils.isEmpty(extra)) {
            lists.addAll(extra);
        }
        changeIndex(currentPosition);

        pagerAdapter = new SimpleFragmentAdapter(mContext, lists);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(currentPosition);


    }

    private void changeIndex(int index) {
        setToolbarDarkTitle(String.format("%d/%d",(index+1),lists.size()));
        tool.setBackgroundColor(Color.TRANSPARENT);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        changeIndex(position);
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
