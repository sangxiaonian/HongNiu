package com.fy.androidlibrary.imgload.control;

import android.content.Context;
import android.widget.ImageView;


/**
 * 作者： ${桑小年} on 2018/7/28.
 * 努力，为梦长留
 * <p>
 * 图片加载框架的接口约束类
 */
public class ImageLoaderControl {

    public interface IImageLoader {
        /**
         * 对一般的加载，列表等
         *
         * @param context   上下文
         * @param imageView ImageView控件
         * @param img       所要加载的图片
         */
        void load(Context context, ImageView imageView, Object img);

        /**
         * 带有缩略图的加载
         *
         * @param context   上下文
         * @param imageView ImageView控件
         * @param img       所要加载的图片
         */
        void loadWithThumbnail(Context context, ImageView imageView, Object img,Object thumbnail);


        /**
         * 加载头像
         *
         * @param context   上下文
         * @param imageView ImageView控件
         * @param img       所要加载的图片
         */
        void loadHeaed(Context context, ImageView imageView, Object img);

        /**
         * 设置全局占位图 ，优先级低
         *
         * @param imgID
         */
        void globalPlaceholder(int imgID);

        /**
         * 设置全局加载错误时候的图片 ，优先级低
         *
         * @param imgID
         */
        void globalErrorImg(int imgID);

        /**
         * 设置占位图
         *
         * @param imgID
         */
        IImageLoader placeholder(int imgID);

        /**
         * 设置加载错误时候的图片
         *
         * @param imgID
         */
        IImageLoader errorImg(int imgID);

        /**
         * 设置占位图
         *
         * @param imgID
         */
        IImageLoader headPlaceholder(int imgID);

        /**
         * 设置加载错误时候的图片
         *
         * @param imgID
         */
        IImageLoader headErrorImg(int imgID);

        /**
         * 当前加载跳过缓存
         */
        IImageLoader skipMemoryCache();


    }

}
