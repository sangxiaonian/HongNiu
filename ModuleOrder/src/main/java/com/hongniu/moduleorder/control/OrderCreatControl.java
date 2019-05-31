package com.hongniu.moduleorder.control;

import android.content.Context;

import com.amap.api.services.core.PoiItem;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;
import com.hongniu.moduleorder.entity.OrderCarNumbean;
import com.hongniu.baselibrary.entity.OrderCreatParamBean;
import com.hongniu.moduleorder.entity.OrderDriverPhoneBean;
import com.luck.picture.lib.entity.LocalMedia;
import com.sang.common.net.listener.TaskControl;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2019/5/23.
 */
public class OrderCreatControl {

    public interface IOrderCreataView {

        /**
         * 跟进类型更改标题
         * @param title
         * @param bt
         */
        void changeTitle(String title, String bt);

        /**
         * 显示车辆信息
         *
         * @param data
         */
        void showCarPop(List<OrderCarNumbean> data);

        /**
         * 显示司机信息
         *
         * @param data
         */
        void showDriverPop(List<OrderDriverPhoneBean> data);

        /**
         * 根据数据初始化页面信息
         * @param infor
         */
        void showInfor(OrderCreatParamBean infor);

        /**
         * 展示所有的图片信息
         * @param imageInfors
         */
        void showImageInfors(List<LocalMedia> imageInfors);

        /**
         * 根据订单状态修更改item能否修改
         * @param orderState
         * @param payOnLine
         * @param insurance
         */
        void changeEnableByOrder(OrderDetailItemControl.OrderState orderState, boolean payOnLine, boolean insurance);

        /**
         * 新增修改订单成功
         * @param data
         * @param type
         */
        void finishSuccess(OrderDetailBean data, int type);

        /**
         * 获取当前已经更改的信息
         * @param infor
         */
        void getValue(OrderCreatParamBean infor);

        void showFinishAleart(String s);

        void showConsigneePop(List<OrderDriverPhoneBean> data);
    }

    public interface IOrderCreataPresenter {

        /**
         * 查询车辆信息
         *
         * @param carNum 車牌號
         */
        void queryCarInfor(String carNum);

        /**
         * 根据司机名字获取司机手机号
         *
         * @param driverName 司机名称
         */
        void queryDriverInfor(String driverName);

        /**
         * 填写完数据之后点击提交按钮
         * @param result
         * @param listener
         */
        void submit(List<String> result, TaskControl.OnTaskListener listener);

        /**
         * 修改订单的时候，根据传入的订单ID查询相应的订单数据
         *
         * @param orderID
         * @param listener
         */
        void queryOrderInfor(String orderID, TaskControl.OnTaskListener listener);

        /**
         * 更改当前页面状态
         *
         * @param type 0 创建订单 1修改订单 2车货匹配订单
         * @param context
         */
        void changeType(int type, Context context);

        /**
         * 车货匹配时候，传入的数据
         * @param event
         */
        void saveInfor(OrderCreatParamBean event);

        /**
         * 更改起始位置
         * @param t
         */
        void changeStartPlaceInfor(PoiItem t);

        /**
         * 更改设置目的地
         * @param t
         */
        void changeEndPlaceInfor(PoiItem t);

        /**
         * 点击返回按钮
         */
        void onBacePress();

        /**
         * 查询收货人
         * @param textCenter
         */
        void queryConsighee(String textCenter);
    }

    public interface IOrderCreataMode {

        /**
         * 更改当前页面状态
         *
         * @param type 0 创建订单 1修改订单 2车货匹配订单
         */
        void saveType(int type);
        /**
         * 根据车牌号查询车辆
         *
         * @param carNum
         */
        Observable<CommonBean<List<OrderCarNumbean>>> queryCarInfor(String carNum);

        /**
         * 根据司机名字获取司机手机号
         *
         * @param driverName 司机名称
         */
        Observable<CommonBean<List<OrderDriverPhoneBean>>> queryDriverInfor(String driverName);

        /**
         * 填写完数据之后点击提交按钮
         * @param images
         */
        Observable<CommonBean<OrderDetailBean>> submit(List<String> images);

        /**
         * 修改订单的时候，根据订单ID查询订单数据
         *
         * @param orderID
         */
        Observable<CommonBean<OrderDetailBean>> queryOrderInfor(String orderID);

        /**
         * 储存修改房源的原始数据
         *
         * @param data
         */
        void saveOrderInfor(OrderDetailBean data);

        /**
         * 获取当前订单信息
         */
        OrderCreatParamBean getInfor();

        /**
         * 车货匹配时候，传入的数据
         * @param event
         */
        void saveInfor(OrderCreatParamBean event);

        /**
         * 获取当前类型
         */
        int getType();
    }


}
