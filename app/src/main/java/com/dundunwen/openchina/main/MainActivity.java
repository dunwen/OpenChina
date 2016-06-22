package com.dundunwen.openchina.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dundunwen.openchina.R;
import com.dundunwen.openchina.bean.HtmlUserInfo;
import com.dundunwen.openchina.bean.MyInformation;
import com.dundunwen.openchina.bean.User;
import com.dundunwen.openchina.blog_unit.MainBlogView;
import com.dundunwen.openchina.login.LocalLoginView;
import com.dundunwen.openchina.singleton.HtmlUserInfoHolder;
import com.dundunwen.openchina.singleton.MyInformationHolder;
import com.dundunwen.openchina.utils.Apis;
import com.facebook.drawee.view.SimpleDraweeView;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawer;

    public static final String NAME_OF_SHAREPREFERCE = "LOGIN_STATUS";
    public static final String KEY_OF_ACCESS_TOKEN = "KEY_OF_ACCESS_TOKEN";
    public static final String KEY_OF_ACCESS_TOKEN_TIME_OUT = "KEY_OF_ACCESS_TOKEN_TIME_OUT";
    public static final String KEY_OF_REFRESH_TOKEN = "KEY_OF_REFRESH_TOKEN";
    public static final String KEY_OF_TOKEN_TYPE = "KEY_OF_TOKEN_TYPE";
    public static final String KEY_OF_UID = "KEY_OF_UID";

    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler(getMainLooper());
        if (checkIsLogin()) {
            initToolBar();
            initDrawerLayout();
            initFirstInFragment();
            getDatas();
        }
    }

    private boolean checkIsLogin() {
        SharedPreferences sp = getSharedPreferences(NAME_OF_SHAREPREFERCE, MODE_PRIVATE);
        String accessToken = sp.getString("KEY_OF_ACCESS_TOKEN", "");
        long timeOut = sp.getLong(KEY_OF_ACCESS_TOKEN_TIME_OUT, 0);
        String refreshToke = sp.getString(KEY_OF_REFRESH_TOKEN, "");
        String tokenType = sp.getString(KEY_OF_TOKEN_TYPE, "");
        int uid = sp.getInt(KEY_OF_UID, -1);
        long currTime = System.currentTimeMillis();
        if (TextUtils.isEmpty(accessToken) || currTime >= timeOut) {
            Intent i = new Intent(this, LocalLoginView.class);
            startActivity(i);
            this.finish();
            return false;
        } else {
            HtmlUserInfo info = new HtmlUserInfo();
            info.setAccess_token(accessToken);
            info.setExpires_in((int) (currTime - timeOut));
            info.setRefresh_token(refreshToke);
            info.setUid(uid);
            info.setToken_type(tokenType);
            HtmlUserInfoHolder.getInstance().setHtmlUserInfo(info);
            return true;
        }
    }

    private void initFirstInFragment() {
        MainBlogView blogView = new MainBlogView();
        replaceFragment(blogView);
    }

    private void initDrawerLayout() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_blog);
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("博客");
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_blog) {
            MainBlogView blogView = new MainBlogView();
            replaceFragment(blogView);
        } else if (id == R.id.nav_dongdan) {

        } else if (id == R.id.nav_synthesize) {

        } else if (id == R.id.nav_me) {

        } else if (id == R.id.nav_about) {

        }
        toolbar.setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_frame, fragment);
        ft.commit();
    }

    public void getDatas() {
        String accessToken = HtmlUserInfoHolder.getInstance().getHtmlUserInfo().getAccess_token();
//        Apis.Helper.getSimpleApi().getMyInformation(accessToken,"json")
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Action1<MyInformation>() {
//                    @Override
//                    public void call(final MyInformation myInformation) {
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                SimpleDraweeView imageview = (SimpleDraweeView) drawer.findViewById(R.id.header_icon);
//                                imageview.setImageURI(Uri.parse(myInformation.getPortrait()));
//                                TextView userName  = (TextView) drawer.findViewById(R.id.header_user_name);
//                                userName.setText(""+myInformation.getName());
//                                TextView address  = (TextView) drawer.findViewById(R.id.header_user_email);
//                                address.setText(""+myInformation.getCity());
//                                MyInformationHolder.getInstance().setMyInformation(myInformation);
//
//                            }
//                        });
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Toast.makeText(MainActivity.this,"获取用户信息失败。",Toast.LENGTH_SHORT).show();
//                    }
//                });
        Apis.Helper.getSimpleApi().getUser(accessToken, "json")
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(final User user) {
                        MyInformationHolder.getInstance().setMyInformation(user);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                SimpleDraweeView imageview = (SimpleDraweeView) drawer.findViewById(R.id.header_icon);
                                imageview.setImageURI(Uri.parse(user.getAvatar()));
                                TextView userName = (TextView) drawer.findViewById(R.id.header_user_name);
                                userName.setText("" + user.getName());
                                TextView address = (TextView) drawer.findViewById(R.id.header_user_email);
                                address.setText("" + user.getEmail());
                            }
                        });
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }
}
