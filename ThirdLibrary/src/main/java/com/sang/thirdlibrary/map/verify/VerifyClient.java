package com.sang.thirdlibrary.map.verify;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fy.androidlibrary.utils.JLog;
import com.webank.facelight.contants.WbCloudFaceContant;
import com.webank.facelight.contants.WbFaceError;
import com.webank.facelight.contants.WbFaceVerifyResult;
import com.webank.facelight.listerners.WbCloudFaceVeirfyLoginListner;
import com.webank.facelight.listerners.WbCloudFaceVeirfyResultListener;
import com.webank.facelight.tools.WbCloudFaceVerifySdk;
import com.webank.facelight.ui.FaceVerifyStatus;
import com.webank.mbank.wehttp.WeLog;
import com.webank.mbank.wehttp.WeOkHttp;
import com.webank.normal.net.BaseResponse;

import java.io.Serializable;


/**
 * 作者：  on 2020/3/1.
 * 实名认证
 */
public class VerifyClient {

    private String appID;// = "IDAGnuKK";
    private String secret ;//= "9gE8cd5DgLfm3oJX3aezDUUWHq96MXb8IQuPqlQUkLxXefUndP5D5xObPECzpO6y";
    private String SDKlicense;// = "pYXTl8k0mN99he+yGFM+SMWNWmbReaV1gamC88dGg65hnjzZDeQDHbkZ/uBxPk8jE1WK4BfAUrS+6a08xkvBptMW6H1zwHnVAxzd6nddDrDKvgHMl+E/YDRsN2ty6UdyQYsHRclY6iu12sVRdzIeEPBN/InzEBPzQsenQOWDEo+Q5DOPtE/i4C0KokT0x5JI9/RWVioxKp4RrLSNOArr+KVIfc/a74+yfTaMhQCyAdg2onpNSuzHSeY5egtno0QBSCLLmoxTQwUB1F/bfXngRdV9dFbbmQBpOV00KNbqGYmer+s3RvR2lkrGB48MuEJHWA3hgGRquw03ncG8zf/RZg==";
    private String TAG="VerifyClient";


    private static class InnerClass {
        private static VerifyClient client = new VerifyClient();
    }


    public static VerifyClient getInstance() {
        return InnerClass.client;
    }

    private WeOkHttp myOkHttp = new WeOkHttp();

    public VerifyClient setAppID(String appID) {
        this.appID = appID;
        return this;
    }

    public VerifyClient setSecret(String secret) {
        this.secret = secret;
        return this;

    }

    public VerifyClient setSDKlicense(String SDKlicense) {
        this.SDKlicense = SDKlicense;
        return this;

    }

    public void initClient(boolean isDebug) {

        myOkHttp.config()
                //配置超时,单位:s
                .timeout(20, 20, 20)
                //添加PIN
                .log(WeLog.Level.BODY);
    }

