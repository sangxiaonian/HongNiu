package com.hongniu.freight.ui.holder.order.helper;

import com.hongniu.freight.config.Status;
import com.hongniu.freight.ui.holder.order.helper.control.HelperControl;

import java.util.Map;

/**
 * 作者：  on 2020/3/8.
 */
public class BaseHelper implements HelperControl {
    protected int status=-1;
    protected boolean isInsurance;
    protected boolean hasReceiptImage;//是否有回单


    @Override
    public BaseHelper setStatus(int status) {
        this.status=status;
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
        this.isInsurance=isInsurance;
        return this;
    }
    /**
     * 设置是否有回单
     * @param hasReceiptImage true 有，false 没有回单
     * @return
     */
    public HelperControl setHasReceiptImage(boolean hasReceiptImage) {
        this.hasReceiptImage=hasReceiptImage;
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
        return null;
    }

    /**
     * 获取状态
     *
     * @return
     */
    @Override
    public Status getStatus() {
        Status result=Status.All;
        for (Status value : Status.values()) {
            if (value.getStatus()==status){
                result=value;
                break;
            }
        }
        return result;
    }
}
