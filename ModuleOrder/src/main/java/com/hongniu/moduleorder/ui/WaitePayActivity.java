package com.hongniu.moduleorder.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.ImageView;

import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.QueryOrderStateBean;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.moduleorder.R;
import com.sang.common.event.BusFactory;
import com.sang.common.event.IBus;
import com.sang.common.imgload.ImageLoader;
import com.sang.common.utils.JLog;
import com.sang.thirdlibrary.pay.ali.AliPay;
import com.sang.thirdlibrary.pay.entiy.PayBean;
import com.sang.thirdlibrary.pay.entiy.PayResult;
import com.sang.thirdlibrary.pay.unionpay.UnionPayClient;
import com.sang.thirdlibrary.pay.wechat.WeChatAppPay;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @data 2018/10/29
 * @Author PING
 * @Description 支付界面
 */
public class WaitePayActivity extends BaseActivity {
    public static final String PAYTYPE = "payType";
    public static final String PAYINFOR = "payInfor";
    public static final String ISDEUBG = "ISDEUBG";
    public static final String ORDERID = "ORDERID";
    public static final String HAVEPOLICY = "havePolicy";
    private ImageView img;
    private String orderID;
    private Subscription sub;
    private boolean havePolicy;//是否购买保险，默认是false


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waite_pay);
        setToolbarTitle("等待付款");

        initView();
        initData();
        query();
    }

    @Override
    protected void initView() {
        super.initView();
        img = findViewById(R.id.img);
        ImageLoader.getLoader().load(this, img, R.raw.listloading);
    }

    @Override
    protected void initData() {
        super.initData();
        int payType = getIntent().getIntExtra(PAYTYPE, 0);
        orderID = getIntent().getStringExtra(ORDERID);
        havePolicy = getIntent().getBooleanExtra(HAVEPOLICY,false);
        PayBean bean = getIntent().getParcelableExtra(PAYINFOR);
        boolean isDebug = getIntent().getBooleanExtra(ISDEUBG, false);
//        0微信支付 1银联支付 2线下支付 3支付宝 4余额
        switch (payType) {
            case 0://微信支付
                new WeChatAppPay().pay(this, bean);
                break;
            case 1://银联支付
                new UnionPayClient().setDebug(isDebug).pay(this, bean);
                break;
            case 3://支付宝支付
                new AliPay().setDebug(isDebug).pay(this, bean);
                break;

            case 2://线下支付
            case 5://企业支付
            case 4://余额支付

                break;
        }
    }


    //此处为接收回调的结果,由于改为查询，因此此处仅仅接受支付失败或者取消的情况
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PayResult event) {
        if (event.code!=PayResult.SUCCESS) {
            Intent mIntent = new Intent();
            mIntent.putExtra("payResult", event.code);
            mIntent.putExtra("payResultDes", event.ms);
            // 设置结果，并进行传送
            setResult(Activity.RESULT_OK, mIntent);
            finish();
        }

    }

    //此处为接收成功的情况
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PaySucess event) {
        img.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mIntent = new Intent();
                mIntent.putExtra("payResult", PayResult.SUCCESS);
                mIntent.putExtra("payResultDes", "");
                // 设置结果，并进行传送
                setResult(Activity.RESULT_OK, mIntent);
                finish();
            }
        },3000);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusFactory.getBus().unregister(this);
        if (sub != null) {
            sub.cancel();
        }
    }


    public static void startPay(Activity activity, int payType, PayBean payBean, String orderId,boolean havePolicy) {
        startPay(activity, payType, payBean, orderId,havePolicy, false);
    }

    /**
     *
     * @param activity
     * @param payType   支付方式
     * @param payBean   支付需要的数据
     * @param orderId    订单ID
     * @param havePolicy  是否购买保险
     * @param isDebug     是否是测试模式
     */
    public static void startPay(Activity activity, int payType, PayBean payBean, String orderId,boolean havePolicy, boolean isDebug) {
        Intent intent = new Intent(activity, WaitePayActivity.class);
        intent.putExtra(PAYTYPE, payType);
        intent.putExtra(PAYINFOR, payBean);
        intent.putExtra(ISDEUBG, isDebug);
        intent.putExtra(ORDERID, orderId);
        intent.putExtra(HAVEPOLICY, havePolicy);
        activity.startActivityForResult(intent, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (disposable != null) {
            disposable.dispose();
        }
        if (sub != null) {
            sub.cancel();
        }
    }


    public void query() {
        Flowable.range(0, 100)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        SystemClock.sleep(3000);
                        return integer;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        sub = s;
                        s.request(1);
                    }

                    @Override
                    public void onNext(Integer aLong) {
                        HttpAppFactory.queryOrderState(orderID)
                                .map(new Function<CommonBean<QueryOrderStateBean>, CommonBean<QueryOrderStateBean>>() {
                                    @Override
                                    public CommonBean<QueryOrderStateBean> apply(CommonBean<QueryOrderStateBean> queryOrderStateBeanCommonBean) throws Exception {
                                        return queryOrderStateBeanCommonBean ;
                                    }
                                })
                                .subscribe(new NetObserver<QueryOrderStateBean>(null) {

                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        super.onSubscribe(d);
                                        disposable = d;
                                    }

                                    @Override
                                    public void doOnSuccess(QueryOrderStateBean data) {

                                        //如果没有保险，订单状态为2，表示此事已经支付完成
                                        if (!havePolicy && data.getOrderState() == 2) {
                                            BusFactory.getBus().post(new PaySucess( ));

                                            //如果购买保险，且保险状态为已经支付
                                        } else if (havePolicy&& data.getPayPolicyState() == 1 && data.getOrderState() == 2) {
                                            BusFactory.getBus().post(new PaySucess( ));
                                        } else if (sub != null) {
                                            sub.request(1);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        super.onError(e);
                                        if (sub != null) {
                                            sub.request(1);
                                        }
                                    }
                                });

                    }

                    @Override
                    public void onError(Throwable t) {
                        if (sub != null) {
                            sub.request(1);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    public static class PaySucess implements IBus.IEvent{}

}
