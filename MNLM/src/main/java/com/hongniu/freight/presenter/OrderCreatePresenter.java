package com.hongniu.freight.presenter;

import android.text.TextUtils;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.fy.androidlibrary.net.listener.TaskControl;
import com.fy.androidlibrary.net.rx.BaseObserver;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.companylibrary.net.NetObserver;
import com.hongniu.freight.control.OrderCreateControl;
import com.hongniu.freight.entity.AppAddressListBean;
import com.hongniu.freight.entity.CargoTypeAndColorBeans;
import com.hongniu.freight.entity.InsuranceInfoBean;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.entity.OrderSelectDriverInfoBean;
import com.hongniu.freight.entity.OrderSelectOwnerInfoBean;
import com.hongniu.freight.entity.TranMapBean;
import com.hongniu.freight.mode.OrderCreateMode;

import java.util.List;
import java.util.Objects;

/**
 * 作者：  on 2020/2/17.
 */
public class OrderCreatePresenter implements OrderCreateControl.IOrderCreatePresenter {
    OrderCreateControl.IOrderCreateView view;
    OrderCreateControl.IOrderCreateMode mode;

    public OrderCreatePresenter(OrderCreateControl.IOrderCreateView view) {
        this.view = view;
        mode = new OrderCreateMode();
    }

    /**
     * 储存传入的数据
     *
     * @param orderInfoBean
     */
    @Override
    public void saveInfo(OrderInfoBean orderInfoBean) {
        if (orderInfoBean != null) {
            mode.saveInfo(orderInfoBean);
            //发货地址
            TranMapBean startInfor = new TranMapBean();
            startInfor.setAddressDetail(orderInfoBean.getStartPlaceInfo());
            startInfor.setAddress(orderInfoBean.getStartPlaceInfo());
            LatLonPoint start = new LatLonPoint(orderInfoBean.getStartPlaceLat(), orderInfoBean.getStartPlaceLon());
            PoiItem startPio = new PoiItem("", start, "", "");
            startInfor.setPoiItem(startPio);
            startInfor.setName(orderInfoBean.getShipperName());
            startInfor.setPhone(orderInfoBean.getShipperMobile());
            mode.saveStartInfo(startInfor);
            view.showStartInfo(startInfor);

            //收货地址
            TranMapBean endInfor = new TranMapBean();
            endInfor.setAddressDetail(orderInfoBean.getDestinationInfo());
            endInfor.setAddress(orderInfoBean.getDestinationInfo());
            LatLonPoint end = new LatLonPoint(orderInfoBean.getDestinationLat(), orderInfoBean.getDestinationLon());
            PoiItem endPio = new PoiItem("", end, "", "");
            endInfor.setPoiItem(endPio);
            endInfor.setName(orderInfoBean.getReceiverName());
            endInfor.setPhone(orderInfoBean.getReceiverMobile());
            mode.saveEndInfo(endInfor);
            view.showEndInfo(endInfor);

            //更改保险数据
            mode.saveIsInsurance(orderInfoBean.getInsurance() == 1);
            view.switchInsurance(mode.getIsInsurance());
            if (mode.getIsInsurance()) {
                InsuranceInfoBean insuranceInfoBean = new InsuranceInfoBean();
                insuranceInfoBean.setId(orderInfoBean.getInsuranceUserId());
                insuranceInfoBean.setUsername(orderInfoBean.getInsureUsername());
                insuranceInfoBean.setIdnumber(orderInfoBean.getInsureIdnumber());
                mode.onChangeInsuranceInfo(0, insuranceInfoBean);
                //初始化保险信息
                view.initInsuranceInfo(orderInfoBean.getGoodPrice(), orderInfoBean.getInsureUsername());
            }
            //发货时间
            mode.saveStartTime(orderInfoBean.getDepartureTime());

            CargoTypeAndColorBeans cargoTypeAndColorBeans = new CargoTypeAndColorBeans();
            cargoTypeAndColorBeans.setValue(orderInfoBean.getCargoTypeClassificationCode());
            cargoTypeAndColorBeans.setName(orderInfoBean.getCargoTypeClassificationInfo());
            mode.switchCargoType(cargoTypeAndColorBeans);
            view.switchCargoType(cargoTypeAndColorBeans);
            view.showTime(TextUtils.isEmpty(orderInfoBean.getDepartureTime()) ? "立即发货" : orderInfoBean.getDepartureTime());
            view.initOrderUIInfo(orderInfoBean);


            if ("1".equals(orderInfoBean.getIsdirect())) {
                OrderSelectOwnerInfoBean infoBean = new OrderSelectOwnerInfoBean();
                infoBean.setOwnerCompanyAccountId(orderInfoBean.getOwnerCompanyAccountId());
                infoBean.setOwnerId(orderInfoBean.getOwnerId());
                infoBean.setOwnerMobile(orderInfoBean.getOwnerMobile());
                infoBean.setOwnerName(orderInfoBean.getOwnerName());
                infoBean.setCarNumber(orderInfoBean.getCarNum());
                infoBean.setCarid(orderInfoBean.getCarId());
                infoBean.setVehicleType(orderInfoBean.getCarInfo());
                infoBean.setModel(orderInfoBean.getCartype());
                mode.saveOwnerInfo(infoBean);

                OrderSelectDriverInfoBean driverInfoBean = new OrderSelectDriverInfoBean();
                driverInfoBean.setId(orderInfoBean.getDriverId());
                driverInfoBean.setContact(orderInfoBean.getDriverName());
                driverInfoBean.setMobile(orderInfoBean.getDriverMobile());
                mode.saveDriverInfo(driverInfoBean);
                view.initDriverInfo(driverInfoBean);
                view.initOwnerInfo(infoBean);
            }
        } else {
            view.switchInsurance(false);

        }
    }

