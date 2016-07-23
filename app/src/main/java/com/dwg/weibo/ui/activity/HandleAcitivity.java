package com.dwg.weibo.ui.activity;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dwg.weibo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/23.
 */

public class HandleAcitivity extends BaseActivity {


    @BindView(R.id.cancal)
    TextView cancal;
    @BindView(R.id.inputType)
    TextView inputType;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.idea_send)
    TextView ideaSend;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.repost_img)
    ImageView repostImg;
    @BindView(R.id.repost_name)
    TextView repostName;
    @BindView(R.id.repost_content)
    TextView repostContent;
    @BindView(R.id.repost_layout)
    LinearLayout repostLayout;
    @BindView(R.id.ImgList)
    RecyclerView ImgList;
    @BindView(R.id.idea_linearLayout)
    LinearLayout ideaLinearLayout;
    @BindView(R.id.blankspace)
    ImageView blankspace;
    @BindView(R.id.publicbutton)
    TextView publicbutton;
    @BindView(R.id.limitTextView)
    TextView limitTextView;
    @BindView(R.id.picture)
    ImageView picture;
    @BindView(R.id.mentionbutton)
    ImageView mentionbutton;
    @BindView(R.id.trendbutton)
    ImageView trendbutton;
    @BindView(R.id.emoticonbutton)
    ImageView emoticonbutton;
    @BindView(R.id.more_button)
    ImageView moreButton;

    @Override
    protected void initParams() {
        inputType.setText("评论");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_handle;
    }

    @OnClick(R.id.picture)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.picture:
                Intent i = new Intent(this, AlbumActivity.class);
                startActivity(i);
                break;
        }
    }

}
