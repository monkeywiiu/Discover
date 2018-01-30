package com.example.discover.ui.Search;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.discover.R;
import com.example.discover.SearchActivity;
import com.example.discover.adapter.SearchRecyclerAdapter;
import com.example.discover.adapter.SelectTypeRecyclerAdapter;
import com.example.discover.app.Constant;
import com.example.discover.base.BaseFragment;
import com.example.discover.bean.DetailBean.ACacheFindList;
import com.example.discover.bean.DetailBean.ItemList;
import com.example.discover.bean.DetailBean.SectionList;
import com.example.discover.bean.LitePalBean.LabelType;
import com.example.discover.databinding.FragmentSearchBinding;
import com.example.discover.http.RequestListener;
import com.example.discover.http.cahe.ACache;
import com.example.discover.model.SearchModel;
import com.example.discover.ui.RecyclerViewNoBugLinearLayoutManager;
import com.example.discover.utils.DebugUtil;
import com.example.discover.utils.DensityUtil;
import com.example.discover.utils.LitePalUtil;
import com.example.discover.view.CustomView.MyPopupWindow;
import com.example.zmenu.PUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;

/**
 * Created by monkeyWiiu on 2018/1/12.
 */

public class SearchFragment extends BaseFragment<FragmentSearchBinding> implements View.OnClickListener {

    private boolean isPrepare = false;
    private boolean isFirst = true;
    private List<Integer> categoryIdList = new ArrayList<>();
    private ACacheFindList findList;
    private List<Object> combineList = new ArrayList<>();
    private List<ItemList> authorList = new ArrayList<>();
    private ACache mCache;
    private RecyclerView sTRecyclerView;
    private SearchRecyclerAdapter adapter;
    private List<String> selectLabel;
    private List<LabelType> savedLabelList;
    private SelectTypeRecyclerAdapter strAdapter;
    @Override
    public int setContentView() {
        return R.layout.fragment_search;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();

        mCache = ACache.get(getContext());
        selectLabel = LitePalUtil.getSelectLabel();
        init();
        initSelectTypeRecyclerView();
        categoryIdList = getCategoryIdList();
        isPrepare = true;

        initMainRecyclerTest();
    }

    @Override
    protected void loadData() {
        if (!isPrepare || !isFirst || !isVisibile) {
            return;
        }

        findList = (ACacheFindList) mCache.getAsObject(Constant.EYE_FIND);
        if (findList != null) {
            loadSuccess();
            setAdapterTest(getPrecessedData());
        } else {
            findList = new ACacheFindList();
            loadDetail();
        }
        //避免重复加载
        isFirst = false;
    }

