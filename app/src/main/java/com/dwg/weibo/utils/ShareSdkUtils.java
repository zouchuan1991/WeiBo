package com.dwg.weibo.utils;

import android.content.Context;
import android.util.Log;

import com.dwg.weibo.MainActivity;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

/**
 * Created by Administrator on 2016/7/30.
 */

public class ShareSdkUtils {

    public static void loginOut(Context context) {
        Platform platform = ShareSDK.getPlatform(context, SinaWeibo.NAME);

        if (platform.isValid()) {
            platform.removeAccount();
            ToastUtils.showToast(context, "注销成功");
        }
        authorize(context);
    }

    public static void authorize(final Context context) {
        PlatformActionListener platformActionListener = (MainActivity) context;
        Platform platform = ShareSDK.getPlatform(context, SinaWeibo.NAME);

        platform.SSOSetting(true);
        //设置监听

        platform.setPlatformActionListener(platformActionListener);
        if (!platform.isValid()) {
            platform.authorize(null);
        } else {
            String token = platform.getDb().getToken();
            String userId = platform.getDb().getUserId();
            Log.e("验证通过", platform.getDb().getToken());
            Log.e("userName:", token);
            Log.e("userIcan", userId);
        }
    }

    public static String getToken(Context context) {
        Platform platform = ShareSDK.getPlatform(context, SinaWeibo.NAME);
        if (platform != null) {
            return platform.getDb().getToken();
        } else {
            return null;
        }
    }

    public static boolean isAuthorize(Context context) {
        Platform platform = ShareSDK.getPlatform(context, SinaWeibo.NAME);
        return platform.isValid();
    }
}
