package com.hongniu.modulecargoodsmatch.control;

import com.amap.api.maps.model.Poi;
import com.amap.api.navi.model.AMapCarInfo;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.modulecargoodsmatch.entity.MatchOrderInfoBean;
import com.fy.androidlibrary.net.listener.TaskControl;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者：  on 2019/11/2.
 */
public class MatchOrderDataControl {

    public interface IMatchOrderDataView {
        /**
         * 显示标题
         * @param title
         */
        void showTitle(String title);

        /**
         * 显示订单状态
         * @param orderState
         */
        void showOrderState(String orderState);

        /**
         * 预约取货时间
         * @param pickupTime
         */
        void showPickupTime(String pickupTime);

        /**
         * 配送车辆
         * @param carInfo
         */
        void showCarInfo(String carInfo);

        /**
         * 送达时间
         * @param arriveTime
         */
        void showArriveTime(String arriveTime);

        /**
         * 显示发货人、收货人信息
         * @param info
         * @param showContact 是否显示联系人信息
         */
        void showPlaceInfo(MatchOrderInfoBean info, boolean showContact);

        /**
         * 送达凭证
         * @param arriveVoucherImages 图片
         * @param showArriveVoucher   是否显示送达凭证 true 显示
         * @param remark
         */
        void showArriveVoucher(List<String> arriveVoucherImages, boolean showArriveVoucher, String remark);

        /**
         * 显示司机评价模块
         * @param estimate        星级
         * @param estimateContent 评价内容
         * @param showEstimate    是否显示
         */
        void showEstimate(int estimate, String estimateContent, boolean showEstimate);

        /**
         * 显示价格
         * @param price
         * @param priceDetail
         */
        void showPrice(String price, String priceDetail);

        /**
         * 拨打电话
         * @param phone
         */
        void call(String phone);

        /**
         * 展示底部按钮
         * @param buttonInfo
         * @param showButton
         * @param showBtCall
         */
        void showButton(String buttonInfo, boolean showButton, boolean showBtCall);

        /**
         * 确认到达
         * @param id
         */
        void jumpToEntryArrive(String id);

        /**
         * 我要接单
         * @param id
         */
        void showReceiveOrder(String id);

        /**
         * 评价司机
         * @param id
         * @param title
         * @param driverName
         * @param driverMobile
         * @param hide
         */
        void appraiseDriver(String id, String title, String driverName, String driverMobile, String hide);

        void showSuccess(String msg);

        /**
         * 开始导航
         * @param startPoi
         * @param endPoi
         * @param guideCarInfo
         */
        void carLoad(Poi startPoi, Poi endPoi, AMapCarInfo guideCarInfo);


        /**
         * 查看路线
         * @param startPoi
         * @param endPoi
         */
        void showRoute(Poi startPoi, Poi endPoi);

        void guideInfo(String showRoute);

        /**
         * 确认收货
         * @param id
         */
        void entryReceive(String id);

        /**
         * 是否显示发货人评价
         * @param estimate
         * @param estimateContent
         * @param showEstimate
         */
        void showOwnerEstimate(int estimate, String estimateContent, boolean showEstimate);
    }
    public interface IMatchOrderDataPresenter {
        void saveInfor(int type, MatchOrderInfoBean infoBean, TaskControl.OnTaskListener listener);


        void queryDetailInfo(TaskControl.OnTaskListener listener);

        /**
         * 联系发货人
         */
        void contactStart();

        /**
         * 联系收货人
         */
        void contactEnd();

        /**
         * 点击底部按钮
         */
        void clickBt();


        /**
         * 评价司机
         * @param rating
         * @param remark
         * @param listener
         */
        void appraiseDrive(int rating, String remark, TaskControl.OnTaskListener listener);

        /**
         * 跳转到发货人导航
         */
        void startLoad();

        /**
         * 收货人导航
         */
        void endLoad();
    }
    public interface IMatchOrderDataMode {

        void saveInfor(int type);
        void saveDetailInfo(MatchOrderInfoBean data);

        /**
         * 查询订单详情
         * @return
         */
        Observable<CommonBean<MatchOrderInfoBean>> queryInfo();

        /**
         * 获取标题
         * @return
         */
        String getTitle();

        /**
         * 获取订单状态
         * @return
         */
        String getOrderState();

        /**
         * 获取取货时间
         * @return
         */
        String getPickupTime();

        /**
         * 车辆信息
         * @return
         */
        String getCarInfo();

        /**
         * 送达时间
         * @return
         */
        String getArriveTime();

        /**
         * 获取订单信息
         * @return
         */
        MatchOrderInfoBean getInfo();

        /**
         * 是否显示收货、发货人信息
         * @return
         */
        boolean isShowContact();

        /**
         * 获取送达凭证图片
         * @return
         */
        List<String> getArriveVoucherImages();


        /**
         * 获取是否显示送达功能
         * @return true 显示
         */
        boolean isShowArriveVoucher();

        /**
         * 获取评分
         * @return
         */
        int getEstimateDriver();

        /**
         * 获取评价内容
         * @return
         */
        String getEstimateContentDriver();

        /**
         * 是否显示司机评价
         * @return
         */
        boolean isShowEstimateDriver();


        /**
         * 价格详情
         * @return
         */
        String getPriceDetail();

        /**
         * 获取运费
         * @return
         */
        String getPrice();

        /**
         * 是否显示底部按钮
         * @return
         */
        boolean isShowButton();

        /**
         * 获取底部按钮文案
         * @return
         */
        String getButtonInfo();

        /**
         * 显示底部按钮上面的电话图标
         * @return
         */
        boolean isShowBtCall();

        /**
         * 获取当前角色类型
         * @return
         */
        int getType();

        /**
         * 评价司机
         * @param rating
         * @param remark
         * @return
         */
        Observable<CommonBean<Object>> appraiseDrive(int rating, String remark);


        /**
         * 获取是否是查看路线
         * @return
         */
        boolean getShowRoute();


        /**
         * 获取起点位置
         * @return
         */
        Poi getStartPoi();
        /**
         * 获取终点位置
         * @return
         */
        Poi getEndPoi();

        /**
         * 获取火车导航信息
         * @return
         */
        AMapCarInfo getGuideCarInfo();

        String getEstimateContentOwner();

        int getEstimateOwner();

        boolean isShowEstimateOwner();
    }

}
