package com.dwg.weibo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.dwg.weibo.R;
import com.dwg.weibo.entity.ImageInf;
import com.dwg.weibo.utils.ImageScan;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/24.
 */

public class GridViewAdapter extends BaseAdapter {
    private List<ImageInf> mDatas;
    private Context mContext;
    private ArrayList<ImageInf> mSelectedImages;
    private OnSelectedImgListener mOnSelectedImgListener;

    public GridViewAdapter(Context context, List<ImageInf> datas, ArrayList<ImageInf> imageInfs) {
        this.mContext = context;
        this.mDatas = datas;
        this.mSelectedImages = imageInfs;
    }

    public void setOnSelectedImgListener(OnSelectedImgListener onSelectedImgListener) {
        this.mOnSelectedImgListener = onSelectedImgListener;
    }
    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.compose_pic_grid_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        //viewHolder.itemImg.setImageURI("file:///"+mDatas.get(position).getImageFile().getAbsolutePath());
        ImageScan.showThumb(mContext, Uri.parse("file:///" + mDatas.get(position).getImageFile().getAbsolutePath()), viewHolder.itemImg);
        handleSelectedImg(mDatas.get(position), viewHolder);
        return convertView;

    }

    private void handleSelectedImg(final ImageInf imageInf, final ViewHolder viewHolder) {
        if (imageInf.getSecleted()) {
            viewHolder.selectImg.setImageResource(R.drawable.compose_photo_preview_right);
            viewHolder.itemImg.setColorFilter(Color.parseColor("#77000000"));
        } else {
            viewHolder.selectImg.setImageResource(R.drawable.compose_guide_check_box_default);
            viewHolder.itemImg.setColorFilter(null);
        }
        viewHolder.selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imageInf.getSecleted()) {
                    imageInf.setSecleted(true);
                    selectedImg(imageInf);
                    mOnSelectedImgListener.selectedImg(mSelectedImages);
                    viewHolder.itemImg.setColorFilter(0x77000000);
                    viewHolder.selectImg.setImageResource(R.drawable.compose_photo_preview_right);
                } else {
                    imageInf.setSecleted(false);
                    disSelectedImg(imageInf);
                    mOnSelectedImgListener.disSelectedImg(mSelectedImages);
                    viewHolder.itemImg.setColorFilter(null);
                    viewHolder.selectImg.setImageResource(R.drawable.compose_guide_check_box_default);
                }
            }


        });
    }

    private void disSelectedImg(ImageInf imageInf) {
        for (int i = 0; i < mSelectedImages.size(); i++) {
            if (mSelectedImages.get(i).getImageFile().getAbsolutePath().equals(imageInf.getImageFile().getAbsolutePath())) {
                mSelectedImages.remove(i);
            }
        }
    }

    private void selectedImg(ImageInf imageInf) {
        mSelectedImages.add(imageInf);

    }

    public interface OnSelectedImgListener {
        void selectedImg(ArrayList<ImageInf> imageInfs);

        void disSelectedImg(ArrayList<ImageInf> imageInfs);
    }
    public class ViewHolder {


        @BindView(R.id.item_img)
        SimpleDraweeView itemImg;
        @BindView(R.id.select_img)
        ImageView selectImg;

        public ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }

        @OnClick(R.id.select_img)
        void onSelectedImg(View v) {

        }
    }


}
