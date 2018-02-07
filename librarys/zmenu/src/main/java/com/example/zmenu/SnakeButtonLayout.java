package com.example.zmenu;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;


public class SnakeButtonLayout extends RelativeLayout {
    private List<Integer> imageList;
    private List<Integer> colorList;
    private List<FloatButton> viewList = new ArrayList<>();
    public ViewDragHelper mDragHelper;
    public ViewController controller;
    public FloatButton topView;
    public FloatButton topFollowView;
    public int marginRight = 0, marginBottom = 0;
    public onTopViewClickListener onTopViewClickListener;
    public long upTime = 0, downTime = 0;
    public boolean isClickable = false;
    public boolean isVisible = false;
    public interface onTopViewClickListener {
         void onclick();
    }
    public SnakeButtonLayout(Context context) {
        super(context, null);
    }

    public SnakeButtonLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        marginRight = PUtils.getInstance().getMarginRight();
        marginBottom = PUtils.getInstance().getMarginBottom();
        isVisible = PUtils.getInstance().getVisible();
        mDragHelper = ViewDragHelper.create(this, 10f, new MyViewDragCallBack());
        controller = ViewController.getInstance();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initImagesAndColors();
        int len = imageList.size();
        for (int i = 0; i < len; i++) {
            FloatButton floatButton = new FloatButton(getContext());
            floatButton.setImageResource(imageList.get(i));
            floatButton.setBackgroundTintList(getResources().getColorStateList(colorList.get(i)));

            if (!isVisible) {
                floatButton.hide();
            }
            //添加到主布局中去
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, marginRight, marginBottom);
            lp.addRule(ALIGN_PARENT_BOTTOM);
            lp.addRule(ALIGN_PARENT_RIGHT);
            viewList.add(floatButton);
            Log.d("rules", "restore");
            addView(floatButton, lp);

            //获取顶部的view
            if (i == len - 1) {
                topView = floatButton;
            }
            if (i == len - 2) {
                topFollowView = floatButton;
            }
        }
        PUtils.getInstance().setViewList(viewList);
        controller.init(viewList);
    }

    public void initImagesAndColors() {
        this.imageList = new ArrayList<>();
        this.colorList = new ArrayList<>();
        if (PUtils.getImages() != null) {
            this.imageList = PUtils.getImages();
        } else {
            this.imageList.add(R.drawable.image1);
            this.imageList.add(R.drawable.image2);
        }
        if (PUtils.getColors() != null) {
            this.colorList = PUtils.getColors();
        } else {
            this.colorList.add(R.color.background1);
            this.colorList.add(R.color.background2);
        }

    }

    public class MyViewDragCallBack extends ViewDragHelper.Callback {

        //捕捉topview
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            downTime = System.currentTimeMillis();
            Log.d("catchview", "1");
            if (child == topView) {
                topView.stopAnimation();
                return true;
            }
            return false;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {

            topFollowView.setEndValue(left, top);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            upTime = System.currentTimeMillis();
            //拖拽 < 250ms 触发点击事件
            if ((upTime - downTime) < 250L && isClickable == true) {
                onTopViewClickListener.onclick();
            }

            controller.onRelease(topView);
        }

        @Override
        public int getViewHorizontalDragRange(View child)
        {
            return getMeasuredWidth()-child.getMeasuredWidth();
        }

        @Override
        public int getViewVerticalDragRange(View child)
        {
            return getMeasuredHeight()-child.getMeasuredHeight();
        }
    }

    //设置是否可点击
    public void setClickable(boolean isClickable) {
        this.isClickable = isClickable;
    }

    /*实时刷新
    保持平滑状态
     */
    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        try {
            mDragHelper.processTouchEvent(event);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if ( event.getX() > topView.getLeft() && event.getX() < topView.getRight()
                && event.getY() > topView.getTop() && event.getY() < topView.getBottom() ) {
            return true;
        }
        return false;
    }


    public void setOnTopViewClickListener(onTopViewClickListener listener) {
        this.onTopViewClickListener = listener;
    }

    /*@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mDragHelper.abort();
        }
        return super.dispatchTouchEvent(ev);
    }*/

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        controller.setOriginPos(topView.getLeft(), topView.getTop());
    }
}
