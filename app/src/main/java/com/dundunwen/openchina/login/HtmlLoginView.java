package com.dundunwen.openchina.login;

import android.content.Intent;
import android.os.Bundle;

import com.dundunwen.openchina.BaseActivity;
import com.dundunwen.openchina.R;
import com.orhanobut.logger.Logger;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;



public class HtmlLoginView extends BaseActivity{

    WebView mWebView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__view);
        findViews();
        initViews();
        getDatas();
    }

    @Override
    public void findViews() {
        mWebView = (WebView) findViewById(R.id.login_webview);
    }

    @Override
    public void initViews() {
        if(mWebView == null){
            Logger.e("");
        }
        WebSettings mSetting = mWebView.getSettings();
        mSetting.setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                Logger.i(s);
                if(s.contains("dundunwen")){
                    int index = s.indexOf('?');
                    String code = s.substring(index+6,s.lastIndexOf('&'));
                    Logger.i(code);
                    Intent i = new Intent(HtmlLoginView.this,LocalLoginView.class);
                    i.putExtra(LocalLoginView.KEY_CODE,code);
                    HtmlLoginView.this.startActivity(i);
                    return true;
                }
                webView.loadUrl(s);
                return true;
            }
        });
    }

    @Override
    public void getDatas() {
        mWebView.loadUrl("https://www.oschina.net/action/oauth2/authorize?response_type=code&client_id=NXqe70SQG6tznFdrixGU&redirect_uri=http://www.dundunwen.com");
//        mWebView.loadUrl("http://www.dundunwen.com");
    }




}
