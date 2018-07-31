package com.hongniu.baselibrary.arouter;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 作者： ${桑小年} on 2018/7/30.
 * 努力，为梦长留
 */
public class ArouterUtils {


    private String path;


    public static ArouterUtils getInstance(){
        return new ArouterUtils();
    }

    public ArouterUtils() {
    }


    /**
     * 设置路径
     * @param path
     * @return
     */
    public Postcard builder(String path){
       return ARouter.getInstance().build(path);
    }



    /**
     * 跳转界面
     * @param context
     */
    public void navigation(Context context){
        ARouter.getInstance().build(path).navigation(context);
    }




}
