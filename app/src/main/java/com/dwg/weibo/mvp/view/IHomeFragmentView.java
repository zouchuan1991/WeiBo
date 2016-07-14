package com.dwg.weibo.mvp.view;

import com.dwg.weibo.entity.Status;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/11.
 */

public interface IHomeFragmentView {

    public void UpdateListView(ArrayList<Status> statuses);
}
