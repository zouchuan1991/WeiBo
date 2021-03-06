package com.dwg.weibo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import com.dwg.weibo.R;
import com.dwg.weibo.adapter.WeiBoAdapter;
import com.dwg.weibo.entity.Status;
import com.dwg.weibo.entity.User;
import com.dwg.weibo.mvp.presenter.IUserActivityPresenter;
import com.dwg.weibo.mvp.presenter.imp.UserActivityPresenterImp;
import com.dwg.weibo.mvp.view.ISelfFragmentView;
import com.dwg.weibo.ui.common.FillContentHelper;
import com.dwg.weibo.widget.*;
import java.util.ArrayList;
import butterknife.BindView;
import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;


public class UserWeiboFragment extends BaseFragment implements ISelfFragmentView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;



    private Context mContext;
    private IUserActivityPresenter mUserActivityPresenter;
    private ArrayList<Status> mStatus = new ArrayList<>();
    private User mUser;
    private WeiBoAdapter weiBoAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mUser = bundle.getParcelable("user");
        ViewGroup viewGroup;

    }

    @Override
    protected void initParams() {
        mContext = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mUserActivityPresenter = new UserActivityPresenterImp(this);
        weiBoAdapter = new WeiBoAdapter(mContext, mStatus);
        recyclerView.setAdapter(weiBoAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        updateDatas();

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                EventBus.getDefault().post(AttachUtil.isRecyclerViewAttach(recyclerView));
                Log.e("111",">>>.");
            }
        });
    }



    public static UserWeiboFragment newInstance(User user) {

        Bundle args = new Bundle();
        args.putParcelable("user", user);
        UserWeiboFragment fragment = new UserWeiboFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_self_weibo;
    }

    @Override
    protected int updateDatas() {
        mUserActivityPresenter.firstLoadUserWeiBo(mContext, mUser);
        return 0;
    }

    @Override
    public void refreshDatas(ArrayList<Status> statuses) {


        for (Status status : statuses) {
            FillContentHelper.setImgUrl(status);
            if (status.retweeted_status != null) {
                if (status.retweeted_status.pic_urls != null) {
                    FillContentHelper.setImgUrl(status.retweeted_status);
                }
            }
        }
        mStatus = statuses;
        weiBoAdapter.setDatas(mStatus);
        weiBoAdapter.notifyDataSetChanged();
        Log.i("Tag", "" + statuses.size());
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
}
