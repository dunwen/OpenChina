package com.dundunwen.openchina;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dun on 16/6/21.
 */
public abstract class BaseActivity extends AppCompatActivity{
    protected boolean isDebug = true;

    public abstract void findViews();
    public abstract void initViews();
    public abstract void getDatas();

    ProgressDialog waitingDialog;
    public void showWaitingDialog(boolean isShow,boolean canTouchOutSideCancel,String msg){
        if(isShow){
            if(waitingDialog == null){
                waitingDialog = new ProgressDialog(this);
            }
            waitingDialog.setMessage(msg);
            waitingDialog.setCanceledOnTouchOutside(canTouchOutSideCancel);
            waitingDialog.show();
        }else{
            if(waitingDialog.isShowing()){
                waitingDialog.dismiss();
            }
        }
    }
}
