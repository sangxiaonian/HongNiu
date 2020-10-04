package com.fy.androidlibrary.net.rx;



import com.fy.androidlibrary.net.error.ExceptionEngine;
import com.fy.androidlibrary.net.error.NetException;
import com.fy.androidlibrary.net.listener.TaskControl;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作者： ${桑小年} on 2017/11/26.
 * 努力，为梦长留
 */

public class BaseObserver<T> implements Observer<T> {

    protected TaskControl.OnTaskListener listener;

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
            listener.onTaskFail(e, ExceptionEngine.handleExceptionCode(e), ExceptionEngine.handleException(e));
        }

    }

    @Override
    public void onComplete() {
        if (listener != null) {
            listener.onTaskSuccess();
        }
    }
}
