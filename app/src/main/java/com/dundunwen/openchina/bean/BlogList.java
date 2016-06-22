package com.dundunwen.openchina.bean;

import java.util.List;

/**
 * Created by dun on 16/6/22.
 */
public class BlogList {
    List<BlogSummary> bloglist;
    Notice notice;

    public BlogList() {
    }

    public List<BlogSummary> getBloglist() {
        return bloglist;
    }

    public void setBloglist(List<BlogSummary> bloglist) {
        this.bloglist = bloglist;
    }

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }
}
