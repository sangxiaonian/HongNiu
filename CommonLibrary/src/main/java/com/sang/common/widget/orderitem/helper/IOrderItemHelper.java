package com.sang.common.widget.orderitem.helper;

import android.view.View;

/**
 * 作者： ${PING} on 2018/8/2.
 * <p>
 * 订单处理的辅助类接口，用来对货主、车主、司机不同角色的不同状态订单进行处理
 */
public interface IOrderItemHelper {


   int getLeftVisibility();
   int getRightVisibility();


    /**
     * 获取左侧按钮信息
     */
    String getBtLeftInfor();

    /**
     * 获取右侧
     *
     * @return
     */
    String getBtRightInfor();

    /**
     * 获取订单当前状态
     * @return
     */
    String getOrderState();


}
