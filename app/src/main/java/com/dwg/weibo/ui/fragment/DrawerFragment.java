package com.dwg.weibo.ui.fragment;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dwg.weibo.R;
import com.dwg.weibo.entity.User;
import com.dwg.weibo.mvp.presenter.IDrawerFragmentPresenter;
import com.dwg.weibo.mvp.presenter.imp.DrawerFragmentPresenter;
import com.dwg.weibo.mvp.view.IDrawerFragmentView;
import com.dwg.weibo.ui.common.FillContent;
import com.dwg.weibo.utils.ShareSdkUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/29.
 */

public class DrawerFragment extends BaseFragment implements IDrawerFragmentView {
    @BindView(R.id.loginout)
    Button mBtLoginOut;
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
    IDrawerFragmentPresenter drawerFragmentPresenter;

    @OnClick(R.id.loginout)
    void loginOut(View v) {
        ShareSdkUtils.loginOut(getActivity());
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
    protected int updateDatas() {
        drawerFragmentPresenter.requestUserInfo();
        return 0;
    }

    @Override
    public void updateUserInfo(User user) {
        FillContent.fillUserInfo(mContext, user, backImage, icon, nickName, attention, fans, sex);
    }


}
