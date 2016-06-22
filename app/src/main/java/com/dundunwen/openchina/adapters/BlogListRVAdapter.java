package com.dundunwen.openchina.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dundunwen.openchina.R;
import com.dundunwen.openchina.bean.BlogSummary;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dun on 16/6/22.
 */
public class BlogListRVAdapter extends RecyclerView.Adapter<BlogListRVAdapter.MyViewHolder>{
    List<BlogSummary> blogSummaries;
    private static int typeCommon = 0;
    private static int typeFoot = 1;
    private ItemClick listener;

    public BlogListRVAdapter(List<BlogSummary> blogSummaries) {
        this.blogSummaries = blogSummaries;
    }

    public ItemClick getListener() {
        return listener;
    }

    public void setListener(ItemClick listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        MyViewHolder holder = null;
        if(viewType == typeCommon){
            View view = lf.inflate(R.layout.sub_blog_rv_item,parent,false);
            holder = new MyViewHolder(view);
        }else if(viewType == typeFoot){
            View view = lf.inflate(R.layout.rv_foot_loadmore,parent,false);
            holder = new MyViewHolder(view);
        }
        return holder;
    }
    boolean isNoMoreData = false;
    public void isNoMoreData(boolean is){
        if(is){
            this.isNoMoreData = is;
            this.notifyDataSetChanged();
        }
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(position<blogSummaries.size()){
            holder.bindDatas(blogSummaries.get(position),listener,position);
        }else{
            holder.setIsNoMoreData(isNoMoreData);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position<blogSummaries.size()){
            return typeCommon;
        }else{
            return typeFoot;
        }
    }

    @Override
    public int getItemCount() {
        return blogSummaries.size()+1;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.rvitem_tv_blog_title)
        TextView tv_title;
        @BindView(R.id.rvitem_tv_comment_time)
        TextView tv_commentTime;
        @BindView(R.id.rvitem_tv_blog_author)
        TextView tv_author;
        @BindView(R.id.rvitem_tv_pub_time)
        TextView tv_pubTime;
        View itemView;
        ProgressBar pv;
        TextView tv_footMsg;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tv_title = (TextView) itemView.findViewById(R.id.rvitem_tv_blog_title);
            tv_commentTime = (TextView) itemView.findViewById(R.id.rvitem_tv_comment_time);
            tv_author = (TextView) itemView.findViewById(R.id.rvitem_tv_blog_author);
            tv_pubTime = (TextView) itemView.findViewById(R.id.rvitem_tv_pub_time);
            pv = (ProgressBar) itemView.findViewById(R.id.foot_pb);
            tv_footMsg = (TextView) itemView.findViewById(R.id.foot_tv_msg);

        }
        public void bindDatas(BlogSummary summary, final ItemClick listener, final int positon){
            if(tv_title==null||tv_pubTime==null||tv_author==null||tv_commentTime==null){
                return;
            }
            tv_commentTime.setText(summary.getCommentCount()+"");
            tv_author.setText(summary.getAuthor());
            tv_pubTime.setText(summary.getPubDate());
            tv_title.setText(summary.getTitle());
            if(listener!=null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(positon);
                    }
                });
            }
        }

        public void setIsNoMoreData(boolean is){
            if(pv!=null&&tv_footMsg!=null){
                if(is){
                    pv.setVisibility(View.GONE);
                    tv_footMsg.setText("没有更多数据啦~");
                }else{
                    pv.setVisibility(View.VISIBLE);
                    tv_footMsg.setText("正在加载数据...");
                }

            }
        }
    }

    public interface ItemClick{
        void onItemClick(int positon);
    }
}
