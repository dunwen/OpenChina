package com.dundunwen.openchina.login.impls;

import com.dundunwen.openchina.basemvp.BaseModel;
import com.dundunwen.openchina.bean.HtmlUserInfo;

import rx.Observable;

/**
 * Created by dun on 16/6/21.
 */
public interface LoginModelImpl extends BaseModel{
    Observable<HtmlUserInfo> dispatchAuthorize(String code);
}
