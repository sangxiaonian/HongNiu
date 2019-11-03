package com.hongniu.modulecargoodsmatch.mode;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.modulecargoodsmatch.control.MatchOrderDataControl;
import com.hongniu.modulecargoodsmatch.entity.MatchOrderInfoBean;
import com.hongniu.modulecargoodsmatch.net.HttpMatchFactory;

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
        } else if (status == 4 || status == 5 || status == 3) {
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
        } else if (status == 4 || status == 5 || status == 3) {
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
        return infoBean.getStatus() == 4 || infoBean.getStatus() == 5;
    }

    /**
     * 获取评分
     *
     * @return
     */
    @Override
    public int getEstimate() {
        return infoBean.getServiceScore();
    }

    /**
     * 获取评价内容
     *
     * @return
     */
    @Override
    public String getEstimateContent() {
        return infoBean.getContent();
    }

    /**
     * 是否显示司机评价
     *
     * @return
     */
    @Override
    public boolean isShowEstimate() {
        return infoBean.getIsAppraiseRecord()==1;
    }

    /**
     * 价格详情
     *
     * @return
     */
    @Override
    public String getPriceDetail() {
        return String.format("运费明细  起步价%s元*%s公里", infoBean.getStartPrice(), infoBean.getDistance());
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
        int status = infoBean.getStatus();
        return (status == 2 || status == 3)
                || (type == 0 && (status == 4 || status == 5) && !isShowEstimate());


    }

    /**
     * 获取底部按钮文案
     *
     * @return
     */
    @Override
    public String getButtonInfo() {
//        订单状态 1:待付款 2:待接单 3:已接单 4:已送达 5:已完成 6:已取消
        int status = infoBean.getStatus();
        String msg = "";
        if (status == 6 || status == 1) {
            msg = "";
        } else if (status == 2) {
            msg = "我要接单";
        } else if (status == 3) {
            msg = type == 0 ? "联系司机" : "确认送达";
        } else if (status == 4 || status == 5) {
            //已经送达，已完成 司机不能操作
            msg = type == 1 ? "" :
                    (isShowEstimate() ? "" : "评价司机");

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
        return type==0&&infoBean.getStatus()==3;
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
     *  @param rating
     * @param remark
     * @return
     */
    @Override
    public Observable<CommonBean<Object>> appraiseDrive(int rating, String remark) {
      return  HttpMatchFactory.appraiseDrive(rating,remark,infoBean.getId());
    }
}
