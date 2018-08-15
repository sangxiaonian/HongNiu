package com.hongniu.supply.net;

import com.hongniu.baselibrary.entity.CarTypeBean;
import com.hongniu.baselibrary.entity.CommonBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * 作者： ${PING} on 2018/8/13.
 */
public interface AppService {

    @POST("hongniu//api/car/vehicletype")
    Observable<CommonBean<List<CarTypeBean>>> getCarType();



}


