package com.hongniu.supply.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.H5Config;
import com.hongniu.supply.R;
import com.sang.common.utils.JLog;
import com.sang.common.widget.XWebView;

import java.io.Serializable;

@Route(path = ArouterParamsApp.activity_niu_insurance_h5)
public class NiuInsuranceH5Activity extends BaseActivity implements XWebView.OnReceivedTitleListener, View.OnClickListener {

    private XWebView webView;
    private Button btSum;
    private H5Config h5Config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niu_insurance_h5);
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
        btSum=findViewById(R.id.bt_sum);
    }

    @Override
    protected void initListener() {
        super.initListener();
        webView.setOnReceivedTitleListener(this);
        btSum.setOnClickListener(this);
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


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_create).navigation(this);

    }
}