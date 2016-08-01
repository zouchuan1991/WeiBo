package com.dwg.weibo.mvp.model;

import com.dwg.weibo.entity.User;

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
}
