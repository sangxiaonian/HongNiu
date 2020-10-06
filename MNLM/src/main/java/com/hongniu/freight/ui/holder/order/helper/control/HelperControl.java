package com.hongniu.freight.ui.holder.order.helper.control;

import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.config.Status;
import com.hongniu.freight.entity.OrderInfoBean;

import java.util.Map;

/**
 * 作者：  on 2020/2/8.
 */
public interface HelperControl {
    HelperControl setStatus(int status);

    /**
     * 设置是否购买保险
     * @param isInsurance true 购买 false 未购买
     * @return
     */
    HelperControl setInsurance(boolean isInsurance);

    /**
     * 设置是否有回单
     * @param hasReceiptImage true 有，false 没有回单
     * @return
     */
    HelperControl setHasReceiptImage(boolean hasReceiptImage);

    /**
     * 根据状态，获取到指定荒唐的按钮
     * @return
     */
    Map<String, Integer> getButtons(int status);

    /**
     * 获取状态
     * @return
     */
    Status getStatus();


}
