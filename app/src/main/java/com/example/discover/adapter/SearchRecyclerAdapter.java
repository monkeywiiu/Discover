package com.example.discover.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.example.discover.R;
import com.example.discover.base.baseadapter.BaseRecyclerAdapter;
import com.example.discover.base.baseadapter.BaseViewHolder;
import com.example.discover.bean.CategoryDetailBean.ItemList;
import com.example.discover.bean.CategoryDetailBean.SectionList;
import com.example.discover.databinding.RecommendAuthorBinding;
import com.example.discover.databinding.RecommendCategoryBinding;
import com.example.discover.utils.DebugUtil;

import java.util.List;

/**
 * Created by monkeyWiiu on 2018/1/18.
 */

public class SearchRecyclerAdapter extends BaseRecyclerAdapter<Object> {

    private static int TYPE_AUTHOR = 0;
    private static int TYPE_CATEGORY = 1;

    public SearchRecyclerAdapter(Context context) {
        super(context);
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_AUTHOR) {
            return new AuthorBindingHolder(parent, R.layout.recommend_author);
        } else if (viewType == TYPE_CATEGORY) {
            return new CategoryBindingHolder(parent, R.layout.recommend_category);
        } else {
            return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return  TYPE_AUTHOR;
        } else {
            return TYPE_CATEGORY;
        }
    }


    public class AuthorBindingHolder extends BaseViewHolder<List<ItemList>, RecommendAuthorBinding>{
        public AuthorBindingHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void fillHolder(List<ItemList> object, int position) {

            DebugUtil.debug("objjt", object.size() + "");
            itemViewBinding.setData(object);
        }
    }

    public class CategoryBindingHolder extends BaseViewHolder<SectionList, RecommendCategoryBinding> {
        public CategoryBindingHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void fillHolder(SectionList object, int position) {

            if (object.getItemList().size() > 0) {
                itemViewBinding.tvCategory.setText(object.getItemList().get(0).getData().getCategory());
            }
            itemViewBinding.setData(object.getItemList());
        }

    }



    @Override
    public int getItemCount() {
        return mData.size();
    }
}
