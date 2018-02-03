package com.example.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.example.discover.R;
import com.example.discover.base.baseadapter.BaseRecyclerAdapter;
import com.example.discover.base.baseadapter.BaseViewHolder;
import com.example.discover.bean.GankBean;
import com.example.discover.databinding.FooterItemVideoBinding;
import com.example.discover.databinding.ItemWelfareBinding;
import com.example.discover.utils.DebugUtil;

/**
 * Created by monkeyWiiu on 2018/2/3.
 */

public class WelfareAdapter extends BaseRecyclerAdapter<GankBean.ResultBean> {

    private int LOAD_MORE = 1;
    private final static int TYPE_FOOTER = 3;
    private final static int TYPE_CONTENT = 2;
    private final static int STATE_NORMAL = -1;
    private final static int NO_MORE = 0;
    private  int mState = STATE_NORMAL;
    private FooterItemVideoBinding mFooterBinding;

    public WelfareAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {

        if (position + 1 == getItemCount()) {
            DebugUtil.debug("footer", "true");
            return TYPE_FOOTER;
        } else {
            return TYPE_CONTENT;
        }

    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT) {
            return new ContentHolder(parent, R.layout.item_welfare);
        }else {
            return new FooterHolder(parent, R.layout.footer_item_video);
        }
    }

    public class ContentHolder extends BaseViewHolder<GankBean.ResultBean, ItemWelfareBinding> {
        public ContentHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void fillHolder(final GankBean.ResultBean object, final int position) {
            itemViewBinding.setData(object);
            itemViewBinding.executePendingBindings();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(itemViewBinding.ivWelfare, object, position);
                    }

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
            DebugUtil.debug("loadingm", "11");
            mFooterBinding = itemViewBinding;
            itemViewBinding.loading.show();
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

    public void updateStateLoad(boolean loading) {
        if (loading) {
            this.mState = LOAD_MORE;
        }else {
            this.mState = NO_MORE;
        }
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isStaggeredGridLayout(holder)) {
            handleLayoutIfStaggeredGridLayout(holder, holder.getLayoutPosition());
        }
    }

    private boolean isStaggeredGridLayout(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            return true;
        }
        return false;
    }

    protected void handleLayoutIfStaggeredGridLayout(RecyclerView.ViewHolder holder, int position) {
        if (isFooter(position)) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            p.setFullSpan(true);
        }
    }

    public boolean isFooter(int position) {
        if (position + 1 == getItemCount()) {
            DebugUtil.debug("footer", "true");
            return  true;
        } else
            return false;
    }
}
