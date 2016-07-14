package com.dwg.weibo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Administrator on 2016/7/10.
 */

public class WeiboApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);
    }
}
