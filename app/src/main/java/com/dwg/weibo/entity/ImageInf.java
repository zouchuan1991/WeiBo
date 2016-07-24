package com.dwg.weibo.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/24.
 */
public class ImageInf {
    private File ImageFile;
    private Boolean isSecleted = false;

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
}
