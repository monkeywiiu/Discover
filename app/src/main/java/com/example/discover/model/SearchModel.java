package com.example.discover.model;

import android.content.Context;

import com.example.discover.SearchActivity;
import com.example.discover.app.Constant;
import com.example.discover.bean.DetailBean.FindCategory;
import com.example.discover.bean.DetailBean.SectionList;
import com.example.discover.http.HttpClient;
import com.example.discover.http.RequestListener;
import com.example.discover.ui.Search.SearchFragment;
import com.example.discover.utils.DebugUtil;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by monkeyWiiu on 2018/1/16.
 */

public class SearchModel {


    public static void showDetail(SearchFragment context, List<Integer> idList, final RequestListener listener) {
        List<Flowable<FindCategory>> sources = new ArrayList<>();

        for (int id : idList) {
            sources.add(HttpClient.Builder.getEyeService().getEyeCateGory(id));
        }


        Flowable.mergeDelayError(sources)
                .compose(context.<FindCategory>bindToLifecycle())
                .filter(new Predicate<FindCategory>() {
                    @Override
                    public boolean test(FindCategory findCategory) throws Exception {
                        return findCategory != null;
                    }
                })
                .filter(new Predicate<FindCategory>() {
                    @Override
                    public boolean test(FindCategory findCategory) throws Exception {
                        return findCategory.sectionList != null;
                    }
                })
                .flatMap(new Function<FindCategory, Publisher<SectionList>>() {
                    @Override
                    public Publisher<SectionList> apply(FindCategory findCategory) throws Exception {
                        return Flowable.fromIterable(findCategory.sectionList);
                    }
                })
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SectionList>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(SectionList sectionList) {
                        DebugUtil.debug("searchmodel", "chenggon");
                        listener.onSuccess(sectionList);
                    }

                    @Override
                    public void onError(Throwable t) {

                        DebugUtil.debug("searchmodel", "failed");
                        listener.onFailed(t);
                    }

                    @Override
                    public void onComplete() {

                        listener.onCompleted();

                    }
                });
    }

    public static void showTrendingTag(SearchActivity context, final RequestListener listener) {
        HttpClient.Builder.getEyeService().getTrendingTag()
                .compose(context.<List<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(List<String> list) {
                        listener.onSuccess(list);
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

