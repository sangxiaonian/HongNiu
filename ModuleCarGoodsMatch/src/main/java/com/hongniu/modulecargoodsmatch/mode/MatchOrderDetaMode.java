package com.hongniu.modulecargoodsmatch.mode;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.model.AMapCarInfo;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.modulecargoodsmatch.control.MatchOrderDataControl;
import com.hongniu.modulecargoodsmatch.entity.MatchOrderInfoBean;
import com.hongniu.modulecargoodsmatch.net.HttpMatchFactory;
import com.sang.common.utils.SharedPreferencesUtils;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者：  on 2019/11/2.
 */
public class MatchOrderDetaMode implements MatchOrderDataControl.IMatchOrderDataMode {
    private MatchOrderInfoBean infoBean;
    private int type;//0 货主 1司机

    @Override
    public void saveInfor(int type) {
        this.type = type;

    }

    @Override
    public void saveDetailInfo(MatchOrderInfoBean data) {
        this.infoBean = data;
    }

    /**
     * 查询订单详情
     *
     * @return
     */
    @Override
    public Observable<CommonBean<MatchOrderInfoBean>> queryInfo() {
        return HttpMatchFactory.queryMatchOrderDetail(infoBean.getId());
    }

    /**
     * 获取标题
     *
     * @return
     */
    @Override
    public String getTitle() {
        int status = infoBean.getStatus();
        if (status == 2 || status == 6) {
            return "订单详情";
        } else {
            return "订单号：" + infoBean.getOrderNum();
        }
    }

    /**
     * 获取订单状态
     *
     * @return
     */
    @Override
    public String getOrderState() {
        String msg;
        switch (infoBean.getStatus()) {
            case 1:
                msg = "订单待付款";
                break;
            case 2://待接单
                msg = "待司机接单";
                break;
            case 3://已接单
                msg = "待司机送达";
                break;
            case 4://已送达
                msg = "货物已送达";
                break;
            case 5://已完成
                msg = "订单已完成";
                break;
            case 6://已取消
                msg = "已取消找车";
                break;
            case 7://已取消
                msg = "已确认收货";
                break;
            default:
                msg = "异常";
        }
        return msg;
    }

    /**
     * 获取取货时间
     *
     * @return
     */
    @Override
    public String getPickupTime() {
        int status = infoBean.getStatus();
        String msg = "待定";
        if (status == 1) {
            //代付款不能进详情页
            return "";
        } else if (status == 2) {
            //待接单
            msg = "发货人正在等待接单....";
        } else if (status == 6) {
            msg = "您已取消找车";
        } else if (status == 4 || status == 5 || status == 3 || status == 7) {
            msg = "预约取货时间：" + infoBean.getDepartureTime();
        }
        return msg;
    }

    /**
     * 车辆信息
     *
     * @return
     */
    @Override
    public String getCarInfo() {
        int status = infoBean.getStatus();
        String msg = "配送车辆：";
        if (status == 1) {
            //代付款不能进详情页
            return "";
        } else if (status == 2) {
            //待接单
            msg = "配送车辆：" + infoBean.getCartypeName();
        } else if (status == 6) {
            msg = "配送车辆：" + infoBean.getCartypeName();
        } else if (status == 4 || status == 5 || status == 3 || status == 7) {
            msg = String.format("配送车辆：%s（%s  %s）", infoBean.getCartypeName(), infoBean.getDriverName(), infoBean.getDriverMobile());
        }
        return msg;
    }

    /**
     * 送达时间
     *
     * @return
     */
    @Override
    public String getArriveTime() {
        int status = infoBean.getStatus();
        String msg = "";
        if (status == 4 || status == 5) {
            msg = "送达时间：" + infoBean.getDeliveryTime();
        }
        return msg;
    }

    /**
     * 获取订单信息
     *
     * @return
     */
    @Override
    public MatchOrderInfoBean getInfo() {
        return infoBean;
    }

    /**
     * 是否显示收货、发货人信息
     *
     * @return
     */
    @Override
    public boolean isShowContact() {
        return infoBean.getStatus() != 1 && infoBean.getStatus() != 2 && infoBean.getStatus() != 6;
    }

    /**
     * 获取送达凭证图片
     *
     * @return
     */
    @Override
    public List<String> getArriveVoucherImages() {
        return infoBean.getImageUrls();
    }

    /**
     * 获取是否显示送达功能
     *
     * @return true 显示
     */
    @Override
    public boolean isShowArriveVoucher() {
        return infoBean.getStatus() == 4 || infoBean.getStatus() == 5|| infoBean.getStatus() == 7;
    }

    /**
     * 获取评分
     *
     * @return
     */
    @Override
    public int getEstimateDriver() {
        return infoBean.getServiceScore();
    }

    /**
     * 获取评价内容
     *
     * @return
     */
    @Override
    public String getEstimateContentDriver() {
        return infoBean.getContent();
    }

    /**
     * 是否显示司机评价
     *
     * @return
     */
    @Override
    public boolean isShowEstimateDriver() {
        return infoBean.getIsAppraiseRecord() == 1;
    }

    /**
     * 价格详情
     *
     * @return
     */
    @Override
    public String getPriceDetail() {
        return String.format("运费明细  %s", infoBean.getMileageFareSumInfo());
    }

