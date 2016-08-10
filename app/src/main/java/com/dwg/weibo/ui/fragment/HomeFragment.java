package com.dwg.weibo.ui.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.dwg.weibo.R;
import com.dwg.weibo.adapter.HeaderAndFooterRecyclerAdapter;
import com.dwg.weibo.adapter.WeiBoAdapter;
import com.dwg.weibo.entity.Status;
import com.dwg.weibo.mvp.presenter.IHomeFragmentPresenter;
import com.dwg.weibo.mvp.presenter.imp.HomeFragmentPresenterImp;
import com.dwg.weibo.mvp.view.IHomeFragmentView;
import com.dwg.weibo.ui.common.FillContentHelper;
import com.dwg.weibo.ui.common.LoadingFooterView;
import com.dwg.weibo.utils.RecyclerViewStateUtils;
import com.dwg.weibo.widget.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/7/10.
 */

public class HomeFragment extends BaseFragment implements IHomeFragmentView {
    @BindView(R.id.fragment_home_content)
    MyRecyclerView mRecyclerView;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private WeiBoAdapter mAdapter;
    private Context mContext;
    private IHomeFragmentPresenter mHomeFragmentPresenter;
    private ArrayList<Status> mDatas;

    HeaderAndFooterRecyclerAdapter mHeaderAndFooterRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void initParams() {
        mContext = getContext();
        initRecyclerView();
        mHomeFragmentPresenter = new HomeFragmentPresenterImp(this);

        mSwipeRefreshLayout.setColorSchemeColors(mContext.getResources().getColor(R.color.red));
        mSwipeRefreshLayout.setOnRefreshListener(refreshListener);
        updateDatas();
    }


    private void initRecyclerView() {
        mDatas = new ArrayList<>();
        mAdapter = new WeiBoAdapter(mContext, mDatas);
        mHeaderAndFooterRecyclerAdapter = new HeaderAndFooterRecyclerAdapter(mAdapter);
        linearLayoutManager =
            new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerAdapter);
        //以后可能增加设置setHeader
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public int updateDatas() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        refreshListener.onRefresh();

        return 0;
    }


    @Override
    public void updateListView(ArrayList<Status> statuses) {
        mSwipeRefreshLayout.setRefreshing(false);
        int position = mHeaderAndFooterRecyclerAdapter.getItemCount();
        mRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
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
        mHeaderAndFooterRecyclerAdapter.notifyDataSetChanged();
        mRecyclerView.getLayoutManager().scrollToPosition(position);

    }

    @Override
    public void scrollToTop() {
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public void hideLoadingIcon() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showRecyclerView() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRecyclerView() {

    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, mDatas.size(),
            LoadingFooterView.State.TheEnd, null);
    }

    @Override
    public void hideFooter() {
        RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, mDatas.size(),
            LoadingFooterView.State.Normal, null);
    }

    @Override
    public void showLoadFooterView() {
        RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView,
            mDatas.size(), LoadingFooterView.State.Loading, null);
    }

    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            if (mDatas != null && mDatas.size() > 0) {
                mHomeFragmentPresenter.requestMoreData(mContext);
                showLoadFooterView();
            }
        }
    };
    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mHomeFragmentPresenter.firstLoadDatas(mContext);
        }
    };
}
