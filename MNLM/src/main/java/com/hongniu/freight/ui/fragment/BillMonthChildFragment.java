package com.hongniu.freight.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.androidlibrary.utils.CommonUtils;
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.fy.companylibrary.ui.RefrushFragmet;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.BillInfoBean;
import com.hongniu.freight.entity.BillInfoListBean;
import com.hongniu.freight.entity.BillInfoSearchParams;
import com.hongniu.freight.entity.QueryExpendResultBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.ui.holder.BillHeadHolder;
import com.hongniu.freight.ui.holder.BillHolder;
import com.hongniu.freight.ui.holder.EmptyHolder;
import com.hongniu.freight.widget.VistogramView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * 作者：  on 2020/2/12.
 */
@Route(path = ArouterParamApp.fragment_bill_month_child)
public class BillMonthChildFragment extends RefrushFragmet<BillInfoListBean> {

    private int type;
    private BillHeadHolder billHeadHolder;
    BillInfoSearchParams params;
    private String year, month;
    OnBillListener billListener;
    private BillInfoBean data;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        upData();
    }

    private void upData() {
        if (getBundle() != null) {
            type = getBundle().getInt(Param.TYPE,0);
            params = getBundle().getParcelable(Param.TRAN);
            if (refresh != null && params != null) {
                if (!CommonUtils.equals(params.getYear(), year) || !(CommonUtils.equals(params.getMonth(), month))) {
                    year = params.getYear();
                    month = params.getMonth();
                    queryData(true, true);
                }

            }
        }
    }


    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.mnlm_fragment_bill_month_child, null);
        return inflate;

    }

    @Override
    protected void initData() {
        super.initData();
        billHeadHolder = new BillHeadHolder(mContext, rv);
        billHeadHolder.setType(type);
        adapter.addHeard(billHeadHolder);
    }

    @Override
    public void setBundle(Bundle bundle) {
        super.setBundle(bundle);
        upData();
    }

    /**
     * 获取空数据
     *
     * @param parent
     * @return
     */
    @Override
    protected PeakHolder getEmptyHolder(ViewGroup parent) {
        EmptyHolder emptyHolder = new EmptyHolder(mContext, parent);
        emptyHolder.setEmptyMsg("未找到相应账单");
        return emptyHolder;
    }

    @Override
    protected Observable<CommonBean<PageBean<BillInfoListBean>>> getListDatas() {
        params.setPageNum(currentPage);

        Observable<CommonBean<List<QueryExpendResultBean>>> observable=HttpAppFactory
                        .queryInComeVistogram(params);;
        switch (type) {
            case 0://仅仅是收入
                observable = HttpAppFactory
                        .queryInComeVistogram(params);
                break;
            case 1://运费

                observable = HttpAppFactory
                        .queryExpendVistogramTran(params);
                break;
            case 2://保费

                observable = HttpAppFactory
                        .queryExpendVistogramInsurance(params);
                break;
        }

        if (currentPage==1){
           return Observable.zip(observable
                   .filter(new Predicate<CommonBean<List<QueryExpendResultBean>>>() {
                       @Override
                       public boolean test(CommonBean<List<QueryExpendResultBean>> listCommonBean) throws Exception {
                           return listCommonBean.getCode()==Param.SUCCESS_FLAG;
                       }
                   })
                   .map(new Function<CommonBean<List<QueryExpendResultBean>>, CommonBean<List<QueryExpendResultBean>>>() {
                       @Override
                       public CommonBean<List<QueryExpendResultBean>> apply(CommonBean<List<QueryExpendResultBean>> listCommonBean) throws Exception {
                           List<QueryExpendResultBean> data = listCommonBean.getData();
                           List<List<VistogramView.VistogramBean>> current = new ArrayList<>();
                           if (!CollectionUtils.isEmpty(data)) {
                               for (QueryExpendResultBean datum : data) {
                                   List<VistogramView.VistogramBean> list = new ArrayList<>();
                                   for (int i = 0; i < datum.getCosts().size(); i++) {
                                       if (i % 2 == 0) {
                                           //线上支付
                                           list.add(new VistogramView.VistogramBean(Color.parseColor("#F06F28"), datum.getCosts().get(0).getMoney(), datum.getCostDate()));

                                       } else {
                                           //线下支付
                                           list.add(new VistogramView.VistogramBean(Color.parseColor("#007AFF"), datum.getCosts().get(1).getMoney(), datum.getCostDate()));

                                       }
                                   }

                                   current.add(list);
                               }
                           }
                           billHeadHolder.setDatas(current);
                           if (current.size() > 0) {
                               String s = String.format("%s-%s",year,month);
                               for (List<VistogramView.VistogramBean> debugData : current) {
                                   if (debugData != null && debugData.size() > 0) {
                                       if (debugData.get(0).xMark.equals(s)) {
                                           billHeadHolder.setCurrentX(current.indexOf(debugData));
                                       }
                                   }
                               }
                           }
                           return listCommonBean;
                       }
                   })
                   ,
                   HttpAppFactory.searchAccountList(params)
                           .map(new Function<CommonBean<BillInfoBean>, CommonBean<PageBean<BillInfoListBean>>>() {
                               @Override
                               public CommonBean<PageBean<BillInfoListBean>> apply(CommonBean<BillInfoBean> billInfoBeanCommonBean) throws Exception {
                                   CommonBean<PageBean<BillInfoListBean>> commonBean = new CommonBean<>();
                                   commonBean.setCode(billInfoBeanCommonBean.getCode());
                                   commonBean.setMsg(billInfoBeanCommonBean.getMsg());
                                   if (commonBean.getCode() == Param.SUCCESS_FLAG) {
                                         data = billInfoBeanCommonBean.getData();
                                       if (billListener != null) {
                                           billListener.showInfo(data.getTotalMoney(), data.getTotal());
                                       }
                                       PageBean<BillInfoListBean> bean = new PageBean<>();
                                       bean.setList(data.getList());
                                       commonBean.setData(bean);
                                   }

                                   return commonBean;
                               }
                           })
                   ,
                   new BiFunction<CommonBean<List<QueryExpendResultBean>>, CommonBean<PageBean<BillInfoListBean>>, CommonBean<PageBean<BillInfoListBean>>>() {
                       @Override
                       public CommonBean<PageBean<BillInfoListBean>> apply(CommonBean<List<QueryExpendResultBean>> listCommonBean, CommonBean<PageBean<BillInfoListBean>> pageBeanCommonBean) throws Exception {
                           return pageBeanCommonBean;
                       }
                   }
           );
        }else {

            return HttpAppFactory.searchAccountList(params)
                    .map(new Function<CommonBean<BillInfoBean>, CommonBean<PageBean<BillInfoListBean>>>() {
                        @Override
                        public CommonBean<PageBean<BillInfoListBean>> apply(CommonBean<BillInfoBean> billInfoBeanCommonBean) throws Exception {
                            CommonBean<PageBean<BillInfoListBean>> commonBean = new CommonBean<>();
                            commonBean.setCode(billInfoBeanCommonBean.getCode());
                            commonBean.setMsg(billInfoBeanCommonBean.getMsg());
                            if (commonBean.getCode() == Param.SUCCESS_FLAG) {
                                  data = billInfoBeanCommonBean.getData();
                                if (billListener != null) {
                                    billListener.showInfo(data.getTotalMoney(), data.getTotal());
                                }
                                PageBean<BillInfoListBean> bean = new PageBean<>();
                                bean.setList(data.getList());
                                commonBean.setData(bean);
                            }

                            return commonBean;
                        }
                    });
        }

    }

    @Override
    protected XAdapter<BillInfoListBean> getAdapter(List<BillInfoListBean> datas) {
        return new XAdapter<BillInfoListBean>(mContext, datas) {
            @Override
            public BaseHolder<BillInfoListBean> initHolder(ViewGroup parent, int viewType) {
                return new BillHolder(mContext, parent);
            }
        };
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            if (billListener != null) {
                billListener.showInfo(data==null?0:data.getTotalMoney(),data==null?0: data.getTotal());
            }
        }
    }

    public void setBillListener(OnBillListener billListener) {
        this.billListener = billListener;
    }

    public static interface OnBillListener{
        void showInfo(double totle,int count);
    }
}
