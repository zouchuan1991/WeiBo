package com.dwg.weibo.mvp.presenter.imp;

import android.content.Context;
import android.widget.Toast;
import com.dwg.weibo.api.ApiService;
import com.dwg.weibo.entity.FriendsTimelineRrequest;
import com.dwg.weibo.entity.Status;
import com.dwg.weibo.entity.StatusList;
import com.dwg.weibo.mvp.model.IStatusService;
import com.dwg.weibo.mvp.presenter.IHomeFragmentPresenter;
import com.dwg.weibo.mvp.view.IHomeFragmentView;
import com.dwg.weibo.utils.ToastUtils;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/11.
 */

public class HomeFragmentPresenterImp implements IHomeFragmentPresenter {
    public static final String accessToken = "2.00aHpcvBA3pYSD077de44c7c0P8cqu";
    private IHomeFragmentView mHomeFragmentView;
    private ArrayList<Status> mStatus = new ArrayList<>();
    private Context mContext;
    public HomeFragmentPresenterImp(IHomeFragmentView homeFragmentView){
        this.mHomeFragmentView = homeFragmentView;
    }
    @Override
    public void firstLoadDatas(final Context context) {
        mContext = context;
        IStatusService IStatusService = ApiService.createStatusService();
        Call<StatusList> model = IStatusService.getStatusList(
                accessToken,
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
                Toast.makeText(context, "成功获取数据"+response.body().statuses.size(), Toast.LENGTH_SHORT).show();
                if(response.body().statuses.size()!=0){//有新数据才进行刷新
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
    public void requestMoreData(final Context context) {
        mContext = context;
        IStatusService IStatusService = ApiService.createStatusService();
        Call<StatusList> model = IStatusService.getStatusList(
                accessToken,
                FriendsTimelineRrequest.since_id,
                FriendsTimelineRrequest.getMaxId(mStatus),
                FriendsTimelineRrequest.count,
                FriendsTimelineRrequest.page,
                FriendsTimelineRrequest.base_app,
                FriendsTimelineRrequest.feature,
                FriendsTimelineRrequest.trim_user
        );
        model.enqueue(new Callback<StatusList>() {
            @Override
            public void onResponse(Call<StatusList> call, Response<StatusList> response) {
                Toast.makeText(context, "成功获取数据" + response.body().statuses.size(), Toast.LENGTH_SHORT).show();
                if (response.body().statuses.size() != 0) {//有新数据才进行刷新
                    mStatus.addAll(response.body().statuses);
                    mOnMoreDataListener.onDataFinish(mStatus);
                } else {
                    mOnMoreDataListener.noMoreData();
                }
            }

            @Override
            public void onFailure(Call<StatusList> call, Throwable t) {
                mOnMoreDataListener.onError("网络有错误");
            }
        });
    }

    private IStatusService.OnDataFinishedListener mPullToRefreshListener = new IStatusService.OnDataFinishedListener() {
        @Override
        public void noMoreData() {
            ToastUtils.showToast(mContext, "没有更多的数据了");
        }

        @Override
        public void noDataInFirstLoad(String error) {
            ToastUtils.showToast(mContext, "空");
        }

        @Override
        public void onDataFinish(ArrayList<Status> statuslist) {
            mHomeFragmentView.hideLoadingIcon();
            mHomeFragmentView.updateListView(mStatus);
            mHomeFragmentView.scrollToTop();

        }

        @Override
        public void onError(String error) {
            ToastUtils.showToast(mContext, error);
        }
    };
    private IStatusService.OnDataFinishedListener mOnMoreDataListener = new IStatusService.OnDataFinishedListener() {
        @Override
        public void noMoreData() {
            mHomeFragmentView.showEndFooterView();
        }

        @Override
        public void noDataInFirstLoad(String error) {

        }

        @Override
        public void onDataFinish(ArrayList<Status> statuslist) {
            mHomeFragmentView.hideFooter();
            mHomeFragmentView.updateListView(statuslist);
        }

        @Override
        public void onError(String error) {
        }
    };
}
