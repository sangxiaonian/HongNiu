package com.fy.companylibrary.ui;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.androidlibrary.widget.dialog.CenterDialog;
import com.fy.androidlibrary.widget.recycle.control.RecycleControl;
import com.fy.companylibrary.config.ArouterParamMNLM;
import com.fy.companylibrary.config.Param;
import com.hongniu.baselibrary.arouter.ArouterUtils;

import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.sang.thirdlibrary.picture.ImageInforBean;
import com.sang.thirdlibrary.picture.PictureClient;
import com.sang.thirdlibrary.picture.adapter.ImageAdapter;
import com.sang.thirdlibrary.picture.helper.ImageUpHelper;
import com.sang.thirdlibrary.picture.helper.interceptor.FileProgressRequestBody;
import com.sang.thirdlibrary.picture.holder.ImageAddHolder;
import com.sang.thirdlibrary.picture.utils.PicUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;


/**
 * 作者： ${PING} on 2018/11/19.
 * <p>
 * 图片上传功能的基础类
 */
public abstract class BaseImageUpActivity extends CompanyBaseActivity implements RecycleControl.OnItemClickListener<ImageInforBean>, ImageAdapter.DealFailImageListener, ImageAddHolder.OnAddPicturesListener, OnResultCallbackListener<LocalMedia> {

    protected ImageAdapter adapter;
    protected List<ImageInforBean> datas;
    protected ImageUpHelper helper;

    @Override
    public void initData() {
        super.initData();
        helper = new ImageUpHelper(mContext) {
            @Override
            public Observable<ImageInforBean> getUpClient(String path, ImageInforBean bean, FileProgressRequestBody.ProgressListener listener) {
                return upLoadClient(path, bean, listener);
            }
        };
        datas = new ArrayList<>();
        adapter = new ImageAdapter(mContext, datas);
        adapter.setDealFailImageListener(this);
        adapter.setItemClickListener(this);
        adapter.setShowLable(getShowLabel());
        adapter.setMaxCount(getMaxCount());
        adapter.setonAddPicturesListener(this);
    }

    protected abstract Observable<ImageInforBean> upLoadClient(String path, ImageInforBean bean, FileProgressRequestBody.ProgressListener listener);

    /**
     * 判断图片是否上传成功
     *
     * @return
     */
    protected boolean isUpLoadSuccess() {
        int finish = helper.isFinish();
        boolean hasFinish = (finish == 0);
        if (!hasFinish) {
            //有上传失败的图片
            if (finish < 0) {
                showErrorImageDialog(mContext, ((finish) * (-1)));
            } else {
                showImageDialog(mContext, finish);
            }
        }
        return hasFinish;
    }

    /**
     * 是否显示标签
     *
     * @return
     */
    public boolean getShowLabel() {
        return false;
    }

    /**
     * 是否显示进度条
     *
     * @return true 显示 false 不显示
     */
    protected boolean showProgress() {
        return true;
    }


    /**
     * 获取图片类型，用于在同一个页面有多个图片控件时候使用，进行区分
     *
     * @return
     */
    protected int getImageType() {
        return -1;
    }

    @Override
    public void onItemClick(int position, ImageInforBean date) {
        if (!CollectionUtils.isEmpty(datas)){
            ArrayList<String> result=new ArrayList<>();
            for (ImageInforBean data : datas) {
                result.add(TextUtils.isEmpty(data.getPathOriginal())?data.getPath():data.getPathOriginal());
            }
            ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_preview_image)
                    .withStringArrayList(Param.TRAN,result)
                    .withInt(Param.TYPE,position)
                    .navigation();

        }

    }




    @Override
    protected boolean isUseEventBus() {
        return true;
    }

    /**
     * 更改图片最大数量，默认是10
     *
     * @return
     */
    public int getMaxCount() {
        return 10;
    }

    /**
     * 重新上传指定图片
     *
     * @param position
     * @param failBean 上传失败图片的信息
     */
    @Override
    public void upRepeat(int position, ImageInforBean failBean) {
        helper.upImage(failBean);
    }

    /**
     * 取消指定图片
     *
     * @param position
     * @param failBean
     */
    @Override
    public void onClickCancle(int position, ImageInforBean failBean) {
        datas.remove(position);
        adapter.notifyItemDeleted(position);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ImageInforBean event) {
        if (event != null) {
            if (datas.contains(event)) {
                int i = datas.indexOf(event);
                adapter.notifyItemChanged(i, event);
            }
        }
    }


    /**
     * 图片尚未上传完成时候，不在上传图片，仅仅保留当前图片
     */
    protected abstract void giveUp();


    /**
     * 有正在上传的图片
     *
     * @param context
     * @param count
     */
    public void showImageDialog(Context context, int count) {
        new CenterDialog.Builder()
                .setBtLeft("放弃上传")
                .setBtRight("继续上传")
                .setDialogTitle("您有" + count + "张图片正在上传!")
                .setLeftClickListener(new CenterDialog.OnButtonLeftClickListener() {
                    @Override
                    public void onLeftClick(View view, Dialog dialog) {
                        dialog.dismiss();
                        giveUp();
                    }
                })
                .setRightClickListener(new CenterDialog.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .creatDialog(mContext)
                .show();

    }

    /**
     * 有上传失败的图片
     *
     * @param context
     * @param count
     */
    public void showErrorImageDialog(Context context, int count) {
        new CenterDialog.Builder()
                .setBtLeft("放弃上传")
                .setBtRight("重新上传")
                .setDialogTitle("您有" + count + "张图片上传失败!")
                .setLeftClickListener(new CenterDialog.OnButtonLeftClickListener() {
                    @Override
                    public void onLeftClick(View view, Dialog dialog) {
                        dialog.dismiss();
                        giveUp();
                    }
                })
                .setRightClickListener(new CenterDialog.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, Dialog dialog) {
                        dialog.dismiss();
                        helper.upDates(datas);
                    }
                })
                .creatDialog(mContext)
                .show();
    }


    @Override
    public void onClickAdd() {
        List<LocalMedia> list = new ArrayList<>();
        if (datas != null) {
            for (ImageInforBean data : datas) {
                LocalMedia media = new LocalMedia();
                media.setChecked(true);
                media.setPath(TextUtils.isEmpty(data.getPathOriginal())?data.getPath():data.getPathOriginal());
                list.add(media);
            }
        }
        new PictureClient().startPhoto(this,   getMaxCount(), list, this);
    }

    /**
     * return LocalMedia result
     *
     * @param result
     */
    @Override
    public void onResult(List<LocalMedia> result) {
        if (result == null) {
            result = new ArrayList<>();
        }
        int index = datas.size();
        PicUtils.filtImage(3, datas, result);
        adapter.notifyDataSetChanged();
        helper.upDates(datas);
    }
}




