package com.hongniu.modulefinance.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.entity.BalanceOfAccountBean;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;

import java.util.List;

/**
 * 作者： ${PING} on 2018/10/8.
 * 我的钱包 余额明细Adapter
 */
public class BalanceOfAccountAdapter extends XAdapter<BalanceOfAccountBean> {

    public BalanceOfAccountAdapter(Context context, List<BalanceOfAccountBean> list) {
        super(context, list);
    }

    @Override
    public BaseHolder<BalanceOfAccountBean> initHolder(ViewGroup parent, int viewType) {
        return new BaseHolder<BalanceOfAccountBean>(context, parent, R.layout.finance_item_finance) {
            @Override
            public void initView(View itemView, final int position, final BalanceOfAccountBean data) {
                super.initView(itemView, position, data);
                TextView tvOrder = itemView.findViewById(R.id.tv_title);
                TextView tvCarNum = itemView.findViewById(R.id.tv_car_num);
                TextView tvTime = itemView.findViewById(R.id.tv_time);
                TextView tvPrice = itemView.findViewById(R.id.tv_price);

                if (position % 2 == 0) {

                    tvCarNum.setText("车牌号码：" + "皖B555555");
                    tvTime.setText("收款时间：" + "2018-06-30 23:30:53");
                } else {
                    tvCarNum.setText("提现账户：" + "工商银行 2737 **** **23");
                    tvTime.setText("提现时间：" + "2018-06-30 23:30:53");
                }

                tvOrder.setText(getTitle(data.getFundsources(),data.getInorexptype()==1));
                tvPrice.setText((data.getInorexptype()==2?"-":"+")+data.getAmount());


//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        OrderDetailDialog orderDetailDialog = new OrderDetailDialog(context);
//                        orderDetailDialog.setOrdetail(data);
//                        new BottomAlertBuilder()
//                                .creatDialog(orderDetailDialog)
//                                .show();
//
//                    }
//                });
//                ;
            }
        };
    }


    /**
     * 标题表述
     * @param fundsources 支付来源
     * @param b   true 收入
     * @return
     */
    public String getTitle(int fundsources, boolean b) {
        String des = "未知来源";
        StringBuffer buffer=new StringBuffer();

        switch (fundsources) {
            case 1:
                des=b?"我的保费返佣":"支付订单保费";
                break;
            case 2:
                des=b?"订单运费收入":"支付订单运费";
                break;
            case 3:
                des=b?"支付订单保费和运费":"订单保费和运费收入";

                break;
            case 4:
                des="我的保费返佣";
                break;
            case 5:
                break;
            case 6:
                des="好友保费返佣";
                break;
        }
        return des;
    }


}
