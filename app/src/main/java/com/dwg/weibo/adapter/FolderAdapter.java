package com.dwg.weibo.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dwg.weibo.R;
import com.dwg.weibo.entity.AlbumFolderInfo;
import com.dwg.weibo.ui.activity.ImageFolderPopWindow;
import com.dwg.weibo.utils.ImageScan;
import com.dwg.weibo.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/25.
 */

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderHolder> {

    private Context mContext;
    private ArrayList<AlbumFolderInfo> mData;
    private ImageFolderPopWindow.OnFolderClickListener onFolderClickListener;
    public FolderAdapter(ArrayList<AlbumFolderInfo> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        ToastUtils.showToast(mContext, mData.size() + "");
    }

    public void setOnFolderClickListener(ImageFolderPopWindow.OnFolderClickListener onFolderClickListener) {
        this.onFolderClickListener = onFolderClickListener;
    }
    @Override
    public FolderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ToastUtils.showToast(mContext, "onCreate");
        View v = LayoutInflater.from(mContext).inflate(R.layout.compost_imgfolder_list_item, parent, false);
        return new FolderHolder(v);
    }

    @Override
    public void onBindViewHolder(FolderHolder holder, final int position) {
        final AlbumFolderInfo albumFolderInfo = mData.get(position);
        //holder.folderFrontImage.setImageURI("file:///"+albumFolderInfo.getFrontImage().getAbsolutePath());
        ImageScan.showThumb(mContext, Uri.parse("file:///" + albumFolderInfo.getFrontImage().getAbsolutePath()), holder.folderFrontImage);
        holder.folderName.setText(albumFolderInfo.getFolderName());
        holder.imageCount.setText("(" + albumFolderInfo.getImageInfoList().size() + ")");
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FolderAdapter.this.onFolderClickListener.onFolderClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public class FolderHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.folder_front_image)
        SimpleDraweeView folderFrontImage;
        @BindView(R.id.folder_name)
        TextView folderName;
        @BindView(R.id.image_count)
        TextView imageCount;

        private View mView;
        public FolderHolder(View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, itemView);

        }
    }
}
