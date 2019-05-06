package com.hongniu.modulefinance.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.PayInforBeans;
import com.hongniu.baselibrary.entity.WalletDetail;
import com.hongniu.modulefinance.entity.AccountFloowParamBean;
import com.hongniu.modulefinance.entity.BalanceOfAccountBean;
import com.hongniu.modulefinance.entity.BalanceWithDrawBean;
import com.hongniu.modulefinance.entity.BlankInfor;
import com.hongniu.modulefinance.entity.CareNumPageBean;
import com.hongniu.modulefinance.entity.FinanceQueryCarDetailMap;
import com.hongniu.modulefinance.entity.FinanceQueryNiuDetailBean;
import com.hongniu.modulefinance.entity.NiuOfAccountBean;
import com.hongniu.modulefinance.entity.QueryBindHuaInforsBean;
import com.hongniu.modulefinance.entity.QueryExpendBean;
import com.hongniu.modulefinance.entity.QueryExpendResultBean;
import com.hongniu.modulefinance.entity.QuerySubAccStateBean;

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
     * 查询钱包账户流水
     *
     * @param bean
     * @return
     */
    @POST("hongniu/api/account/accountflows")
    Observable<CommonBean<PageBean<BalanceOfAccountBean>>> queryAccountFllows(@Body AccountFloowParamBean bean);

    /**
     * 查询牛贝账户流水
     *
     * @param bean
     * @return
     */
    @POST("hongniu/api/account/accountintegralflows")
    Observable<CommonBean<PageBean<NiuOfAccountBean>>> queryNiuAccountFllows(@Body AccountFloowParamBean bean);

    /**
     * 查询车辆订单明细
     *
     * @param bean
     * @return
     */
    @POST("hongniu/api/account/userOrderdetails")
    Observable<CommonBean<List<FinanceQueryNiuDetailBean>>> queryNiurDetails(@Body CareNumPageBean bean);

    /**
     * 查询车辆订单明细
     *
     * @param bean
     * @return
     */
    @POST("hongniu/api/account/userOrderdetails")
    Observable<CommonBean<List<FinanceQueryCarDetailMap>>> queryCarOrderDetails(@Body CareNumPageBean bean);

    /**
     * 获取我的支付方式
     *
     * @return
     */
    @POST("hongniu/api/refund/queryMyCards")
    Observable<CommonBean<WalletDetail>> queryMyCards();
   /**
     * 解绑支付方式
     *
     * @return
     */
    @POST("hongniu/api/refund/remove")
    Observable<CommonBean<Object>> deleadCard(@Body BlankInfor blankID);


    /**
     * 提现
     *
     * @param bean
     * @return
     */
    @POST("hongniu//api/account/withdraw")
    Observable<CommonBean<String>> withdraw(@Body BalanceWithDrawBean bean);

    /**
     * 查询当前账户是否已经开通华夏银行账户
     *
     * @return
     */
    @POST("hongniu/api/hxbaccount/querySubAcc")
    Observable<CommonBean<QuerySubAccStateBean>> querySubAcc();

    /**
     * 查询用户充值账户信息
     *
     * @return
     */
    @POST("hongniu/api/hxbaccount/rechargeInfo")
    Observable<CommonBean<QueryBindHuaInforsBean>> queryHuaCards( );



}
