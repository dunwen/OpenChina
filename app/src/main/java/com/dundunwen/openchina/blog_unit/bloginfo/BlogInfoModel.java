package com.dundunwen.openchina.blog_unit.bloginfo;

import android.content.Context;

import com.dundunwen.openchina.bean.BlogDetail;
import com.dundunwen.openchina.blog_unit.bloginfo.impls.BlogInfoModelImpl;
import com.dundunwen.openchina.blog_unit.bloginfo.impls.BlogInfoPresenterImpl;
import com.dundunwen.openchina.singleton.HtmlUserInfoHolder;
import com.dundunwen.openchina.utils.Apis;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by dun on 16/6/22.
 */
public class BlogInfoModel implements BlogInfoModelImpl {
    BlogInfoPresenterImpl presenter;
    Context mContext;

    public BlogInfoModel(Context mContext, BlogInfoPresenterImpl presenter) {
        this.mContext = mContext;
        this.presenter = presenter;
    }

    @Override
    public Observable<BlogDetail> getBlogDetail(long id) {
        String token  = HtmlUserInfoHolder.getInstance().getHtmlUserInfo().getAccess_token();
        return Apis.Helper.getSimpleApi().getBlogDetail(token,id,"json").subscribeOn(Schedulers.io());
    }
}
