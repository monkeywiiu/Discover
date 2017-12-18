package com.example.zmenu;

import java.util.List;

/**
 * Created by Administrator on 2017/12/1 0001.
 */

public class PUtils {

    public List<Integer> imageList;
    public List<Integer> colorList;
    public List<FloatButton> viewList;
    public int marginRight, marginBottom;
    public boolean isVisible;
    private static PUtils instance = new PUtils();

    public static PUtils getInstance() {
        if (instance != null) {
            synchronized (PUtils.class) {
                if (instance != null) {
                    return instance;
                }
            }
        }

        return null;
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
