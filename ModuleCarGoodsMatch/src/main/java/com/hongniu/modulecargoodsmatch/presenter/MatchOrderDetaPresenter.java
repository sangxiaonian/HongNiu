package com.hongniu.modulecargoodsmatch.presenter;

import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.modulecargoodsmatch.control.MatchOrderDataControl;
import com.hongniu.modulecargoodsmatch.entity.MatchOrderInfoBean;
import com.hongniu.modulecargoodsmatch.mode.MatchOrderDetaMode;
import com.sang.common.net.listener.TaskControl;

/**
 * 作者：  on 2019/11/2.
 */
public class MatchOrderDetaPresenter implements MatchOrderDataControl.IMatchOrderDataPresenter {
    MatchOrderDataControl.IMatchOrderDataView view;
    MatchOrderDataControl.IMatchOrderDataMode mode;

    public MatchOrderDetaPresenter(MatchOrderDataControl.IMatchOrderDataView view) {
        this.view = view;
        mode=new MatchOrderDetaMode();
    }

    @Override
    public void saveInfor(int type, MatchOrderInfoBean infoBean, TaskControl.OnTaskListener listener) {
        mode.saveInfor(type, infoBean);
        initInfor();
        mode.queryInfo()
                .subscribe(new NetObserver<MatchOrderInfoBean>(listener) {
                    @Override
                    public void doOnSuccess(MatchOrderInfoBean data) {
                      initInfor();
                    }
                });
    }

    /**
     * 联系发货人
     */
    @Override
    public void contactStart() {
        view.call(mode.getInfo().getShipperMobile());
    }

    /**
     * 联系收货人
     */
    @Override
    public void contactEnd() {
        view.call(mode.getInfo().getReceiverMobile());

    }

    /**
     * 点击底部按钮
     */
    @Override
    public void clickBt() {

    }

    private void initInfor(){
        view.showTitle(mode.getTitle());
        view.showOrderState(mode.getOrderState());
        view.showPickupTime(mode.getPickupTime());
        view.showCarInfo(mode.getCarInfo());
        view.showArriveTime(mode.getArriveTime());
        view.showPlaceInfo(mode.getInfo(),mode.isShowContact());
        view.showArriveVoucher(mode.getArriveVoucherImages(),mode.isShowArriveVoucher());
        view.showEstimate(mode.getEstimate(),mode.getEstimateContent(),mode.isShowEstimate());
        view.showPrice(mode.getPrice(),mode.getPriceDetail() );
        view.showButton(mode.getButtonInfo(),mode.isShowButton() ,mode.isShowBtCall());
    }
}
