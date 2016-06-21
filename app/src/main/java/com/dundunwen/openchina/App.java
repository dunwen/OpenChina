package com.dundunwen.openchina;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.Logger;


/**
 * Created by dun on 16/6/21.
 */
public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init();
        Fresco.initialize(this);
    }
}
