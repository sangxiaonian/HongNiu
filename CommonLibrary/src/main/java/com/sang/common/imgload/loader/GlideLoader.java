package com.sang.common.imgload.loader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sang.common.imgload.control.ImageLoaderControl;
import com.sang.common.imgload.loader.glide.GlideApp;

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
    private int tempPlaceholder;//临时展位图
    private int tempErrorImg;


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
        GlideApp.with(context).load(img).apply(getOptions(placeholder, errorImg)).into(imageView);

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
        GlideApp.with(context).load(img).apply(getOptions(headPlaceholder, headErrorImg)).into(imageView);
    }

    /**
     * 设置占位图
     *
     * @param imgID
     */
    @Override
    public void globalPlaceholder(int imgID) {
        this.placeholder = imgID;

    }

    /**
     * 设置加载错误时候的图片
     *
     * @param imgID
     */
    @Override
    public void globalErrorImg(int imgID) {
        this.errorImg = imgID;
    }

    /**
     * 设置占位图
     *
     * @param imgID
     */
    @Override
    public void placeholder(int imgID) {
        this.tempPlaceholder = imgID;
    }

    /**
     * 设置加载错误时候的图片
     *
     * @param imgID
     */
    @Override
    public void errorImg(int imgID) {
        this.tempErrorImg = imgID;
    }

    /**
     * 设置占位图
     *
     * @param imgID
     */
    @Override
    public void headPlaceholder(int imgID) {
        this.headPlaceholder = imgID;
    }

    /**
     * 设置加载错误时候的图片
     *
     * @param imgID
     */
    @Override
    public void headErrorImg(int imgID) {
        this.headErrorImg = imgID;
    }

    /**
     * 当前一次不适用缓存
     */
    @Override
    public ImageLoaderControl.IImageLoader skipMemoryCache() {
        skipMemoryCache = true;
        return this;
    }

    public RequestOptions getOptions(int placeholder, int errorImg) {
        RequestOptions options = new RequestOptions();
//        options.fitCenter();
        if (tempPlaceholder != 0) {
            options.placeholder(tempPlaceholder);
            tempPlaceholder=0;
        } else if (placeholder != 0) {
            options.placeholder(placeholder);
        }

        if (tempErrorImg != 0) {
            options.error(tempErrorImg);
            tempErrorImg=0;
        } else if (errorImg != 0) {
            options.error(errorImg);
        }
        if (skipMemoryCache) {
            options.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
            skipMemoryCache = false;
        }

        return options;
    }
}
