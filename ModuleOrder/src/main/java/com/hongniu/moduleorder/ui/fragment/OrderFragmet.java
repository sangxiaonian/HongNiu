package com.hongniu.moduleorder.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.model.AMapCarInfo;
import com.google.gson.Gson;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.base.RefrushFragmet;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.OrderCreatBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.PayOrderInfor;
import com.hongniu.baselibrary.entity.RoleTypeBean;
import com.hongniu.baselibrary.entity.UpImgData;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.baselibrary.utils.PermissionUtils;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.baselibrary.widget.order.OrderDetailItem;
import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.entity.OrderMainQueryBean;
import com.hongniu.moduleorder.entity.QueryReceiveBean;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.hongniu.moduleorder.ui.OrderScanReceiptActivity;
import com.hongniu.moduleorder.utils.LoactionCollectionUtils;
import com.hongniu.moduleorder.utils.OrderUtils;
import com.sang.common.event.BusFactory;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.JLog;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.thirdlibrary.map.utils.MapConverUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.RoleState.CARGO_OWNER;
import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.RoleState.DRIVER;

/**
 * 订单列表Fragment
 */
public class OrderFragmet extends RefrushFragmet<OrderDetailBean> implements OrderDetailItemControl.OnOrderDetailBtClickListener {


    protected OrderDetailItemControl.RoleState roleState;//角色
    protected OrderMainQueryBean queryBean = new OrderMainQueryBean();
    protected LatLng latLng = new LatLng(0, 0);

    public OrderFragmet() {
    }


    @Override
    protected boolean getUseEventBus() {
        return true;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLocationChange(final Event.UpLoaction event) {
        if (event != null) {
            latLng = new LatLng(event.latitude, event.longitude);
        }
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);

        roleState = (OrderDetailItemControl.RoleState) args.get(Param.TRAN);
        if (roleState == null) {
            roleState = CARGO_OWNER;
        }
        switch (roleState) {
            case CARGO_OWNER:
                queryBean.setUserType(3);
                break;
            case CAR_OWNER:
                queryBean.setUserType(1);
                break;
            case DRIVER:
                queryBean.setUserType(2);
                break;
        }


    }

