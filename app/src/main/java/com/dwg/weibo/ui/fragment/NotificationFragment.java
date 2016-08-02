package com.dwg.weibo.ui.fragment;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.dwg.weibo.R;
import com.dwg.weibo.adapter.NotificationViewPager;
import com.dwg.weibo.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/7/30.
 */

public class NotificationFragment extends BaseFragment {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private NotificationViewPager notificationViewPager;
    private Context mContext;
    private ArrayList<Fragment> mFragments;
    private int[] mTitle = new int[]{R.string.tab_comment, R.string.tab_atme, R.string.tab_mycomment, R.string.tab_sendcomment};

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initParams() {
        this.mContext = getActivity();
        ToastUtils.showToast(mContext, "调用了init方法");
        mFragments = new ArrayList<>();
        mFragments.add(new CommentFragment());
        mFragments.add(new AtMeFragment());
        mFragments.add(new MyCommentFragment());
        mFragments.add(new SendCommentFragment());
        tabs.setTabMode(TabLayout.MODE_FIXED);

        notificationViewPager = new NotificationViewPager(getFragmentManager(), mFragments);
        viewpager.setAdapter(notificationViewPager);
        tabs.setupWithViewPager(viewpager);

        for (int i = 0; i < mTitle.length; i++) {
            tabs.getTabAt(i).setText(mTitle[i]);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notification;
    }

    @Override
    protected int updateDatas() {
        return 0;
    }

}
