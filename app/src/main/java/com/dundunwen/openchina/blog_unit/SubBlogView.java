package com.dundunwen.openchina.blog_unit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dundunwen.openchina.BaseFragment;
import com.dundunwen.openchina.R;
import com.dundunwen.openchina.blog_unit.impls.SubBlogPresenterImpl;
import com.dundunwen.openchina.blog_unit.impls.SubBlogViewImpl;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by dun on 16/6/22.
 */
public class SubBlogView extends BaseFragment implements SubBlogViewImpl {
    public static final String KEY_FRAGMENT_TYPE= "KEY_FRAGMENT_TYPE";
    public static final String TYPE_ME= "TYPE_ME";
    public static final String TYPE_SYNTHESIZE= "TYPE_SYNTHESIZE";
    public static final String TYPE_RECOMMEND= "TYPE_RECOMMEND";
    private String type;
    @BindView(R.id.subblog_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.subblog_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    SubBlogPresenterImpl presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if(b==null){
            Logger.e("bundle is null?");
        }else{
            type = b.getString(KEY_FRAGMENT_TYPE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter = new SubBlogPresenter(getContext(),this);
        getDatas();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_blog_layout,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        findViews(view);
        initViews();

    }

    private void findViews(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.subblog_rv);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.subblog_refresh_layout);
    }

    @Override
    public void findViews() {

    }

    @Override
    public void initViews() {
        refreshLayout.setProgressViewOffset(true, 0, 100);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getBlogList(mRecyclerView,type,true);
            }
        });
    }

    @Override
    public void getDatas() {
        presenter.getBlogList(mRecyclerView,type,true);
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

    @Override
    public void dismissRefreshLayout(boolean is) {
        if(!(refreshLayout.isRefreshing()==is)){
            refreshLayout.setRefreshing(is);
        }
    }
}
