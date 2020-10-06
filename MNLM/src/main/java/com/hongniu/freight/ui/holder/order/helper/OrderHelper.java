package com.hongniu.freight.ui.holder.order.helper;

import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.config.Status;
import com.hongniu.freight.ui.holder.order.helper.control.HelperControl;
import com.hongniu.freight.utils.Utils;

import java.util.Map;

/**
 * 作者：  on 2020/2/8.
 */
public class OrderHelper implements HelperControl {
    HelperControl helper;
    RoleOrder role;


    public OrderHelper(RoleOrder role) {
        this.role = role;
        if (role == RoleOrder.SHIPPER) {
            helper = new TYRHelper();
        } else if (role == RoleOrder.CARRIER) {
            helper = new CYRHelper();
        } else if (role == RoleOrder.DRIVER) {
            helper = new DriverHelper();
        } else if (role == RoleOrder.PLATFORM) {
            helper = new PlatformHelper();
        }
    }

    @Override
    public HelperControl setStatus(int status) {
        helper.setStatus(status);
        return this;
    }

    /**
     * 设置是否购买保险
     *
     * @param isInsurance true 购买 false 未购买
     * @return
     */
    @Override
    public HelperControl setInsurance(boolean isInsurance) {
        helper.setInsurance(isInsurance);
        return this;
    }

    /**
     * 设置是否有回单
     *
     * @param hasReceiptImage true 有，false 没有回单
     * @return
     */
    @Override
    public HelperControl setHasReceiptImage(boolean hasReceiptImage) {
        helper.setHasReceiptImage(hasReceiptImage);
        return this;
    }

    /**
     * 根据状态，获取到指定荒唐的按钮
     *
     * @param status
     * @return
     */
    @Override
    public Map<String, Integer> getButtons(int status) {
        return helper.getButtons(status);
    }

    /**
     * 获取状态
     *
     * @return
     */
    @Override
    public Status getStatus() {
        return helper.getStatus();
    }
}
