package com.hongniu.moduleorder.present;

import android.content.Context;
import android.text.TextUtils;

import com.amap.api.services.core.PoiItem;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.entity.OrderCreatParamBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.UpImgData;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.baselibrary.widget.order.CommonOrderUtils;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderCreatControl;
import com.hongniu.moduleorder.entity.OrderCarNumbean;
import com.hongniu.moduleorder.entity.OrderDriverPhoneBean;
import com.hongniu.moduleorder.entity.OrderInsuranceParam;
import com.hongniu.moduleorder.mode.OrderCreataMode;
import com.luck.picture.lib.entity.LocalMedia;
import com.fy.androidlibrary.net.listener.TaskControl;
import com.hongniu.baselibrary.entity.CommonBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 作者： ${PING} on 2019/5/23.
 */
public class OrderCreataPresenter implements OrderCreatControl.IOrderCreataPresenter {
    OrderCreatControl.IOrderCreataView view;
    OrderCreatControl.IOrderCreataMode mode;
    private Disposable carDisposable;

    public OrderCreataPresenter(OrderCreatControl.IOrderCreataView view) {
        this.view = view;
        mode = new OrderCreataMode();
    }

    /**
     * 查询车辆信息
     *
     * @param carNum
     */
    @Override
    public void queryCarInfor(String carNum) {
        if (carDisposable != null) {
            carDisposable.dispose();
        }
        if (TextUtils.isEmpty(carNum)) {
            return;
        }
        mode.queryCarInfor(carNum).subscribe(new NetObserver<List<OrderCarNumbean>>(null) {
            @Override
            public void doOnSuccess(List<OrderCarNumbean> data) {
                view.showCarPop(data);
            }

            @Override
            public void onSubscribe(Disposable d) {
                super.onSubscribe(d);
                carDisposable = d;
            }
        });
    }

    /**
     * 根据司机名字获取司机手机号
     *
     * @param driverName 司机名称
     */
    @Override
    public void queryDriverInfor(String driverName) {
        if (carDisposable != null) {
            carDisposable.dispose();
        }
        if (TextUtils.isEmpty(driverName)) {
            return;
        }
        mode.queryDriverInfor(driverName)
                .subscribe(new NetObserver<List<OrderDriverPhoneBean>>(null) {
                    @Override
                    public void doOnSuccess(List<OrderDriverPhoneBean> data) {
                        view.showDriverPop(data);
                    }
                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        carDisposable = d;
                    }


                });

    }
    /**
     * 查询收货人
     *
     * @param textCenter
     */
    @Override
    public void queryConsighee(String textCenter) {
        if (carDisposable != null) {
            carDisposable.dispose();
        }
        if (TextUtils.isEmpty(textCenter)) {
            return;
        }
        mode.queryDriverInfor(textCenter)
                .subscribe(new NetObserver<List<OrderDriverPhoneBean>>(null) {
                    @Override
                    public void doOnSuccess(List<OrderDriverPhoneBean> data) {
                        view.showConsigneePop(data);
                    }
                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        carDisposable = d;
                    }


                });
    }

    /**
     * 如果是牛人保界面，储存保险信息
     *
     * @param insuranceParam
     */
    @Override
    public void saveInsuranceInfo(OrderInsuranceParam insuranceParam) {
        if (insuranceParam!=null) {
            mode.saveInsuranceInfo(insuranceParam);
            view.showCargoName(insuranceParam.getCargoName(),insuranceParam.getPrice());
        }
    }

    /**
     * 填写完数据之后点击提交按钮
     * @param result
     * @param listener
     */
    @Override
    public void submit(List<String> result, TaskControl.OnTaskListener listener) {

        OrderCreatParamBean infor = mode.getInfor();
        view.getValue(infor);
        mode.submit(result)
            .subscribe(new NetObserver<OrderDetailBean>(listener) {
                @Override
                public void doOnSuccess(OrderDetailBean data) {
                    view.finishSuccess(data,mode.getType(),mode.getInsuranceInfo());
                }
            })
        ;
    }

    /**
     * 修改订单的时候，根据传入的订单ID查询相应的订单数据
     *
     * @param orderID
     * @param listener
     */
    @Override
    public void queryOrderInfor(String orderID, TaskControl.OnTaskListener listener) {
        mode.queryOrderInfor(orderID)
                .subscribe(new NetObserver<OrderDetailBean>(listener) {
                    @Override
                    public void doOnSuccess(OrderDetailBean data) {
                        mode.saveOrderInfor(data);
                        view.showInfor(mode.getInfor());
                        if (data != null && !CollectionUtils.isEmpty(data.getGoodsImages())) {
                            List<LocalMedia> imageInfors = new ArrayList<>();
                            for (UpImgData imagesBean : data.getGoodsImages()) {
                                LocalMedia media = new LocalMedia();
                                media.setPath(imagesBean.getAbsolutePath());
                                media.setRelativePath(imagesBean.getPath());
                                imageInfors.add(media);
                            }
                            view.showImageInfors(imageInfors);
                        }
                        if (data != null) {
                            view.changeEnableByOrder(data.getOrderState(), CommonOrderUtils.isPayOnLine(data.getPayWay()), data.isInsurance());
                        }
                    }
                });
        ;
    }

    /**
     * 更改当前页面类型
     *
     * @param type    0 创建订单 1修改订单 2车货匹配订单 3完善信息
     * @param context
     */
    @Override
    public void changeType(int type, Context context) {
        mode.saveType(type);
        if (type == 0 || type == 2) {
            view.changeTitle(context.getString(R.string.order_create_order), "确定下单");
        } else if (type == 1) {
            view.changeTitle(context.getString(R.string.order_change), context.getString(R.string.order_entry_change));
        }else if (type == 3) {
            view.changeTitle("完善信息", "确定下单");
        }
    }

    /**
     * 车货匹配时候，传入的数据
     *
     * @param event
     */
    @Override
    public void saveInfor(OrderCreatParamBean event) {
        mode.saveInfor(event);
        view.showInfor(mode.getInfor());
    }

    /**
     * 更改起始位置
     *
     * @param t
     */
    @Override
    public void changeStartPlaceInfor(PoiItem t) {
        OrderCreatParamBean infor = mode.getInfor();
        if (t!=null) {
            String title = Utils.dealPioPlace( t);
            infor.setStartLatitude(t.getLatLonPoint().getLatitude() + "");
            infor.setStartLongitude(t.getLatLonPoint().getLongitude() + "");
            infor.setStartPlaceInfo(title);
        }
    }

    /**
     * 更改设置目的地
     *
     * @param t
     */
    @Override
    public void changeEndPlaceInfor(PoiItem t) {
        OrderCreatParamBean infor = mode.getInfor();
        if (t!=null) {
            String title = Utils.dealPioPlace( t);
            infor.setDestinationLatitude( t.getLatLonPoint().getLatitude() + "");
            infor.setDestinationLongitude( t.getLatLonPoint().getLongitude() + "");
            infor.setDestinationInfo(title);
        }
    }

    /**
     * 点击返回按钮
     */
    @Override
    public void onBacePress() {
        String s = mode.getType() == 1 ? "确认要放弃修改吗" : "确认要放弃下单吗？";
        view.showFinishAleart(s);

    }


}