    /**
     * @param result 发货地址
     */
    @Override
    public void saveStartInfo(AppAddressListBean result) {

        TranMapBean bean=new TranMapBean();
        bean.setAddressDetail(result.getStartPlaceInfo());
        bean.setAddress(result.getStartPlaceInfo());
        bean.setName(result.getShipperName());
        bean.setPhone(result.getShipperMobile());
        PoiItem poiItem =new PoiItem("",new LatLonPoint(result.getStartPlaceLat(),result.getStartPlaceLon()),"","");
        bean.setPoiItem(poiItem);
        mode.saveStartInfo(bean);
        view.showStartInfo(bean);


    }

    /**
     * @param bean 收货地址
     */
    @Override
    public void saveEndInfo(AppAddressListBean result) {

        TranMapBean bean=new TranMapBean();
        bean.setAddressDetail(result.getDestinationInfo());
        bean.setAddress(result.getDestinationInfo());
        bean.setName(result.getReceiverName());
        bean.setPhone(result.getReceiverMobile());
        PoiItem poiItem =new PoiItem("",new LatLonPoint(result.getDestinationLat(),result.getDestinationLon()),"","");
        bean.setPoiItem(poiItem);

        mode.saveEndInfo(bean);
        view.showEndInfo(bean);

    }

    /**
     * 显示发货时间
     *
     * @param listener
     */
    @Override
    public void showStartTime(TaskControl.OnTaskListener listener) {
        mode.getTimeInfo()
                .subscribe(new BaseObserver<Integer>(listener) {
                    @Override
                    public void onNext(Integer result) {
                        super.onNext(result);
                        view.showTimePicker(mode.getDays(), mode.getHours(), mode.getMinutes());
                    }
                })
        ;
    }

    /**
     * 切换当前年月日
     *
     * @param options1 年月日
     * @param options2 小时
     * @param options3 分钟
     */
    @Override
    public void onChangeTime(int options1, int options2, int options3) {
        List<String> days = mode.getDays();
        List<List<String>> hours = mode.getHours();
        List<List<List<String>>> minutes = mode.getMinutes();
        String time;
        if (options1 == 0 && options2 == 0) {
            time = hours.get(options1).get(options2);
            mode.saveStartTime(null);
        } else {
            time = String.format("%s%s%s", days.get(options1), hours.get(options1).get(options2), minutes.get(options1).get(options2).get(options3));
            mode.saveStartTime(time);
        }
        view.showTime(time);
    }

    /**
     * 切换是否购买保险
     */
    @Override
    public void onSwitchIsInsurance() {
        boolean isInsurance = !mode.getIsInsurance();
        mode.saveIsInsurance(isInsurance);
        view.switchInsurance(isInsurance);
    }

