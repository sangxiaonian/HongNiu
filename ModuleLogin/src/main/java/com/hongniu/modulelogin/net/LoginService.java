package com.hongniu.modulelogin.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.modulelogin.entity.request.LoginSMSParams;
import com.hongniu.modulelogin.entity.respond.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 作者： ${PING} on 2018/8/13.
 */
public interface LoginService {

    @POST("hongniu/api/login/getcheckcode")
    Observable<CommonBean<String>> getSmsCode(@Body LoginSMSParams params);

    @POST("hongniu/api/user/login")
    Observable<CommonBean<LoginBean>> loginBySms(@Body LoginSMSParams params);


}

