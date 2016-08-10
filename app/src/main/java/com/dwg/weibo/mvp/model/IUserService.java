package com.dwg.weibo.mvp.model;

import com.dwg.weibo.entity.Status;
import com.dwg.weibo.entity.StatusList;
import com.dwg.weibo.entity.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/7/30.
 */

public interface IUserService {
    @GET("/2/users/show.json")
    Call<User> getUserInfo(
            @Query("access_token") String accessToken,
            @Query("uid") String uid
    );

    @GET("/2/statuses/user_timeline.json")
    Call<StatusList> getUsetTimeLine(
            @Query("access_token") String access_token,
            @Query("uid") String uid,
            @Query("screen_name") String screen_name,
            @Query("since_id") String since_id,
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
