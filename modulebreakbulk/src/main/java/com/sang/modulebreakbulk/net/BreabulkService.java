package com.sang.modulebreakbulk.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.sang.modulebreakbulk.entity.BreakbulkCompanyInfoBean;
import com.sang.modulebreakbulk.entity.BreakbulkCompanyInfoParam;

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
    @POST("hongniu/api/companyAccount/list")
    Observable<CommonBean<List<BreakbulkCompanyInfoBean>>> queryBreakbulkCompany(@Body BreakbulkCompanyInfoParam params);

}
