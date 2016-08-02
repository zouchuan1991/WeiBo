package com.dwg.weibo.ui.fragment;


import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.dwg.weibo.R;
import com.dwg.weibo.adapter.CommentFragmentAdapter;
import com.dwg.weibo.adapter.HeaderAndFooterRecyclerAdapter;
import com.dwg.weibo.entity.Comment;
import com.dwg.weibo.mvp.presenter.ICommentFragmentPresenter;
import com.dwg.weibo.mvp.presenter.imp.CommentFragmentPresenterImp;
import com.dwg.weibo.mvp.view.ICommentFragmentView;
import com.dwg.weibo.ui.common.LoadingFooterView;
import com.dwg.weibo.utils.RecyclerViewStateUtils;
import com.dwg.weibo.widget.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class CommentFragment extends BaseFragment implements ICommentFragmentView {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private Context mContext;
    private ArrayList<Comment> mCommentList = new ArrayList<>();
    private CommentFragmentAdapter mAdapter;
    private HeaderAndFooterRecyclerAdapter headerAndFooterRecyclerAdapter;
    private ICommentFragmentPresenter commentFragmentPresenter;

    @Override
    public void onStart() {
        super.onStart();
        Log.e("onStart", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("onResume", "onResume");
    }


    @Override
    protected void initParams() {
        mContext = getActivity();
        mAdapter = new CommentFragmentAdapter(mContext, mCommentList);
        headerAndFooterRecyclerAdapter = new HeaderAndFooterRecyclerAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mRecyclerview.setAdapter(headerAndFooterRecyclerAdapter);
        mRecyclerview.addOnScrollListener(endlessRecyclerOnScrollListener);
        commentFragmentPresenter = new CommentFragmentPresenterImp(mContext, this);
        updateDatas();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDatas(mCommentList);
            }
        });
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment_list;
    }

    @Override
    protected int updateDatas() {
        mSwipeRefreshLayout.setRefreshing(true);
        commentFragmentPresenter.getToMeComment();
        return 0;
    }

    @Override
    public void refreshDatas(ArrayList<Comment> commentList) {
        mSwipeRefreshLayout.setRefreshing(false);
        int position = headerAndFooterRecyclerAdapter.getItemCount();
        mSwipeRefreshLayout.setRefreshing(false);
        mCommentList = commentList;
        mAdapter.setmDatas(commentList);
        mAdapter.notifyDataSetChanged();
        mRecyclerview.getLayoutManager().scrollToPosition(position);
    }

    @Override
    public void showLoadFooter() {
        RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerview, mCommentList.size(),
                LoadingFooterView.State.Loading, null);
    }

    @Override
    public void hideLoadFooter() {
        Log.e("TAG", "隐藏footer");
        RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerview, mCommentList.size(),
                LoadingFooterView.State.Normal, null);
    }

    @Override
    public void showEndFooter() {
        RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerview, mCommentList.size(),
                LoadingFooterView.State.TheEnd, null);
    }

    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            showLoadFooter();
            commentFragmentPresenter.getMoreComment();
        }
    };
}
