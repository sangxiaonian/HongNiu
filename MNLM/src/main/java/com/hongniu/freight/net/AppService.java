package com.hongniu.freight.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.google.gson.JsonObject;
import com.hongniu.freight.entity.AccountDetailBean;
import com.hongniu.freight.entity.AccountFlowParams;
import com.hongniu.freight.entity.AppAddressListBean;
import com.hongniu.freight.entity.AppAddressListParam;
import com.hongniu.freight.entity.AppraiseParams;
import com.hongniu.freight.entity.BalanceWithDrawBean;
import com.hongniu.freight.entity.BillInfoBean;
import com.hongniu.freight.entity.BillInfoListBean;
import com.hongniu.freight.entity.BillInfoSearchParams;
import com.hongniu.freight.entity.BindBlankParams;
import com.hongniu.freight.entity.BuyInsuranceParams;
import com.hongniu.freight.entity.CarInfoBean;
import com.hongniu.freight.entity.CarTypeBean;
import com.hongniu.freight.entity.CargoTypeAndColorBeans;
import com.hongniu.freight.entity.FaceBackParams;
import com.hongniu.freight.entity.InsuranceInfoBean;
import com.hongniu.freight.entity.LocationBean;
import com.hongniu.freight.entity.LoginCreatInsuredBean;
import com.hongniu.freight.entity.LoginInfo;
import com.hongniu.freight.entity.OrderCrateParams;
import com.hongniu.freight.entity.OrderDispathCarParams;
import com.hongniu.freight.entity.OrderDriverPhoneBean;
import com.hongniu.freight.entity.OrderFindCarParams;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.entity.OrderNumberInfoBean;
import com.hongniu.freight.entity.OrderSelectDriverInfoBean;
import com.hongniu.freight.entity.OrderSelectOwnerInfoBean;
import com.hongniu.freight.entity.OrderStatusBean;
import com.hongniu.freight.entity.PageParams;
import com.hongniu.freight.entity.PageSearchParams;
import com.hongniu.freight.entity.PathBean;
import com.hongniu.freight.entity.PayInforBeans;
import com.hongniu.freight.entity.PersonInfor;
import com.hongniu.freight.entity.QueryAppraiseInfo;
import com.hongniu.freight.entity.QueryBindHuaInforsBean;
import com.hongniu.freight.entity.QueryBlankInforsBean;
import com.hongniu.freight.entity.QueryExpendResultBean;
import com.hongniu.freight.entity.QueryOrderListBean;
import com.hongniu.freight.entity.QueryPayInfoParams;
import com.hongniu.freight.entity.QueryReceiveBean;
import com.hongniu.freight.entity.QuerySmsParams;
import com.hongniu.freight.entity.QueryVeriBean;
import com.hongniu.freight.entity.UpImgData;
import com.hongniu.freight.entity.VerifyCompanyParams;
import com.hongniu.freight.entity.VerifyPersonParams;
import com.hongniu.freight.entity.VerifyInfoBean;
import com.hongniu.freight.entity.WayBillBean;
import com.hongniu.freight.ui.QueryInsurancePriceParams;
import com.sang.thirdlibrary.chact.UserInfor;
import com.sang.thirdlibrary.map.verify.VerifyTokenBeans;
import com.sang.thirdlibrary.pay.entiy.PayBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 作者：  on 2020/2/23.
 */
public interface AppService {

    /**
     * 发送验证码
     *
     * @param params
     * @return
     */
    @POST("api/login/getcheckcode")
    Observable<CommonBean<String>> getSmsCode(@Body QuerySmsParams params);

    /**
     * 发送验证码
     *
     * @param params
     * @return
     */
    @POST("api/login/login")
    Observable<CommonBean<LoginInfo>> login(@Body QuerySmsParams params);

    /**
     * 查询个人资料
     *
     * @param o
     * @return
     */
    @POST("api/user/finduserinfo")
    Observable<CommonBean<PersonInfor>> queryMyInfo(@Body Object o);

    /**
     * 修改头像
     *
     * @param o
     * @return
     */
    @POST("api/user/updateUserLogo")
    Observable<ResponseBody> upDateLogo(@Body JsonObject o);

    /**
     * 查询用户认证资料
     *
     * @return
     */
    @POST("api/user/finduserinfo")
    Observable<CommonBean<PersonInfor>> queryIdentityCert();

    /**
     * 获取实名认证token
     *
     * @return
     */
    @POST("api/tencentFaceIdr/getVerifyInfo")
    Observable<CommonBean<VerifyTokenBeans>> getVerifyToken();

    /**
     * 上传实名认证结果
     *
     * @param json
     * @return
     */
    @POST("api/aliRPBasic/uploadResult")
    Observable<CommonBean<Object>> uploadResult(@Body JsonObject json);

