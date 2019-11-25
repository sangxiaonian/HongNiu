package com.hongniu.modulecargoodsmatch.utils;

import android.content.Context;

import com.hongniu.modulecargoodsmatch.R;

/**
 * 作者：  on 2019/11/2.
 */
public class MatchOrderListHelper {

    public static String CANCLE_CAR = "取消订单";
    public static String PAY = "立即付款";
    public static String CONTACT_DRIVER = "联系司机";
    public static String EVALUATE_DRIVER = "评价司机";
    public static String EVALUATE_OWNER = "评价发货人";
    public static String RECEIVE_ORDER = "我要接单";
    public static String ENTRY_ARRIVE = "确认送达";
    public static String ENTRY_RECEIVE = "确认收货";

    private int type;
    private int state;

    public MatchOrderListHelper setType(int type) {
        this.type = type;
        return this;
    }

    public MatchOrderListHelper setState(int state) {
        this.state = state;
        return this;
    }

    public String getStateDes() {
        String msg;
        switch (state) {
            case 1:
                msg = "待付款";
                break;
            case 2:
                msg = "待接单";
                break;
            case 3:
                msg = "已接单";
                break;
            case 4:
                msg = "已送达";
                break;
            case 5:
                msg = "已完成";
                break;
            case 6:
                msg = "已取消";
                break;
            case 7:
                msg = "已确认收货";
                break;
            default:
                msg = "异常";
        }
        return msg;
    }




    public int getStateColor(Context mContext) {
        int color;
        switch (state) {
            case 1:
                color = mContext.getResources().getColor(R.color.color_of_e83515);
                break;
            case 2:
                color = mContext.getResources().getColor(R.color.color_of_e83515);
                break;
            case 3:
                color = mContext.getResources().getColor(R.color.color_of_52c41a);
                break;
            case 4://"已送达"
            case 6://"已取消"
            case 5://"已完成"

                color = mContext.getResources().getColor(R.color.color_of_797c8b);

                break;

            default:
                color = mContext.getResources().getColor(R.color.color_of_797c8b);
        }
        return color;
    }


    public String getButtonWhite() {
        if (type == 1) {//货主
            return _getOwnerWhite(state);
        } else {//司机
            return _getDriverWhite(state);

        }

    }

    private String _getOwnerWhite(int state) {
        String msg = "";
        switch (state) {
            case 1://代付款
            case 2:
            case 3:
                msg = CANCLE_CAR;
                break;
            case 4:
            case 5:
            case 6:
            default:
        }
        return msg;
    }

    private String _getDriverWhite(int state) {

        return "";
    }

    public String getButtonRed(boolean appraise) {
        if (type == 1) {//货主
            return _getOwnerRed(state,appraise);
        } else {//司机
            return _getDriverRed(state,appraise);

        }

    }

    private String _getDriverRed(int state, boolean appraise) {
        String msg = "";
        switch (state) {

            case 2:
                msg = RECEIVE_ORDER;
                break;
            case 3:
                msg = ENTRY_ARRIVE;
                break;
            case 1://代付款
            case 4:
            case 5:
                break;
            case 6:
            case 7:
                msg=appraise?EVALUATE_OWNER:"";
                break;
            default:
        }
        return  msg;
    }

    private String _getOwnerRed(int state, boolean appraise) {
        String msg = "";
        //state	number	订单状态 1:待付款 2:待接单 3:已接单 4:已送达 5:已完成 6:已取消 7已确认收货

        switch (state) {
            case 1://代付款
                msg = PAY;
                break;
            case 3:
                msg = CONTACT_DRIVER;
                break;
            case 4:
                msg = ENTRY_RECEIVE;
                break;
            case 2:
            case 5:
                break;
            case 6:
            case 7:
                msg=appraise?EVALUATE_DRIVER:"";
            default:
        }
        return  msg;
    }


}
