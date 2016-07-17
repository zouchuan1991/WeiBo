package com.dwg.weibo.ui.activity;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import butterknife.BindView;
import com.dwg.weibo.R;
import com.dwg.weibo.adapter.CommentAdapter;
import com.dwg.weibo.adapter.HeaderAndFooterRecyclerAdapter;

/**
 * Created by Administrator on 2016/7/17.
 */
public abstract class BaseDetailActivity extends BaseActivity {
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

  private HeaderAndFooterRecyclerAdapter mTransmitRecyclerAdapter;
  private HeaderAndFooterRecyclerAdapter mCommentRecyclerAdapter;
  private CommentAdapter mCommentAdapter;
  private Context mContext;

  @Override protected void initParams() {
    mContext = this;
    initSwipeRefresh();
    initRecyclerView();
  }

  private void initRecyclerView() {
    LinearLayoutManager linearLayoutManager =
        new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
    mRecyclerView.setLayoutManager(linearLayoutManager);
    mCommentRecyclerAdapter = new HeaderAndFooterRecyclerAdapter(mCommentAdapter);
    mRecyclerView.setAdapter(mCommentRecyclerAdapter);
    addHeaderView();
  }

  protected abstract void addHeaderView();

  private void initSwipeRefresh() {
    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {

      }
    });
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_status_detail;
  }
}
