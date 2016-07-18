package com.dwg.weibo.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.dwg.weibo.adapter.HeaderAndFooterRecyclerAdapter;

/**
 * Created by Administrator on 2016/7/18.
 */
public class RecyclerViewUtils {
  public static void addheadView(RecyclerView recyclerView, View view) {
    HeaderAndFooterRecyclerAdapter headerAndFooterRecyclerAdapter =
        (HeaderAndFooterRecyclerAdapter) recyclerView.getAdapter();
    headerAndFooterRecyclerAdapter.addHeaderView(view);
  }
}
