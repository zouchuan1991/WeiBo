package com.dwg.weibo.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/24.
 */
public class ImageInf implements Parcelable {
    private File ImageFile;
    private Boolean isSecleted = false;

    public ImageInf() {

    }

    protected ImageInf(Parcel in) {
        ImageFile = (File) in.readSerializable();
        isSecleted = in.readInt() != 0;
    }

    public static final Creator<ImageInf> CREATOR = new Creator<ImageInf>() {
        @Override
        public ImageInf createFromParcel(Parcel in) {
            return new ImageInf(in);
        }

        @Override
        public ImageInf[] newArray(int size) {
            return new ImageInf[size];
        }
    };

    public File getImageFile() {
        return ImageFile;
    }

    public void setImageFile(File imageFile) {
        ImageFile = imageFile;
    }

    public Boolean getSecleted() {
        return isSecleted;
    }

    public void setSecleted(Boolean secleted) {
        isSecleted = secleted;
    }

    public static List<ImageInf> FileToImageInfo(List<File> files) {
        if (files != null) {
            List<ImageInf> imageInfos = new ArrayList<>();
            for (File file : files) {
                ImageInf imageInfo = new ImageInf();
                imageInfo.setImageFile(file);
                imageInfo.setSecleted(false);
                imageInfos.add(imageInfo);
            }
            return imageInfos;
        } else {
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.ImageFile);
        dest.writeInt(this.isSecleted ? 1 : 0);
    }
}
