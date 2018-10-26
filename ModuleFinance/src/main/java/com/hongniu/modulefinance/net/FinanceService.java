package com.hongniu.modulefinance.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.modulefinance.entity.AccountFloowParamBean;
import com.hongniu.modulefinance.entity.AllBalanceOfAccountBean;
import com.hongniu.modulefinance.entity.BalanceWithDrawBean;
import com.hongniu.modulefinance.entity.QueryExpendBean;
import com.hongniu.modulefinance.entity.QueryExpendResultBean;
import com.hongniu.modulefinance.entity.WalletHomeDetail;

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

    /**
     * 查询财务收入图表数据
     *
     * @return
     */
    @POST("hongniu/api/finance/getExpressIncome")
    Observable<CommonBean<List<QueryExpendResultBean>>> queryInComeVistogram(@Body QueryExpendBean infor);

    /**
     * 财务搜索
     * year		int	年份
     * month	false	int	月份，从1开始，1代表1月，2代表2月以此类推。
     * carNo	false	String	车牌号
     * financeType	true	int	财务类型，0支出和收入；1支出；2收入
     * pageNum	false	int	页面索引
     * pageSize	false	int	页面记录条数
     *
     * @return
     */
    @POST("hongniu/api/finance/search")
    Observable<CommonBean<PageBean<OrderDetailBean>>> queryFinance(@Body QueryExpendBean infor);


    /**
     * 查询钱包账户详情
     *
     * @return
     */
    @POST("hongniu/api/account/accountdetails")
    Observable<CommonBean<WalletHomeDetail>> queryAccountdetails();

    /**
     * 查询钱包账户流水
     *
     * @param bean
     * @return
     */
    @POST("hongniu/api/account/accountflows")
    Observable<CommonBean<AllBalanceOfAccountBean>> queryAccountFllows(@Body AccountFloowParamBean bean);

  /**
     * 获取我的支付方式
     *
     * @return
     */
    @POST("hongniu/api/refund/queryMyCards")
    Observable<CommonBean<WalletHomeDetail>> queryMyCards();


  /**
     * 提现
     *
     * @return
   * @param bean
     */
    @POST("hongniu//api/account/withdraw")
    Observable<CommonBean<String>> withdraw(@Body BalanceWithDrawBean bean);


}
