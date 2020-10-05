package com.hongniu.baselibrary.utils;

import android.text.TextUtils;

import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.androidlibrary.utils.CommonUtils;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.UpImgData;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 作者： ${PING} on 2018/10/30.
 */
public class UpLoadImageUtils {

    OnUpLoadListener loadListener;
    int type=Param.REEIVE;
    /**
     * 已经完成的图片
     */
    private List<UpImgData> finishDate = new ArrayList<>();
    private List<LocalMedia> images = new ArrayList<>();
    private Disposable disposable;

    //0上传中 1完成 2失败
    private int state=1;

    public UpLoadImageUtils(int type) {
        this.type = type;
    }

    public void setOnUpLoadListener(OnUpLoadListener loadListener) {
        this.loadListener = loadListener;
    }

    public void upList(List<LocalMedia> images) {
        this.images=images;
        List<String> list = new ArrayList<>();
        finishDate.clear();
        for (LocalMedia pic : images) {
            UpImgData data = new UpImgData();
            //TODO 图片处理
//            data.setPath(pic.getRelativePath());
            data.setAbsolutePath(TextUtils.isEmpty(pic.getCompressPath())?pic.getPath():pic.getCompressPath());
            if (data.getAbsolutePath()!=null&&data.getAbsolutePath().startsWith("http")){
                finishDate.add(data);
            }else {
                list.add(pic.getCompressPath()==null?pic.getPath():pic.getCompressPath());
            }
        }
        if (disposable!=null){
            disposable.dispose();
        }

      if (list.size()>0){
          HttpAppFactory.upImage(type,list)
                  .subscribe(new NetObserver<List<UpImgData>>(null) {
                      @Override
                      public void onSubscribe(Disposable d) {
                          super.onSubscribe(d);
                          disposable = d;
                          state=0;
                      }

                      @Override
                      public void doOnSuccess(List<UpImgData> data) {
                          if (!CollectionUtils.isEmpty(data)){
                              finishDate.addAll(data);
                          }
                          state=1;
                      }

                      @Override
                      public void onError(Throwable e) {
                          super.onError(e);
                          state=2;
                          if (loadListener!=null){
                              loadListener.onUpLoadFail(unFinishCount());
                          }
                      }
                  });
      }else {
            state=1;
      }
    }

    public int unFinishCount(){
        return images.size()-finishDate.size();
    }

    public boolean isFinish(){
        return unFinishCount()==0||state!=0;
    }

    public List<String> getResult(){
        List<String> result=new ArrayList<>();
        for (UpImgData upImgData : finishDate) {
            result.add(upImgData.getPath());
        }
        return result;
    }

    /**
     * 重新上传图片
     */
    public void reUpLoad() {
        upList(images);
    }


    public interface OnUpLoadListener{
        void onUpLoadFail(int failCount);
    }


}
