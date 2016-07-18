package com.dwg.weibo.ui.activity;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.BindView;
import com.dwg.weibo.R;
import com.dwg.weibo.adapter.CommentAdapter;
import com.dwg.weibo.adapter.HeaderAndFooterRecyclerAdapter;
import com.dwg.weibo.entity.Comment;
import com.dwg.weibo.entity.Status;
import com.dwg.weibo.mvp.presenter.IStatusDetailPresenter;
import com.dwg.weibo.mvp.presenter.imp.StatusDetailPresenterImp;
import com.dwg.weibo.mvp.view.IBaseActivityView;
import com.dwg.weibo.ui.common.LoadingFooterView;
import com.dwg.weibo.utils.RecyclerViewStateUtils;
import com.dwg.weibo.widget.EndlessRecyclerOnScrollListener;
import java.util.ArrayList;

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

  private IStatusDetailPresenter mStatusDetailPresenter;
  private HeaderAndFooterRecyclerAdapter mTransmitRecyclerAdapter;
  private HeaderAndFooterRecyclerAdapter mCommentRecyclerAdapter;
  private ArrayList<Comment> mComments = new ArrayList<>();
  private ArrayList<Status> mStatuses = new ArrayList<>();
  private CommentAdapter mCommentAdapter;
  private Context mContext;
  public Status mStatus;
  private int mType;
  @Override protected void initParams() {
    mContext = this;
    mStatus = getIntent().getParcelableExtra("status");
    mCommentAdapter = new CommentAdapter(mContext, mComments);
    mStatusDetailPresenter = new StatusDetailPresenterImp(this, mStatus);
    mStatusDetailPresenter.pullToRefreshComment(mContext);
    initSwipeRefresh();
    initRecyclerView();

  }

  private void initRecyclerView() {
    LinearLayoutManager linearLayoutManager =
        new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
    mRecyclerView.setLayoutManager(linearLayoutManager);
    mCommentRecyclerAdapter = new HeaderAndFooterRecyclerAdapter(mCommentAdapter);
    mRecyclerView.setAdapter(mCommentRecyclerAdapter);
    addHeaderView(mType);
    mRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
  }

  protected abstract void addHeaderView(int type);

  private void initSwipeRefresh() {
    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {

      }
    });
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_status_detail;
  }

  @Override public void updateCommentList(ArrayList<Comment> comments) {
    this.mComments = comments;
    mCommentAdapter.setDatas(comments);
    mCommentRecyclerAdapter.notifyDataSetChanged();
  }

  @Override
  public void showLoadFooterView(int currentGroup) {
    RecyclerViewStateUtils.setFooterViewState(BaseDetailActivity.this, mRecyclerView,
        mCommentAdapter.getItemCount(), LoadingFooterView.State.Loading, null);
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
}
