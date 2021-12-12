package com.hongniu.supply.manager

import android.content.Context
import com.fy.androidlibrary.utils.JLog
import com.fy.androidlibrary.utils.SharedPreferencesUtils
import com.fy.companylibrary.manager.PrivacyManger
import com.google.gson.Gson
import com.hongniu.freight.Config
import com.hongniu.supply.BuildConfig
import com.hongniu.supply.R
import com.hongniu.supply.entity.PushBean
import com.hongniu.supply.ui.receiver.MyPlushBroadcastReceiver
import com.sang.thirdlibrary.chact.ChactHelper
import com.sang.thirdlibrary.map.verify.VerifyClient
import com.sang.thirdlibrary.push.NotificationUtils
import com.sang.thirdlibrary.push.client.PushClient
import com.sang.thirdlibrary.push.client.Umeng
import com.sang.thirdlibrary.push.inter.PlushRegisterListener
import com.umeng.message.entity.UMessage
import org.greenrobot.eventbus.EventBus

/**
 *@data  2021/12/12$
 *@Author PING
 *@Description
 *
 *由于改版，需要对部分第三方做延迟初始化等操作，此处进行统一管理
 */
object ThirdManager {

    fun init(context: Context,isDebug: Boolean) {
        val isAgree = PrivacyManger.isAgreePrivacy()
        registerUM(context, isAgree)
        registerRong(context, isAgree)
        verifyClient(context,isAgree,isDebug)

    }


    private fun registerUM(context: Context, isAgree: Boolean) {
        val plushClient = PushClient.getClient();
        plushClient.setPlush(context)
        plushClient.setPlushRegisterListener(object : PlushRegisterListener {

            /**
             * 推送注册成功
             *
             * @param data
             */
            override fun onSuccess(data: String?) {
                JLog.d("推送注册成功:$data");
                SharedPreferencesUtils.getInstance().putString("UMENG", data)
                EventBus.getDefault().postSticky(Umeng())
            }

            /**
             * 推送注册失败
             *
             * @param code  失败码
             * @param errorReson 失败原因
             */
            override fun onFailure(code: String?, errorReson: String?) {
                JLog.e("推送注册失败:$code :  $errorReson")
            }
        })
        NotificationUtils.getInstance().setReceiver(MyPlushBroadcastReceiver::class.java)
        plushClient.setPlushDealWithMessageListener { context, message ->
            JLog.i("接收到推送信息：$message");
            if (message is UMessage) {
                try {
                    val custom = message.custom;
                    var pushBean: PushBean? = null;
                    try {
                        pushBean = Gson().fromJson(custom, PushBean::class.java)
                    } catch (e: Exception) {
                        e.printStackTrace();
                    }
                    NotificationUtils.getInstance()
                        .setSound(if (pushBean?.showSound == true) R.raw.notify_sound else 0)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        plushClient.init(context, isAgree)
    }

    private fun registerRong(context: Context, isAgree: Boolean) {
        ChactHelper.getHelper()
            .setIsAgree(isAgree)
            .initHelper(context.applicationContext, BuildConfig.RONG_CLOUD_APP_KEY) //未读消息监听
            .setUnReadCountListener { count: Int ->
                EventBus.getDefault().postSticky(count)
            }
    }

    private fun verifyClient(context: Context, isAgree: Boolean, isDebug: Boolean) {
        var verify_appID = "IDAOczat" //人脸识别正式版本
        var verify_secret = "9iL7CJ3WLob3tyt0S4MnE319wqKOPad8iMzBYfjuq5xHdv0lNyTnNiAocbR5KQvn"
        var verify_SDKlicense =
            "uUIYZYlnFqOhA/QJH9qWifrjM6bOz0ouRvDGYIr9Ec0mTMqMqgkoCNhVlp/NGXVXEQ/wQHt5Aw45tsEJn4SAY5HaP/0xZfKdPAizDgQE1a/0kZK6SYmra0BdqOAkglxYP8d/xzAKU2InNXv2i1lmW1gwGjF9gOXi3w8SBDqjG2YEbmgGFSym7uo+3nZF0iqmAss5fFEuKkzk1kTncUTNLzWXYxOZMCLYviy8tzMSMLkptYRm1Fcp3fLhU0vrdVVO7kwmqdOxNoI/NTPtitJajK4CC4XgXNkR33FcYKOw/+COY8dDatw6jONr06RCJQI7Rqx0AM+7fFwVVFkQULSSBg=="

        val verify_appID_debug = "TIDAVBoi" //人脸识别正式版本

        val verify_secret_debug = "CjJXtRZAx2dAsU1yJRIhqgvBlOh6HWXLHK3H8LBUdmn24HNGJ21YKyZqVPYHZzkz"
        val verify_SDKlicense_debug =
            "uUIYZYlnFqOhA/QJH9qWifrjM6bOz0ouRvDGYIr9Ec0mTMqMqgkoCNhVlp/NGXVXEQ/wQHt5Aw45tsEJn4SAY5HaP/0xZfKdPAizDgQE1a/0kZK6SYmra0BdqOAkglxYP8d/xzAKU2InNXv2i1lmW1gwGjF9gOXi3w8SBDqjG2aIi5DV2uAzdpK5LFqGeRQmWuIRXQRE45dL5/wSNmQjguve3GxMpEVqD3a08mV+1xLPRrYCHZOEHxzoiYhqNn+/e+S0Wsxlohhbi5PQwkf62iLGIFmYpWWrVfOnqh8njWFTSZ5bnxx8olyzYri29hCmnud6nl3cAUl338sIc1q4Jg=="

        val verify_appID_releast = "IDAOczat" //人脸识别正式版本

        val verify_secret_releast =
            "9iL7CJ3WLob3tyt0S4MnE319wqKOPad8iMzBYfjuq5xHdv0lNyTnNiAocbR5KQvn"
        val verify_SDKlicense_releast =
            "uUIYZYlnFqOhA/QJH9qWifrjM6bOz0ouRvDGYIr9Ec0mTMqMqgkoCNhVlp/NGXVXEQ/wQHt5Aw45tsEJn4SAY5HaP/0xZfKdPAizDgQE1a/0kZK6SYmra0BdqOAkglxYP8d/xzAKU2InNXv2i1lmW1gwGjF9gOXi3w8SBDqjG2YEbmgGFSym7uo+3nZF0iqmAss5fFEuKkzk1kTncUTNLzWXYxOZMCLYviy8tzMSMLkptYRm1Fcp3fLhU0vrdVVO7kwmqdOxNoI/NTPtitJajK4CC4XgXNkR33FcYKOw/+COY8dDatw6jONr06RCJQI7Rqx0AM+7fFwVVFkQULSSBg=="

        if (isDebug) {
            verify_appID = verify_appID_debug
            verify_secret = verify_secret_debug
            verify_SDKlicense = verify_SDKlicense_debug
        } else {
            verify_appID = verify_appID_releast
            verify_secret = verify_secret_releast
            verify_SDKlicense = verify_SDKlicense_releast
        }
        // 初始化实人认证 SDK
        VerifyClient.getInstance()
            .setAppID(verify_appID)
            .setSecret(verify_secret)
            .setSDKlicense(verify_SDKlicense)
            .initClient(isDebug)
    }
}