package com.dwg.weibo.ui.fragment;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dwg.weibo.R;
import com.dwg.weibo.adapter.ImageAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/8.
 */

public class UserAlbumFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private Context mContext;
    private ImageAdapter imageAdapter;
    private ArrayList<String> mDatas;

    @Override
    protected void initParams() {
        mContext = getActivity();
        imageAdapter = new ImageAdapter(mContext, mDatas);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        updateDatas();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_self_album;
    }

    @Override
    protected int updateDatas() {

        return 0;
    }
}