    /**
     * 显示支付方式dialog
     */
    @Override
    public void showPayDialog() {
        mode.getPayWaysInfo();
        view.showPayDialog(mode.getPayType(), mode.getPayWaysInfo());
    }

    /**
     * 切换支付方式
     *
     * @param payType 支付方式
     */
    @Override
    public void switchPayWay(int payType) {
        mode.savePayType(payType);
        String currentPayType = mode.getPayWaysInfo().get(payType);
        view.showPayType(payType, currentPayType);
    }

    /**
     * 展示所有被保险人信息
     *
     * @param listener
     */
    @Override
    public void showInsuranceInfo(TaskControl.OnTaskListener listener) {

        mode.getAllInsuranceInfos()
                .subscribe(new NetObserver<List<InsuranceInfoBean>>(listener) {
                    @Override
                    public void doOnSuccess(List<InsuranceInfoBean> inforBeans) {
                        super.doOnSuccess(inforBeans);
                        view.showInsuranceDialog(inforBeans);
                    }
                })
        ;
    }

    /**
     * 切换被保险人信息
     *
     * @param position
     * @param def
     */
    @Override
    public void onChangeInsuranceInfo(int position, InsuranceInfoBean def) {
        mode.onChangeInsuranceInfo(position, def);
    }

    /**
     * 创建订单
     *
     * @param listener
     */
    @Override
    public void createOrder(TaskControl.OnTaskListener listener) {
        view.getParams(mode.getParams());
        mode.createOrder()
                .subscribe(new NetObserver<OrderInfoBean>(listener) {
                    @Override
                    public void doOnSuccess(OrderInfoBean o) {
                        super.doOnSuccess(o);
                        view.finishSuccess(o);
                    }
                })
        ;
    }

    /**
     * 查询保费
     *
     * @param msg
     */
    @Override
    public void searchInsruancePrice(String msg) {
        mode.queryInsurancePrice(msg)
                .subscribe(new NetObserver<String>(null) {
                    @Override
                    public void doOnSuccess(String s) {
                        super.doOnSuccess(s);
                        if (s != null && s.startsWith(".")) {
                            s = "0" + s;
                        }
                        view.showInsurancePrice(String.format("保费%s元", s));
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.showInsurancePrice(String.format("保费%s元", 0));
                    }
                });

    }

    /**
     * 显示货物种类弹窗
     *
     * @param listener
     */
    @Override
    public void showCargoType(TaskControl.OnTaskListener listener) {
        if (!CollectionUtils.isEmpty(mode.getCargoType())) {
            view.showCargoTypes(mode.getCargoType());
        } else {
            mode.queryCargoType()
                    .subscribe(new NetObserver<List<CargoTypeAndColorBeans>>(listener) {
                        @Override
                        public void doOnSuccess(List<CargoTypeAndColorBeans> cargoTypeAndColorBeans) {
                            super.doOnSuccess(cargoTypeAndColorBeans);
                            view.showCargoTypes(cargoTypeAndColorBeans);
                        }
                    });
            ;
        }
    }

    /**
     * 更新货物代码
     *
     * @param options1
     */
    @Override
    public void switchCargoType(int options1) {
        List<CargoTypeAndColorBeans> cargoType = mode.getCargoType();
        CargoTypeAndColorBeans cargoTypeAndColorBeans = cargoType.get(options1);
        mode.switchCargoType(cargoTypeAndColorBeans);
        view.switchCargoType(cargoTypeAndColorBeans);
    }

    @Override
    public void saveDriverInfo(OrderSelectDriverInfoBean result) {
        mode.saveDriverInfo(result);
        view.initDriverInfo(result);
    }

    @Override
    public void saveOwnerInfo(OrderSelectOwnerInfoBean result) {
        mode.saveOwnerInfo(result);
        view.initOwnerInfo(result);

        if (Objects.equals(result.isDriver(), "是")) {
            OrderSelectDriverInfoBean driverInfoBean = new OrderSelectDriverInfoBean();
            driverInfoBean.setMobile(result.getOwnerMobile());
            driverInfoBean.setContact(result.getOwnerName());
            driverInfoBean.setId(result.getOwnerId());
            saveDriverInfo(driverInfoBean);
        }

    }

    @Override
    public OrderSelectOwnerInfoBean getOwnerInfo() {
        return mode.getOwnerInfo();
    }

}
