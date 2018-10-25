package com.hongniu.baselibrary.net;

import com.hongniu.baselibrary.entity.CarTypeBean;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PayInforBeans;
import com.hongniu.baselibrary.entity.RoleTypeBean;
import com.hongniu.baselibrary.entity.SMSParams;

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
     * 获取用户最近使用角色
     * @return
     */
    @POST("hongniu/api/user/queryUserRole")
    Observable<CommonBean<RoleTypeBean>> getRoleType();

    /**
     * 发送验证码
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

}


