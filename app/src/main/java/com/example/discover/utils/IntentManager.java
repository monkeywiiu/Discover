package com.example.discover.utils;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.View;

import com.example.discover.AuthorHomeActivity;
import com.example.discover.R;
import com.example.discover.ResultActivity;
import com.example.discover.VideoDetailActivity;
import com.example.discover.app.Constant;
import com.example.discover.bean.DetailBean.ItemList;

/**
 * Created by monkeyWiiu on 2018/1/30.
 */

public class IntentManager {

    public static void toVideoDetailActivity(Activity context, ItemList item, View view) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        intent.putExtra("item", item);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                context,
                Pair.create(view, context.getString(R.string.transition_shot)),
                Pair.create(view, context.getString(R.string.transition_shot_background))
        );
        context.startActivity(intent, options.toBundle());
    }

    public static void toAuthorHomeActivity(Context context,ItemList list) {
        int color = 0;
        if (list.getData().getItemList().size()  > 0) {
            color = (Integer) Constant.LabelMap.get(list.getData().getItemList().get(0).getData().getCategory());
        }
        Intent intent = new Intent(context, AuthorHomeActivity.class);
        intent.putExtra("AuthorId", list.getData().getHeader().getId());
        intent.putExtra("AuthorName", list.getData().getHeader().getTitle());
        intent.putExtra("AuthorIcon", list.getData().getHeader().getIcon());
        intent.putExtra("AuthorDesc", list.getData().getHeader().getDescription());
        intent.putExtra("Color", color);
        try {
            intent.putExtra("AuthorBack", list.getData().getItemList().get(0).getData().getTags().get(0).getHeaderImage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        context.startActivity(intent);
    }

    public static void fromDetailtoAuthor(Context context, ItemList list) {
        int color = 0;
        color = (int) Constant.LabelMap.get(list.getData().getCategory());
        Intent intent = new Intent(context, AuthorHomeActivity.class);

        intent.putExtra("AuthorId", list.getData().getAuthor().getId());
        intent.putExtra("AuthorName", list.getData().getAuthor().getName());
        intent.putExtra("AuthorIcon", list.getData().getAuthor().getIcon());
        intent.putExtra("AuthorDesc", list.getData().getAuthor().getDescription());
        intent.putExtra("Color", color);
        try {
            intent.putExtra("AuthorBack", list.getData().getTags().get(0).getHeaderImage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        context.startActivity(intent);
    }

}
