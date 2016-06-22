package com.dundunwen.openchina.blog_unit.bloginfo;

import android.content.Context;

import com.dundunwen.openchina.bean.BlogDetail;
import com.dundunwen.openchina.blog_unit.bloginfo.impls.BlogInfoModelImpl;
import com.dundunwen.openchina.blog_unit.bloginfo.impls.BlogInfoPresenterImpl;
import com.dundunwen.openchina.singleton.HtmlUserInfoHolder;
import com.dundunwen.openchina.utils.Apis;

import okhttp3.ResponseBody;
import retrofit2.Call;
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

    @Override
    public Call<ResponseBody> addFavorite(long id) {
        String token  = HtmlUserInfoHolder.getInstance().getHtmlUserInfo().getAccess_token();
        return Apis.Helper.getSimpleApi().addFavouir(token,id,3,"json");
    }

    @Override
    public Call<ResponseBody> delFavorite(long id) {
        String token  = HtmlUserInfoHolder.getInstance().getHtmlUserInfo().getAccess_token();
        return Apis.Helper.getSimpleApi().delFavouir(token,id,3,"json");
    }
}
