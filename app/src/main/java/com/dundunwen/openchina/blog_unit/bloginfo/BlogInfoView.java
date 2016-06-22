package com.dundunwen.openchina.blog_unit.bloginfo;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.dundunwen.openchina.BaseActivity;
import com.dundunwen.openchina.R;
import com.dundunwen.openchina.bean.CommentList;
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
import butterknife.OnClick;

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

    @OnClick(R.id.blog_info_iv_show_comment)
    public void showCommend(View view){
        Intent i = new Intent(this, CommentListActivity.class);
        i.putExtra(CommentListActivity.KEY_ID,id);
        startActivity(i);
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
        ActionBar mBar = getSupportActionBar();
        if(mBar!=null);
        mBar.setTitle("博客详情");
        mBar.setDisplayHomeAsUpEnabled(true);
        mBar.setDisplayShowHomeEnabled(false);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home :{
                this.finish();
            }
            default:{
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @OnClick(R.id.blog_info_iv_add_comment)
    void clickBindView(View v){
        Intent i = new Intent(this, CommentListActivity.class);
        i.putExtra(CommentListActivity.KEY_ID,id);
        startActivity(i);
    }

    @Override
    public void getDatas() {
        presenter.getAllDatas(id);
    }


    @OnClick(R.id.blog_info_iv_favour)
    void addOrDelFavour(View view){
        if(!isFavour){
            presenter.addFavorite(id);
        }else{
            presenter.delFavorite(id);
        }
    }


    @Override
    public void setWebViewContent(String content) {
        mWebView.loadDataWithBaseURL(Apis.baseUrl, content, "text/html", "UTF-8", Apis.baseUrl);
    }
    boolean isFavour = false;
    @Override
    public void setIsFavour(boolean isFavour) {
        this.isFavour = isFavour;
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
