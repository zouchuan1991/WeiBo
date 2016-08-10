package com.dwg.weibo.ui.activity;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dwg.weibo.R;
import com.dwg.weibo.adapter.ProfileViewPager;
import com.dwg.weibo.entity.User;
import com.dwg.weibo.ui.common.FillContent;
import com.dwg.weibo.ui.fragment.UserAlbumFragment;
import com.dwg.weibo.ui.fragment.UserInfoFragment;
import com.dwg.weibo.ui.fragment.UserWeiboFragment;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/8.
 */

public class ProfileActivity extends BaseActivity {


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
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private User mUser;
    private Context mContext;
    private ProfileViewPager mProfileViewPager;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] mTitle = new String[]{"关于", "微博", "相册"};

    @Override
    protected void initParams() {
        mContext = this;
        mUser = getIntent().getParcelableExtra("user");
        FillContent.fillUserInfo(mContext, mUser, backImage, icon, nickName, attention, fans, sex);
        fragments.add(new UserInfoFragment());
        fragments.add(UserWeiboFragment.newInstance(mUser));
        fragments.add(new UserAlbumFragment());
        mProfileViewPager = new ProfileViewPager(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(mProfileViewPager);
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        tablayout.setupWithViewPager(viewpager);
        for (int i = 0; i < mTitle.length; i++) {
            tablayout.getTabAt(i).setText(mTitle[i]);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile;
    }


}
