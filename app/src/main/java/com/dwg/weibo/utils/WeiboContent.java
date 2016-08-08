package com.dwg.weibo.utils;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/8/3.
 */

public class WeiboContent {
    public static final String AT = "@[\u4e00-\u9fa5a-zA-Z0-9_-]{2,30}";
    public static final String TOPIC = "#[^#]+#";
    public static final String URL = "http://[a-zA-Z0-9+&@#/%?=~_\\-!:,\\.]+";
    public static final String EMOJI = "\\[[\u4e00-\u9fa5a-zA-Z0-9]+\\]";
    public static final String ALL = "(" + AT + ")" + "|" + "(" + TOPIC + ")" + "|" + "(" + URL + ")" + "|" + "(" + EMOJI + ")";

    public static SpannableStringBuilder getSpannableStringBuilder(final Context context, String source) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(source);
        Pattern pattern = Pattern.compile(ALL);
        Matcher matcher = pattern.matcher(spannableStringBuilder);

        while (matcher.find()) {
            String AT = matcher.group(1);
            String TOPIC = matcher.group(2);
            String URL = matcher.group(3);
            String EMOJI = matcher.group(4);
            if (AT != null) {
                System.out.println("at" + AT);
                int start = matcher.start(1);
                int end = start + AT.length();
                WeiboContentSpannableClick weiboContentSpannableClick = new WeiboContentSpannableClick(context) {
                    @Override
                    public void onClick(View widget) {
                        super.onClick(widget);
                        ToastUtils.showToast(context, "点击了at");
                    }
                };
                spannableStringBuilder.setSpan(weiboContentSpannableClick, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }
            if (TOPIC != null) {
                System.out.println("topic" + TOPIC);
                int start = matcher.start(2);
                int end = start + TOPIC.length();
                WeiboContentSpannableClick weiboContentSpannableClick = new WeiboContentSpannableClick(context) {
                    @Override
                    public void onClick(View widget) {
                        super.onClick(widget);
                        ToastUtils.showToast(context, "点击了topic");
                    }
                };
                spannableStringBuilder.setSpan(weiboContentSpannableClick, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }
            if (URL != null) {
                System.out.println("url" + URL);
                int start = matcher.start(3);
                int end = start + URL.length();
                WeiboContentSpannableClick weiboContentSpannableClick = new WeiboContentSpannableClick(context) {
                    @Override
                    public void onClick(View widget) {
                        super.onClick(widget);
                        ToastUtils.showToast(context, "点击了url");
                    }
                };
                spannableStringBuilder.setSpan(weiboContentSpannableClick, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }
            if (EMOJI != null) {
                System.out.println("emoji" + EMOJI);
            }
        }
        return spannableStringBuilder;
    }
}
