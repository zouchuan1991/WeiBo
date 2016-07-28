package com.dwg.weibo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.dwg.weibo.service.PostService;
import com.dwg.weibo.ui.activity.BaseActivity;
import com.dwg.weibo.ui.activity.HandleAcitivity;
import com.dwg.weibo.ui.fragment.BaseFragment;
import com.dwg.weibo.ui.fragment.HomeFragment;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

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
    private  String accessToken = null;
    private FragmentManager mFragmentManager;
    private Context mContext;

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
        ShareSDK.initSDK(this);

        Platform pf = ShareSDK.getPlatform(MainActivity.this, SinaWeibo.NAME);
        pf.SSOSetting(true);
        //设置监听
        pf.setPlatformActionListener(this);
        //获取登陆用户的信息，如果没有授权，会先授权，然后获取用户信息
        if(!pf.isAuthValid()){
            pf.authorize(null);
        }else{
            String token = pf.getDb().getToken();
            accessToken = token;
            String userId = pf.getDb().getUserId();
            Log.e("验证通过","yanzhengtongguo");
            Log.e("userName:",token);
            Log.e("userIcan",userId);
        }
    }


    @Override
    protected void initParams() {
        mContext = this;
        mRadioBtnHome.setChecked(true);
        addFragment(new HomeFragment());
    }

    private void addFragment(BaseFragment fragment) {
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.activity_home_content, fragment).commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> res) {


    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}
