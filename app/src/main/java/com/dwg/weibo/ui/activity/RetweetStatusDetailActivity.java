package com.dwg.weibo.ui.activity;

import android.widget.LinearLayout;

import com.dwg.weibo.ui.common.RetweetHeaderView;
import com.dwg.weibo.utils.RecyclerViewUtils;

/**
 * Created by Administrator on 2016/7/21.
 */
public class RetweetStatusDetailActivity extends BaseDetailActivity {
    LinearLayout mLinearlayout;

    @Override
    protected void addHeaderView(int type) {
        mLinearlayout = new RetweetHeaderView(this, mStatus);
        RecyclerViewUtils.addheadView(mRecyclerView, mLinearlayout);
    }
}
