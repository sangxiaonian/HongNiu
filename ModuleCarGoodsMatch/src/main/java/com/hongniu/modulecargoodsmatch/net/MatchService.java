package com.hongniu.modulecargoodsmatch.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.IDParams;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.PagerParambean;
import com.hongniu.modulecargoodsmatch.entity.GoodsOwnerInforBean;
import com.hongniu.modulecargoodsmatch.entity.MatchCarPreInforBean;
import com.hongniu.modulecargoodsmatch.entity.MatchChooseGrapBean;
import com.hongniu.modulecargoodsmatch.entity.MatchCreatGoodsSourceParams;
import com.hongniu.modulecargoodsmatch.entity.MatchGrapDetailParams;
import com.hongniu.modulecargoodsmatch.entity.MatchGrapSingleDetailBean;
import com.hongniu.modulecargoodsmatch.entity.MatchMyJoinGoodsInofrBean;
import com.hongniu.modulecargoodsmatch.entity.MatchQueryGoodsInforParams;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @data 2019/5/21
 * @Author PING
 * @Description
 */
public interface MatchService {


    /**
     * 车货匹配发布货源信息
     *
     * @return
     */
    @POST("hongniu/api/goodsSource/add")
    Observable<CommonBean<Object>> creatGoodSour(@Body MatchCreatGoodsSourceParams params);

    /**
     * 车货匹配列表信息
     *
     * @return
     */
    @POST("hongniu/api/goodsSource/queryPage")
    Observable<CommonBean<PageBean<GoodsOwnerInforBean>>> queryMatchGoodsInfor(@Body MatchQueryGoodsInforParams params);

    /**
     * 我参与的车货匹配
     *
     * @return
     */
    @POST("hongniu/api/robOrder/myJoin")
    Observable<CommonBean<PageBean<MatchMyJoinGoodsInofrBean>>> queryMatchMyJoinGoodsInfor(@Body PagerParambean params);

    /**
     * 删除车货匹配
     *
     * @return
     */
    @POST("hongniu/api/goodsSource/delete")
    Observable<CommonBean<Object>> deleteMatchGoods(@Body IDParams params);

    /**
     * 接单明细
     *
     * @return
     */
    @POST("hongniu/api/robOrder/queryPage")
    Observable<CommonBean<PageBean<MatchGrapSingleDetailBean>>> queryGraoDetail(@Body MatchGrapDetailParams params);

    /**
     * 支付接单保证金，生成接单记录
     *
     * @return
     */
    @POST("hongniu/api/robOrder/add")
    Observable<CommonBean<GoodsOwnerInforBean>> grapMatch(@Body MatchGrapDetailParams params);

    /**
     * 取消参与
     *
     * @return
     */
    @POST("hongniu/api/robOrder/cancel")
    Observable<CommonBean<Object>> cancleParticipation(@Body MatchGrapDetailParams params);

    /**
     * 下单
     *
     * @return
     */
    @POST("hongniu/api/robOrder/choose")
    Observable<CommonBean<MatchChooseGrapBean>> chooseGrap(@Body MatchGrapDetailParams params);

    /**
     * 重新找司机
     *
     * @return
     */
    @POST("hongniu/api/robOrder/reset")
    Observable<CommonBean<Object>> resetDriver(@Body MatchGrapDetailParams params);
   /**
     * 车货匹配查询车辆宽高和所需车辆类型
     *
     * @return
     */
    @POST("hongniu/api/goodsSource/preload")
    Observable<CommonBean<MatchCarPreInforBean>> queryGoodCarInfor( );


}
