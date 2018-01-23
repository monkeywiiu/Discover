package com.example.discover.bean.DetailBean;

import java.io.Serializable;

/**
 * Created by monkeyWiiu on 2018/1/23.
 */

public class WebUrl implements Serializable {

    private String raw;
    private String forWeibo;

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getForWeibo() {
        return forWeibo;
    }

    public void setForWeibo(String forWeibo) {
        this.forWeibo = forWeibo;
    }
}
