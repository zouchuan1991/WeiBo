package com.dwg.weibo.ui.fragment;


import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dwg.weibo.R;
import com.dwg.weibo.adapter.CommentFragmentAdapter;
import com.dwg.weibo.adapter.HeaderAndFooterRecyclerAdapter;
import com.dwg.weibo.entity.CommentList;
import com.dwg.weibo.mvp.presenter.ICommentFragmentPresenter;
import com.dwg.weibo.mvp.presenter.imp.CommentFragmentPresenterImp;
import com.dwg.weibo.mvp.view.ICommentFragmentView;
import com.dwg.weibo.ui.common.LoadingFooterView;
import com.dwg.weibo.utils.RecyclerViewStateUtils;
import com.dwg.weibo.widget.EndlessRecyclerOnScrollListener;

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
    private CommentList mCommentList = new CommentList();
    private CommentFragmentAdapter mAdapter;
    private HeaderAndFooterRecyclerAdapter headerAndFooterRecyclerAdapter;
    private ICommentFragmentPresenter commentFragmentPresenter;

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
    public void refreshDatas(CommentList commentList) {
        mSwipeRefreshLayout.setRefreshing(false);
        mCommentList = commentList;
        mAdapter.setmDatas(commentList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadFooter() {
        RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerview, mCommentList.comments.size(),
                LoadingFooterView.State.Loading, null);
    }

    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            commentFragmentPresenter.getMoreComment();
            showLoadFooter();
        }
    };
}
