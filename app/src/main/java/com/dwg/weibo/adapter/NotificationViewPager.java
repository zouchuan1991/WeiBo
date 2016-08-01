package com.dwg.weibo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/30.
 */

public class NotificationViewPager extends FragmentPagerAdapter {
    private ArrayList<Fragment> mDatas;

    public NotificationViewPager(FragmentManager fm, ArrayList<Fragment> mDatas) {
        super(fm);
        this.mDatas = mDatas;
    }

    @Override
    public Fragment getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }
}
