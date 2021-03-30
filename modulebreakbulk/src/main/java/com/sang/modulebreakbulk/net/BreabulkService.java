package com.sang.modulebreakbulk.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.PagerParambean;
import com.sang.modulebreakbulk.entity.BreakbulkCompanyInfoBean;
import com.sang.modulebreakbulk.entity.BreakbulkCompanyInfoParam;
import com.sang.modulebreakbulk.entity.BreakbulkConsignmentCreateParams;
import com.sang.modulebreakbulk.entity.BreakbulkConsignmentInfoBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @data 2019/5/21
 * @Author PING
 * @Description
 */
public interface BreabulkService {


    /**
     * 车货匹配列表信息
     *
     * @return
     */
    @POST("api/companyAccount/list")
    Observable<CommonBean<List<BreakbulkCompanyInfoBean>>> queryBreakbulkCompany(@Body BreakbulkCompanyInfoParam params);
    /**
     * 车货匹配列表信息
     *
     * @return
     * @param params
     */
    @POST("api/ltl/queryPage")
    Observable<CommonBean<PageBean<BreakbulkConsignmentInfoBean>>> queryBreakbulkConsignmentRecord(@Body PagerParambean params);

   /**
     * 创建零担发货
     *
     * @return
    * @param params
     */
    @POST("api/ltl/addGoods")
    Observable<CommonBean<Object>> creatBreakbulkConsignment(@Body BreakbulkConsignmentCreateParams params);

}
