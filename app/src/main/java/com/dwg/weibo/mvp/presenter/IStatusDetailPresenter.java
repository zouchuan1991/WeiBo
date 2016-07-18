package com.dwg.weibo.mvp.presenter;

import android.content.Context;

/**
 * Created by Administrator on 2016/7/18.
 */
public interface IStatusDetailPresenter {
  void pullToRefreshComment(Context context);

  void requestMoreComment(Context context);

  void pullToRefreshTransmit(Context context);

  void requestMOreTransmit(Context context);
}
