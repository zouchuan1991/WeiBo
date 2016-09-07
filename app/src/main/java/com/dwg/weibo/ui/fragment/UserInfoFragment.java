package com.dwg.weibo.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dwg.weibo.R;
import com.dwg.weibo.entity.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/8.
 */

public class UserInfoFragment extends BaseFragment {

    @BindView(R.id.at)
    TextView at;
    @BindView(R.id.private_mail)
    TextView privateMail;
    @BindView(R.id.detail_info)
    TextView detailInfo;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.introduce)
    TextView introduce;

    private User mUser;

    @OnClick({R.id.at,R.id.private_mail,R.id.detail_info})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.detail_info:

                break;
        }
    }
    @Override
    protected void initParams() {
        Bundle b = getArguments();
        mUser = b.getParcelable("user");
        address.setText(mUser.location);
        introduce.setText(mUser.description);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_self_info;
    }

    @Override
    protected int updateDatas() {
        return 0;
    }


    public static Fragment newInstance(User mUser) {
        Bundle args = new Bundle();
        args.putParcelable("user", mUser);
        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
