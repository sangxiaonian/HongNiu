package com.sang.thirdlibrary.picture.helper;

import android.content.Context;
import android.text.TextUtils;

import com.fy.androidlibrary.net.rx.RxUtils;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.sang.thirdlibrary.picture.ImageInforBean;
import com.sang.thirdlibrary.picture.helper.interceptor.FileProgressRequestBody;

import org.greenrobot.eventbus.EventBus;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 图片上传的adapter
 * <p>
 * 作者： ${PING} on 2018/11/13.
 */
public abstract class ImageUpHelper {


    private Context context;

    List<ImageInforBean> images = new ArrayList<>();
    private Subscription subscription;
    Map<ImageInforBean, Disposable> disposables = new HashMap<>();

    private int type;//上传图片类型

    public ImageUpHelper(Context context) {
        this.context = context;
    }


    /**
     * 更新并上传图片
     *
     * @param datas
     */
    public void upDates(List<ImageInforBean> datas) {
        if (datas == null) {
            images.clear();
        } else {
            images = datas;
        }
        loadImages(images);

    }

    /**
     * 获取所有图片
     *
     * @return
     */
    public List<ImageInforBean> getImages() {
        return images == null ? images = new ArrayList<>() : images;
    }

    private void loadImages(final List<ImageInforBean> images) {
        if (subscription != null) {
            subscription.cancel();
        }
        Set<ImageInforBean> beans = disposables.keySet();
        for (ImageInforBean bean : beans) {
            Disposable disposable = disposables.get(bean);
            if (disposable != null) {
                disposable.dispose();
            }
            if (bean.getProgress() < 100) {
                bean.setProgress(0);
                postEvent(bean);
            }
        }
        if (CollectionUtils.isEmpty(images)) {
            return;
        }


        Flowable.fromIterable(images)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<ImageInforBean>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        subscription = s;
                        s.request(3);
                    }


                    @Override
                    public void onNext(ImageInforBean bean) {
                        upImage(bean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        for (ImageInforBean image : images) {
                            if (image.getProgress() != 100) {
                                image.setProgress(-1);
                                image.setErrorType(0);
                                postEvent(image);
                            }
                        }
                    }


                    @Override
                    public void onComplete() {
                    }
                });

    }


    public void upImage(final ImageInforBean bean) {
        creatUp(bean, new FileProgressRequestBody.ProgressListener() {
            @Override
            public void transferred(long current, long totle) {
                bean.setProgress(current * 100f / totle);
                if (bean.getProgress() > 99) {
                    bean.setProgress(99);
                }
                postEvent(bean);
            }
        }).subscribe(new Observer<ImageInforBean>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposables.put(bean, d);
                bean.setErrorType(-1);
            }

            @Override
            public void onNext(ImageInforBean bean) {
                bean.setProgress(100);
                postEvent(bean);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                bean.setErrorType(0);
                ;
                postEvent(bean);
                if (subscription != null) {
                    subscription.request(1);
                }
            }

            @Override
            public void onComplete() {
                disposables.remove(bean);
                if (subscription != null) {
                    subscription.request(1);
                }
            }
        });
    }

    private void postEvent(ImageInforBean bean) {
        EventBus.getDefault().post(bean);
    }


    private Observable<ImageInforBean> creatUp(final ImageInforBean bean, final FileProgressRequestBody.ProgressListener listener) {
        return Observable.just(bean)
                .flatMap(new Function<ImageInforBean, ObservableSource<ImageInforBean>>() {
                    @Override
                    public ObservableSource<ImageInforBean> apply(final ImageInforBean inforBean) throws Exception {
                        if (inforBean.getPath() != null && !inforBean.getPath().startsWith("http") && TextUtils.isEmpty(inforBean.getPathUrl())) {
                            //此处开始上传图
                            return Observable
                                    .just(inforBean.getPath())
                                    .flatMap(new Function<String, ObservableSource<ImageInforBean>>() {
                                        @Override
                                        public ObservableSource<ImageInforBean> apply(String s) throws Exception {
                                            return getUpClient(s, bean, listener);

                                        }
                                    });


                        } else {
                            inforBean.setProgress(100);
                            return Observable.just(inforBean);//对于网络图片
                        }
                    }
                })
                .compose(RxUtils.<ImageInforBean>getSchedulersObservableTransformer())
                ;

    }

    public abstract Observable<ImageInforBean> getUpClient(String path, ImageInforBean bean, FileProgressRequestBody.ProgressListener listener);


    /**
     * 当前完成情况
     *
     * @return 0 上传完成 大于0 尚未完成张数  小于0 失败张数
     */
    public int isFinish() {
        if (CollectionUtils.isEmpty(images)) {
            return 0;
        } else {

            List<ImageInforBean> temp = new ArrayList<>();
            List<ImageInforBean> tempFail = new ArrayList<>();

            for (ImageInforBean image : images) {
                if (image.getProgress() > -1 && image.getProgress() < 100) {
                    temp.add(image);
                } else if (image.getErrorType() > -1) {
                    tempFail.add(image);
                }
            }

            if (temp.isEmpty() && tempFail.isEmpty()) {
                return 0;
            } else if (!temp.isEmpty()) {
                return temp.size();
            } else {
                return -tempFail.size();
            }

        }
    }

    /**
     * 获取图片过小
     */
    public int getMinError() {
        List<ImageInforBean> tempFail = new ArrayList<>();
        for (ImageInforBean image : images) {
            if (image.getErrorType() == 1) {
                tempFail.add(image);
            }
        }
        return tempFail.size();

    }

    /**
     * 获取图片过小
     */
    public List<ImageInforBean> getMinErrorDates() {
        List<ImageInforBean> tempFail = new ArrayList<>();
        for (ImageInforBean image : images) {
            if (image.getErrorType() == 1) {
                tempFail.add(image);
            }
        }
        return tempFail;

    }
}
