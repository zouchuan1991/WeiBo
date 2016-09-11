package com.dwg.weibo.ui.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dwg.weibo.R;
import com.dwg.weibo.entity.Status;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/21.
 */
public class RetweetHeaderView extends LinearLayout {
    @BindView(R.id.profile_img)
    SimpleDraweeView profileImg;
    @BindView(R.id.profile_name)
    TextView profileName;
    @BindView(R.id.profile_verified)
    ImageView profileVerified;
    @BindView(R.id.profile_time)
    TextView profileTime;
    @BindView(R.id.weiboComeFrom)
    TextView weiboComeFrom;
    @BindView(R.id.popover_arrow)
    ImageView popoverArrow;
    @BindView(R.id.origin_context)
    TextView originContext;
    @BindView(R.id.retweet_content)
    TextView retweetContent;
    @BindView(R.id.weibo_images)
    RecyclerView weiboImages;
    @BindView(R.id.retweetStatus_layout)
    LinearLayout retweetLayout;
    @BindView(R.id.text_favour)
    TextView textFavour;
    @BindView(R.id.linear_favour)
    LinearLayout linearFavour;
    @BindView(R.id.text_comment)
    TextView textComment;
    @BindView(R.id.linear_comment)
    LinearLayout linearComment;
    @BindView(R.id.text_transmit)
    TextView textTransmit;
    @BindView(R.id.linear_transmit)
    LinearLayout linearTransmit;
    @BindView(R.id.body)
    RelativeLayout body;
    @BindView(R.id.retweet_status_layout)
    LinearLayout retweetStatusLayout;
    @BindView(R.id.commentBar_retweet)
    TextView commentBarRetweet;
    @BindView(R.id.retweet_indicator)
    ImageView retweetIndicator;
    @BindView(R.id.commentBar_comment)
    TextView commentBarComment;
    @BindView(R.id.comment_indicator)
    ImageView commentIndicator;
    @BindView(R.id.commentBar_like)
    TextView commentBarLike;
    @BindView(R.id.bottom)
    LinearLayout linearLayout;
    private Context mContext;
    private Status mStatus;
    private View mView;

    public RetweetHeaderView(Context context, Status status) {
        super(context);
        this.mContext = context;
        this.mStatus = status;
        init();

    }

    private void init() {
        mView = inflate(mContext, R.layout.activity_stats_detail_retweet, this);
        ButterKnife.bind(this, mView);
        initContext();
    }

    private void initContext() {
        linearLayout.setVisibility(GONE);
        FillContent.fillTitle(mContext, mStatus, profileImg, profileVerified, profileName, profileTime,
                weiboComeFrom);
        FillContent.fillRetweetContent(mContext, mStatus, retweetContent);
        FillContent.fillWeiBoContent(mContext, originContext, mStatus.text);
        FillContent.fillFloatBar(mContext, mStatus, commentBarComment, commentBarRetweet,
                commentBarLike);
        FillContent.fillWeiBoImgList(mContext, mStatus.retweeted_status, weiboImages);

    }
}
