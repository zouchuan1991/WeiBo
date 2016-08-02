package com.dwg.weibo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dwg.weibo.R;
import com.dwg.weibo.entity.Comment;
import com.dwg.weibo.ui.common.FillContent;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/1.
 */

public class CommentFragmentAdapter extends RecyclerView.Adapter<CommentFragmentAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Comment> mDatas;

    public CommentFragmentAdapter(Context mContext, ArrayList<Comment> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    public void setmDatas(ArrayList<Comment> commentList) {
        this.mDatas = commentList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.fragment_comment, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = mDatas.get(position);
        FillContent.fillToMeComment(mContext, holder, comment);
    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.repost_img)
        public SimpleDraweeView repostImg;
        @BindView(R.id.repost_name)
        public TextView repostName;
        @BindView(R.id.repost_content)
        public TextView repostContent;
        @BindView(R.id.repost_layout)
        public LinearLayout repostLayout;
        @BindView(R.id.comment_image)
        public SimpleDraweeView commentImage;
        @BindView(R.id.comment_name)
        public TextView commentName;
        @BindView(R.id.comment_time)
        public TextView commentTime;
        @BindView(R.id.reply)
        public TextView reply;
        @BindView(R.id.comment_content)
        public TextView commentContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
