package com.example.discover.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.discover.R;
import com.example.discover.base.baseadapter.BaseRecyclerAdapter;
import com.example.discover.base.baseadapter.BaseViewHolder;
import com.example.discover.bean.DetailBean.Data;
import com.example.discover.bean.LitePalBean.SearchTag;
import com.example.discover.databinding.SearchTagBinding;
import com.example.discover.databinding.TagHeaderNewBinding;
import com.example.discover.databinding.TagHeaderRecoBinding;
import com.example.discover.utils.DebugUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by monkeyWiiu on 2018/1/28.
 */

public class SearchTagAdapter extends BaseRecyclerAdapter<String> {

    private static int HEADER_ONE = 0;
    private static int HEADER_TWO = 1;
    private static int TAG = 3;
    public SearchTagAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if ("new".equals(mData.get(position))) {
            return HEADER_ONE;
        } else if ("recommend".equals(mData.get(position))) {
            return HEADER_TWO;
        }else {
            return TAG;
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_ONE) {
            return new HeaderOneHolder(parent, R.layout.tag_header_new);
        } else if (viewType == TAG) {
            return new SearchTagHolder(parent, R.layout.search_tag);
        } else if (viewType == HEADER_TWO) {
            return new HeaderTwoHolder(parent, R.layout.tag_header_reco);
        }
        return null;
    }

    public class HeaderOneHolder extends BaseViewHolder<Object, TagHeaderNewBinding> {
        private HeaderOneHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void fillHolder(Object object, int position) {

            /*if (DataSupport.count(SearchTag.class) == 0) {
                itemViewBinding.itemNew.setVisibility(View.GONE);
            } else {
                itemViewBinding.itemNew.setVisibility(View.VISIBLE);
            }*/
        }
    }

    public class HeaderTwoHolder extends BaseViewHolder<Object, TagHeaderRecoBinding> {

        private HeaderTwoHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void fillHolder(Object object, int position) {

        }
    }

    public class SearchTagHolder extends BaseViewHolder<String, SearchTagBinding> {

        private SearchTagHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void fillHolder(final String object, final int position) {
            if (position < DataSupport.count(SearchTag.class) + 1) {
                DebugUtil.debug("tagposition", position + "//" + object);
                itemViewBinding.delete.setVisibility(View.VISIBLE);
                itemViewBinding.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        delete(position);
                        DataSupport.deleteAll(SearchTag.class, "tag = ?", object);
                        if (DataSupport.count(SearchTag.class) == 0) {
                            delete(0);
                        }
                    }
                });
            } else {
                itemViewBinding.delete.setVisibility(View.GONE);
            }

            itemViewBinding.tvTag.setText(object);
        }

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }
}
