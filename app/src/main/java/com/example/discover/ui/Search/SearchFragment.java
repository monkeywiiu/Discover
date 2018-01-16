package com.example.discover.ui.Search;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.example.discover.R;
import com.example.discover.adapter.SelectTypeRecyclerAdapter;
import com.example.discover.base.BaseFragment;
import com.example.discover.bean.CateGoryEyeBean;
import com.example.discover.bean.LitePalBean.LabelType;
import com.example.discover.databinding.FragmentSearchBinding;
import com.example.discover.http.RequestListener;
import com.example.discover.model.SearchModel;
import com.example.discover.ui.RecyclerViewNoBugLinearLayoutManager;
import com.example.discover.utils.DebugUtil;
import com.example.discover.utils.DensityUtil;
import com.example.discover.utils.LitePalUtil;
import com.example.discover.view.CustomView.MyPopupWindow;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by monkeyWiiu on 2018/1/12.
 */

public class SearchFragment extends BaseFragment<FragmentSearchBinding> implements View.OnClickListener {

    private boolean isPrepare = false;
    private boolean isFirst = true;
    private List<Integer> categoryIdList = new ArrayList<>();
    private List<List<CateGoryEyeBean.SectionListBean>> categoryConentList = new ArrayList<>();
    private RecyclerView sTRecyclerView;
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
        selectLabel = LitePalUtil.getSelectLabel();
        init();
        initSelectTypeRecyclerView();
        categoryIdList = getCategoryIdList();
        isPrepare = true;
    }

    @Override
    protected void loadData() {
        if (!isPrepare || !isFirst || !isVisibile) {
            return;
        }

        loadDetail();
        //避免重复加载
        isFirst = false;
    }

    private void init() {

        Drawable drawable = getResources().getDrawable(R.drawable.search_normal);
        drawable.setBounds(0, 0 , DensityUtil.dip2px(30), DensityUtil.dip2px(30));
        bindingView.tvSearch.setCompoundDrawables(drawable, null, null, null);
        bindingView.cvAdd.setOnClickListener(this);
    }


    private void loadDetail() {
        categoryIdList = getCategoryIdList();
        if (categoryIdList.size() > 0) {

            DebugUtil.debug("test211", "search");
            SearchModel.showDetail(categoryIdList, new RequestListener() {
                @Override
                public void onSuccess(Object object) {
                    CateGoryEyeBean cateGoryEyeBean = (CateGoryEyeBean) object;
                    DebugUtil.debug("categoryeye", cateGoryEyeBean.getCategoryInfo().getName());

                }

                @Override
                public void onFailed() {

                }

                @Override
                public void addSubscription(Subscription subscription) {
                    addToMySubscription(subscription);
                }
            });
        }

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
                //Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
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
