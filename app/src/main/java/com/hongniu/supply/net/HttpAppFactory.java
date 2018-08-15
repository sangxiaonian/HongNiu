package com.hongniu.supply.net;

import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.LoginBean;
import com.hongniu.modulelogin.entity.LoginAddCarBean;
import com.hongniu.modulelogin.entity.LoginPersonInfor;
import com.hongniu.modulelogin.entity.LoginSMSParams;
import com.sang.common.net.rx.RxUtils;
import com.sang.common.utils.ConvertUtils;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2018/8/13.
 * <p>
 * 登录使用的App
 */
public class HttpAppFactory {


    /**
     * 登录时候获取验证码
     *
     * @param mobile 手机号
     */
    public static Observable<CommonBean<String>> getSmsCode(String mobile) {
        LoginSMSParams params = new LoginSMSParams();
        params.setMobile(mobile);
        params.setCode(ConvertUtils.MD5(mobile, Param.key));
        return AppClient.getInstance().getLoginService()
                .getSmsCode(params)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 使用验证码登录
     *
     * @param mobile 手机号
     * @param code   验证码
     */
    public static Observable<CommonBean<LoginBean>> loginBySms(String mobile, String code) {
        LoginSMSParams params = new LoginSMSParams();
        params.setMobile(mobile);
        params.setCheckCode(code);
        return AppClient.getInstance().getLoginService()
                .loginBySms(params)
                .compose(RxUtils.<CommonBean<LoginBean>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 获取个人信息
     */
    public static Observable<CommonBean<LoginPersonInfor>> getPersonInfor() {

        return AppClient.getInstance().getLoginService()
                .getPersonInfor()
                .compose(RxUtils.<CommonBean<LoginPersonInfor>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 更改个人信息
     *
     * @param infor
     */
    public static Observable<CommonBean<String>> changePersonInfor(LoginPersonInfor infor) {

        return AppClient.getInstance().getLoginService()
                .changePersonInfor(infor)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 新增车辆
     *
     */
    public static Observable<CommonBean<String>> addCar(LoginAddCarBean infor) {

        return AppClient.getInstance().getLoginService()
                .addCar(infor)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer())
                ;
    }


}
