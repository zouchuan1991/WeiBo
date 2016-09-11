package com.dwg.weibo.ui.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dwg.weibo.R;
import com.dwg.weibo.adapter.CommentFragmentAdapter;
import com.dwg.weibo.adapter.ImageAdapter;
import com.dwg.weibo.entity.Comment;
import com.dwg.weibo.entity.Status;
import com.dwg.weibo.entity.User;
import com.dwg.weibo.service.PostService;
import com.dwg.weibo.ui.activity.HandleAcitivity;
import com.dwg.weibo.utils.DateUtils;
import com.dwg.weibo.utils.TimeUtils;
import com.dwg.weibo.utils.ToastUtils;
import com.dwg.weibo.utils.WeiboContent;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/11.
 */

public class FillContent {
    public static void fillTitle(Context context, Status status, SimpleDraweeView profile_img, ImageView profile_verified, TextView profile_name, TextView profile_time, TextView weibo_comefrom) {
        fillProfileImg(context,status.user,profile_img,profile_verified);
        setWeiBoName(profile_name, status.user);
        setWeiBoTime(context, profile_time, status.created_at);
        setWeiBoComeFrom(weibo_comefrom, status.source);
    }

    public static void setWeiBoComeFrom(TextView weibo_comefrom, String source) {
        //正则表达处理
        weibo_comefrom.setText("来自    " + FillContentHelper.setSource(source));
    }

    public static void setWeiBoTime(Context context, TextView profile_time, String created_at) {
        Date data = DateUtils.parseDate(created_at, DateUtils.WeiBo_ITEM_DATE_FORMAT);
        TimeUtils timeUtils = TimeUtils.instance(context);
        profile_time.setText(timeUtils.buildTimeString(data.getTime())+"    ");

    }

    public static void fillProfileImg(final Context context, final User user, final SimpleDraweeView profile_img, final ImageView profile_verified){
        if (user.verified == true && user.verified_type == 0) {
            profile_verified.setImageResource(R.drawable.avatar_vip);
        } else if (user.verified == true && (user.verified_type == 1 || user.verified_type == 2 || user.verified_type == 3)) {
            profile_verified.setImageResource(R.drawable.avatar_enterprise_vip);
        } else if (user.verified == false && user.verified_type == 220) {
            profile_verified.setImageResource(R.drawable.avatar_grassroot);
        } else {
            profile_verified.setVisibility(View.INVISIBLE);
        }
        profile_img.setImageURI(Uri.parse(user.avatar_hd));
    }
    public static void setWeiBoName(TextView textView, User user) {
        if (user.remark != null && user.remark.length() > 0) {
            textView.setText(user.remark);
        } else {
            textView.setText(user.name);
        }
    }

    public static void fillWeiBoContent(Context mContext, TextView weibo_content, String text) {
        weibo_content.setText(WeiboContent.getSpannableStringBuilder(mContext, text));
    }

    public static void fillButtonBar(final Context context, final Status status, LinearLayout linearComment, LinearLayout linearTransmit, LinearLayout linearFavour, TextView comment, TextView transmit, TextView favour) {
        if (status.comments_count != 0) {
            comment.setText(status.comments_count + "");
        } else {
            comment.setText("评论");

        }

        if (status.reposts_count != 0) {
            transmit.setText(status.reposts_count + "");
        } else {
            transmit.setText("转发");
        }

        if (status.attitudes_count != 0) {
            favour.setText(status.attitudes_count + "");
        } else {
            favour.setText("赞");
        }
        fillButtonBar(context, status, linearComment, linearTransmit, linearFavour);
    }

    public static void fillButtonBar(final Context context, final Status status, LinearLayout linearComment, LinearLayout linearTransmit, LinearLayout linearFavour) {
        linearComment.setOnClickListener(new View.OnClickListener() {//评论
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, HandleAcitivity.class);
                i.putExtra("POST_TYPE", PostService.POST_COMMNET_WEIBO);
                i.putExtra("createStatus", status);
                context.startActivity(i);
            }
        });
        linearFavour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(context, "点击了赞");
