package com.dwg.weibo.ui.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dwg.weibo.R;
import com.dwg.weibo.entity.Status;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Administrator on 2016/7/17.
 */
public class OriginHeaderView extends LinearLayout {

  @BindView(R.id.profile_img) SimpleDraweeView profileImg;
  @BindView(R.id.profile_name) TextView profileName;
  @BindView(R.id.profile_verified) ImageView profileVerified;
  @BindView(R.id.profile_time) TextView profileTime;
  @BindView(R.id.weiboComeFrom) TextView weiboComeFrom;
  @BindView(R.id.popover_arrow) ImageView popoverArrow;
  @BindView(R.id.weibo_content) TextView weiboContent;
  @BindView(R.id.weibo_images) RecyclerView weiboImages;
  @BindView(R.id.text_favour) TextView textFavour;
  @BindView(R.id.linear_favour) LinearLayout linearFavour;
  @BindView(R.id.text_comment) TextView textComment;
  @BindView(R.id.linear_comment) LinearLayout linearComment;
  @BindView(R.id.text_transmit) TextView textTransmit;
  @BindView(R.id.linear_transmit) LinearLayout linearTransmit;
  @BindView(R.id.body) RelativeLayout body;
  @BindView(R.id.commentBar_retweet) TextView commentBarRetweet;
  @BindView(R.id.retweet_indicator) ImageView retweetIndicator;
  @BindView(R.id.commentBar_comment) TextView commentBarComment;
  @BindView(R.id.comment_indicator) ImageView commentIndicator;
  @BindView(R.id.commentBar_like) TextView commentBarLike;
  @BindView(R.id.status_bottom_bar) LinearLayout linearLayout;
  private Context mContext;
  private View mView;
  private Status mStatus;

  public OriginHeaderView(Context context, Status status, int Type) {
    super(context);
    this.mContext = context;
    this.mStatus = status;
    init();
  }

  private void init() {
    this.mView = inflate(mContext, R.layout.activity_status_detail_original, this);
    ButterKnife.bind(this, mView);
    initContent();
  }

  private void initContent() {
    linearLayout.setVisibility(GONE);
    FillContent.fillTitle(mContext, mStatus, profileImg, profileVerified, profileName, profileTime,
        weiboComeFrom);
    FillContent.fillWeiBoContent(mContext, weiboContent, mStatus.text);
    FillContent.fillFloatBar(mContext, mStatus, commentBarComment, commentBarRetweet,
        commentBarLike);
    FillContent.fillWeiBoImgList(mContext, mStatus, weiboImages);
  }
}
