package com.hongniu.moduleorder.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OnItemClickListener;
import com.hongniu.moduleorder.control.OrderEvent;
import com.sang.common.event.BusFactory;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;

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
                String placeInfor;
                if (data.getProvinceName()!=null&&data.getProvinceName().equals( data.getCityName())){
                      placeInfor = data.getProvinceName()   + data.getAdName()
                            + data.getSnippet();
                }else {
                      placeInfor = data.getProvinceName() + data.getCityName() + data.getAdName()
                            + data.getSnippet();
                }
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
