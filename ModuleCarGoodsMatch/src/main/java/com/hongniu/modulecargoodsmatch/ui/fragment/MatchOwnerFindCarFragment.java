package com.hongniu.modulecargoodsmatch.ui.fragment;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsMatch;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.MatchCarTypeInfoBean;
import com.hongniu.modulecargoodsmatch.net.HttpMatchFactory;
import com.hongniu.modulecargoodsmatch.ui.CarPageAdapter;

import java.util.List;

/**
 * @data 2019/10/27
 * @Author PING
 * @Description 货主找车
 */
@Route(path = ArouterParamsMatch.fragment_match_owner_find_car)
public class MatchOwnerFindCarFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager pager;
    private RadioGroup rg;
    private View img_next;
    private View img_last;
    private TextView tv_start_address;
    private TextView tv_start_address_dess;
    private TextView tv_end_address;
    private TextView tv_end_address_dess;
    private TextView tv_time;
    private ViewGroup ll_start_address;
    private ViewGroup ll_end_address;
    private View bt_sum;

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_match_owner_find_car, null, false);
        pager = inflate.findViewById(R.id.pager);
        rg = inflate.findViewById(R.id.rg);
        img_last = inflate.findViewById(R.id.img_last);
        img_next = inflate.findViewById(R.id.img_next);
        tv_start_address=inflate.findViewById(R.id.tv_start_address);
        tv_start_address_dess=inflate.findViewById(R.id.tv_start_address_dess);
        tv_end_address=inflate.findViewById(R.id.tv_end_address);
        tv_end_address_dess=inflate.findViewById(R.id.tv_end_address_dess);
        tv_time=inflate.findViewById(R.id.tv_time);
        bt_sum=inflate.findViewById(R.id.bt_sum);
        ll_start_address=inflate.findViewById(R.id.ll_start_address);
        ll_end_address=inflate.findViewById(R.id.ll_start_address);
        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();
        //查询车辆信息
        HttpMatchFactory.queryCarTypeInfo()
                .subscribe(new NetObserver<List<MatchCarTypeInfoBean>>(this) {
                    @Override
                    public void doOnSuccess(List<MatchCarTypeInfoBean> data) {
                        initTags(data);
                        pager.setAdapter(new CarPageAdapter(data, getContext()));
                    }
                })
        ;

    }


    @Override
    protected void initListener() {
        super.initListener();
        rg.setOnCheckedChangeListener(this);
        pager.addOnPageChangeListener(this);
        img_last.setOnClickListener(this);
        img_next.setOnClickListener(this);
        bt_sum.setOnClickListener(this);
        ll_start_address.setOnClickListener(this);
        ll_end_address.setOnClickListener(this);
    }

    private void initTags(List<MatchCarTypeInfoBean> data) {
        rg.removeAllViews();
        for (MatchCarTypeInfoBean datum : data) {
            RadioButton tvButton = (RadioButton) LayoutInflater.from(getContext()).inflate(R.layout.item_radio_button, rg, false);
            tvButton.setText(datum.getCarType() == null ? "" : datum.getCarType());
            rg.addView(tvButton);
        }

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View child = group.getChildAt(i);
            if (child.getId() == checkedId && pager.getCurrentItem() != i) {
                pager.setCurrentItem(i);
            }
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (rg.getChildAt(i) != null) {
            rg.getChildAt(i).performClick();
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.img_last) {

            if (pager.getCurrentItem() > 0) {
                pager.setCurrentItem(pager.getCurrentItem() - 1);
            }
        } else if (v.getId() == R.id.img_next) {
            if (pager.getAdapter() != null && pager.getCurrentItem() < pager.getAdapter().getCount() - 1) {
                pager.setCurrentItem(pager.getCurrentItem() + 1);
            }
        }else if (v.getId() == R.id.bt_sum) {
            ArouterUtils.getInstance().builder(ArouterParamsMatch.activity_match_creat_order)
                    .navigation(getContext());
        }else if (v.getId() == R.id.ll_start_address) {
            ArouterUtils.getInstance().builder(ArouterParamsMatch.activity_match_map)
                    .withBoolean(Param.TRAN,true)
                    .navigation(getContext());
        }else if (v.getId() == R.id.ll_end_address) {
            ArouterUtils.getInstance()
                    .builder(ArouterParamsMatch.activity_match_map)
                    .withBoolean(Param.TRAN,false)
                    .navigation(getContext());
        }
    }
}
