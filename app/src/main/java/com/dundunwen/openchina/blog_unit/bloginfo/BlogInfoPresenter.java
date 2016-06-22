package com.dundunwen.openchina.blog_unit.bloginfo;

import android.content.Context;
import android.os.Handler;

import com.dundunwen.openchina.bean.BlogDetail;
import com.dundunwen.openchina.blog_unit.bloginfo.impls.BlogInfoModelImpl;
import com.dundunwen.openchina.blog_unit.bloginfo.impls.BlogInfoPresenterImpl;
import com.dundunwen.openchina.blog_unit.bloginfo.impls.BlogInfoViewImpl;
import com.orhanobut.logger.Logger;

import rx.functions.Action1;

/**
 * Created by dun on 16/6/22.
 */
public class BlogInfoPresenter implements BlogInfoPresenterImpl{
    BlogInfoModelImpl model;
    BlogInfoViewImpl view;
    Context mContext;
    Handler mHandler;
    public BlogInfoPresenter(Context mContext, BlogInfoViewImpl view) {
        this.mContext = mContext;
        this.view = view;
        this.model = new BlogInfoModel(mContext,this);
        mHandler = new Handler(mContext.getMainLooper());
    }

    @Override
    public void getAllDatas(long id) {
        model.getBlogDetail(id).subscribe(new Action1<BlogDetail>() {
            @Override
            public void call(BlogDetail blogDetail) {
                final String content = blogDetail.getBody();
                final int isFavour = blogDetail.getFavorite();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setIsFavour(isFavour==1);
                        view.setWebViewContent(content);
                    }
                });
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Logger.e("发生了错误");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.showError(-1,"网络请求错误");
                    }
                });
            }
        });
    }
}
