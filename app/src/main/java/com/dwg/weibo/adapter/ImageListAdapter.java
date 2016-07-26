package com.dwg.weibo.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dwg.weibo.R;
import com.dwg.weibo.entity.ImageInf;
import com.dwg.weibo.utils.ImageScan;
import com.dwg.weibo.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/26.
 */

public class ImageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ImageInf> mDatas;
    private Context mContext;
    private static final int TYPE_IMAGE_ITEM = 1;
    private static final int TYPE_BUTTON_ADD = 2;
    private OnAddImageClickListener onAddImageClickListener;
    private int ImageCount = 9;

    public ImageListAdapter(ArrayList<ImageInf> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        ToastUtils.showToast(mContext, mDatas.size() + "总共");
    }

    public void setDatas(ArrayList<ImageInf> datas) {
        this.mDatas = datas;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mDatas.size()) {
            return TYPE_BUTTON_ADD;
        } else if (position < mDatas.size()) {
            return TYPE_IMAGE_ITEM;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_IMAGE_ITEM) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.compose_image_item, parent, false);
            return new ImageListHolder(v);
        } else if (viewType == TYPE_BUTTON_ADD) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.compose_pic_add, parent, false);
            return new ImageAddHolder(v);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        if (mDatas != null && mDatas.size() > 0) {
            return mDatas.size() + 1;//增加最后的增加照片按钮
        } else {
            return 0;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ImageListHolder) {
            Uri uri = Uri.parse("file:///" + mDatas.get(position).getImageFile().getAbsolutePath());
            ImageScan.showThumb(mContext, uri, ((ImageListHolder) holder).image);
            ((ImageListHolder) holder).close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatas.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mDatas.size() + 1);
                }
            });
        } else if (holder instanceof ImageAddHolder) {
            if (position == ImageCount) {
                ((ImageAddHolder) holder).mView.setVisibility(View.GONE);
            } else {
                ((ImageAddHolder) holder).mView.setVisibility(View.VISIBLE);
            }
        }
    }


    public class ImageListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        SimpleDraweeView image;
        @BindView(R.id.close)
        ImageView close;

        public ImageListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ImageAddHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.add)
        ImageView add;
        private View mView;

        @OnClick(R.id.add)
        void onAddImageClick() {
            onAddImageClickListener.OnAddImageClick();
        }

        public ImageAddHolder(View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnAddImageClickListener {
        void OnAddImageClick();
    }

    public void setOnAddImageClickListener(OnAddImageClickListener onAddImageClickListener) {
        this.onAddImageClickListener = onAddImageClickListener;
    }
}
