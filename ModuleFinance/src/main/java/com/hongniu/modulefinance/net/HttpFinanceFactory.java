package com.hongniu.modulefinance.net;

import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.modulefinance.entity.FinanceOrderBean;
import com.hongniu.modulefinance.entity.QueryExpendBean;
import com.hongniu.modulefinance.entity.QueryExpendResultBean;
import com.sang.common.net.rx.RxUtils;

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
    public static Observable<CommonBean<PageBean<OrderDetailBean>>> queryFinance1(QueryExpendBean bean) {
        bean.setPageSize(Param.PAGE_SIZE);
        return FinanceClient.getInstance().getService().queryFinance1(bean)
                .map(new Function<CommonBean<PageBean<FinanceOrderBean>>, CommonBean<PageBean<OrderDetailBean>>>() {
                    @Override
                    public CommonBean<PageBean<OrderDetailBean>> apply(CommonBean<PageBean<FinanceOrderBean>> sour) throws Exception {
                        CommonBean<PageBean<OrderDetailBean>> result = new CommonBean<>();
                        result.setCode(sour.getCode());
                        result.setMsg(sour.getMsg());
                        PageBean<OrderDetailBean> pageBean = new PageBean<>();
                        result.setData(pageBean);
                        pageBean.setPageNum(sour.getData().getPageNum());
                        pageBean.setPages(sour.getData().getPages());
                        pageBean.setPageSize(sour.getData().getPageSize());
                        pageBean.setTotal(sour.getData().getTotal());
                        pageBean.setTotalMoney(sour.getData().getTotalMoney());

                        List<OrderDetailBean> list = new ArrayList<>();
                        pageBean.setList(list);
                        List<FinanceOrderBean> soutList = sour.getData().getList();
                        if (soutList != null && !soutList.isEmpty()) {
                            for (FinanceOrderBean sourData : soutList) {
                                OrderDetailBean data = new OrderDetailBean();


                                data.setCarId(sourData.getCarId());
                                data.setCarInfo(sourData.getCarInfo());
                                data.setCarNum(sourData.getCarNum());
                                data.setDeliveryDate(sourData.getDeliveryDate());
                                data.setDepartNum(sourData.getDepartNum());
                                data.setDestinationInfo(sourData.getDestinationInfo());
                                data.setDestinationLatitude(sourData.getDestinationLatitude());
                                data.setDestinationLongitude(sourData.getDestinationLongitude());
                                data.setDriverMobile(sourData.getDriverMobile());
                                data.setDriverName(sourData.getDriverName());
                                data.setFinanceType(sourData.getFinanceType());
                                data.setGoodName(sourData.getGoodName());
                                data.setGoodPrice(sourData.getGoodPrice() + "");
                                data.setGoodVolume(sourData.getGoodVolume() + "");
                                data.setGoodWeight(sourData.getGoodWeight() + "");
                                data.setId(sourData.getId());
                                data.setMoney(sourData.getMoney() + "");
                                data.setOrderNum(sourData.getOrderNum());
                                data.setOwnerMobile(sourData.getOwnerMobile());
                                data.setOwnerName(sourData.getOwnerName());
                                data.setPayTime(sourData.getDeliveryDate());
                                data.setRoleType(sourData.getRoleType());
                                data.setStartLatitude(sourData.getStartLatitude());
                                data.setStartLongitude(sourData.getStartLongitude());
                                data.setStartPlaceInfo(sourData.getStartPlaceInfo());
                                data.setStatus(sourData.getStatus());
                                list.add(data);

                            }
                        }


                        return result;
                    }
                })

                .compose(RxUtils.<CommonBean<PageBean<OrderDetailBean>>>getSchedulersObservableTransformer())
                ;
    }
}
