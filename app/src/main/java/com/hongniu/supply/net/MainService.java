package com.hongniu.supply.net;

import com.google.gson.JsonObject;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.utils.clickevent.ClickEventBean;
import com.hongniu.freight.entity.LoginInfo;
import com.hongniu.supply.entity.AppToken;
import com.hongniu.baselibrary.entity.CompanyInfoBean;
import com.hongniu.supply.entity.CompanyTokenInfoBean;
import com.hongniu.supply.entity.HomeADBean;
import com.hongniu.supply.entity.WayBillBean;
import com.hongniu.supply.entity.WayBillParams;

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

    /**
     * 根据运单号查询运单信息
     *
     * @return
     * @param eventParams
     */
    @POST("hongniu/api/waybill/show")
    Observable<CommonBean<List<WayBillBean>>>queryWaybill(@Body WayBillParams eventParams);
   /**
     *上传友盟token
     *
     * @return
    * @param eventParams
    */
    @POST("hongniu/api/user/updateDevice")
    Observable<CommonBean<Object>>upToken(@Body AppToken eventParams);
    /**
     * 查询所有子平台信息列表
     *
     * @return
     */
    @POST("hongniu/api/app/getsubinfo")
    Observable<CommonBean<List<CompanyInfoBean>>> queryCompanyInfo( );

    /**
     * 获取子平台登录token
     *
     * @return
     */
    @POST("hongniu/api/app/getsubtoken")
    Observable<CommonBean<CompanyTokenInfoBean>> queryCompanyLoginToken(@Body JsonObject object);

    /**
     * 获取子平台登录token
     *
     * @return
     */
    @POST("wlhyapi/api/login/tokenlogin")
    Observable<CommonBean<LoginInfo>> loginWithToken(@Body JsonObject object);

}
