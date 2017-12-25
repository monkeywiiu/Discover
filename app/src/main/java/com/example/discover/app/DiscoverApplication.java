package com.example.discover.app;

import android.app.Application;

import com.example.discover.utils.DebugUtil;
import com.example.http.HttpUtils;

/**
 * Created by Administrator on 2017/12/9 0009.
 */

public class DiscoverApplication extends Application {
    private static DiscoverApplication discoverApplication;

    public static DiscoverApplication getDiscoverApplication() {
        return discoverApplication;
    }

    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
        discoverApplication = this;
        HttpUtils.getInstance().init(this, DebugUtil.DEBUG);
    }
}
