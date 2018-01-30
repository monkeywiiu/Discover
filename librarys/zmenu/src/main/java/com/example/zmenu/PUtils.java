package com.example.zmenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/1 0001.
 */

public class PUtils {

    private List<Integer> imageList;
    private List<Integer> colorList;
    private List<FloatButton> viewList;
    private int marginRight, marginBottom;
    private boolean isVisible;
    private static volatile PUtils instance;

    public static PUtils getInstance() {
        if (instance == null) {
            synchronized (PUtils.class) {
                if (instance == null) {
                    instance = new PUtils();
                }
            }
        }

        return instance;
    }

    public  void setImagesAndColors(List<Integer> imagelist , List<Integer> colorlist) {
        instance.imageList = imagelist;
        instance.colorList = colorlist;
    }

    public void setMargin(int marginRight, int marginBottom) {
        instance.marginRight = marginRight;
        instance.marginBottom = marginBottom;
    }

    public void setVisible(boolean isVisible) {
        getInstance().isVisible = isVisible;
    }

    public boolean getVisible() {
        return getInstance().isVisible;
    }
    public static List<Integer> getImages() {
        return getInstance().imageList;
    }

    public static List<Integer> getColors() {
        return getInstance().colorList;
    }

    public int getMarginRight() {
        return getInstance().marginRight;
    }

    public int getMarginBottom(){
        return getInstance().marginBottom;
    }

    public void setViewList(List<FloatButton> viewList) {
        instance.viewList = viewList;
    }

    public List<FloatButton> getViewList() {
        return getInstance().viewList;
    }
}
