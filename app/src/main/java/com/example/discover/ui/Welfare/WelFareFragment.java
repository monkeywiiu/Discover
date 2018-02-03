package com.example.discover.ui.Welfare;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.discover.R;
import com.example.discover.adapter.WelfareAdapter;
import com.example.discover.app.Constant;
import com.example.discover.base.BaseFragment;
import com.example.discover.base.baseadapter.OnItemClickListener;
import com.example.discover.bean.GankBean;
import com.example.discover.bean.HotEyeBean;
import com.example.discover.databinding.FragmentWelfareBinding;
import com.example.discover.http.RequestListener;
import com.example.discover.http.cahe.ACache;
import com.example.discover.model.GankModel;
import com.example.discover.utils.DebugUtil;
import com.example.discover.utils.IntentManager;
import com.example.zmenu.PUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monkeyWiiu on 2018/2/3.
 */

public class WelFareFragment extends BaseFragment<FragmentWelfareBinding> {

    private boolean isFirst = true;
    private boolean isPrepare = false;
    private int num = 20;
    private int page = 1;
    private ACache mCache;
    private GankBean mgankBean;
    private WelfareAdapter welfareAdapter;
    private StaggeredGridLayoutManager layoutManager;
    private ArrayList<String> imgList = new ArrayList<>();
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCache = ACache.get(getContext());
        welfareAdapter = new WelfareAdapter(getContext());
        initRecyclerView();
        isPrepare = true;
    }

    @Override
    public void loadData() {
        if (!isPrepare || !isVisibile || !isFirst) {
            return;
        }

        //先从缓存读取数据，如果没有在请求
        mgankBean = (GankBean) mCache.getAsObject(Constant.GANK_WELFARE);
        if (mgankBean != null ) {
            showContentView();
            for (int i = 0; i < mgankBean.getResults().size(); i++) {
                imgList.add(mgankBean.getResults().get(i).getUrl());
            }
            setWelfareAdapter(mgankBean);
            page ++;
            DebugUtil.debug("test12", mgankBean.getResults().get(0).getType());
        }else {
            showWelfare();
        }

        isFirst = false;
    }

    public void showWelfare() {
        GankModel.loadGank("福利", num, page, new RequestListener() {
            @Override
            public void onSuccess(Object object) {

                showContentView();
                GankBean gankBean = (GankBean) object;
                if (page == 1) {

                    if (gankBean != null && gankBean.getResults() != null && gankBean.getResults().size() > 0) {

                        imgList.clear();
                        for (int i = 0; i < gankBean.getResults().size(); i++) {
                            imgList.add(gankBean.getResults().get(i).getUrl());
                        }
                        setWelfareAdapter(gankBean);
                        mCache.remove(Constant.GANK_WELFARE);
                        mCache.put(Constant.GANK_WELFARE, gankBean, 18000);
                    }

                } else {
                    if (gankBean.getResults() != null && gankBean.getResults().size() > 0) {

                        welfareAdapter.updateStateLoad(false);
                        welfareAdapter.addAll(gankBean.getResults());
                        welfareAdapter.notifyDataSetChanged();
                        for (int i = 0; i < gankBean.getResults().size(); i++) {
                            imgList.add(gankBean.getResults().get(i).getUrl());
                        }
                    } else {
                        //数据刷新到底了
                        welfareAdapter.hideLoading();
                    }
                }
                DebugUtil.debug("welfaress", gankBean.getResults().size() + "");

                page++;
            }

            @Override
            public void onFailed(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        });
    }

    public void initRecyclerView() {
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        bindingView.rvWelfare.setLayoutManager(layoutManager);

        bindingView.rvWelfare.setOnScrollListener(new RecyclerView.OnScrollListener() {

            //recyclerview滑动时ZMemu自动隐藏
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == XRecyclerView.SCROLL_STATE_IDLE  && PUtils.getInstance().getViewList() != null) {

                    for (int i = 0; i < PUtils.getInstance().getViewList().size(); i++) {
                        PUtils.getInstance().getViewList().get(i).show();
                    }
                } else {
                    for (int i = 0; i < PUtils.getInstance().getViewList().size(); i++) {
                        PUtils.getInstance().getViewList().get(i).hide();
                    }
                }


                int column = layoutManager.getColumnCountForAccessibility(null,null);
                int[] positions = new int[column];
                layoutManager.findLastCompletelyVisibleItemPositions(positions);

                if (!welfareAdapter.isLoading()) {

                    for (int i =0; i < positions.length; i++){
                        if (positions[i] >= layoutManager.getItemCount() - column && layoutManager
                                .findViewByPosition(positions[i]).getBottom() <=  recyclerView.getHeight()){

                            welfareAdapter.updateStateLoad(true);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    DebugUtil.debug("startpage", page + "");
                                    showWelfare();
                                }
                            }, 1000);

                            break;
                        }
                    }
                }

            }
        });
    }

    public void setWelfareAdapter(GankBean result) {
        welfareAdapter.addAll(result.getResults());
        bindingView.rvWelfare.setAdapter(welfareAdapter);
        welfareAdapter.notifyDataSetChanged();
        welfareAdapter.setOnItemClickListener(new OnItemClickListener<GankBean.ResultBean>() {
            @Override
            public void onClick(View view, GankBean.ResultBean resultBean, int position) {
                DebugUtil.debug("wellll", "click");
                IntentManager.toBigImageActivity(getActivity(), view, position, imgList);
            }
        });
    }
    @Override
    public int setContentView() {
        return R.layout.fragment_welfare;
    }


}