    public void initMainRecyclerTest() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bindingView.rvMain.setLayoutManager(linearLayoutManager);
        bindingView.srlSearchRefresh.setColorSchemeResources(R.color.background5, R.color.background2, R.color.background4);
        //SwipeRefreshLayout与CoordinatorLayout嵌套刷新，动态设置SwipeRefreshLayout是否可用
        bindingView.abl.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset >= 0) {
                    bindingView.srlSearchRefresh.setEnabled(true);
                } else {
                    bindingView.srlSearchRefresh.setEnabled(false);
                }
            }
        });
        //上拉刷新
        bindingView.srlSearchRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                JZVideoPlayer.goOnPlayOnPause();
                bindingView.srlSearchRefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadDetail();
                    }
                }, 1000);
            }
        });

        //recyclerview滑动时ZMemu自动隐藏
        bindingView.rvMain.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
            }
        });
    }

    public void setAdapterTest(List<Object> objects) {
        adapter = new SearchRecyclerAdapter(getContext());
        adapter.clear();
        adapter.addAll(objects);
        bindingView.rvMain.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void init() {

        Drawable drawable = getResources().getDrawable(R.drawable.search_normal);
        drawable.setBounds(0, 0 , DensityUtil.dip2px(30), DensityUtil.dip2px(30));
        bindingView.tvSearch.setCompoundDrawables(drawable, null, null, null);
        bindingView.tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        bindingView.cvAdd.setOnClickListener(this);
    }

    private void loadDetail() {
        categoryIdList = getCategoryIdList();
        findList = new ACacheFindList();
        if (categoryIdList.size() > 0) {

            DebugUtil.debug("test211", "search");
            SearchModel.showDetail(this, categoryIdList, new RequestListener() {
                @Override
                public void onSuccess(Object object) {
                    SectionList find = (SectionList) object;

                    switch (find.getType()) {
                        case "horizontalScrollCardSection":
                            findList.getScrollCardSection().add(find);
                            break;
                        case "videoListSection":
                            findList.getVideoSection().add(find);
                            break;
                        case "authorSection":
                            findList.getAuthorSection().add(find);
                            break;
                    }
                }
                @Override
                public void onFailed(Throwable throwable) {

                    bindingView.srlSearchRefresh.setRefreshing(false);
                    loadError();
                }

                @Override
                public void onCompleted() {

                    if (findList.getScrollCardSection().size() > 0 || findList.getVideoSection().size() > 0 || findList.getAuthorSection().size() > 0) {

                        loadSuccess();
                        setAdapterTest(getPrecessedData());
                        mCache.remove(Constant.EYE_FIND);
                        mCache.put(Constant.EYE_FIND, findList, 18000);
                    }
                }
            });
        } else {
            clear();
            loadSuccess();
        }
    }

    public void clear() {
        if (adapter != null) {
            adapter.clear();
            adapter.notifyDataSetChanged();
        }

    }
    public List<Object> getPrecessedData() {

        authorList.clear();
        combineList.clear();
        for (int i = 0; i < findList.getAuthorSection().size(); i++) {
            authorList.addAll(findList.getAuthorSection().get(i).getItemList());
        }
        combineList.add(authorList);
        combineList.addAll(findList.getVideoSection());

        return combineList;
    }
    public void initSelectTypeRecyclerView() {

        sTRecyclerView = bindingView.rvSelectType;
        //初始化selectTypeRecyclerView
        strAdapter = new SelectTypeRecyclerAdapter(selectLabel, getContext());
        LinearLayoutManager layoutManager = new RecyclerViewNoBugLinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        sTRecyclerView.setLayoutManager(layoutManager);
        sTRecyclerView.setAdapter(strAdapter);
        //长按删除监听
        strAdapter.setItemCLickListener(new SelectTypeRecyclerAdapter.ItemClickListener() {
            @Override
            public void onLongItemLClick(int position) {
                DebugUtil.debug("itemposition", "" + position);
                DataSupport.deleteAll(LabelType.class, "type = ?", selectLabel.get(position));
                selectLabel.remove(position);
                strAdapter.notifyItemRemoved(position);
                strAdapter.notifyItemRangeChanged(position, selectLabel.size() - position);
            }
        });

        //设置拖拽监听
        /*ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                final int dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);

            }
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //strAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //strAdapter.onItemDismiss(viewHolder.getAdapterPosition());
            }
        });
        mItemTouchHelper.attachToRecyclerView(sTRecyclerView);*/
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cv_add:
                MyPopupWindow popupWindow = new MyPopupWindow(getContext());
                popupWindow.showPopupWindow(bindingView.cvAdd);
                popupWindow.setPopItemClickListener(new MyPopupWindow.PopItemClickListener() {
                    @Override
                    public void ItemClick(String labelType) {
                        //存入labelType中没有的type
                        savedLabelList = DataSupport.findAll(LabelType.class);
                        for (LabelType label : savedLabelList) {
                            if (labelType.equals(label.getType())) {
                                return;
                            }
                        }
                        LabelType label = new LabelType();
                        label.setType(labelType);
                        label.save();
                        //刷新数据
                        selectLabel.add(DataSupport.findLast(LabelType.class).getType());
                        strAdapter.notifyDataSetChanged();
                    }
                });
        }
    }

    public void loadSuccess() {
        bindingView.srlSearchRefresh.setRefreshing(false);
        if (bindingView.rlError.getVisibility() == View.VISIBLE) {
            bindingView.rlError.setVisibility(View.GONE);
        }
        if (bindingView.rlSearchLoading.getVisibility() == View.VISIBLE) {
            bindingView.rlSearchLoading.setVisibility(View.GONE);
        }
    }

    public void loadError() {
        if (bindingView.rlError.getVisibility() == View.GONE) {
            bindingView.rlError.setVisibility(View.VISIBLE);
        }
    }
    private List<Integer> getCategoryIdList() {
        List<LabelType> labelTypes = DataSupport.findAll(LabelType.class);
        List<Integer> list = new ArrayList<>();
        for (LabelType type : labelTypes) {
            switch (type.getType()) {
                case "创意":
                    list.add(2);
                    break;
                case "开胃":
                    list.add(4);
                    break;
                case "旅行":
                    list.add(6);
                    break;
                case "预告":
                    list.add(8);
                    break;
                case "动画":
                    list.add(10);
                    break;
                case "剧情":
                    list.add(12);
                    break;
                case "广告":
                    list.add(14);
                    break;
                case "运动":
                    list.add(18);
                    break;
                case "音乐":
                    list.add(20);
                    break;
                case "记录":
                    list.add(22);
                    break;
                case "时尚":
                    list.add(24);
                    break;
                case "萌宠":
                    list.add(26);
                    break;
                case "搞笑":
                    list.add(28);
                    break;
                case "游戏":
                    list.add(30);
                    break;
                case "科普":
                    list.add(32);
                    break;
                case "集锦":
                    list.add(34);
                    break;
                case "生活":
                    list.add(36);
                    break;
                case "综艺":
                    list.add(38);
                    break;
            }
        }
        return list;
    }
}
