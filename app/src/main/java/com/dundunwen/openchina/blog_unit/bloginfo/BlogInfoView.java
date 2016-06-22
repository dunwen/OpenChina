package com.dundunwen.openchina.blog_unit.bloginfo;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.dundunwen.openchina.BaseActivity;
import com.dundunwen.openchina.R;
import com.dundunwen.openchina.blog_unit.bloginfo.impls.BlogInfoPresenterImpl;
import com.dundunwen.openchina.blog_unit.bloginfo.impls.BlogInfoViewImpl;
import com.dundunwen.openchina.login.LocalLoginView;
import com.dundunwen.openchina.utils.Apis;
import com.orhanobut.logger.Logger;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dun on 16/6/22.
 */
public class BlogInfoView extends BaseActivity implements BlogInfoViewImpl{
    @BindView(R.id.blog_info_webview)
    WebView mWebView;
    @BindView(R.id.blog_info_iv_keyboard)
    ImageView ic_keyboard;
    @BindView(R.id.blog_info_iv_show_comment)
    ImageView ic_showCommend;
    @BindView(R.id.blog_info_iv_add_comment)
    ImageView ic_addCommend;
    @BindView(R.id.blog_info_iv_share)
    ImageView ic_share;
    @BindView(R.id.blog_info_iv_favour)
    ImageView ic_favour;

    BlogInfoPresenterImpl presenter;
    long id = -1;
    public static  final  String KEY_ID ="KEY_ID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_info_layout);
        ButterKnife.bind(this);
        presenter = new BlogInfoPresenter(this,this);

        Bundle b = getIntent().getExtras();
        if(b==null){
            Logger.e("bundle is null?");
        }else{
            id = b.getLong(KEY_ID);
        }

        findViews();
        initViews();
        getDatas();
    }


    @Override
    public void findViews() {

    }

    @Override
    public void initViews() {
        WebSettings mSetting = mWebView.getSettings();
        mSetting.setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });
    }

    @Override
    public void getDatas() {
        presenter.getAllDatas(id);
    }


    @Override
    public void setWebViewContent(String content) {
        mWebView.loadDataWithBaseURL(Apis.baseUrl, content, "text/html", "UTF-8", Apis.baseUrl);
    }

    @Override
    public void setIsFavour(boolean isFavour) {
        if(isFavour){
            ic_favour.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_favour));
        }else{
            ic_favour.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_unfavour));
        }
    }

    @Override
    public void callLoading(boolean is, String msg) {

    }

    @Override
    public void startNextView(Intent i) {

    }

    @Override
    public void showError(int errorCode, String msg) {

    }
}
