package com.dundunwen.openchina.blog_unit.impls;

import android.support.v7.widget.RecyclerView;

import com.dundunwen.openchina.basemvp.BaseModel;
import com.dundunwen.openchina.bean.BlogList;

import java.util.List;

import rx.Observable;

/**
 * Created by dun on 16/6/22.
 */
public interface SubBlogModelImpl extends BaseModel{
    Observable<BlogList> getSynthesizeBlogList(int page,int pageSize);
    Observable<BlogList> getRecommentBlogList(int page,int pageSize);
    Observable<BlogList> getUserBlogList(int page,int pageSize);
}