//                Intent i = new Intent(context, PostService.class);
//                i.putExtra("POST_TYPE", PostService.POST_FAVOUR_WEIBO);
//                i.putExtra("createCommentForStatus", status);
//                context.startActivity(i);
            }
        });
        linearTransmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, HandleAcitivity.class);
                i.putExtra("POST_TYPE", PostService.POST_REPOST_WEIBO);
                i.putExtra("createStatus", status);
                context.startActivity(i);
            }
        });
    }

    public static void fillFloatBar(final Context context, final Status status, TextView comment,
        TextView transmit, TextView favour) {
        if (status.comments_count != 0) {
            comment.setText(status.comments_count + "  评论");
        }

        if (status.reposts_count != 0) {
            transmit.setText(status.reposts_count + "  转发");
        }

        if (status.attitudes_count != 0) {
            favour.setText(status.attitudes_count + "  赞");
        }

    }

    public static void fillWeiBoImgList(Context context, Status status, RecyclerView weibo_images) {

        ArrayList<String> imageDatas = status.thumbnail_pic_urls;
        Log.e("TAG", "图片的数量为>>>" + imageDatas.size());
        if(imageDatas!=null&&imageDatas.size()>0)
        weibo_images.setHasFixedSize(true);
        ImageAdapter imageAdapter = new ImageAdapter(context,imageDatas);
        weibo_images.setAdapter(imageAdapter);
        weibo_images.setLayoutManager(initGridLayoutManager(context,imageDatas));

        imageAdapter.setData(imageDatas);

        imageAdapter.notifyDataSetChanged();


    }

    private static GridLayoutManager initGridLayoutManager(Context context, ArrayList<String> imageDatas) {
        GridLayoutManager gridLayoutManager;
        if ( imageDatas!= null) {
            switch (imageDatas.size()) {
                case 1:
                    gridLayoutManager = new GridLayoutManager(context, 2);
                    break;
                case 2:
                    gridLayoutManager = new GridLayoutManager(context, 2);
                    break;
                case 3:
                    gridLayoutManager = new GridLayoutManager(context, 3);
                    break;
                case 4:
                    gridLayoutManager = new GridLayoutManager(context, 2);
                    break;
                default:
                    gridLayoutManager = new GridLayoutManager(context, 3);
                    break;
            }
        }else{
            gridLayoutManager = new GridLayoutManager(context, 3);
        }
        return gridLayoutManager;
    }

    public static void fillRetweetContent(Context mContext, Status status, TextView retweetContent) {
        if (status.retweeted_status.user != null){
            StringBuffer retweetcontent_buffer = new StringBuffer();
            retweetcontent_buffer.setLength(0);
            retweetcontent_buffer.append("@");
            retweetcontent_buffer.append(status.retweeted_status.user.name + " :  ");
            retweetcontent_buffer.append(status.retweeted_status.text);
            retweetContent.setText(WeiboContent.getSpannableStringBuilder(mContext, retweetcontent_buffer.toString()));
        }else{
            retweetContent.setText("抱歉，此微博已被作者删除。");
        }
    }

    public static void fillRepostOriginContent(Context mContext, Status mPostStatus, SimpleDraweeView repostImg, TextView repostName, TextView repostContent) {
        if (mPostStatus.bmiddle_pic != null) {
            repostImg.setImageURI(Uri.parse(mPostStatus.bmiddle_pic));
        } else {
            repostImg.setImageURI(Uri.parse(mPostStatus.user.avatar_hd));
        }
        repostName.setText("@" + mPostStatus.user.name);
        repostContent.setText(WeiboContent.getSpannableStringBuilder(mContext, mPostStatus.text));
    }

    public static void fillUserInfo(Context mContext, User user, SimpleDraweeView backImage,
                                    SimpleDraweeView userIcon, TextView userNickName,
                                    TextView friendsCount, TextView followsCount, ImageView sex) {
        backImage.setImageURI(user.cover_image_phone);
        userIcon.setImageURI(user.avatar_hd);
        userNickName.setText(user.screen_name);
        friendsCount.setText("关注  " + user.friends_count);
        followsCount.setText("粉丝  " + user.followers_count);
        if (user.gender.equals("m")) {
            sex.setImageResource(R.drawable.list_male);
        } else if (user.gender.equals("f")) {
            sex.setImageResource(R.drawable.list_female);
        }
    }

    public static void fillToMeComment(Context context, CommentFragmentAdapter.ViewHolder holder, Comment comment) {
        if (comment.status.retweeted_status != null) {
            FillContent.fillRepostOriginContent(context, comment.status.retweeted_status, holder.repostImg, holder.repostName, holder.repostContent);
        } else {
            FillContent.fillRepostOriginContent(context, comment.status, holder.repostImg, holder.repostName, holder.repostContent);
        }
        holder.commentImage.setImageURI(comment.user.avatar_hd);
        holder.commentName.setText(comment.user.screen_name);
        setWeiBoTime(context, holder.commentTime, comment.created_at);
        holder.commentContent.setText(WeiboContent.getSpannableStringBuilder(context, comment.text));
    }
}
