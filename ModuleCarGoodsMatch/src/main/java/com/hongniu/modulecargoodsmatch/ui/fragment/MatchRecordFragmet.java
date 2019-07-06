package com.hongniu.modulecargoodsmatch.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterParams;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.base.RefrushFragmet;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.OrderCreatParamBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.utils.BaseUtils;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.baselibrary.widget.dialog.ListDialog;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.GoodsOwnerInforBean;
import com.hongniu.modulecargoodsmatch.entity.MatchChooseGrapBean;
import com.hongniu.modulecargoodsmatch.entity.MatchGrapSingleDetailBean;
import com.hongniu.modulecargoodsmatch.entity.MatchQueryGoodsInforParams;
import com.hongniu.modulecargoodsmatch.net.HttpMatchFactory;
import com.sang.common.event.BusFactory;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.rong.eventbus.EventBus;

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
                    public void initView(View itemView, int position, final MatchGrapSingleDetailBean match) {
                        super.initView(itemView, position, match);
                        TextView tvTitle = itemView.findViewById(R.id.tv_title);
                        TextView tvCarInfor = itemView.findViewById(R.id.tv_car_infor);
                        TextView tv_car_number = itemView.findViewById(R.id.tv_car_number);
                        TextView bt = itemView.findViewById(R.id.bt);
                        TextView btCancle = itemView.findViewById(R.id.btn_cancel);
                        tvTitle.setText(String.format("%s 已支付%s元保证金", match.driverName, match.robAmount));
                        tvCarInfor.setText(String.format("车辆信息：%s", match.carTypeName));
                        tv_car_number.setText(String.format("车牌号：%s", match.carNum));
                        btCancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                resetDriver(match);
                            }
                        });
                        bt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HttpMatchFactory
                                        .choseMatch(match.goodsSourceId, match.id)
                                        .subscribe(new NetObserver<MatchChooseGrapBean>(MatchRecordFragmet.this) {
                                            @Override
                                            public void doOnSuccess(MatchChooseGrapBean data) {
                                                dialog.dismiss();
                                                OrderCreatParamBean bean = new OrderCreatParamBean();
                                                bean.setGsNum(data.gsNum);
                                                bean.setDeliveryDate(data.startTime);
                                                bean.setStartPlaceInfo(data.startPlaceInfo);
                                                bean.setStartLongitude(data.startPlaceX);
                                                bean.setStartLatitude(data.startPlaceY);
                                                bean.setDestinationInfo(data.destinationInfo);
                                                bean.setDestinationLongitude(data.destinationX);
                                                bean.setDestinationLatitude(data.destinationY);
                                                bean.setDepartNum(data.departNum);
                                                bean.setGoodName(data.goodName);
                                                bean.setGoodVolume(data.goodVolume);
                                                bean.setGoodWeight(data.goodWeight);
                                                bean.setMoney(data.freightAmount);
                                                bean.setDriverMobile(data.driverMobile);
                                                bean.setDriverName(data.driverName);
                                                bean.setCarNum(data.carNum);
                                                bean.setOwnerName(data.ownerName);
                                                bean.setOwnerMobile(data.ownerMobile);
                                                ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_create).navigation(getContext());
                                                EventBus.getDefault().postSticky(bean);
                                                BusFactory.getBus().postSticky(bean);
                                            }
                                        });

                            }
                        });
                    }
                }

                        ;
            }
        });

    }

    private void resetDriver(final MatchGrapSingleDetailBean match) {
        CenterAlertBuilder builder = Utils.creatDialog(getContext(), "确认删除车货匹配？", "发布一旦删除，无法恢复", "返回记录", "确定删除");
        builder.setRightClickListener(new DialogControl.OnButtonRightClickListener() {
            @Override
            public void onRightClick(View view, DialogControl.ICenterDialog cdialog) {
                cdialog.dismiss();
                HttpMatchFactory
                        .resetDriver(match.goodsSourceId, match.id)
                        .subscribe(new NetObserver<Object>(MatchRecordFragmet.this) {
                            @Override
                            public void doOnSuccess(Object data) {
                                dialog.dismiss();
                                queryData(true,true);
                            }

                        });

            }
        })
                .creatDialog(new CenterAlertDialog(getContext()))
                .show();
        ;


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
                return new BaseHolder<GoodsOwnerInforBean>(getContext(), parent, R.layout.item_match_my_record) {
                    @Override
                    public void initView(View itemView, final int position, final GoodsOwnerInforBean data) {
                        super.initView(itemView, position, data);
                        TextView bt_left = itemView.findViewById(R.id.bt_left);
                        TextView bt_right = itemView.findViewById(R.id.bt_right);
                        TextView tvTitle = itemView.findViewById(R.id.tv_title);
                        TextView tvTime = itemView.findViewById(R.id.tv_time);
                        TextView tv_start_point = itemView.findViewById(R.id.tv_start_point);
                        TextView tv_end_point = itemView.findViewById(R.id.tv_end_point);
                        TextView tv_goods = itemView.findViewById(R.id.tv_goods);
                        TextView tv_price = itemView.findViewById(R.id.tv_price);
                        TextView tv_remark = itemView.findViewById(R.id.tv_remark);
                        TextView tv1 = itemView.findViewById(R.id.tv1);
                        TextView tv_state = itemView.findViewById(R.id.tv_state);

                        tv_state.setVisibility(View.VISIBLE);
                        tv1.setVisibility(View.GONE);
                        tv_price.setVisibility(View.GONE);
                        String statusName = "";
                        switch (data.status) {
                            case 0:
                                statusName = "待接单";
                                break;
                            case 1:
                                statusName = "待确认";
                                break;
                            case 2:
                                statusName = "已下单";
                                break;
                            case 3:
                                statusName = "运输中";
                                break;
                            case 4:
                                statusName = "已完成";
                                break;
                            case 5:
                                statusName = "已失效";
                                break;
                        }

                        tv_state.setText(statusName);
                        tvTitle.setText(String.format("%s正在寻找%s（%s米）", data.userName == null ? "" : data.userName, data.carType == null ? "车辆" : data.carType, data.carLength == null ? "0" : data.carLength));
                        tvTime.setText(String.format("需要发货时间：%s", data.startTime));
                        tv_start_point.setText(String.format("发货地：%s", data.startPlaceInfo));
                        tv_end_point.setText(String.format("收货地：%s", data.destinationInfo));
                        tv_goods.setText(String.format("货物名：%s", data.goodsSourceDetail));
                        tv_remark.setVisibility(TextUtils.isEmpty(data.remark) ? View.GONE : View.VISIBLE);
                        tv_remark.setText(String.format("货主备注：%s", data.remark == null ? "" : data.remark));

                        bt_left.setText("删除发布");
                        bt_right.setText("接单信息");

                        bt_left.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                CenterAlertBuilder builder = Utils.creatDialog(mContext, "确认删除车货匹配？", "发布一旦删除，无法恢复", "返回记录", "确定删除");
                                builder.setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                                    @Override
                                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                                        dialog.dismiss();
                                        deletedMatch(data.id, position);

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

                                queryGrapDetail(data);

                            }
                        });


                    }
                };
            }
        };
    }

    private PageBean<MatchGrapSingleDetailBean> inforBean;//所选择的订单信息

    /**
     * 接单明细
     *
     * @param data
     */
    private void queryGrapDetail(GoodsOwnerInforBean data) {
        HttpMatchFactory
                .queryGraoDetail(data.id, 1)
                .subscribe(new NetObserver<PageBean<MatchGrapSingleDetailBean>>(this) {
                    @Override
                    public void doOnSuccess(PageBean<MatchGrapSingleDetailBean> data) {

                        if (BaseUtils.isCollectionsEmpty(data.getList())) {
                            ToastUtils.getInstance().show("暂无接单记录");
                        } else {
                            singleDetail.clear();
                            singleDetail.addAll(data.getList());
                            dialog.setTitle("接单信息");
                            dialog.setDescribe("请尽快确认并下单，超时将自动取消该接单信息");
                            listAdapter.notifyDataSetChanged();
                            dialog.show(getChildFragmentManager(), "");
                            inforBean = data;
                        }

                    }

                });
    }

    /**
     * 删除发布
     *
     * @param id
     * @param position
     */
    private void deletedMatch(String id, final int position) {
        HttpMatchFactory.deleteMatchGoods(id)
                .subscribe(new NetObserver<Object>(this) {
                    @Override
                    public void doOnSuccess(Object data) {
                        adapter.notifyItemDeleted(position);
                        queryData(true);
                        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();

                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        queryData(true);
    }

}


