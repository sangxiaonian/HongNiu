package com.hongniu.freight.ui.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.fy.androidlibrary.imgload.ImageLoader;
import com.hongniu.freight.R;
import com.luck.picture.lib.photoview.PhotoView;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

import java.util.List;

public class SimpleFragmentAdapter extends PagerAdapter {

    /**
     * 最大缓存图片数量
     */
    private static final int MAX_CACHE_SIZE = 20;
    /**
     * 缓存view
     */
    private SparseArray<View> mCacheView;
    private List<String> images;

    public SimpleFragmentAdapter(Context mContext, List<String> lists) {
        images = lists;
        mCacheView = new SparseArray<>();
    }

    private void clear() {
        if (null != mCacheView) {
            mCacheView.clear();
            mCacheView = null;
        }
    }

    public void removeCacheView(int position) {
        if (mCacheView != null && position < mCacheView.size()) {
            mCacheView.removeAt(position);
        }
    }


    @Override
    public int getCount() {
        return images != null ? images.size() : 0;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((View) object);
        if (mCacheView.size() > MAX_CACHE_SIZE) {
            mCacheView.remove(position);
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View contentView = mCacheView.get(position);
        if (contentView == null) {
            contentView = LayoutInflater.from(container.getContext())
                    .inflate(R.layout.picture_image_preview, container, false);
            // 常规图控件
            final PhotoView imageView = contentView.findViewById(R.id.preview_image);
            // 长图控件
            final SubsamplingScaleImageView longImageView = contentView.findViewById(R.id.longImg);
            // 视频播放按钮
            ImageView ivPlay = contentView.findViewById(R.id.iv_play);
            String path = images.get(position);
            if (path != null) {
                ivPlay.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                longImageView.setVisibility(View.GONE);
                ImageLoader.getLoader().load(imageView.getContext(), imageView, path);
            }
            mCacheView.put(position, contentView);
        }
        (container).addView(contentView, 0);
        return contentView;
    }
}