    /**
     * 获取运费
     *
     * @return
     */
    @Override
    public String getPrice() {
        return String.format("运费总计  %s元", infoBean.getEstimateFare());
    }

    /**
     * 是否显示底部按钮
     *
     * @return
     */
    @Override
    public boolean isShowButton() {

//        订单状态 1:待付款 2:待接单 3:已接单 4:已送达 5:已完成 6:已取消 7 已确认收货
        int status = infoBean.getStatus();
        return (status == 2 || status == 3)
                || (type == 0 && (status == 4)
                || (infoBean.isAppraise() == 0 && (status == 6 || status == 7)));


    }

    /**
     * 获取底部按钮文案
     *
     * @return
     */
    @Override
    public String getButtonInfo() {
//        订单状态 1:待付款 2:待接单 3:已接单 4:已送达 5:已完成 6:已取消 7 已确认收货
        int status = infoBean.getStatus();
        String msg = "";
        if (status == 1) {
            msg = "";
        } else if (status == 2) {
            msg = "我要接单";
        } else if (status == 3) {
            msg = type == 0 ? "联系司机" : "确认送达";
        } else if (status == 4) {
            //已经送达，已完成 司机不能操作
            msg = type == 1 ? "" :
                    ("确认收货");
        } else if (status == 6 || status == 7) {
            //已经送达，已完成 司机不能操作
            msg = infoBean.isAppraise() == 0 ? (type == 0 ? "评价司机" : "评价发货人") : "";

        } else {
            msg = "";
        }
        return msg;
    }

    /**
     * 显示底部按钮上面的电话图标
     *
     * @return
     */
    @Override
    public boolean isShowBtCall() {
        return type == 0 && infoBean.getStatus() == 3;
    }

    /**
     * 获取当前角色类型
     *
     * @return //0 货主 1司机
     */
    @Override
    public int getType() {
        return type;
    }

    /**
     * 评价司机
     *
     * @param rating
     * @param remark
     * @return
     */
    @Override
    public Observable<CommonBean<Object>> appraiseDrive(int rating, String remark) {
        return HttpMatchFactory.appraiseDrive(rating, remark, infoBean.getId());
    }

    /**
     * 获取是否是查看路线
     *
     * @return
     */
    @Override
    public boolean getShowRoute() {
        return !SharedPreferencesUtils.getInstance().getBoolean(Param.CANTRUCK);
    }

    /**
     * 获取起点位置
     *
     * @return
     */
    @Override
    public Poi getStartPoi() {
        Poi start = new Poi(infoBean.getStartPlaceInfo(), new LatLng(infoBean.getStartPlaceLat(), infoBean.getStartPlaceLon()), null);
        return start;
    }

    /**
     * 获取终点位置
     *
     * @return
     */
    @Override
    public Poi getEndPoi() {
        Poi end = new Poi(infoBean.getDestinationInfo(), new LatLng(infoBean.getDestinationLat(), infoBean.getDestinationLon()), null);

        return end;
    }

    /**
     * 获取火车导航信息
     *
     * @return
     */
    @Override
    public AMapCarInfo getGuideCarInfo() {
        AMapCarInfo carInfo = new AMapCarInfo();
        carInfo.setCarNumber(infoBean.getPlateNum());
//        approvedLoad	true	string	行驶证核定载质量
//        plateNum	true	string	行驶证车牌
//        grossMass	true	string	行驶证总质量
//        vehicleType	true	string	行驶证车辆类型
//        overallDimension	true	string	行驶证外廓尺寸
//        carLength	true	string	车长(单位米)
//                carWidth	true	string	车宽
//        carHeight	true	string	车高
//        vehicleAxleNumber	true	string	carInfo

        carInfo.setCarType(infoBean.getVehicleType());//设置车辆类型，0小车，1货车
        carInfo.setCarNumber(infoBean.getPlateNum());//设置车辆的车牌号码. 如:京DFZ239,京ABZ239
//                            aMapCarInfo.setVehicleSize("4");// * 设置货车的等级
        carInfo.setVehicleLoad(infoBean.getApprovedLoad());//设置货车的载重，单位：吨。
//        carInfo.setVehicleWeight(vaule.getVehicleLoad());//设置货车的自重
        carInfo.setVehicleLength(infoBean.getCarLength());//  * 设置货车的最大长度，单位：米。
        carInfo.setVehicleWidth(infoBean.getCarWidth());//设置货车的最大宽度，单位：米。 如:1.8，1.5等等。
        carInfo.setVehicleHeight(infoBean.getCarHeight());//设置货车的高度，单位：米。
        carInfo.setVehicleAxis(infoBean.getVehicleAxleNumber());//设置货车的轴数
        carInfo.setVehicleLoadSwitch(true);//设置车辆的载重是否参与算路
        carInfo.setRestriction(true);//设置是否躲避车辆限行。
        return carInfo;
    }

    @Override
    public String getEstimateContentOwner() {

        return infoBean.getDriverContent();
    }

    @Override
    public int getEstimateOwner() {
        return infoBean.getDriverServiceScore();
    }

    @Override
    public boolean isShowEstimateOwner() {
        return infoBean.getIsAppraiseDriver() == 1;
    }
}
