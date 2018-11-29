package com.sang.thirdlibrary.chact;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.LruCache;

import com.sang.common.net.rx.BaseObserver;
import com.sang.common.utils.JLog;
import com.sang.thirdlibrary.chact.control.ChactControl;
import com.sang.thirdlibrary.chact.control.OnGetUserInforListener;

import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

/**
 * 作者： ${桑小年} on 2018/11/25.
 * 努力，为梦长留
 */
public class ChactHelper {

    OnGetUserInforListener listener;
    ChactControl.OnReceiveUnReadCountListener unReadCountListener;



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
    public ChactHelper initHelper(Application application) {
        RongIM.init(application);

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
                if (unReadCountListener!=null){
                    unReadCountListener.onReceiveUnRead(i);
                }
            }
        }, Conversation.ConversationType.PRIVATE);
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

    public void connect(Context context, String token, RongIMClient.ConnectCallback callback) {
        if (context.getApplicationInfo().packageName.equals(getCurProcessName(context.getApplicationContext()))) {
            JLog.e("开始连接服务器");
            RongIM.connect(token, callback);
        }
    }

    /**
     * 开启器单聊
     *
     * @param context
     * @param userID
     * @param title
     */
    public void startPriver(Context context, String userID, String title) {
        RongIM.getInstance().startPrivateChat(context, userID, title);

    }

    /**
     *     设置获取用户信息
     */
    public ChactHelper setUseInfor(final OnGetUserInforListener listener) {
        this.listener = listener;
        return this;
//        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
//            @Override
//            public UserInfo getUserInfo(final String s) {
//                if (listener != null) {
//                    listener.onGetUserInfor(s)
//                            .subscribe(new BaseObserver<UserInfor>(null) {
//                                @Override
//                                public void onNext(UserInfor infor) {
//                                    super.onNext(infor);
//                                    refreshUserInfoCache(s, infor);
//                                }
//                            });
//                }
//                return null;
//            }
//        }, true);
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
        final String name = TextUtils.isEmpty(infor.getContact()) ? infor.getMobile() : infor.getContact();
        final Uri head = TextUtils.isEmpty(infor.getLogoPath()) ? null : Uri.parse(infor.getLogoPath());
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(userID, name, head));
    }




    public void put(String userId, UserInfor infor) {
        cache.put(userId, infor);
//        infor.updateAllAsync("userId = ?", userId);

        final String name = TextUtils.isEmpty(infor.getContact()) ? infor.getMobile() : infor.getContact();
        final Uri head = TextUtils.isEmpty(infor.getLogoPath()) ? null : Uri.parse(infor.getLogoPath());
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(userId, name, head));
    }

    private LruCache<String, UserInfor> cache = new LruCache<>(100);


}
