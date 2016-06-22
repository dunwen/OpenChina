package com.dundunwen.openchina.blog_unit.bloginfo;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.dundunwen.openchina.bean.BlogDetail;
import com.dundunwen.openchina.blog_unit.bloginfo.impls.BlogInfoModelImpl;
import com.dundunwen.openchina.blog_unit.bloginfo.impls.BlogInfoPresenterImpl;
import com.dundunwen.openchina.blog_unit.bloginfo.impls.BlogInfoViewImpl;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;

/**
 * Created by dun on 16/6/22.
 *
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

    @Override
    public void addFavorite(long id) {
        model.addFavorite(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.body().string().contains("200")){
                        Toast.makeText(mContext,"添加收藏成功",Toast.LENGTH_SHORT).show();
                        view.setIsFavour(true);
                    }else{
                        Toast.makeText(mContext,"添加收藏失败",Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext,"添加收藏失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void delFavorite(long id) {
        model.delFavorite(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.body().string().contains("200")){
                        Toast.makeText(mContext,"取消收藏成功",Toast.LENGTH_SHORT).show();
                        view.setIsFavour(false);
                    }else{
                        Toast.makeText(mContext,"取消收藏失败",Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext,"取消收藏失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
