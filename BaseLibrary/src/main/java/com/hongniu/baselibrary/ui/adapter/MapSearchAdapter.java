package com.hongniu.baselibrary.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.hongniu.baselibrary.R;
import com.hongniu.baselibrary.utils.Utils;

import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.recycleview.inter.OnItemClickListener;

import java.util.List;

/**
 * 作者： ${PING} on 2018/11/1.
 */
public class MapSearchAdapter extends XAdapter<PoiItem> {
    public MapSearchAdapter(Context context, List<PoiItem> list) {
        super(context, list);
    }



    OnItemClickListener<PoiItem> clickListener;

    public XAdapter<PoiItem> setClickListener(OnItemClickListener<PoiItem> clickListener) {
        this.clickListener = clickListener;
        return this;
    }

    @Override
    public BaseHolder<PoiItem> initHolder(ViewGroup parent, int viewType) {
        return new BaseHolder<PoiItem>(context, parent, R.layout.map_select_item) {
            @Override
            public void initView(View itemView, final int position, final PoiItem data) {
                super.initView(itemView, position, data);
                TextView tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                TextView tvDes = (TextView) itemView.findViewById(R.id.tv_des);
                final ImageView img = itemView.findViewById(R.id.img);
                img.setVisibility(View.GONE);
                String placeInfor= Utils.dealPioPlace(data);
                tvDes.setText(placeInfor);
                tvTitle.setText(data.getTitle());
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        img.setVisibility(View.VISIBLE);
                        if (clickListener != null) {
                            clickListener.onItemClick(position, data);
                        }

                    }
                });
            }
        };

    }


}