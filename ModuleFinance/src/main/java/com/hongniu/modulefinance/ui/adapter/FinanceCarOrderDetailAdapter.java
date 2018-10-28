package com.hongniu.modulefinance.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongniu.baselibrary.widget.order.CommonOrderUtils;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.control.OnItemClickListener;
import com.hongniu.modulefinance.entity.FinanceQueryCarDetailBean;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;

import java.util.List;

/**
 * 作者： ${PING} on 2018/10/8.
 * 车辆订单详情
 */
public class FinanceCarOrderDetailAdapter extends XAdapter<FinanceQueryCarDetailBean> {

    OnItemClickListener<FinanceQueryCarDetailBean> itemClickListener;


    public FinanceCarOrderDetailAdapter(Context context, List<FinanceQueryCarDetailBean> list) {
        super(context, list);
    }

    @Override
    public int getViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public BaseHolder<FinanceQueryCarDetailBean> initHolder(ViewGroup parent, int viewType) {

        if (viewType==0){
            return new BaseHolder<FinanceQueryCarDetailBean>(context, parent, R.layout.finance_item_cardetail) {
                @Override
                public void initView(View itemView, final int position, final FinanceQueryCarDetailBean data) {
                    super.initView(itemView, position, data);
                    TextView tvOrder = itemView.findViewById(R.id.tv_title);
                    TextView time = itemView.findViewById(R.id.tv_time);
                    TextView state= itemView.findViewById(R.id.tv_state);
                    if (data.getBean()!=null) {
                        tvOrder.setText("订单号：" + data.getBean().getOrderNum());
                        time.setText("发车时间：" + data.getBean().getDeliveryDate());
                        state.setText(CommonOrderUtils.getOrderStateDes(data.getBean().getOrderState()));
                    }else {
                        tvOrder.setText("订单号：" );
                        time.setText("发车时间：" );
                        state.setText("");

                    }

                    itemView.setOnClickListener(new View.OnClickListener() {
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

        }else {
            return new BaseHolder<FinanceQueryCarDetailBean>(context, parent, R.layout.finance_item_text){
                @Override
                public void initView(View itemView, int position, FinanceQueryCarDetailBean data) {
                    super.initView(itemView, position, data);
                    TextView title = itemView.findViewById(R.id.tv_title);
                    title.setText((data.getTime()==null)?"":(data.getTime()));
                }
            };
        }

   }

    public void setItemClickListener(OnItemClickListener<FinanceQueryCarDetailBean> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
