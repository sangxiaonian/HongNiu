package com.hongniu.baselibrary.net;

import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PayInforBeans;
import com.hongniu.baselibrary.entity.QueryPayPassword;
import com.hongniu.baselibrary.entity.RoleTypeBean;
import com.hongniu.baselibrary.entity.SMSParams;
import com.sang.common.net.rx.RxUtils;
import com.sang.common.utils.ConvertUtils;

import java.util.List;

import io.reactivex.Observable;

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
        SMSParams params = new SMSParams();
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

    /**
     * 查询是否设置过支付密码
     */
    public static Observable<CommonBean<QueryPayPassword>> queryPayPassword() {
        return AppClient.getInstance().getService()
                .queryPayPassword()
                .compose(RxUtils.<CommonBean<QueryPayPassword>>getSchedulersObservableTransformer());

    }
    /**
     * 新增收款方式
     */
    public static Observable<CommonBean<String>> addBlankCard(PayInforBeans bean) {
        bean.setType(1);
        return AppClient.getInstance().getService()
                .addPayWays(bean)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());

    }   /**
     * 新增收款方式
     */
    public static Observable<CommonBean<String>> addWeiChat(PayInforBeans bean) {
        bean.setType(0);
        return AppClient.getInstance().getService()
                .addPayWays(bean)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());

    }
}
