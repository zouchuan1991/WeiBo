package com.dwg.weibo.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.dwg.weibo.entity.AlbumFolderInfo;
import com.dwg.weibo.entity.ImageInf;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/7/24.
 */

public abstract class ImageScan {
    private static final int LOADER_ID = 1;
    private Context mContext;
    private LoaderManager mLoaderManager;
    private String[] IMAGE_PROJECTION = new String[]{
            MediaStore.Images.Media.DATA,//图片路径
            MediaStore.Images.Media.DISPLAY_NAME,//图片名加后缀
            MediaStore.Images.Media.TITLE
    };
    private ArrayList<AlbumFolderInfo> albumFolderInfos = new ArrayList<>();

    public ImageScan(Context context, LoaderManager loaderManager) {
        this.mContext = context;
        this.mLoaderManager = loaderManager;
        startImgScan();

    }

    private void startImgScan() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            ToastUtils.showToast(mContext, "暂无外部存储");
            return;
        }
        this.mLoaderManager.initLoader(LOADER_ID, null, loaderCallbacks);
    }

    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(
                    mContext,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    IMAGE_PROJECTION,
                    null,
                    null,
                    MediaStore.Images.Media.DEFAULT_SORT_ORDER
            );
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (data.getCount() == 0) {
                ToastUtils.showToast(mContext, "没有图片");
            } else {
//                ToastUtils.showToast(mContext, "" + data.getCount());
                HashMap<String, ArrayList<File>> albumImageListMap = new HashMap<>();//图片目录的hashmap
                ArrayList<File> albumFolderList = new ArrayList<>();
                int dataIndex = data.getColumnIndex(MediaStore.Images.Media.DATA);//_data
                while (data.moveToNext()) {//将所有图片分目录存放在hashMap中
                    File file = new File(data.getString(dataIndex));
                    File parentFile = file.getParentFile();

                    if (!albumFolderList.contains(parentFile)) {//生成目录list
                        albumFolderList.add(parentFile);
                    }
                    String parentPath = parentFile.getAbsolutePath();
                    ArrayList<File> albumImageFileList = albumImageListMap.get(parentPath);
                    if (albumImageFileList == null) {
                        albumImageFileList = new ArrayList<>();
                        albumImageListMap.put(parentPath, albumImageFileList);
                    }
                    albumImageFileList.add(file);
                }

                sortByLastModified(albumFolderList);//对目录进行排序

                Set<String> sets = albumImageListMap.keySet();//对目录中的文件进行排序
                for (String s : sets) {
                    ArrayList<File> files = albumImageListMap.get(s);
                    sortByLastModified(files);
                }

                albumFolderInfos = new ArrayList<>();

                //添加第一个folder->即所有图片
                AlbumFolderInfo AllImageFolderInfo = createAllImageFolderInfo(albumImageListMap, albumFolderList);
                albumFolderInfos.add(AllImageFolderInfo);

                //添加其他folder
                for (int albumFolderPos = 0; albumFolderPos < albumFolderList.size(); albumFolderPos++) {
                    File file = albumFolderList.get(albumFolderPos);
                    String albumPath = file.getAbsolutePath();
                    AlbumFolderInfo albumFolderInfo = new AlbumFolderInfo();

                    //设置目录名称
                    albumFolderInfo.setFolderName(file.getName());

                    //设置首张照片
                    albumFolderInfo.setFrontImage(albumImageListMap.get(albumPath).get(0));

                    //设置目录中的所有图片
                    List<File> imageList = albumImageListMap.get(albumPath);
                    List<ImageInf> imageListInfo = ImageInf.FileToImageInfo(imageList);
                    albumFolderInfo.setImageInfoList(imageListInfo);
                    //添加到相机胶卷中
                    AllImageFolderInfo.getImageInfoList().addAll(imageListInfo);

                    albumFolderInfos.add(albumFolderInfo);
                }
            }
            scanFinish(albumFolderInfos);

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    private AlbumFolderInfo createAllImageFolderInfo(HashMap<String, ArrayList<File>> albumImageList, ArrayList<File> albumFolderList) {
        AlbumFolderInfo albumFolderInfo = new AlbumFolderInfo();
        albumFolderInfo.setFolderName("相册胶卷");
        albumFolderInfo.setFrontImage(albumImageList.get(albumFolderList.get(0).getAbsolutePath()).get(0)); //设置第一张图片
        albumFolderInfo.setImageInfoList(new ArrayList<ImageInf>());
        return albumFolderInfo;
    }

    private void sortByLastModified(List<File> files) {
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File lhs, File rhs) {
                if (lhs.lastModified() > rhs.lastModified()) {
                    return -1;
                } else if (lhs.lastModified() < rhs.lastModified()) {
                    return 1;
                }
                return 0;
            }
        });
    }

    public abstract void scanFinish(ArrayList<AlbumFolderInfo> folderLists);

    public static void showThumb(Context context, Uri uri, SimpleDraweeView draweeView) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(DensityUtil.dip2px(context, 144), DensityUtil.dip2px(context, 144)))
                .build();


        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setAutoPlayAnimations(true)
                .setOldController(draweeView.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        draweeView.setController(controller);
    }
}
