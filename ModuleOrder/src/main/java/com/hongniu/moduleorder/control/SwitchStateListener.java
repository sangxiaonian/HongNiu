package com.hongniu.moduleorder.control;

import com.hongniu.baselibrary.widget.order.RoleState;

public interface SwitchStateListener {
        boolean isShowing();

        void closePop();

        void setRoalState(RoleState state);
    }