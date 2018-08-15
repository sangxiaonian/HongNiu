package com.hongniu.moduleorder.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.moduleorder.entity.OrderCreatParamBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public interface OrderService {


    /**
     * 创建订单
     * @return
     */
    @POST("hongniu/api/order/add")
    Observable<CommonBean<String>> creatOrder(@Body OrderCreatParamBean infor);


}
