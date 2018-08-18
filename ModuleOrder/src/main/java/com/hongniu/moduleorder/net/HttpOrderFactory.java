package com.hongniu.moduleorder.net;

import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.moduleorder.entity.OrderCarNumbean;
import com.hongniu.moduleorder.entity.OrderCreatParamBean;
import com.hongniu.moduleorder.entity.OrderMainQueryBean;
import com.hongniu.moduleorder.entity.OrderParamBean;
import com.iflytek.cloud.thirdparty.T;
import com.sang.common.net.rx.RxUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class HttpOrderFactory {

    /**
     * 创建订单
     *
     * @param bean
     * @return
     */
    public static Observable<CommonBean<String>> creatOrder(OrderCreatParamBean bean) {

        return OrderClient.getInstance().getService().creatOrder(bean).compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());

    }

    /**
     * 获取到所有车牌号
     *
     * @param carNum 车牌号
     * @return
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
     * @return
     */
    public static Observable<CommonBean<OrderCarNumbean>> addCarNum(OrderCarNumbean bean) {
        bean.setUserId(Utils.getPgetPersonInfor().getId());
        return OrderClient.getInstance().getService().addCarNum(bean).compose(RxUtils.<CommonBean<OrderCarNumbean>>getSchedulersObservableTransformer());

    }

    /**
     * 查询订单
     *
     * @param bean
     * @return
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
     * @return
     */
    public static Observable<CommonBean<ResponseBody>> cancleOrder(String orderID) {
        OrderParamBean bean = new OrderParamBean();
        bean.setId(orderID);
        return OrderClient.getInstance()
                .getService()
                .cancleOrder(bean)
                .compose(RxUtils.<CommonBean<ResponseBody>>getSchedulersObservableTransformer());

    }

    /**
     * 线下支付订单
     *
     * @param orderID 订单ID
     * @return
     */
    public static Observable<CommonBean<ResponseBody>> payOrderOffLine(String orderID) {
        OrderParamBean bean = new OrderParamBean();
        bean.setId(orderID);
        return OrderClient.getInstance()
                .getService()
                .payOrderOffLine(bean)
                .compose(RxUtils.<CommonBean<ResponseBody>>getSchedulersObservableTransformer());

    }


}
