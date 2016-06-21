package com.dundunwen.openchina.login.impls;

import com.dundunwen.openchina.basemvp.BasePresenter;

/**
 * Created by dun on 16/6/21.
 */
public interface LoginPresenterImpl extends BasePresenter{
    /**
     * 调用授权页面，调到一个网页里面登录
     * */
    void dispatchHtmlLogin();

    /**
     * 获取asset_token的方法
     * */
    void dispatchLocalLogin(String code);
}
