package com.hongniu.baselibrary.base;

import com.sang.common.net.listener.TaskControl;
import com.sang.common.net.rx.BaseObserver;

/**
 * 作者： ${PING} on 2018/8/13.
 */
public class NetObserver<T> extends BaseObserver<T> {

    public NetObserver(TaskControl.OnTaskListener listener) {
        super(listener);
    }



}
