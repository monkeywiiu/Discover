package com.example.discover.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;

import com.example.discover.R;
import com.example.discover.VideoDetailActivity;
import com.example.discover.base.baseadapter.BaseRecyclerAdapter;
import com.example.discover.base.baseadapter.BaseViewHolder;
import com.example.discover.bean.DetailBean.ItemList;
import com.example.discover.databinding.CategoryCardBinding;
import com.example.discover.utils.IntentManager;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * Created by monkeyWiiu on 2018/1/18.
 */

public class CategoryPopAdapter extends BaseRecyclerAdapter<ItemList> {
    public CategoryPopAdapter(Context context ) {
        super(context);
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CateGoryCardHolder(parent, R.layout.category_card);
    }

    public class CateGoryCardHolder extends BaseViewHolder<ItemList, CategoryCardBinding> {
        public CateGoryCardHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void fillHolder(final ItemList object, int position) {
            //itemViewBinding.tvText.setText(object.getData().getTitle());
            itemViewBinding.setItemList(object);
            RxView.clicks(itemViewBinding.videoAlbum)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe(new Consumer<Object>() {

                        @Override
                        public void accept(Object o) throws Exception {
                            IntentManager.toVideoDetailActivity((Activity) mContext, object, itemViewBinding.videoAlbum);
                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /*private void toVideoDetailActivity(ItemList item, View view) {
        Intent intent = new Intent(mContext, VideoDetailActivity.class);
        intent.putExtra("item", item);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                mContext,
                Pair.create(view, mContext.getString(R.string.transition_shot)),
                Pair.create(view, mContext.getString(R.string.transition_shot_background))
        );
        mContext.startActivity(intent, options.toBundle());
    }*/
}
