package com.example.discover.bean.LitePalBean;


import org.litepal.crud.DataSupport;

/**
 * Created by monkeyWiiu on 2018/1/28.
 */

public class SearchTag extends DataSupport {

    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
