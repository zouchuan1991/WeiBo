package com.dwg.weibo.mvp.view;

import com.dwg.weibo.entity.Comment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/9.
 */
public interface ISelfWeiboFragment {
    void refreshDatas(ArrayList<Comment> commentList);

    void showLoadFooter();

    void hideLoadFooter();

    void showEndFooter();
}
