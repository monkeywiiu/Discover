package com.example.discover.bean.CategoryDetailBean;

import java.io.Serializable;

/**
 * Created by monkeyWiiu on 2018/1/17.
 */

public class Cover implements Serializable {
    public String feed;
    public String detail;
    public String blurred;

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getBlurred() {
        return blurred;
    }

    public void setBlurred(String blurred) {
        this.blurred = blurred;
    }
}
