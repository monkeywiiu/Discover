package com.example.discover.base.baseadapter;

import android.support.v7.widget.RecyclerView;

import com.example.discover.utils.DebugUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/12/13 0013.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    public List<T> mData;

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.baseFillHolder(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addAll(List<T> data) {
        this.mData = data;
        DebugUtil.debug("listSize", data.size() + "");
    }

}
