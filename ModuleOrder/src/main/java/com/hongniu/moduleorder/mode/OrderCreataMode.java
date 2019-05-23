package com.hongniu.moduleorder.mode;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.UpImgData;
import com.hongniu.moduleorder.control.OrderCreatControl;
import com.hongniu.moduleorder.entity.OrderCarNumbean;
import com.hongniu.moduleorder.entity.OrderCreatParamBean;
import com.hongniu.moduleorder.entity.OrderDriverPhoneBean;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.luck.picture.lib.entity.LocalMedia;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.ConvertUtils;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2019/5/23.
 */
public class OrderCreataMode implements OrderCreatControl.IOrderCreataMode {
    private List<OrderCarNumbean> carNumbeans; //车牌号相关信息
    private OrderCreatParamBean paramBean ;
    private int type;//0 创建订单 1修改订单 2车货匹配订单

    public OrderCreataMode() {
        paramBean = new OrderCreatParamBean();
    }
    /**
     * 更改当前页面状态
     *
     * @param type 0 创建订单 1修改订单 2车货匹配订单
     */
    @Override
    public void saveType(int type) {
        this.type=type;
    }
    /**
     * 根据车牌号查询车辆
     *
     * @param carNum
     */
    @Override
    public Observable<CommonBean<List<OrderCarNumbean>>> queryCarInfor(String carNum) {
        return HttpOrderFactory.getCarNum(carNum);

    }

    /**
     * 根据司机名字获取司机手机号
     *
     * @param driverName 司机名称
     */
    @Override
    public Observable<CommonBean<List<OrderDriverPhoneBean>>> queryDriverInfor(String driverName) {
        return HttpOrderFactory.getDriverPhone(driverName);
    }

    /**
     * 填写完数据之后点击提交按钮
     */
    @Override
    public void submit() {

    }

    /**
     * 修改订单的时候，根据订单ID查询订单数据
     *
     * @param orderID
     */
    @Override
    public Observable<CommonBean<OrderDetailBean>> queryOrderInfor(String orderID) {
        return HttpOrderFactory.queryOrderDetail(orderID);
    }

    /**
     * 储存修改房源的原始数据
     *
     * @param orderDetailBean
     */
    @Override
    public void saveOrderInfor(OrderDetailBean orderDetailBean) {
        paramBean.setId(orderDetailBean.getId());
        paramBean.setDepartNum(orderDetailBean.getDepartNum());
        paramBean.setStartLatitude(orderDetailBean.getStartLatitude() + "");
        paramBean.setStartLongitude(orderDetailBean.getStartLongitude() + "");
        paramBean.setStartPlaceInfo(orderDetailBean.getStartPlaceInfo() + "");
        paramBean.setDestinationLatitude(orderDetailBean.getDestinationLatitude() + "");
        paramBean.setDestinationLongitude(orderDetailBean.getDestinationLongitude() + "");
        paramBean.setDestinationInfo(orderDetailBean.getDestinationInfo());
        String timeDes = orderDetailBean.getDeliveryDate();
        try {
            timeDes = ConvertUtils.formatString(orderDetailBean.getDeliveryDate(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        }
        paramBean.setDeliveryDate(timeDes);
        paramBean.setGoodName(orderDetailBean.getGoodName());
        paramBean.setGoodVolume(orderDetailBean.getGoodVolume());
        paramBean.setGoodWeight(orderDetailBean.getGoodWeight());
        paramBean.setMoney(orderDetailBean.getMoney());
        paramBean.setCarNum(orderDetailBean.getCarNum());
        paramBean.setOwnerMobile(orderDetailBean.getOwnerMobile());
        paramBean.setOwnerName(orderDetailBean.getOwnerName());
        paramBean.setDriverName(orderDetailBean.getDriverName());
        paramBean.setDriverMobile(orderDetailBean.getDriverMobile());

    }

    /**
     * 获取当前订单信息
     */
    @Override
    public OrderCreatParamBean getInfor() {
        return paramBean;
    }


}
