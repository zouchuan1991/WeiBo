package com.dwg.weibo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.dwg.weibo.service.PostService;
import com.dwg.weibo.ui.activity.BaseActivity;
import com.dwg.weibo.ui.activity.HandleAcitivity;
import com.dwg.weibo.ui.fragment.BaseFragment;
import com.dwg.weibo.ui.fragment.DrawerFragment;
import com.dwg.weibo.ui.fragment.HomeFragment;
import com.dwg.weibo.ui.fragment.NotificationFragment;
import com.dwg.weibo.utils.ShareSdkUtils;
import com.dwg.weibo.utils.ToastUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

public class MainActivity extends BaseActivity implements PlatformActionListener {

    @BindView(R.id.radio_btn_home)
    RadioButton mRadioBtnHome;
    @BindView(R.id.radio_btn_message)
    RadioButton mRadioBtnMessage;
    @BindView(R.id.image_btn_add)
    ImageButton mImageBtnAdd;
    @BindView(R.id.radio_btn_discover)
    RadioButton mRadioBtnDiscover;
    @BindView(R.id.radio_btn_profile)
    RadioButton mRadioBtnProfile;
    @BindView(R.id.activity_main)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private FragmentManager mFragmentManager;
    private Context mContext;
    private ActionBarDrawerToggle mDrawerToggle;
    private BaseFragment mHomeFragment;
    private BaseFragment mNotificationFragment;
    private DrawerFragment mDrawerFragment;
    public static final String TYPE_HOME_FRAGMENT = "HOME_FRAGMENT";
    public static final String TYPE_COMMENT_FRAGMENT = "COMMENT_FRAGMENT";
    @OnClick({R.id.radio_btn_home, R.id.radio_btn_message, R.id.image_btn_add, R.id.radio_btn_discover, R.id.radio_btn_profile})
    public void onTabClick(View view) {
        switch (view.getId()) {
            case R.id.radio_btn_home:
                Toast.makeText(this, "111", Toast.LENGTH_SHORT).show();
                thirdSinaLogin();
                break;
            case R.id.image_btn_add:
                Intent i = new Intent(mContext, HandleAcitivity.class);
                i.putExtra("POST_TYPE", PostService.POST_CREATE_WEIBO);
                startActivity(i);
                break;
        }
    }



    private void thirdSinaLogin() {


    }


    @Override
    protected void initParams() {
        mContext = this;
        mFragmentManager = getSupportFragmentManager();
        mRadioBtnHome.setChecked(true);
        setSupportActionBar(toolbar);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                //toolbar,
                R.string.app_name,
                R.string.app_name

        ) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        if (!ShareSdkUtils.isAuthorize(mContext)) {
            ToastUtils.showToast(mContext, "还没有进行授权");
            ShareSdkUtils.authorize(mContext);
        }
        mHomeFragment = new HomeFragment();
        mNotificationFragment = new NotificationFragment();
        mDrawerFragment = new DrawerFragment();
        addDrawerFragment(mDrawerFragment);
        addFragment(mHomeFragment);
    }

    private void addDrawerFragment(BaseFragment drawerFragment) {
        mFragmentManager.beginTransaction()
                .add(R.id.drawer_content, drawerFragment)
                .commit();
    }

    private void addFragment(BaseFragment fragment) {

        mFragmentManager.beginTransaction()
                .add(R.id.activity_home_content, fragment, "homeFragment")
                .commit();
    }

    public void replaceFragment(String type) {
        mDrawerLayout.closeDrawers();
        BaseFragment fragment;
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        switch (type) {
            case TYPE_HOME_FRAGMENT:
                fragment = mHomeFragment;
                if (!fragment.isAdded()) {
                    transaction.hide(mNotificationFragment)
                            .add(R.id.activity_home_content, mHomeFragment)
                            .commit();
                } else {
                    transaction.hide(mNotificationFragment)
                            .show(mHomeFragment)
                            .commit();
                }
                break;
            case TYPE_COMMENT_FRAGMENT:
                fragment = mNotificationFragment;
                if (!fragment.isAdded()) {
                    transaction.hide(mHomeFragment)
                            .add(R.id.activity_home_content, mNotificationFragment)
                            .commit();
                } else {
                    transaction.hide(mHomeFragment)
                            .show(mNotificationFragment)
                            .commit();
                }
                break;
            default:
        }


    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> res) {
        ToastUtils.showToast(mContext, "授权成功");
        mDrawerFragment.updateDatas();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        ToastUtils.showToast(mContext, "授权失败");
        createDialog();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        ToastUtils.showToast(mContext, "取消授权");
        createDialog();
    }

    public void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("授权失败，是否重新授权");
        builder.setPositiveButton("再次授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ShareSdkUtils.authorize(mContext);
            }
        });
        builder.setNegativeButton("退出应用", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create();
        builder.show();
    }
}
