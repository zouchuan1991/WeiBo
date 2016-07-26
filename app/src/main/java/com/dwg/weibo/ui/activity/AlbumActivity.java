package com.dwg.weibo.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dwg.weibo.R;
import com.dwg.weibo.adapter.GridViewAdapter;
import com.dwg.weibo.entity.AlbumFolderInfo;
import com.dwg.weibo.entity.ImageInf;
import com.dwg.weibo.utils.ImageScan;
import com.dwg.weibo.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/23.
 */

public class AlbumActivity extends BaseActivity implements ImageFolderPopWindow.OnFolderClickListener, GridViewAdapter.OnSelectedImgListener {
    @BindView(R.id.cancal)
    TextView cancal;
    @BindView(R.id.foldername)
    TextView foldername;
    @BindView(R.id.folder_arrow)
    ImageView folderArrow;
    @BindView(R.id.folder)
    LinearLayout folder;
    @BindView(R.id.next)
    TextView next;
    @BindView(R.id.toolbar_layout)
    RelativeLayout toolbarLayout;
    @BindView(R.id.gridview)
    GridView gridview;
    @BindView(R.id.preview)
    TextView preview;
    @BindView(R.id.originpic)
    TextView originpic;

    private Context mContext;
    private ArrayList<AlbumFolderInfo> mAlbumFolderInfos;
    private ImageFolderPopWindow imageFolderPopWindow;
    private ArrayList<ImageInf> mSelectedImages;
    private int mCurrentFolder = 0;
    GridViewAdapter gridViewAdapter;
    @OnClick(R.id.folder)
    void selectFolder(View v) {
        initImageFolderpopWindow();
    }

    @OnClick(R.id.next)
    void onNext(View v) {
        ToastUtils.showToast(mContext, "选择完成");
        Intent i = new Intent();
        i.putParcelableArrayListExtra("selectImgList", mSelectedImages);
        setResult(0, i);
        finish();
    }
    @Override
    protected void initParams() {
        mSelectedImages = getIntent().getParcelableArrayListExtra("selectedImgList");
        mContext = this;
        initImageScan();
        changeNextButtonState();
    }

    private void setAlreadySelectedImg() {
        if (mSelectedImages == null || mSelectedImages.size() == 0) {
            return;
        }
        List<ImageInf> allImagelist = mAlbumFolderInfos.get(0).getImageInfoList();
        for (ImageInf imageInf : mSelectedImages) {
            String selectPath = imageInf.getImageFile().getAbsolutePath();
            for (int i = 0; i < allImagelist.size(); i++) {
                if (allImagelist.get(i).getImageFile().getAbsolutePath().equals(selectPath)) {
                    allImagelist.get(i).setSecleted(true);
                }
            }
        }
    }

    private void initImageFolderpopWindow() {
        imageFolderPopWindow = new ImageFolderPopWindow(mContext, mAlbumFolderInfos);
        imageFolderPopWindow.showAsDropDown(toolbarLayout);
        imageFolderPopWindow.setOnFolderClickListener(this);

    }

    private void initImageScan() {
        new ImageScan(mContext, getSupportLoaderManager()) {
            @Override
            public void scanFinish(ArrayList<AlbumFolderInfo> folderLists) {
                ToastUtils.showToast(mContext, "加载完毕" + folderLists.size());
                mAlbumFolderInfos = folderLists;
                mHandler.sendEmptyMessage(1);
            }
        };
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setAlreadySelectedImg();
            initGridView();
        }
    };

    private void initGridView() {
        gridViewAdapter = new GridViewAdapter(mContext, mAlbumFolderInfos.get(mCurrentFolder).getImageInfoList(), mSelectedImages);
        gridview.setAdapter(gridViewAdapter);
        gridViewAdapter.setOnSelectedImgListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pic_select;
    }

    /**
     * Folder点击的回调
     */
    @Override
    public void onFolderClick(int position) {
        ToastUtils.showToast(mContext, mAlbumFolderInfos.get(position).getFolderName() + "回调");
        mCurrentFolder = position;
        setCurrentFolderName();
        initGridView();
        imageFolderPopWindow.dismiss();

    }

    private void setCurrentFolderName() {
        foldername.setText(mAlbumFolderInfos.get(mCurrentFolder).getFolderName());
    }

    @Override
    public void selectedImg(ArrayList<ImageInf> imageInfs) {
        mSelectedImages = imageInfs;
        changeNextButtonState();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)//api需要大于16，当前最小minsdk为15
    private void changeNextButtonState() {
        if (mSelectedImages == null) {
            return;
        }
        if (mSelectedImages.size() > 0) {
            next.setBackground(getResources().getDrawable(R.drawable.compose_send_corners_highlight_bg));
            next.setText("下一步(" + mSelectedImages.size() + ")");
            next.setClickable(true);
            next.setTextColor(getResources().getColor(R.color.white));
        } else {
            next.setBackground(getResources().getDrawable(R.drawable.compose_send_corners_bg));
            next.setText("下一步");
            next.setClickable(false);
            next.setTextColor(getResources().getColor(R.color.no_access));
        }
    }

    @Override
    public void disSelectedImg(ArrayList<ImageInf> imageInfs) {
        mSelectedImages = imageInfs;
        changeNextButtonState();
    }
}
