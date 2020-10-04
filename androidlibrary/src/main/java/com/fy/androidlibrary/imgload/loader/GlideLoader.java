package com.fy.androidlibrary.imgload.loader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fy.androidlibrary.imgload.control.ImageLoaderControl;
import com.fy.androidlibrary.imgload.loader.glide.GlideApp;

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
        loadWithThumbnail(context,imageView,img,null);

    }

    /**
     * 带有缩略图的加载
     *
     * @param context   上下文
     * @param imageView ImageView控件
     * @param img       所要加载的图片
     * @param thumbnail
     */
    @Override
    public void loadWithThumbnail(Context context, ImageView imageView, Object img, Object thumbnail) {
        if (thumbnail != null) {
            GlideApp.with(context).load(img)
                    .thumbnail(GlideApp.with(context)
                            .load(thumbnail)
                            .apply(getOptions(placeholder, errorImg)
                            ))
                    .apply(getOptions(placeholder, errorImg)).into(imageView);
        } else {
            GlideApp.with(context).load(img)
                    .apply(getOptions(placeholder, errorImg)).into(imageView);
        }
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
    public ImageLoaderControl.IImageLoader placeholder(int imgID) {
        this.tempPlaceholder = imgID;
        return this;
    }

    /**
     * 设置加载错误时候的图片
     *
     * @param imgID
     */
    @Override
    public ImageLoaderControl.IImageLoader errorImg(int imgID) {
        this.tempErrorImg = imgID;
        return this;
    }

    /**
     * 设置占位图
     *
     * @param imgID
     */
    @Override
    public ImageLoaderControl.IImageLoader headPlaceholder(int imgID) {
        this.headPlaceholder = imgID;
        return this;
    }

    /**
     * 设置加载错误时候的图片
     *
     * @param imgID
     */
    @Override
    public ImageLoaderControl.IImageLoader headErrorImg(int imgID) {
        this.headErrorImg = imgID;
        return this;
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
        if (tempPlaceholder != 0) {
            options.placeholder(tempPlaceholder);
            tempPlaceholder = 0;
        } else if (placeholder != 0) {
            options.placeholder(placeholder);
        }

        if (tempErrorImg != 0) {
            options.error(tempErrorImg);
            tempErrorImg = 0;
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
