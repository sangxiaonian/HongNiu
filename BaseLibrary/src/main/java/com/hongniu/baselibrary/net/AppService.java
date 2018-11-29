package com.hongniu.baselibrary.net;

import com.hongniu.baselibrary.entity.CarTypeBean;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.LoginPersonInfor;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.OrderIdBean;
import com.hongniu.baselibrary.entity.PayInforBeans;
import com.hongniu.baselibrary.entity.QueryOrderParamBean;
import com.hongniu.baselibrary.entity.QueryOrderStateBean;
import com.hongniu.baselibrary.entity.QueryPayPassword;
import com.hongniu.baselibrary.entity.QueryRongParams;
import com.hongniu.baselibrary.entity.RoleTypeBean;
import com.hongniu.baselibrary.entity.SMSParams;
import com.hongniu.baselibrary.entity.UpImgData;
import com.hongniu.baselibrary.entity.WalletDetail;
import com.sang.thirdlibrary.chact.UserInfor;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 作者： ${PING} on 2018/8/13.
 */
public interface AppService {


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


}


