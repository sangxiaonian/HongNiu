package com.hongniu.freight.mode;

import com.amap.api.services.core.LatLonPoint;
import com.fy.androidlibrary.net.rx.RxUtils;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.hongniu.freight.control.OrderCreateControl;
import com.hongniu.freight.entity.CargoTypeAndColorBeans;
import com.hongniu.freight.entity.InsuranceInfoBean;
import com.hongniu.freight.entity.OrderCrateParams;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.entity.TranMapBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.ui.QueryInsurancePriceParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * 作者：  on 2020/2/17.
 */
public class OrderCreateMode implements OrderCreateControl.IOrderCreateMode {

    List<String> days;
    List<List<String>> hours;
    List<List<List<String>>> minutes;
    private TranMapBean startInfor;
    private TranMapBean endInfor;
    private boolean isInsurance;//是否购买保险 true 是
    private int payType = -1;//付款方式 -1未知 0现付 1到付
    List<String> payWays;//付款方式
    OrderCrateParams params;
    private InsuranceInfoBean insuranceInforBean;
    private OrderInfoBean orderInfoBean;//从订单页面传入的订单详情数据,只有修改订单时候会有数据
    private List<CargoTypeAndColorBeans> cargoTypes;
    private CargoTypeAndColorBeans cargoTypeAndColorBeans;//货物分类

    public OrderCreateMode() {
        params = new OrderCrateParams();
        cargoTypes=new ArrayList<>();
        days = new ArrayList<>();
        hours = new ArrayList<>();
        minutes = new ArrayList<>();
        payWays = new ArrayList<>();
        payWays.add("现付");
        payWays.add("到付");
        payType = 0;
    }

    /**
     * 储存传入的数据
     *
     * @param orderInfoBean
     */
    @Override
    public void saveInfo(OrderInfoBean orderInfoBean) {
        this.orderInfoBean = orderInfoBean;
    }

    /**
     * @param result 发货地址
     */
    @Override
    public void saveStartInfo(TranMapBean result) {
        this.startInfor = result;
    }

    /**
     * @param result 收货地址
     */
    @Override
    public void saveEndInfo(TranMapBean result) {
        this.endInfor = result;

    }

    /**
     * 获取发货时间年月日
     *
     * @return
     */
    @Override
    public Observable<Integer> getTimeInfo() {
        if (!CollectionUtils.isEmpty(days)) {
            return Observable.just(1);
        } else {
            return Observable.just(1)
                    .map(new Function<Integer, Integer>() {
                        @Override
                        public Integer apply(Integer integer) throws Exception {
                            days.clear();
                            days.addAll(getCurrentMonthDays(90));
                            hours.clear();
                            minutes.clear();
//                        days.remove(0);
                            days.add(0, "今天");
                            for (int i = 0; i < days.size(); i++) {

                                List<String> hour = new ArrayList<>();
                                hours.add(hour);

                                List<List<String>> minute = new ArrayList<>();
                                minutes.add(minute);
                                for (int j = 0; j < 24; j++) {
                                    List<String> min = new ArrayList<>();

                                    if (i == 0 && j == 0) {
                                        hour.add("立即发货");
                                        ArrayList<String> strings = new ArrayList<>();
                                        minute.add(strings);
                                        for (int k = 0; k < 60; k++) {
                                            strings.add("");
                                        }
                                    }
                                    for (int k = 0; k < 60; k++) {
                                        min.add(String.format(Locale.CHINESE, "%d分", (k)));
                                    }
                                    minute.add(min);
                                    hour.add(String.format(Locale.CHINESE, "%d点", (j)));
                                }
                            }
                            return integer;
                        }
                    })
                    .compose(RxUtils.<Integer>getSchedulersObservableTransformer());

        }
    }

    @Override
    public List<List<List<String>>> getMinutes() {
        return minutes;
    }

    @Override
    public List<String> getDays() {
        return days;
    }

    @Override
    public List<List<String>> getHours() {
        return hours;
    }

    /**
     * 更改发货时间
     *
     * @param time
     */
    @Override
    public void saveStartTime(String time) {
        params.setDepartureTime(time);
    }

    /**
     * 是否购买保险
     *
     * @return
     */
    @Override
    public boolean getIsInsurance() {
        return isInsurance;
    }

    /**
     * 储存当前是是否购买保险
     *
     * @param isInsurance true 是
     */
    @Override
    public void saveIsInsurance(boolean isInsurance) {
        this.isInsurance = isInsurance;
    }

    /**
     * 显示付款方式
     *
     * @return
     */
    @Override
    public List<String> getPayWaysInfo() {
        return payWays;
    }

    /**
     * 获取当前付款方式
     *
     * @return
     */
    @Override
    public int getPayType() {
        return payType;
    }

    /**
     * 储存当前付款方式
     *
     * @param payType
     */
    @Override
    public void savePayType(int payType) {
        this.payType = payType;
    }

