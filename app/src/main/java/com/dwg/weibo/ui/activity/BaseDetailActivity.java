package com.dwg.weibo.ui.activity;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.dwg.weibo.R;
import com.dwg.weibo.adapter.CommentAdapter;
import com.dwg.weibo.adapter.HeaderAndFooterRecyclerAdapter;
import com.dwg.weibo.adapter.RepostAdapter;
import com.dwg.weibo.entity.Comment;
import com.dwg.weibo.entity.Status;
import com.dwg.weibo.mvp.presenter.IStatusDetailPresenter;
import com.dwg.weibo.mvp.presenter.imp.StatusDetailPresenterImp;
import com.dwg.weibo.mvp.view.IBaseActivityView;
import com.dwg.weibo.ui.common.FillContent;
import com.dwg.weibo.ui.common.LoadingFooterView;
import com.dwg.weibo.utils.RecyclerViewStateUtils;
import com.dwg.weibo.widget.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/7/17.
 */
public abstract class BaseDetailActivity extends BaseActivity implements IBaseActivityView {
  @BindView(R.id.swiperefreshlayout)
  SwipeRefreshLayout mSwipeRefreshLayout;
  @BindView(R.id.recyclerview)
  RecyclerView mRecyclerView;
  @BindView(R.id.linear_favour)
  LinearLayout mLinearFavour;
  @BindView(R.id.linear_comment)
  LinearLayout mLinearComment;
  @BindView(R.id.linear_transmit)
  LinearLayout mLineartransmit;

  public IStatusDetailPresenter mStatusDetailPresenter;
  private HeaderAndFooterRecyclerAdapter mTransmitRecyclerAdapter;
  private HeaderAndFooterRecyclerAdapter mCommentRecyclerAdapter;
  private ArrayList<Comment> mComments = new ArrayList<>();
  private ArrayList<Status> mReposts = new ArrayList<>();
  private CommentAdapter mCommentAdapter;
  private RepostAdapter mRepostAdapter;
  private Context mContext;
  public Status mStatus;
  private int mType;
  @Override protected void initParams() {
    mContext = this;
    mStatus = getIntent().getParcelableExtra("status");
    mCommentAdapter = new CommentAdapter(mContext, mComments);
    mRepostAdapter = new RepostAdapter(mContext, mReposts);
    mStatusDetailPresenter = new StatusDetailPresenterImp(this, mStatus);
    mStatusDetailPresenter.pullToRefreshComment(mContext);
    initSwipeRefresh();
    initRecyclerView();
    FillContent.fillButtonBar(mContext,mStatus,mLinearComment,mLineartransmit,mLinearFavour);
  }

  private void initRecyclerView() {
    LinearLayoutManager linearLayoutManager =
        new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
    mRecyclerView.setLayoutManager(linearLayoutManager);
    mCommentRecyclerAdapter = new HeaderAndFooterRecyclerAdapter(mCommentAdapter);
    mTransmitRecyclerAdapter = new HeaderAndFooterRecyclerAdapter(mRepostAdapter);
    mRecyclerView.setAdapter(mCommentRecyclerAdapter);
    addHeaderView(mType);

  }

  protected abstract void addHeaderView(int type);

  private void initSwipeRefresh() {
    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
      }
    });
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_status_detail;
  }

  @Override public void updateCommentList(ArrayList<Comment> comments, boolean firstLoad) {


    int position = mCommentRecyclerAdapter.getItemCount();
    mRecyclerView.clearOnScrollListeners();
    mRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
    Log.e("CommentPosition", "" + position);

    this.mComments = comments;
    mCommentAdapter.setDatas(comments);
    //mRecyclerView.setAdapter(mCommentRecyclerAdapter) ;
    mCommentRecyclerAdapter.notifyDataSetChanged();
    if (!firstLoad) {
      mRecyclerView.getLayoutManager().scrollToPosition(position);
    }


  }

  @Override
  public void updateRepostList(ArrayList<Status> reposts, boolean firstLoad) {

    mRecyclerView.setAdapter(mTransmitRecyclerAdapter);
    int position = mTransmitRecyclerAdapter.getItemCount();
    mRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
    Log.e("CommentPosition", "" + position);

    this.mReposts = reposts;
    mRepostAdapter.setDatas(mReposts);
    mTransmitRecyclerAdapter.notifyDataSetChanged();
    if (!firstLoad) {
      mRecyclerView.getLayoutManager().scrollToPosition(position);
    }
  }

  @Override
  public void showLoadFooterView(int currentGroup) {
    Log.e("addFooter0", "addFooter0");
    RecyclerViewStateUtils.setFooterViewState(BaseDetailActivity.this, mRecyclerView,
        mComments.size(), LoadingFooterView.State.Loading, null);
  }

  @Override public void hideLoadFooterView() {
    RecyclerViewStateUtils.setFooterViewState(BaseDetailActivity.this, mRecyclerView,
        mComments.size(),
        LoadingFooterView.State.Normal, null);
  }

  private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener =
      new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
          super.onLoadNextPage(view);
          if (mComments != null && mComments.size() > 0) {
            mStatusDetailPresenter.requestMoreComment(mContext);
            showLoadFooterView(1);
          }
        }
      };

  @Override public void ScrollToTop() {
  }
}
