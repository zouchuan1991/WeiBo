package com.dwg.weibo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/8.
 */

public class ProfileViewPager extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;

    public ProfileViewPager(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        if (fragments != null) {
            return fragments.size();
        } else {
            return 0;
        }
    }
}