    @Override
    public XAdapter<OrderDetailBean> getAdapter(List<OrderDetailBean> datas) {
        return new XAdapter<OrderDetailBean>(getContext(), datas) {
            @Override
            public BaseHolder<OrderDetailBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<OrderDetailBean>(getContext(), parent, R.layout.order_main_item) {
                    @Override
                    public void initView(View itemView, int position, final OrderDetailBean data) {
                        super.initView(itemView, position, data);
                        OrderDetailItem item = itemView.findViewById(R.id.order_detail);//身份角色
                        item.setIdentity(roleState);
                        item.setInfor(data);
                        item.setOnButtonClickListener(OrderFragmet.this);
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                    }
                };
            }
        };
    }

    @Override
    protected Observable<CommonBean<PageBean<OrderDetailBean>>> getListDatas() {
        queryBean.setPageNum(currentPage);
        return HttpOrderFactory.queryOrder(queryBean);
    }


    /**
     * "取消订单";
     *
     * @param orderBean
     */
    @Override
    public void onOrderCancle(final OrderDetailBean orderBean) {
        creatDialog("确认要取消订单？", "订单一旦去取消，无法恢复", "返回订单", "取消订单")
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        dialog.dismiss();
                        HttpOrderFactory.cancleOrder(orderBean.getId())
                                .subscribe(new NetObserver<OrderDetailBean>(taskListener) {
                                    @Override
                                    public void doOnSuccess(OrderDetailBean data) {
                                    }

                                    @Override
                                    public void onComplete() {
                                        super.onComplete();
                                        BusFactory.getBus().post(new OrderEvent.OrderUpdate(roleState));
                                    }
                                });
                    }
                }).creatDialog(new CenterAlertDialog(getContext()))
                .show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final OrderEvent.OrderUpdate event) {
        if (event != null) {
            if (rv != null && roleState != null && !roleState.equals(event.roleState)) {
                rv.smoothScrollToPosition(0);
            }
            queryData(true, true);
        }
    }


    /**
     * 购买保险
     *
     * @param orderBean
     */
    @Override
    public void onOrderBuyInsurance(OrderDetailBean orderBean) {
        ArouterUtils.getInstance()
                .builder(ArouterParamOrder.activity_order_pay)
                .withBoolean(Param.TRAN, true)
                .navigation(getContext());
        PayOrderInfor payOrder = new PayOrderInfor();
        payOrder.insurance = true;
        payOrder.orderID = orderBean.getId();
        payOrder.orderNum = orderBean.getOrderNum();
        BusFactory.getBus().postSticky(payOrder);
//        changeOrderState(orderBean, 2, true, true);
    }

    /**
     * ORDER_PAY 继续付款
     *
     * @param orderBean
     */
    @Override
    public void onOrderPay(OrderDetailBean orderBean) {
        if (orderBean.getMoney() != null) {
            try {
                PayOrderInfor payOrder = new PayOrderInfor();
                payOrder.insurance = false;
                payOrder.money = Float.parseFloat(orderBean.getMoney());
                payOrder.orderID = orderBean.getId();
                payOrder.orderNum = orderBean.getOrderNum();
                payOrder.receiptMobile = orderBean.getReceiptMobile();
                payOrder.receiptName = orderBean.getReceiptName();
                BusFactory.getBus().postSticky(payOrder);
                ArouterUtils.getInstance()
                        .builder(ArouterParamOrder.activity_order_pay)
                        .navigation(getContext());

//                changeOrderState(orderBean, 2, false, true);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }


        }

    }


    /**
     * ORDER_CHECK_INSURANCE 查看保单
     *
     * @param orderBean
     */
    @Override
    public void onCheckInsruance(OrderDetailBean orderBean) {
        if (orderBean.getPolicyInfo() != null) {
            OrderCreatBean orderCreatBean = new Gson().fromJson(orderBean.getPolicyInfo(), OrderCreatBean.class);
            OrderUtils.scanPDf(getActivity(), orderCreatBean.getDownloadUrl());
        } else {
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("暂无保单信息");

        }
    }

    /**
     * ORDER_CHECK_PATH 查看轨迹
     *
     * @param orderBean
     */
    @Override
    public void onCheckPath(final OrderDetailBean orderBean) {
        ArouterUtils.getInstance().builder(ArouterParamOrder.activity_map_check_path).withBoolean(Param.TRAN, true).navigation(getActivity());
        OrderEvent.CheckPathEvent checkPathEvent = new OrderEvent.CheckPathEvent(orderBean);
        checkPathEvent.setBean(orderBean);
        checkPathEvent.setRoaleState(roleState);
        BusFactory.getBus().postSticky(checkPathEvent);

    }

    @Override
    public void onStart() {
        super.onStart();
        queryData(true);
    }

    /**
     * ORDER_ENTRY_ORDER 确认收货
     *
     * @param orderBean
     */
    @Override
    public void onEntryOrder(final OrderDetailBean orderBean) {
        creatDialog("确认已收到货物？", "收货请务必检查货物完好无损", "返回订单", "确定收货")
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        dialog.dismiss();
                        HttpOrderFactory.entryReceiveCargo(orderBean.getId())
                                .subscribe(new NetObserver<OrderDetailBean>(taskListener) {
                                    @Override
                                    public void doOnSuccess(OrderDetailBean data) {
                                    }

                                    @Override
                                    public void onComplete() {
                                        super.onComplete();
                                        BusFactory.getBus().post(new OrderEvent.OrderUpdate(roleState));

                                    }
                                });
                    }
                }).creatDialog(new CenterAlertDialog(getContext()))
                .show();
    }


    /**
     * ORDER_START_CAR           ="开始发车";
     *
     * @param orderBean
     */
    @Override
    public void onStartCar(final OrderDetailBean orderBean) {
        creatDialog("确认要开始发车？", "车辆行驶中请记得安全驾车", "返回订单", "开始发车")
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        latLng = new LatLng(0, 0);
                        if (roleState == DRIVER) {
                            Observable.just(1)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .map(new Function<Integer, Integer>() {
                                        @Override
                                        public Integer apply(Integer integer) throws Exception {
                                            OrderEvent.UpLoactionEvent upLoactionEvent = new OrderEvent.UpLoactionEvent();
                                            upLoactionEvent.start = true;
                                            BusFactory.getBus().post(upLoactionEvent);
                                            return integer;
                                        }
                                    })
                                    .observeOn(Schedulers.io())
                                    .map(new Function<Integer, Double>() {
                                        @Override
                                        public Double apply(Integer integer) throws Exception {
                                            JLog.i(Thread.currentThread().getName());
                                            while (latLng == null || latLng.latitude == 0) {
                                                SystemClock.sleep(1000);
                                            }
                                            double v = MapConverUtils.caculeDis(latLng.latitude
                                                    , latLng.longitude
                                                    , orderBean.getStartLatitude(), orderBean.getStartLongitude());
                                            return v;
                                        }
                                    })
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .filter(new Predicate<Double>() {
                                        @Override
                                        public boolean test(Double aDouble) throws Exception {
                                            JLog.i(Thread.currentThread().getName());
                                            OrderEvent.UpLoactionEvent upLoactionEvent = new OrderEvent.UpLoactionEvent();
                                            upLoactionEvent.start = false;
                                            BusFactory.getBus().post(upLoactionEvent);
                                            if (aDouble > Param.ENTRY_MIN) {
                                                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("距离发货地点还有" + ConvertUtils.changeFloat(aDouble / 1000, 1) + "公里，无法开始发车");

                                            }
                                            return aDouble < Param.ENTRY_MIN;
                                        }
                                    })
                                    .flatMap(new Function<Double, ObservableSource<CommonBean<String>>>() {
                                        @Override
                                        public ObservableSource<CommonBean<String>> apply(Double aDouble) throws Exception {

                                            return HttpOrderFactory.driverStart(orderBean.getId());

                                        }
                                    })
                                    .subscribe(new NetObserver<String>(taskListener) {


                                        @Override
                                        public void doOnSuccess(String data) {
                                            RoleTypeBean bean = new RoleTypeBean();
                                            bean.setRoleId(2);
                                            bean.setCarId(orderBean.getCarId());
                                            bean.setOrderId(orderBean.getId());
                                            bean.setStartLatitude(orderBean.getStartLatitude());
                                            bean.setStartLongitude(orderBean.getStartLongitude());
                                            bean.setDestinationLatitude(orderBean.getDestinationLatitude());
                                            bean.setDestinationLongitude(orderBean.getDestinationLongitude());
                                            EventBus.getDefault().post(bean);
                                        }

                                        @Override
                                        public void onComplete() {
                                            super.onComplete();
                                            BusFactory.getBus().post(new OrderEvent.OrderUpdate(roleState));
                                        }
                                    });


                        } else {
                            HttpOrderFactory.driverStart(orderBean.getId())
                                    .subscribe(new NetObserver<String>(taskListener) {
                                        @Override
                                        public void doOnSuccess(String data) {

                                        }

                                        @Override
                                        public void onComplete() {
                                            super.onComplete();
                                            BusFactory.getBus().post(new OrderEvent.OrderUpdate(roleState));
                                        }
                                    });
                        }
                        dialog.dismiss();

                    }
                }).creatDialog(new CenterAlertDialog(getContext()))
                .show();
    }

    /**
     * ORDER_CHECK_ROUT          ="查看路线";
     *
     * @param orderBean
     */
    @Override
    public void onCheckRout(final OrderDetailBean orderBean) {
        PermissionUtils.applyMap(getActivity(), new PermissionUtils.onApplyPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean isAll) {

                //查看路线
                Poi start = new Poi(orderBean.getStartPlaceInfo(), new LatLng(orderBean.getStartLatitude(), orderBean.getStartLongitude()), "");
                Poi end = new Poi(orderBean.getDestinationInfo(), new LatLng(orderBean.getDestinationLatitude(), orderBean.getDestinationLongitude()), "");
                AmapNaviParams amapNaviParams = new AmapNaviParams(start, null, end, AmapNaviType.DRIVER);
                amapNaviParams.setUseInnerVoice(true);
                LoactionCollectionUtils loactionCollectionUtils = new LoactionCollectionUtils();
                loactionCollectionUtils.setOrderInfor(orderBean);
                AmapNaviPage.getInstance().showRouteActivity(getContext().getApplicationContext(),
                        amapNaviParams, loactionCollectionUtils);


            }


            @Override
            public void noPermission(List<String> denied, boolean quick) {

            }
        });

    }

    /**
     * ORDER_CHECK_ROUT          ="货车导航";
     *
     * @param orderBean
     */
    @Override
    public void onTruchGuid(OrderDetailBean orderBean) {
        Poi start = new Poi(orderBean.getStartPlaceInfo(), new LatLng(orderBean.getStartLatitude(), orderBean.getStartLongitude()), "");
        Poi end = new Poi(orderBean.getDestinationInfo(), new LatLng(orderBean.getDestinationLatitude(), orderBean.getDestinationLongitude()), "");

        ArouterUtils.getInstance().builder(ArouterParamLogin.activity_login_truck_infor)
                .withString(Param.TRAN, orderBean.getCarNum())
                .withParcelable("start", start)
                .withParcelable("end", end)
                .navigation(getContext());

    }

    /**
     * ORDER_ENTRY_ARRIVE        ="确认到达";
     *
     * @param orderBean
     */
    @Override
    public void onEntryArrive(final OrderDetailBean orderBean) {
        creatDialog("确认已到达目的地？", "感谢您的辛苦付出", "返回订单", "确定到达")
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        double v = MapConverUtils.caculeDis(latLng.latitude
                                , latLng.longitude
                                , orderBean.getDestinationLatitude(), orderBean.getDestinationLongitude());
                        dialog.dismiss();
                        if (latLng.latitude == 0 || latLng.longitude == 0) {
                            OrderEvent.UpLoactionEvent upLoactionEvent = new OrderEvent.UpLoactionEvent();
                            upLoactionEvent.start = true;
                            BusFactory.getBus().post(upLoactionEvent);
                            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("正在获取当前位置，请稍后再试");
                        } else if (v > Param.ENTRY_MIN) {//距离过大，超过确认订单的最大距离
                            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("距离收货地点还有" + ConvertUtils.changeFloat(v / 1000, 1) + "公里，无法确认到达");
                        } else {
                            HttpOrderFactory.entryArrive(orderBean.getId())
                                    .subscribe(new NetObserver<String>(taskListener) {
                                        @Override
                                        public void doOnSuccess(String data) {
                                            BusFactory.getBus().post(new OrderEvent.UpLoactionEvent());
                                        }

                                        @Override
                                        public void onComplete() {
                                            super.onComplete();
                                            BusFactory.getBus().post(new OrderEvent.OrderUpdate(roleState));
                                        }
                                    });
                        }
                    }
                }).creatDialog(new CenterAlertDialog(getContext()))
                .show();
    }

    /**
     * ORDER_ENTRY_ARRIVE        ="查看回单";
     *
     * @param orderBean
     */
    @Override
    public void onCheckReceipt(OrderDetailBean orderBean) {
        HttpOrderFactory.queryReceiptInfo(orderBean.getId())
                .subscribe(new NetObserver<QueryReceiveBean>(this) {
                    @Override
                    public void doOnSuccess(QueryReceiveBean data) {
                        final List<String> list = new ArrayList<>();
                        List<UpImgData> images = data.getImages();
                        if (!CommonUtils.isEmptyCollection(images)) {
                            for (UpImgData image : images) {
                                list.add(image.getAbsolutePath());
                            }
                        }
                        OrderScanReceiptActivity.launchActivity(getActivity(), 0, 0, list);

                    }


                });

//

    }

    /**
     * ORDER_ENTRY_ARRIVE        ="上传回单";
     *
     * @param orderBean
     */
    @Override
    public void onUpReceipt(OrderDetailBean orderBean) {
        ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_up_receipt)
                .withString(Param.TRAN, orderBean.getId()).navigation(getContext());
    }

    /**
     * ORDER_CHANGE_RECEIPT        ="修改回单";
     *
     * @param orderBean
     */
    @Override
    public void onChangeReceipt(final OrderDetailBean orderBean) {
        HttpOrderFactory.queryReceiptInfo(orderBean.getId())
                .subscribe(new NetObserver<QueryReceiveBean>(this) {
                    @Override
                    public void doOnSuccess(QueryReceiveBean data) {
                        ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_up_receipt)
                                .withString(Param.TRAN, orderBean.getId()).navigation(getContext());
                        BusFactory.getBus().postSticky(new OrderEvent.UpReceiver(data));

                    }


                });
    }

    /**
     * ORDER_CHANGE        ="修改订单";
     *
     * @param orderBean
     */
    @Override
    public void onChangeOrder(OrderDetailBean orderBean) {

        ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_create).navigation(getContext());
        Event.ChangeOrder changeOrder = new Event.ChangeOrder(orderBean.getId());
        changeOrder.orderType=1;
        BusFactory.getBus().postSticky(changeOrder);


    }

    /**
     * ORDER_CHECK_GOODS        ="查看货单";
     *
     * @param orderBean
     */
    @Override
    public void onCheckGoods(OrderDetailBean orderBean) {
        HttpOrderFactory.queryCargoInfo(orderBean.getId())
                .subscribe(new NetObserver<List<UpImgData>>(this) {
                    @Override
                    public void doOnSuccess(List<UpImgData> data) {
                        final List<String> list = new ArrayList<>();
                        if (!CommonUtils.isEmptyCollection(data)) {
                            for (UpImgData image : data) {
                                list.add(image.getAbsolutePath());
                            }
                        }
                        OrderScanReceiptActivity.launchActivity(getActivity(), 2, 0, list);

                    }


                });
    }

    /**
     * 被拒原因
     *
     * @param orderBean
     */
    @Override
    public void onPayRefuse(OrderDetailBean orderBean) {
        new CenterAlertBuilder()
                .setDialogTitle("被拒原因")
                .setDialogContent(orderBean!=null?orderBean.getVerifyFailCause():"")
                .setBtLeft("知道了")
                .hideBtRight()
                .setBtLeftColor(getContext().getResources().getColor(com.hongniu.baselibrary.R.color.color_title_dark))
                .creatDialog(new CenterAlertDialog(getContext()))
                .show();

    }

    protected CenterAlertBuilder creatDialog(String title, String content, String btleft, String
            btRight) {
        return Utils.creatDialog(getContext(), title, content, btleft, btRight);

    }


}
