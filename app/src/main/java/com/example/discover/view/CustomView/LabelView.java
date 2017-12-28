package com.example.discover.view.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.discover.R;
import com.example.discover.utils.DebugUtil;

/**
 * Created by monkeyWiiu on 2017/12/28.
 */

public class LabelView extends View {
    /**
     * 文本
     */
    private String mLabelText;
    /**
     * 文本的颜色
     */
    private int mLabelTextColor;
    /**
     * 文本的大小
     */
    private int mLabelTextSize;
    /**
     * 背景颜色
     */
    private int mBackground;
    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private Paint mPaint;
    private RectF mRectF;

    public LabelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelView(Context context) {
        this(context, null);
    }

    /**
     * 获得我自定义的样式属性
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public LabelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //默认为黑，否则不设置颜色就为透明不可见
        mLabelTextColor = Color.BLACK;
        mBackground = Color.WHITE;
        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LabelView, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.LabelView_labelText:
                    mLabelText = a.getString(attr);
                    break;
                case R.styleable.LabelView_labelTextColor:
                    mLabelTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.LabelView_labelTextSize:
                    //默认设置为16sp, TypeValue也可以把sp转化为px
                    mLabelTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.LabelView_labelBackground:
                    mBackground = a.getColor(attr, Color.YELLOW);
                    break;
            }

        }
        a.recycle();

        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        mPaint.setTextSize(mLabelTextSize);
        // mPaint.setColor(mTitleTextColor);
        mBound = new Rect();
        mPaint.getTextBounds(mLabelText, 0, mLabelText.length(), mBound);
        mRectF = new RectF();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mPaint.setTextSize(mLabelTextSize);
            mPaint.getTextBounds(mLabelText, 0, mLabelText.length(), mBound);
            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            mPaint.setTextSize(mLabelTextSize);
            mPaint.getTextBounds(mLabelText, 0, mLabelText.length(), mBound);
            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        DebugUtil.debug("backcolor", mBackground + "");
        mPaint.setColor(mBackground);
        canvas.drawRect(getMeasuredHeight() / 2, 0, getMeasuredWidth() - getMeasuredHeight() / 2, getMeasuredHeight(), mPaint);
        //左半圆
        mRectF.left = 0;
        mRectF.top = 0;
        mRectF.right = getMeasuredHeight();
        mRectF.bottom = getMeasuredHeight();
        canvas.drawArc(mRectF, -90, -180, false, mPaint);
        //右半圆
        mRectF.left = getMeasuredWidth() - getMeasuredHeight();
        mRectF.top = 0;
        mRectF.right = getMeasuredWidth();
        mRectF.bottom = getMeasuredHeight();
        canvas.drawArc(mRectF, -90, 180, false, mPaint);
        mPaint.setColor(mLabelTextColor);
        canvas.drawText(mLabelText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }

    public void setText(String text) {

        mLabelText = text;
    }

    public void setBackground(int color) {

        mBackground = color;
    }
}
