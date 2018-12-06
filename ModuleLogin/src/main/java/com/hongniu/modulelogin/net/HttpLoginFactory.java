package com.hongniu.modulelogin.net;

import com.hongniu.baselibrary.entity.CarTypeBean;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.LoginBean;
import com.hongniu.baselibrary.entity.LoginPersonInfor;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.PagerParambean;
import com.hongniu.modulelogin.entity.LoginCarInforBean;
import com.hongniu.modulelogin.entity.LoginCreatInsuredBean;
import com.hongniu.modulelogin.entity.LoginSMSParams;
import com.hongniu.baselibrary.entity.PayInforBeans;
import com.hongniu.modulelogin.entity.SetPayPassWord;
import com.sang.common.net.rx.RxUtils;
import com.sang.common.utils.CommonUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
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
     * 使用验证码登录
     *
     * @param mobile 手机号
     * @param code   验证码
     */
    public static Observable<CommonBean<String>> checkSms(String mobile, String code) {
        LoginSMSParams params = new LoginSMSParams();
        params.setMobile(mobile);
        params.setCheckCode(code);
        return LoginClient.getInstance().getLoginService()
                .ckeckcode(params)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer())
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
     * 获取货车辆类型
     */
    public static Observable<CommonBean<List<CarTypeBean>>> queyTruckTypes() {

        return LoginClient.getInstance()
                .getLoginService()
                .queyTruckTypes()
                .compose(RxUtils.<CommonBean<List<CarTypeBean>>>getSchedulersObservableTransformer());

    }
   /**
     * 修改货车信息
     */
    public static Observable<CommonBean<String>> upTruckInfor(LoginCarInforBean bean) {

        return LoginClient.getInstance()
                .getLoginService()
                .upTruckInfor(bean)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());

    }

    /**
     * 查询货车量详细信息
     */
    public static Observable<CommonBean<LoginCarInforBean>> queyCarDetailInfor(final String carNumber) {
        final LoginCarInforBean bean = new LoginCarInforBean();
        bean.setCarNumber(carNumber);
        return LoginClient.getInstance()
                .getLoginService()
                .queyCarDetailInfor(bean)
                .map(new Function<CommonBean<List<LoginCarInforBean>>, CommonBean<LoginCarInforBean>>() {
                    @Override
                    public CommonBean<LoginCarInforBean> apply(CommonBean<List<LoginCarInforBean>> listCommonBean) throws Exception {

                        CommonBean<LoginCarInforBean> bean1 = new CommonBean<LoginCarInforBean>();
                        bean1.setMsg(listCommonBean.getMsg());
                        bean1.setCode(listCommonBean.getCode());
                        List<LoginCarInforBean> data = listCommonBean.getData();
                        if (!CommonUtils.isEmptyCollection(data)) {
                            bean1.setData(data.get(0));
                        } else {
                            LoginCarInforBean bean2 = new LoginCarInforBean();
                            bean2.setCarNumber(carNumber);
                            bean1.setData(bean2);
                        }
                        return bean1;
                    }
                })
                .compose(RxUtils.<CommonBean<LoginCarInforBean>>getSchedulersObservableTransformer());

    }

    /**
     * 获取车辆类型
     */
    public static Observable<CommonBean<PageBean<LoginCarInforBean>>> getCarList(int currentPage) {

        PagerParambean bean = new PagerParambean(currentPage);
        return LoginClient.getInstance().getLoginService().getCarList(bean).compose(RxUtils.<CommonBean<PageBean<LoginCarInforBean>>>getSchedulersObservableTransformer());

    }


    /**
     * 更改默认收款方式
     *
     * @param id        付款方式ID
     * @param isDefault 1 默认 0 非默认
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
        bean.setType(1);
        return LoginClient.getInstance().getLoginService()
                .addBlankCard(bean)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());

    }


    /**
     * 更新支付密码
     *
     * @param passwordMd5
     * @param smsCode
     * @return
     */
    public static Observable<CommonBean<String>> upPassword(String passwordMd5, String smsCode) {
        SetPayPassWord payPassword = new SetPayPassWord(passwordMd5, smsCode);
        return LoginClient.getInstance().getLoginService()
                .setPayPassword(payPassword)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());
    }


    /**
     * 创建被保险人信息
     */
    public static Observable<CommonBean<LoginCreatInsuredBean>> creatInsuredInfor(LoginCreatInsuredBean bean) {
        return LoginClient.getInstance().getLoginService()
                .creatInsuredInfor(bean)
                .compose(RxUtils.<CommonBean<LoginCreatInsuredBean>>getSchedulersObservableTransformer());
    }

    /**
     * 修改被保险人信息
     */
    public static Observable<CommonBean<LoginCreatInsuredBean>> upInsuredInfor(LoginCreatInsuredBean bean) {
        return LoginClient.getInstance().getLoginService()
                .upInsuredInfor(bean)
                .compose(RxUtils.<CommonBean<LoginCreatInsuredBean>>getSchedulersObservableTransformer());
    }

    /**
     * 修改被保险人信息
     */
    public static Observable<CommonBean<String>> deletedInsuredInfor(String id) {
        LoginCreatInsuredBean bean = new LoginCreatInsuredBean();
        bean.setId(id);
        return LoginClient.getInstance().getLoginService()
                .deletedInsuredInfor(bean)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());
    }


}
