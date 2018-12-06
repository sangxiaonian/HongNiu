package com.hongniu.baselibrary.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 作者： ${PING} on 2018/12/6.
 */
public class TruckGudieSwitchBean {

    @SerializedName(value = "state", alternate = {"switch"})
    private boolean state;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
