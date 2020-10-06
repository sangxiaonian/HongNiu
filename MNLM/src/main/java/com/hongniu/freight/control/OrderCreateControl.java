package com.hongniu.freight.control;

import com.fy.androidlibrary.net.listener.TaskControl;
import com.fy.companylibrary.entity.CommonBean;
import com.hongniu.freight.entity.CargoTypeAndColorBeans;
import com.hongniu.freight.entity.OrderCrateParams;
import com.hongniu.freight.entity.InsuranceInfoBean;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.entity.TranMapBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

/**
 * 作者：  on 2020/2/17.
 */
public class OrderCreateControl {

    public interface IOrderCreateView {

        /**
         * 修改订单时候，初始化定订单数据
         *
         * @param orderInfoBean 订单详情
         */
        void initOrderUIInfo(OrderInfoBean orderInfoBean);

        /**
         * 展示发货信息
         *
         * @param result
         */
        void showStartInfo(TranMapBean result);

        /**
         * 初始化收货信息
         *
         * @param result
         */
        void showEndInfo(TranMapBean result);

        /**
         * 显示发货日期弹窗
         *
         * @param days
         * @param hours
         * @param minutes
         */
        void showTimePicker(List<String> days, List<List<String>> hours, List<List<List<String>>> minutes);

        /**
         * 显示发货日期
         *
         * @param time
         */
        void showTime(String time);

        /**
         * 切换当前是否购买保险
         *
         * @param isInsurance true 购买
         */
        void switchInsurance(boolean isInsurance);

        /**
         * 显示付款方式
         *
         * @param payType
         * @param payWaysInfo
         */
        void showPayDialog(int payType, List<String> payWaysInfo);

        void showPayType(int payType, String currentPayType);

        /**
         * 显示选择被保险人信息弹窗
         *
         * @param inforBeans 被保险人信息
         */
        void showInsuranceDialog(List<InsuranceInfoBean> inforBeans);

        /**
         * 获取所有参数
         *
         * @param params
         */
        void getParams(OrderCrateParams params);

        /**
         * 创建订单成功
         *
         * @param o
         */
        void finishSuccess(OrderInfoBean o);

        void showInsurancePrice(String insurancePrice);

        /**
         * 初始化保险信息
         *
         * @param goodPrice
         * @param insureUsername
         */
        void initInsuranceInfo(String goodPrice, String insureUsername);


        /**
         * 显示货物种类
         * @param cargoType
         */
        void showCargoTypes(List<CargoTypeAndColorBeans> cargoType);

        void switchCargoType(CargoTypeAndColorBeans cargoTypeAndColorBeans);

    }

    ;

    public interface IOrderCreatePresenter {

        /**
         * 储存传入的数据
         *
         * @param orderInfoBean
         */
        void saveInfo(OrderInfoBean orderInfoBean);

        /**
         * @param result 发货地址
         */
        void saveStartInfo(TranMapBean result);

        /**
         * @param result 收货地址
         */
        void saveEndInfo(TranMapBean result);

        /**
         * 显示发货时间
         *
         * @param listener
         */
        void showStartTime(TaskControl.OnTaskListener listener);

        /**
         * 切换当前年月日
         *
         * @param options1
         * @param options2
         * @param options3
         */
        void onChangeTime(int options1, int options2, int options3);

        /**
         * 切换是否购买保险
         */
        void onSwitchIsInsurance();

        /**
         * 显示支付方式dialog
         */
        void showPayDialog();

        /**
         * 切换支付方式
         *
         * @param payType 支付方式
         */
        void switchPayWay(int payType);

        /**
         * 展示所有被保险人信息
         *
         * @param listener
         */
        void showInsuranceInfo(TaskControl.OnTaskListener listener);

        /**
         * 切换被保险人信息
         *
         * @param position
         * @param def
         */
        void onChangeInsuranceInfo(int position, InsuranceInfoBean def);

        /**
         * 创建订单
         *
         * @param listener
         */
        void createOrder(TaskControl.OnTaskListener listener);


        /**
         * 查询保费
         *
         * @param msg
         */
        void searchInsruancePrice(String msg);


        /**
         * 显示货物种类弹窗
         * @param listener
         */
        void showCargoType(TaskControl.OnTaskListener listener);

        /**
         * 更新货物代码
         * @param options1
         */
        void switchCargoType(int options1);
    }

    ;

    public interface IOrderCreateMode {

        /**
         * 储存传入的数据
         *
         * @param orderInfoBean
         */
        void saveInfo(OrderInfoBean orderInfoBean);

        /**
         * @param result 发货地址
         */
        void saveStartInfo(TranMapBean result);

        /**
         * @param result 收货地址
         */
        void saveEndInfo(TranMapBean result);

        /**
         * 获取发货时间年月日
         *
         * @return
         */
        Observable<Integer> getTimeInfo();

        List<List<List<String>>> getMinutes();

        List<String> getDays();

        List<List<String>> getHours();

        /**
         * 更改发货时间
         *
         * @param time
         */
        void saveStartTime(String time);

        /**
         * 是否购买保险
         *
         * @return
         */
        boolean getIsInsurance();

        /**
         * 储存当前是是否购买保险
         *
         * @param isInsurance true 是
         */
        void saveIsInsurance(boolean isInsurance);

        /**
         * 显示付款方式
         *
         * @return
         */
        List<String> getPayWaysInfo();

        /**
         * 获取当前付款方式
         *
         * @return 当前付款方式
         */
        int getPayType();

        /**
         * 储存当前付款方式
         */
        void savePayType(int payType);

        /**
         * 获取所有被保险人信息
         *
         * @return
         */
        Observable<CommonBean<List<InsuranceInfoBean>>> getAllInsuranceInfos();

        /**
         * 切换被保险人信息
         *
         * @param position
         * @param def
         */
        void onChangeInsuranceInfo(int position, InsuranceInfoBean def);

        OrderCrateParams getParams();

        /**
         * 创建订单
         *
         * @return
         */
        Observable<CommonBean<OrderInfoBean>> createOrder();


        /**
         * 根据货物价格查询保费
         *
         * @param msg
         * @return
         */
        Observable<CommonBean<String>> queryInsurancePrice(String msg);

        /**
         * 查询货物种类
         *
         * @return
         */
        ObservableSource<CommonBean<List<CargoTypeAndColorBeans>>> queryCargoType();   /**
         * 查询货物种类
         *
         * @return
         */
         List<CargoTypeAndColorBeans>  getCargoType();

        /**
         * 更新当前选中的货物代码
         * @param cargoTypeAndColorBeans
         */
        void switchCargoType(CargoTypeAndColorBeans cargoTypeAndColorBeans);

    }

    ;

}
