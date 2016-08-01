package com.dwg.weibo.mvp.presenter.imp;

import android.content.Context;

import com.dwg.weibo.api.ApiService;
import com.dwg.weibo.entity.User;
import com.dwg.weibo.mvp.model.IUserService;
import com.dwg.weibo.mvp.presenter.IDrawerFragmentPresenter;
import com.dwg.weibo.mvp.view.IDrawerFragmentView;
import com.dwg.weibo.utils.ShareSdkUtils;
import com.dwg.weibo.utils.ToastUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/30.
 */

public class DrawerFragmentPresenter implements IDrawerFragmentPresenter {

    private Context mContext;
    private IDrawerFragmentView drawerFragmentView;

    public DrawerFragmentPresenter(Context context, IDrawerFragmentView drawerFragmentView) {
        this.mContext = context;
        this.drawerFragmentView = drawerFragmentView;
    }

    @Override
    public void requestUserInfo() {
        IUserService userService = ApiService.createUserService();
        Call<User> model = userService.getUserInfo(
                ShareSdkUtils.getToken(mContext),
                ShareSdkUtils.getUid(mContext)
        );
        model.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (null == response.body()) {
                    ToastUtils.showToast(mContext, "空的");
                    return;
                }
                ToastUtils.showToast(mContext, "获取信息成功");
                drawerFragmentView.updateUserInfo(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
