package com.dwg.weibo.mvp.view;

import com.dwg.weibo.entity.Status;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/9.
 */
public interface ISelfFragmentView {
    void refreshDatas(ArrayList<Status> statuses);

    void showLoadFooter();

    void hideLoadFooter();

    void showEndFooter();
}