    /**
     * 获取所有被保险人信息
     *
     * @return
     */
    @Override
    public Observable<CommonBean<List<InsuranceInfoBean>>> getAllInsuranceInfos() {
        return HttpAppFactory.queryInsuranceList();
    }

    /**
     * 切换被保险人信息
     *
     * @param position
     * @param def
     */
    @Override
    public void onChangeInsuranceInfo(int position, InsuranceInfoBean def) {
        this.insuranceInforBean = def;
    }

    @Override
    public OrderCrateParams getParams() {
        return params;
    }

    /**
     * 创建订单
     *
     * @return
     */
    @Override
    public Observable<CommonBean<OrderInfoBean>> createOrder() {
        //发货信息
        if (startInfor != null) {
            params.setStartPlaceInfo(startInfor.getAddressDetail());
            params.setStartPlaceLat(startInfor.getPoiItem().getLatLonPoint().getLatitude() + "");
            params.setStartPlaceLon(startInfor.getPoiItem().getLatLonPoint().getLongitude() + "");
            params.setShipperName(startInfor.getName());
            params.setShipperMobile(startInfor.getPhone());
        }

        //收货地信息
        if (endInfor != null) {
            params.setDestinationInfo(endInfor.getAddressDetail());
            params.setDestinationLat(endInfor.getPoiItem().getLatLonPoint().getLatitude() + "");
            params.setDestinationLon(endInfor.getPoiItem().getLatLonPoint().getLongitude() + "");
            params.setReceiverMobile(endInfor.getPhone());
            params.setReceiverName(endInfor.getName());
        }
        params.setFreightPayClass(payType == 0 ? 1 : 2);
        params.setInsurance(isInsurance ? 1 : 0);

        params.setCargoTypeClassificationCode(cargoTypeAndColorBeans==null?null:cargoTypeAndColorBeans.getValue());
        if (isInsurance && insuranceInforBean != null) {
            params.setInsuranceUserId(insuranceInforBean.getId());
        } else {
            params.setInsuranceUserId(null);
        }

        if (orderInfoBean != null) {
            //修改订单
            params.setId(orderInfoBean.getId());
            return HttpAppFactory.modifyOrder(params);
        } else {
            //新增订单
            return HttpAppFactory.createOrder(params);
        }
    }

    /**
     * 根据货物价格查询保费
     *
     * @param msg
     * @return
     */
    @Override
    public Observable<CommonBean<String>> queryInsurancePrice(String msg) {

        QueryInsurancePriceParams params = new QueryInsurancePriceParams();
        if (startInfor != null) {
            LatLonPoint latLonPoint = startInfor.getPoiItem().getLatLonPoint();
            params.setDestinationLat(latLonPoint.getLatitude());
            params.setDestinationLon(latLonPoint.getLongitude());

        }
        if (endInfor != null) {
            LatLonPoint latLonPoint = endInfor.getPoiItem().getLatLonPoint();
            params.setDestinationLat(latLonPoint.getLatitude());
            params.setDestinationLon(latLonPoint.getLongitude());
        }
        params.setGoodPrice(msg);

        return HttpAppFactory.queryInstancePrice(params);

    }

    /**
     * 查询货物种类
     *
     * @return
     */
    @Override
    public ObservableSource<CommonBean<List<CargoTypeAndColorBeans>>> queryCargoType() {
        return HttpAppFactory.queryConfigInfoType(6)
                .map(new Function<CommonBean<List<CargoTypeAndColorBeans>>, CommonBean<List<CargoTypeAndColorBeans>>>() {
                    @Override
                    public CommonBean<List<CargoTypeAndColorBeans>> apply(CommonBean<List<CargoTypeAndColorBeans>> listCommonBean) throws Exception {
                        cargoTypes.clear();
                        if (listCommonBean.getCode()== Param.SUCCESS_FLAG&&!CollectionUtils.isEmpty(listCommonBean.getData())){
                            cargoTypes.addAll(listCommonBean.getData());
                        }
                        return listCommonBean;
                    }
                })
                ;
    }

    /**
     * 查询货物种类
     *
     * @return
     */
    @Override
    public List<CargoTypeAndColorBeans> getCargoType() {
        return cargoTypes;
    }

    /**
     * 更新当前选中的货物代码
     *
     * @param cargoTypeAndColorBeans
     */
    @Override
    public void switchCargoType(CargoTypeAndColorBeans cargoTypeAndColorBeans) {
        this.cargoTypeAndColorBeans=cargoTypeAndColorBeans;
    }


    /**
     * 获取当前月份的日期
     *
     * @return
     */
    private List<String> getCurrentMonthDays(int count) {
        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");

        List<String> times = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            String format = simpleDateFormat.format(calendar.getTime());
            times.add(format);
        }
        return times;
    }


}
