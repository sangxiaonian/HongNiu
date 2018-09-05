package com.hongniu.supply.ui;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.supply.R;
@Route(path = ArouterParamsApp.activity_h5)
public class H5Activity extends BaseActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);
        setToolbarDarkTitle("");
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        webView = findViewById(R.id.webview);

    }

    @Override
    protected void initData() {
        super.initData();

      webView.loadUrl(getIntent().getStringExtra(Param.TRAN));
    }
}