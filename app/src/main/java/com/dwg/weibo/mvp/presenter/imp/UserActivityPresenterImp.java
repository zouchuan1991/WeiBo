package com.dwg.weibo.mvp.presenter.imp;

import android.content.Context;
import android.util.Log;

import com.dwg.weibo.api.ApiService;
import com.dwg.weibo.entity.FriendsTimelineRrequest;
import com.dwg.weibo.entity.Status;
import com.dwg.weibo.entity.StatusList;
import com.dwg.weibo.entity.User;
import com.dwg.weibo.mvp.model.IUserService;
import com.dwg.weibo.mvp.presenter.IUserActivityPresenter;
import com.dwg.weibo.mvp.view.ISelfWeiboFragment;
import com.dwg.weibo.utils.ShareSdkUtils;
import com.dwg.weibo.utils.ToastUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/8/9.
 */
public class UserActivityPresenterImp implements IUserActivityPresenter {

    private Context mContext;
    private ArrayList<Status> mStatus = new ArrayList<>();
    private ISelfWeiboFragment selfWeiboFragment;

    public UserActivityPresenterImp(ISelfWeiboFragment selfWeiboFragment) {
        this.selfWeiboFragment = selfWeiboFragment;
    }

    @Override
    public void firstLoadUserWeiBo(Context context, User user) {
        mContext = context;
        IUserService userService = ApiService.createUserService();
        Call<StatusList> model = userService.getUsetTimeLine(
                ShareSdkUtils.getToken(context),
                user.id,
                user.screen_name,
                FriendsTimelineRrequest.getSinceId(mStatus),
                FriendsTimelineRrequest.max_id,
                FriendsTimelineRrequest.count,
                FriendsTimelineRrequest.page,
                FriendsTimelineRrequest.base_app,
                FriendsTimelineRrequest.feature,
                FriendsTimelineRrequest.trim_user
        );
        model.enqueue(new Callback<StatusList>() {
            @Override
            public void onResponse(Call<StatusList> call, Response<StatusList> response) {
                Log.e("TAG", "请求到数据了");
                if (response.body() != null && response.body().statuses.size() != 0) {//有新数据才进行刷新
                    response.body().statuses.addAll(mStatus);
                    mStatus = response.body().statuses;
                    mPullToRefreshListener.onDataFinish(mStatus);
                } else {
                    mPullToRefreshListener.noMoreData();
                }
            }

            @Override
            public void onFailure(Call<StatusList> call, Throwable t) {
                mPullToRefreshListener.onError("网络出问题了");
            }
        });
    }

    @Override
    public void requestMoreUserWeiBo(Context context) {

    }

    IUserService.OnDataFinishedListener mPullToRefreshListener = new IUserService.OnDataFinishedListener() {
        @Override
        public void noMoreData() {
            ToastUtils.showToast(mContext, "没有更多数据了");
        }

        @Override
        public void noDataInFirstLoad(String error) {
            ToastUtils.showToast(mContext, "没有数据");
        }

        @Override
        public void onDataFinish(ArrayList<Status> statuslist) {
            selfWeiboFragment.hideLoadFooter();
            selfWeiboFragment.refreshDatas(statuslist);
        }

        @Override
        public void onError(String error) {

        }
    };
}
