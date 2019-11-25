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
        mode = new MatchOrderDetaMode();
    }

    @Override
    public void saveInfor(final int type, final MatchOrderInfoBean infoBean, TaskControl.OnTaskListener listener) {
        mode.saveInfor(type);
        mode.saveDetailInfo(infoBean);
        initInfor();
        queryDetailInfo(listener);
    }

    @Override
    public void queryDetailInfo(TaskControl.OnTaskListener listener) {
        mode.queryInfo()
                .subscribe(new NetObserver<MatchOrderInfoBean>(listener) {
                    @Override
                    public void doOnSuccess(MatchOrderInfoBean data) {
                        mode.saveDetailInfo(data);
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
        int type = mode.getType();//0 货主 1司机
        MatchOrderInfoBean info = mode.getInfo();
        int status = info.getStatus();


        //        订单状态 1:待付款 2:待接单 3:已接单 4:已送达 5:已完成 6:已取消
        if (status == 2) {
            // "我要接单";
            view.showReceiveOrder(info.getId());
        } else if (status == 3) {
            if (type==0){
                //联系司机
                view.call(info.getDriverMobile());
            }else {
                //确认送达
                view.jumpToEntryArrive(info.getId());
            }
        } else if (status == 4 ) {
            //确认收货
            //已经送达，已完成 司机不能操作
            if (type==0 ){
                view.entryReceive(info.getId());
            }
        }else if ((status==6||status==7)&&mode.isShowButton()){
            //评价司机
            if (type==0){

                view.appraiseDriver(mode.getInfo().getId(),"评价司机", info.getDriverName(), info.getDriverMobile());
            }else {
                view.appraiseDriver(mode.getInfo().getId(),"评价发货人", info.getShipperName(), info.getShipperMobile());
            }

        }


    }

    /**
     * 评价司机
     *  @param rating
     * @param remark
     * @param listener
     */
    @Override
    public void appraiseDrive(int rating, String remark, TaskControl.OnTaskListener listener) {
        mode.appraiseDrive(rating,remark)
            .subscribe(new NetObserver<Object>(listener) {
                @Override
                public void doOnSuccess(Object data) {
                    view.showSuccess("评价成功");
                }
            })
        ;
    }

    /**
     * 跳转到发货人导航
     */
    @Override
    public void startLoad() {
        if (mode.getShowRoute()) {

            view.showRoute(null,mode.getStartPoi());
        }else {
            view.carLoad(null,mode.getStartPoi(), mode.getGuideCarInfo());
        }
    }

    /**
     * 收货人导航
     */
    @Override
    public void endLoad() {
        if (mode.getShowRoute()) {
            view.showRoute(null,mode.getEndPoi());
        }else {
            view.carLoad(null,mode.getEndPoi(),mode.getGuideCarInfo());
        }
    }

    private void initInfor() {
        view.showTitle(mode.getTitle());
        view.showOrderState(mode.getOrderState());
        view.showPickupTime(mode.getPickupTime());
        view.showCarInfo(mode.getCarInfo());
        view.showArriveTime(mode.getArriveTime());
        view.showPlaceInfo(mode.getInfo(), mode.isShowContact());
        view.showArriveVoucher(mode.getArriveVoucherImages(), mode.isShowArriveVoucher(), mode.getInfo().getDeliveryMark());
        view.showEstimate(mode.getEstimateDriver(), mode.getEstimateContentDriver(), mode.isShowEstimateDriver());
        view.showOwnerEstimate(mode.getEstimateOwner(), mode.getEstimateContentOwner(), mode.isShowEstimateOwner());
        view.showPrice(mode.getPrice(), mode.getPriceDetail());
        view.showButton(mode.getButtonInfo(), mode.isShowButton(), mode.isShowBtCall());
        view.guideInfo(mode.getShowRoute()?"查看路线":"货车导航");
    }
}
