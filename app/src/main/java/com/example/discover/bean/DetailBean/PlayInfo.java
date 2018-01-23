package com.example.discover.bean.DetailBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by monkeyWiiu on 2018/1/23.
 */

public class PlayInfo implements Serializable {

    /**
     * height : 720
     * width : 1280
     * urlList : [{"name":"aliyun","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=66374&editionType=high&source=aliyun","size":14871277},{"name":"qcloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=66374&editionType=high&source=qcloud","size":14871277},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=66374&editionType=high&source=ucloud","size":14871277}]
     * name : 高清
     * type : high
     * url : http://baobab.kaiyanapp.com/api/v1/playUrl?vid=66374&editionType=high&source=aliyun
     */

    private int height;
    private int width;
    private String name;
    private String type;
    private String url;
    private List<UrlList> urlList;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<UrlList> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<UrlList> urlList) {
        this.urlList = urlList;
    }
}
