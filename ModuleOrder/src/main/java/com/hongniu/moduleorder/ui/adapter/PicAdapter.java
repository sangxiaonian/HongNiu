package com.hongniu.moduleorder.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OnItemClickListener;
import com.luck.picture.lib.entity.LocalMedia;
import com.sang.common.imgload.ImageLoader;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;

import java.util.List;

/**
 * 作者： ${PING} on 2018/10/22.
 * 图片上传选择后展示的adapter
 */
public class PicAdapter  extends XAdapter<LocalMedia>{


    OnItemClickListener<LocalMedia> listener;


    public PicAdapter(Context context, List<LocalMedia> list) {
        super(context, list);
    }

    public void setOnItemClickListener(OnItemClickListener<LocalMedia>  listener) {
        this.listener = listener;
    }

    @Override
    public BaseHolder<LocalMedia> initHolder(ViewGroup parent, int viewType) {
        return new BaseHolder<LocalMedia>(context,parent, R.layout.order_item_creat_order_img){
            @Override
            public void initView(View itemView, final int position, final LocalMedia data) {
                super.initView(itemView, position, data);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener!=null){
                            listener.onItemClick(position,data);
                        }
                    }
                });
                ImageView img = itemView.findViewById(R.id.img);
                ImageView img_deleted = itemView.findViewById(R.id.img_deleted);
                ImageLoader.getLoader().load(mContext,img,data.getPath());
                img_deleted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        list.remove(data);
                         notifyItemDeleted(position);
//                                PictureSelectorUtils.openExternalPreview((Activity) mContext,position,pics);
                    }
                });
            }
        };
    }
}
