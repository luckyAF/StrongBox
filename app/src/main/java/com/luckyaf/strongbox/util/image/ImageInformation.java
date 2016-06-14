package com.luckyaf.strongbox.util.image;

import java.io.Serializable;

/**
 * 类描述：记录图片详情，activity交流
 *
 * @author Created by luckyAF on 16/4/23
 */
public class ImageInformation implements Serializable {
    public String path;//路径
    public String pathName;
    public String key; //加密key
    public long photoId;//图片id
    public int width;//宽度
    public int height;//高度
    private static final String prefix = "file://";

    public ImageInformation(String path) {
        this.pathName = path;
        this.path = pathAddPreFix(path);
    }

    public static String pathAddPreFix(String path) {
        if (!path.startsWith(prefix)) {
            path = prefix + path;
        }
        return path;
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "path='" + path + '\'' +
                ", photoId=" + photoId +
                ", width=" + width +
                ", height=" + height +
                ",key=" + key +
                '}';
    }
}
