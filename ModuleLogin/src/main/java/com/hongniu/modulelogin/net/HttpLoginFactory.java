package com.hongniu.modulelogin.net;

import com.fy.androidlibrary.utils.CollectionUtils;
import com.hongniu.baselibrary.entity.CarTypeBean;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.LoginBean;
import com.hongniu.baselibrary.entity.LoginPersonInfor;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.PagerParambean;
import com.hongniu.baselibrary.entity.QueryBlankInforsBean;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulelogin.entity.LoginBlindBlankParams;
import com.hongniu.baselibrary.entity.CarInforBean;
import com.hongniu.modulelogin.entity.LoginCreatInsuredBean;
import com.hongniu.modulelogin.entity.LoginSMSParams;
import com.hongniu.baselibrary.entity.PayInforBeans;
import com.hongniu.modulelogin.entity.SetPayPassWord;
import com.fy.androidlibrary.net.rx.RxUtils;
import com.hongniu.baselibrary.entity.CommonBean;

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
    }   /**
     * 退出登录
     *
     */
    public static Observable<CommonBean<LoginBean>> loginOut() {

        return LoginClient.getInstance().getLoginService()
                .loginOut()
                .compose(RxUtils.<CommonBean<LoginBean>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 查询绑定银行卡时候，支持的银行列表
     * @return
     */
    public static Observable<CommonBean<List<QueryBlankInforsBean>>> queryBlanks() {

        return LoginClient.getInstance().getLoginService()
                .queryBlanks()
                .compose(RxUtils.<CommonBean<List<QueryBlankInforsBean>>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 绑定银行卡
     * @return
     */
    public static Observable<CommonBean<String>> bindBlanks(LoginBlindBlankParams params) {

        return LoginClient.getInstance().getLoginService()
                .bindBlank(params)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer())
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
                .map(new Function<CommonBean<LoginPersonInfor>, CommonBean<LoginPersonInfor>>() {
                    @Override
                    public CommonBean<LoginPersonInfor> apply(CommonBean<LoginPersonInfor> loginPersonInforCommonBean) throws Exception {
                        if (loginPersonInforCommonBean.getCode()==200){
                            Utils.savePersonInfor(loginPersonInforCommonBean.getData());
                        }
                        return loginPersonInforCommonBean;
                    }
                })
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
    }  /**
     * 司机实名认证
     *
     * @param infor
     */
    public static Observable<CommonBean<String>> changeDriverInfor(LoginPersonInfor infor) {

        return LoginClient.getInstance().getLoginService()
                .changeDriverInfor(infor)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 新增车辆
     */
    public static Observable<CommonBean<ResponseBody>> addCar(CarInforBean infor) {

        return LoginClient.getInstance().getLoginService()
                .addCar(infor)
                .compose(RxUtils.<CommonBean<ResponseBody>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 修改车辆
     */
    public static Observable<CommonBean<String>> upDataCar(CarInforBean infor) {

        return LoginClient.getInstance().getLoginService()
                .upDataCar(infor)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 删除车辆
     */
    public static Observable<CommonBean<String>> deletedCar(String id) {
        CarInforBean bean = new CarInforBean();
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

        return HttpAppFactory.getCarType();

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
    public static Observable<CommonBean<String>> upTruckInfor(CarInforBean bean) {

        return LoginClient.getInstance()
                .getLoginService()
                .upTruckInfor(bean)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());

    }

    /**
     * 查询货车量详细信息
     */
    public static Observable<CommonBean<CarInforBean>> queyCarDetailInfor(final String carNumber) {
        final CarInforBean bean = new CarInforBean();
        bean.setCarNumber(carNumber);
        return LoginClient.getInstance()
                .getLoginService()
                .queyCarDetailInfor(bean)
                .map(new Function<CommonBean<List<CarInforBean>>, CommonBean<CarInforBean>>() {
                    @Override
                    public CommonBean<CarInforBean> apply(CommonBean<List<CarInforBean>> listCommonBean) throws Exception {

                        CommonBean<CarInforBean> bean1 = new CommonBean<CarInforBean>();
                        bean1.setMsg(listCommonBean.getMsg());
                        bean1.setCode(listCommonBean.getCode());
                        List<CarInforBean> data = listCommonBean.getData();
                        if (!CollectionUtils.isEmpty(data)) {
                            bean1.setData(data.get(0));
                        } else {
                            CarInforBean bean2 = new CarInforBean();
                            bean2.setCarNumber(carNumber);
                            bean1.setData(bean2);
                        }
                        return bean1;
                    }
                })
                .compose(RxUtils.<CommonBean<CarInforBean>>getSchedulersObservableTransformer());

    }

    /**
     * 获取车辆类型
     */
    public static Observable<CommonBean<PageBean<CarInforBean>>> getCarList(int currentPage) {

        return HttpAppFactory.getCarList(currentPage);
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
