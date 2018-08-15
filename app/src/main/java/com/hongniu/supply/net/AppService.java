package com.hongniu.supply.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.LoginBean;
import com.hongniu.modulelogin.entity.LoginAddCarBean;
import com.hongniu.modulelogin.entity.LoginPersonInfor;
import com.hongniu.modulelogin.entity.LoginSMSParams;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 作者： ${PING} on 2018/8/13.
 */
public interface AppService {

    @POST("hongniu//api/car/vehicletype")
    Observable<CommonBean<String>> getCarType(@Body LoginSMSParams params);



}


