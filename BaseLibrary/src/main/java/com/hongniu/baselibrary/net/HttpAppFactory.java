package com.hongniu.baselibrary.net;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.fy.androidlibrary.net.rx.RxUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.BreakbulkConsignmentInfoBean;
import com.hongniu.baselibrary.entity.CarInforBean;
import com.hongniu.baselibrary.entity.CarTypeBean;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.GrapSingleInforBean;
import com.hongniu.baselibrary.entity.GrapSingleInforParams;
import com.hongniu.baselibrary.entity.IDParams;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.OrderIdBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.PagerParambean;
import com.hongniu.baselibrary.entity.PayInforBeans;
import com.hongniu.baselibrary.entity.PayParam;
import com.hongniu.baselibrary.entity.QueryOrderParamBean;
import com.hongniu.baselibrary.entity.QueryOrderStateBean;
import com.hongniu.baselibrary.entity.QueryPayPassword;
import com.hongniu.baselibrary.entity.QueryRongParams;
import com.hongniu.baselibrary.entity.RoleTypeBean;
import com.hongniu.baselibrary.entity.SMSParams;
import com.hongniu.baselibrary.entity.TruckGudieSwitchBean;
import com.hongniu.baselibrary.entity.UpImgData;
import com.hongniu.baselibrary.entity.WalletDetail;
import com.hongniu.baselibrary.utils.Utils;
import com.sang.thirdlibrary.chact.UserInfor;
import com.sang.thirdlibrary.pay.entiy.PayBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 作者： ${PING} on 2018/8/13.
 * <p>
 * 登录使用的App
 */
public class HttpAppFactory {


    /**
     * 查询接单状态
     */
    @Deprecated
    public static Observable<CommonBean<GrapSingleInforBean>> queryGrapSingleInfor(String robid) {
        GrapSingleInforParams params = new GrapSingleInforParams();
        params.robId = robid;
        return AppClient.getInstance().getService().queryGrapSingleInfor(params)
                .compose(RxUtils.<CommonBean<GrapSingleInforBean>>getSchedulersObservableTransformer());

    }
    /**
     * 查询车货匹配订单状态
     *
     * @param orderId
     * @return
     */
    public static Observable<CommonBean<IDParams>> queryNewMatch(String orderId) {
        IDParams bean = new IDParams();
        bean.id=orderId;
        return AppClient.getInstance().getService().queryNewMatch(bean)
                .compose(RxUtils.<CommonBean<IDParams>>getSchedulersObservableTransformer())
                ;
    }
    /**
     * 获取车辆类型
     */
    public static Observable<CommonBean<List<CarTypeBean>>> getCarType() {

        return AppClient.getInstance().getService().getCarType().compose(RxUtils.<CommonBean<List<CarTypeBean>>>getSchedulersObservableTransformer());

    }

    /**
     * 获取用户最近使用角色
     */
    public static Observable<CommonBean<RoleTypeBean>> getRoleType() {
        return AppClient.getInstance().getService().getRoleType()
                .compose(RxUtils.<CommonBean<RoleTypeBean>>getSchedulersObservableTransformer());
    }

