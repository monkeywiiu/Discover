package com.example.discover.utils;

import android.content.Context;
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
}
