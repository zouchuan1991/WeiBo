package com.dwg.weibo.mvp.presenter;

import android.content.Context;

/**
 * Created by Administrator on 2016/7/11.
 */

public interface IHomeFragmentPresenter {
    void firstLoadDatas(Context context);

    void requestMoreData(Context context);
}
