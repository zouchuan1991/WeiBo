package com.dwg.weibo.mvp.model;

import com.dwg.weibo.entity.RepostList;
import com.dwg.weibo.entity.Status;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/7/22.
 */
public interface ITransmitService {
    @GET("/2/statuses/repost_timeline.json")
    Call<RepostList> getRepostList(
            @Query("access_token") String accessToken,
            @Query("id") String id,
            @Query("since_id") String sinceId,
            @Query("max_id") String maxId,
            @Query("count") int count,
            @Query("page") int page,
            @Query("filter_by_author") int fileterByAythor
    );

    interface OnTransmitCallBack {
        void noMoreDate();

        void onDataFinish(ArrayList<Status> reposts);

        void onError(String error);
    }
}
