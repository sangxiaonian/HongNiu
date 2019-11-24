package com.hongniu.modulecargoodsmatch.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterParamsMatch;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.base.RefrushFragmet;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.PayParam;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.baselibrary.widget.pay.DialogPayUtils;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.MatchOrderInfoBean;
import com.hongniu.modulecargoodsmatch.net.HttpMatchFactory;
import com.hongniu.modulecargoodsmatch.ui.holder.MatchOrderInfoHolder;
import com.hongniu.modulecargoodsmatch.utils.MatchOrderListHelper;
import com.hongniu.modulecargoodsmatch.widget.CancelOrderDialog;
import com.hongniu.modulecargoodsmatch.widget.DriverDialog;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.CommonUtils;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.thirdlibrary.pay.entiy.PayBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @data 2019/10/27
 * @Author PING
 * @Description 货主找车
 */
@Route(path = ArouterParamsMatch.fragment_match_driver_order_receiving)
public class MatchDriverOrderRecevingFragment extends RefrushFragmet<MatchOrderInfoBean> implements MatchOrderInfoHolder.MatchOrderItemClickListener, DialogPayUtils.PayListener {
    private int type;//角色类型   0 2 司机 1货主

    protected PayParam payParam;
    DialogPayUtils payUtils;


    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        if (args != null) {
            type = args.getInt(Param.TYPE);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_match_driver_order_receiving, null, false);
    }

    @Override
    protected void initData() {
        super.initData();
        payUtils = new DialogPayUtils(getContext());
        payParam = new PayParam();
        payUtils.setPayParam(payParam);
        payUtils.setPayListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        queryData(true);
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected Observable<CommonBean<PageBean<MatchOrderInfoBean>>> getListDatas() {
        if (type == 0) {
            return HttpMatchFactory.queryDriverOrder(currentPage);
        } else {
            return HttpMatchFactory.queryMyOrder(currentPage, type);
        }

    }

    @Override
    protected XAdapter<MatchOrderInfoBean> getAdapter(List<MatchOrderInfoBean> datas) {
        return new XAdapter<MatchOrderInfoBean>(getContext(), datas) {
            @Override
            public BaseHolder<MatchOrderInfoBean> initHolder(ViewGroup parent, int viewType) {
                MatchOrderInfoHolder infoHolder = new MatchOrderInfoHolder(getContext(), parent);
                infoHolder.setListener(MatchDriverOrderRecevingFragment.this);
                infoHolder.setType(type);
                return infoHolder;
            }
        };
    }

    @Override
    public void onBtClick(int position, MatchOrderInfoBean infoHolder, int type, String btState) {

        if (MatchOrderListHelper.CANCLE_CAR.equals(btState)) {
            _cancleCar(infoHolder);
        } else if (MatchOrderListHelper.PAY.equals(btState)) {
            _pay(infoHolder);

        } else if (MatchOrderListHelper.CONTACT_DRIVER.equals(btState)) {
            CommonUtils.call(getContext(), infoHolder.getDriverMobile());
        } else if (MatchOrderListHelper.EVALUATE_DRIVER.equals(btState)) {
            //评价司机
            _evaluateDriver(infoHolder);
        } else if (MatchOrderListHelper.EVALUATE_OWNER.equals(btState)) {
            //评价收货人
            _evaluateOwner(infoHolder);
        } else if (MatchOrderListHelper.RECEIVE_ORDER.equals(btState)) {
            _receiveOrder(infoHolder);
        } else if (MatchOrderListHelper.ENTRY_ARRIVE.equals(btState)) {
            //确认送达
            ArouterUtils.getInstance().builder(ArouterParamsMatch.activity_match_entry_arrive)
                    .withString(Param.TRAN, infoHolder.getId())
                    .navigation(getContext());
        } else if (MatchOrderListHelper.ENTRY_RECEIVE.equals(btState)) {
            //确认收货

            _entryReceive(infoHolder);
        }


    }

    /**
     * 确认收货
     *
     * @param infoHolder
     */
    private void _entryReceive(final MatchOrderInfoBean infoHolder) {
        CenterAlertBuilder builder = Utils.creatDialog(getContext(), "确认收货", "确认收货后，运费将立即转入司机账户", "我再想想", "确认收货");
        builder
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog cdialog) {
                        cdialog.dismiss();
                        HttpMatchFactory.matchEntryReceive(infoHolder.getId())
                                .subscribe(new NetObserver<Object>(null) {
                                    @Override
                                    public void doOnSuccess(Object data) {
                                        queryData(true);
                                    }
                                });

                    }
                })
                .creatDialog(new CenterAlertDialog(getContext()))
                .show();
    }

    DriverDialog dialog;

    /**
     * @data 2019/11/3
     * @Author PING
     * @Description 评价司机
     */
    private void _evaluateDriver(final MatchOrderInfoBean infoHolder) {
        if (dialog == null) {
            dialog = new DriverDialog(getContext());
        }
        dialog.setTitle("评价司机");
        dialog.setSubTitle(String.format("%s(%s)", infoHolder.getDriverName(), infoHolder.getDriverMobile()));
        dialog.setEntryClickListener(new DriverDialog.EntryClickListener() {
            @Override
            public void OnEntryClick(int rating, String trim) {
                HttpMatchFactory
                        .appraiseDrive(rating, trim, infoHolder.getId())
                        .subscribe(new NetObserver<Object>(MatchDriverOrderRecevingFragment.this) {
                            @Override
                            public void doOnSuccess(Object data) {
                                queryData(true);
                            }
                        })
                ;
            }
        });
        dialog.builder().show(null);
    }

    /**
     * @data 2019/11/3
     * @Author PING
     * @Description 评价发货人
     */
    private void _evaluateOwner(final MatchOrderInfoBean infoHolder) {
        if (dialog == null) {
            dialog = new DriverDialog(getContext());
        }
        dialog.setTitle("评价发货人");
        dialog.setSubTitle(String.format("%s(%s)", infoHolder.getShipperName(), infoHolder.getShipperMobile()));
        dialog.setEntryClickListener(new DriverDialog.EntryClickListener() {
            @Override
            public void OnEntryClick(int rating, String trim) {
                HttpMatchFactory
                        .appraiseDrive(rating, trim, infoHolder.getId())
                        .subscribe(new NetObserver<Object>(MatchDriverOrderRecevingFragment.this) {
                            @Override
                            public void doOnSuccess(Object data) {
                                queryData(true);
                            }
                        })
                ;
            }
        });
        dialog.builder().show(null);
    }

    /**
     * 支付
     *
     * @param infoHolder
     */
    private void _pay(MatchOrderInfoBean infoHolder) {
        payUtils.setTitle("确认下单并支付订单");
        payUtils.setListener(this);
        payUtils.setSubtitle(String.format("支付总额：%s", infoHolder.getEstimateFare()));
        payUtils.setSubtitleDes(String.format("运费明细  起步价%s元*%s公里", infoHolder.getStartPrice(), infoHolder.getDistance()));
        payUtils.setShowCompany(false);
        payUtils.setPayCount(infoHolder.getEstimateFare());

        payParam.setPaybusiness(5);
        payParam.setCarGoodsOrderId(infoHolder.getId());
        payUtils.show(getChildFragmentManager());
    }

    /**
     * @data 2019/11/2
     * @Author PING
     * @Description 我要接单
     */
    private void _receiveOrder(final MatchOrderInfoBean infoHolder) {
        CenterAlertBuilder builder = Utils.creatDialog(getContext(), "确定接单？", "接单后，即可与货主取得联系", "放弃接单", "确定接单");
        builder
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog cdialog) {
                        cdialog.dismiss();
                        HttpMatchFactory
                                .receiverOrder(infoHolder.getId())
                                .subscribe(new NetObserver<Object>(MatchDriverOrderRecevingFragment.this) {
                                    @Override
                                    public void doOnSuccess(Object data) {
                                        queryData(true, true);
                                    }

                                });

                    }
                })
                .creatDialog(new CenterAlertDialog(getContext()))
                .show();
    }

    /**
     * 取消订单
     *
     * @param infoHolder
     */
    private void _cancleCar(final MatchOrderInfoBean infoHolder) {

        CancelOrderDialog orderDialog = new CancelOrderDialog(getContext());
        orderDialog.setEntryClickListener(new CancelOrderDialog.CancelEntryClickListener() {
            @Override
            public void OnCancelOrderClick(boolean isDriver, String trim) {
                HttpMatchFactory
                        .cancelCar(infoHolder.getId(), isDriver ? 2 : 1, trim)
                        .subscribe(new NetObserver<Object>(MatchDriverOrderRecevingFragment.this) {
                            @Override
                            public void doOnSuccess(Object data) {
                                queryData(true, true);
                            }

                        });
            }
        });
        orderDialog.show();

//        CenterAlertBuilder builder = Utils.creatDialog(getContext(), "确认要取消订单？", "取消后，您可在“我的找车”里查看记录", "我再想想", "取消找车");
//        builder
//                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
//                    @Override
//                    public void onRightClick(View view, DialogControl.ICenterDialog cdialog) {
//
//
//                    }
//                })
//                .creatDialog(new CenterAlertDialog(getContext()))
//                .show();

    }

    @Override
    public void onItemClick(int position, MatchOrderInfoBean data) {
        if (data.getStatus() != 1) {//代付款状态不能进入详情页面
            ArouterUtils.getInstance()
                    .builder(ArouterParamsMatch.activity_match_order_detail)
                    .withInt(Param.TYPE, type == 1 ? 0 : 1)
                    .withParcelable(Param.TRAN, data)
                    .navigation(getContext());
        }
    }

    @Override
    public void canclePay(DialogControl.IDialog dialog) {

    }

    @Override
    public void jump2Pay(PayBean data, int payType, PayParam payParam) {
        ArouterUtils.getInstance()
                .builder(ArouterParamOrder.activity_waite_pay)
                .withInt("payType", payParam.getPayType())
                .withParcelable("payInfor", data)
                .withBoolean("ISDEUBG", Param.isDebug)
                .withString("ORDERID", payParam.getCarGoodsOrderId())
                .withBoolean("havePolicy", false)
                .withInt("queryType", 5)
                .navigation((Activity) getContext(), 1);
    }
}
