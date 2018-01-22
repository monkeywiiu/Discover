package com.example.discover.utils;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.discover.R;
import com.example.discover.adapter.AuthorPopAdapter;
import com.example.discover.adapter.CategoryPopAdapter;
import com.example.discover.bean.CategoryDetailBean.ItemList;
import com.example.discover.bean.CategoryDetailBean.SectionList;
import com.example.discover.view.CustomView.CircleImageView;

import java.util.List;

/**
 * Created by monkeyWiiu on 2018/1/18.
 */

public class DatabindingUtil {

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
        /*Glide.with(view.getContext()).load(url)
                .asBitmap()
                .placeholder(R.drawable.cross_image)
                .error(R.drawable.close_press)
                .into(new BitmapImageViewTarget(view) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable circleImage = RoundedBitmapDrawableFactory.create(view.getResources(), resource);
                        circleImage.setCircular(true);
                        view.setImageDrawable(circleImage);
                    }
                });*/
    }
    @BindingAdapter("visibility")
    public static void setVisibility(View view, boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
