package com.example.discover.base.baseadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.discover.utils.DebugUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/13 0013.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    public List<T> mData = new ArrayList<>();
    public Context mContext;

    public BaseRecyclerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.baseFillHolder(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addAll(List<T> data) {
        this.mData.addAll(data);
        DebugUtil.debug("listSize", data.size() + "");
    }

    public void clear() {
        this.mData.clear();
    }
}
