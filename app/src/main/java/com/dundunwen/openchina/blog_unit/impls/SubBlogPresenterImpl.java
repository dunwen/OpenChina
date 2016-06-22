package com.dundunwen.openchina.blog_unit.impls;

import android.support.v7.widget.RecyclerView;

import com.dundunwen.openchina.basemvp.BasePresenter;

/**
 * Created by dun on 16/6/22.
 */
public interface SubBlogPresenterImpl extends BasePresenter{
    void getBlogList(RecyclerView mView,String type,boolean isReLoad);
}
