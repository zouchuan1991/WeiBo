package com.dwg.weibo.entity;

import java.util.ArrayList;

public class CommentList {

  /**
   * 微博列表
   */
  public ArrayList<Comment> comments;
  public String previous_cursor;
  public String next_cursor;
  public int total_number;
}