    public void startVerify(final Context context, VerifyTokenBeans token, final OnVerifyListener listener) {
        JLog.d("start getFaceId");

        WbCloudFaceVerifySdk.InputData inputData = new WbCloudFaceVerifySdk.InputData(
                token.getFaceId(),
                token.getOrderNo(),
                appID,
                "1.0.0",
                token.getNonceStr(),
                token.getUserID(),
                token.getSign(),
                FaceVerifyStatus.Mode.ACT,
                SDKlicense);


        Bundle data = new Bundle();
        data.putSerializable(WbCloudFaceContant.INPUT_DATA, inputData);
        //是否展示刷脸成功页面，默认展示
        data.putBoolean(WbCloudFaceContant.SHOW_SUCCESS_PAGE, false);
        //是否展示刷脸失败页面，默认展示
        data.putBoolean(WbCloudFaceContant.SHOW_FAIL_PAGE, false);
        //颜色设置
        String color = WbCloudFaceContant.WHITE;
        data.putString(WbCloudFaceContant.COLOR_MODE, color);
        //是否需要录制上传视频 默认需要
        data.putBoolean(WbCloudFaceContant.VIDEO_UPLOAD, true);
        //是否开启闭眼检测，默认不开启
        data.putBoolean(WbCloudFaceContant.ENABLE_CLOSE_EYES, true);
        //是否播放提示音，默认播放
        data.putBoolean(WbCloudFaceContant.PLAY_VOICE, true);
        //设置选择的比对类型  默认为公安网纹图片对比
        //公安网纹图片比对 WbCloudFaceVerifySdk.ID_CRAD
        //自带比对源比对  WbCloudFaceVerifySdk.SRC_IMG
        //仅活体检测  WbCloudFaceVerifySdk.NONE
        //默认公安网纹图片比对
        final String compareType = WbCloudFaceContant.ID_CARD;
        data.putString(WbCloudFaceContant.COMPARE_TYPE, compareType);
        WbCloudFaceVerifySdk.getInstance().initSdk(context, data, new WbCloudFaceVeirfyLoginListner() {
            @Override
            public void onLoginSuccess() {
                JLog.i("onLoginSuccess");
                WbCloudFaceVerifySdk.getInstance().startWbFaceVeirifySdk(context, new WbCloudFaceVeirfyResultListener() {
                    @Override
                    public void onFinish(WbFaceVerifyResult result) {
                        if (result != null) {
                            if (result.isSuccess()) {
                                JLog.d("刷脸成功! Sign=" + result.getSign() + "; liveRate=" + result.getLiveRate() +
                                        "; similarity=" + result.getSimilarity() + "userImageString=" + result.getUserImageString());
                                Toast.makeText(context, "刷脸成功", Toast.LENGTH_SHORT).show();
                                listener.onVerifySuccess();
                            } else {
                                listener.onVerifyFail();
                                WbFaceError error = result.getError();
                                if (error != null) {
                                    JLog.d("刷脸失败！domain=" + error.getDomain() + " ;code= " + error.getCode()
                                            + " ;desc=" + error.getDesc() + ";reason=" + error.getReason());
                                    if (error.getDomain().equals(WbFaceError.WBFaceErrorDomainCompareServer)) {
                                        JLog.d("对比失败，liveRate=" + result.getLiveRate() +
                                                "; similarity=" + result.getSimilarity());
                                    }
                                    Toast.makeText(context, "刷脸失败!" + error.getDesc(),
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    Log.e(TAG, "sdk返回error为空！");
                                }
                            }
                        } else {
                            listener.onVerifyFail();
                            Log.e(TAG, "sdk返回结果为空！");
                        }
                        //测试用代码
                        //不管刷脸成功失败，只要结束了，自带对比和活体检测都更新userId

                    }
                });
            }

            @Override
            public void onLoginFailed(WbFaceError error) {
                JLog.i("onLoginFailed!");
                listener.onVerifyFail();
                if (error != null) {
                    JLog.d("登录失败！domain=" + error.getDomain() + " ;code= " + error.getCode()
                            + " ;desc=" + error.getDesc() + ";reason=" + error.getReason());
                    if (error.getDomain().equals(WbFaceError.WBFaceErrorDomainParams)) {
                        Toast.makeText(context, "传入参数有误！" + error.getDesc(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "登录刷脸sdk失败！" + error.getDesc(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "sdk返回error为空！");
                }
            }
        });

    }


    public interface OnVerifyListener {
        /**
         * 认证成功
         */
        void onVerifySuccess();

        /**
         * 认证失败
         */
        void onVerifyFail();

        /**
         * 取消认证
         */
        void onVerifyCancel();
    }

    public static class Result implements Serializable {
        public String bizSeqNo;  //openApi给的业务流水号
        public String orderNo;  //合作方上送的订单号
        public String faceId;  //32位唯一标识
    }

    public static class GetFaceIdResponse extends BaseResponse<Result> {

    }
}
