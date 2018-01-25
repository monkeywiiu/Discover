package com.example.discover.bean.LitePalBean;

import org.litepal.crud.DataSupport;

/**
 * Created by monkeyWiiu on 2018/1/25.
 */

public class Follow extends DataSupport {
    private int authorId;
    private String authorName;
    private String iconUrl;
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
