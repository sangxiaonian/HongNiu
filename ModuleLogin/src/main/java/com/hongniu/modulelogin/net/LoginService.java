package com.hongniu.modulelogin.net;

import com.hongniu.baselibrary.entity.CarTypeBean;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.LoginBean;
import com.hongniu.baselibrary.entity.LoginPersonInfor;
import com.hongniu.baselibrary.entity.QueryBlankInforsBean;
import com.hongniu.baselibrary.entity.QueryRongParams;
import com.hongniu.modulelogin.entity.LoginBlindBlankParams;
import com.hongniu.baselibrary.entity.CarInforBean;
import com.hongniu.modulelogin.entity.LoginCreatInsuredBean;
import com.hongniu.modulelogin.entity.LoginSMSParams;
import com.hongniu.baselibrary.entity.PayInforBeans;
import com.hongniu.modulelogin.entity.SetPayPassWord;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 作者： ${PING} on 2018/8/13.
 */
public interface LoginService {


    @POST("api/login/login")
    Observable<CommonBean<LoginBean>> loginBySms(@Body LoginSMSParams params);

    @POST("api/user/appLogout")
    Observable<CommonBean<LoginBean>> loginOut( );

    /**
     * 查询绑定银行卡时候支持的银行卡列表
     *
     * @return
     */
    @POST("api/refund/bankList")
    Observable<CommonBean<List<QueryBlankInforsBean>>> queryBlanks();

    /**
     * 绑定银行卡
     *
     * @return
     */
    @POST("api/refund/add")
    Observable<CommonBean<String>> bindBlank(@Body LoginBlindBlankParams params);

    @POST("api/login/ckeckcode")
    Observable<CommonBean<String>> ckeckcode(@Body LoginSMSParams params);

    /**
     * 获取个人信息
     */
    @POST("api/user/finduserinfo")
    Observable<CommonBean<LoginPersonInfor>> getPersonInfor(@Body QueryRongParams params);

    /**
     * 更改个人信息,即为实名认证
     */
    @POST("api/hxbaccount/certification")
    Observable<CommonBean<String>> changePersonInfor(@Body LoginPersonInfor infor);
 /**
     * 司机实名认证
     */
    @POST("api/hxbaccount/certificationDriver")
    Observable<CommonBean<String>> changeDriverInfor(@Body LoginPersonInfor infor);

    /**
     * 新增车辆信息
     */
    @POST("api/car/savecar")
    Observable<CommonBean<ResponseBody>> addCar(@Body CarInforBean infor);

    /**
     * 修改车辆信息
     */
    @POST("api/car/updatecar")
    Observable<CommonBean<String>> upDataCar(@Body CarInforBean infor);

    /**
     * 删除车辆信息
     */
    @POST("api/car/deletebyid")
    Observable<CommonBean<String>> deletedCar(@Body CarInforBean infor);


    /**
     * 根据车牌号查询车辆详情信息
     *
     * @return
     */
    @POST("api/car/querynumber")
    Observable<CommonBean<List<CarInforBean>>> queyCarDetailInfor(@Body CarInforBean parambean);

    /**
     * 获取货车导航车辆类型
     *
     * @return
     */
    @POST("api/car/vehicletype")
    Observable<CommonBean<List<CarTypeBean>>> queyTruckTypes();

    /**
     * 修改货车导航信息
     *
     * @return
     */
    @POST("api/car/updatenavigationcar")
    Observable<CommonBean<String>> upTruckInfor(@Body CarInforBean bean);

    /**
     * 获取我的付款方式
     *
     * @return
     */
    @POST("api/refund/queryMyCards")
    Observable<CommonBean<List<PayInforBeans>>> queryMyPayInforList(@Body PayInforBeans beans);

    /**
     * 更改默认收款方式
     *
     * @return
     */
    @POST("api/refund/modifyDefault")
    Observable<CommonBean<String>> changeDefaultPayWay(@Body PayInforBeans beans);

    /**
     * 新增银行卡付款方式
     *
     * @return
     */
    @POST("api/refund/add")
    Observable<CommonBean<String>> addBlankCard(@Body PayInforBeans beans);

    /**
     * 设置/更新支付密码
     *
     * @param beans
     * @return
     */
    @POST("api/account/updatepass")
    Observable<CommonBean<String>> setPayPassword(@Body SetPayPassWord beans);

    /**
     * 新增被保险人信息
     *
     * @param beans
     * @return
     */
    @POST("api/userinsured/add")
    Observable<CommonBean<LoginCreatInsuredBean>> creatInsuredInfor(@Body LoginCreatInsuredBean beans);


    /**
     * 修改被保险人信息
     *
     * @param beans
     * @return
     */
    @POST("api/userinsured/update")
    Observable<CommonBean<LoginCreatInsuredBean>> upInsuredInfor(@Body LoginCreatInsuredBean beans);

    /**
     * 删除被保险人信息
     *
     * @param beans
     * @return
     */
    @POST("api/userinsured/delete")
    Observable<CommonBean<String>> deletedInsuredInfor(@Body LoginCreatInsuredBean beans);


}


