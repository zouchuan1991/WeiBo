package com.dwg.weibo.mvp.presenter.imp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.dwg.weibo.api.ApiService;
import com.dwg.weibo.entity.FriendsTimelineRrequest;
import com.dwg.weibo.entity.Status;
import com.dwg.weibo.mvp.model.IStatusService;
import com.dwg.weibo.entity.StatusList;
import com.dwg.weibo.mvp.presenter.IHomeFragmentPresenter;
import com.dwg.weibo.mvp.view.IHomeFragmentView;
import com.dwg.weibo.ui.common.FillContentHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/11.
 */

public class HomeFragmentPresenterImp implements IHomeFragmentPresenter {
    private IHomeFragmentView homeFragmentView;
    private ArrayList<Status> mStatus = new ArrayList<>();
    public HomeFragmentPresenterImp(IHomeFragmentView homeFragmentView){
        this.homeFragmentView = homeFragmentView;
    }
    @Override
    public void firstLoadDatas(final Context context) {
        final String accessToken = "2.00aHpcvBA3pYSD077de44c7c0P8cqu";
        IStatusService IStatusService = ApiService.createStatusService();
        Call<StatusList> model = IStatusService.getStatusList(accessToken, FriendsTimelineRrequest.getSinceId(mStatus),FriendsTimelineRrequest.max_id,FriendsTimelineRrequest.count,FriendsTimelineRrequest.page,FriendsTimelineRrequest.base_app,FriendsTimelineRrequest.feature,FriendsTimelineRrequest.trim_user);
        model.enqueue(new Callback<StatusList>() {
            @Override
            public void onResponse(Call<StatusList> call, Response<StatusList> response) {
                Toast.makeText(context, "成功获取数据"+response.body().statuses.size(), Toast.LENGTH_SHORT).show();
                if(response.body().statuses.size()!=0){//有新数据才进行刷新
                    response.body().statuses.addAll(mStatus);
                    mStatus = response.body().statuses;
                    homeFragmentView.UpdateListView(mStatus);
                }
            }

            @Override
            public void onFailure(Call<StatusList> call, Throwable t) {
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                t.printStackTrace();
            }
        });
    }
}
