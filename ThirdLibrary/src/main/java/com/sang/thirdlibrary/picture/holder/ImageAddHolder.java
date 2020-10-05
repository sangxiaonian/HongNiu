package com.sang.thirdlibrary.picture.holder;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fy.androidlibrary.utils.permission.PermissionUtils;
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.sang.thirdlibrary.R;


/**
 * 作者： ${PING} on 2017/8/31.
 */

public class ImageAddHolder extends PeakHolder {


    private OnAddPicturesListener addPicturesListener;

    public  interface OnAddPicturesListener{
        void onClickAdd();
    }


    public ImageAddHolder(View itemView) {
        super(itemView);
    }

    public ImageAddHolder(Context context, int layoutID) {
        super(context, layoutID);
    }

    public ImageAddHolder(Context context, ViewGroup parent, int layoutID) {
        super(context, parent, layoutID);
    }

    public ImageAddHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.item_image_infor);
    }

    @Override
    public void initView(View itemView, int position) {
        super.initView(itemView, position);
        ImageView image = itemView.findViewById(R.id.image);
        image.setImageResource(R.drawable.ic_add_pic);
        image.setVisibility(View.VISIBLE);
        itemView.findViewById(R.id.progress).setVisibility(View.GONE);
        itemView.findViewById(R.id.fail).setVisibility(View.GONE);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addPicturesListener!=null){
                    addPicturesListener.onClickAdd();
                }
            }
        });
    }




    public void setonAddPicturesListener(OnAddPicturesListener addPicturesListener) {
        this.addPicturesListener = addPicturesListener;
    }


    /**
     * 启动相机拍照
     *
     * @param context
     */
    public void applyCamera(final Activity context) {
        PermissionUtils.applyCamera(context, new PermissionUtils.onApplyPermission() {
            @Override
            public void hasPermission() {
                if (addPicturesListener!=null){
                    addPicturesListener.onClickAdd();
                }
            }

            @Override
            public void noPermission() {

            }
        });

    }

}
