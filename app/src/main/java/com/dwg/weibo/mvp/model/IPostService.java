package com.dwg.weibo.mvp.model;

import com.dwg.weibo.entity.Status;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/7/27.
 */

public interface IPostService {
    @FormUrlEncoded
    @POST("/2/statuses/update.json")
    Call<Status> sendTextContext(
            @Query("access_token") String accessToken,
            @Field("status") String context
    );

    @Multipart
    @POST("/2/statuses/upload.json")
    Call<Status> sendTextImgContext(
            @Part("access_token") RequestBody accessToken,
            @Part("status") RequestBody context,
            @Part("pic\";filename=\"file") RequestBody requestBody
    );

    @FormUrlEncoded
    @POST("/2/comments/create.json")
    Call<Status> createComment(
            @Query("access_token") String accessToken,
            @Field("comment") String comment,
            @Query("id") String id,
            @Query("comment_ori") int commentOri
    );
}
