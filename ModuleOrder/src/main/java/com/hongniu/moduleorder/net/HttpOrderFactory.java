package com.hongniu.moduleorder.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.moduleorder.entity.OrderCreatParamBean;
import com.sang.common.net.rx.RxUtils;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class HttpOrderFactory {

    /**
     * 创建订单
     * @param bean
     * @return
     */
    public static Observable<CommonBean<String>> creatOrder(OrderCreatParamBean bean){

        return OrderClient.getInstance().getService().creatOrder(bean).compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());

    }


}
