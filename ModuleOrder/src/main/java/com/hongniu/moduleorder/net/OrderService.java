package com.hongniu.moduleorder.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.moduleorder.entity.OrderCarNumbean;
import com.hongniu.moduleorder.entity.OrderCreatParamBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public interface OrderService {


    /**
     * 创建订单
     *
     * @return
     */
    @POST("hongniu/api/order/add")
    Observable<CommonBean<String>> creatOrder(@Body OrderCreatParamBean infor);

    /**
     * 获取车牌号联想
     *
     * 参数名称	是否必须	数据类型	描述
     * userId	true	string	车主Id
     * carNumber	true	string	车牌号
     *
     * @return
     */
    @POST("hongniu/api/carowner/querycars")
    Observable<CommonBean<List<OrderCarNumbean>>> getCarNum(@Body OrderCarNumbean infor);

    /**
     * 车牌号联想
     * 参数名称	是否必须	数据类型	描述
     * id	false	long	车辆Id。如果有id时，为修改现有的联想车辆；否则为新增联想。
     * userId	true	string	车主Id
     * carNumber	true	string	车牌号
     * ownerName	true	string	司机姓名
     * ownerPhone	true	string	司机手机号
     *
     * @return
     */
    @POST("hongniu/api/carowner/savecar")
    Observable<CommonBean<OrderCarNumbean>> addCarNum(@Body OrderCarNumbean infor);


}
