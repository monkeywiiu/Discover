package com.example.discover.bean.DetailBean;

import java.io.Serializable;

/**
 * Created by monkeyWiiu on 2018/1/17.
 */

public class Footer implements Serializable {

    public String type;
    public Data data;

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
}
