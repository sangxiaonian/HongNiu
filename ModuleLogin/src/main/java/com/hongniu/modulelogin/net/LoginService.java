package com.hongniu.modulelogin.net;

import com.hongniu.baselibrary.entity.CarTypeBean;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.LoginBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.PagerParambean;
import com.hongniu.modulelogin.entity.LoginCarInforBean;
import com.hongniu.modulelogin.entity.LoginPersonInfor;
import com.hongniu.modulelogin.entity.LoginSMSParams;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 作者： ${PING} on 2018/8/13.
 */
public interface LoginService {

    @POST("hongniu/api/login/getcheckcode")
    Observable<CommonBean<String>> getSmsCode(@Body LoginSMSParams params);

    @POST("hongniu/api/login/login")
    Observable<CommonBean<LoginBean>> loginBySms(@Body LoginSMSParams params);

    /**
     * 获取个人信息
     */
    @POST("hongniu/api/user/finduserinfo")
    Observable<CommonBean<LoginPersonInfor>> getPersonInfor();

    /**
     * 更改个人信息
     */
    @POST("hongniu/api/user/updateuserinfo")
    Observable<CommonBean<String>> changePersonInfor(@Body LoginPersonInfor infor);

    /**
     * 新增车辆信息
     */
    @POST("hongniu/api/car/savecar")
    Observable<CommonBean<ResponseBody>> addCar(@Body LoginCarInforBean infor);

    /**
     * 删除车辆信息
     */
    @POST("hongniu/api/car/deletebyid")
    Observable<CommonBean<String>> deletedCar(@Body LoginCarInforBean infor);

    /**
     * 获取车辆类型
     *
     * @return
     */
    @POST("hongniu//api/car/vehicletype")
    Observable<CommonBean<List<CarTypeBean>>> getCarType();

    /**
     * 获取车辆类型
     *
     * @return
     */
    @POST("hongniu/api/car/selectpagecar")
    Observable<CommonBean<PageBean<LoginCarInforBean>>> getCarList(@Body PagerParambean parambean);


}


