package com.example.zmenu;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;

/**
 * Created by xmuSistone.
 */
public class FloatButton extends FloatingActionButton {
    private Spring springX, springY;
    private SimpleSpringListener followerListenerX, followerListenerY; // 此为跟踪的回调，当前面一个view移动的时候，此为后面的view，需要更新endValue

    public FloatButton(Context context) {
        this(context, null);
    }

    public FloatButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        SpringSystem mSpringSystem = SpringSystem.create();
        springX = mSpringSystem.createSpring();
        springY = mSpringSystem.createSpring();

        springX.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                int xPos = (int) spring.getCurrentValue();
                setScreenX(xPos);
            }
        });

        springY.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                int yPos = (int) spring.getCurrentValue();
                setScreenY(yPos);
            }
        });

        followerListenerX = new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                int xPos = (int) spring.getCurrentValue();
                springX.setEndValue(xPos);
            }
        };

        followerListenerY = new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                int yPos = (int) spring.getCurrentValue();
                springY.setEndValue(yPos);
            }
        };
    }

    private void setScreenX(int screenX) {

        this.offsetLeftAndRight(screenX - getLeft());
    }

    private void setScreenY(int screenY) {
        this.offsetTopAndBottom(screenY - getTop());
    }

    /**
     * 顶部ImageView强行停止动画
     */
    public void stopAnimation() {
        springX.setAtRest();
        springY.setAtRest();
    }

    /**
     * 只为最顶部的view调用，触点松开后，回归原点
     * @param xPos
     * @param yPos
     */
    public void onRelease(int xPos, int yPos) {
        setCurrentSpringPos(getLeft(), getTop());
        setEndValue(xPos, yPos);
    }

    /**
     * 设置当前spring位置
     */
    public void setCurrentSpringPos(int xPos, int yPos) {
        springX.setCurrentValue(xPos);
        springY.setCurrentValue(yPos);
    }

    public Spring getSpringX() {
        return springX;
    }

    public Spring getSpringY() {
        return springY;
    }

    public SimpleSpringListener getFollowerListenerX() {
        return followerListenerX;
    }

    public SimpleSpringListener getFollowerListenerY() {
        return followerListenerY;
    }

    public void setEndValue(int x, int y) {
        springX.setEndValue(x);
        springY.setEndValue(y);
    }

    //设置阴影为0

    @Override
    public void setElevation(float elevation) {
        super.setElevation(0);
    }
}
