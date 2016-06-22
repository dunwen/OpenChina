package com.dundunwen.openchina.bean;

/**
 * Created by dun on 16/6/22.
 */
public class BlogSummary {
    /**
     * 博客id
     * */
    long id;
    String author;
    String title;
    String pubDate;
    long authorid;
    int commentCount;
    long type;

    public BlogSummary() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getAuthorid() {
        return authorid;
    }

    public void setAuthorid(long authorid) {
        this.authorid = authorid;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }
}
