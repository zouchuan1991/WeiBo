package com.dwg.weibo.ui.fragment;

import android.view.View;
import android.widget.Button;

import com.dwg.weibo.R;
import com.dwg.weibo.utils.ShareSdkUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/29.
 */

public class DrawerFragment extends BaseFragment {
    @BindView(R.id.loginout)
    Button mBtLoginOut;

    @OnClick(R.id.loginout)
    void loginOut(View v) {
        ShareSdkUtils.loginOut(getActivity());
    }
    @Override
    protected void initParams() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.drawer_menu;
    }
}
