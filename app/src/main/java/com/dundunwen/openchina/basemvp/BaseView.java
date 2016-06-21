package com.dundunwen.openchina.basemvp;

import android.content.Intent;

/**
 * Created by dun on 16/6/21.
 */
public interface BaseView {
    void callLoading(boolean is,String msg);
    void startNextView(Intent i);
    void showError(int errorCode,String msg);
}
