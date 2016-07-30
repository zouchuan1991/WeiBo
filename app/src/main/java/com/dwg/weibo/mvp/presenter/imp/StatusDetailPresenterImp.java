package com.dwg.weibo.mvp.presenter.imp;

import android.content.Context;
import android.util.Log;

import com.dwg.weibo.api.ApiService;
import com.dwg.weibo.entity.Comment;
import com.dwg.weibo.entity.CommentList;
import com.dwg.weibo.entity.RepostList;
import com.dwg.weibo.entity.Status;
import com.dwg.weibo.mvp.model.ICommentService;
import com.dwg.weibo.mvp.model.ITransmitService;
import com.dwg.weibo.mvp.presenter.IStatusDetailPresenter;
import com.dwg.weibo.ui.activity.BaseDetailActivity;
import com.dwg.weibo.ui.common.RequestParam;
import com.dwg.weibo.utils.ShareSdkUtils;
import com.dwg.weibo.utils.ToastUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/18.
 */
public class StatusDetailPresenterImp implements IStatusDetailPresenter {
  private Context mContext;
  private Status mStatus;
  private ArrayList<Comment> mComments = new ArrayList<>();
  private ArrayList<Status> mReposts = new ArrayList<>();
  private BaseDetailActivity mBaseDetailActivity;

  public StatusDetailPresenterImp(BaseDetailActivity baseDetailActivity, Status status) {
    this.mBaseDetailActivity = baseDetailActivity;
    this.mStatus = status;
  }

  @Override public void pullToRefreshComment(Context context) {
    this.mContext = context;
    ICommentService commentService = ApiService.createCommentService();
    Call<CommentList> model = commentService.getCommentList(
            ShareSdkUtils.getToken(mContext),
        mStatus.id,
        mComments.size() == 0 ? "0" : mComments.get(0).id,
        "0",
        RequestParam.GET_COMMENT_COUNT,
        1,
        0
    );
    model.enqueue(new Callback<CommentList>() {
      @Override
      public void onResponse(Call<CommentList> call, Response<CommentList> response) {
        ToastUtils.showToast(mContext, "成功获取数据");
        if (response != null && response.body() != null && response.body().comments.size() != 0) {
          response.body().comments.addAll(mComments);
          mComments = response.body().comments;
          onCommentCallBack.onDataFinish(mComments);
        } else {
          onCommentCallBack.noMoreDate();
        }
      }

      @Override public void onFailure(Call<CommentList> call, Throwable t) {
        onCommentCallBack.onError("网络可能出错了");
      }
    });
  }

  @Override public void requestMoreComment(Context context) {
    this.mContext = context;
    ICommentService commentService = ApiService.createCommentService();
    Call<CommentList> model = commentService.getCommentList(
            ShareSdkUtils.getToken(mContext),
        mStatus.id,
        "0",
        mComments.size() == 0 ? "0" : mComments.get(mComments.size() - 1).id,
        RequestParam.GET_COMMENT_COUNT,
        1,
        0
    );
    model.enqueue(new Callback<CommentList>() {
      @Override
      public void onResponse(Call<CommentList> call, Response<CommentList> response) {
        if (response.body().comments.size() != 0) {
          mComments.addAll(response.body().comments);
          onRequestMoreComment.onDataFinish(mComments);
        } else {
          onRequestMoreComment.noMoreDate();
        }
      }

      @Override public void onFailure(Call<CommentList> call, Throwable t) {
        ToastUtils.showToast(mContext, "失败了");
        onRequestMoreComment.onError("网络可能出错了");
      }
    });
  }

  @Override public void pullToRefreshTransmit(Context context) {
    this.mContext = context;
    ITransmitService iTransmitService = ApiService.createTransmitService();
    Call<RepostList> model = iTransmitService.getRepostList(
            ShareSdkUtils.getToken(mContext),
            mStatus.id,
            mReposts.size() == 0 ? "0" : mReposts.get(0).id,
            "0",
            RequestParam.GET_TRANMIT_COUNT,
            1,
            0
    );
    model.enqueue(new Callback<RepostList>() {
      @Override
      public void onResponse(Call<RepostList> call, Response<RepostList> response) {
        if (response.body() != null && response.body().reposts.size() != 0) {
          response.body().reposts.addAll(mReposts);
          mReposts = response.body().reposts;
          onRepostCallBack.onDataFinish(mReposts);
        } else {
          onRepostCallBack.noMoreDate();
        }
      }

      @Override
      public void onFailure(Call<RepostList> call, Throwable t) {
        onCommentCallBack.onError("网络可能出错了");
      }
    });
  }

  @Override public void requestMOreTransmit(Context context) {

  }

  ICommentService.OnCommentCallBack onCommentCallBack = new ICommentService.OnCommentCallBack() {
    @Override public void noMoreDate() {
      ToastUtils.showToast(mContext, "还没有评论");
    }

    @Override public void onDataFinish(ArrayList<Comment> commentlist) {
      Log.e("data加载完毕", "完毕");
      mBaseDetailActivity.updateCommentList(commentlist, true);
    }

    @Override public void onError(String error) {
      ToastUtils.showToast(mContext, error);
    }
  };

  ICommentService.OnCommentCallBack onRequestMoreComment = new ICommentService.OnCommentCallBack() {
    @Override public void noMoreDate() {

    }

    @Override public void onDataFinish(ArrayList<Comment> commentlist) {
      mBaseDetailActivity.hideLoadFooterView();
      mBaseDetailActivity.updateCommentList(commentlist, false);
    }

    @Override
    public void onError(String error) {

    }
  };

  ITransmitService.OnTransmitCallBack onRepostCallBack = new ITransmitService.OnTransmitCallBack() {
    @Override
    public void noMoreDate() {

    }

    @Override
    public void onDataFinish(ArrayList<Status> reposts) {
      mBaseDetailActivity.hideLoadFooterView();
      mBaseDetailActivity.updateRepostList(reposts, true);
    }

    @Override
    public void onError(String error) {
      ToastUtils.showToast(mContext, error);
    }
  };

  ITransmitService.OnTransmitCallBack onRequestMoreRepost = new ITransmitService.OnTransmitCallBack() {
    @Override
    public void noMoreDate() {

    }

    @Override
    public void onDataFinish(ArrayList<Status> reposts) {

    }

    @Override public void onError(String error) {

    }
  };
}
