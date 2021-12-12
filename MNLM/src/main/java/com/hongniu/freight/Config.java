package com.hongniu.freight;

import android.app.Activity;
import android.app.Application;

import com.fy.androidlibrary.net.OkHttp;
import com.fy.androidlibrary.utils.JLog;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.CompanyClient;
import com.hdgq.locationlib.listener.OnResultListener;
import com.hongniu.baselibrary.entity.CompanyInfoBean;
import com.hongniu.freight.huoyun.FreightClient;
import com.hongniu.freight.net.interceptor.HeardInterceptor;
import com.hongniu.freight.net.interceptor.LoginOutRespondInterceptor;
import com.hongniu.freight.ui.MainActivity;
import com.sang.thirdlibrary.map.verify.VerifyClient;
import com.umeng.commonsdk.debug.E;

/**
 * 作者：  on 2020/10/7.
 */
public class Config {

    Application application;
    private boolean isDebug;


    //网络货运
    public static String freight_uniquie = "37103085";//企业唯一标识
    public static String freight_secret = "5928b6bab955463a858ef0777e0e8853c7750ed293c2420f8a7942b79ef11f91";


    private String currentPackageName;
    private HeardInterceptor heardInterceptor;
    CompanyInfoBean companyInfoBean;

    private static class Inner {
        private static Config config = new Config();
    }

    public static Config getInstance() {
        return Inner.config;
    }

    private Config() {
    }

    //设置当前公司信息
    public void setCompanyInfoBean(CompanyInfoBean companyInfoBean) {
        this.companyInfoBean = companyInfoBean;
        currentPackageName = companyInfoBean.getAndroidPackage();
    }

    public CompanyInfoBean getCompanyInfoBean() {
        return companyInfoBean;
    }

    public void init(Application context, boolean isDebug) {
        this.application = context;
        this.isDebug = isDebug;
        heardInterceptor = new HeardInterceptor(application);
        CompanyClient.getInstance()
                .addInterceptor(heardInterceptor)
                .addInterceptor(new LoginOutRespondInterceptor(application))
                .addInterceptor(OkHttp.getLogInterceptor());//添加log日志



//        //保活
        FreightClient.getClient().startKeepLive((Application) context, context.getString(R.string.app_name), "正在使用", R.mipmap.ic_launcher);
    }

    public void setCurrentPackageName(String currentPackageName) {
        this.currentPackageName = currentPackageName;
    }

    public void intNetClient(String apiUrl, String subAppCode) {
        if (apiUrl != null) {
            if (heardInterceptor != null) {
                heardInterceptor.setSubAppCode(subAppCode);
            }
            CompanyClient.getInstance()
                    .setBaseUrl(apiUrl);
        }

    }

    public void initFreight(Activity activity) {
        //初始化网络获取数据
        FreightClient.getClient().initSdk(activity, currentPackageName,
                freight_secret,
                freight_uniquie,
                false, new OnResultListener() {
                    @Override
                    public void onSuccess() {
                        JLog.i("初始化成功");
                    }

                    @Override
                    public void onFailure(String s, String s1) {
                        JLog.e("初始化失败：" + s + "  " + s1);

                    }
                });
    }
}
