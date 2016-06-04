package com.luckyaf.strongbox.util.image;

/**
 * 类描述：图片文件夹 包含图片数量和第一张图片
 *
 * @author Created by luckyAF on 16/4/23
 */
public class ImageFolder {
    private ImageInformation imageInformation;
    private int mCount = 0;
    private String mName = "";

    public ImageFolder(String name, ImageInformation imageInformation, int count) {
        mName = name;
        this.imageInformation = imageInformation;
        mCount = count;
    }

    public String getPath() {
        return imageInformation.path;
    }

    public int getCount() {
        return mCount;
    }

    public String getmName() {
        return mName;
    }

    @Override
    public String toString() {
        return "ImageInfoExtra{" +
                "mImageInfo=" + imageInformation +
                ", mCount=" + mCount +
                ", mName='" + mName + '\'' +
                '}';
    }
}
