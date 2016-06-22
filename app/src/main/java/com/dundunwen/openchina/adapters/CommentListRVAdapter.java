package com.dundunwen.openchina.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dundunwen.openchina.R;
import com.dundunwen.openchina.bean.CommentInfo;
import com.dundunwen.openchina.bean.Refer;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by dun on 16/6/22.
 */
public class CommentListRVAdapter extends RecyclerView.Adapter<CommentListRVAdapter.MyViewHolder>{
    List<CommentInfo> list;
    boolean isNoMoreData = false;
    private static int typeCommon = 0;
    private static int typeFoot = 1;
    public CommentListRVAdapter(List<CommentInfo> list) {
        this.list = list;
    }
    ItemClickListener listener;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        MyViewHolder holder = null;
        if(viewType == typeCommon){
            View view = lf.inflate(R.layout.comment_list_item,parent,false);
            holder = new MyViewHolder(view);
        }else if(viewType == typeFoot){
            View view = lf.inflate(R.layout.rv_foot_loadmore,parent,false);
            holder = new MyViewHolder(view);
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if(position<list.size()){
            return typeCommon;
        }else{
            return typeFoot;
        }
    }

    public ItemClickListener getListener() {
        return listener;
    }

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(position<list.size()){
            holder.bindData(list.get(position));
            holder.setListener(listener,position);
        }else{
            holder.setIsNoMoreData(isNoMoreData);
        }
    }

    public void isNoMoreData(boolean is){
        if(is){
            this.isNoMoreData = is;
            this.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView iv_headIcon;
        TextView tv_author;
        TextView tv_refer;
        TextView tv_comment;
        ProgressBar pv;
        TextView tv_footMsg;
        View itemView;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            iv_headIcon = (SimpleDraweeView) itemView.findViewById(R.id.commentlist_item_head_icon);
            tv_author = (TextView) itemView.findViewById(R.id.commentlist_item_author);
            tv_comment = (TextView) itemView.findViewById(R.id.commentlist_item_comment);
            tv_refer = (TextView) itemView.findViewById(R.id.commentlist_item_refers);
            pv = (ProgressBar) itemView.findViewById(R.id.foot_pb);
            tv_footMsg = (TextView) itemView.findViewById(R.id.foot_tv_msg);
        }

        public void bindData(CommentInfo info){
            iv_headIcon.setImageURI(Uri.parse(info.getPortrait()));
            tv_author.setText(info.getAuthor());
            tv_comment.setText(info.getContent());
            List<Refer> refers = info.getRefers();
            if(refers!=null&&refers.size()>0){
                Refer refer = refers.get(refers.size()-1);
                String content = refer.getTitle()+"\n"+refer.getBody();
                tv_refer.setText(content);
            }else{
                tv_refer.setVisibility(View.GONE);
            }
        }

        public void setListener(final ItemClickListener listener, final int positon){
            if(listener!=null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick(positon);
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
    interface ItemClickListener{
        void onClick(int position);
    }
}
