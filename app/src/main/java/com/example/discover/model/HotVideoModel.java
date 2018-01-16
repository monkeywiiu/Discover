package com.example.discover.model;

import com.example.discover.bean.HotEyeBean;
import com.example.discover.bean.LitePalBean.LikeVideo;
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

public class HotVideoModel {

    public static void showVideo(int start, int num, final RequestListener listener) {
        Subscription subscription = HttpClient.Builder.getEyeService().getEyeHot(start, num)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotEyeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed();
                        DebugUtil.debug("test1", "failed");
                    }

                    @Override
                    public void onNext(HotEyeBean eyeBean) {
                        listener.onSuccess(eyeBean);
                        //DebugUtil.debug("test1", "successed");
                    }
                });

        listener.addSubscription(subscription);
    }

    public static void addToFavor(int id, String title, String desc, String playUrl,
                                  String imageUrl, int labelColor, String labelText, int size) {
        LikeVideo likeVideo = new LikeVideo();
        likeVideo.setId(1);
        likeVideo.setVideoId(id);
        likeVideo.setTitle(title);
        likeVideo.setDescription(desc);
        likeVideo.setPlayUrl(playUrl);
        likeVideo.setImageUrl(imageUrl);
        likeVideo.setLabelColor(labelColor);
        likeVideo.setLabelText(labelText);
        likeVideo.setSize(size);
        likeVideo.save();
    }

    public static void deleteFromFavor() {

    }
}
