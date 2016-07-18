package com.dwg.weibo.ui.common;

import android.text.TextUtils;
import com.dwg.weibo.entity.Status;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/7/11.
 */

public class FillContentHelper {
    public static void setImgUrl(Status status) {
        //如果微博存在图片
        if (status.pic_urls != null && status.pic_urls.size() > 0) {
            //如果本地私有字段已经被处理过了，就不需要再处理
            if (status.bmiddle_pic_urls.size() > 0) {
                return;
            }
            for (Status.PicUrlsBean picUrlsBean : status.pic_urls) {
                status.thumbnail_pic_urls.add(picUrlsBean.thumbnail_pic);
                status.bmiddle_pic_urls.add(picUrlsBean.thumbnail_pic.replace("thumbnail", "bmiddle"));
                status.origin_pic_urls.add(picUrlsBean.thumbnail_pic.replace("thumbnail", "large"));
            }
        }


    }

    public static String setSource(String source) {
        //如果字段是空的，就没必要在接着下去了。服务器确实有时候会返回空
        if (TextUtils.isEmpty(source)) {
            return null;
        }
        //如果已经提取过关键字，就不需要再处理
        if (!source.contains("</a>")) {
            return null;
        }

        Pattern mpattern = Pattern.compile("<(.*?)>(.*?)</a>");
        Matcher mmatcher = mpattern.matcher(source);

        if (mmatcher.find()) {
            String weibocomefrom = mmatcher.group(2);
            return source = weibocomefrom;
        } else {
            return null;
        }
    }
}
