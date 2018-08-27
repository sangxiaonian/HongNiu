package com.hongniu.supply;

import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.hongniu.baselibrary.base.BaseActivity;
import com.lidong.pdf.PDFView;

public class H5Activity extends BaseActivity {

    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        pdfView=findViewById(R.id.pdfview);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    /**
     * 获取打开网络的pdf文件
     * @param fileUrl
     * @param fileName
     */
    private void displayFromFile1( String fileUrl ,String fileName) {
//        pdfView.(this,this,this,fileUrl,fileName);   //设置pdf文件地址

    }
    public static void setDefaultWebSettings(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        //5.0以上开启混合模式加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
            webSettings.setLoadWithOverviewMode(true);
            //允许js代码
            webSettings.setJavaScriptEnabled(true);
            //允许SessionStorage/LocalStorage存储
            webSettings.setDomStorageEnabled(true);
            //禁用放缩
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                webSettings.setDisplayZoomControls(false);
            }
            webSettings.setBuiltInZoomControls(false);
        }
        webSettings.setUseWideViewPort(true);

        //禁用文字缩放
        webSettings.setTextZoom(100);

        //允许WebView使用File协议
        webSettings.setAllowFileAccess(true);
        //不保存密码
        webSettings.setSavePassword(false);

        //移除部分系统JavaScript接口
        //自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
    }


}
