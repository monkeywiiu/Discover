package com.example.zmenu;

import android.util.Log;

import java.util.List;

/**
 * Created by Administrator on 2017/12/2 0002.
 */

public class ViewController {

    public int resetPosX,resetPosY;
    public List<FloatButton> imageViewList;
    public ViewController() {

    }

    public static ViewController getInstance() {
        return new ViewController();
    }
    public void init(List<FloatButton> list) {
        Log.d("text1", "11");
        imageViewList = list;
        for (int i = 1; i < list.size(); i++) {
            Log.d("text1", "12");
            FloatButton view1 = list.get(i - 1);
            FloatButton view2 = list.get(i);
            view2.getSpringX().addListener(view1.getFollowerListenerX());
            view2.getSpringY().addListener(view1.getFollowerListenerY());
        }
    }

    /**
     * 设置view最初的原始位置
     */
    public void setOriginPos(int xPos, int yPos) {
        resetPosX = xPos;
        resetPosY = yPos;

        int len = imageViewList.size();
        for (int i = 0; i < len; i++) {
            imageViewList.get(i).setCurrentSpringPos(xPos, yPos);
        }
    }

    public void onRelease(FloatButton topView) {
        topView.onRelease(resetPosX, resetPosY);
    }

}
