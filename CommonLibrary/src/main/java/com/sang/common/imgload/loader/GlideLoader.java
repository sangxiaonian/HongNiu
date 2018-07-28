package com.sang.common.imgload.loader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sang.common.imgload.control.ImageLoaderControl;

/**
 * 作者： ${桑小年} on 2018/7/28.
 * 努力，为梦长留
 */
public class GlideLoader implements ImageLoaderControl.IImageLoader {


    private RequestOptions headOptions;
    private int placeholder;
    private int errorImg;
    private boolean skipMemoryCache;
    private int headPlaceholder;
    private int headErrorImg;


    public GlideLoader() {
        headOptions = new RequestOptions().fitCenter();
    }

    /**
     * 对一般的加载，列表等
     *
     * @param context   上下文
     * @param imageView ImageView控件
     * @param img       所要加载的图片
     */
    @Override
    public void load(Context context, ImageView imageView, Object img) {
        Glide.with(context).applyDefaultRequestOptions(getOptions(placeholder,errorImg)).load(imageView);
    }

    /**
     * 加载头像
     *
     * @param context   上下文
     * @param imageView ImageView控件
     * @param img       所要加载的图片
     */
    @Override
    public void loadHeaed(Context context, ImageView imageView, Object img) {
        Glide.with(context).applyDefaultRequestOptions(getOptions(headPlaceholder, headErrorImg)).load(imageView);
    }

    /**
     * 设置占位图
     *
     * @param imgID
     */
    @Override
    public void placeholder(int imgID) {
        this.placeholder=imgID;

    }

    /**
     * 设置加载错误时候的图片
     *
     * @param imgID
     */
    @Override
    public void errorImg(int imgID) {
        this.errorImg=imgID;
    }

    /**
     * 设置占位图
     *
     * @param imgID
     */
    @Override
    public void headPlaceholder(int imgID) {
        this.headPlaceholder=imgID;
    }

    /**
     * 设置加载错误时候的图片
     *
     * @param imgID
     */
    @Override
    public void headErrorImg(int imgID) {
        this.headErrorImg=imgID;
    }

    /**
     * 当前一次不适用缓存
     */
    @Override
    public void skipMemoryCache() {
      skipMemoryCache=true;
    }

    public RequestOptions getOptions(int placeholder, int errorImg) {
        RequestOptions options = new RequestOptions();
        options.fitCenter();
        if ( placeholder !=0) {
            options.placeholder( placeholder);
        }
        if ( errorImg !=0) {
            options.placeholder( errorImg);
        }
        if (skipMemoryCache){
            options.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
            skipMemoryCache=false;
        }

        return options;
    }
}
