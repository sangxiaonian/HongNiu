package com.hongniu.modulecargoodsmatch.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.IDParams;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.PagerParambean;
import com.hongniu.modulecargoodsmatch.entity.GoodsOwnerInforBean;
import com.hongniu.modulecargoodsmatch.entity.MatchCreatGoodsSourceParams;
import com.hongniu.modulecargoodsmatch.entity.MatchGrapDetailParams;
import com.hongniu.modulecargoodsmatch.entity.MatchGrapSingleDetailBean;
import com.hongniu.modulecargoodsmatch.entity.MatchMyJoinGoodsInofrBean;
import com.hongniu.modulecargoodsmatch.entity.MatchQueryGoodsInforParams;
import com.sang.common.net.rx.RxUtils;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class HttpMatchFactory {


    /**
     * 创建货源信息
     *
     * @param params
     * @return
     */
    public static Observable<CommonBean<Object>> creatGoodSour(MatchCreatGoodsSourceParams params) {
        return MatchClient.getInstance().getService()
                .creatGoodSour(params)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer())
                ;

    }

    /**
     * 查询车货匹配信息列表
     *
     * @param params
     * @return
     */
    public static Observable<CommonBean<PageBean<GoodsOwnerInforBean>>> queryMatchGoodsInfor(MatchQueryGoodsInforParams params) {
        return MatchClient.getInstance().getService()
                .queryMatchGoodsInfor(params)
                .compose(RxUtils.<CommonBean<PageBean<GoodsOwnerInforBean>>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 我参与的车货匹配
     *
     * @param params
     * @return
     */
    public static Observable<CommonBean<PageBean<MatchMyJoinGoodsInofrBean>>> queryMatchMyJoinGoodsInfor(PagerParambean params) {
        return MatchClient.getInstance().getService()
                .queryMatchMyJoinGoodsInfor(params)
                .compose(RxUtils.<CommonBean<PageBean<MatchMyJoinGoodsInofrBean>>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 删除车货匹配
     *
     * @return
     */
    public static Observable<CommonBean<Object>> deleteMatchGoods(String id) {
        IDParams params = new IDParams();
        params.id = id;
        return MatchClient.getInstance().getService()
                .deleteMatchGoods(params)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 抢单明细查詢
     *
     * @return
     */
    public static Observable<CommonBean<PageBean<MatchGrapSingleDetailBean>>> queryGraoDetail(String id, int currentPage) {
        MatchGrapDetailParams params = new MatchGrapDetailParams(currentPage);
        params.goodsSourceId = id;
        return MatchClient.getInstance().getService()
                .queryGraoDetail(params)
                .compose(RxUtils.<CommonBean<PageBean<MatchGrapSingleDetailBean>>>getSchedulersObservableTransformer())
                ;
    }
    /**
     * 抢单明细查詢
     *
     * @return
     */
    public static Observable<CommonBean<Object>> grapMatch(String id, String carId,String robAount) {
        MatchGrapDetailParams params = new MatchGrapDetailParams(0);
        params.goodsSourceId = id;
        params.carId = carId;
        params.robAmount = robAount;
        return MatchClient.getInstance().getService()
                .grapMatch(params)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer())
                ;
    }


}