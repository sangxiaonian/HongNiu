package com.hongniu.modulefinance.net;

import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.modulefinance.entity.AccountFloowParamBean;
import com.hongniu.modulefinance.entity.BalanceOfAccountBean;
import com.hongniu.modulefinance.entity.BalanceWithDrawBean;
import com.hongniu.modulefinance.entity.CareNumPageBean;
import com.hongniu.modulefinance.entity.FinanceQueryCarDetailBean;
import com.hongniu.modulefinance.entity.FinanceQueryCarDetailMap;
import com.hongniu.modulefinance.entity.NiuOfAccountBean;
import com.hongniu.modulefinance.entity.QueryExpendBean;
import com.hongniu.modulefinance.entity.QueryExpendResultBean;
import com.hongniu.baselibrary.entity.WalletDetail;
import com.sang.common.net.rx.RxUtils;
import com.sang.common.utils.ConvertUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class HttpFinanceFactory {


    /**
     * 运费支出图表数据
     *
     * @param year  年
     * @param month 月
     * @return
     */
    public static Observable<CommonBean<List<QueryExpendResultBean>>> queryExpendVistogramTran(String year, String month) {

        return FinanceClient.getInstance().getService().queryExpendVistogramTran(new QueryExpendBean(year, month))
                .compose(RxUtils.<CommonBean<List<QueryExpendResultBean>>>getSchedulersObservableTransformer());
    }

    /**
     * 运费支出图表数据
     *
     * @param year  年
     * @param month 月
     * @return
     */
    public static Observable<CommonBean<List<QueryExpendResultBean>>> queryExpendVistogramInsurance(String year, String month) {

        return FinanceClient.getInstance().getService().queryExpendVistogramInsurance(new QueryExpendBean(year, month))
                .compose(RxUtils.<CommonBean<List<QueryExpendResultBean>>>getSchedulersObservableTransformer());
    }

    /**
     * 财务收入图表数据
     *
     * @param year  年
     * @param month 月
     * @return
     */
    public static Observable<CommonBean<List<QueryExpendResultBean>>> queryInComeVistogram(String year, String month) {

        return FinanceClient.getInstance().getService().queryInComeVistogram(new QueryExpendBean(year, month))
                .compose(RxUtils.<CommonBean<List<QueryExpendResultBean>>>getSchedulersObservableTransformer());
    }

    /**
     * 查询财务状况
     * year		int	年份
     * month	false	int	月份，从1开始，1代表1月，2代表2月以此类推。
     * carNo	false	String	车牌号
     * financeType	true	int	财务类型，0支出和收入；1支出；2收入
     * pageNum	false	int	页面索引
     * pageSize	false	int	页面记录条数
     *
     * @return
     */
    public static Observable<CommonBean<PageBean<OrderDetailBean>>> queryFinance(QueryExpendBean bean) {
        bean.setPageSize(Param.PAGE_SIZE);
        return FinanceClient.getInstance().getService().queryFinance(bean)
                .compose(RxUtils.<CommonBean<PageBean<OrderDetailBean>>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 查询钱包账户详情
     *
     * @return
     */
    public static Observable<CommonBean<WalletDetail>> queryAccountdetails() {
        return HttpAppFactory.queryAccountdetails()
                ;
    }


    /**
     * 余额提现
     * @param amount       提现金额
     * @param payPassword  密码
     * @param refundId     提现方式的ID
     * @return
     */
    public static Observable<CommonBean<String>> withdraw(String amount,String payPassword,String refundId) {
        BalanceWithDrawBean bean=new BalanceWithDrawBean(amount,ConvertUtils.MD5(payPassword),refundId);
        return FinanceClient.getInstance().getService().withdraw(bean)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 余额明细/待入账明细
     *
     * @param type        1余额 2待入账
     * @param currentPage
     */
    public static Observable<CommonBean<PageBean<BalanceOfAccountBean>>> gueryBananceOfAccount(final int type, int currentPage) {
        final AccountFloowParamBean bean = new AccountFloowParamBean();
        bean.setFlowtype(type);
        bean.setPageNum(currentPage);
        bean.setPageSize(Param.PAGE_SIZE);
        return FinanceClient
                .getInstance()
                .getService()
                .queryAccountFllows(bean)

                .compose(RxUtils.<CommonBean<PageBean<BalanceOfAccountBean>>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 牛贝待入账，已入账查询
     * @param currentPage
     * @param type
     */
    public static Observable<CommonBean<PageBean<NiuOfAccountBean>>> gueryNiuList(int currentPage, final int type) {
        AccountFloowParamBean bean = new AccountFloowParamBean();
        bean.setFlowtype(type);
        bean.setPageNum(currentPage);
        bean.setPageSize(Param.PAGE_SIZE);
       return FinanceClient
                .getInstance()
                .getService()
                .queryNiuAccountFllows(bean)
               .compose(RxUtils.<CommonBean<PageBean<NiuOfAccountBean>>>getSchedulersObservableTransformer())
               ;


    }
 /**
     * 牛贝待入账，已入账查询
  * @param currentPage
  */
    public static Observable<CommonBean<PageBean<FinanceQueryCarDetailBean>>> queryCarOrderDetails(int currentPage, final String userId) {
        CareNumPageBean bean = new CareNumPageBean(currentPage,userId);

       return FinanceClient
                .getInstance()
                .getService()
                .queryCarOrderDetails(bean)
                .map(new Function<CommonBean<List<FinanceQueryCarDetailMap>>, CommonBean<PageBean<FinanceQueryCarDetailBean>>>() {
                    @Override
                    public CommonBean<PageBean<FinanceQueryCarDetailBean>> apply(CommonBean<List<FinanceQueryCarDetailMap>> financeQueryCarDetailCommonBean) throws Exception {

                        CommonBean<PageBean<FinanceQueryCarDetailBean>> bean = new CommonBean<>();
                        bean.setMsg(financeQueryCarDetailCommonBean.getMsg());
                        bean.setCode(financeQueryCarDetailCommonBean.getCode());
                        PageBean<FinanceQueryCarDetailBean> pageBean=new PageBean<>();
                        pageBean.setList(new ArrayList<FinanceQueryCarDetailBean>());
                        bean.setData(pageBean);
                        List<FinanceQueryCarDetailMap> data = financeQueryCarDetailCommonBean.getData();
                        if (data!=null ){

                            for (FinanceQueryCarDetailMap datum : data) {
                                pageBean.getList().add(new FinanceQueryCarDetailBean(1,datum.getDates(),null));
                                if (datum.getList()!=null) {
                                    for (OrderDetailBean orderDetailBean : datum.getList()) {
                                        pageBean.getList().add(new FinanceQueryCarDetailBean(0,datum.getDates(),orderDetailBean));
                                    }
                                }
                            }
//
                        }
                        return bean;
                    }
                })
               .compose(RxUtils.<CommonBean<PageBean<FinanceQueryCarDetailBean>>>getSchedulersObservableTransformer())
               ;


    }






}
