package com.hongniu.modulelogin.net;

import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.modulelogin.entity.request.LoginSMSParams;
import com.hongniu.modulelogin.entity.respond.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 作者： ${PING} on 2018/8/13.
 */
public interface LoginService {

    @Headers({"hn_app_key:" + Param.AppKey})
    @POST("hongniu/api/login/getcheckcode")
    Observable<CommonBean<String>> getSmsCode(@Body LoginSMSParams params);

    @Headers({"hn_app_key:" + Param.AppKey})
    @POST("/api/user/login")
    Observable<CommonBean<LoginBean>> loginBySms(@Body LoginSMSParams params);


}


