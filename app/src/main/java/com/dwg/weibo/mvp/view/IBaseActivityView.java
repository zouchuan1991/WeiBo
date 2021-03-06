package com.dwg.weibo.mvp.view;

import com.dwg.weibo.entity.Comment;
import com.dwg.weibo.entity.Status;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/18.
 */
public interface IBaseActivityView {
  void updateCommentList(ArrayList<Comment> comments, boolean firstLoad);

  void updateRepostList(ArrayList<Status> reposts, boolean firstLoad);
  void showLoadFooterView(int currentGroup);

  void hideLoadFooterView();

  void ScrollToTop();
}
