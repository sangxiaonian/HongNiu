package com.hongniu.supply.net.rx;


import com.hongniu.supply.net.listener.TaskControl;

/**
 * 作者： ${桑小年} on 2017/11/26.
 * 努力，为梦长留
 */

public class NetObserver<T> extends BaseObserver<T> {


    public NetObserver(TaskControl.OnTaskListener listener) {
        super(listener);
    }
}
