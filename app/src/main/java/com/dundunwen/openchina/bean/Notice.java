package com.dundunwen.openchina.bean;

/**
 * Created by dun on 16/6/22.
 */
public class Notice {
    /**
     * 未读评论数
     * */
    int replyCount;

    /**
     * 未读私信数

     * */
    int msgCount;
    /**
     * 	未读@我数
     * */
    int referCount;
    /**
     * 新增粉丝数
     * */
    int fansCount;

    public Notice() {
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public int getReferCount() {
        return referCount;
    }

    public void setReferCount(int referCount) {
        this.referCount = referCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }
}
