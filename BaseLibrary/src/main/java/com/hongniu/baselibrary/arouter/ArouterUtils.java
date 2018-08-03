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
//          .withTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
       return ARouter.getInstance().build(path)
//               .withTransition(R.anim.activity_entry_en,R.anim.activity_entry_out)
               ;
    }



    /**
     * 跳转界面
     * @param context
     */
    public void navigation(Context context){
        ARouter.getInstance().build(path).navigation(context);
    }




}
