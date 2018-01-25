package com.example.discover.bean;

import com.example.discover.bean.DetailBean.ItemList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by monkeyWiiu on 2018/1/25.
 */

public class AuthorDetailBean implements Serializable {

    private List<ItemList> itemList;

    public List<ItemList> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemList> itemList) {
        this.itemList = itemList;
    }
}
