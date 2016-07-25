package com.dwg.weibo.ui.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.dwg.weibo.R;
import com.dwg.weibo.adapter.FolderAdapter;
import com.dwg.weibo.entity.AlbumFolderInfo;
import com.dwg.weibo.utils.ScreenUtil;
import com.dwg.weibo.utils.ToastUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/25.
 */

public class ImageFolderPopWindow extends PopupWindow {
    private Context mContext;
    private ArrayList<AlbumFolderInfo> mAlbumFolderInfos;
    private View mView;
    private RecyclerView listView;

    public ImageFolderPopWindow(Context context, ArrayList<AlbumFolderInfo> albumFolderInfos) {
        this.mContext = context;
        this.mAlbumFolderInfos = albumFolderInfos;
        mView = LayoutInflater.from(mContext).inflate(R.layout.compost_imgfolder_list, null);
        setContentView(mView);
        initPopWindow();
        initList();
    }

    private void initList() {
        listView = (RecyclerView) mView.findViewById(R.id.folder_list);
        FolderAdapter folderAdapter = new FolderAdapter(mAlbumFolderInfos, mContext);
        listView.setAdapter(folderAdapter);
        ToastUtils.showToast(mContext, listView.getLayoutManager().getItemCount() + "<<<<<");
    }

    private void initPopWindow() {
        this.setWidth(ScreenUtil.getScreenWidth(mContext));
        this.setHeight(ScreenUtil.getScreenHeight(mContext));
        Drawable dw = new ColorDrawable(0x77000000);
        this.setBackgroundDrawable(dw);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
    }

}
