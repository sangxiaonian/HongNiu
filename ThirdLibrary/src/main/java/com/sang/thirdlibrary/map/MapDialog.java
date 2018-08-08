package com.sang.thirdlibrary.map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.widget.dialog.ListDialog;
import com.sang.thirdlibrary.R;

import java.util.List;

/**
 * 作者： ${PING} on 2018/8/8.
 */
public class MapDialog extends ListDialog<PoiItem> {
    public MapDialog(Context context) {
        super(context);
    }

    private int layoutID = R.layout.map_select_item;
    private int selectPositio = -1;

    @Override
    protected XAdapter<PoiItem> initAdapter(Context mContext, List<PoiItem> datas1) {

        XAdapter<PoiItem> xAdapter = new XAdapter<PoiItem>(mContext, datas1) {
            /**
             * 初始化ViewHolder,{@link XAdapter#onCreateViewHolder(ViewGroup, int)}处,用于在非头布局\脚布局\刷新时候
             * 调用
             *
             * @param parent   父View,即为RecycleView
             * @param viewType holder类型,在{@link XAdapter#getItemViewType(int)}处使用
             * @return BaseHolder或者其父类
             */
            @Override
            public BaseHolder<PoiItem> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<PoiItem>(context, parent, layoutID) {
                    @Override
                    public void initView(View itemView, final int position, final PoiItem data) {
                        super.initView(itemView, position, data);
                        TextView tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                        TextView tvDes = (TextView) itemView.findViewById(R.id.tv_des);
                        ImageView img = itemView.findViewById(R.id.img);
                        if (selectPositio == position) {
                            img.setVisibility(View.VISIBLE);
                        } else {
                            img.setVisibility(View.GONE);
                        }

                        tvDes.setText(data.getSnippet());
                        tvTitle.setText(data.getTitle());
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (selectPositio != position) {
                                    selectPositio = position;
                                    adapter.notifyDataSetChanged();
                                }


                                if (rightListener != null) {
                                    rightListener.entryListener();
                                }
                            }
                        });
                    }
                };
            }
        };


        return xAdapter;
    }

    @Override
    public ListDialog<PoiItem> setDatas(List<PoiItem> datas) {
        return super.setDatas(datas);
    }
}
