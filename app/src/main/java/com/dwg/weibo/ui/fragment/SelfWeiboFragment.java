package com.dwg.weibo.ui.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.dwg.weibo.R;

import butterknife.BindView;


public class SelfWeiboFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private Context mContext;

    @Override
    protected void initParams() {
        mContext = getActivity();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_self_weibo;
    }

    @Override
    protected int updateDatas() {

        return 0;
    }
}
