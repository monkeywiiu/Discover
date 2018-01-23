package com.example.discover.bean.DetailBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by monkeyWiiu on 2018/1/17.
 */

public class FindCategory implements Serializable{
    public CategoryInfo categoryInfo;
    public List<SectionList> sectionList;

    public CategoryInfo getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(CategoryInfo categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public List<SectionList> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<SectionList> sectionList) {
        this.sectionList = sectionList;
    }
}
