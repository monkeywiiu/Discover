package com.example.discover.model;

import com.example.discover.bean.GankBean;
import com.example.discover.http.HttpClient;
import com.example.discover.http.RequestListener;
import com.example.discover.utils.DebugUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by monkeyWiiu on 2018/2/3.
 */

public class GankModel {

    public static void loadGank(String id, int num, int page, final RequestListener listener) {
        HttpClient.Builder.getGankService().getGankData(id, num, page)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GankBean>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(GankBean gankBean) {
                        DebugUtil.debug("welfaress", "successed");
                        listener.onSuccess(gankBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        DebugUtil.debug("welfaress", "failed");
                        listener.onFailed(t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
