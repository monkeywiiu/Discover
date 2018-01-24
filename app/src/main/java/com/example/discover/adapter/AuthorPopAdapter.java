package com.example.discover.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.discover.AuthorHomeActivity;
import com.example.discover.R;
import com.example.discover.app.Constant;
import com.example.discover.base.baseadapter.BaseRecyclerAdapter;
import com.example.discover.base.baseadapter.BaseViewHolder;
import com.example.discover.bean.DetailBean.ItemList;
import com.example.discover.databinding.AuthorCardBinding;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * Created by monkeyWiiu on 2018/1/18.
 */

public class AuthorPopAdapter extends BaseRecyclerAdapter<ItemList> {

    public AuthorPopAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AuthorCardHolder(parent, R.layout.author_card);
    }

    public class AuthorCardHolder extends BaseViewHolder<ItemList, AuthorCardBinding> {
        public AuthorCardHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void fillHolder(final ItemList object, int position) {


            RxView.clicks(itemViewBinding.cvEnter)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            toAuthorHomeActivity(object);
                        }
                    });
            setBackGroundColor(itemViewBinding, object);
            itemViewBinding.setItemList(object);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setBackGroundColor(AuthorCardBinding binding, ItemList object) {
        if (object.getData().getItemList().size() > 0) {
            int color;
            color = (Integer) Constant.LabelMap.get(object.getData().getItemList().get(0).getData().getCategory());
            binding.cvAuthor.setCardBackgroundColor(color);
            binding.cvImage.setCardBackgroundColor(color);
            binding.civAvatar.setBorderColor(color);
        }
    }

    public void toAuthorHomeActivity(ItemList list) {
        int color = 0;
        if (list.getData().getItemList().size()  > 0) {
            color = (Integer) Constant.LabelMap.get(list.getData().getItemList().get(0).getData().getCategory());
        }
        Toast.makeText(mContext, "dianji", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mContext, AuthorHomeActivity.class);
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

        mContext.startActivity(intent);
    }
}
