package com.dundunwen.openchina.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import com.dundunwen.openchina.BaseActivity;
import com.dundunwen.openchina.R;
import com.dundunwen.openchina.basemvp.BaseView;
import com.dundunwen.openchina.bean.HtmlUserInfo;
import com.dundunwen.openchina.login.impls.LoginPresenterImpl;
import com.dundunwen.openchina.login.impls.LoginViewImpl;
import com.dundunwen.openchina.utils.Apis;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LocalLoginView extends BaseActivity implements LoginViewImpl {
    public static final String KEY_CODE = "KEY_CODE";

    @BindView(R.id.btn_login)
    Button btn_login;
    LoginPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_login_view);
        ButterKnife.bind(this);

        presenter = new LoginPresenter(this, this);


        findViews();
        initViews();
        getDatas();
    }

    @OnClick(R.id.btn_login)
    void dispatchHtmlLogin() {
        presenter.dispatchHtmlLogin();
    }

    @Override
    public void findViews() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void getDatas() {

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle b = intent.getExtras();
        if (b == null) {
            Logger.w("bundle is null");
        } else {
            String code = b.getString(KEY_CODE);
            presenter.dispatchLocalLogin(code);
        }
    }

    @Override
    public void callLoading(boolean is, String msg) {
        showWaitingDialog(is, false, msg);
    }

    @Override
    public void startNextView(Intent i) {
        startActivity(i);
    }

    @Override
    public void showError(int errorCode, String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
