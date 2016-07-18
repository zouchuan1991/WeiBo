package com.dwg.weibo.mvp.presenter.imp;

import android.content.Context;
import android.util.Log;
import com.dwg.weibo.api.ApiService;
import com.dwg.weibo.entity.Comment;
import com.dwg.weibo.entity.CommentList;
import com.dwg.weibo.entity.Status;
import com.dwg.weibo.mvp.model.ICommentService;
import com.dwg.weibo.mvp.presenter.IStatusDetailPresenter;
import com.dwg.weibo.ui.activity.BaseDetailActivity;
import com.dwg.weibo.ui.common.RequestParam;
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
  private BaseDetailActivity mBaseDetailActivity;

  public StatusDetailPresenterImp(BaseDetailActivity baseDetailActivity, Status status) {
    this.mBaseDetailActivity = baseDetailActivity;
    this.mStatus = status;
  }

  @Override public void pullToRefreshComment(Context context) {
    this.mContext = context;
    ICommentService commentService = ApiService.createCommentService();
    Call<CommentList> model = commentService.getCommentList(
        HomeFragmentPresenterImp.accessToken,
        mStatus.id,
        "0",
        "0",
        RequestParam.GET_COMMENT_COUNT,
        1,
        0
    );
    model.enqueue(new Callback<CommentList>() {
      @Override
      public void onResponse(Call<CommentList> call, Response<CommentList> response) {
        ToastUtils.showToast(mContext, "成功获取数据");
        if (response.body().comments.size() != 0) {
          mComments = response.body().comments;
          onCommentCallBack.onDataFinish(response.body().comments);
        } else {
          onCommentCallBack.noMoreDate();
        }
      }

      @Override public void onFailure(Call<CommentList> call, Throwable t) {
        Log.e("id:", mStatus.id);
        Log.e("error", t.toString());
        ToastUtils.showToast(mContext, "失败了");
        onCommentCallBack.onError("网络可能出错了");
      }
    });
  }

  @Override public void requestMoreComment(Context context) {
    this.mContext = context;
  }

  @Override public void pullToRefreshTransmit(Context context) {

  }

  @Override public void requestMOreTransmit(Context context) {

  }

  ICommentService.OnCommentCallBack onCommentCallBack = new ICommentService.OnCommentCallBack() {
    @Override public void noMoreDate() {
      ToastUtils.showToast(mContext, "还没有评论");
    }

    @Override public void onDataFinish(ArrayList<Comment> commentlist) {
      mBaseDetailActivity.updateCommentList(commentlist);
    }

    @Override public void onError(String error) {
      ToastUtils.showToast(mContext, error);
    }
  };
}
