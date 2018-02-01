package com.example.discover.utils;

import android.databinding.BindingAdapter;
import android.databinding.BindingBuildInfo;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.discover.R;
import com.example.discover.adapter.AuthorPopAdapter;
import com.example.discover.adapter.CategoryPopAdapter;
import com.example.discover.bean.DetailBean.Data;
import com.example.discover.bean.DetailBean.ItemList;
import com.example.discover.view.CustomView.CircleImageView;

import java.util.List;

/**
 * Created by monkeyWiiu on 2018/1/18.
 */

public class BindingUtil {

    @BindingAdapter("authorData")
    public static void setAuthorData(RecyclerView view, List<ItemList> lists) {
        AuthorPopAdapter authorPopAdapter = new AuthorPopAdapter(view.getContext());
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext()
                , LinearLayoutManager.HORIZONTAL, false);
        authorPopAdapter.addAll(lists);
        view.setLayoutManager(manager);
        view.setAdapter(authorPopAdapter);
    }

    @BindingAdapter("categoryData")
    public static void setCategoryData(RecyclerView view, List<ItemList> list) {

        CategoryPopAdapter categoryPopAdapter = new CategoryPopAdapter(view.getContext());
        categoryPopAdapter.addAll(list);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext()
                , LinearLayoutManager.HORIZONTAL, false);
        view.setLayoutManager(manager);
        view.setAdapter(categoryPopAdapter);
    }

    @BindingAdapter("imageUrl")
    public static void setImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(url)
                .crossFade(800)
                .placeholder(R.drawable.cross_image)
                .error(R.drawable.cross_image)
                .into(view);
    }

    @BindingAdapter("circleImageUrl")
    public static void setCircleImage(CircleImageView view, String url) {
        Glide.with(view.getContext()).load(url)
                .crossFade(800)
                .dontAnimate()
                .placeholder(R.drawable.cross_image)
                .error(R.drawable.close_press)
                .into(view);

    }
    @BindingAdapter("visibility")
    public static void setVisibility(View view, boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("authorName")
    public static void setAuthorName(TextView v, ItemList item) {
        if (item.getData().getAuthor() != null) {
            v.setText(item.getData().getAuthor().getName());
        } else
            v.setText("");
    }


    @BindingAdapter("time")
    public static void setTime(TextView view, Long time) {
        view.setText(DateUtils.getRelativeTimeSpanString(time,
                System.currentTimeMillis(),  DateUtils.SECOND_IN_MILLIS).toString().toLowerCase());
    }

    @BindingAdapter("type")
    public static void setType(TextView view, Data data) {
        view.setText(data.getCategory() + " | " + TimeUtils.secToTime((int) data.getDuration()));
    }
}
