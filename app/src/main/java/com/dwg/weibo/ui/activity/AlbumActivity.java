package com.dwg.weibo.ui.activity;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dwg.weibo.R;

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

    @Override
    protected void initParams() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pic_select;
    }

}
