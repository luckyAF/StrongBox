package com.luckyaf.strongbox.bean;

/**
 * 类描述：文件模型
 *
 * @author Created by luckyAF on 16/6/3
 */
public class FileModel {
    public static int FILE_DIR = 0;
    public static int FILE_FILE = 1;
    private String name;
    private String path;
    private int fileType;

    public FileModel() {
        super();
    }

    public FileModel(String name, String path, int fileType) {
        super();
        this.name = name;
        this.path = path;
        this.fileType = fileType;
    }

    public static int getFileDir() {
        return FILE_DIR;
    }

    public static void setFileDir(int fileDir) {
        FILE_DIR = fileDir;
    }

    public static int getFileFile() {
        return FILE_FILE;
    }

    public static void setFileFile(int fileFile) {
        FILE_FILE = fileFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }
}
