package com.hongniu.freight.widget;

import android.content.Context;
import android.graphics.Color;
import android.net.http.SslError;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.fy.androidlibrary.utils.JLog;
import com.hongniu.freight.R;


/**
 * 作者： ${PING} on 2018/9/5.
 */
public class XWebView extends LinearLayout {

    private ProgressBar mProgressBar;
    private WebView webView;

    OnReceivedTitleListener onReceivedTitleListener;

    public XWebView(Context context) {
        this(context, null, 0);
    }


    public XWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public XWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(VERTICAL);

        mProgressBar = (ProgressBar) LayoutInflater.from(getContext()).inflate(
                R.layout.progress_horizontal, null);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        try {
            addView(mProgressBar, LayoutParams.MATCH_PARENT,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
                            5, getResources().getDisplayMetrics()));
        } catch (Exception ex) {
        }

        webView = new WebView(context);
        addView(webView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();

//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

                handler.proceed();
            }


        });
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    if (mProgressBar.getVisibility() != GONE) {
                        mProgressBar.setVisibility(GONE);
                    }
                } else {
                    if (mProgressBar.getVisibility() != VISIBLE) {
                        mProgressBar.setVisibility(VISIBLE);
                    }
                }
                mProgressBar.setProgress(newProgress);

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (onReceivedTitleListener != null) {
                    onReceivedTitleListener.onReceivedTitle(view, title);
                }
            }

            ;

        });

    }

    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    public void setOnReceivedTitleListener(OnReceivedTitleListener onReceivedTitleListener) {
        this.onReceivedTitleListener = onReceivedTitleListener;
    }

    public boolean canGoBack() {
        return webView.canGoBack();
    }

    public void goBack() {
        webView.goBack();
    }

    public interface OnReceivedTitleListener {
        void onReceivedTitle(WebView view, String title);
    }


}
