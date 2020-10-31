package com.fy.companylibrary.net;

import com.fy.androidlibrary.net.error.NetException;
import com.fy.androidlibrary.net.listener.TaskControl;
import com.fy.androidlibrary.net.rx.BaseObserver;
import com.fy.companylibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;

/**
 * 作者：  on 2019/10/28.
 */
public class NetObserver<T> extends BaseObserver<CommonBean<T>> {
    public NetObserver(TaskControl.OnTaskListener listener) {
        super(listener);
    }


    @Override
    public void onNext(CommonBean<T> result) {
        super.onNext(result);
        if (result.getCode() == Param.SUCCESS_FLAG) {
            doOnSuccess(result.getData());
        } else {
            onError(new NetException(result.getCode(), result.getMsg()));
        }
    }

    public void doOnSuccess(T t) {

    }


}
