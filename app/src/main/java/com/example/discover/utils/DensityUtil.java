package com.example.discover.utils;

import android.content.Context;
import android.util.TypedValue;
import android.view.WindowManager;

import com.example.discover.app.DiscoverApplication;

/**
 * Created by monkeyWiiu on 2017/12/20.
 */

public class DensityUtil {

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = DiscoverApplication.getDiscoverApplication().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = DiscoverApplication.getDiscoverApplication().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取actionBarSize  高度
     */
    public static int getActionBarSize() {
        TypedValue tv = new TypedValue();
        if (DiscoverApplication.getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, DiscoverApplication.getContext().getResources().getDisplayMetrics());

            return actionBarHeight;
        } else {
            return 0;
        }
    }
}
