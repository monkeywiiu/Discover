package com.example.discover.model;

import com.example.discover.bean.AuthorDetailBean;
import com.example.discover.bean.DetailBean.ItemList;
import com.example.discover.http.HttpClient;
import com.example.discover.http.RequestListener;
import com.example.discover.ui.Search.Author.ItemFragment;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by monkeyWiiu on 2018/1/25.
 */

public class AuthorVideoModel {

    public static void showVideo(ItemFragment context, int start, int id, String strategy, final RequestListener listener) {
        HttpClient.Builder.getEyeService().getAuthorRelated(start, id, strategy)
                .compose(context.<AuthorDetailBean>bindToLifecycle())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AuthorDetailBean>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(AuthorDetailBean itemLists) {

                        listener.onSuccess(itemLists);
                    }

                    @Override
                    public void onError(Throwable t) {

                        t.printStackTrace();
                        listener.onFailed(t);
                    }

                    @Override
                    public void onComplete() {
                        listener.onCompleted();
                    }
                });

    }
}
