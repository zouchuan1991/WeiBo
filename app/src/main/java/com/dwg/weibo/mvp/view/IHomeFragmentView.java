package com.dwg.weibo.mvp.view;

import com.dwg.weibo.entity.Status;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/11.
 */

public interface IHomeFragmentView {
    void hideFooter();

    void showLoadFooterView();

    void updateListView(ArrayList<Status> statuses);

    void scrollToTop();

    void hideLoadingIcon();

    void showRecyclerView();

    void hideRecyclerView();

    void showEndFooterView();

    void hideRefreshView();
}
