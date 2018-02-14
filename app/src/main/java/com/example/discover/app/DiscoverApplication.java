package com.example.discover.app;

import android.app.Application;
import android.content.Context;

import com.example.discover.utils.DebugUtil;
import com.example.http.HttpUtils;
import com.squareup.haha.perflib.Instance;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.litepal.LitePal;

/**
 * Created by Administrator on 2017/12/9 0009.
 */

public class DiscoverApplication extends Application {
    private static DiscoverApplication discoverApplication;

    private RefWatcher refWatcher;
    public static DiscoverApplication getDiscoverApplication() {
        return discoverApplication;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        discoverApplication = this;
        HttpUtils.getInstance().init(this, DebugUtil.DEBUG);
        refWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {

        DiscoverApplication application = (DiscoverApplication) context.getApplicationContext();
        return application.refWatcher;
    }



}
