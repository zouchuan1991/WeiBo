package com.dwg.weibo.ui.common;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dwg.weibo.R;
import com.dwg.weibo.entity.User;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/11.
 */
public class UserInfoHeaderView extends LinearLayout {

    private final User mUser;
    @BindView(R.id.backImage)
    SimpleDraweeView backImage;
    @BindView(R.id.icon)
    SimpleDraweeView icon;
    @BindView(R.id.nick_name)
    TextView nickName;
    @BindView(R.id.sex)
    ImageView sex;
    @BindView(R.id.attention)
    TextView attention;
    @BindView(R.id.fans)
    TextView fans;
    private Context mContext;
    private View mView;

    public UserInfoHeaderView(Context context, User user) {
        super(context);
        mUser = user;
        mContext = context;
        init();
    }

    private void init() {
        mView = inflate(mContext, R.layout.compose_profile, null);
        ButterKnife.bind(this, mView);
        initContent();
    }

    private void initContent() {
        FillContent.fillUserInfo(mContext, mUser, backImage, icon, nickName, attention, fans, sex);
    }


}
