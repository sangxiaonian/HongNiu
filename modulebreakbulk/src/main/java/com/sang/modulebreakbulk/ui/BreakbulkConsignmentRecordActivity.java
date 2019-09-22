package com.sang.modulebreakbulk.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsBreakbulk;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.modulebreakbulk.R;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.modulebreakbulk.entity.BreakbulkCompanyInfoParam;
import com.sang.modulebreakbulk.entity.BreakbulkConsignmentInfoBean;
import com.sang.modulebreakbulk.entity.BreakbulkConsignmentRecordBean;
import com.sang.modulebreakbulk.net.HttpBreakFactory;
import com.sang.thirdlibrary.chact.ChactHelper;

import java.util.List;

import io.reactivex.Observable;

/**
 * @data 2019/9/22
 * @Author PING
 * @Description 零担发货发货记录
 */
@Route(path = ArouterParamsBreakbulk.activity_breakbulk_consignment_record)
public class BreakbulkConsignmentRecordActivity extends RefrushActivity<BreakbulkConsignmentInfoBean> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakbulk_consignment_record);
        setToolbarTitle("发货记录");
        initView();

        initData();
        initListener();
        queryData(true);
    }

    @Override
    protected Observable<CommonBean<PageBean<BreakbulkConsignmentInfoBean>>> getListDatas() {
        return HttpBreakFactory.queryBreakbulkConsignmentRecord(currentPage);
    }

    @Override
    protected XAdapter<BreakbulkConsignmentInfoBean> getAdapter(List<BreakbulkConsignmentInfoBean> datas) {
        return new XAdapter<BreakbulkConsignmentInfoBean>(mContext, datas) {
            @Override
            public BaseHolder<BreakbulkConsignmentInfoBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<BreakbulkConsignmentInfoBean>(mContext, parent, R.layout.item_breakbulk_record) {
                    @Override
                    public void initView(View itemView, int position, final BreakbulkConsignmentInfoBean data) {
                        super.initView(itemView, position, data);
                        TextView tv_company = itemView.findViewById(R.id.tv_company);
                        TextView tv_phone = itemView.findViewById(R.id.tv_phone);
                        ImageView img_phone = itemView.findViewById(R.id.img_phone);
                        ImageView img_chat = itemView.findViewById(R.id.img_chat);
                        TextView tv_address = itemView.findViewById(R.id.tv_address);
                        TextView tv_cargo_name = itemView.findViewById(R.id.tv_cargo_name);
                        TextView tv_cargo_weight = itemView.findViewById(R.id.tv_cargo_weight);
                        TextView tv_cargo_size = itemView.findViewById(R.id.tv_cargo_size);
                        TextView tv_price = itemView.findViewById(R.id.tv_price);
                        TextView tv_price_gap = itemView.findViewById(R.id.tv_price_gap);
                        TextView tv_order = itemView.findViewById(R.id.tv_order);
                        TextView bt_pay = itemView.findViewById(R.id.bt_pay);



                       tv_company.setText("收货单位："+CommonUtils.getText(data.getReceiptUserName()));
                       tv_phone.setText("收货人电话："+CommonUtils.getText(data.getReceiptMobile()));
                       tv_address.setText("收货人地址："+CommonUtils.getText(data.getDestinationInfo()));
                       tv_cargo_name.setText("货物名称："+CommonUtils.getText(data.getGoodsName()));
                       tv_cargo_weight.setText("货物重量："+CommonUtils.getText(data.getGoodsWeight()));
                       tv_cargo_size.setText("货物体积："+CommonUtils.getText(data.getGoodsVolume()));
                       tv_price.setText("预估运费："+CommonUtils.getText(data.getEstimateFare()));
                       tv_price_gap.setText("运费差额："+CommonUtils.getText(data.getBalanceFare()));
                       tv_order.setText("运输单号："+CommonUtils.getText(data.getWaybillNum()));

                       if (data.getEstimateFareIsPay()==0){
                           //预估运费未支付
                           bt_pay.setText("支付运费");
                       }else if (data.getActualFareIsPay()==0){
                           bt_pay.setText("支付运费差额");
                       }else {
                           bt_pay.setText("查看运单状态");
                       }

                        img_phone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CommonUtils.toDial(mContext, data.getReceiptMobile());
                            }
                        });
                        img_chat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ChactHelper.getHelper().startPriver(mContext, data.getId(), data.getReceiptUserName());
                            }
                        });

                        bt_pay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.getInstance().show("支付");
                            }
                        });

                    }
                };
            }
        };
    }
}
