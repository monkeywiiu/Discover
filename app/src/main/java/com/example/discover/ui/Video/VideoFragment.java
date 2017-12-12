package com.example.discover.ui.Video;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.discover.R;
import com.example.discover.base.BaseFragment;
import com.example.discover.bean.EyeBean;
import com.example.discover.databinding.FragmentVideoBinding;
import com.example.discover.http.HttpClient;
import com.example.discover.utils.DebugUtil;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/12/9 0009.
 */

public class VideoFragment extends BaseFragment<FragmentVideoBinding> {

    public int start = 0;
    public int num = 20;
    public boolean isPrepare = false;
    public String title;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepare = true;

        loadData();
    }

    @Override
    public void loadData() {
        if (!isPrepare || !isVisibile) {
            return;
        }

        loadVideo();
    }

    public void loadVideo() {
        Subscription subscription = HttpClient.Builder.getEyeService().getEyeDetail(start, num)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EyeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        DebugUtil.debug("Title", "error1");
                    }

                    @Override
                    public void onNext(EyeBean eyeBean) {
                        if (eyeBean != null) {
                            title = eyeBean.getItemList().get(20).getData().getDescription();
                            DebugUtil.debug("Title", title + "");
                        }
                    }
                });
        addSubscription(subscription);
    }
    @Override
    public int setContentView() {
        return R.layout.fragment_video;
    }
}
