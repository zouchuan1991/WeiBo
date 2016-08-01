package com.dwg.weibo.mvp.presenter.imp;

import android.content.Context;

import com.dwg.weibo.api.ApiService;
import com.dwg.weibo.entity.CommentList;
import com.dwg.weibo.mvp.model.ICommentService;
import com.dwg.weibo.mvp.presenter.ICommentFragmentPresenter;
import com.dwg.weibo.mvp.view.ICommentFragmentView;
import com.dwg.weibo.ui.common.RequestParam;
import com.dwg.weibo.utils.ShareSdkUtils;
import com.dwg.weibo.utils.ToastUtils;

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
    }

    @Override
    public void getToMeComment() {
        ICommentService commentService = ApiService.createCommentService();
        Call<CommentList> model = commentService.getToMeComment(
                ShareSdkUtils.getToken(mContext),
                mCommentList.comments == null ? "0" : mCommentList.comments.get(0).id,
                "0",
                RequestParam.GET_TOME_COMMENT_COUNT,
                1
        );
        model.enqueue(new Callback<CommentList>() {
            @Override
            public void onResponse(Call<CommentList> call, Response<CommentList> response) {
                if (response.body().comments != null) {
                    ToastUtils.showToast(mContext, "成功获取");
                    mCommentFragmentView.refreshDatas(response.body());
                }
            }

            @Override
            public void onFailure(Call<CommentList> call, Throwable t) {
                ToastUtils.showToast(mContext, "获取评论失败");
            }
        });
    }

    @Override
    public void getMoreComment() {
        ICommentService commentService = ApiService.createCommentService();
        Call<CommentList> model = commentService.getToMeComment(
                ShareSdkUtils.getToken(mContext),
                "0",
                mCommentList.comments == null ? "0" : mCommentList.comments.get(0).id,
                RequestParam.GET_TOME_COMMENT_COUNT,
                1
        );
    }

}
