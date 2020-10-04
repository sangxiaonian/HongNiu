package com.fy.androidlibrary.net.rx;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者： ${PING} on 2018/8/13.
 */
public class RxUtils {

    public static <T> ObservableTransformer<T, T> getSchedulersObservableTransformer() {
        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        ;
            }


        };
    }

    /**
     * RxJava2 当取消订阅后(dispose())，RxJava抛出的异常后续无法接收(此时后台线程仍在跑，可能会抛出IO等异常),全部由RxJavaPlugin接收，需要提前设置ErrorHandler
     *      * 详情：http://engineering.rallyhealth.com/mobile/rxjava/reactive/2017/03/15/migrating-to-rxjava-2.html#Error Handling
     * ————————————————
     */
    public static void setRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
    }
    /**
     * 异常重试机制
     * @param times  重试次数
     * @param delyTime 下次重试间隔时间
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> retry(final int times, final long delyTime) {
        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                            int mRetryCount;

                            @Override
                            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {

                                    @Override
                                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                                        mRetryCount++;
                                        return delyTime > 0 && mRetryCount <= times ? Observable.timer(delyTime, TimeUnit.MILLISECONDS) : Observable.error(throwable);
                                    }

                                });
                            }
                        })
                        ; }
        };

    }
}