    /**
     * 查询订单状态
     *
     * @param orderId
     */
    public static Observable<CommonBean<QueryOrderStateBean>> queryOrderState(String orderId) {
        OrderIdBean bean = new OrderIdBean();
        bean.setId(orderId);
        return AppClient.getInstance().getService().queryOrder(bean)
                .compose(RxUtils.<CommonBean<QueryOrderStateBean>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 查询零担订单状态
     *
     * @param orderId
     */
    public static Observable<CommonBean<BreakbulkConsignmentInfoBean>> queryBreak(String orderId) {
        OrderIdBean bean = new OrderIdBean();
        bean.setId(orderId);
        return AppClient.getInstance().getService().queryBreak(bean)
                .compose(RxUtils.<CommonBean<BreakbulkConsignmentInfoBean>>getSchedulersObservableTransformer())
                ;
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

    }

    /**
     * 新增收款方式
     */
    public static Observable<CommonBean<String>> addWeiChat(PayInforBeans bean) {
        bean.setType(0);
        return AppClient.getInstance().getService()
                .addPayWays(bean)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());

    }

    /**
     * 查询钱包账户详情
     *
     * @return
     */
    public static Observable<CommonBean<WalletDetail>> queryAccountdetails() {
        return AppClient.getInstance().getService().queryAccountdetails()
                .map(new Function<CommonBean<WalletDetail>, CommonBean<WalletDetail>>() {
                    @Override
                    public CommonBean<WalletDetail> apply(CommonBean<WalletDetail> walletDetailCommonBean) throws Exception {
                        if (walletDetailCommonBean != null
                                && walletDetailCommonBean.getCode() == 200
                                && walletDetailCommonBean.getData() != null) {
                            Utils.setPassword(walletDetailCommonBean.getData().isSetPassWord());
                        }
                        return walletDetailCommonBean;
                    }
                })
                .compose(RxUtils.<CommonBean<WalletDetail>>getSchedulersObservableTransformer())
                ;
    }


    /**
     * 上传多张图片
     *
     * @param type
     * @param paths
     * @return
     */
    public static Observable<CommonBean<List<UpImgData>>> upImage(int type, List<String> paths) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        for (String path : paths) {
            File file = new File(path);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            builder.addFormDataPart("file", file.getName(), requestBody);
        }
        builder.addFormDataPart("classify", String.valueOf(type));

        return AppClient.getInstance()
                .getService()
                .uploadFilesWithParts(builder.build())
                .compose(RxUtils.<CommonBean<List<UpImgData>>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 查询订单详情数据
     *
     * @param orderID
     * @param ordernumber
     * @param flowid
     */
    public static Observable<CommonBean<OrderDetailBean>> queryOrderDetail(String orderID, String ordernumber, String flowid) {
        QueryOrderParamBean bean = new QueryOrderParamBean();
        bean.setId(orderID);
        bean.setOrderNum(ordernumber);
        bean.setFlowId(flowid);
        return AppClient.getInstance()
                .getService()
                .queryOrderDetail(bean)
                .map(new Function<CommonBean<OrderDetailBean>, CommonBean<OrderDetailBean>>() {
                    @Override
                    public CommonBean<OrderDetailBean> apply(CommonBean<OrderDetailBean> orderDetailBeanCommonBean) throws Exception {
                        //查询订单详情时候，使用的数据类型不同于订单列表
//                     订单列表数据   角色类似 1车主 2司机 3 货主
//                     订单详情数据   1 货主 2车主 3司机
                        if (orderDetailBeanCommonBean.getCode() == 200 && orderDetailBeanCommonBean.getData() != null) {
                            switch (orderDetailBeanCommonBean.getData().getRoleType()) {
                                case 1:
                                    orderDetailBeanCommonBean.getData().setRoleType(3);
                                    break;
                                case 2:
                                    orderDetailBeanCommonBean.getData().setRoleType(1);
                                    break;
                                case 3:
                                    orderDetailBeanCommonBean.getData().setRoleType(2);
                                    break;
                            }
                        }
                        return orderDetailBeanCommonBean;
                    }
                })
                .compose(RxUtils.<CommonBean<OrderDetailBean>>getSchedulersObservableTransformer());

    }

    /**
     * 根据userID查询用户数据
     */
    public static Observable<CommonBean<UserInfor>> queryRongInfor(String userId) {

        QueryRongParams bean = new QueryRongParams();
        bean.setUserId(userId);
        return AppClient.getInstance()
                .getService()
                .queryRongInfor(bean)
                .compose(RxUtils.<CommonBean<UserInfor>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 查询车辆导航信息是否开启
     */
    public static Observable<CommonBean<TruckGudieSwitchBean>> queryTruckGuide() {
        return AppClient.getInstance()
                .getService()
                .queryTruckGuide()
                .compose(RxUtils.<CommonBean<TruckGudieSwitchBean>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 获取车辆类型
     */
    public static Observable<CommonBean<PageBean<CarInforBean>>> getCarList(int currentPage) {

        PagerParambean bean = new PagerParambean(currentPage);
        return AppClient.getInstance().getService().getCarList(bean).compose(RxUtils.<CommonBean<PageBean<CarInforBean>>>getSchedulersObservableTransformer());

    }


    /**
     * 线下支付订单
     * <p>
     * orderNum     true	string	订单号
     * openid       true	string	微信用户openid
     * hasFreight   true	boolean	是否付运费，true=是
     * hasPolicy    true	boolean	是否买保险，true=是
     * onlinePay    true	boolean	是否线上支付,false=线下支付
     * payType      true	    int 	支付方式 0微信支付 1银联支付 2线下支付 3 支付宝
     */
    public static Observable<CommonBean<PayBean>> pay(PayParam bean) {
        //支付方式
        int payType = bean.getPayType();
        if (payType == 1) {//银联支付
            return AppClient.getInstance()
                    .getService()
                    .payUnion(bean)
                    .compose(RxUtils.<CommonBean<PayBean>>getSchedulersObservableTransformer());

        } else if (payType == 0) {//微信付款
            return AppClient.getInstance()
                    .getService()
                    .payWeChat(bean)
                    .compose(RxUtils.<CommonBean<PayBean>>getSchedulersObservableTransformer());
        } else if (payType == 3) {//支付宝
            return AppClient.getInstance()
                    .getService()
                    .payAli(bean)
                    .compose(RxUtils.<CommonBean<PayBean>>getSchedulersObservableTransformer());
        } else if (payType == 2) {//线下支付
            return AppClient.getInstance()
                    .getService()
                    .payOrderOffLine(bean)
                    .compose(RxUtils.<CommonBean<PayBean>>getSchedulersObservableTransformer());
        } else if (payType == 4) {//余额支付
            return AppClient.getInstance()
                    .getService()
                    .payBalance(bean)
                    .compose(RxUtils.<CommonBean<PayBean>>getSchedulersObservableTransformer());
        } else if (payType == 5) {//企业支付
            return AppClient.getInstance()
                    .getService()
                    .payBalance(bean)
                    .compose(RxUtils.<CommonBean<PayBean>>getSchedulersObservableTransformer());
        } else {
            return null;
        }


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

}
