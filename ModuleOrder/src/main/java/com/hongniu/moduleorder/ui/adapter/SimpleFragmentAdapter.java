package com.hongniu.moduleorder.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hongniu.moduleorder.R;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.photoview.PhotoView;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import com.sang.common.imgload.ImageLoader;

import java.util.List;

/**
 *@data  2018/10/24
 *@Author PING
 *@Description 图片预览
 *
 *
 */
public class SimpleFragmentAdapter extends PagerAdapter {


    private final List<String> images;
    private final Context context;

    public SimpleFragmentAdapter(Context context, List<String> datas) {
        this.images = datas;
        this.context = context;
    }

    public int getCount() {
        return images.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.picture_image_preview, container, false);
        final PhotoView imageView = (PhotoView) contentView.findViewById(R.id.preview_image);
        final SubsamplingScaleImageView longImg = (SubsamplingScaleImageView) contentView.findViewById(R.id.longImg);
        String path =  images.get(position);


        imageView.setVisibility( View.VISIBLE);
        longImg.setVisibility(  View.GONE);
        ImageLoader.getLoader().load(context, imageView, path);
        container.addView(contentView, 0);
        return contentView;
    }
}