package com.sang.thirdlibrary.chact;

import android.app.Application;
import android.content.Context;

import com.github.barteksc.pdfviewer.util.Util;
import com.sang.common.utils.JLog;

import io.rong.callkit.util.HeadsetInfo;
import io.rong.imkit.MainActivity;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import io.rong.subscaleview.Utils;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

/**
 * 作者： ${桑小年} on 2018/11/25.
 * 努力，为梦长留
 */
public class ChactHelper {

    private static class Inner{
        private static ChactHelper helper=new ChactHelper();
    }

    public static ChactHelper getHelper(){
        return Inner.helper;
    }

    public void initHelper(Application application){
        RongIM.init(application);
    }

    public void connect(Context context,String token) {
        connect(context,token,new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {
                JLog.e("初始化失败");
            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                JLog.e("初始化成功" + userid);
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

                JLog.e("初始错误----------");
                JLog.e("初始错误" + errorCode.getValue());
            }
        });
    }

    public void connect(Context context,String token,  RongIMClient.ConnectCallback callback) {
        if (context.getApplicationInfo().packageName.equals(getCurProcessName(context.getApplicationContext()))) {
            JLog.e("开始连接服务器");
            RongIM.connect(token, callback);
        }
    }

    public void startPriver(Context context,String userID,String title){
        RongIM.getInstance().startPrivateChat(context, userID, title);
        JLog.i(userID+">>>"+title);
    }
}
