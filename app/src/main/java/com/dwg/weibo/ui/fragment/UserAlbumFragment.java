package com.dwg.weibo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dwg.weibo.R;
import com.dwg.weibo.adapter.ImageAdapter;
import com.dwg.weibo.api.ApiService;
import com.dwg.weibo.entity.FriendsTimelineRrequest;
import com.dwg.weibo.entity.Status;
import com.dwg.weibo.entity.StatusList;
import com.dwg.weibo.entity.User;
import com.dwg.weibo.mvp.model.IStatusService;
import com.dwg.weibo.mvp.model.IUserService;
import com.dwg.weibo.mvp.presenter.IUserActivityPresenter;
import com.dwg.weibo.mvp.presenter.imp.UserActivityPresenterImp;
import com.dwg.weibo.mvp.view.ISelfFragmentView;
import com.dwg.weibo.ui.common.FillContentHelper;
import com.dwg.weibo.utils.ShareSdkUtils;
import com.dwg.weibo.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/8/8.
 */

public class UserAlbumFragment extends BaseFragment implements ISelfFragmentView {
    @BindView(R.id.album_recyclerview)
    RecyclerView recyclerView;
    private Context mContext;
    private ImageAdapter imageAdapter;
    private ArrayList<String> mDatas = new ArrayList<>();
    private User mUser;

    @Override
    protected void initParams() {
        mContext = getActivity();
        Bundle arguments = getArguments();
        mUser = arguments.getParcelable("user");
        imageAdapter = new ImageAdapter(mContext, mDatas);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(imageAdapter);
        updateDatas();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_self_album;
    }

    @Override
    protected int updateDatas() {
        IUserService userService = ApiService.createUserService();
        Call<StatusList> userTimeLine = userService.getUsetTimeLine(
                ShareSdkUtils.getToken(mContext),
                mUser.id,
                mUser.screen_name,
                "0",
                FriendsTimelineRrequest.max_id,
                FriendsTimelineRrequest.count,
                FriendsTimelineRrequest.page,
                FriendsTimelineRrequest.base_app,
                FriendsTimelineRrequest.feature,
                FriendsTimelineRrequest.trim_user
        );
        userTimeLine.enqueue(new Callback<StatusList>() {
            @Override
            public void onResponse(Call<StatusList> call, Response<StatusList> response) {
                if(response.body()!=null&&response.body().statuses!=null){
                    refreshDatas(response.body().statuses);
                }else{

                }
            }

            @Override
            public void onFailure(Call<StatusList> call, Throwable t) {

            }
        });
        return 0;
    }

    @Override
    public void refreshDatas(ArrayList<Status> statuses) {
        Log.e("tupian","图片加载了");
        for(Status status:statuses){
            FillContentHelper.setImgUrl(status);
            if(status.retweeted_status!=null){
                if(status.retweeted_status.pic_urls!=null){
                    FillContentHelper.setImgUrl(status.retweeted_status);
                }
            }
            mDatas.addAll(status.bmiddle_pic_urls);
        }
        Log.e("tupian",""+mDatas.size());
        imageAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadFooter() {

    }

    @Override
    public void hideLoadFooter() {

    }

    @Override
    public void showEndFooter() {

    }

    public static Fragment newInstance(User mUser) {
        Bundle b= new Bundle();
        b.putParcelable("user",mUser);
        UserAlbumFragment fragment = new UserAlbumFragment();
        fragment.setArguments(b);
        return fragment;
    }
}
