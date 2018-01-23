package com.example.discover.bean.DetailBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by monkeyWiiu on 2018/1/17.
 */

public class SectionList implements Serializable {
    public int id;
    public String type;
    public Header header;
    public List<ItemList> itemList;
    public Footer footer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<ItemList> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemList> itemList) {
        this.itemList = itemList;
    }

    public Footer getFooter() {
        return footer;
    }

    public void setFooter(Footer footer) {
        this.footer = footer;
    }
}
