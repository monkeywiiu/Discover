package com.example.discover.bean.DetailBean;

import java.io.Serializable;

/**
 * Created by monkeyWiiu on 2018/1/17.
 */

public class ItemList implements Serializable{
    private String type;
    private Data data;

    private String tag;
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
