package com.example.discover.bean;

import com.example.discover.bean.DetailBean.ItemList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/9 0009.
 */

public class HotEyeBean implements Serializable {


    private List<ItemList> itemList;

    public List<ItemList> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemList> itemList) {
        this.itemList = itemList;
    }


}
