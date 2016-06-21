package com.dundunwen.openchina.singleton;

import com.dundunwen.openchina.bean.HtmlUserInfo;

/**
 * Created by dun on 16/6/21.
 */
public class HtmlUserInfoHolder {
    private static HtmlUserInfoHolder holder;
    private HtmlUserInfo htmlUserInfo;
    private HtmlUserInfoHolder() {
    }
    public static HtmlUserInfoHolder getInstance(){
        if(holder==null){
            holder = new HtmlUserInfoHolder();
        }
        return holder;
    }

    public  HtmlUserInfoHolder getHolder() {
        return holder;
    }

    public void setHolder(HtmlUserInfoHolder holder) {
        this.holder = holder;
    }

    public HtmlUserInfo getHtmlUserInfo() {
        return htmlUserInfo;
    }

    public void setHtmlUserInfo(HtmlUserInfo htmlUserInfo) {
        this.htmlUserInfo = htmlUserInfo;
    }
}
