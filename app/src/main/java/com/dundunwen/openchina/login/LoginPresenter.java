package com.dundunwen.openchina.login;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.dundunwen.openchina.bean.HtmlUserInfo;
import com.dundunwen.openchina.login.impls.LoginModelImpl;
import com.dundunwen.openchina.login.impls.LoginPresenterImpl;
import com.dundunwen.openchina.login.impls.LoginViewImpl;
import com.dundunwen.openchina.main.MainActivity;
import com.dundunwen.openchina.singleton.HtmlUserInfoHolder;

import rx.functions.Action1;

/**
 * Created by dun on 16/6/21.
 */
public class LoginPresenter implements LoginPresenterImpl {
    LoginViewImpl view;
    LoginModelImpl model;
    Context mContext;

    Handler mHandler;

    public LoginPresenter(Context mContext, LoginViewImpl view) {
        this.mContext = mContext;
        this.view = view;
        this.model = new LoginModel(this,mContext);
        mHandler = new Handler(mContext.getMainLooper());
    }

    @Override
    public void dispatchHtmlLogin() {
        Intent i = new Intent(mContext,HtmlLoginView.class);
        view.startNextView(i);
    }

    @Override
    public void dispatchLocalLogin(String code) {
        view.callLoading(true,"正在获取授权~");
        model.dispatchAuthorize(code).subscribe(new Action1<HtmlUserInfo>() {
            @Override
            public void call(HtmlUserInfo htmlUserInfo) {
                HtmlUserInfoHolder.getInstance().setHtmlUserInfo(htmlUserInfo);
                model.saveHtmlUserInfo(htmlUserInfo);
                Intent i = new Intent(mContext, MainActivity.class);
                view.callLoading(false,"");
                view.startNextView(i);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                view.showError(0,"授权失败啦!");
            }
        });
    }
}
