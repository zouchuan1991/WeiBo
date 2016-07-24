package com.dwg.weibo.entity;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2016/7/24.
 */

public class AlbumFolderInfo {
    /**
     * 目录名
     */
    private String folderName;
    /**
     * 包含所有图片信息
     */
    private List<ImageInf> imageInfoList;
    /**
     * 第一张图片
     */
    private File frontImage;

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public List<ImageInf> getImageInfoList() {
        return imageInfoList;
    }

    public void setImageInfoList(List<ImageInf> imageInfoList) {
        this.imageInfoList = imageInfoList;
    }

    public File getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(File frontImage) {
        this.frontImage = frontImage;
    }
}
