package com.hongniu.moduleorder.net;

import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.moduleorder.entity.OrderCarNumbean;
import com.hongniu.moduleorder.entity.OrderCreatParamBean;
import com.hongniu.moduleorder.entity.OrderMainQueryBean;
import com.sang.common.net.rx.RxUtils;

import java.util.List;

import io.reactivex.Observable;

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
                .compose(RxUtils.<CommonBean<PageBean<OrderDetailBean>>>getSchedulersObservableTransformer());

    }


}
