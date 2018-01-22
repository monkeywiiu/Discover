package com.example.discover.bean.CategoryDetailBean;

import java.io.Serializable;

/**
 * Created by monkeyWiiu on 2018/1/19.
 */

public class Tag implements Serializable {
    private String name;
    private String headerImage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }
}
