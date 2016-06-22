package com.dundunwen.openchina.blog_unit.bloginfo.impls;

import com.dundunwen.openchina.basemvp.BasePresenter;

/**
 * Created by dun on 16/6/22.
 */
public interface BlogInfoPresenterImpl extends BasePresenter{
    void getAllDatas(long id);
    void addFavorite(long id);
    void delFavorite(long id);
}
