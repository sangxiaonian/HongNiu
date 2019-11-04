package com.hongniu.modulecargoodsmatch.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterParamsMatch;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.base.RefrushFragmet;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.LoginBean;
import com.hongniu.baselibrary.entity.OrderCreatParamBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.utils.BaseUtils;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.baselibrary.widget.dialog.ListDialog;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.control.RecordFragmentControl;
import com.hongniu.modulecargoodsmatch.entity.GoodsOwnerInforBean;
import com.hongniu.modulecargoodsmatch.entity.MatchChooseGrapBean;
import com.hongniu.modulecargoodsmatch.entity.MatchGrapSingleDetailBean;
import com.hongniu.modulecargoodsmatch.entity.MatchQueryGoodsInforParams;
import com.hongniu.modulecargoodsmatch.net.HttpMatchFactory;
import com.hongniu.modulecargoodsmatch.ui.holder.MatchGoodsListHolder;
import com.sang.common.event.BusFactory;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.thirdlibrary.chact.ChactHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.rong.eventbus.EventBus;

/**
 * 作者： ${PING} on 2019/5/12.
 * 我发布的车货匹配列表
 */
@Deprecated
@Route(path = ArouterParamsMatch.fragment_match_my_record)
public class MatchRecordFragmet extends RefrushFragmet<GoodsOwnerInforBean> implements RecordFragmentControl.OnSwitchFiltrateListener {

    private String id;//自己的id，用于判断订单是否是自己创建的订单

    private ListDialog<MatchGrapSingleDetailBean> dialog;
    private ArrayList<MatchGrapSingleDetailBean> singleDetail;
    private XAdapter<MatchGrapSingleDetailBean> listAdapter;
    private MatchQueryGoodsInforParams params;
    private int type;//1.车货匹配列表(只包含可已接单) 2.我的发布列表

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null) {
            type = getArguments().getInt(Param.TYPE, 1);
        }
    }

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
        params = new MatchQueryGoodsInforParams(currentPage);
        LoginBean loginInfor = Utils.getLoginInfor();
        id = loginInfor == null ? "" : loginInfor.getId();
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
                        btCancle.setVisibility(match.status == 1 ? View.VISIBLE : View.GONE);
                        bt.setVisibility(match.status == 1 ? View.VISIBLE : View.GONE);
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
        CenterAlertBuilder builder = Utils.creatDialog(getContext(), "确认要重新找司机吗？", "", "取消", "确认");
        builder.hideContent()
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog cdialog) {
                        cdialog.dismiss();
                        HttpMatchFactory
                                .resetDriver(match.goodsSourceId, match.id)
                                .subscribe(new NetObserver<Object>(MatchRecordFragmet.this) {
                                    @Override
                                    public void doOnSuccess(Object data) {
                                        dialog.dismiss();
                                        queryData(true, true);
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
        params.queryType = type;
        return HttpMatchFactory.queryMatchGoodsInfor(params);
    }

    @Override
    protected XAdapter<GoodsOwnerInforBean> getAdapter(List<GoodsOwnerInforBean> datas) {
        return new XAdapter<GoodsOwnerInforBean>(getContext(), datas) {
            @Override
            public BaseHolder<GoodsOwnerInforBean> initHolder(ViewGroup parent, int viewType) {
                MatchGoodsListHolder holder = new MatchGoodsListHolder(getContext(), parent);
                holder.setId(id);
                holder.setType(type==1?0:1);
                holder.setClickButtonListener(new MatchGoodsListHolder.OnClickButtonListener() {
                    @Override
                    public void onClickLeft(String btName, final int position, final GoodsOwnerInforBean data) {
                        onClickButton(position,btName,data);

                    }

                    @Override
                    public void onClickRight(String btName, int position, GoodsOwnerInforBean data) {
                        onClickButton(position,btName,data);
                    }
                });
                return holder;
            }
        };
    }

    private void onClickButton(final int position, String btName, final GoodsOwnerInforBean data) {
        if ("联系货主".equals(btName)){
            ChactHelper.getHelper().startPriver(getContext(), data.userId, data.userName);

        }else if ("我要接单".equals(btName)){
            ArouterUtils.getInstance()
                    .builder(ArouterParamsMatch.activity_match_grap_single)
                    .withString(Param.TITLE, data.userName)
                    .withString(Param.CAR_TYPE, data.carType)
                    .withString(Param.TRAN, data.id)
                    .navigation(getContext());
        }else if ("删除发布".equals(btName)){
            CenterAlertBuilder builder = Utils.creatDialog(getContext(), "确认删除明珠城配？", "发布一旦删除，无法恢复", "返回记录", "确定删除");
            builder.setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                @Override
                public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                    dialog.dismiss();
                    deletedMatch(data.id, position);
                }
            })
                    .creatDialog(new CenterAlertDialog(getContext()))
                    .show();
        }else if ("接单信息".equals(btName)){
            queryGrapDetail(data);
        }
    }


    /**
     * 接单明细
     *
     * @param inforBean
     */
    private void queryGrapDetail(final GoodsOwnerInforBean inforBean) {
        HttpMatchFactory
                .queryGraoDetail(inforBean.id, inforBean.status, 1)
                .subscribe(new NetObserver<PageBean<MatchGrapSingleDetailBean>>(this) {
                    @Override
                    public void doOnSuccess(PageBean<MatchGrapSingleDetailBean> data) {

                        if (BaseUtils.isCollectionsEmpty(data.getList())) {
                            ToastUtils.getInstance().show("暂无接单记录");
                        } else {
                            singleDetail.clear();
                            singleDetail.addAll(data.getList());
                            dialog.setTitle("接单信息");
                            dialog.setDescribe((inforBean.status == 0 || inforBean.status == 1) ? "请尽快确认并下单，超时将自动取消该接单信息" : "");
                            listAdapter.notifyDataSetChanged();
                            dialog.show(getChildFragmentManager(), "");
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


    /**
     * 切换数据
     *
     * @param time
     * @param carType
     */
    @Override
    public void onSwithFiltrate(String time, String carType) {
        params.deliveryDateType=time;
        params.carType=carType;
        queryData(true,true);
    }
}


