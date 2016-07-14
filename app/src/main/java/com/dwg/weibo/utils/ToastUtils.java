package com.dwg.weibo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/7/12.
 */

public class ToastUtils {
    public static void showToast(Context context,String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
