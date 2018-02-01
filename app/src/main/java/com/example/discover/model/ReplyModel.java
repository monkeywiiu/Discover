package com.example.discover.model;

import com.example.discover.bean.DetailBean.Replies;
import com.example.discover.bean.DetailBean.ReplyList;
import com.example.discover.http.HttpClient;
import com.example.discover.http.RequestListener;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by monkeyWiiu on 2018/1/30.
 */

public class ReplyModel {

    public static void showReplies(RxAppCompatActivity activity, boolean clean, int lastSequence, int id, final RequestListener listener) {
        Flowable<Replies> repliesFlowable;
        if (clean) {
            repliesFlowable = HttpClient.Builder.getEyeService().fetchReplies(id);
        }else {
            repliesFlowable = HttpClient.Builder.getEyeService().fetchReplies(id, lastSequence);
        }

        repliesFlowable.compose(activity.<Replies>bindToLifecycle())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Replies>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Replies replies) {
                        listener.onSuccess(replies);
                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}
