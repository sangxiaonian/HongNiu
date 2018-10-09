package com.hongniu.modulelogin.net;

import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CarTypeBean;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.LoginBean;
import com.hongniu.baselibrary.entity.LoginPersonInfor;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.PagerParambean;
import com.hongniu.modulelogin.entity.LoginCarInforBean;
import com.hongniu.modulelogin.entity.LoginSMSParams;
import com.hongniu.modulelogin.entity.PayInforBeans;
import com.sang.common.net.rx.RxUtils;
import com.sang.common.utils.ConvertUtils;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * 作者： ${PING} on 2018/8/13.
 * <p>
 * 登录使用的App
 */
public class HttpLoginFactory {




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
        return LoginClient.getInstance().getLoginService()
                .loginBySms(params)
                .compose(RxUtils.<CommonBean<LoginBean>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 获取个人信息
     */
    public static Observable<CommonBean<LoginPersonInfor>> getPersonInfor() {

        return LoginClient.getInstance().getLoginService()
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

        return LoginClient.getInstance().getLoginService()
                .changePersonInfor(infor)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 新增车辆
     */
    public static Observable<CommonBean<ResponseBody>> addCar(LoginCarInforBean infor) {

        return LoginClient.getInstance().getLoginService()
                .addCar(infor)
                .compose(RxUtils.<CommonBean<ResponseBody>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 修改车辆
     */
    public static Observable<CommonBean<String>> upDataCar(LoginCarInforBean infor) {

        return LoginClient.getInstance().getLoginService()
                .upDataCar(infor)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 删除车辆
     */
    public static Observable<CommonBean<String>> deletedCar(String id) {
        LoginCarInforBean bean = new LoginCarInforBean();
        bean.setId(id);
        return LoginClient.getInstance().getLoginService()
                .deletedCar(bean)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 获取车辆类型
     */
    public static Observable<CommonBean<List<CarTypeBean>>> getCarType() {

        return LoginClient.getInstance().getLoginService().getCarType().compose(RxUtils.<CommonBean<List<CarTypeBean>>>getSchedulersObservableTransformer());

    }

    /**
     * 获取车辆类型
     */
    public static Observable<CommonBean<PageBean<LoginCarInforBean>>> getCarList(int currentPage) {

        PagerParambean bean = new PagerParambean(currentPage);
        return LoginClient.getInstance().getLoginService().getCarList(bean).compose(RxUtils.<CommonBean<PageBean<LoginCarInforBean>>>getSchedulersObservableTransformer());

    }

    /**
     * 查询收款方式列表
     */
    public static Observable<CommonBean<List<PayInforBeans>>> queryMyPayInforList() {

        PayInforBeans bean = new PayInforBeans();
        return LoginClient.getInstance().getLoginService()
                .queryMyPayInforList(bean)
                .compose(RxUtils.<CommonBean<List<PayInforBeans>>>getSchedulersObservableTransformer());

    }
  /**
     * 更改默认收款方式
   * @param id 付款方式ID
   * @param isDefault  1 默认 0 非默认
   */
    public static Observable<CommonBean<String>> changeDefaultPayWay(String id, int isDefault) {

        PayInforBeans bean = new PayInforBeans();
        bean.setIsDefault(isDefault);
        bean.setId(id);
        return LoginClient.getInstance().getLoginService()
                .changeDefaultPayWay(bean)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());

    }

    /**
     * 新增银行卡收款方式
     */
    public static Observable<CommonBean<String>> addBlankCard(PayInforBeans bean) {

        return LoginClient.getInstance().getLoginService()
                .addBlankCard(bean)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());

    }

}
