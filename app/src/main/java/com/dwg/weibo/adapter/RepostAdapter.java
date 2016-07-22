package com.dwg.weibo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dwg.weibo.R;
import com.dwg.weibo.entity.Status;
import com.dwg.weibo.entity.User;
import com.dwg.weibo.ui.common.FillContent;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/22.
 */
public class RepostAdapter extends RecyclerView.Adapter<RepostAdapter.ViewHolder> {

    private ArrayList<Status> mDatas;
    private Context mContext;

    public RepostAdapter(Context context, ArrayList<Status> statuses) {
        this.mContext = context;
        this.mDatas = statuses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_repost, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Status status = mDatas.get(position);
        User user = status.user;
        FillContent.fillProfileImg(mContext, user, holder.profileImg, holder.profileVerified);
        FillContent.fillWeiBoContent(mContext, holder.commentText, status.text);
        FillContent.setWeiBoName(holder.profileName, user);
        FillContent.setWeiBoTime(mContext, holder.commentTime, status.created_at);
        FillContent.setWeiBoComeFrom(holder.weiboComeFrom, status.source);
    }

    public void setDatas(ArrayList<Status> statuses) {
        this.mDatas = statuses;
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
        @BindView(R.id.profile_img)
        SimpleDraweeView profileImg;
        @BindView(R.id.profile_name)
        TextView profileName;
        @BindView(R.id.comment_text)
        TextView commentText;
        @BindView(R.id.comment_time)
        TextView commentTime;
        @BindView(R.id.weiboComeFrom)
        TextView weiboComeFrom;
        @BindView(R.id.profile_verified)
        ImageView profileVerified;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
