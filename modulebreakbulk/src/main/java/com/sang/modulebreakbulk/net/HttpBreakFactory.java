package com.sang.modulebreakbulk.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.PagerParambean;
import com.sang.common.net.rx.RxUtils;
import com.sang.modulebreakbulk.entity.BreakbulkCompanyInfoBean;
import com.sang.modulebreakbulk.entity.BreakbulkCompanyInfoParam;
import com.sang.modulebreakbulk.entity.BreakbulkConsignmentCreateParams;
import com.sang.modulebreakbulk.entity.BreakbulkConsignmentInfoBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class HttpBreakFactory {


    /**
     * 查询物流公司
     *
     * @param params
     * @return
     */
    public static Observable<CommonBean<PageBean<BreakbulkCompanyInfoBean>>> queryBreakbulkCompany(BreakbulkCompanyInfoParam params) {
        return BreakbulkClient.getInstance().getService()
                .queryBreakbulkCompany(params)
                .map(new Function<CommonBean<List<BreakbulkCompanyInfoBean>>, CommonBean<PageBean<BreakbulkCompanyInfoBean>>>() {
                    @Override
                    public CommonBean<PageBean<BreakbulkCompanyInfoBean>> apply(CommonBean<List<BreakbulkCompanyInfoBean>> pageBeanCommonBean) throws Exception {
                        CommonBean<PageBean<BreakbulkCompanyInfoBean>> commonBean = new CommonBean<>();
                        commonBean.setCode(pageBeanCommonBean.getCode());
                        PageBean<BreakbulkCompanyInfoBean> pageBean = new PageBean<>();
                        pageBean.setList(pageBeanCommonBean.getData());
                        commonBean.setData(pageBean);
                        return commonBean;
                    }
                })
                .compose(RxUtils.<CommonBean<PageBean<BreakbulkCompanyInfoBean>>>getSchedulersObservableTransformer())
                ;

    }

    /**
     * 查询我的发货记录
     *
     * @return
     */
    public static Observable<CommonBean<PageBean<BreakbulkConsignmentInfoBean>>> queryBreakbulkConsignmentRecord(int page) {

        PagerParambean params=new PagerParambean(page);
        return BreakbulkClient.getInstance().getService()
                .queryBreakbulkConsignmentRecord(params)
                .compose(RxUtils.<CommonBean<PageBean<BreakbulkConsignmentInfoBean>>>getSchedulersObservableTransformer())
                ;

    }


    public static Observable<CommonBean<Object>> creatBreakbulk(BreakbulkConsignmentCreateParams params) {
        return BreakbulkClient.getInstance().getService()
                .creatBreakbulkConsignment(params)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer())
                ;

    }

}
