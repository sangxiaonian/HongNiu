package com.hongniu.moduleorder.control;

import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;

public interface SwitchStateListener {
    boolean isShowing();

    void closePop();

    void setRoalState(OrderDetailItemControl.RoleState state);
}