package com.dwg.weibo.ui.activity;

import android.widget.LinearLayout;
import com.dwg.weibo.ui.common.OriginHeaderView;
import com.dwg.weibo.utils.RecyclerViewUtils;

/**
 * Created by Administrator on 2016/7/17.
 */
public class OriginStatusDetailActivity extends BaseDetailActivity {

  private LinearLayout mLinearLayout;

  @Override protected void addHeaderView(int type) {
    mLinearLayout = new OriginHeaderView(this, mStatus, type);
    RecyclerViewUtils.addheadView(mRecyclerView, mLinearLayout);
  }
}
