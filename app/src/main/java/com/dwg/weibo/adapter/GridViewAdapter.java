package com.dwg.weibo.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dwg.weibo.R;
import com.dwg.weibo.entity.ImageInf;
import com.dwg.weibo.utils.DensityUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/24.
 */

public class GridViewAdapter extends BaseAdapter {
    private List<ImageInf> mDatas;
    private Context mContext;

    public GridViewAdapter(Context context, List<ImageInf> datas) {
        this.mContext = context;
        this.mDatas = datas;
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
        showThumb(Uri.parse("file:///" + mDatas.get(position).getImageFile().getAbsolutePath()), viewHolder.itemImg);

        return convertView;

    }

    public class ViewHolder {

        @BindView(R.id.item_img)
        SimpleDraweeView itemImg;
        @BindView(R.id.select_img)
        SimpleDraweeView selectImg;

        public ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }

    }

    public void showThumb(Uri uri, SimpleDraweeView draweeView) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(DensityUtil.dip2px(mContext, 144), DensityUtil.dip2px(mContext, 144)))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        draweeView.setController(controller);
    }
}
