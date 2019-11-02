package com.hongniu.modulecargoodsmatch.control;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.modulecargoodsmatch.entity.MatchOrderInfoBean;
import com.sang.common.net.listener.TaskControl;

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
         */
        void showArriveVoucher(List<String> arriveVoucherImages, boolean showArriveVoucher);

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
    }
    public interface IMatchOrderDataPresenter {
        void saveInfor(int type, MatchOrderInfoBean infoBean, TaskControl.OnTaskListener listener);

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
    }
    public interface IMatchOrderDataMode {

        void saveInfor(int type, MatchOrderInfoBean infoBean);

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
        int getEstimate();

        /**
         * 获取评价内容
         * @return
         */
        String getEstimateContent();

        /**
         * 是否显示司机评价
         * @return
         */
        boolean isShowEstimate();


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
    }

}
