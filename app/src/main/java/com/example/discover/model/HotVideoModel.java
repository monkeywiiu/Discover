package com.example.discover.model;

import com.example.discover.bean.HotEyeBean;
import com.example.discover.bean.LitePalBean.LikeVideo;
import com.example.discover.http.HttpClient;
import com.example.discover.http.RequestListener;
import com.example.discover.ui.Video.VideoFragment;
import com.example.discover.utils.DebugUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/12/12 0012.
 */

public class HotVideoModel {

    public static void showVideo(VideoFragment context, int start, int num, final RequestListener listener) {

        DebugUtil.debug("test12345", "successed111");
            HttpClient.Builder.getEyeService().getEyeHot(start, num)
                    .compose(context.<HotEyeBean>bindToLifecycle())
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<HotEyeBean>() {
                        @Override
                        public void onSubscribe(Subscription s) {

                            s.request(Long.MAX_VALUE);
                        }

                        @Override
                        public void onNext(HotEyeBean hotEyeBean) {

                            listener.onSuccess(hotEyeBean);
                        }

                        @Override
                        public void onError(Throwable t) {

                            listener.onFailed();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
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
