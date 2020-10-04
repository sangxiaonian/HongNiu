package com.fy.androidlibrary.imgload;

import android.content.Context;
import android.widget.ImageView;

import com.fy.androidlibrary.imgload.control.ImageLoaderControl;
import com.fy.androidlibrary.imgload.loader.GlideLoader;


/**
 * 作者： ${桑小年} on 2018/7/28.
 * 努力，为梦长留
 */
public class ImageLoader implements ImageLoaderControl.IImageLoader {

    private static ImageLoader loader;

    private ImageLoaderControl.IImageLoader realLoader;

    public static ImageLoader getLoader() {
        if (loader == null) {
            synchronized (ImageLoader.class) {
                if (loader == null) {
                    loader = new ImageLoader();
                }
            }
        }
        return loader;
    }


    private ImageLoader() {
        realLoader = new GlideLoader();
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
        realLoader.load(context, imageView, img);
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
        realLoader.loadWithThumbnail(context, imageView, img,thumbnail);
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
        realLoader.loadHeaed(context, imageView, img);
    }

    /**
     * 设置占位图
     *
     * @param imgID
     */
    @Override
    public void globalPlaceholder(int imgID) {
        realLoader.globalPlaceholder(imgID);
    }

    /**
     * 设置加载错误时候的图片
     *
     * @param imgID
     */
    @Override
    public void globalErrorImg(int imgID) {
        realLoader.globalErrorImg(imgID);
    }

    /**
     * 设置占位图
     *
     * @param imgID
     */
    @Override
    public ImageLoaderControl.IImageLoader  placeholder(int imgID) {
        realLoader.placeholder(imgID);
        return this;

    }

    /**
     * 设置加载错误时候的图片
     *
     * @param imgID
     */
    @Override
    public ImageLoaderControl.IImageLoader  errorImg(int imgID) {
        realLoader.errorImg(imgID);
        return this;
    }

    /**
     * 设置占位图
     *
     * @param imgID
     */
    @Override
    public ImageLoaderControl.IImageLoader  headPlaceholder(int imgID) {
        realLoader.headPlaceholder(imgID);
        return this;
    }

    /**
     * 设置加载错误时候的图片
     *
     * @param imgID
     */
    @Override
    public ImageLoaderControl.IImageLoader  headErrorImg(int imgID) {
        realLoader.headErrorImg(imgID);
        return this;
    }

    /**
     * 跳过缓存
     */
    @Override
    public ImageLoaderControl.IImageLoader skipMemoryCache() {
        realLoader.skipMemoryCache();
        return this;
    }
}
