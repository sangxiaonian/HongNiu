package com.hongniu.supply.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.H5Config;
import com.hongniu.supply.R;
import com.sang.common.utils.JLog;
import com.sang.common.widget.XWebView;

import java.io.Serializable;

@Route(path = ArouterParamsApp.activity_h5)
public class H5Activity extends BaseActivity implements XWebView.OnReceivedTitleListener {

    private XWebView webView;
    private H5Config h5Config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);
        setToolbarTitle("");
        setToolBarLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        webView = findViewById(R.id.webview);

    }

    @Override
    protected void initListener() {
        super.initListener();
        webView.setOnReceivedTitleListener(this);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();

        }
    }

    @Override
    protected void initData() {
        super.initData();
         h5Config = (H5Config) getIntent().getSerializableExtra(Param.TRAN);
        if (h5Config!=null) {
            if (h5Config.isDarkTitle){
                setToolbarDarkTitle(h5Config.title);
            }else {
                setToolbarTitle(h5Config.title);
            }
            webView.setOnReceivedTitleListener(this);
            webView.loadUrl(h5Config.url);
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        if (h5Config.changeTitle|| TextUtils.isEmpty(h5Config.title)){
            if (h5Config.isDarkTitle){
                setToolbarDarkTitle(title);
            }else {
                setToolbarTitle(title);
            }
        }
    }



}