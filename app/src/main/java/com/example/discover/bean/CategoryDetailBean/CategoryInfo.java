package com.example.discover.bean.CategoryDetailBean;

import java.io.Serializable;

/**
 * Created by monkeyWiiu on 2018/1/17.
 */

public class CategoryInfo implements Serializable {

    public String dataType;
    public int id;
    public String name;
    public String description;
    public String headerImage;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }
}
