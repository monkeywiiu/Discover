package com.example.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.discover.R;
import com.example.discover.app.Constant;
import com.example.discover.view.CustomView.LabelView;

import java.util.List;

/**
 * Created by monkeyWiiu on 2018/1/13.
 */

public class SelectTypeRecyclerAdapter extends RecyclerView.Adapter<SelectTypeRecyclerAdapter.MyViewHolder> {

    private List<String> mLabelList;
    private Context mContext;

    public SelectTypeRecyclerAdapter(List<String> list, Context context) {
        this.mLabelList = list;
        mContext = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.select_label_view, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.labelView.setBackground((int) Constant.LabelMap.get(mLabelList.get(position)));
        holder.labelView.setText(mLabelList.get(position));
    }

    @Override
    public int getItemCount() {
        return mLabelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private LabelView labelView;
        public MyViewHolder(View itemView) {
            super(itemView);
            labelView = itemView.findViewById(R.id.lv_select);
        }
    }
}
