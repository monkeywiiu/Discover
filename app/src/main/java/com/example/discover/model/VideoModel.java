package com.example.discover.model;

import com.example.discover.bean.EyeBean;
import com.example.discover.http.HttpClient;
import com.example.discover.http.RequestListener;
import com.example.discover.utils.DebugUtil;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/12/12 0012.
 */

public class VideoModel {

    public static void showVideo(int start, int num, final RequestListener listener) {
        Subscription subscription = HttpClient.Builder.getEyeService().getEyeDetail(start, num)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EyeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed();
                        DebugUtil.debug("test1", "failed");
                    }

                    @Override
                    public void onNext(EyeBean eyeBean) {
                        listener.onSuccess(eyeBean);
                        //DebugUtil.debug("test1", "successed");
                    }
                });

        listener.addSubscription(subscription);
    }
}
