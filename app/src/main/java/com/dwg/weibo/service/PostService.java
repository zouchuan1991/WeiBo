package com.dwg.weibo.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dwg.weibo.api.ApiService;
import com.dwg.weibo.entity.Status;
import com.dwg.weibo.entity.WeiBoCreateBean;
import com.dwg.weibo.mvp.model.IPostService;
import com.dwg.weibo.utils.ShareSdkUtils;
import com.dwg.weibo.utils.ToastUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/27.
 */

public class PostService extends Service {
    private Context mContext;
    private String mPostType;
    public static final String POST_REPOST_WEIBO = "转发微博";
    public static final String POST_CREATE_WEIBO = "创建微博";
    public static final String POST_COMMNET_WEIBO = "评论微博";
    public static final String POST_REPLY_WEIBO = "回复微博";

    public static final String POST_FAVOUR_WEIBO = "收藏微博";
    private WeiBoCreateBean mWeiBoCreateBean;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Log.e("TAG", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mWeiBoCreateBean = intent.getParcelableExtra("weiboCreateBean");
        mPostType = intent.getStringExtra("POST_TYPE");
        switch (mPostType) {
            case POST_COMMNET_WEIBO:
                commentWeibo();
                break;
            case POST_CREATE_WEIBO:
                createWeibo();
                break;
            case POST_REPLY_WEIBO:
                replyWeibo();
                break;
            case POST_REPOST_WEIBO:
                repostWeibo();
                break;
            case POST_FAVOUR_WEIBO:
                favourWeibo();
                break;
        }


        return super.onStartCommand(intent, flags, startId);
    }

    private void favourWeibo() {
        IPostService postService = ApiService.createPostService();
        Call<Status> model = postService.favourWeibo(
                ShareSdkUtils.getToken(mContext),
                mWeiBoCreateBean.status.id
        );
        model.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                ToastUtils.showToast(mContext, "收藏微博成功");
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                ToastUtils.showToast(mContext, "收藏失败");
            }
        });
    }

    /**
     * 转发微博
     */
    private void repostWeibo() {
        IPostService postService = ApiService.createPostService();
        Call<Status> model = postService.repostWeibo(
                ShareSdkUtils.getToken(mContext),
                mWeiBoCreateBean.status.id,
                mWeiBoCreateBean.content,
                0
        );
        model.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                ToastUtils.showToast(mContext, "转发成功");
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                ToastUtils.showToast(mContext, "转发失败");
            }
        });
    }

    /**
     * 回复微博
     */
    private void replyWeibo() {
        ToastUtils.showToast(mContext, "回复微博");
    }

    /**
     * 创建微博
     */
    private void createWeibo() {
        if (mWeiBoCreateBean.selectImgList != null && mWeiBoCreateBean.selectImgList.size() > 0) {
            sendTextImgContext(mWeiBoCreateBean);
        } else {
            sendTextContext(mWeiBoCreateBean);
        }
    }

    /**
     * 评论微博
     */
    private void commentWeibo() {
        IPostService postService = ApiService.createPostService();
        Call<Status> model = postService.createComment(
                ShareSdkUtils.getToken(mContext),
                mWeiBoCreateBean.content,
                mWeiBoCreateBean.status.id,
                0
        );
        model.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                ToastUtils.showToast(mContext, "评论微博成功");
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                ToastUtils.showToast(mContext, "评论微博失败");
            }
        });
    }

    /**
     * 发送纯文本微博
     *
     * @param weiBoCreateBean
     */
    private void sendTextContext(WeiBoCreateBean weiBoCreateBean) {

        IPostService postService = ApiService.createPostService();
        Call<Status> statusCall = postService.sendTextContext(
                ShareSdkUtils.getToken(mContext),
                weiBoCreateBean.content
        );
        statusCall.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                ToastUtils.showToast(mContext, "发布成功");
//                Log.e("createTime",response.body().created_at);
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                ToastUtils.showToast(mContext, "发布失败");
            }
        });
    }

    /**
     * 发送图片和文本微博
     *
     * @param weiBoCreateBean
     */
    private void sendTextImgContext(WeiBoCreateBean weiBoCreateBean) {
        IPostService postService = ApiService.createPostService();
        File file = weiBoCreateBean.selectImgList.get(0).getImageFile();
//        String strEncode="";
//        try {
//            strEncode = URLEncoder.encode(weiBoCreateBean.content,"utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        RequestBody accessBody = RequestBody.create(
                MediaType.parse("text/plain"),
                ShareSdkUtils.getToken(mContext)
        );
        RequestBody contentBody = RequestBody.create(
                MediaType.parse("text/plain"),
                weiBoCreateBean.content
        );
        RequestBody imageBody = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                file
        );
        //MultipartBody.Part part = MultipartBody.Part.createFormData("file",file.getName(),requestBody);
        Call<Status> model = postService.sendTextImgContext(
                accessBody,
                contentBody,
                imageBody
        );
        model.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                ToastUtils.showToast(mContext, "发送带图片的微博成功");
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                ToastUtils.showToast(mContext, "发送带图片的失败");
            }
        });
    }
}