    /**
     * 个人承运人身份认证
     *
     * @param params
     * @return
     */
    @POST("api/identityAuthen/personCarrierAuth")
    Observable<CommonBean<String>> verifyCarrierPerson(@Body VerifyPersonParams params);

    /**
     * 公司承运人身份认证
     *
     * @param params
     * @return
     */
    @POST("api/identityAuthen/companyCarrierAuth")
    Observable<CommonBean<String>> verifyCarrierCompany(@Body VerifyCompanyParams params);

    /**
     * 公司托运人身份认证
     *
     * @param params
     * @return
     */
    @POST("api/identityAuthen/companyShipperAuth")
    Observable<CommonBean<String>> verifyShipperCompany(@Body VerifyCompanyParams params);

    /**
     * 个人托运人身份认证
     *
     * @param params
     * @return
     */
    @POST("api/identityAuthen/personShipperAuth")
    Observable<CommonBean<String>> verifyShipperPersonal(@Body VerifyPersonParams params);

    /**
     * 司机身份认证
     *
     * @param params
     * @return
     */
    @POST("api/identityAuthen/driverAuth")
    Observable<CommonBean<String>> verifyDriver(@Body VerifyPersonParams params);

    /**
     * 个人托运人身份认证信息
     *
     * @return
     */
    @POST("api/identityAuthen/identityDetail")
    Observable<CommonBean<VerifyInfoBean>> queryVerifyCarrierPerson();

    /**
     * 被保险人列表
     *
     * @return
     */
    @POST("api/userinsured/list")
    Observable<CommonBean<List<InsuranceInfoBean>>> queryInsuranceList();

    /**
     * 查询我的订单数量
     *
     * @return
     */
    @POST("api/deliveryOrder/myOrderNum")
    Observable<CommonBean<OrderNumberInfoBean>> queryOrderNumber();

    /**
     * 创建订单
     *
     * @return
     */
    @POST("api/deliveryOrder/add")
    Observable<CommonBean<OrderInfoBean>> createOrder(@Body OrderCrateParams params);

    /**
     * 修改订单
     *
     * @return
     */
    @POST("api/deliveryOrder/update")
    Observable<CommonBean<OrderInfoBean>> modifyOrder(@Body OrderCrateParams params);

    /**
     * 查询订单列表
     *
     * @return
     */
    @POST("api/deliveryOrder/queryPage")
    Observable<CommonBean<PageBean<OrderInfoBean>>> queryOrderList(@Body QueryOrderListBean params);

    /**
     * 查询货物分类，车辆颜色等
     *
     * @return
     * @param params
     */
    @POST("api/codeSet/getByType")
    Observable<CommonBean<List<CargoTypeAndColorBeans>>> queryCargoType(@Body JsonObject params);

    /**
     * 查询
     *
     * @return
     */
    @POST("api/deliveryOrder/platformOrderList")
    Observable<CommonBean<PageBean<OrderInfoBean>>> queryPlatformOrderList(@Body QueryOrderListBean params);

    /**
     * 查询订单详情
     *
     * @return
     */
    @POST("api/deliveryOrder/detail")
    Observable<CommonBean<OrderInfoBean>> queryOrderDetail(@Body JsonObject params);

    /**
     * 承运人查询我要接单列表
     *
     * @return
     */
    @POST("api/deliveryOrder/ownerOrderList")
    Observable<CommonBean<PageBean<OrderInfoBean>>> queryOwnerOrderList(@Body PageParams params);

    /**
     * 查询评价数据
     *
     * @return
     */
    @POST("api/deliveryOrder/searchOrderUserInfo")
    Observable<CommonBean<QueryAppraiseInfo>> queryAppraise(@Body JsonObject params);

    /**
     * 评价员工
     *
     * @return
     */
    @POST("api/deliveryOrder/evaluate")
    Observable<CommonBean<Object>> appraise(@Body AppraiseParams params);

    /**
     * 取消订单
     *
     * @return
     */
    @POST("api/deliveryOrder/cancel")
    Observable<CommonBean<Object>> orderCancel(@Body JsonObject params);

    /**
     * 平台员工接单
     *
     * @return
     */
    @POST("api/deliveryOrder/platformAcceptOrder")
    Observable<CommonBean<Object>> orderReceivePlatform(@Body JsonObject params);

    /**
     * 平台员工发布找车
     *
     * @param params
     * @return
     */
    @POST("api/deliveryOrder/findCarInfo/add")
    Observable<CommonBean<Object>> orderFindCarInfo(@Body OrderFindCarParams params);

    /**
     * 平台员工派车
     *
     * @param params
     * @return
     */
    @POST("api/deliveryOrder/dispatch")
    Observable<CommonBean<Object>> orderDispathCar(@Body OrderDispathCarParams params);

