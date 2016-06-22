package com.dundunwen.openchina.blog_unit;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dundunwen.openchina.adapters.BlogListRVAdapter;
import com.dundunwen.openchina.bean.BlogList;
import com.dundunwen.openchina.bean.BlogSummary;
import com.dundunwen.openchina.blog_unit.bloginfo.BlogInfoView;
import com.dundunwen.openchina.blog_unit.impls.SubBlogModelImpl;
import com.dundunwen.openchina.blog_unit.impls.SubBlogPresenterImpl;
import com.dundunwen.openchina.blog_unit.impls.SubBlogViewImpl;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by dun on 16/6/22.
 */
public class SubBlogPresenter implements SubBlogPresenterImpl {
    SubBlogModelImpl model;
    SubBlogViewImpl view;
    Context mContext;
    String type;
    List<BlogSummary> blogSummaries = new ArrayList<>();
    int currentPage = 0;
    Handler mHandler;
    public SubBlogPresenter(Context mContext, SubBlogViewImpl view) {
        this.mContext = mContext;
        this.view = view;
        this.model = new SubBlogModel(mContext,this);
        mHandler = new Handler(mContext.getMainLooper());
    }

    @Override
    public void getBlogList(final RecyclerView mView, String type, boolean isReload) {
        this.type = type;
        if(type.equals(SubBlogView.TYPE_SYNTHESIZE)){
            getSynthesize(mView,type,isReload);
        }else if(type.equals(SubBlogView.TYPE_RECOMMEND)){
            getRecomment(mView,type,isReload);
        }else if(type.equals(SubBlogView.TYPE_ME)){
            getUserBlogList(mView,type,isReload);
        }
    }

    private void getUserBlogList(RecyclerView mView, String type, boolean isReload) {
        if(isReload){
            view.dismissRefreshLayout(true);
            blogSummaries.clear();
            currentPage = 0;
        }
        setBlogSummaries(model.getUserBlogList(currentPage++,20),mView);
    }

    private void getRecomment(RecyclerView mView, String type, boolean isReload) {
        if(isReload){
            view.dismissRefreshLayout(true);
            blogSummaries.clear();
            currentPage = 0;
        }
        setBlogSummaries(model.getRecommentBlogList(currentPage++,20),mView);
    }

    private void getSynthesize(final RecyclerView mView, String type, boolean isReload) {
        if(isReload){
            view.dismissRefreshLayout(true);
            blogSummaries.clear();
            currentPage = 0;
        }

        setBlogSummaries(model.getSynthesizeBlogList(currentPage++,20),mView);
    }

    boolean isNoMoreData =false;
    private  void setBlogSummaries(Observable<BlogList> observable,final RecyclerView mView){
        if(isNoMoreData){
            return;
        }
        if(observable == null){
            initRecyclerView(mView,null);
            return;
        }
        observable.subscribe(new Action1<BlogList>() {
            @Override
            public void call(final BlogList blogList) {
                final int from = blogSummaries.size();
                if(blogList.getBloglist()!=null){
                    blogSummaries.addAll(blogList.getBloglist());
                }

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        BlogListRVAdapter adapter = (BlogListRVAdapter) mView.getAdapter();
                        if(adapter!=null){
                            if(from==0){
                                adapter.notifyDataSetChanged();
                            }else{
                                adapter.notifyItemChanged(from,blogSummaries.size());
                            }
                        }else{
                            initRecyclerView(mView,adapter);
                        }
                        if(blogList.getBloglist()==null||blogList.getBloglist().size()<20){
                            isNoMoreData = true;
                            ((BlogListRVAdapter)mView.getAdapter()).isNoMoreData(true);
                        }
                        view.dismissRefreshLayout(false);
                    }
                });
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Logger.e("发生错误",throwable);
            }
        });
    }


    private void initRecyclerView(RecyclerView mView, BlogListRVAdapter adapter) {
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        mView.setLayoutManager(llm);

        adapter = new BlogListRVAdapter(blogSummaries);
        adapter.setListener(new BlogListRVAdapter.ItemClick() {
            @Override
            public void onItemClick(int positon) {
                Intent i = new Intent(mContext, BlogInfoView.class);
                i.putExtra(BlogInfoView.KEY_ID,blogSummaries.get(positon).getId());
                mContext.startActivity(i);
            }
        });
        mView.setAdapter(adapter);
        mView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isScrolledToBottom = true;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastItem = llm.findLastCompletelyVisibleItemPosition();
                int allItem = llm.getItemCount() - 2;
//                Log.i(">>>>", "onScrollStateChanged: last>"+lastItem+" all>"+allItem);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (lastItem >= allItem) {
                        getBlogList(recyclerView,type,false);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    isScrolledToBottom = true;
                } else {
                    isScrolledToBottom = false;
                }
            }
        });
    }
}
