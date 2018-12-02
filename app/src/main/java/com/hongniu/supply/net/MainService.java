package com.hongniu.supply.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.utils.clickevent.ClickEventBean;
import com.hongniu.supply.entity.HomeADBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public interface MainService {


    /**
     * 查询首页活动信息
     *
     * @return
     */
    @POST("hongniu/api/activity/list")
    Observable<CommonBean<List<HomeADBean>>> queryActivity();

    /**
     * 查询需要统计的点击事件
     *
     * @return
     */
    @POST("hongniu/api/account/eventtpye")
    Observable<CommonBean<List<HomeADBean>>> queryClickEvent();

    /**
     * 上传需要统计的点击事件
     *
     * @return
     * @param eventParams
     */
    @POST("hongniu/api/account/eventupload")
    Observable<CommonBean<String>> upClickEvent(@Body ClickEventBean eventParams);


}
