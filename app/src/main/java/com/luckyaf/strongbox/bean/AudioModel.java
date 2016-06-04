package com.luckyaf.strongbox.bean;

/**
 * 类描述：音频模型
 *
 * @author Created by luckyAF on 16/6/3
 */
public class AudioModel {
    private int id;
    private String title;//名称
    private String album;//专辑
    private String artist;//作者
    private String path;//路径
    private String displayName;
    private String mimeType;
    private long duration;
    private long size;

    public AudioModel(int id, String title, String album, String artist,
                      String path, String displayName, String mimeType, long duration,
                      long size) {
        super();
        this.id = id;
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.path = path;
        this.displayName = displayName;
        this.mimeType = mimeType;
        this.duration = duration;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }




}
