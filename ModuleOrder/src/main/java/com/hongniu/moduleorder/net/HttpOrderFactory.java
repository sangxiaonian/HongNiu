package com.hongniu.moduleorder.net;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.CreatInsuranceBean;
import com.hongniu.baselibrary.entity.OrderCreatBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.OrderIdBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.moduleorder.entity.LocationBean;
import com.hongniu.moduleorder.entity.OrderCarNumbean;
import com.hongniu.moduleorder.entity.OrderCreatParamBean;
import com.hongniu.moduleorder.entity.OrderDriverPhoneBean;
import com.hongniu.moduleorder.entity.OrderMainQueryBean;
import com.hongniu.moduleorder.entity.OrderParamBean;
import com.hongniu.moduleorder.entity.OrderSearchBean;
import com.hongniu.moduleorder.entity.PathBean;
import com.hongniu.moduleorder.entity.QueryInsurancePriceBean;
import com.hongniu.moduleorder.entity.VersionBean;
import com.sang.common.net.error.NetException;
import com.sang.common.net.rx.RxUtils;
import com.sang.common.utils.ConvertUtils;
import com.sang.thirdlibrary.pay.entiy.PayBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class HttpOrderFactory {

    /**
     * 检查版本更新
     */
    public static Observable<CommonBean<VersionBean>> checkVersion() {
        VersionBean bean = new VersionBean();
        bean.setType(2);
        return OrderClient.getInstance().getService()
                .checkVersion(bean)

                .compose(RxUtils.<CommonBean<VersionBean>>getSchedulersObservableTransformer());

    }

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
     * 获取到所有司机手机号
     */
    public static Observable<CommonBean<List<OrderDriverPhoneBean>>> getDriverPhone(String mobile) {
        OrderDriverPhoneBean bean = new OrderDriverPhoneBean();
        bean.setDriverMobile(mobile);
        return OrderClient.getInstance().getService().getDriverPhone(bean).compose(RxUtils.<CommonBean<List<OrderDriverPhoneBean>>>getSchedulersObservableTransformer());

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
     * payType      true	    int 	支付方式 0微信支付 1银联支付 2线下支付
     */
    public static Observable<CommonBean<PayBean>> payOrderOffLine(OrderParamBean bean) {
        //支付方式
        int payType = bean.getPayType();
        if (payType == 1) {
            return OrderClient.getInstance()
                    .getService()
                    .payUnionOffLine(bean)
                    .filter(new Predicate<CommonBean<PayBean>>() {
                        @Override
                        public boolean test(CommonBean<PayBean> payBeanCommonBean) throws Exception {
                            PayBean data = payBeanCommonBean.getData();
                            if (data !=null&&"00".equals(data.getCode())){
                                return true;
                            }else {
                                throw new NetException(500,data.getMsg());
                            }
                        }
                    })
                    .compose(RxUtils.<CommonBean<PayBean>>getSchedulersObservableTransformer());

        } else {
            return OrderClient.getInstance()
                    .getService()
                    .payOrderOffLine(bean)
                    .compose(RxUtils.<CommonBean<PayBean>>getSchedulersObservableTransformer());
        }


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
    public static Observable<List<LocationBean>> getPath(String orderID) {
        OrderIdBean bean = new OrderIdBean();
        bean.setOrderId(orderID);
        return OrderClient.getInstance()
                .getService()
                .getPath(bean)
                .map(new Function<CommonBean<PathBean>, List<LocationBean>>() {
                    @Override
                    public List<LocationBean> apply(CommonBean<PathBean> pathBeanCommonBean) throws Exception {
                        if (pathBeanCommonBean.getCode() == 200) {
                            return pathBeanCommonBean.getData().getList();

                        } else {
                            throw new NetException(pathBeanCommonBean.getCode(), pathBeanCommonBean.getMsg());
                        }

                    }
                })

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

    /**
     * 搜索订单
     * @return
     */
    public static Observable<CommonBean<PageBean<OrderSearchBean>>> searchOrder() {
            return Observable.just(1)
                    .map(new Function<Integer, CommonBean<PageBean<OrderSearchBean>>>() {
                        @Override
                        public CommonBean<PageBean<OrderSearchBean>> apply(Integer integer) throws Exception {

                            CommonBean<PageBean<OrderSearchBean>> bean = new CommonBean<>();
                            bean.setCode(200);

                            PageBean<OrderSearchBean> pageBean = new PageBean<>();
                            ArrayList<OrderSearchBean> balanceOfAccountBeans = new ArrayList<>();
                            int random = ConvertUtils.getRandom(19, 21);
                            for (int i = 0; i < random; i++) {
                                balanceOfAccountBeans.add(new OrderSearchBean());
                            }
                            pageBean.setList(balanceOfAccountBeans);
                            bean.setData(pageBean);

                            return bean;
                        }
                    });
        }
}
