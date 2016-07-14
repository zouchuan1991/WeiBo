package com.dwg.weibo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.dwg.weibo.ui.fragment.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/9.
 */

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initParams();
    }



    protected abstract void initParams();

    protected abstract int getLayoutId();
}
