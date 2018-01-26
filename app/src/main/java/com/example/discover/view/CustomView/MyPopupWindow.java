package com.example.discover.view.CustomView;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.discover.R;
import com.example.discover.adapter.LabelChooseAdapter;
import com.example.discover.app.Constant;
import com.example.discover.utils.DensityUtil;

import java.util.zip.Inflater;

/**
 * Created by monkeyWiiu on 2018/1/13.
 */

public class MyPopupWindow extends PopupWindow {


    private PopItemClickListener mListener;
    public interface PopItemClickListener {
        void ItemClick(String labelType);
    }

    public void setPopItemClickListener(PopItemClickListener listener) {
        this.mListener = listener;
    }
    public MyPopupWindow(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.my_popup_window, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(contentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        //this.setWidth(DensityUtil.dip2px(300));
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
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
        this.setAnimationStyle(R.style.AnimationFadeLT);

        initRecycler(contentView, context);
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 48);
        } else {
            this.dismiss();
        }
    }

    public void initRecycler(View view, Context context) {
        RecyclerView rvLabel = view.findViewById(R.id.rv_label_choose);
        LabelChooseAdapter adapter;adapter = new LabelChooseAdapter(Constant.videoTypeList, Constant.LabelMap, context);
        adapter.setItemClickListener(new LabelChooseAdapter.ItemClickListener() {
            @Override
            public void itemClick(String labelType) {
                mListener.ItemClick(labelType);
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false);
        rvLabel.setLayoutManager(layoutManager);
        rvLabel.setAdapter(adapter);
    }

}
