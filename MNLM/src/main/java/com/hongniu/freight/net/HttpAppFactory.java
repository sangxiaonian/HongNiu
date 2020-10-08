package com.hongniu.freight.net;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.fy.androidlibrary.net.rx.RxUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.fy.companylibrary.net.CompanyClient;
import com.google.gson.JsonObject;
import com.hongniu.freight.entity.AccountDetailBean;
import com.hongniu.freight.entity.AccountFlowParams;
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
import com.hongniu.freight.entity.VerifyInfoBean;
import com.hongniu.freight.entity.VerifyPersonParams;
import com.hongniu.freight.entity.WayBillBean;
import com.hongniu.freight.ui.QueryInsurancePriceParams;
import com.hongniu.freight.utils.InfoUtils;
import com.sang.thirdlibrary.chact.UserInfor;
import com.sang.thirdlibrary.map.verify.VerifyTokenBeans;
import com.sang.thirdlibrary.pay.PayInfoBean;
import com.sang.thirdlibrary.picture.helper.interceptor.FileProgressRequestBody;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * 作者： ${PING} on 2018/8/13.
 * <p>
 * 登录使用的App
 */
public class HttpAppFactory {


    /**
     * 获取验证码
     *
     * @param phone
     * @return
     */
    public static Observable<CommonBean<String>> getSms(String phone) {
        QuerySmsParams params = new QuerySmsParams();
        params.setMobile(phone);
        params.setCode(ConvertUtils.MD5(phone + Param.key));
        return CompanyClient.getInstance().creatService(AppService.class)
                .getSmsCode(params)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());

    }

    /**
     * 获取验证码
     *
     * @param phone
     * @return
     */
    public static Observable<CommonBean<LoginInfo>> login(String phone, String sms) {
        QuerySmsParams params = new QuerySmsParams();
        params.setMobile(phone);
        params.setCheckCode(sms);
        return CompanyClient.getInstance().creatService(AppService.class)
                .login(params)
                .map(new Function<CommonBean<LoginInfo>, CommonBean<LoginInfo>>() {
                    @Override
                    public CommonBean<LoginInfo> apply(CommonBean<LoginInfo> loginInfoCommonBean) throws Exception {
                        if (loginInfoCommonBean.getCode() == Param.SUCCESS_FLAG) {
                            InfoUtils.saveLoginInfo(loginInfoCommonBean.getData());
                        }
                        return loginInfoCommonBean;
                    }
                })
                .compose(RxUtils.<CommonBean<LoginInfo>>getSchedulersObservableTransformer());

    }

    /**
     * 查询个人信息
     *
     * @return
     */
    public static Observable<CommonBean<PersonInfor>> queryMyInfo() {
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryMyInfo(new Object())
                .map(new Function<CommonBean<PersonInfor>, CommonBean<PersonInfor>>() {
                    @Override
                    public CommonBean<PersonInfor> apply(CommonBean<PersonInfor> loginInfoCommonBean) throws Exception {
                        if (loginInfoCommonBean.getCode() == Param.SUCCESS_FLAG) {
                            InfoUtils.saveMyInfo(loginInfoCommonBean.getData());
                        }
                        return loginInfoCommonBean;
                    }
                })
                .compose(RxUtils.<CommonBean<PersonInfor>>getSchedulersObservableTransformer());

    }

    /**
     * 查询个人信息
     *
     * @return
     */
    public static Observable<ResponseBody> upDateLogo(String userLogo) {
        List<String> result = new ArrayList<>();
        result.add(userLogo);
        JsonObject object = new JsonObject();
        object.addProperty("userLogo", userLogo);
        return CompanyClient.getInstance().creatService(AppService.class)
                .upDateLogo(object)
                .compose(RxUtils.<ResponseBody>getSchedulersObservableTransformer());

    }


    /**
     * 高德地图搜索PIO
     *
     * @param poiSearch
     */
    public static Observable<CommonBean<PageBean<PoiItem>>> searchPio(PoiSearch poiSearch) {
        return Observable.just(poiSearch)
                .map(new Function<PoiSearch, PoiResult>() {

                    public PoiResult apply(PoiSearch poiSearch) throws Exception {
                        return poiSearch.searchPOI();
                    }
                })
                .map(new Function<PoiResult, ArrayList<PoiItem>>() {
                    @Override
                    public ArrayList<PoiItem> apply(PoiResult poiResult) throws Exception {


                        return poiResult.getPois();
                    }
                })
                .map(new Function<ArrayList<PoiItem>, CommonBean<PageBean<PoiItem>>>() {
                    @Override
                    public CommonBean<PageBean<PoiItem>> apply(ArrayList<PoiItem> poiItems) throws Exception {
                        CommonBean<PageBean<PoiItem>> bean = new CommonBean<>();
                        PageBean<PoiItem> pageBean = new PageBean<>();
                        pageBean.setList(poiItems);
                        bean.setCode(200);
                        bean.setData(pageBean);
                        return bean;
                    }
                })
                .compose(RxUtils.<CommonBean<PageBean<PoiItem>>>getSchedulersObservableTransformer())
                ;


    }


    /**
     * 获取实名认证token
     *
     * @return 获取实名认证token
     */
    public static Observable<CommonBean<VerifyTokenBeans>> getVerifyToken() {
        return CompanyClient.getInstance().creatService(AppService.class)
                .getVerifyToken()
                .compose(RxUtils.<CommonBean<VerifyTokenBeans>>getSchedulersObservableTransformer());

    }

    /**
     * 上传实名认证结果
     *
     * @param result 返回识别结果(0 失败 1 成功)
     * @return
     */
    public static Observable<CommonBean<Object>> uploadResult(int result) {
        JsonObject json = new JsonObject();
        json.addProperty("appResult", result);
        return CompanyClient.getInstance().creatService(AppService.class)
                .uploadResult(json)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer());

    }

    /**
     * @param params
     * @return
     * @data 2020/3/1
     * @Author PING
     * @Description 个人承运人身份认证
     */
    public static Observable<CommonBean<String>> verifyCarrierPerson(VerifyPersonParams params) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .verifyCarrierPerson(params)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());
    }

    /**
     * @param params
     * @return
     * @data 2020/3/1
     * @Author PING
     * @Description 公司承运人身份认证
     */
    public static Observable<CommonBean<String>> verifyCarrierCompany(VerifyCompanyParams params) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .verifyCarrierCompany(params)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());
    }

    /**
     * @param params
     * @return
     * @data 2020/3/1
     * @Author PING
     * @Description 个人托运人身份认证
     */
    public static Observable<CommonBean<String>> verifyShipperPersonal(VerifyPersonParams params) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .verifyShipperPersonal(params)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());
    }

    /**
     * @param params
     * @return
     * @data 2020/3/1
     * @Author PING
     * @Description 个人托运人身份认证
     */
    public static Observable<CommonBean<String>> verifyShipperCompany(VerifyCompanyParams params) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .verifyShipperCompany(params)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());
    }

    /**
     * @param params
     * @return
     * @data 2020/3/1
     * @Author PING
     * @Description 司机身份认证
     */
    public static Observable<CommonBean<String>> verifyDriver(VerifyPersonParams params) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .verifyDriver(params)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());
    }

    /**
     * @return
     * @data 2020/3/1
     * @Author PING
     * @Description 个人承运人身份认证
     */
    public static Observable<CommonBean<VerifyInfoBean>> queryVerifyCarrierPerson() {
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryVerifyCarrierPerson()
                .compose(RxUtils.<CommonBean<VerifyInfoBean>>getSchedulersObservableTransformer());
    }

    /**
     * 查询被保险人列表
     *
     * @return
     */
    public static Observable<CommonBean<List<InsuranceInfoBean>>> queryInsuranceList() {
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryInsuranceList()
                .compose(RxUtils.<CommonBean<List<InsuranceInfoBean>>>getSchedulersObservableTransformer());
    }

    /**
     * 查询我的订单数量
     *
     * @return
     */
    public static Observable<CommonBean<OrderNumberInfoBean>> queryOrderNumber() {
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryOrderNumber()
                .compose(RxUtils.<CommonBean<OrderNumberInfoBean>>getSchedulersObservableTransformer());
    }

    /**
     * 创建订单
     *
     * @param param
     * @return
     */
    public static Observable<CommonBean<OrderInfoBean>> createOrder(OrderCrateParams param) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .createOrder(param)
                .compose(RxUtils.<CommonBean<OrderInfoBean>>getSchedulersObservableTransformer());
    }
   /**
     * 修改订单
     *
     * @param param
     * @return
     */
    public static Observable<CommonBean<OrderInfoBean>> modifyOrder(OrderCrateParams param) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .modifyOrder(param)
                .compose(RxUtils.<CommonBean<OrderInfoBean>>getSchedulersObservableTransformer());
    }

    /**
     * 查询订单列表
     *
     * @param param
     * @return
     */
    public static Observable<CommonBean<PageBean<OrderInfoBean>>> queryOrderList(QueryOrderListBean param) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryOrderList(param)
                .compose(RxUtils.<CommonBean<PageBean<OrderInfoBean>>>getSchedulersObservableTransformer());
    }  /**
     * 查询订单列表
     *
     * @param type 文件分类：
     * 1-业务类型代码
     * 2-车牌颜色代码
     * 4-车辆能源类型
     * 6-货物分类代码
     * @return
     */
    public static Observable<CommonBean<List<CargoTypeAndColorBeans>>> queryConfigInfoType(int type) {
        JsonObject json = new JsonObject();
        json.addProperty("type", type);
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryCargoType(json)
                .compose(RxUtils.<CommonBean<List<CargoTypeAndColorBeans>>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 查询订单列表
     *
     * @param param
     * @return
     */
    public static Observable<CommonBean<PageBean<OrderInfoBean>>> queryPlatformOrderList(QueryOrderListBean param) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryPlatformOrderList(param)
                .compose(RxUtils.<CommonBean<PageBean<OrderInfoBean>>>getSchedulersObservableTransformer());
    }

    /**
     * 查询订单详情
     *
     * @return
     */
    public static Observable<CommonBean<OrderInfoBean>> queryOrderDetail(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryOrderDetail(json)
                .compose(RxUtils.<CommonBean<OrderInfoBean>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 查询订单详情
     *
     * @return
     */
    public static Observable<CommonBean<PageBean<OrderInfoBean>>> queryOwnerOrderList(int currentPage, int pageSize) {
        PageParams json = new PageParams();
        json.setPageNum(currentPage);
        json.setPageSize(pageSize);
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryOwnerOrderList(json)
                .compose(RxUtils.<CommonBean<PageBean<OrderInfoBean>>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 查询订单详情
     *
     * @return
     */
    public static Observable<CommonBean<QueryAppraiseInfo>> queryAppraise(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryAppraise(json)
                .compose(RxUtils.<CommonBean<QueryAppraiseInfo>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 查询订单详情
     *
     * @return
     */
    public static Observable<CommonBean<Object>> appraise(AppraiseParams params) {

        return CompanyClient.getInstance().creatService(AppService.class)
                .appraise(params)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 查询订单详情
     *
     * @return
     */
    public static Observable<CommonBean<OrderInfoBean>> cancelOrder(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryOrderDetail(json)
                .compose(RxUtils.<CommonBean<OrderInfoBean>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 查询订单状态
     *
     * @return
     */
    public static Observable<CommonBean<OrderStatusBean>> queryOrderStatus(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryOrderStatus(json)
                .compose(RxUtils.<CommonBean<OrderStatusBean>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 取消订单
     *
     * @return
     */
    public static Observable<CommonBean<Object>> orderCancel(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        return CompanyClient.getInstance().creatService(AppService.class)
                .orderCancel(json)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 平台员工接单
     *
     * @return
     */
    public static Observable<CommonBean<Object>> orderReceivePlatform(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        return CompanyClient.getInstance().creatService(AppService.class)
                .orderReceivePlatform(json)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 平台员工发布找车信息
     *
     * @return
     */
    public static Observable<CommonBean<Object>> orderFindCarInfo(OrderFindCarParams params) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .orderFindCarInfo(params)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 平台员工发布找车信息
     *
     * @return
     */
    public static Observable<CommonBean<Object>> orderDispathCar(OrderDispathCarParams params) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .orderDispathCar(params)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 平台员工发布找车信息
     *
     * @return
     */
    public static Observable<CommonBean<List<OrderDriverPhoneBean>>> getDriverPhone(String name) {
        OrderDriverPhoneBean bean = new OrderDriverPhoneBean();
        bean.setDriverName(name);
        return CompanyClient.getInstance().creatService(AppService.class)
                .getDriverPhone(bean)
                .compose(RxUtils.<CommonBean<List<OrderDriverPhoneBean>>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 实际承运人接单
     *
     * @return
     */
    public static Observable<CommonBean<Object>> orderReceive(OrderDispathCarParams params) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .orderReceive(params)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 平台运功修改订单补款金额
     *
     * @return
     */
    public static Observable<CommonBean<Object>> updateFare(String id, String price) {
        JsonObject object = new JsonObject();
        object.addProperty("id", id);
        object.addProperty("balanceMoney", price);
        return CompanyClient.getInstance().creatService(AppService.class)
                .updateFare(object)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 立即发车 开始发车
     *
     * @return
     */
    public static Observable<CommonBean<Object>> orderStart(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        return CompanyClient.getInstance().creatService(AppService.class)
                .orderStart(json)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 确认到达
     *
     * @return
     */
    public static Observable<CommonBean<Object>> orderEnd(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        return CompanyClient.getInstance().creatService(AppService.class)
                .orderEnd(json)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 确认收货
     *
     * @return
     */
    public static Observable<CommonBean<Object>> orderEntryReceive(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        return CompanyClient.getInstance().creatService(AppService.class)
                .orderEntryReceive(json)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 确认购买保险
     *
     * @return
     */
    public static Observable<CommonBean<Object>> buyInsurance(BuyInsuranceParams params) {

        return CompanyClient.getInstance().creatService(AppService.class)
                .buyInsurance(params)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 根据货物价格查询保费
     *
     * @param params
     * @return
     */
    public static Observable<CommonBean<String>> queryInstancePrice(QueryInsurancePriceParams params) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryInstancePrice(params)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer())
                ;
    }


    /**
     * 上传图片
     *
     * @param type             文件分类：
     *                         1-货单
     *                         2-回单
     *                         4-logo 头像
     *                         7-企业营业执照
     *                         8-身份证图片
     *                         12-驾驶证行驶证图片
     *                         13-道路运输许可证图片
     *                         14-挂靠协议图片
     *                         15-司机从业资格证
     *                         16-意见反馈图片
     * @param path             路径
     * @param progressListener 上传进度监听
     * @return
     */
    public static Observable<UpImgData> upImage(int type, String path, FileProgressRequestBody.ProgressListener progressListener) {
        List<String> list = new ArrayList<>();
        list.add(path);
        return upImage(type, list, progressListener)
                        .map(new Function<CommonBean<List<UpImgData>>, UpImgData>() {
                            @Override
                            public UpImgData apply(CommonBean<List<UpImgData>> listCommonBean) throws Exception {
                                return listCommonBean.getData().get(0);
                            }
                        })
                ;
    }

    /**
     * 上传图片
     *
     * @param type             文件分类：
     *                         1-货单
     *                         2-回单
     *                         4-logo 头像
     *                         7-企业营业执照
     *                         8-身份证图片
     *                         12-驾驶证行驶证图片
     *                         13-道路运输许可证图片
     *                         14-挂靠协议图片
     *                         15-司机从业资格证
     *                         16-意见反馈图片
     * @param paths            路径
     * @param progressListener 上传进度监听
     * @return
     */
    public static Observable<CommonBean<List<UpImgData>>> upImage(int type, List<String> paths, FileProgressRequestBody.ProgressListener progressListener) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        for (String path : paths) {
            File file = new File(path);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            if (progressListener != null) {
                FileProgressRequestBody filePart = new FileProgressRequestBody(requestBody, progressListener);
                builder.addFormDataPart("file", file.getName(), filePart);
            } else {
                builder.addFormDataPart("file", file.getName(), requestBody);
            }
        }

        builder.addFormDataPart("classify", String.valueOf(type));
        return CompanyClient.getInstance().creatService(AppService.class)
                .upLoadImage(builder.build())

                .compose(RxUtils.<CommonBean<List<UpImgData>>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 查询我的车辆列表
     */
    public static Observable<CommonBean<PageBean<CarInfoBean>>> queryCarList(PageSearchParams param) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryCarList(param)
                .compose(RxUtils.<CommonBean<PageBean<CarInfoBean>>>getSchedulersObservableTransformer());
    }

    /**
     * 查询所有车辆列表
     */
    public static Observable<CommonBean<PageBean<CarInfoBean>>> queryAllCarList(int currentPage, String searchText) {
        PageSearchParams param = new PageSearchParams();
        param.setPageNum(currentPage);
        param.setSearchText(searchText);
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryAllCarList(param)
                .compose(RxUtils.<CommonBean<PageBean<CarInfoBean>>>getSchedulersObservableTransformer());
    }

    /**
     * 查询车辆类型
     *
     * @return
     */
    public static Observable<CommonBean<List<CarTypeBean>>> queryCarTypeList() {

        return CompanyClient.getInstance().creatService(AppService.class)
                .queryCarTypeList()
                .compose(RxUtils.<CommonBean<List<CarTypeBean>>>getSchedulersObservableTransformer());
    }

    /**
     * @data 2020/3/3
     * @Author PING
     * @Description 新增修改车辆
     */
    public static Observable<CommonBean<Object>> createCar(CarInfoBean infoBean) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .createCar(infoBean)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer());
    }

    /**
     * @data 2020/3/3
     * @Author PING
     * @Description 新增修改车辆
     */
    public static Observable<CommonBean<Object>> deletedCar(String id) {
        JsonObject object = new JsonObject();
        object.addProperty("id", id);
        return CompanyClient.getInstance().creatService(AppService.class)
                .deletedCar(object)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer());
    }

    /**
     * @return
     * @data 2020/3/3
     * @Author PING
     * @Description 查询账户详情数据
     */
    public static Observable<CommonBean<AccountDetailBean>> queryAccountDetails() {
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryAccountDetails()
                .compose(RxUtils.<CommonBean<AccountDetailBean>>getSchedulersObservableTransformer());
    }

    /**
     * @return
     * @data 2020/3/3
     * @Author PING
     * @Description 查询账户开户行信息
     */
    public static Observable<CommonBean<QueryBindHuaInforsBean>> queryRechargeInfo() {
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryRechargeInfo()
                .compose(RxUtils.<CommonBean<QueryBindHuaInforsBean>>getSchedulersObservableTransformer());
    }

    /**
     * @return
     * @data 2020/3/3
     * @Author PING
     * @Description 查看是否已经开通华夏银行子账号
     */
    public static Observable<CommonBean<QueryVeriBean>> querySubAcc() {
        return CompanyClient.getInstance().creatService(AppService.class)
                .querySubAcc()
                .compose(RxUtils.<CommonBean<QueryVeriBean>>getSchedulersObservableTransformer());
    }

    /**
     * 查询绑定银行卡时候，支持的银行列表
     *
     * @return
     */
    public static Observable<CommonBean<List<QueryBlankInforsBean>>> queryBlanks() {

        return CompanyClient.getInstance().creatService(AppService.class)
                .queryBlanks()
                .compose(RxUtils.<CommonBean<List<QueryBlankInforsBean>>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 绑定银行卡
     *
     * @return
     */
    public static Observable<CommonBean<String>> bindBlanks(BindBlankParams params) {

        return CompanyClient.getInstance().creatService(AppService.class)
                .bindBlank(params)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 查询收款方式列表
     *
     * @return
     */
    public static Observable<CommonBean<List<PayInforBeans>>> queryMyCards() {

        return CompanyClient.getInstance().creatService(AppService.class)
                .queryMyCards(new Object())
                .compose(RxUtils.<CommonBean<List<PayInforBeans>>>getSchedulersObservableTransformer());

    }

    /**
     * 解绑支付方式
     *
     * @return
     */
    public static Observable<CommonBean<Object>> deleadCard(String id) {
        JsonObject object = new JsonObject();
        object.addProperty("id", id);
        return CompanyClient.getInstance().creatService(AppService.class)
                .deleadCard(object)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer());

    }

    /**
     * @param params
     * @return
     * @data 2020/3/3
     * @Author PING
     * @Description 查询账户详情数据
     */
    public static Observable<CommonBean<BillInfoBean>> searchAccountList(BillInfoSearchParams params) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .searchAccountList(params)
                .compose(RxUtils.<CommonBean<BillInfoBean>>getSchedulersObservableTransformer());
    }

    /**
     * @param params
     * @return
     * @data 2020/3/3
     * @Author PING
     * @Description 查询账户数据流水信息, 待入账和余额明细
     */
    public static Observable<CommonBean<PageBean<BillInfoListBean>>> queryAccountFlows(AccountFlowParams params) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryAccountFlows(params)
                .compose(RxUtils.<CommonBean<PageBean<BillInfoListBean>>>getSchedulersObservableTransformer());
    }

    /**
     * 余额提现
     *
     * @param amount      提现金额
     * @param payPassword 密码
     * @param refundId    提现方式的ID
     * @return
     */
    public static Observable<CommonBean<String>> withdraw(String amount, String payPassword, String refundId) {
        BalanceWithDrawBean bean = new BalanceWithDrawBean(amount, ConvertUtils.MD5(payPassword), refundId);
        return CompanyClient.getInstance().creatService(AppService.class)
                .withdraw(bean)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 运费支出图表数据
     *
     * @return
     */
    public static Observable<CommonBean<List<QueryExpendResultBean>>> queryExpendVistogramTran(BillInfoSearchParams params) {

        return CompanyClient.getInstance().creatService(AppService.class)
                .queryExpendVistogramTran(params)
                .compose(RxUtils.<CommonBean<List<QueryExpendResultBean>>>getSchedulersObservableTransformer());
    }

    /**
     * 运费支出图表数据
     *
     * @return
     */
    public static Observable<CommonBean<List<QueryExpendResultBean>>> queryExpendVistogramInsurance(BillInfoSearchParams params) {

        return CompanyClient.getInstance().creatService(AppService.class)
                .queryExpendVistogramInsurance(params)
                .compose(RxUtils.<CommonBean<List<QueryExpendResultBean>>>getSchedulersObservableTransformer());
    }

    /**
     * 财务收入图表数据
     *
     * @return
     */
    public static Observable<CommonBean<List<QueryExpendResultBean>>> queryInComeVistogram(BillInfoSearchParams params) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryInComeVistogram(params)
                .compose(RxUtils.<CommonBean<List<QueryExpendResultBean>>>getSchedulersObservableTransformer());
    }

    /**
     * @param payInfoParams
     * @return
     * @data 2020/3/3
     * @Author PING
     * @Description 查询账户详情数据
     */
    public static Observable<CommonBean<PayInfoBean>> queryPayInfo(QueryPayInfoParams payInfoParams) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryPayInfo(payInfoParams)
                .compose(RxUtils.<CommonBean<PayInfoBean>>getSchedulersObservableTransformer());
    }

    /**
     * @param payInfoParams
     * @return
     * @data 2020/3/3
     * @Author PING
     * @Description 查询账户详情数据
     */
    public static Observable<CommonBean<Object>> faceBack(FaceBackParams payInfoParams) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .faceBack(payInfoParams)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer());
    }


    /**
     * 更新支付密码
     *
     * @param md5  密码的MD5
     * @param code 验证码
     * @return
     */
    public static Observable<CommonBean<Object>> upPassword(String md5, String code) {
        JsonObject json = new JsonObject();
        json.addProperty("newPassWord", md5);
        json.addProperty("checkCode", code);
        return CompanyClient.getInstance().creatService(AppService.class)
                .upPass(json)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer());
    }

    /**
     * 查看轨迹
     *
     * @param id
     * @return
     */
    public static Observable<CommonBean<PathBean>> getPath(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("orderId", id);
        return CompanyClient.getInstance().creatService(AppService.class)
                .getPath(json)
                .compose(RxUtils.<CommonBean<PathBean>>getSchedulersObservableTransformer());
    }

    /**
     * 删除被保险人信息
     */
    public static Observable<CommonBean<String>> deletedInsuredInfor(String id) {
        LoginCreatInsuredBean bean = new LoginCreatInsuredBean();
        bean.setId(id);
        return CompanyClient.getInstance().creatService(AppService.class)
                .deletedInsuredInfor(bean)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());
    }

    /**
     * 创建被保险人信息
     */
    public static Observable<CommonBean<LoginCreatInsuredBean>> creatInsuredInfor(LoginCreatInsuredBean bean) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .creatInsuredInfor(bean)
                .compose(RxUtils.<CommonBean<LoginCreatInsuredBean>>getSchedulersObservableTransformer());
    }

    /**
     * 修改被保险人信息
     */
    public static Observable<CommonBean<LoginCreatInsuredBean>> upInsuredInfor(LoginCreatInsuredBean bean) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .upInsuredInfor(bean)
                .compose(RxUtils.<CommonBean<LoginCreatInsuredBean>>getSchedulersObservableTransformer());
    }


    /**
     * 根据userID查询用户数据
     */
    public static Observable<CommonBean<UserInfor>> queryRongInfor(String userId) {

        JsonObject bean = new JsonObject();
        bean.addProperty("userId", userId);
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryRongInfor(bean)
                .compose(RxUtils.<CommonBean<UserInfor>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 上传位置信息
     *
     * @param datas
     * @return
     */
    public static Observable<CommonBean<String>> upLoaction(List<LocationBean> datas) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .upLoaction(datas)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 根据运单号查询运单信息
     *
     * @param wayBillNumber
     * @return
     */
    public static Observable<CommonBean<List<WayBillBean>>> queryWaybill(String wayBillNumber) {
        JsonObject array = new JsonObject();
        array.addProperty("waybillNum", wayBillNumber);
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryWaybill(array)
                .compose(RxUtils.<CommonBean<List<WayBillBean>>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 根据运单号查询运单信息
     *
     * @param token
     * @return
     */
    public static Observable<CommonBean<Object>> upDateToken(String token) {

        JsonObject json = new JsonObject();
        json.addProperty("deviceType", "android");
        json.addProperty("deviceTokens", token);
        return CompanyClient.getInstance().creatService(AppService.class)
                .upDateToken(json)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer());
    }


    /**
     * 查看回单
     * @param id
     * @return
     */
    public static Observable<CommonBean<QueryReceiveBean>> queryReceiptInfo(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("orderId", id);
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryReceiptInfo(json)
                .compose(RxUtils.<CommonBean<QueryReceiveBean>>getSchedulersObservableTransformer());
    }
    /**
     * 保存回单
     * @param bean
     * @return
     */
    public static Observable<CommonBean<String>> saveReceiptInfo(QueryReceiveBean bean) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .saveReceiptInfo(bean)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());
    }
}
