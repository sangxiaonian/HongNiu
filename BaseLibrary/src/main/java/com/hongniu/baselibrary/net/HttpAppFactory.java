package com.hongniu.baselibrary.net;

import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.OrderIdBean;
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
import com.hongniu.baselibrary.utils.Utils;
import com.sang.common.net.rx.RxUtils;
import com.sang.common.utils.ConvertUtils;
import com.sang.thirdlibrary.chact.UserInfor;

import java.io.File;
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
}
