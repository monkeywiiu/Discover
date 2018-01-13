package com.example.discover.bean.LitePalBean;

import org.litepal.crud.DataSupport;

/**
 * Created by monkeyWiiu on 2018/1/13.
 */

public class LabelType extends DataSupport {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
