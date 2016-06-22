package com.dundunwen.openchina.blog_unit;

import android.content.Context;

import com.dundunwen.openchina.bean.BlogList;
import com.dundunwen.openchina.blog_unit.impls.SubBlogModelImpl;
import com.dundunwen.openchina.blog_unit.impls.SubBlogPresenterImpl;
import com.dundunwen.openchina.singleton.HtmlUserInfoHolder;
import com.dundunwen.openchina.utils.Apis;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by dun on 16/6/22.
 */
public class SubBlogModel implements SubBlogModelImpl {
    SubBlogPresenterImpl presenter;
    Context mContext;
    public SubBlogModel(Context mContext, SubBlogPresenter subBlogPresenter) {
        this.presenter = subBlogPresenter;
        this.mContext = mContext;
    }

    @Override
    public Observable<BlogList> getSynthesizeBlogList(int page,int pageSize) {
        String token  = HtmlUserInfoHolder.getInstance().getHtmlUserInfo().getAccess_token();
        return Apis.Helper.getSimpleApi().getSynthesizeBlogList(token,page,pageSize,"json")
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BlogList> getRecommentBlogList(int page, int pageSize) {
        String token  = HtmlUserInfoHolder.getInstance().getHtmlUserInfo().getAccess_token();
        return Apis.Helper.getSimpleApi().getRecommentBlogList(token,page,pageSize,"json")
                .subscribeOn(Schedulers.io());
    }
}
