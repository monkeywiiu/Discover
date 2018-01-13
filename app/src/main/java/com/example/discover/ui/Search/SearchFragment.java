package com.example.discover.ui.Search;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.discover.R;
import com.example.discover.adapter.SelectTypeRecyclerAdapter;
import com.example.discover.base.BaseFragment;
import com.example.discover.bean.LitePalBean.LabelType;
import com.example.discover.databinding.FragmentSearchBinding;
import com.example.discover.utils.DensityUtil;
import com.example.discover.utils.LitePalUtil;
import com.example.discover.view.CustomView.MyPopupWindow;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by monkeyWiiu on 2018/1/12.
 */

public class SearchFragment extends BaseFragment<FragmentSearchBinding> implements View.OnClickListener {

    private TextView searchText;
    private RecyclerView sTRecyclerView;
    private List<String> selectLabel;
    private List<LabelType> savedLabelList;
    private SelectTypeRecyclerAdapter strAdapter;
    private Handler mHandler;
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
    }

    private void init() {

        sTRecyclerView = bindingView.rvSelectType;
        Drawable drawable = getResources().getDrawable(R.drawable.search_normal);
        drawable.setBounds(0, 0 , DensityUtil.dip2px(30), DensityUtil.dip2px(30));
        bindingView.tvSearch.setCompoundDrawables(drawable, null, null, null);
        bindingView.ivAdd.setOnClickListener(this);

        //初始化selectTypeRecyclerView
        strAdapter = new SelectTypeRecyclerAdapter(selectLabel, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        sTRecyclerView.setLayoutManager(layoutManager);
        sTRecyclerView.setAdapter(strAdapter);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_add:
                MyPopupWindow popupWindow = new MyPopupWindow(getContext());
                popupWindow.showPopupWindow(bindingView.ivAdd);
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

}
