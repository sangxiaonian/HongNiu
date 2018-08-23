package com.hongniu.modulefinance.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.modulefinance.entity.QueryExpendBean;
import com.hongniu.modulefinance.entity.QueryExpendResultBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public interface FinanceService {


    /**
     * 查询运费支出图表数据
     *
     * @return
     */
    @POST("hongniu/api/finance/getExpressCost")
    Observable<CommonBean<List<QueryExpendResultBean>>> queryExpendVistogramTran(@Body QueryExpendBean infor);

    /**
     * 查询保费支出图表数据
     *
     * @return
     */
    @POST("hongniu//api/finance/getInsuranceCost")
    Observable<CommonBean<List<QueryExpendResultBean>>> queryExpendVistogramInsurance(@Body QueryExpendBean infor);


}
