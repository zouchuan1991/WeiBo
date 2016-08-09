package com.dwg.weibo.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dwg.weibo.MainActivity;
import com.dwg.weibo.R;
import com.dwg.weibo.entity.User;
import com.dwg.weibo.mvp.presenter.IDrawerFragmentPresenter;
import com.dwg.weibo.mvp.presenter.imp.DrawerFragmentPresenter;
import com.dwg.weibo.mvp.view.IDrawerFragmentView;
import com.dwg.weibo.ui.activity.ProfileActivity;
import com.dwg.weibo.ui.common.FillContent;
import com.dwg.weibo.utils.ShareSdkUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/29.
 */

public class DrawerFragment extends BaseFragment implements IDrawerFragmentView {


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
    @BindView(R.id.weibo)
    TextView weibo;
    @BindView(R.id.notification)
    TextView notification;
    @BindView(R.id.private_message)
    TextView privateMessage;
    @BindView(R.id.loginout)
    Button loginout;
    @BindView(R.id.profile)
    RelativeLayout layout;

    private Context mContext;
    IDrawerFragmentPresenter drawerFragmentPresenter;
    private User mUser;

    @OnClick({R.id.loginout, R.id.profile})
    void loginOut(View v) {
        switch (v.getId()) {
            case R.id.loginout://注销账号
                ShareSdkUtils.loginOut(getActivity());
                break;
            case R.id.profile://点击了自己
                Intent i = new Intent(mContext, ProfileActivity.class);
                i.putExtra("user", mUser);
                startActivity(i);
                break;
        }
    }


    @Override
    protected void initParams() {
        mContext = getActivity();
        drawerFragmentPresenter = new DrawerFragmentPresenter(mContext, this);
        updateDatas();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.drawer_menu;
    }

    @Override
    public int updateDatas() {
        drawerFragmentPresenter.requestUserInfo();
        return 0;
    }

    @Override
    public void updateUserInfo(User user) {
        this.mUser = user;
        FillContent.fillUserInfo(mContext, user, backImage, icon, nickName, attention, fans, sex);
    }


    @OnClick({R.id.weibo, R.id.notification, R.id.private_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.weibo:
                ((MainActivity) mContext).replaceFragment(MainActivity.TYPE_HOME_FRAGMENT);
                break;
            case R.id.notification:
                ((MainActivity) mContext).replaceFragment(MainActivity.TYPE_COMMENT_FRAGMENT);
                break;
            case R.id.private_message:
                break;
        }
    }
}
