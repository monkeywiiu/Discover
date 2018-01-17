package com.example.discover.bean.CategoryDetailBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by monkeyWiiu on 2018/1/17.
 */

public class Data implements Serializable {
    public String dataType;
    public int id;
    public String title;
    public String text;
    public String description;
    public String image;
    public String actionUrl;
    public boolean shade;
    public Cover cover;
    public String playUrl;
    public String category;
    public long duration;
    public Header header;
    public List<ItemList> itemList;
    public Author author;
    public String icon;

    public int getId() {
        return id;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }

    public List<ItemList> getItemList() {
        return itemList;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public Author getAuthor() {
        return author;
    }

    public Cover getCover() {
        return cover;
    }

    public Header getHeader() {
        return header;
    }

    public long getDuration() {
        return duration;
    }

    public String getCategory() {
        return category;
    }

    public String getDataType() {
        return dataType;
    }

    public String getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setItemList(List<ItemList> itemList) {
        this.itemList = itemList;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setShade(boolean shade) {
        this.shade = shade;
    }

    public void setText(String text) {
        this.text = text;
    }

}
