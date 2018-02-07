package com.example.discover.bean.LitePalBean;

import org.litepal.crud.DataSupport;

/**
 * Created by monkeyWiiu on 2017/12/31.
 * id : 不变,与表中数据绑定
 */

public class LikeVideo extends DataSupport {
    private int id;
    private int videoId;
    private String title;
    private String description;
    private String playUrl;
    private String imageUrl;
    private String authorName;
    private int authorId;
    private String authorIcon;
    private String authorDesc;
    private int labelColor;
    private String labelText;
    private int size;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String auhtorName) {
        this.authorName = auhtorName;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int auhtorId) {
        this.authorId = auhtorId;
    }

    public String getAuthorDesc() {
        return authorDesc;
    }

    public void setAuthorDesc(String authorDesc) {
        this.authorDesc = authorDesc;
    }

    public String getAuthorIcon() {
        return authorIcon;
    }

    public void setAuthorIcon(String authorIcon) {
        this.authorIcon = authorIcon;
    }

    public int getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(int labelColor) {
        this.labelColor = labelColor;
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