    /**
     * 获取车牌号联想
     * <p>
     * 参数名称	是否必须	数据类型	描述
     * userId	true	string	车主Id
     * carNumber	true	string	车牌号
     *
     * @return
     */
    @POST("api/user/querydriver")
    Observable<CommonBean<List<OrderDriverPhoneBean>>> getDriverPhone(@Body OrderDriverPhoneBean infor);

    /**
     * 实际承运人接单
     *
     * @param params
     * @return
     */
    @POST("api/deliveryOrder/ownerOrders")
    Observable<CommonBean<Object>> orderReceive(@Body OrderDispathCarParams params);

    /**
     * 平台运功修改订单补款金额
     *
     * @param params
     * @return
     */
    @POST("api/deliveryOrder/updateFare")
    Observable<CommonBean<Object>> updateFare(@Body JsonObject params);

    /**
     * 开始发车 立即发车
     *
     * @param params
     * @return
     */
    @POST("api/deliveryOrder/start")
    Observable<CommonBean<Object>> orderStart(@Body JsonObject params);

    /**
     * 确认到达
     *
     * @param params
     * @return
     */
    @POST("api/deliveryOrder/endSend")
    Observable<CommonBean<Object>> orderEnd(@Body JsonObject params);

    /**
     * .确认收货
     *
     * @param params
     * @return
     */
    @POST("api/deliveryOrder/receive")
    Observable<CommonBean<Object>> orderEntryReceive(@Body JsonObject params);

    /**
     * .确认收货
     *
     * @param params
     * @return
     */
    @POST("api/deliveryOrder/buyInsurance")
    Observable<CommonBean<Object>> buyInsurance(@Body BuyInsuranceParams params);

    /**
     * 更加货物金额查询保费
     *
     * @param params
     * @return
     */
    @POST("api/deliveryOrder/premiumCalculate")
    Observable<CommonBean<String>> queryInstancePrice(@Body QueryInsurancePriceParams params);

    /**
     * 查询订单状态
     *
     * @return
     */
    @POST("api/deliveryOrder/queryStatus")
    Observable<CommonBean<OrderStatusBean>> queryOrderStatus(@Body JsonObject params);


    /**
     * 上传图片
     *
     * @return
     */
    @POST("api/file/uploadFiles")
    Observable<CommonBean<List<UpImgData>>> upLoadImage(@Body MultipartBody multipartBody);

    /**
     * 我的车辆列表
     *
     * @return
     */
    @POST("api/car/selectpagecar")
    Observable<CommonBean<PageBean<CarInfoBean>>> queryCarList(@Body PageParams pageParams);

    /**
     * 全部车辆列表
     *
     * @return
     */
    @POST("api/car/selectpagecar/all")
    Observable<CommonBean<PageBean<CarInfoBean>>> queryAllCarList(@Body PageSearchParams pageParams);

    /**
     * 我的车辆列表
     *
     * @return
     */
    @POST("api/car/vehicletype")
    Observable<CommonBean<List<CarTypeBean>>> queryCarTypeList();

    /**
     * 新增修改修改车辆
     *
     * @return
     */
    @POST("api/car/savecar")
    Observable<CommonBean<Object>> createCar(@Body CarInfoBean infoBean);

    /**
     * 删除修改车辆
     *
     * @return
     */
    @POST("api/car/deletebyid")
    Observable<CommonBean<Object>> deletedCar(@Body JsonObject infoBean);

    /**
     * 查询账户数据
     *
     * @return
     */
    @POST("api/account/accountdetails")
    Observable<CommonBean<AccountDetailBean>> queryAccountDetails();

    /**
     * 查询充值账户信息
     *
     * @return
     */
    @POST("api/hxbaccount/rechargeInfo")
    Observable<CommonBean<QueryBindHuaInforsBean>> queryRechargeInfo();

    /**
     * 查看是否已经开通华夏银行子账号
     *
     * @return
     */
    @POST("api/hxbaccount/querySubAcc")
    Observable<CommonBean<QueryVeriBean>> querySubAcc();

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
    Observable<CommonBean<String>> bindBlank(@Body BindBlankParams params);

    /**
     * 获取我的付款方式
     *
     * @param o
     * @return
     */
    @POST("api/refund/queryMyCards")
    Observable<CommonBean<List<PayInforBeans>>> queryMyCards(@Body Object o);

    /**
     * 解绑支付方式
     *
     * @param blankID
     * @return
     */
    @POST("api/refund/remove")
    Observable<CommonBean<Object>> deleadCard(@Body JsonObject blankID);


