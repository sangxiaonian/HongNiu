package com.sang.thirdlibrary.share.wechat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.sang.common.net.listener.TaskControl;
import com.sang.common.net.rx.RxUtils;
import com.sang.thirdlibrary.pay.PayConfig;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 作者： ${PING} on 2018/10/18.
 * 分享给微信好友
 */
public class WeChartShareSession {


    public void shareUrl(final Context activity, final String url, final String title, final String des, final Bitmap bmp){

        Observable.just(bmp)
                .map(new Function<Bitmap, byte[]>() {
                    @Override
                    public byte[] apply(Bitmap bitmap) throws Exception {
                        return bmpToByteArray(bitmap,false);
                    }
                })
                .compose(RxUtils.<byte[]>getSchedulersObservableTransformer())
                .subscribe(new Observer<byte[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(byte[] bytes) {
                        IWXAPI api = WXAPIFactory.createWXAPI(activity, PayConfig.weChatAppid);
                        WXWebpageObject webpage =new WXWebpageObject();
                        webpage.webpageUrl=url;
                        WXMediaMessage msg=new WXMediaMessage(webpage);
                        msg.title = title;
                        msg.description = des;
                        msg.thumbData= bmpToByteArray(bmp,true);
                        SendMessageToWX.Req req=new SendMessageToWX.Req();
                        req.transaction=new Random().nextLong()+"";
                        req.message=msg;
                        req.scene=SendMessageToWX.Req.WXSceneSession ;
                        api.sendReq(req);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });




    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        int i;
        int j;
        if (bmp.getHeight() > bmp.getWidth()) {
            i = bmp.getWidth();
            j = bmp.getWidth();
        } else {
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);

        while (true) {
            localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0, i, j), null);
            if (needRecycle)
                bmp.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {
                // F.out(e);
            }
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

    }
}
