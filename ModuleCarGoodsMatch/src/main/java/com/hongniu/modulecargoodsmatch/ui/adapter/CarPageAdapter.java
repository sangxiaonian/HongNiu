package com.hongniu.modulecargoodsmatch.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fy.androidlibrary.utils.ConvertUtils;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.MatchCarTypeInfoBean;
import com.fy.androidlibrary.imgload.ImageLoader;

import java.util.List;
import java.util.Locale;

/**
 * 作者：  on 2019/11/1.
 */
public class CarPageAdapter extends PagerAdapter {

    List<MatchCarTypeInfoBean> datas;
    private Context context;


    public CarPageAdapter(List<MatchCarTypeInfoBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_car_type_info, container, false);
        container.addView(inflate);
        TextView tv_car_size = inflate.findViewById(R.id.tv_car_size);
        TextView tv_car_weight = inflate.findViewById(R.id.tv_car_weight);
        ImageView img = inflate.findViewById(R.id.img);

        MatchCarTypeInfoBean infoBean = datas.get(position);
        ImageLoader.getLoader().load(context,img,infoBean.getCarImage());
        tv_car_size.setText(String.format(Locale.CHINESE,"长宽高：%s*%s*%s米", ConvertUtils.changeFloat(infoBean.getLength(),2),ConvertUtils.changeFloat(infoBean.getWidth(),2),ConvertUtils.changeFloat(infoBean.getHeight(),2)));
        tv_car_weight.setText(String.format(Locale.CHINESE,"载重：%s吨",ConvertUtils.changeFloat(infoBean.getLoad(),2) ));

        return inflate;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);

    }
}
