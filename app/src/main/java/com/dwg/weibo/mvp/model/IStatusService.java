package com.dwg.weibo.mvp.model;

import com.dwg.weibo.entity.Status;
import com.dwg.weibo.entity.StatusList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/7/10.
 */
public interface IStatusService {
    @GET("/2/statuses/friends_timeline.json")
    Call<StatusList> getStatusList(
            @Query("access_token") String accessToken,
            @Query("since_id")String sinceId,
            @Query("max_id") String maxId,
            @Query("count") int count,
            @Query("page") int page,
            @Query("base_app") int baseApp,
            @Query("feature") int feature,
            @Query("trim_user") int trimUser
    );

    interface OnDataFinishedListener {
        void noMoreData();

        void noDataInFirstLoad(String error);

        void onDataFinish(ArrayList<Status> statuslist);

        void onError(String error);
    }
}
