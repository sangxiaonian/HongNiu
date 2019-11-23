package com.hongniu.modulelogin.entity;

import com.luck.picture.lib.entity.LocalMedia;

/**
 * 作者：  on 2019/11/23.
 */
public class HttpMedia {
    private int state;//0初始状态 1 成功 2失败
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
