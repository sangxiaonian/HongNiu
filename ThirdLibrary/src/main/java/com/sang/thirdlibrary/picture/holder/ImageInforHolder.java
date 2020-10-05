package com.sang.thirdlibrary.picture.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fy.androidlibrary.imgload.ImageLoader;
import com.fy.androidlibrary.widget.RoundProgress;
import com.fy.androidlibrary.widget.recycle.control.RecycleControl;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.sang.thirdlibrary.R;
import com.sang.thirdlibrary.picture.ImageInforBean;
import com.sang.thirdlibrary.picture.adapter.ImageAdapter;

/**
 * 作者： ${PING} on 2018/11/13.
 * <p>
 * 用来显示图片的holder
 */
public class ImageInforHolder extends BaseHolder<ImageInforBean> {
    private RecycleControl.OnItemClickListener<ImageInforBean> itemClickListener;
    private View fail;
    private RoundProgress progress;
    private boolean showLable = true;//是否显示标签



    public ImageInforHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.item_image_infor);
    }

    public void setShowLable(boolean showLable) {
        this.showLable = showLable;
    }

    public void setItemClickListener(RecycleControl.OnItemClickListener<ImageInforBean> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    ImageAdapter.DealFailImageListener dealFailImageListener;

    public void setDealFailImageListener(ImageAdapter.DealFailImageListener dealFailImageListener) {
        this.dealFailImageListener = dealFailImageListener;
    }


    @Override
    public void initView(final View itemView, final int position, final ImageInforBean data) {
        super.initView(itemView, position, data);
        ImageView image = itemView.findViewById(R.id.image);
        progress = itemView.findViewById(R.id.progress);
        fail = itemView.findViewById(R.id.fail);
        ImageView tvReload = itemView.findViewById(R.id.img_fail);//重新上传
        ImageLoader.getLoader().load(mContext, image, data.getPath());
        setProgress(data);
        tvReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealFailImageListener.upRepeat(position, data);
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(position, data);
                }
            }
        });

    }


    public void setProgress(ImageInforBean data) {
        if (data.getErrorType() >= 0) {//图片上传失败
            //图片上传失败分为两种情况，0网络异常，1图片过小
            //如果图片上传失败
            fail.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
        } else {
            if (data.getProgress() < 100) {//图片上传中
                fail.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                progress.setProgress(data.getProgress());
            } else {//上传完成
                fail.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);
            }
        }


    }
}
