package com.dundunwen.openchina.login;

import android.content.Context;

import com.dundunwen.openchina.bean.HtmlUserInfo;
import com.dundunwen.openchina.login.impls.LoginModelImpl;
import com.dundunwen.openchina.login.impls.LoginPresenterImpl;
import com.dundunwen.openchina.utils.Apis;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by dun on 16/6/21.
 */
public class LoginModel implements LoginModelImpl {
    LoginPresenterImpl presenter;
    Context mContext;

    public LoginModel(LoginPresenterImpl presenter,Context mContext) {
        this.presenter = presenter;
        this.mContext = mContext;
    }

    @Override
    public Observable<HtmlUserInfo> dispatchAuthorize(String code) {
        return Apis.Helper.getSimpleApi().getAccessToken(
                "NXqe70SQG6tznFdrixGU",
                "qSeUO5QV0MAK9NejiuWXcxTPKEDqzC8D",
                "authorization_code",
                "http://www.dundunwen.com",
                code,
                "json"
        ).subscribeOn(Schedulers.io());
    }
}
