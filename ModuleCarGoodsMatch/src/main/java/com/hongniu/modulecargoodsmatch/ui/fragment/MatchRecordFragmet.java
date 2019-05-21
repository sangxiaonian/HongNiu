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
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.baselibrary.widget.dialog.ListDialog;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.GoodsOwnerInforBean;
import com.hongniu.modulecargoodsmatch.entity.MatchGrapSingleDetailBean;
import com.hongniu.modulecargoodsmatch.entity.MatchQueryGoodsInforParams;
import com.hongniu.modulecargoodsmatch.net.HttpMatchFactory;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2019/5/12.
 * 我发布的车货匹配列表
 */
@Route(path = ArouterParams.fragment_match_my_record)
public class MatchRecordFragmet extends RefrushFragmet<GoodsOwnerInforBean> {


    private ListDialog<MatchGrapSingleDetailBean> dialog;
    private ArrayList<MatchGrapSingleDetailBean> singleDetail;
    private XAdapter<MatchGrapSingleDetailBean> listAdapter;

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
        dialog = new ListDialog<>();
        dialog.setAdapter(listAdapter = new XAdapter<MatchGrapSingleDetailBean>(getContext(), singleDetail = new ArrayList<>()) {


            @Override
            public BaseHolder<MatchGrapSingleDetailBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<MatchGrapSingleDetailBean>(getContext(), parent, R.layout.match_item_grap_single_detail) {
                    @Override
                    public void initView(View itemView, int position, MatchGrapSingleDetailBean data) {
                        super.initView(itemView, position, data);
                        TextView tvTitle = itemView.findViewById(R.id.tv_title);
                        TextView tvCarInfor = itemView.findViewById(R.id.tv_car_infor);
                        TextView bt = itemView.findViewById(R.id.bt);
                        tvTitle.setText("大佬 已支付2000元意向金");
                        tvCarInfor.setText("车辆信息：大货车");
                        bt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.getInstance().show("我要下单");
                            }
                        });
                    }
                };
            }
        });

    }


    @Override
    protected Observable<CommonBean<PageBean<GoodsOwnerInforBean>>> getListDatas() {
        MatchQueryGoodsInforParams params = new MatchQueryGoodsInforParams(currentPage);
        params.queryType = 2;
        return HttpMatchFactory.queryMatchGoodsInfor(params);
    }

    @Override
    protected XAdapter<GoodsOwnerInforBean> getAdapter(List<GoodsOwnerInforBean> datas) {
        return new XAdapter<GoodsOwnerInforBean>(getContext(), datas) {
            @Override
            public BaseHolder<GoodsOwnerInforBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<GoodsOwnerInforBean>(getContext(), parent,R.layout.item_match_my_record) {
                    @Override
                    public void initView(View itemView, int position, GoodsOwnerInforBean data) {
                        super.initView(itemView, position, data);
                        TextView bt_left = itemView.findViewById(R.id.bt_left);
                        TextView bt_right = itemView.findViewById(R.id.bt_right);
                        TextView tvTitle = itemView.findViewById(R.id.tv_title);
                        TextView tvTime = itemView.findViewById(R.id.tv_time);
                        TextView tv_start_point = itemView.findViewById(R.id.tv_start_point);
                        TextView tv_end_point = itemView.findViewById(R.id.tv_end_point);
                        TextView tv_goods = itemView.findViewById(R.id.tv_goods);
                        TextView tv_price = itemView.findViewById(R.id.tv_price);
                        TextView tv1 = itemView.findViewById(R.id.tv1);

                        tvTitle.setText(data.userName+"正在寻找"+data.carType);
                        tvTime.setText("需要发货时间："+data.startTime);
                        tv_start_point.setText("发货地："+data.startPlaceInfo);
                        tv_end_point.setText("收货地："+data.destinationInfo);
                        tv_goods.setText("货物名："+data.goodName);
                        bt_left.setText("删除发布");
                        bt_right.setText("抢单明细");

                        bt_left.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                CenterAlertBuilder builder = Utils.creatDialog(mContext, "确认删除车货匹配？", "发布一旦删除，无法恢复", "返回记录", "确定删除");
                                builder.setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                                    @Override
                                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                                        dialog.dismiss();
                                        ToastUtils.getInstance().show("删除发布");
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
                                ToastUtils.getInstance().show("抢单明细");
                                int random = ConvertUtils.getRandom(2, 10);
                                singleDetail.clear();
                                dialog.setTitle("抢单明细");
                                dialog.setDescribe("共有 " + random + " 人支付抢单意向金，你可选择1人完成下单");
                                for (int i = 0; i < random; i++) {
                                    singleDetail.add(new MatchGrapSingleDetailBean());
                                }
                                listAdapter.notifyDataSetChanged();
                                dialog.show(getChildFragmentManager(), "");

                            }
                        });


                    }
                };
            }
        };
    }
}


