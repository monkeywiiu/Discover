package com.example.discover.app;

import android.app.Application;
import android.content.Context;

import com.example.discover.utils.DebugUtil;
import com.example.http.HttpUtils;

/**
 * Created by Administrator on 2017/12/9 0009.
 */

public class DiscoverApplication extends Application {
    private static DiscoverApplication discoverApplication;

    private static Context context;
    public static DiscoverApplication getDiscoverApplication() {
        return discoverApplication;
    }

    public static Context getContext() {
        return context;
    }
    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        discoverApplication = this;
        HttpUtils.getInstance().init(this, DebugUtil.DEBUG);
    }
}
