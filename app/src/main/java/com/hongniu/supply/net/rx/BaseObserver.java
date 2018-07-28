package com.hongniu.supply.net.rx;


import com.hongniu.supply.net.error.NetException;
import com.hongniu.supply.net.listener.TaskControl;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作者： ${桑小年} on 2017/11/26.
 * 努力，为梦长留
 */

public class BaseObserver<T> implements Observer<T> {

    private TaskControl.OnTaskListener listener;

    public BaseObserver(TaskControl.OnTaskListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (listener != null) {
            listener.onTaskStart(d);
        }
    }


    @Override
    public void onNext(T result) {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (listener != null) {
            if (e instanceof NetException) {
                listener.onTaskFail(e,((NetException) e).getErrorCode(), ((NetException) e).getErrorMSg());
            }else {
                listener.onTaskFail(e,e.getLocalizedMessage(), e.getMessage());
            }
        }
    }

    @Override
    public void onComplete() {
        if (listener != null) {
            listener.onTaskSuccess();
        }
    }
}
