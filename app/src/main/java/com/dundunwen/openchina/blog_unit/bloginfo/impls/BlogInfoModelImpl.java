package com.dundunwen.openchina.blog_unit.bloginfo.impls;

import com.dundunwen.openchina.basemvp.BaseModel;
import com.dundunwen.openchina.bean.BlogDetail;

import rx.Observable;

/**
 * Created by dun on 16/6/22.
 */
public interface BlogInfoModelImpl extends BaseModel{
    Observable<BlogDetail> getBlogDetail(long id);
}
