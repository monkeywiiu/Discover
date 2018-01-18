package com.example.discover.bean.CategoryDetailBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by monkeyWiiu on 2018/1/18.
 */

public class ACacheFindList  implements Serializable {
    private List<SectionList> scrollCardSection = new ArrayList<>() ;
    private List<SectionList> videoSection = new ArrayList<>() ;
    private List<SectionList> authorSection = new ArrayList<>() ;

    public List<SectionList> getScrollCardSection() {
        return scrollCardSection;
    }

    public void setScrollCardSection(List<SectionList> scrollCardSection) {
        this.scrollCardSection = scrollCardSection;
    }

    public List<SectionList> getVideoSection() {
        return videoSection;
    }

    public void setVideoSection(List<SectionList> videoSection) {
        this.videoSection = videoSection;
    }

    public List<SectionList> getAuthorSection() {
        return authorSection;
    }

    public void setAuthorSection(List<SectionList> authorSection) {
        this.authorSection = authorSection;
    }
}
