package com.dwg.weibo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dwg.weibo.R;
import com.dwg.weibo.entity.AlbumFolderInfo;
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

    public FolderAdapter(ArrayList<AlbumFolderInfo> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        ToastUtils.showToast(mContext, mData.size() + "");
    }

    @Override
    public FolderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ToastUtils.showToast(mContext, "onCreate");
        View v = LayoutInflater.from(mContext).inflate(R.layout.compost_imgfolder_list_item, parent, false);
        return new FolderHolder(v);
    }

    @Override
    public void onBindViewHolder(FolderHolder holder, int position) {
        ToastUtils.showToast(mContext, "填充了");
        AlbumFolderInfo albumFolderInfo = mData.get(position);
//        holder.folderFrontImage.setImageURI("file:///"+albumFolderInfo.getFrontImage().getAbsolutePath());
//        holder.folderName.setText(albumFolderInfo.getFolderName());
//        holder.imageCount.setText("("+albumFolderInfo.getImageInfoList().size()+")");
    }

    @Override
    public int getItemCount() {
        ToastUtils.showToast(mContext, "得到数量");
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

        public FolderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
