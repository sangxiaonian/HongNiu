package com.sang.common.net.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者： ${PING} on 2018/8/13.
 */
public class RxUtils {

    public static  <T>ObservableTransformer<T, T> getSchedulersObservableTransformer(){
        return   new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        ;
            }


        };
    }




}
