package com.hongniu.baselibrary.net;

import com.hongniu.baselibrary.entity.CarInforBean;
import com.hongniu.baselibrary.entity.CarTypeBean;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.GrapSingleInforBean;
import com.hongniu.baselibrary.entity.GrapSingleInforParams;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.OrderIdBean;
import com.hongniu.baselibrary.entity.PayParam;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.PagerParambean;
import com.hongniu.baselibrary.entity.PayInforBeans;
import com.hongniu.baselibrary.entity.QueryOrderParamBean;
import com.hongniu.baselibrary.entity.QueryOrderStateBean;
import com.hongniu.baselibrary.entity.QueryPayPassword;
import com.hongniu.baselibrary.entity.QueryRongParams;
import com.hongniu.baselibrary.entity.RoleTypeBean;
import com.hongniu.baselibrary.entity.SMSParams;
import com.hongniu.baselibrary.entity.TruckGudieSwitchBean;
import com.hongniu.baselibrary.entity.UpImgData;
import com.hongniu.baselibrary.entity.WalletDetail;
import com.sang.thirdlibrary.chact.UserInfor;
import com.sang.thirdlibrary.pay.entiy.PayBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 作者： ${PING} on 2018/8/13.
 */
public interface AppService {


    /**
     * 查询接单状态
     *
     * @return
     */
    @POST("hongniu/api/robOrder/queryRob")
    Observable<CommonBean<GrapSingleInforBean>> queryGrapSingleInfor(@Body GrapSingleInforParams params);

    /**
     * 获取车辆类型
     *
     * @return
     */
    @POST("hongniu//api/car/vehicletype")
    Observable<CommonBean<List<CarTypeBean>>> getCarType();

    /**
     * 跟进userID 获取融云指定用户信息
     */
    @POST("hongniu/api/user/finduserinfo")
    Observable<CommonBean<UserInfor>> queryRongInfor(@Body QueryRongParams params);

    /**
     * 查询订单状态
     *
     * @param bean
     * @return
     */
    @POST("hongniu/api/order/queryOrder")
    Observable<CommonBean<QueryOrderStateBean>> queryOrder(@Body OrderIdBean bean);

    /**
     * 获取用户最近使用角色
     *
     * @return
     */
    @POST("hongniu/api/user/queryUserRole")
    Observable<CommonBean<RoleTypeBean>> getRoleType();

    /**
     * 发送验证码
     *
     * @param params
     * @return
     */
    @POST("hongniu/api/login/getcheckcode")
    Observable<CommonBean<String>> getSmsCode(@Body SMSParams params);

    /**
     * 获取我的付款方式
     *
     * @return
     */
    @POST("hongniu/api/refund/queryMyCards")
    Observable<CommonBean<List<PayInforBeans>>> queryMyCards(@Body PayInforBeans beans);


    /**
     * 查询是否已经设置过支付密码
     *
     * @return
     */
    @POST("hongniu/api/account/accountdetails")
    Observable<CommonBean<QueryPayPassword>> queryPayPassword();

    /**
     * 新增付款方式
     *
     * @return
     */
    @POST("hongniu/api/refund/add")
    Observable<CommonBean<String>> addPayWays(@Body PayInforBeans beans);
    /**
     * 查询订单详情
     *
     * @param infor
     * @return
     */
    @POST("hongniu/api/order/detail")
    Observable<CommonBean<OrderDetailBean>> queryOrderDetail(@Body QueryOrderParamBean infor);

    /**
     * 查询钱包账户详情
     *
     * @return
     */
    @POST("hongniu/api/account/accountdetails")
    Observable<CommonBean<WalletDetail>> queryAccountdetails();


    @Multipart
    @POST("hongniu/api/file/uploadFiles")
    Observable<CommonBean<String>> uploadFilesWithParts(@Part("classify") int type, @Part() List<MultipartBody.Part> parts);

//    @Multipart
    @POST("hongniu/api/file/uploadFiles")
    Observable<CommonBean<List<UpImgData>>> uploadFilesWithParts(@Body MultipartBody multipartBody);

    /**
     * 查询是否开启货车导航
     * @return
     */
    @POST("hongniu/api/car/navigationSwitch")
    Observable<CommonBean<TruckGudieSwitchBean>> queryTruckGuide();
    /**
     * 获取车辆类型
     *
     * @return
     */
    @POST("hongniu/api/car/selectpagecar")
    Observable<CommonBean<PageBean<CarInforBean>>> getCarList(@Body PagerParambean parambean);

    /**
     * 微信支付
     * orderNum     true	string	订单号
     * openid       true	string	微信用户openid
     * hasFreight   true	boolean	是否付运费，true=是
     * hasPolicy    true	boolean	是否买保险，true=是
     * onlinePay    true	boolean	是否线上支付,false=线下支付
     *
     * @param infor 订单ID
     * @return
     */
    @POST("hongniu/wx/jsApiPay")
    Observable<CommonBean<PayBean>> payWeChat(@Body PayParam infor);

    /**
     * 线下支付订单
     * orderNum     true	string	订单号
     * openid       true	string	微信用户openid
     * hasFreight   true	boolean	是否付运费，true=是
     * hasPolicy    true	boolean	是否买保险，true=是
     * onlinePay    true	boolean	是否线上支付,false=线下支付
     *
     * @param infor 订单ID
     * @return
     */
    @POST("hongniu/api/account/accountpay")
    Observable<CommonBean<PayBean>> payOrderOffLine(@Body PayParam infor);

    /**
     * 银联支付
     * orderNum     true	string	订单号
     * openid       true	string	微信用户openid
     * hasFreight   true	boolean	是否付运费，true=是
     * hasPolicy    true	boolean	是否买保险，true=是
     * onlinePay    true	boolean	是否线上支付,false=线下支付
     *
     * @param infor 订单ID
     * @return
     */
    @POST("hongniu/api/unionpay/unionpaytn")
    Observable<CommonBean<PayBean>> payUnion(@Body PayParam infor);

    /**
     * 支付宝支付
     * orderNum     true	string	订单号
     * openid       true	string	微信用户openid
     * hasFreight   true	boolean	是否付运费，true=是
     * hasPolicy    true	boolean	是否买保险，true=是
     * onlinePay    true	boolean	是否线上支付,false=线下支付
     *
     * @param infor 订单ID
     * @return
     */
    @POST("hongniu/api/alipay/getorderinfo")
    Observable<CommonBean<PayBean>> payAli(@Body PayParam infor);

    /**
     * 余额支付
     * orderNum     true	string	订单号
     * openid       true	string	微信用户openid
     * hasFreight   true	boolean	是否付运费，true=是
     * hasPolicy    true	boolean	是否买保险，true=是
     * onlinePay    true	boolean	是否线上支付,false=线下支付
     *
     * @param infor 订单ID
     * @return
     */
    @POST("hongniu/api/account/accountpay")
    Observable<CommonBean<PayBean>> payBalance(@Body PayParam infor);

}


