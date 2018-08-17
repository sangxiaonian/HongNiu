package com.hongniu.moduleorder.present;

import com.hongniu.moduleorder.control.OrderMainControl;
import com.hongniu.moduleorder.mode.OrderMainMode;

/**
 * 作者： ${PING} on 2018/8/17.
 */
public class OrderMainPresenter implements OrderMainControl.IOrderMainPresent {
    private OrderMainControl.IOrderMainView view;
    private OrderMainControl.IOrderMainMode mode;

    public OrderMainPresenter(OrderMainControl.IOrderMainView view) {
        this.view = view;
        mode=new OrderMainMode();
    }


}
