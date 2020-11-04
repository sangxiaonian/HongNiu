package com.hongniu.freight.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.companylibrary.config.ArouterParamMNLM;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.H5Config;
import com.hongniu.freight.widget.XWebView;


@Route(path = ArouterParamMNLM.activity_h5)
public class H5Activity extends CompanyBaseActivity implements XWebView.OnReceivedTitleListener {

    private XWebView webView;
    private H5Config h5Config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnlm_activity_h5);
        setWhitToolBar("");
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
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();

        }
    }

    @Override
    protected void initData() {
        super.initData();
        h5Config = (H5Config) getIntent().getSerializableExtra(Param.TRAN);
        if (h5Config != null) {

            setWhitToolBar(h5Config.title);
            webView.setOnReceivedTitleListener(this);
            webView.loadUrl(h5Config.url);
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        if (h5Config.changeTitle || TextUtils.isEmpty(h5Config.title)) {

            setWhitToolBar(title);
        }
    }


}