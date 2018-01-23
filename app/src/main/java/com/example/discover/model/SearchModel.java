package com.example.discover.model;

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
                        listener.onFailed();
                    }

                    @Override
                    public void onComplete() {

                        listener.onCompleted();

                    }
                });
        /*rx.Observable.mergeDelayError(sources)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FindCategory>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed();
                        DebugUtil.debug("test221", "failed");
                    }

                    @Override
                    public void onNext(FindCategory cateGoryEyeBean) {

                        listener.onSuccess(cateGoryEyeBean);
                        DebugUtil.debug("test221", "successed");
                    }
                });*/
        //合并多个接口的请求

    }
}

