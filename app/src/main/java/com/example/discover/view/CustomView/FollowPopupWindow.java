package com.example.discover.view.CustomView;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.discover.R;
import com.example.discover.adapter.LabelChooseAdapter;
import com.example.discover.app.Constant;

/**
 * Created by monkeyWiiu on 2018/1/26.
 */

public class FollowPopupWindow extends PopupWindow {

    private PopItemClickListener mListener;
    public interface PopItemClickListener {
        void ItemClick();
    }

    public void setPopItemClickListener(PopItemClickListener listener) {
        this.mListener = listener;
    }
    public FollowPopupWindow(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.follow_popup, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(contentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);

        //刷新状态
        this.update();
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationFadeRT);

        CardView cardView = this.getContentView().findViewById(R.id.cv);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          
                mListener.ItemClick();
                hide();
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
            this.showAsDropDown(parent,   parent.getLayoutParams().width / 2, 0);
        } else {
            this.dismiss();
        }
    }

    public void hide() {
        this.dismiss();
    }
}
