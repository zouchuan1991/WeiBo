package com.dwg.weibo.mvp.presenter;

import android.content.Context;

import com.dwg.weibo.entity.User;

/**
 * Created by Administrator on 2016/8/9.
 */
public interface IUserActivityPresenter {
    void firstLoadUserWeiBo(Context context, User user);

    void requestMoreUserWeiBo(Context context);

    void firstLoadUserAlbum(Context context, User user);

    void requestMoreUserAlbum(Context context);
}
