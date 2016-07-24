package com.dwg.weibo.ui.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dwg.weibo.R;
import com.dwg.weibo.adapter.GridViewAdapter;
import com.dwg.weibo.entity.AlbumFolderInfo;
import com.dwg.weibo.utils.ImageScan;
import com.dwg.weibo.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/7/23.
 */

public class AlbumActivity extends BaseActivity {
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
    @Override
    protected void initParams() {
        mContext = this;
        initImageScan();
    }

    private void initImageScan() {
        ImageScan imageScan = new ImageScan(mContext, getSupportLoaderManager()) {
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
            initGridView();
        }
    };

    private void initGridView() {
        gridview.setAdapter(new GridViewAdapter(mContext, mAlbumFolderInfos.get(0).getImageInfoList()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pic_select;
    }


}
