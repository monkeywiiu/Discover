package com.example.discover.view.CustomView;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.example.discover.R;
import com.example.discover.adapter.PopReplyAdapter;
import com.example.discover.adapter.ReplyAdapter;
import com.example.discover.app.Constant;
import com.example.discover.bean.DetailBean.ItemList;
import com.example.discover.bean.DetailBean.Replies;
import com.example.discover.bean.DetailBean.ReplyList;
import com.example.discover.http.HttpClient;
import com.example.discover.http.RequestListener;
import com.example.discover.model.ReplyModel;
import com.example.discover.utils.DebugUtil;
import com.example.discover.utils.DensityUtil;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by monkeyWiiu on 2018/2/1.
 */

public class ReplyPopupWindow extends PopupWindow {

    private int id;
    private PopReplyAdapter replyAdapter;
    private LinearLayoutManager linearLayoutManager;
    private FrameLayout noComment;
    private FrameLayout avLoading;
    private FrameLayout reLoading;
    private RecyclerView replyRecyclerView;
    private List<ReplyList> replyLists = new ArrayList<>();
    private boolean firstRequest = true;
    private boolean loading = false;
    private int lastSequence;
    private Subscription mSubscription;

    public ReplyPopupWindow(Context context, int id) {

        this.id = id;
        //this.id = 25923;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.reply_popup_window, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(contentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        //this.setWidth(DensityUtil.dip2px(300));
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(DensityUtil.dip2px((300)));
        // 设置SelectPicPopupWindow弹出窗体可点击
        //this.setFocusable(true);
        this.setOutsideTouchable(true);
        /*if (Build.VERSION.SDK_INT > 20) {
            this.setElevation(10f);
        }*/

        //刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        //ColorDrawable dw = new ColorDrawable();
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        //this.setBackgroundDrawable(dw);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationFadeBT);

        noComment = contentView.findViewById(R.id.no_comment);
        avLoading = contentView.findViewById(R.id.loading);
        reLoading = contentView.findViewById(R.id.reloading);

        initRecyclerView(contentView, context);
        showReplies();


        RxView.clicks(reLoading)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        showReplies();
                        DebugUtil.debug("eeee", "eee");
                    }
                });
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else {
            this.dismiss();
        }
    }

    public void initRecyclerView(View view, Context context) {


        replyRecyclerView = view.findViewById(R.id.rv_reply);
        replyAdapter = new PopReplyAdapter(replyLists);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        replyRecyclerView.setLayoutManager(linearLayoutManager);
        replyRecyclerView.setAdapter(replyAdapter);
        //上拉加载
        replyRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = linearLayoutManager.getItemCount();
                if (lastVisibleItem == totalItemCount - 5 && !loading) {

                    loading = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            showReplies();
                        }
                    }, 1000);

                }
            }
        });
    }
    public void showReplies() {

        Flowable<Replies> repliesFlowable;
        if (firstRequest) {
            repliesFlowable = HttpClient.Builder.getEyeService().fetchReplies(id);
        }else {
            repliesFlowable = HttpClient.Builder.getEyeService().fetchReplies(id, lastSequence);
        }

        repliesFlowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Replies>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mSubscription = s;
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Replies replies) {
                        avLoading.setVisibility(View.GONE);
                        reLoading.setVisibility(View.GONE);
                        DebugUtil.debug("eeee", "next");
                        if (replies.getReplyList().size() > 0) {
                            replyLists.addAll(replies.getReplyList());
                            replyAdapter.notifyDataSetChanged();
                            lastSequence = replies.getReplyList().get(replies.getReplyList().size() - 1).getSequence();
                            firstRequest = false;
                            loading =false;
                        } else {
                            noComment.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                        DebugUtil.debug("eeee",  "error");
                        avLoading.setVisibility(View.GONE);
                        if (firstRequest) {
                            reLoading.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onComplete() {
                        DebugUtil.debug("eeee",  "ecccr");
                        mSubscription.cancel();
                    }
                });


    }
    @Override
    public void dismiss() {
        //repliesFlowable.un
        mSubscription.cancel();
        super.dismiss();
    }
}
