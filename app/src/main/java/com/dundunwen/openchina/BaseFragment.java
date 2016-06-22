package com.dundunwen.openchina;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

/**
 * Created by dun on 16/6/21.
 */
public abstract class BaseFragment extends Fragment{
    protected boolean isDebug = true;

    public abstract void findViews();
    public abstract void initViews();
    public abstract void getDatas();

    ProgressDialog waitingDialog;
    public void showWaitingDialog(boolean isShow,boolean canTouchOutSideCancel,String msg){
        if(isShow){
            if(waitingDialog == null){
                waitingDialog = new ProgressDialog(getContext());
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
