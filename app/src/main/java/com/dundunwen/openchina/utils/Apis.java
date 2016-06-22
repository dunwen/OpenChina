package com.dundunwen.openchina.utils;

import com.dundunwen.openchina.bean.BlogDetail;
import com.dundunwen.openchina.bean.BlogList;
import com.dundunwen.openchina.bean.HtmlUserInfo;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by dun on 16/6/17.
 */
public interface Apis {
    static String baseUrl = "http://www.oschina.net/";

    @GET("/action/openapi/token")
    Observable<HtmlUserInfo> getAccessToken(@Query("client_id")String client_id,
                                            @Query("client_secret")String client_secret,
                                            @Query("grant_type")String grant_type,
                                            @Query("redirect_uri")String redirect_uri,
                                            @Query("code")String code,
                                            @Query("dataType")String dataType
                                            );
    @GET("/action/openapi/blog_list")
    Observable<BlogList> getSynthesizeBlogList(
            @Query("access_token")String access_token,
            @Query("page")int page,
            @Query("pageSize")int pageSize,
            @Query("dataType")String dataType
    );

    @GET("/action/openapi/blog_recommend_list")
    Observable<BlogList> getRecommentBlogList(
            @Query("access_token")String access_token,
            @Query("page")int page,
            @Query("pageSize")int pageSize,
            @Query("dataType")String dataType
    );
    @GET("/action/openapi/blog_detail")
    Observable<BlogDetail> getBlogDetail(
            @Query("access_token")String access_token,
            @Query("id")long id,
            @Query("dataType")String dataType
    );

    public static class Helper {
        public static Apis getSimpleApi() {
            return DefautReftAdapter.getDefautReftAdapter(baseUrl).build().create(Apis.class);
        }
    }

    public static class CookieHolder {
        HashSet<String> cookies = new HashSet<String>();
        private static CookieHolder holder;

        private CookieHolder() {
        }

        public static CookieHolder getInstance() {
            if (holder == null) {
                holder = new CookieHolder();
            }
            return holder;
        }

        public HashSet<String> getCookies() {
            return cookies;
        }

        public void setCookies(HashSet<String> cookies) {
            this.cookies = cookies;
        }
    }


    public static class DefautReftAdapter {
        private static String cookieString = null;

        public static String getCookieString() {
            return cookieString;
        }

        public static void setCookieString(String cookieString) {
            DefautReftAdapter.cookieString = cookieString;
        }

        public static class AddCookiesInterceptor implements Interceptor {

            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                HashSet<String> preferences = CookieHolder.getInstance().getCookies();
                for (String cookie : preferences) {
                    builder.addHeader("Cookie", cookie);
                }
                return chain.proceed(builder.build());
            }
        }

        public static class ReceivedCookiesInterceptor implements Interceptor {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());

                if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                    HashSet<String> cookies = new HashSet();

                    for (String header : originalResponse.headers("Set-Cookie")) {
                        cookies.add(header);
                    }


                    CookieHolder.getInstance().setCookies(cookies);
                }
                return originalResponse;
            }
        }

        public static Retrofit.Builder getDefautReftAdapter(String baseUrl) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AddCookiesInterceptor())
                    .addInterceptor(new ReceivedCookiesInterceptor())
                    .build();
            return new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .client(client);
        }
    }
}
