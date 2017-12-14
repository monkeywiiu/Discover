package com.example.discover.http;

import rx.Subscription;

/**
 * Created by Administrator on 2017/12/12 0012.
 */

public interface RequestListener {
    void onSuccess(Object object);

    void onFailed();

    void addSubscription(Subscription subscription);
}
