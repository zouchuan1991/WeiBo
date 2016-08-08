package com.dwg.weibo.utils;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.dwg.weibo.R;

/**
 * Created by Administrator on 2016/8/3.
 */

public class WeiboContentSpannableClick extends ClickableSpan {
    private Context mContext;

    public WeiboContentSpannableClick(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onClick(View widget) {

    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(mContext.getResources().getColor(R.color.click_color));
        ds.setUnderlineText(false);
    }
}
