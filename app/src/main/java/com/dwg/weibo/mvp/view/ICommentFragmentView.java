package com.dwg.weibo.mvp.view;

import com.dwg.weibo.entity.CommentList;

/**
 * Created by Administrator on 2016/8/1.
 */

public interface ICommentFragmentView {
    void refreshDatas(CommentList commentList);

    void showLoadFooter();
}
