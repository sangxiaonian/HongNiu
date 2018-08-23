package com.hongniu.modulefinance.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.modulefinance.entity.QueryExpendBean;
import com.hongniu.modulefinance.entity.QueryExpendResultBean;
import com.sang.common.net.rx.RxUtils;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class HttpFinanceFactory {


    /**
     * 运费支出图表数据
     * @param year  年
     * @param month 月
     * @return
     */
    public static  Observable<CommonBean<List<QueryExpendResultBean>>> queryExpendVistogramTran(String year, String month){

       return FinanceClient.getInstance().getService().queryExpendVistogramTran(new QueryExpendBean(year,month))
               .compose(RxUtils.<CommonBean<List<QueryExpendResultBean>>>getSchedulersObservableTransformer());
    }/**
     * 运费支出图表数据
     * @param year  年
     * @param month 月
     * @return
     */
    public static  Observable<CommonBean<List<QueryExpendResultBean>>> queryExpendVistogramInsurance(String year, String month){

       return FinanceClient.getInstance().getService().queryExpendVistogramInsurance(new QueryExpendBean(year,month))
               .compose(RxUtils.<CommonBean<List<QueryExpendResultBean>>>getSchedulersObservableTransformer());
    }
}
