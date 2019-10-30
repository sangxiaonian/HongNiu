package com.hongniu.baselibrary.arouter;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hongniu.baselibrary.R;

/**
 * 作者： ${桑小年} on 2018/7/30.
 * 努力，为梦长留
 */
public class ArouterUtils {



    private static class InnerClass{
        private static ArouterUtils utils=new ArouterUtils();
    }

    public static ArouterUtils getInstance(){
        return InnerClass.utils;
    }

    private ArouterUtils() {
    }


    /**
     * 设置路径
     * @param path
     * @return
     */
    public Postcard builder(String path){
       return ARouter.getInstance().build(path)
               ;
    }








}
