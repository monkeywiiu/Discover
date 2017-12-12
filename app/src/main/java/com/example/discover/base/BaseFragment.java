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

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public abstract class BaseFragment<SV extends ViewDataBinding> extends Fragment {

    public SV bindingView;
    public RelativeLayout container;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    public boolean isVisibile = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ll = inflater.inflate(R.layout.fragment_base, null);
        bindingView = DataBindingUtil.inflate(getActivity().getLayoutInflater(), setContentView(), null, false);
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

    public void onVisible() {
        loadData();
    }

    public void onInvisible(){}
    public abstract void loadData();
    public abstract int setContentView();

    public void addSubscription(Subscription subscription) {
        this.compositeSubscription.add(subscription);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeSubscription != null &&compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
        }
    }
}
