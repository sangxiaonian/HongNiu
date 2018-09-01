package com.hongniu.moduleorder.net;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.CreatInsuranceBean;
import com.hongniu.baselibrary.entity.OrderCreatBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.moduleorder.entity.LocationBean;
import com.hongniu.moduleorder.entity.OrderCarNumbean;
import com.hongniu.moduleorder.entity.OrderCreatParamBean;
import com.hongniu.baselibrary.entity.OrderIdBean;
import com.hongniu.moduleorder.entity.OrderMainQueryBean;
import com.hongniu.moduleorder.entity.OrderParamBean;
import com.hongniu.moduleorder.entity.PathBean;
import com.hongniu.moduleorder.entity.QueryInsurancePriceBean;
import com.hongniu.moduleorder.entity.WxPayBean;
import com.sang.common.net.rx.RxUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class HttpOrderFactory {

    /**
     * 创建订单
     *
     * @param bean
     */
    public static Observable<CommonBean<OrderDetailBean>> creatOrder(OrderCreatParamBean bean) {

        return OrderClient.getInstance().getService().creatOrder(bean).compose(RxUtils.<CommonBean<OrderDetailBean>>getSchedulersObservableTransformer());

    }

    /**
     * 获取到所有车牌号
     *
     * @param carNum 车牌号
     */
    public static Observable<CommonBean<List<OrderCarNumbean>>> getCarNum(String carNum) {
        OrderCarNumbean bean = new OrderCarNumbean();
        bean.setCarNumber(carNum);
        return OrderClient.getInstance().getService().getCarNum(bean).compose(RxUtils.<CommonBean<List<OrderCarNumbean>>>getSchedulersObservableTransformer());

    }

    /**
     * 添加联想车牌号
     *
     * @param bean
     */
    public static Observable<CommonBean<OrderCarNumbean>> addCarNum(OrderCarNumbean bean) {
        bean.setUserId(Utils.getLoginInfor().getId());
        return OrderClient.getInstance().getService().addCarNum(bean).compose(RxUtils.<CommonBean<OrderCarNumbean>>getSchedulersObservableTransformer());

    }

    /**
     * 查询订单
     */
    public static Observable<CommonBean<PageBean<OrderDetailBean>>> queryOrder(OrderMainQueryBean bean) {
        bean.setPageSize(Param.PAGE_SIZE);
        return OrderClient.getInstance()
                .getService()
                .queryOrder(bean)
                //对于未支付运费的状态进行转换
                .map(new Function<CommonBean<PageBean<OrderDetailBean>>, CommonBean<PageBean<OrderDetailBean>>>() {
                    @Override
                    public CommonBean<PageBean<OrderDetailBean>> apply(CommonBean<PageBean<OrderDetailBean>> pageBeanCommonBean) throws Exception {
                        PageBean<OrderDetailBean> data = pageBeanCommonBean.getData();
                        if (data != null && data.getList() != null) {
                            for (OrderDetailBean orderDetailBean : data.getList()) {
                                if (orderDetailBean.getStatus() == 2 && !orderDetailBean.isHasFreight()) {
                                    orderDetailBean.setStatus(1);
                                }
                            }
                        }
                        return pageBeanCommonBean;
                    }
                })
                .compose(RxUtils.<CommonBean<PageBean<OrderDetailBean>>>getSchedulersObservableTransformer());

    }

    /**
     * 取消订单
     *
     * @param orderID 订单ID
     */
    public static Observable<CommonBean<OrderDetailBean>> cancleOrder(String orderID) {
        OrderIdBean bean = new OrderIdBean();
        bean.setId(orderID);
        return OrderClient.getInstance()
                .getService()
                .cancleOrder(bean)
                .compose(RxUtils.<CommonBean<OrderDetailBean>>getSchedulersObservableTransformer());

    }

    /**
     * 线下支付订单
     * <p>
     * orderNum     true	string	订单号
     * openid       true	string	微信用户openid
     * hasFreight   true	boolean	是否付运费，true=是
     * hasPolicy    true	boolean	是否买保险，true=是
     * onlinePay    true	boolean	是否线上支付,false=线下支付
     */
    public static Observable<CommonBean<WxPayBean>> payOrderOffLine(OrderParamBean bean) {
        return OrderClient.getInstance()
                .getService()
                .payOrderOffLine(bean)
                .compose(RxUtils.<CommonBean<WxPayBean>>getSchedulersObservableTransformer());

    }


    /**
     * 创建保单
     * orderNum	true	string	订单编号
     * goodsValue	true	number	货物价值
     */
    public static Observable<CommonBean<OrderCreatBean>> creatInsurance(CreatInsuranceBean bean) {
        return OrderClient.getInstance()
                .getService()
                .creatInsurance(bean)
                .compose(RxUtils.<CommonBean<OrderCreatBean>>getSchedulersObservableTransformer());
    }

    /**
     * 根据货物金额查询保费
     *
     * @param cargoPrice 货物金额
     * @param orderId
     */
    public static Observable<CommonBean<String>> queryInstancePrice(String cargoPrice, String orderId) {
        QueryInsurancePriceBean bean = new QueryInsurancePriceBean(orderId, cargoPrice);
        return OrderClient.getInstance()
                .getService()
                .queryInstancePrice(bean)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());
    }

    /**
     * 开始发车
     *
     * @param orderId
     */
    public static Observable<CommonBean<String>> driverStart(String orderId) {
        OrderIdBean bean = new OrderIdBean();
        bean.setId(orderId);
        return OrderClient.getInstance()
                .getService()
                .driverStart(bean)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());
    }

    /**
     * 确认到达
     *
     * @param orderId
     */
    public static Observable<CommonBean<String>> entryArrive(String orderId) {
        OrderIdBean bean = new OrderIdBean();
        bean.setId(orderId);
        return OrderClient.getInstance()
                .getService()
                .entryArrive(bean)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());
    }

    /**
     * 确认收货
     *
     * @param orderId
     */
    public static Observable<CommonBean<OrderDetailBean>> entryReceiveCargo(String orderId) {
        OrderIdBean bean = new OrderIdBean();
        bean.setId(orderId);
        return OrderClient.getInstance()
                .getService()
                .entryReceiveCargo(bean)
                .compose(RxUtils.<CommonBean<OrderDetailBean>>getSchedulersObservableTransformer());
    }

    /**
     * 更改订单状态
     *
     * @param orderBean  订单相关信息
     * @param state      订单状态
     * @param insurance  是否购买保险
     * @param hasFreight 是否支付运费
     */
    public static Observable<CommonBean<String>> changeOrderState(OrderDetailBean orderBean, int state, boolean insurance, boolean hasFreight) {
        orderBean.setInsurance(insurance);
        orderBean.setHasFreight(hasFreight);
        orderBean.setStatus(state);
        return OrderClient.getInstance()
                .getService()
                .debugChangeState(orderBean)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());
    }

    /**
     * 上传位置数据
     *
     * @param poll
     */
    public static Observable<CommonBean<String>> upLoaction(List<LocationBean> poll) {
        return OrderClient.getInstance()
                .getService()
                .upLoaction(poll)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());
    }

    /**
     * 获取指定车辆轨迹
     *
     * @param orderID
     */
    public static Observable<CommonBean<PathBean>> getPath(String orderID) {
        OrderIdBean bean =new OrderIdBean();
        bean.setOrderId(orderID);
        return OrderClient.getInstance()
                .getService()
                .getPath(bean)
               ;
    }

    /**
     * 高德地图搜索PIO
     *
     * @param poiSearch
     */
    public static Observable<CommonBean<PageBean<PoiItem>>> searchPio(PoiSearch poiSearch) {


        return Observable.just(poiSearch)
                .map(new Function<PoiSearch, PoiResult>() {

                    public PoiResult apply(PoiSearch poiSearch) throws Exception {
                        return poiSearch.searchPOI();
                    }
                })
                .map(new Function<PoiResult, ArrayList<PoiItem>>() {
                    @Override
                    public ArrayList<PoiItem> apply(PoiResult poiResult) throws Exception {


                        return poiResult.getPois();
                    }
                })
                .map(new Function<ArrayList<PoiItem>, CommonBean<PageBean<PoiItem>>>() {
                    @Override
                    public CommonBean<PageBean<PoiItem>> apply(ArrayList<PoiItem> poiItems) throws Exception {
                        CommonBean<PageBean<PoiItem>> bean = new CommonBean<>();
                        PageBean<PoiItem> pageBean = new PageBean<>();
                        pageBean.setList(poiItems);
                        bean.setCode(200);
                        bean.setData(pageBean);
                        return bean;
                    }
                })
                .compose(RxUtils.<CommonBean<PageBean<PoiItem>>>getSchedulersObservableTransformer())
                ;


    }
}
