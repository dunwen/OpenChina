package com.dundunwen.openchina.singleton;

import com.dundunwen.openchina.bean.HtmlUserInfo;
import com.dundunwen.openchina.bean.MyInformation;
import com.dundunwen.openchina.bean.User;

/**
 * Created by dun on 16/6/22.
 */
public class MyInformationHolder {
    private static MyInformationHolder holder;
    private User user;
    private MyInformationHolder() {
    }
    public static MyInformationHolder getInstance(){
        if(holder==null){
            holder = new MyInformationHolder();
        }
        return holder;
    }

    public User getMyInformation() {
        return user;
    }

    public void setMyInformation(User myInformation) {
        this.user = myInformation;
    }
}
