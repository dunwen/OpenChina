package com.dundunwen.openchina.bean;

import java.util.List;

/**
 * Created by dun on 16/6/22.
 */
public class CommentInfo {
    String content;
    long id;
    String pubDate;
    int appClient;
    String author;
    int authorid;
    String portrait;
    List<Refer> refers;

    public CommentInfo() {
    }

    public int getAppClient() {
        return appClient;
    }

    public void setAppClient(int appClient) {
        this.appClient = appClient;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAuthorid() {
        return authorid;
    }

    public void setAuthorid(int authorid) {
        this.authorid = authorid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public List<Refer> getRefers() {
        return refers;
    }

    public void setRefers(List<Refer> refers) {
        this.refers = refers;
    }
}
