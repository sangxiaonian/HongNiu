package com.sang.thirdlibrary.bug;

import android.content.Context;

import com.fy.androidlibrary.utils.DeviceUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

/**
 * 作者： ${PING} on 2018/9/11.
 */
public class BugClient {

    private String appKey="5b9788ecf43e4845ec000071";
    private String pushSercet="77a9468f91f6fd0106780654b1140e13";

    private static class InnerClient {
        private final static BugClient client = new BugClient();
    }

    public static BugClient getInstance() {
        return InnerClient.client;
    }


    public void init(Context context) {
        /*
注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，UMConfigure.init调用中appkey和channel参数请置为null）。
*/
        UMConfigure.init(context,appKey, DeviceUtils.getDeviceBrand(), UMConfigure.DEVICE_TYPE_PHONE, pushSercet);
        UMConfigure.setLogEnabled(true);
    }


    public void onResume(Context context){
        MobclickAgent.onResume(context); //统计时长
    }
    public void onPause(Context context){
        MobclickAgent.onPause(context); //统计时长
    }


}
