package com.hongniu.supply.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.PagerParambean;
import com.hongniu.supply.entity.HomeADBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import xiaonian.sang.com.festivitymodule.invite.entity.BrokerageDetailsBean;
import xiaonian.sang.com.festivitymodule.invite.entity.InviteDetailBean;
import xiaonian.sang.com.festivitymodule.invite.entity.QueryInvitedInfo;

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


}
