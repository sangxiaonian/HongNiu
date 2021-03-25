package com.hongniu.modulefinance.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fy.androidlibrary.utils.CommonUtils;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.control.OnItemClickListener;
import com.hongniu.modulefinance.entity.NiuOfAccountBean;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.thirdlibrary.chact.ChactHelper;

import java.util.List;

/**
 * 作者： ${PING} on 2018/10/8.
 * 牛贝已入账、待入账 Adapter
 */
public class NiuOfAccountAdapter extends XAdapter<NiuOfAccountBean> {

    OnItemClickListener<NiuOfAccountBean> itemClickListener;


    public NiuOfAccountAdapter(Context context, List<NiuOfAccountBean> list) {
        super(context, list);
    }

    @Override
    public BaseHolder<NiuOfAccountBean> initHolder(ViewGroup parent, int viewType) {
        return new BaseHolder<NiuOfAccountBean>(context, parent, R.layout.finance_item_niu_finance) {
            @Override
            public void initView(View itemView, final int position, final NiuOfAccountBean data) {
                super.initView(itemView, position, data);
                View ll_item = itemView.findViewById(R.id.ll_item);
                TextView tvOrder = itemView.findViewById(R.id.tv_title);
                TextView tvCarNum = itemView.findViewById(R.id.tv_car_num);
                TextView tvTime = itemView.findViewById(R.id.tv_time);
                TextView tvPrice = itemView.findViewById(R.id.tv_price);
                ImageView imagePhone = itemView.findViewById(R.id.img_phone);
                ImageView img_chact = itemView.findViewById(R.id.img_chact);


                final String name = !TextUtils.isEmpty(data.getName()) ?   data.getName():(TextUtils.isEmpty(data.getContact())?"":data.getContact());

                tvCarNum.setVisibility(View.GONE);
                tvOrder.setText(String.format(mContext.getString(R.string.friend_name), name)  );
                tvTime.setText(String.format(mContext.getString(R.string.finance_niu_phone_number),TextUtils.isEmpty(data.getMobile())?"":data.getMobile())  );
                tvPrice.setText(TextUtils.isEmpty(data.getIntegralStr())?"":data.getIntegralStr() );

                imagePhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonUtils.call(context, data.getMobile());
                    }
                });

                img_chact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //TODO 牛贝相关数据
                        ChactHelper.getHelper().startPriver(context, data.getRongId(), name);

                    }
                });


                ll_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemClickListener!=null){
                            itemClickListener.onItemClick(position,data);
                        }
                    }
                });
//                ;
            }
        };
    }

    public void setItemClickListener(OnItemClickListener<NiuOfAccountBean> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
