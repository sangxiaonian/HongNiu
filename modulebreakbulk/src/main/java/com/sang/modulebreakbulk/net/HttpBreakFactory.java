package com.sang.modulebreakbulk.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.sang.common.net.rx.RxUtils;
import com.sang.modulebreakbulk.entity.BreakbulkCompanyInfoBean;
import com.sang.modulebreakbulk.entity.BreakbulkCompanyInfoParam;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class HttpBreakFactory {


    /**
     * 创建货源信息
     *
     * @param params
     * @return
     */
    public static Observable<CommonBean<PageBean<BreakbulkCompanyInfoBean>>> creatGoodSour(BreakbulkCompanyInfoParam params) {
        return BreakbulkClient.getInstance().getService()
                .queryBreakbulkCompany(params)
                .map(new Function<CommonBean<List<BreakbulkCompanyInfoBean>>, CommonBean<PageBean<BreakbulkCompanyInfoBean>>>() {
                    @Override
                    public CommonBean<PageBean<BreakbulkCompanyInfoBean>> apply(CommonBean<List<BreakbulkCompanyInfoBean>> pageBeanCommonBean) throws Exception {
                        CommonBean<PageBean<BreakbulkCompanyInfoBean>> commonBean=new CommonBean<>();
                        commonBean.setCode(pageBeanCommonBean.getCode());
                        PageBean<BreakbulkCompanyInfoBean> pageBean=new PageBean<>();
                        pageBean.setList(pageBeanCommonBean.getData());
                        commonBean.setData(pageBean);
                        return commonBean;
                    }
                })
                .compose(RxUtils.<CommonBean<PageBean<BreakbulkCompanyInfoBean>>>getSchedulersObservableTransformer())
                ;

    }


}
