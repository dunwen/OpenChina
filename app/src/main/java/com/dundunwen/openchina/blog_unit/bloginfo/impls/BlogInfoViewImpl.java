package com.dundunwen.openchina.blog_unit.bloginfo.impls;

import com.dundunwen.openchina.basemvp.BaseView;

/**
 * Created by dun on 16/6/22.
 */
public interface BlogInfoViewImpl extends BaseView{
    void setWebViewContent(String content);
    void setIsFavour(boolean isFavour);
}
