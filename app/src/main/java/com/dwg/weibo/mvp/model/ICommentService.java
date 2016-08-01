package com.dwg.weibo.mvp.model;

import com.dwg.weibo.entity.Comment;
import com.dwg.weibo.entity.CommentList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/7/18.
 */
public interface ICommentService {
  @GET("/2/comments/show.json") Call<CommentList> getCommentList(
      @Query("access_token") String accessToken,
      @Query("id") String id,
      @Query("since_id") String sinceId,
      @Query("max_id") String maxId,
      @Query("count") int count,
      @Query("page") int page,
      @Query("filter_by_author") int fileterByAythor
  );

  @GET("/2/comments/to_me.json")
  Call<CommentList> getToMeComment(
          @Query("access_token") String accessToken,
          @Query("since_id") String sinceId,
          @Query("max_id") String maxId,
          @Query("count") int count,
          @Query("page") int page
  );

  interface OnCommentCallBack {
    void noMoreDate();

    void onDataFinish(ArrayList<Comment> commentlist);

    void onError(String error);
  }
}
