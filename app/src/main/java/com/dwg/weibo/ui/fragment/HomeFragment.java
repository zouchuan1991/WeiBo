package com.dwg.weibo.ui.fragment;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.dwg.weibo.R;
import com.dwg.weibo.adapter.FragmentHomeAdapter;
import com.dwg.weibo.entity.Status;
import com.dwg.weibo.mvp.presenter.IHomeFragmentPresenter;
import com.dwg.weibo.mvp.presenter.imp.HomeFragmentPresenterImp;
import com.dwg.weibo.mvp.view.IHomeFragmentView;
import com.dwg.weibo.ui.common.FillContent;
import com.dwg.weibo.ui.common.FillContentHelper;
import com.dwg.weibo.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/7/10.
 */

public class HomeFragment extends BaseFragment implements IHomeFragmentView {
    @BindView(R.id.fragment_home_content)
    RecyclerView mRecyclerView;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private FragmentHomeAdapter mAdapter;
    private Context mContext;
    private IHomeFragmentPresenter homeFragmentPresenter;
    private ArrayList<Status> mDatas;

    @Override
    protected void initParams() {
        mContext = getContext();
        initRecyclerView();
        homeFragmentPresenter = new HomeFragmentPresenterImp(this);
        homeFragmentPresenter.firstLoadDatas(mContext);
        mSwipeRefreshLayout.setColorSchemeColors(mContext.getResources().getColor(R.color.red));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                homeFragmentPresenter.firstLoadDatas(mContext);
            }
        });
    }

    private void initRecyclerView() {
        mDatas = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new FragmentHomeAdapter(mContext,mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    public void UpdateListView(ArrayList<Status> statuses) {
        for(Status status:statuses){
            FillContentHelper.setImgUrl(status);
            if(status.retweeted_status!=null){
                if(status.retweeted_status.pic_urls!=null){
                    FillContentHelper.setImgUrl(status.retweeted_status);
                }
            }
        }
        mDatas = statuses;
        mAdapter.setDatas(statuses);
        mAdapter.notifyDataSetChanged();

    }
}
