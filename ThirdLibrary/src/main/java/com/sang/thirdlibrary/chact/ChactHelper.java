package com.sang.thirdlibrary.chact;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.LruCache;

import com.fy.androidlibrary.net.rx.BaseObserver;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.utils.JLog;
import com.sang.thirdlibrary.chact.control.ChactControl;
import com.sang.thirdlibrary.chact.control.OnGetUserInforListener;

import java.util.Locale;
import java.util.Objects;

import io.rong.common.RLog;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

/**
 * 作者： ${桑小年} on 2018/11/25.
 * 努力，为梦长留
 */
public class ChactHelper {

    OnGetUserInforListener listener;
    ChactControl.OnReceiveUnReadCountListener unReadCountListener;
    private String ownerID;


    private static class Inner {
        private static ChactHelper helper = new ChactHelper();
    }

    public static ChactHelper getHelper() {
        return Inner.helper;
    }

    /**
     * 初始化数据
     *
     * @param application
     */
    public ChactHelper initHelper(Application application,String key) {
        String deviceBrand = DeviceUtils.getDeviceBrand();
        if (application.getApplicationInfo().packageName.equals(getCurProcessName(application))) {
//            if (deviceBrand.equalsIgnoreCase("Xiaomi")){
//                //小米推送配置
//                RongPushClient.registerMiPush(application, "2882303761517871354", "5731787151354");
//            }else if (deviceBrand.equalsIgnoreCase("huawei")){
//                //华为配置
//                RongPushClient.registerHWPush(application);
//            }
            RongIM.init(application,key);
            RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                @Override
                public UserInfo getUserInfo(final String s) {
                    if (listener != null) {
                        listener.onGetUserInfor(s)
                                .subscribe(new BaseObserver<UserInfor>(null) {
                                    @Override
                                    public void onNext(UserInfor infor) {
                                        super.onNext(infor);
                                        refreshUserInfoCache(s, infor);
                                    }
                                });
                    }
                    return null;
                }
            }, true);


            RongIM.getInstance().addUnReadMessageCountChangedObserver(new IUnReadMessageObserver() {
                @Override
                public void onCountChanged(int i) {
                    if (unReadCountListener != null) {
                        unReadCountListener.onReceiveUnRead(i);
                    }
                }
            }, Conversation.ConversationType.PRIVATE);
        }
        return this;
    }

    /**
     * 连接融云服务器
     *
     * @param context
     * @param token
     */
    public void connect(Context context, String token) {
        connect(context, token, new RongIMClient.ConnectCallback() {


            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                JLog.e("初始化成功" + userid);
            }

            @Override
            public void onError(RongIMClient.ConnectionErrorCode connectionErrorCode) {
                JLog.e("初始化失败");
                JLog.e("初始错误" + connectionErrorCode.getValue());

            }

            @Override
            public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {

            }


        });
    }

    public void connect(final Context context, final String token, final RongIMClient.ConnectCallback callback) {


        if (context.getApplicationInfo().packageName.equals(getCurProcessName(context.getApplicationContext()))) {
            ownerID = null;
            JLog.e("开始连接服务器");
            RongIM.connect(token, new RongIMClient.ConnectCallback() {



                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    ownerID = userid;
                    if (callback != null) {
                        callback.onSuccess(userid);
                    }
                    JLog.e("初始化成功" + userid);
                }

                @Override
                public void onError(RongIMClient.ConnectionErrorCode connectionErrorCode) {
                    JLog.e("初始化失败");

                    if (callback != null) {
                        callback.onError(connectionErrorCode);
                    }

                    if (connectionErrorCode.getValue() == 31010) {//不是异地登录
                        ToastUtils.getInstance().show("异地登录");
                    }
                    JLog.e("初始错误" + connectionErrorCode.getValue());
                }

                @Override
                public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {
                    callback.onDatabaseOpened(databaseOpenStatus);
                }

            });
        }
    }

    public void disConnect() {
        try {
            RongIM.getInstance().logout();
            RongIM.getInstance().disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断当前是否处于断开连接状态
     * @return true 断开连接
     */
    public boolean disConnectState(){
        return !RongIM.getInstance().getCurrentConnectionStatus().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED);
    }


    /**
     * 开启器单聊
     *
     * @param context
     * @param userID
     * @param title
     */
    public void startPriver(Context context, String userID, String title) {
        if (Objects.equals(userID,ownerID) ) {
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("您不能和自己对话");
        } else {
            if (userID!=null){
                startPrivateChat(context, userID, title == null ? "聊天" : title, context.getApplicationInfo().packageName);

            }else {
                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("用户ID为空");
            }
        }

    }

    public void startPrivateChat(Context context, String targetUserId, String title,String packageName) {
        if (context != null && !TextUtils.isEmpty(targetUserId)) {
            if (RongContext.getInstance() == null) {
                JLog.e( "startPrivateChat. RongIM SDK not init, please do after init.");
            } else {
                if (TextUtils.isEmpty(packageName)) {
                    packageName = context.getApplicationInfo().packageName;
                }
                Uri uri = Uri.parse("rong://" + packageName).buildUpon().appendPath("conversation").appendPath(Conversation.ConversationType.PRIVATE.getName().toLowerCase(Locale.US)).appendQueryParameter("targetId", targetUserId).appendQueryParameter("title", title).build();
                Intent intent = new Intent("android.intent.action.VIEW", uri);
                intent.setPackage(packageName);
                context.startActivity(intent);
            }
        } else {
            JLog.e(  "startPrivateChat. context or targetUserId can not be empty!!!");
        }
    }




    /**
     * 设置获取用户信息
     */
    public ChactHelper setUseInfor(final OnGetUserInforListener listener) {
        this.listener = listener;
        return this;

    }


    public ChactHelper setUnReadCountListener(ChactControl.OnReceiveUnReadCountListener unReadCountListener) {
        this.unReadCountListener = unReadCountListener;
        return this;
    }

    /**
     * 刷新用户头像信息
     *
     * @param userID
     * @param infor
     */
    public void refreshUserInfoCache(String userID, UserInfor infor) {
        if (infor==null){
            return;
        }
        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(infor.getContact())) {
            builder.append(infor.getContact()).append("\t\t");

        }
        builder.append(infor.getMobile());
        final String name = builder.toString();
        final Uri head = TextUtils.isEmpty(infor.getLogoPath()) ? null : Uri.parse(infor.getLogoPath());
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(userID, name, head));
    }


    public void put(String userId, UserInfor infor) {
        cache.put(userId, infor);

        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(infor.getContact())) {
            builder.append(infor.getContact()).append("\t\t");

        }
        builder.append(infor.getMobile());


        final String name = builder.toString();
        final Uri head = TextUtils.isEmpty(infor.getLogoPath()) ? null : Uri.parse(infor.getLogoPath());
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(userId, name, head));
    }

    private LruCache<String, UserInfor> cache = new LruCache<>(100);


}
