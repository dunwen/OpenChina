package com.dundunwen.openchina.blog_unit.bloginfo;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dundunwen.openchina.BaseActivity;
import com.dundunwen.openchina.R;
import com.dundunwen.openchina.adapters.CommentListRVAdapter;
import com.dundunwen.openchina.bean.CommentInfo;
import com.dundunwen.openchina.bean.CommentList;
import com.dundunwen.openchina.singleton.HtmlUserInfoHolder;
import com.dundunwen.openchina.utils.Apis;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class CommentListActivity extends BaseActivity {

    @BindView(R.id.commentlist_refreshlayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.commentlist_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.commentlist_btn_send)
    Button btn_send;
    @BindView(R.id.commentlist_et_comment)
    EditText et_comment;

    List<CommentInfo> commentInfos = new ArrayList<>();
    int currentPage = 0;
    Handler mHandler;
    long id;
    public static final String KEY_ID = "KEY_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        ButterKnife.bind(this);
        mHandler = new Handler(getMainLooper());

        Bundle b = getIntent().getExtras();
        if (b == null) {
            Logger.e("bundle is null?");
        } else {
            id = b.getLong(KEY_ID);
        }

        findViews();
        initViews();
        getDatas();
    }

    @Override
    public void findViews() {

    }

    @Override
    public void initViews() {
        getSupportActionBar().setTitle("评论列表");
        mRefreshLayout.setProgressViewOffset(true, 0, 100);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCommentList(false);
            }
        });
    }

    boolean isNoMoreData = false;

    void getCommentList(boolean isLoadMore) {
        if (!isLoadMore) {
            if (mRefreshLayout.isRefreshing()) {
                mRefreshLayout.setRefreshing(true);
            }
            commentInfos.clear();
            currentPage = 0;
        } else {
            if (isLoadMore) {
                return;
            }
        }
        final int from = commentInfos.size();
        String accessToken = HtmlUserInfoHolder.getInstance().getHtmlUserInfo().getAccess_token();
        Apis.Helper.getSimpleApi().getCommentList(accessToken, Integer.parseInt(id + ""), currentPage++, 20, "json")
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<CommentList>() {
                    @Override
                    public void call(final CommentList commentList) {
                        commentInfos.addAll(commentList.getCommentlist());
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                CommentListRVAdapter adapter = (CommentListRVAdapter) mRecyclerView.getAdapter();
                                if (adapter != null) {
                                    if (from == 0) {
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        adapter.notifyItemChanged(from, commentInfos.size());
                                    }
                                } else {
                                    initAdapter(adapter);
                                }
                                if (commentList.getCommentlist().size() < 20) {
                                    ((CommentListRVAdapter) mRecyclerView.getAdapter()).isNoMoreData(true);
                                    isNoMoreData = true;
                                }
                                mRefreshLayout.setRefreshing(false);
                            }
                        });


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.i("发生错误");
                        Toast.makeText(CommentListActivity.this, "获取评论失败", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void initAdapter(CommentListRVAdapter adapter) {
        adapter = new CommentListRVAdapter(commentInfos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isScrolledToBottom = true;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastItem = llm.findLastCompletelyVisibleItemPosition();
                int allItem = llm.getItemCount() - 2;
//                Log.i(">>>>", "onScrollStateChanged: last>"+lastItem+" all>"+allItem);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (lastItem >= allItem) {
                        getCommentList(false);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    isScrolledToBottom = true;
                } else {
                    isScrolledToBottom = false;
                }
            }
        });
    }

    @OnClick(R.id.commentlist_btn_send)
    void ClickSend(View v) {
        String content = et_comment.getText().toString();
        if (!TextUtils.isEmpty(content)) {
            String accessToken = HtmlUserInfoHolder.getInstance().getHtmlUserInfo().getAccess_token();
            Apis.Helper.getSimpleApi().commentPub(accessToken, id, content, "json")
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                if (response.body().string().contains("200")) {
                                    Toast.makeText(CommentListActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                                    getCommentList(false);
                                } else {
                                    Toast.makeText(CommentListActivity.this, "评论失败啦", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(CommentListActivity.this, "评论失败啦", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    @Override
    public void getDatas() {
        getCommentList(false);
    }
}
