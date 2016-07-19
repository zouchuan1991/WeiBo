package com.dwg.weibo.utils;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.dwg.weibo.adapter.HeaderAndFooterRecyclerAdapter;
import com.dwg.weibo.ui.common.LoadingFooterView;

public class RecyclerViewStateUtils {

    /**
     * 设置headerAndFooterAdapter的FooterView State
     *
     * @param instance      context
     * @param recyclerView  recyclerView
     * @param pageSize      分页展示时，recyclerView每一页的数量
     * @param state         FooterView State
     * @param errorListener FooterView处于Error状态时的点击事件
     */
    public static void setFooterViewState(Activity instance, RecyclerView recyclerView,
        int pageSize, LoadingFooterView.State state, View.OnClickListener errorListener) {

        Log.e("addFooter1", "addFooter");

        if (instance == null || instance.isFinishing()) {
            return;
        }

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof HeaderAndFooterRecyclerAdapter)) {
            return;
        }

        HeaderAndFooterRecyclerAdapter headerAndFooterAdapter = (HeaderAndFooterRecyclerAdapter) outerAdapter;

        //只有一页的时候，就别加什么FooterView了
        if (headerAndFooterAdapter.getInnerAdapter().getItemCount() < pageSize) {
            return;
        }

        LoadingFooterView footerView;

        //已经有footerView了
        if (headerAndFooterAdapter.getFooterViewsCount() > 0) {
            footerView = (LoadingFooterView) headerAndFooterAdapter.getFooterView();
            footerView.setState(state);

            if (state == LoadingFooterView.State.NetWorkError) {
                footerView.setOnClickListener(errorListener);
            }
            recyclerView.scrollToPosition(headerAndFooterAdapter.getItemCount() - 1);
        } else {
            Log.e("addFooter2", "addFooter");
            footerView = new LoadingFooterView(instance);
            footerView.setState(state);

            if (state == LoadingFooterView.State.NetWorkError) {
                footerView.setOnClickListener(errorListener);
            }

            headerAndFooterAdapter.addFooterView(footerView);
            recyclerView.scrollToPosition(headerAndFooterAdapter.getItemCount() - 1);
        }
    }

    /**
     * 获取当前RecyclerView.FooterView的状态
     *
     * @param recyclerView
     */
    public static LoadingFooterView.State getFooterViewState(RecyclerView recyclerView) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HeaderAndFooterRecyclerAdapter) {
            if (((HeaderAndFooterRecyclerAdapter) outerAdapter).getFooterViewsCount() > 0) {
                LoadingFooterView footerView =
                    (LoadingFooterView) ((HeaderAndFooterRecyclerAdapter) outerAdapter).getFooterView();
                return footerView.getState();
            }
        }

        return LoadingFooterView.State.Normal;
    }

    /**
     * 设置当前RecyclerView.FooterView的状态
     *
     * @param recyclerView
     * @param state
     */
    public static void setFooterViewState(RecyclerView recyclerView,
        LoadingFooterView.State state) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HeaderAndFooterRecyclerAdapter) {
            if (((HeaderAndFooterRecyclerAdapter) outerAdapter).getFooterViewsCount() > 0) {
                LoadingFooterView footerView =
                    (LoadingFooterView) ((HeaderAndFooterRecyclerAdapter) outerAdapter).getFooterView();
                footerView.setState(state);
            }
        }
    }
}