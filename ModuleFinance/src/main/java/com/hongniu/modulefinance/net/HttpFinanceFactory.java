package com.hongniu.modulefinance.net;

import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.modulefinance.entity.AccountFloowParamBean;
import com.hongniu.modulefinance.entity.AllBalanceOfAccountBean;
import com.hongniu.modulefinance.entity.BalanceOfAccountBean;
import com.hongniu.modulefinance.entity.NiuOfAccountBean;
import com.hongniu.modulefinance.entity.QueryExpendBean;
import com.hongniu.modulefinance.entity.QueryExpendResultBean;
import com.hongniu.modulefinance.entity.WalletHomeDetail;
import com.sang.common.net.rx.RxUtils;
import com.sang.common.utils.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

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
    public static Observable<CommonBean<WalletHomeDetail>> queryAccountdetails() {
        return FinanceClient.getInstance().getService().queryAccountdetails()
                .compose(RxUtils.<CommonBean<WalletHomeDetail>>getSchedulersObservableTransformer())
                ;
    }
 /**
     * 查询钱包账户详情
     *
     * @return
     */
    public static Observable<CommonBean<WalletHomeDetail>> queryMyCards() {
        return FinanceClient.getInstance().getService().queryMyCards()
                .compose(RxUtils.<CommonBean<WalletHomeDetail>>getSchedulersObservableTransformer())
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
                .map(new Function<CommonBean<AllBalanceOfAccountBean>, CommonBean<PageBean<BalanceOfAccountBean>>>() {
                    @Override
                    public CommonBean<PageBean<BalanceOfAccountBean>> apply(CommonBean<AllBalanceOfAccountBean> bean) throws Exception {
                        CommonBean<PageBean<BalanceOfAccountBean>> result = new CommonBean<>();
                        result.setCode(bean.getCode());
                        result.setMsg(bean.getMsg());
                        if (bean.getCode() == 200) {
                            result.setData(type == 1 ? bean.getData().getAccountFlows() : bean.getData().getTobeaccountFlows());
                        }
                        return result;
                    }
                })
                .compose(RxUtils.<CommonBean<PageBean<BalanceOfAccountBean>>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 牛币待入账，已入账查询
     */
    public static Observable<CommonBean<PageBean<NiuOfAccountBean>>> gueryNiuList() {
        return Observable.just(1)
                .map(new Function<Integer, CommonBean<PageBean<NiuOfAccountBean>>>() {
                    @Override
                    public CommonBean<PageBean<NiuOfAccountBean>> apply(Integer integer) throws Exception {

                        CommonBean<PageBean<NiuOfAccountBean>> bean = new CommonBean<>();
                        bean.setCode(200);

                        PageBean<NiuOfAccountBean> pageBean = new PageBean<>();
                        ArrayList<NiuOfAccountBean> balanceOfAccountBeans = new ArrayList<>();
                        int random = ConvertUtils.getRandom(19, 21);
                        for (int i = 0; i < random; i++) {
                            balanceOfAccountBeans.add(new NiuOfAccountBean());
                        }
                        pageBean.setList(balanceOfAccountBeans);
                        bean.setData(pageBean);

                        return bean;
                    }
                });
    }
}
