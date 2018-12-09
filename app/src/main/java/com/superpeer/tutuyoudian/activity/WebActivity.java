package com.superpeer.tutuyoudian.activity;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.superpeer.base_libs.base.BaseActivity;
import com.superpeer.tutuyoudian.R;

public class WebActivity extends BaseActivity {

    private WebView mWeb;
    private String url;
    private String title = "";
    private String content = "";

    @Override
    protected void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        setHeadTitle(title);

        initWvSetting();

    }

    /**
     * 初始化webview
     */
    private void initWvSetting() {

        mWeb = (WebView) findViewById(R.id.webView);
        WebSettings settings = mWeb.getSettings();
        //设置允许和js交互
        settings.setJavaScriptEnabled(true);

        if(!TextUtils.isEmpty(content)){
            mWeb.loadDataWithBaseURL(null,content, "text/html" , "utf-8", null);
        }

        if(null!=url&&!"".equals(url)) {
            mWeb.setWebViewClient(new WebViewClient() {

                @SuppressLint("MissingPermission")
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            mWeb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            mWeb.getSettings().setPluginState(WebSettings.PluginState.ON);
            mWeb.loadUrl(url);
        }

    }

}
