package com.dundunwen.openchina.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.dundunwen.openchina.bean.HtmlUserInfo;
import com.dundunwen.openchina.login.impls.LoginModelImpl;
import com.dundunwen.openchina.login.impls.LoginPresenterImpl;
import com.dundunwen.openchina.main.MainActivity;
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

    @Override
    public void saveHtmlUserInfo(HtmlUserInfo info) {
        SharedPreferences sp = mContext.getSharedPreferences(MainActivity.NAME_OF_SHAREPREFERCE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(MainActivity.KEY_OF_ACCESS_TOKEN,info.getAccess_token());
        editor.putLong(MainActivity.KEY_OF_ACCESS_TOKEN_TIME_OUT,System.currentTimeMillis()+info.getExpires_in());
        editor.putString(MainActivity.KEY_OF_REFRESH_TOKEN,info.getRefresh_token());
        editor.putInt(MainActivity.KEY_OF_UID,info.getUid());
        editor.putString(MainActivity.KEY_OF_TOKEN_TYPE,info.getToken_type());
        editor.apply();
    }
}
