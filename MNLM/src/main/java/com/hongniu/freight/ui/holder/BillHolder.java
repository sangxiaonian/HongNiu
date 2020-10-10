package com.hongniu.freight.ui.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fy.androidlibrary.utils.CommonUtils;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.BillInfoListBean;

/**
 * 作者：  on 2020/2/13.
 */
public class BillHolder extends BaseHolder<BillInfoListBean> {

    public BillHolder(Context context, ViewGroup parent ) {
        super(context, parent, R.layout.mnlm_item_bill);

    }

    @Override
    public void initView(View itemView, int position, BillInfoListBean data) {
        super.initView(itemView, position, data);
        TextView tvCount = itemView.findViewById(R.id.tv_count);
        TextView tvStatus = itemView.findViewById(R.id.tv_statute);
        TextView tv_des = itemView.findViewById(R.id.tv_des);
        TextView tv_time = itemView.findViewById(R.id.tv_time);
        int financeType = data.getFinanceType();
        StringBuffer buffer = new StringBuffer();
        String time="";
        String title="";
        String des="";
        String ordernumber = data.getOrderNum();
        if (financeType == 1) {//支出
            CommonUtils.setText(tvStatus,data.getTitle());
            time="付款时间：" +( data.getPayTime()==null?"--":data.getPayTime());
            des="车牌号码：" + (data.getCarNum() == null ? "" : data.getCarNum());
            title="订单号：" + (ordernumber == null ? "" : ordernumber);
        } else if (financeType == 2) {//收入
            title="订单运费收入" ;
            buffer.append("+");
            des="订单号：" + (ordernumber == null ? "" : ordernumber);
            time="收款时间：" +( data.getPayTime()==null?"--":data.getPayTime());
        } else {//所有情况
            title="账单" ;
            des="订单号：" + (ordernumber == null ? "" : ordernumber);
            time="时间"+ ( data.getPayTime()==null?"--":data.getPayTime());
        }
        buffer.append(data.getMoney()+"");
        CommonUtils.setText(tv_time,time);
        CommonUtils.setText(tvStatus,title);
        CommonUtils.setText(tv_des,des);
        CommonUtils.setText(tvCount,buffer.toString().trim());
    }
}
