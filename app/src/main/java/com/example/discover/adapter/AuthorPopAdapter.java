package com.example.discover.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.example.discover.R;
import com.example.discover.base.baseadapter.BaseRecyclerAdapter;
import com.example.discover.base.baseadapter.BaseViewHolder;
import com.example.discover.bean.CategoryDetailBean.SectionList;
import com.example.discover.databinding.AuthorCardBinding;
import com.example.discover.utils.DebugUtil;

import java.util.List;

/**
 * Created by monkeyWiiu on 2018/1/18.
 */

public class AuthorPopAdapter extends BaseRecyclerAdapter<SectionList> {
    //public AuthorPopAdapter(){};
    public AuthorPopAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AuthorCardHolder(parent, R.layout.author_card);
    }

    public class AuthorCardHolder extends BaseViewHolder<SectionList, AuthorCardBinding> {
        public AuthorCardHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void fillHolder(SectionList object, int position) {

            itemViewBinding.tvText.setText(object.getItemList().get(0).getData().getItemList().get(0).getData().getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
