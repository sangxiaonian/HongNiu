package com.hongniu.freight.presenter;

import com.fy.androidlibrary.net.listener.TaskControl;

import com.fy.companylibrary.net.NetObserver;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.control.OrderDetailControl;
import com.hongniu.freight.entity.AppInsuranceInfo;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.mode.OrderDetailMode;

/**
 * 作者：  on 2020/2/8.
 */
public class OrderDetailPresenter implements OrderDetailControl.IOrderDetailPresenter {
    OrderDetailControl.IOrderDetailView view;
    OrderDetailControl.IOrderDetailMode mode;

    public OrderDetailPresenter(OrderDetailControl.IOrderDetailView view) {
        this.view = view;
        mode = new OrderDetailMode();
    }

    /**
     * 初始化所有的数据
     *
     * @param infoBean
     * @param roler
     */
    @Override
    public void initInfo(OrderInfoBean infoBean, RoleOrder roler) {
        mode.saveInfo(infoBean, roler);
        view.showOrderState(mode.getStatusDes(), String.format("订单编号\t %s", infoBean.getOrderNum()));
        view.showOrderAddressInfo(infoBean);
        view.initDriverInfo(infoBean, mode.isShowDriverInfo());
        view.showOrderDetail(infoBean, mode.isShowCargoPrice(), mode.isShowRealePrice());
        view.showShipperInfo(infoBean, mode.isShowCarInfo());
        view.showCarInfo(infoBean, mode.isShowCarInfo());
        view.showButton(roler, mode.getButtonMsg(), infoBean, mode.getStatus());
    }

    /**
     * 查询订单详情
     *
     * @param listener
     */
    @Override
    public void queryDetail(TaskControl.OnTaskListener listener) {
        mode.queryOrderDetail()
                .subscribe(new NetObserver<OrderInfoBean>(listener) {
                    @Override
                    public void doOnSuccess(OrderInfoBean infoBean) {
                        super.doOnSuccess(infoBean);
                        initInfo(infoBean, mode.getRole());

                    }
                })
        ;
    }

    /**
     * 联系发货人
     */
    @Override
    public void contactStart() {
        view.statCall(mode.getOrderInfo().getShipperMobile());
    }

    /**
     * 联系收货人
     */
    @Override
    public void contactEnd() {
        view.statCall(mode.getOrderInfo().getReceiverMobile());
    }

    /**
     * 联系承运人
     */
    @Override
    public void contactCarrier() {
        view.startChat(mode.getOrderInfo().getOwnerRongId(), mode.getOrderInfo().getOwnerName());
    }

    /**
     * 联系司机
     */
    @Override
    public void contactDriver() {
        view.startChat(mode.getOrderInfo().getDriverRongId(), mode.getOrderInfo().getDriverName());
    }

    /**
     * 联系托运人
     */
    @Override
    public void contactShipper() {
        view.startChat(mode.getOrderInfo().getUserRongId(), mode.getOrderInfo().getUserName());
    }

    /**
     * 查看保单
     */
    @Override
    public void checkInsurance() {
        AppInsuranceInfo insurance = mode.getInsurance();
        if (insurance != null) {
            view.checkInsurance(insurance);

        } else {
            view.showError("保单暂未生成,请稍后再试");
        }
    }


}
