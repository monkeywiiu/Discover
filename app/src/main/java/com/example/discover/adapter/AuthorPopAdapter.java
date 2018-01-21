package com.example.discover.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.example.discover.R;
import com.example.discover.base.baseadapter.BaseRecyclerAdapter;
import com.example.discover.base.baseadapter.BaseViewHolder;
import com.example.discover.bean.CategoryDetailBean.ItemList;
import com.example.discover.databinding.AuthorCardBinding;


/**
 * Created by monkeyWiiu on 2018/1/18.
 */

public class AuthorPopAdapter extends BaseRecyclerAdapter<ItemList> {
    //public AuthorPopAdapter(){};
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
        public void fillHolder(ItemList object, int position) {

            itemViewBinding.setItemList(object);
            //itemViewBinding.tvText.setText(object.getItemList().get(0).getData().getItemList().get(0).getData().getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
