package com.dundunwen.openchina.login.impls;

import com.dundunwen.openchina.basemvp.BasePresenter;

/**
 * Created by dun on 16/6/21.
 */
public interface LoginPresenterImpl extends BasePresenter{
    void dispatchHtmlLogin();
    void dispatchLocalLogin(String code);
}
