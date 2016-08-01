package com.dwg.weibo.api;

import com.dwg.weibo.mvp.model.ICommentService;
import com.dwg.weibo.mvp.model.IPostService;
import com.dwg.weibo.mvp.model.IStatusService;
import com.dwg.weibo.mvp.model.ITransmitService;
import com.dwg.weibo.mvp.model.IUserService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/7/10.
 */

public class ApiService {
    public static final String baseUrl = "https://api.weibo.com";
    private static Retrofit retrofit;
    public static IStatusService createStatusService(){return retrofit().create(IStatusService.class);}
    public static Retrofit retrofit(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
        }
        return retrofit;
    }

    public static ICommentService createCommentService() {
        return retrofit().create(ICommentService.class);
    }

    public static ITransmitService createTransmitService() {
        return retrofit().create(ITransmitService.class);
    }

    public static IPostService createPostService() {
        return retrofit().create(IPostService.class);
    }

    public static IUserService createUserService() {
        return retrofit().create(IUserService.class);
    }

}
