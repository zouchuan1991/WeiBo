package com.dwg.weibo.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dwg.weibo.R;
import com.dwg.weibo.adapter.ImageListAdapter;
import com.dwg.weibo.entity.ImageInf;
import com.dwg.weibo.entity.Status;
import com.dwg.weibo.entity.WeiBoCreateBean;
import com.dwg.weibo.service.PostService;
import com.dwg.weibo.ui.common.FillContent;
import com.dwg.weibo.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/23.
 */

public class HandleAcitivity extends BaseActivity implements ImageListAdapter.OnAddImageClickListener {


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
    SimpleDraweeView repostImg;
    @BindView(R.id.repost_name)
    TextView repostName;
    @BindView(R.id.repost_content)
    TextView repostContent;
    @BindView(R.id.repost_layout)
    LinearLayout repostLayout;
    @BindView(R.id.ImgList)
    RecyclerView mImgRecyclerView;
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


    private String mPostType;
    private Status mStatus;

    @OnClick(R.id.idea_send)
    void sendClick(View view) {
        WeiBoCreateBean weiBoCreateBean = new WeiBoCreateBean(content.getText().toString(), mSelectedImages, mStatus);
        Intent i = new Intent(this, PostService.class);
        i.putExtra("weiboCreateBean", weiBoCreateBean);
        i.putExtra("POST_TYPE", mPostType);
        startService(i);
        finish();
    }
    private ArrayList<ImageInf> mSelectedImages = new ArrayList<>();
    private Context mContext;
    @Override
    protected void initParams() {
        mContext = this;
        mPostType = getIntent().getStringExtra("POST_TYPE");
        mStatus = getIntent().getParcelableExtra("createStatus");
        initContent();
        Log.e("POST_TYPE", mPostType);
    }

    /**
     * 对不同的界面进行不同的渲染
     */
    private void initContent() {
        switch (mPostType) {
            case PostService.POST_COMMNET_WEIBO:
                inputType.setText(R.string.post_comment_weibo);
                repostLayout.setVisibility(View.GONE);
                break;
            case PostService.POST_CREATE_WEIBO:
                inputType.setText(R.string.post_create_weibo);
                repostLayout.setVisibility(View.GONE);
                break;
            case PostService.POST_REPLY_WEIBO:
                inputType.setText(R.string.post_reply_weibo);
                break;
            case PostService.POST_REPOST_WEIBO:
                inputType.setText(R.string.post_repost_weibo);
                repostLayout.setVisibility(View.VISIBLE);
                repostWeibo();
                break;
        }
    }

    private void repostWeibo() {

        if (mStatus.retweeted_status != null) {
            content.setText("//@" + mStatus.retweeted_status.text);
            FillContent.fillRepostOriginContent(mContext, mStatus.retweeted_status, repostImg, repostName, repostContent);
        } else {
            FillContent.fillRepostOriginContent(mContext, mStatus, repostImg, repostName, repostContent);
        }

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
                i.putParcelableArrayListExtra("selectedImgList", mSelectedImages);
                startActivityForResult(i, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (data != null) {
                    mSelectedImages = data.getParcelableArrayListExtra("selectImgList");
                    ToastUtils.showToast(mContext, "选择了几张照片" + mSelectedImages.size());
                    initImgList();
                }
                break;
        }
    }

    private void initImgList() {
        mImgRecyclerView.setVisibility(View.VISIBLE);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3);
        mImgRecyclerView.setLayoutManager(layoutManager);
        ImageListAdapter imageListAdapter = new ImageListAdapter(mSelectedImages, mContext);
        imageListAdapter.setOnAddImageClickListener(this);
        mImgRecyclerView.setAdapter(imageListAdapter);
    }

    @Override
    public void OnAddImageClick() {
        Intent i = new Intent(this, AlbumActivity.class);
        i.putParcelableArrayListExtra("selectedImgList", mSelectedImages);
        startActivityForResult(i, 0);
    }
}
