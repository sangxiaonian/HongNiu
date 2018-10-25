package com.hongniu.baselibrary.net;

import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PayInforBeans;
import com.hongniu.baselibrary.entity.RoleTypeBean;
import com.hongniu.baselibrary.entity.SMSParams;
import com.sang.common.net.rx.RxUtils;
import com.sang.common.utils.ConvertUtils;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 作者： ${PING} on 2018/8/13.
 * <p>
 * 登录使用的App
 */
public class HttpAppFactory {


    /**
     * 获取用户最近使用角色
     */
    public static Observable<CommonBean<RoleTypeBean>> getRoleType() {
        return AppClient.getInstance().getService().getRoleType()
                .compose(RxUtils.<CommonBean<RoleTypeBean>>getSchedulersObservableTransformer());
    }
    /**
     * 获取验证码
     *
     * @param mobile 手机号
     */
    public static Observable<CommonBean<String>> getSmsCode(String mobile) {
        SMSParams params = new  SMSParams();
        params.setMobile(mobile);
        params.setCode(ConvertUtils.MD5(mobile, Param.key));
        return AppClient.getInstance().getService()
                .getSmsCode(params)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer())
                ;
    }


    /**
     * 查询收款方式列表
     */
    public static Observable<CommonBean<List<PayInforBeans>>> queryMyCards() {

        PayInforBeans bean = new PayInforBeans();
        return AppClient.getInstance().getService()
                .queryMyCards(bean)
                .compose(RxUtils.<CommonBean<List<PayInforBeans>>>getSchedulersObservableTransformer());

    }

}
