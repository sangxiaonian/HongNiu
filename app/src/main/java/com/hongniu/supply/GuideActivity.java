package com.hongniu.supply;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.sang.common.imgload.ImageLoader;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.JLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导界面
 */
@Route(path = ArouterParamsApp.activity_guide_activity)
public class GuideActivity extends BaseActivity {
    ViewPager vp;
    LinearLayout llIndex;
    private List<GuideBean> datas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        setToolbarTitle("");
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        vp = findViewById(R.id.vp);
        llIndex = findViewById(R.id.ll_index);
    }


    @Override
    protected void initData() {
        super.initData();
        datas.add(new GuideBean("三大角色", "您的角色随时切换", R.mipmap.img_ydy_1));
        datas.add(new GuideBean("个人财务", "收入与支出，一目了然", R.mipmap.img_ydy_2));
        datas.add(new GuideBean("快速下单", "分分钟创建订单", R.mipmap.img_ydy_3));
        datas.add(new GuideBean("行程轨迹", "随时查看车辆位置", R.mipmap.img_ydy_4));
        vp.setAdapter(new GuideAdapter());
        llIndex.removeAllViews();
        for (int i = 0; i < datas.size(); i++) {
            View view = creatView();
            if (i == vp.getCurrentItem()) {
                view.setBackgroundColor(getResources().getColor(R.color.color_light));
            } else {
                view.setBackgroundColor(getResources().getColor(R.color.color_guide));
            }
            llIndex.addView(view);
        }


    }

    @Override
    protected void initListener() {
        super.initListener();
        vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == vp.getAdapter().getCount() - 1) {
                    llIndex.setVisibility(View.GONE);
                } else {
                    llIndex.setVisibility(View.VISIBLE);
                }
                setSelectView(position);
            }
        });
    }

    private void setSelectView(int position) {
        for (int i = 0; i < llIndex.getChildCount(); i++) {
            View view = llIndex.getChildAt(i);
            if (i == position) {
                view.setBackgroundResource(R.drawable.shape_radius_2_f06f28);
            } else {
                view.setBackgroundResource(R.drawable.shape_radius_2_eaeaea);
            }
        }
    }

    private View creatView() {
        TextView view = new TextView(mContext);
        ViewGroup.MarginLayoutParams params = new LinearLayout.LayoutParams(DeviceUtils.dip2px(mContext, 10), DeviceUtils.dip2px(mContext, 3));
        params.leftMargin = (int) getResources().getDimension(R.dimen.app_item_gap);
        view.setLayoutParams(params);
        return view;
    }

    private class GuideAdapter extends PagerAdapter {

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return datas.size();
        }


        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_vp_guide, container, false);
            TextView tvTitle = inflate.findViewById(R.id.tv_title);
            TextView tvContent = inflate.findViewById(R.id.tv_content);
            Button bt = inflate.findViewById(R.id.button);
            ImageView img = inflate.findViewById(R.id.img);
            GuideBean bean = datas.get(position);
            tvTitle.setText(bean.title == null ? "" : bean.title);
            tvContent.setText(bean.content == null ? "" : bean.content);
            ImageLoader.getLoader().load(mContext, img, bean.res);
            container.addView(inflate);

            if (position == getCount() - 1) {
                bt.setVisibility(View.VISIBLE);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpNext();
                    }
                });
            } else {
                bt.setVisibility(View.INVISIBLE);
                bt.setOnClickListener(null);
            }


            return inflate;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    private void jumpNext() {
        ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_main).navigation(mContext);
        finish();
    }

    private class GuideBean {
        private String title;
        private String content;
        private int res;

        public GuideBean(String title, String content, int res) {
            this.title = title;
            this.content = content;
            this.res = res;
        }
    }


}
