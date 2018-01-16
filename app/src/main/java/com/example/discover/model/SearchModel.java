package com.example.discover.model;

import android.support.annotation.NonNull;

import com.example.discover.bean.CateGoryEyeBean;
import com.example.discover.http.HttpClient;
import com.example.discover.http.RequestListener;
import com.example.discover.utils.DebugUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by monkeyWiiu on 2018/1/16.
 */

public class SearchModel {


    public static void showDetail(List<Integer> idList, final RequestListener listener) {
        List<rx.Observable<CateGoryEyeBean>> sources = new ArrayList<>();

        for (int id : idList) {
            sources.add(HttpClient.Builder.getEyeService().getEyeCateGory(id));
        }

        Subscription subscription = rx.Observable.mergeDelayError(sources)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CateGoryEyeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed();
                        DebugUtil.debug("test221", "failed");
                    }

                    @Override
                    public void onNext(CateGoryEyeBean cateGoryEyeBean) {

                        listener.onSuccess(cateGoryEyeBean);
                        DebugUtil.debug("test221", "successed");
                    }
                });
        //合并多个接口的请求

    }
}
