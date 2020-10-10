package com.hongniu.freight.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.ui.adapter.SimpleFragmentAdapter;
import com.luck.picture.lib.widget.PreviewViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 查看回单、大图，主要是对图片进行预览
 * <p>
 * 查看回单，查看货单 type 0 单纯的查看回单 1预览回单，有删除功能 2查看货单，有重新上传功能
 */
@Route(path = ArouterParamApp.activity_preview_image)

public class PreviewImageActivity extends CompanyBaseActivity implements ViewPager.OnPageChangeListener {

    private final static String previewList = Param.TRAN;
    private final static String index = Param.TYPE;

    private PreviewViewPager viewPager;
    private TextView tvIndex;

    private List<String> lists = new ArrayList<>();
    private int currentPosition;
    private SimpleFragmentAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnlm_activity_preview_image);
        initView();
        initData();
        initListener();
        setWhitToolBar("预览");

    }

    @Override
    protected void initView() {
        super.initView();
        viewPager = findViewById(R.id.preview_pager);
        tvIndex = findViewById(R.id.tv_index);
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        currentPosition = intent.getIntExtra(index, 0);
        ArrayList<String> extra = intent.getStringArrayListExtra(previewList);
        if (!CollectionUtils.isEmpty(extra)) {
            lists.addAll(extra);
        }
        changeIndex(currentPosition);

        pagerAdapter = new  SimpleFragmentAdapter(mContext, lists);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(currentPosition);





    }

    private void changeIndex(int index) {
        tvIndex.setText((index+1) + "/" + lists.size());
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
