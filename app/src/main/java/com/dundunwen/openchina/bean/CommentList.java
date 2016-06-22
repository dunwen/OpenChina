package com.dundunwen.openchina.bean;

import java.util.List;

/**
 * Created by dun on 16/6/22.
 */
public class CommentList {
    int count;
    List<CommentInfo> commentlist;
    Notice notice;

    public CommentList() {
    }

    public List<CommentInfo> getCommentlist() {
        return commentlist;
    }

    public void setCommentlist(List<CommentInfo> commentlist) {
        this.commentlist = commentlist;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }
}
