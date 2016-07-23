package com.dwg.weibo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dwg.weibo.R;
import com.dwg.weibo.entity.Status;
import com.dwg.weibo.ui.activity.OriginStatusDetailActivity;
import com.dwg.weibo.ui.activity.RetweetStatusDetailActivity;
import com.dwg.weibo.ui.common.FillContent;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/11.
 */

public class FragmentHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ORINGIN_ITEM = 0;
    private static final int TYPE_RETWEET_ITEM = 1;
    private ArrayList<Status> mDatas;
    private Context mContext;
    private View mView;

    public FragmentHomeAdapter(Context context, ArrayList<Status> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
    }

    public void setDatas(ArrayList<Status> statuses) {
        this.mDatas = statuses;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ORINGIN_ITEM) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.list_item_status, parent, false);
            return new OriginViewHolder(mView);

        } else if (viewType == TYPE_RETWEET_ITEM) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.list_item_retweet_status, parent, false);
            return new RetweetViewHolder(mView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position != mDatas.size()) {
            Status status = mDatas.get(position);
            if (holder instanceof OriginViewHolder) {
                FillContent.fillTitle(mContext, status, ((OriginViewHolder) holder).profile_img, ((OriginViewHolder) holder).profile_verified, ((OriginViewHolder) holder).profile_name, ((OriginViewHolder) holder).profile_time, ((OriginViewHolder) holder).weiboComeFrom);
                FillContent.fillWeiBoContent(mContext, ((OriginViewHolder) holder).weibo_content, status.text);
                FillContent.fillButtonBar(mContext, status, ((OriginViewHolder) holder).linear_comment, ((OriginViewHolder) holder).linear_transmit, ((OriginViewHolder) holder).linear_favour, ((OriginViewHolder) holder).text_comment, ((OriginViewHolder) holder).text_transmit, ((OriginViewHolder) holder).text_favour);
                FillContent.fillWeiBoImgList(mContext, status, ((OriginViewHolder) holder).weibo_images);
                ((OriginViewHolder) holder).origin_status_layout.setOnClickListener(
                    new View.OnClickListener() {
                        @Override public void onClick(View v) {
                            Intent i = new Intent(mContext, OriginStatusDetailActivity.class);
                            i.putExtra("status", mDatas.get(position));
                            mContext.startActivity(i);
                        }
                    });
            } else if (holder instanceof RetweetViewHolder) {
                FillContent.fillTitle(mContext, status, ((RetweetViewHolder) holder).profileImg, ((RetweetViewHolder) holder).profileVerified, ((RetweetViewHolder) holder).profileName, ((RetweetViewHolder) holder).profileTime, ((RetweetViewHolder) holder).weiboComeFrom);
                FillContent.fillRetweetContent(mContext, status, ((RetweetViewHolder) holder).retweetContent);
                FillContent.fillWeiBoContent(mContext, ((RetweetViewHolder) holder).originContext, status.text);
                FillContent.fillButtonBar(mContext, status, ((RetweetViewHolder) holder).linear_comment, ((RetweetViewHolder) holder).linearTransmit, ((RetweetViewHolder) holder).linearFavour, ((RetweetViewHolder) holder).textComment, ((RetweetViewHolder) holder).textTransmit, ((RetweetViewHolder) holder).textFavour);
                FillContent.fillWeiBoImgList(mContext, mDatas.get(position).retweeted_status, ((RetweetViewHolder) holder).weiboImages);
                ((RetweetViewHolder) holder).retweet_status_layout.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(mContext, RetweetStatusDetailActivity.class);
                                i.putExtra("status", mDatas.get(position));
                                mContext.startActivity(i);
                            }
                        });
            }

        }
    }


    @Override
    public int getItemCount() {
        if (mDatas.size() == 0) {
            return 0;
        } else {
            return mDatas.size();
        }

    }

    @Override
    public int getItemViewType(int position) {

        if (mDatas.get(position).retweeted_status != null) {
            return TYPE_RETWEET_ITEM;
        } else {
            return TYPE_ORINGIN_ITEM;
        }
    }


    public static class OriginViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.profile_img)
        SimpleDraweeView profile_img;
        @BindView(R.id.profile_verified)
        ImageView profile_verified;
        @BindView(R.id.profile_name)
        TextView profile_name;
        @BindView(R.id.profile_time)
        TextView profile_time;
        @BindView(R.id.weiboComeFrom)
        TextView weiboComeFrom;
        @BindView(R.id.weibo_content)
        TextView weibo_content;
        @BindView(R.id.weibo_images)
        RecyclerView weibo_images;
        @BindView(R.id.linear_favour)
        LinearLayout linear_favour;
        @BindView(R.id.linear_comment)
        LinearLayout linear_comment;
        @BindView(R.id.linear_transmit)
        LinearLayout linear_transmit;
        @BindView(R.id.text_comment)
        TextView text_comment;
        @BindView(R.id.text_favour)
        TextView text_favour;
        @BindView(R.id.text_transmit)
        TextView text_transmit;
        @BindView(R.id.origin_status_layout)
        LinearLayout origin_status_layout;
        public OriginViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public static class RetweetViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.profile_img)
        SimpleDraweeView profileImg;
        @BindView(R.id.profile_verified)
        ImageView profileVerified;
        @BindView(R.id.profile_name)
        TextView profileName;
        @BindView(R.id.profile_time)
        TextView profileTime;
        @BindView(R.id.weiboComeFrom)
        TextView weiboComeFrom;
        @BindView(R.id.popover_arrow)
        ImageView popoverArrow;
        @BindView(R.id.retweet_content)
        TextView retweetContent;
        @BindView(R.id.origin_context)
        TextView originContext;
        @BindView(R.id.weibo_images)
        RecyclerView weiboImages;
        @BindView(R.id.retweetStatus_layout)
        LinearLayout retweetStatusLayout;
        @BindView(R.id.text_favour)
        TextView textFavour;
        @BindView(R.id.linear_favour)
        LinearLayout linearFavour;
        @BindView(R.id.text_comment)
        TextView textComment;
        @BindView(R.id.linear_comment)
        LinearLayout linear_comment;
        @BindView(R.id.text_transmit)
        TextView textTransmit;
        @BindView(R.id.linear_transmit)
        LinearLayout linearTransmit;
        @BindView(R.id.retweet_status_layout)
        LinearLayout retweet_status_layout;
        public RetweetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
