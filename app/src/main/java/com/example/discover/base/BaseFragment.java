package com.example.discover.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.discover.R;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;


/**
 * Created by Administrator on 2017/12/5 0005.
 */

public abstract class BaseFragment<SV extends ViewDataBinding> extends RxFragment {

    public SV bindingView;
    public RelativeLayout container;
    public AVLoadingIndicatorView avLoading;
    private RelativeLayout rlLoading;
    private RelativeLayout rlError;
    public boolean isVisibile = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ll = inflater.inflate(R.layout.fragment_base, null);
        bindingView = DataBindingUtil.inflate(getActivity().getLayoutInflater(), setContentView(), null, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        bindingView.getRoot().setLayoutParams(params);
        container = ll.findViewById(R.id.container);
        container.addView(bindingView.getRoot());
        return ll;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisibile = true;
            onVisible();
        } else {
            isVisibile = false;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        avLoading = (AVLoadingIndicatorView) getView(R.id.av_loading);
        rlLoading = (RelativeLayout) getView(R.id.rl_loading);
        rlError = (RelativeLayout) getView(R.id.rl_error);
        showLoading();
        RxView.clicks(rlError)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Refresh();
                    }
                });

    }

    public View getView(int id) {
        return getView().findViewById(id);
    }

    public void onVisible() {
        loadData();
    }
    public void onInvisible(){}
    protected void loadData(){};
    public abstract int setContentView();



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 加载中的状态
     */
    protected void showLoading() {
        if (rlLoading.getVisibility() == View.GONE) {
            rlLoading.setVisibility(View.VISIBLE);
        }
        if (rlError.getVisibility() == View.VISIBLE) {
            rlError.setVisibility(View.GONE);
        }
    }
    /**
     * 加载完成的状态
     */
    protected void showContentView() {
        if (rlError.getVisibility() == View.VISIBLE) {
            rlLoading.setVisibility(View.GONE);
        }
        if (rlLoading.getVisibility() == View.VISIBLE) {
            rlLoading.setVisibility(View.GONE);
        }
    }
    /**
     * 加载失败
     */
    protected void Error() {
        if (rlError.getVisibility() == View.GONE) {
            rlError.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 加载失败重新加载
     */

    protected void Refresh() {

    }
/*
    //显示loading
    public void showLoading() {
        avLoading.show();
    }

    //隐藏loading
    public void stopLoading() {
        avLoading.hide();
    }
    */


}
