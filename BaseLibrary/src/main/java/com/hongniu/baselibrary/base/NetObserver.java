package com.hongniu.baselibrary.base;

import com.hongniu.baselibrary.entity.CommonBean;
import com.sang.common.net.error.NetException;
import com.sang.common.net.listener.TaskControl;
import com.sang.common.net.rx.BaseObserver;

/**
 * 作者： ${PING} on 2018/8/13.
 */
public abstract class NetObserver<T> extends BaseObserver<CommonBean<T>> {


    public NetObserver(TaskControl.OnTaskListener listener) {
        super(listener);
    }

    @Override
    public void onNext(CommonBean<T> result) {
        super.onNext(result);
        if (result.getCode()!=200){
            onError(new NetException(result.getCode(),result.getMsg()));
        }else {
            doOnSuccess(result.getData());
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (listener != null) {
            if (e instanceof NetException) {

                listener.onTaskFail(e,((NetException) e).getErrorCode(), ((NetException) e).getErrorMSg());
            }else {
                listener.onTaskFail(e,e.getLocalizedMessage(),"网络异常");
            }
        }

    }

    public abstract void doOnSuccess(T data);
}
