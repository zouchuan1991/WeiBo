package com.dwg.weibo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.dwg.weibo.R;
import com.dwg.weibo.entity.Comment;
import com.dwg.weibo.entity.User;
import com.dwg.weibo.ui.common.FillContent;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/17.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
  private ArrayList<Comment> mDatas;
  private Context mContext;
  private View mView;

  public CommentAdapter(Context context, ArrayList<Comment> datas) {
    this.mContext = context;
    this.mDatas = datas;
  }

  @Override
  public CommentAdapter.CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    mView = LayoutInflater.from(mContext).inflate(R.layout.list_item_comment, parent, false);
    return new CommentViewHolder(mView);
  }

  @Override public void onBindViewHolder(CommentAdapter.CommentViewHolder holder, int position) {
    Comment comment = mDatas.get(position);
    User user = comment.user;
    FillContent.fillProfileImg(mContext, user, holder.profileImg, holder.profileVerified);
    FillContent.fillWeiBoContent(mContext, holder.commentText, comment.text);
    FillContent.setWeiBoName(holder.profileName, user);
    FillContent.setWeiBoTime(mContext, holder.commentTime, comment.created_at);
    FillContent.setWeiBoComeFrom(holder.weiboComeFrom, comment.source);
  }

  @Override public int getItemCount() {
    if (mDatas != null) {
      return mDatas.size();
    } else {
      return 0;
    }
  }

  public class CommentViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.profile_img) SimpleDraweeView profileImg;
    @BindView(R.id.profile_name) TextView profileName;
    @BindView(R.id.comment_text) TextView commentText;
    @BindView(R.id.comment_time) TextView commentTime;
    @BindView(R.id.weiboComeFrom) TextView weiboComeFrom;
    @BindView(R.id.profile_verified) ImageView profileVerified;

    public CommentViewHolder(View itemView) {
      super(itemView);
    }
  }
}
