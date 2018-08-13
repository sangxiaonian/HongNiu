package com.hongniu.modulelogin.net;

import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.modulelogin.entity.request.LoginSMSParams;
import com.sang.common.net.rx.RxUtils;
import com.sang.common.utils.ConvertUtils;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2018/8/13.
 * <p>
 * 登录使用的App
 */
public class HttpLoginFactory {


    /**
     * 登录时候获取验证码
     *
     * @param mobile 手机号
     */
    public static Observable<CommonBean<String>> getSmsCode(String mobile) {
        LoginSMSParams params = new LoginSMSParams();
        params.setMobile(mobile);
        params.setCode(ConvertUtils.MD5(mobile, Param.key));
        return LoginClient.getInstance().getLoginService()
                .getSmsCode(params)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer())
                ;
    }


}
