package com.hongniu.baselibrary.net;

import com.hongniu.baselibrary.entity.CarTypeBean;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.OrderIdBean;
import com.hongniu.baselibrary.entity.PayInforBeans;
import com.hongniu.baselibrary.entity.QueryOrderStateBean;
import com.hongniu.baselibrary.entity.QueryPayPassword;
import com.hongniu.baselibrary.entity.RoleTypeBean;
import com.hongniu.baselibrary.entity.SMSParams;
import com.hongniu.baselibrary.entity.WalletDetail;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 作者： ${PING} on 2018/8/13.
 */
public interface AppService {

    @POST("hongniu//api/car/vehicletype")
    Observable<CommonBean<List<CarTypeBean>>> getCarType();

    /**
     * 查询订单状态
     *
     * @return
     * @param bean
     */
    @POST("hongniu/api/order/queryOrder")
    Observable<CommonBean<QueryOrderStateBean>> queryOrder(@Body OrderIdBean bean);

    /**
     * 获取用户最近使用角色
     *
     * @return
     */
    @POST("hongniu/api/user/queryUserRole")
    Observable<CommonBean<RoleTypeBean>> getRoleType();

    /**
     * 发送验证码
     *
     * @param params
     * @return
     */
    @POST("hongniu/api/login/getcheckcode")
    Observable<CommonBean<String>> getSmsCode(@Body SMSParams params);

    /**
     * 获取我的付款方式
     *
     * @return
     */
    @POST("hongniu/api/refund/queryMyCards")
    Observable<CommonBean<List<PayInforBeans>>> queryMyCards(@Body PayInforBeans beans);


    /**
     * 查询是否已经设置过支付密码
     *
     * @return
     */
    @POST("hongniu/api/account/accountdetails")
    Observable<CommonBean<QueryPayPassword>> queryPayPassword();

    /**
     * 新增付款方式
     *
     * @return
     */
    @POST("hongniu/api/refund/add")
    Observable<CommonBean<String>> addPayWays(@Body PayInforBeans beans);

    /**
     * 查询钱包账户详情
     *
     * @return
     */
    @POST("hongniu/api/account/accountdetails")
    Observable<CommonBean<WalletDetail>> queryAccountdetails();


}