    /**
     * 搜索账单
     *
     * @return
     */
    @POST("api/finance/search")
    Observable<CommonBean<BillInfoBean>> searchAccountList(@Body BillInfoSearchParams params);

    /**
     * 查询运费支出图表数据
     *
     * @return
     */
    @POST("api/finance/getExpressCost")
    Observable<CommonBean<List<QueryExpendResultBean>>> queryExpendVistogramTran(@Body BillInfoSearchParams infor);

    /**
     * 查询保费支出图表数据
     *
     * @return
     */
    @POST("api/finance/getInsuranceCost")
    Observable<CommonBean<List<QueryExpendResultBean>>> queryExpendVistogramInsurance(@Body BillInfoSearchParams infor);

    /**
     * 查询财务收入图表数据
     *
     * @return
     */
    @POST("api/finance/getExpressIncome")
    Observable<CommonBean<List<QueryExpendResultBean>>> queryInComeVistogram(@Body BillInfoSearchParams infor);


    /**
     * 查询账户数据流水信息,待入账和余额明细
     *
     * @param params
     * @return
     */
    @POST("api/account/accountflows")
    Observable<CommonBean<PageBean<BillInfoListBean>>> queryAccountFlows(@Body AccountFlowParams params);

    /**
     * 提现
     *
     * @param bean
     * @return
     */
    @POST("api/account/withdraw")
    Observable<CommonBean<String>> withdraw(@Body BalanceWithDrawBean bean);

    /**
     * 查询支付需要的数据
     *
     * @param payInfoParams
     * @return
     */
    @POST("api/account/pay")
    Observable<CommonBean<PayBean>> queryPayInfo(@Body QueryPayInfoParams payInfoParams);

    /**
     * 意见反馈
     *
     * @param payInfoParams
     * @return
     */
    @POST("api/user/insertFeedback")
    Observable<CommonBean<Object>> faceBack(@Body FaceBackParams payInfoParams);

    /**
     * 更新支付提现密码
     *
     * @param payInfoParams
     * @return
     */
    @POST("api/account/updatepass")
    Observable<CommonBean<Object>> upPass(@Body JsonObject payInfoParams);

    /**
     * 获取指定订单所有位置信息
     */
    @POST("api/position/list")
    Observable<CommonBean<PathBean>> getPath(@Body JsonObject infor);

    /**
     * 删除被保险人信息
     *
     * @param beans
     * @return
     */
    @POST("api/userinsured/delete")
    Observable<CommonBean<String>> deletedInsuredInfor(@Body LoginCreatInsuredBean beans);

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
     * 跟进userID 获取融云指定用户信息
     */
    @POST("api/user/finduserinfo")
    Observable<CommonBean<UserInfor>> queryRongInfor(@Body JsonObject params);

    /**
     * 上传所有位置信息
     */
    @POST("api/position/save")
    Observable<CommonBean<String>> upLoaction(@Body List<LocationBean> locationBeans);

    /**
     * 根据运单号查询运单信息
     *
     * @param eventParams
     * @return
     */
    @POST("api/waybill/show")
    Observable<CommonBean<List<WayBillBean>>> queryWaybill(@Body JsonObject eventParams);

    /**
     * 修改用户设备号 上传友盟token
     *
     * @return
     */
    @POST("api/user/updateDevice")
    Observable<CommonBean<Object>> upDateToken(@Body JsonObject formVals);

    /**
     * 查看回单
     *
     * @return
     */
    @POST("api/deliveryOrder/queryReceiptInfo")
    Observable<CommonBean<QueryReceiveBean>> queryReceiptInfo(@Body JsonObject formVals);

    /**
     * 保存回单
     *
     * @return
     */
    @POST("api/deliveryOrder/saveReceiptInfo")
    Observable<CommonBean<String>> saveReceiptInfo(@Body QueryReceiveBean bean);



    /**
     * 获取子平台登录token
     *
     * @return
     */
    @POST("api/login/tokenlogin")
    Observable<CommonBean<LoginInfo>> loginWithToken(@Body JsonObject object);

    /**
     * 查询司机
     *
     * @return
     */
    @POST("api/user/fuzzyquerydriver")
    Observable<CommonBean<List<OrderSelectDriverInfoBean>>> querySelectDriverInfo(@Body PageSearchParams bean);

    /**
     * 查询承运人
     *
     * @return
     */
    @POST("api/car/selectcarbycarnumber")
    Observable<CommonBean<List<OrderSelectOwnerInfoBean>>> querySelectOwnerInfo(@Body PageSearchParams bean);

    /**
     * 查询常用地址
     *
     * @return
     */
    @POST("api/address/queryAddress")
    Observable<CommonBean<PageBean<AppAddressListBean>>> queryAddressList(@Body AppAddressListParam bean);

}
