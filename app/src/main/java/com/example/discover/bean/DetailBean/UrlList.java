package com.example.discover.bean.DetailBean;

import java.io.Serializable;

/**
 * Created by monkeyWiiu on 2018/1/23.
 */

public class UrlList implements Serializable {

    /**
     * name : aliyun
     * url : http://baobab.kaiyanapp.com/api/v1/playUrl?vid=66374&editionType=high&source=aliyun
     * size : 14871277
     */

    private String name;
    private String url;
    private int size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
