package com.example.discover.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.discover.R;
import com.example.discover.base.BaseFragment;
import com.example.discover.base.baseadapter.BaseRecyclerAdapter;
import com.example.discover.base.baseadapter.BaseViewHolder;
import com.example.discover.bean.LitePalBean.Follow;
import com.example.discover.databinding.FooterItemVideoBinding;
import com.example.discover.databinding.ItemFollowBinding;
import com.example.discover.utils.DebugUtil;
import com.example.discover.view.CustomView.FollowPopupWindow;

import org.litepal.crud.DataSupport;

/**
 * Created by monkeyWiiu on 2018/1/26.
 */

public class FollowRecyclerAdapter extends BaseRecyclerAdapter<Follow> {

    private final static int TYPE_CONTENT = 2;
    private final static int TYPE_FOOTER = 3;
    private FooterItemVideoBinding mFooterBinding;
    private final static int LOAD_MORE = 1;
    private final static int NO_MORE = 0;
    private final static int STATE_NORMAL = -1;
    private int mState = STATE_NORMAL;
    public FollowRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_CONTENT;
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT) {
            return new FollowHolder(parent, R.layout.item_follow);
        } else {
            return new FooterHolder(parent, R.layout.footer_item_video);
        }

    }

    public class FollowHolder extends BaseViewHolder<Follow, ItemFollowBinding> {
        public FollowHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void fillHolder(final Follow object, final int position) {

            itemViewBinding.setFollow(object);
            itemViewBinding.selector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FollowPopupWindow window = new FollowPopupWindow(v.getContext());
                    window.showPopupWindow(itemViewBinding.selector);
                    window.setPopItemClickListener(new FollowPopupWindow.PopItemClickListener() {
                        @Override
                        public void ItemClick() {
                            DataSupport.deleteAll(Follow.class, "authorId = ?", String.valueOf(object.getAuthorId()));
                            delete(position);
                        }
                    });
                }
            });
        }
    }

    public class FooterHolder extends BaseViewHolder<Object, FooterItemVideoBinding> {
        public FooterHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void fillHolder(Object object, int position) {
            mFooterBinding = itemViewBinding;
            //itemViewBinding.loading.show();
        }
    }

    public void updateStateLoad(boolean loading) {

        if (loading) {
            this.mState = LOAD_MORE;
        }else {
            this.mState = NO_MORE;
        }
    }

    public boolean isLoading() {
        if (this.mState == LOAD_MORE) {
            return true;
        } else
            return false;
    }

    public void hideLoading() {
        mFooterBinding.loading.hide();
    }
}
