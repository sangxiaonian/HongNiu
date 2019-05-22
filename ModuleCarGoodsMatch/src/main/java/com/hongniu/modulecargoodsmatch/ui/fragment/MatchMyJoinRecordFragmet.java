package com.hongniu.modulecargoodsmatch.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParams;
import com.hongniu.baselibrary.base.RefrushFragmet;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.PagerParambean;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.MatchMyJoinGoodsInofrBean;
import com.hongniu.modulecargoodsmatch.net.HttpMatchFactory;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.thirdlibrary.chact.ChactHelper;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2019/5/12.
 * 我发布的车货匹配列表
 */
@Route(path = ArouterParams.fragment_match_my_join)
public class MatchMyJoinRecordFragmet extends RefrushFragmet<MatchMyJoinGoodsInofrBean> {


    @Override
    protected View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_match_my_record, null, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queryData(true);
    }

    @Override
    protected void initData() {
        super.initData();

    }


    @Override
    protected Observable<CommonBean<PageBean<MatchMyJoinGoodsInofrBean>>> getListDatas() {
        PagerParambean params = new PagerParambean(currentPage);
        return HttpMatchFactory.queryMatchMyJoinGoodsInfor(params);
    }

    @Override
    protected XAdapter<MatchMyJoinGoodsInofrBean> getAdapter(List<MatchMyJoinGoodsInofrBean> datas) {
        return new XAdapter<MatchMyJoinGoodsInofrBean>(getContext(), datas) {
            @Override
            public BaseHolder<MatchMyJoinGoodsInofrBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<MatchMyJoinGoodsInofrBean>(getContext(), parent,R.layout.item_match_my_record) {
                    @Override
                    public void initView(View itemView, int position, MatchMyJoinGoodsInofrBean data) {
                        super.initView(itemView, position, data);
                        TextView tvTitle = itemView.findViewById(R.id.tv_title);
                        TextView tvTime = itemView.findViewById(R.id.tv_time);
                        TextView tv_start_point = itemView.findViewById(R.id.tv_start_point);
                        TextView tv_end_point = itemView.findViewById(R.id.tv_end_point);
                        TextView tv_goods = itemView.findViewById(R.id.tv_goods);
                        TextView tv_price = itemView.findViewById(R.id.tv_price);
                        TextView tv1 = itemView.findViewById(R.id.tv1);
                        final TextView bt_left = itemView.findViewById(R.id.bt_left);
                        TextView bt_right = itemView.findViewById(R.id.bt_right);
                        bt_left.setText("取消参与");
                        bt_right.setText("联系货主");
                        bt_left.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                CenterAlertBuilder builder = Utils.creatDialog(mContext, "确认取消参与抢单？", "参与一旦取消，无法恢复", "返回记录", "确定取消");
                                builder.setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                                    @Override
                                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                                        dialog.dismiss();
                                        ToastUtils.getInstance().show("确定取消");
                                        queryData(true, true);
                                    }
                                })
                                        .creatDialog(new CenterAlertDialog(mContext))
                                        .show();
                                ;
                            }
                        });
                        bt_right.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                ChactHelper.getHelper().startPriver(mContext, "10", "测试");
                                ToastUtils.getInstance().show("联系货主");
                            }
                        });


                    }
                };
            }
        };
    }
}

