package com.hongniu.moduleorder.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.CreatInsuranceBean;
import com.hongniu.moduleorder.entity.LocationBean;
import com.hongniu.moduleorder.entity.OrderCarNumbean;
import com.hongniu.moduleorder.entity.OrderCreatParamBean;
import com.hongniu.moduleorder.entity.OrderIdBean;
import com.hongniu.moduleorder.entity.OrderMainQueryBean;
import com.hongniu.moduleorder.entity.OrderParamBean;
import com.hongniu.moduleorder.entity.QueryInsurancePriceBean;
import com.hongniu.moduleorder.entity.WxPayBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
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
    Observable<CommonBean<OrderDetailBean>> creatOrder(@Body OrderCreatParamBean infor);

    /**
     * 获取车牌号联想
     * <p>
     * 参数名称	是否必须	数据类型	描述
     * userId	true	string	车主Id
     * carNumber	true	string	车牌号
     *
     * @return
     */
    @POST("hongniu/api/car/querynumber")
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

    /**
     * 查询订单数据
     * pageNum	false	number	页数，默认1
     * pageSize	false	number	每页条数，默认20
     * queryStatus	false	string	订单状态(2待发货，3配送中，4已到达,5已收货)
     * hasFreight	false	string	是否付运费(1是，0否)
     * userType	false	string	我的身份（3-货主/1-车主/2-司机）
     * deliveryDate	false	string	发车日期(today-今天 tomorrow-明天 thisweek-本周 nextweek-下周)
     *
     * @return
     */
    @POST("hongniu/api/order/queryPage")
    Observable<CommonBean<PageBean<OrderDetailBean>>> queryOrder(@Body OrderMainQueryBean infor);

    /**
     * 取消订单
     *
     * @param infor 订单ID
     * @return
     */
    @POST("hongniu/api/order/cancel")
    Observable<CommonBean<OrderDetailBean>> cancleOrder(@Body OrderIdBean infor);

    /**
     * 线下支付订单
     * orderNum     true	string	订单号
     * openid       true	string	微信用户openid
     * hasFreight   true	boolean	是否付运费，true=是
     * hasPolicy    true	boolean	是否买保险，true=是
     * onlinePay    true	boolean	是否线上支付,false=线下支付
     *
     * @param infor 订单ID
     * @return
     */
    @POST("hongniu/wx/jsApiPay")
    Observable<CommonBean<WxPayBean>> payOrderOffLine(@Body OrderParamBean infor);

    /**
     * 创建保单
     * orderNum	true	string	订单编号
     * goodsValue	true	number	货物价值
     */
    @POST("hongniu/api/policy/create")
    Observable<CommonBean<String>> creatInsurance(@Body CreatInsuranceBean infor);

    /**
     * 根据货物金额查询保费
     * orderId	true	number	订单id
     * goodPrice	true	number	货物价值，单位元，不能超过200万
     */
    @POST("hongniu/api/order/updategoodprice")
    Observable<CommonBean<String>> queryInstancePrice(@Body QueryInsurancePriceBean infor);

    /**
     * 司机开始发车
     * id	true	number	订单id
     */
    @POST("hongniu/api/order/start")
    Observable<CommonBean<String>> driverStart(@Body OrderIdBean infor);
   /**
     * 确认到达
     * id	true	number	订单id
     */
    @POST("hongniu/api/order/endSend")
    Observable<CommonBean<String>> entryArrive(@Body OrderIdBean infor);

    /**
     * 确认收货
     * id	true	number	订单id
     */
    @POST("hongniu//api/order/receive")
    Observable<CommonBean<ResponseBody>> entryReceiveCargo(@Body OrderIdBean infor);



    /**
     * 更改订单状态
     */
    @POST("hongniu/api/order/test-update-order")
    Observable<CommonBean<String>> debugChangeState(@Body OrderDetailBean infor);

    /**
     * 上传所有位置信息
     */
    @POST("hongniu/api/position/save")
    Observable<CommonBean<String>> upLoaction(@Body List<LocationBean> locationBeans);


}
