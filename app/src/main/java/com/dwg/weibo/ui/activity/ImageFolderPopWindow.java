package com.dwg.weibo.ui.activity;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

import com.dwg.weibo.R;
import com.dwg.weibo.adapter.FolderAdapter;
import com.dwg.weibo.entity.AlbumFolderInfo;
import com.dwg.weibo.utils.ScreenUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/25.
 */

public class ImageFolderPopWindow extends PopupWindow {
    private Context mContext;
    private ArrayList<AlbumFolderInfo> mAlbumFolderInfos;
    private View mView;
    private RecyclerView listView;
    FolderAdapter folderAdapter;
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
        folderAdapter = new FolderAdapter(mAlbumFolderInfos, mContext);
        RecyclerView.LayoutManager layout = new
                LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(layout);
        listView.setAdapter(folderAdapter);
        listView.getLayoutParams().height = (int) (ScreenUtil.getScreenHeight(mContext) * 0.6);
    }

    public interface OnFolderClickListener {
        void onFolderClick(int position);
    }

    void setOnFolderClickListener(OnFolderClickListener onFolderClickListener) {
        folderAdapter.setOnFolderClickListener(onFolderClickListener);
    }
    private void initPopWindow() {
        this.setWidth(ScreenUtil.getScreenWidth(mContext));
        this.setHeight(ScreenUtil.getScreenHeight(mContext));
        Drawable dw = new ColorDrawable(0x77000000);
        this.setBackgroundDrawable(dw);
        this.setFocusable(true);
        this.setOutsideTouchable(true);

        this.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();

                Rect rect = new Rect(listView.getLeft(), listView.getTop(), listView.getRight(), listView.getBottom());
                if (!rect.contains((int) x, (int) y)) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
    }

}
