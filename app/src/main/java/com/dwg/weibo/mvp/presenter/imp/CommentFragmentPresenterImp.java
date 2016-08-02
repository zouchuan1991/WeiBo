package com.dwg.weibo.mvp.presenter.imp;

import android.content.Context;
import android.util.Log;

import com.dwg.weibo.api.ApiService;
import com.dwg.weibo.entity.Comment;
import com.dwg.weibo.entity.CommentList;
import com.dwg.weibo.mvp.model.ICommentService;
import com.dwg.weibo.mvp.presenter.ICommentFragmentPresenter;
import com.dwg.weibo.mvp.view.ICommentFragmentView;
import com.dwg.weibo.ui.common.RequestParam;
import com.dwg.weibo.utils.ShareSdkUtils;
import com.dwg.weibo.utils.ToastUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/8/1.
 */

public class CommentFragmentPresenterImp implements ICommentFragmentPresenter {
    private Context mContext;
    private CommentList mCommentList = new CommentList();
    private ICommentFragmentView mCommentFragmentView;

    public CommentFragmentPresenterImp(Context context, ICommentFragmentView commentFragmentView) {
        this.mContext = context;
        this.mCommentFragmentView = commentFragmentView;
        mCommentList.comments = new ArrayList<>();
    }

    @Override
    public void getToMeComment() {
        ICommentService commentService = ApiService.createCommentService();
        Call<CommentList> model = commentService.getToMeComment(
                ShareSdkUtils.getToken(mContext),
                mCommentList.comments.size() == 0 ? "0" : mCommentList.comments.get(0).id,
                "0",
                RequestParam.GET_TOME_COMMENT_COUNT,
                1
        );
        model.enqueue(new Callback<CommentList>() {
            @Override
            public void onResponse(Call<CommentList> call, Response<CommentList> response) {
                if (response.body().comments != null) {
                    ToastUtils.showToast(mContext, "成功获取");
                    response.body().comments.addAll(mCommentList.comments);
                    mCommentList = response.body();
                    firstRequestCallBack.onDataFinish(mCommentList.comments);
                } else {
                    firstRequestCallBack.noMoreDate();
                }
            }

            @Override
            public void onFailure(Call<CommentList> call, Throwable t) {
                ToastUtils.showToast(mContext, "获取评论失败");
                firstRequestCallBack.onError("网络出错");
            }
        });
    }

    @Override
    public void getMoreComment() {
        ICommentService commentService = ApiService.createCommentService();
        Call<CommentList> model = commentService.getToMeComment(
                ShareSdkUtils.getToken(mContext),
                "0",
                mCommentList.comments.size() == 0 ? "0" : mCommentList.comments.get(mCommentList.comments.size() - 1).id,
                RequestParam.GET_TOME_COMMENT_COUNT,
                1
        );
        model.enqueue(new Callback<CommentList>() {
            @Override
            public void onResponse(Call<CommentList> call, Response<CommentList> response) {
                if (response.body().comments.size() != 0) {
                    if (response.body().comments.size() >= 2) {
                        response.body().comments.remove(0);
                        mCommentList.comments.addAll(response.body().comments);
                    } else {
                        Log.e("TAG", "没有更多数据了   ");
                        requestMoreDataCallBack.noMoreDate();
                    }

                }
            }

            @Override
            public void onFailure(Call<CommentList> call, Throwable t) {
                requestMoreDataCallBack.onError("网络出错");
            }
        });
    }

    ICommentService.OnCommentCallBack firstRequestCallBack = new ICommentService.OnCommentCallBack() {
        @Override
        public void noMoreDate() {
            ToastUtils.showToast(mContext, "没有数据");
        }

        @Override
        public void onDataFinish(ArrayList<Comment> commentlist) {
            mCommentFragmentView.refreshDatas(commentlist);
        }

        @Override
        public void onError(String error) {
            ToastUtils.showToast(mContext, error);
        }
    };

    ICommentService.OnCommentCallBack requestMoreDataCallBack = new ICommentService.OnCommentCallBack() {
        @Override
        public void noMoreDate() {
            mCommentFragmentView.showEndFooter();
        }

        @Override
        public void onDataFinish(ArrayList<Comment> commentlist) {
            mCommentFragmentView.hideLoadFooter();
            mCommentFragmentView.refreshDatas(commentlist);
        }

        @Override
        public void onError(String error) {
            ToastUtils.showToast(mContext, error);
        }
    };
}
