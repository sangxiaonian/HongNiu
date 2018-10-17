package com.sang.thirdlibrary.pay.ali;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.sang.common.event.BusFactory;
import com.sang.thirdlibrary.pay.control.PayControl;
import com.sang.thirdlibrary.pay.entiy.PayBean;
import com.sang.thirdlibrary.pay.entiy.PayResult;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 作者： ${PING} on 2018/10/17.
 */
public class AliPay implements PayControl.IPayClient {

    @Override
    public void pay(final Activity activity, PayBean bean) {
        Observable.just(bean)
                .map(new Function<PayBean, Map<String, String>>() {
                    @Override
                    public Map<String, String> apply(PayBean bean) throws Exception {
                        PayTask alipay = new PayTask(activity);
                        Map<String, String> result = alipay.payV2(bean.getOrderInfo(), true);
                        return result;
                    }
                })
                .subscribe(new Observer<Map<String, String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Map<String, String> stringStringMap) {
                        AliPayResult payResult = new AliPayResult(stringStringMap);

                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            BusFactory.getBus().post(new PayResult( PayResult.SUCCESS));
                        } else {
                            BusFactory.getBus().post(new  PayResult( PayResult.FAIL));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        BusFactory.getBus().post(new  PayResult( PayResult.FAIL));
                    }

                    @Override
                    public void onComplete() {

                    }
                })
        ;
    }

    @Override
    public void setDebug(boolean isDebug) {

    }
}
