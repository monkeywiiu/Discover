package com.example.discover.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    private ItemClickListener mListener;
    public interface ItemClickListener {
        void onLongItemLClick(int position);
    }

    public void setItemCLickListener(ItemClickListener listener) {
        this.mListener = listener;
    }

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
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.labelView.setCardBackgroundColor((int) Constant.LabelMap.get(mLabelList.get(position)));
        holder.textLabel.setText(mLabelList.get(position));
        holder.labelView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mListener.onLongItemLClick(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLabelList.size();
    }

    /**
     * 拖拽移位
     *
     */
    /*@Override
    public void onItemMove(int fromPosition, int toPosition) {

        String prev = mLabelList.remove(fromPosition);
        mLabelList.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);

    }

    @Override
    public void onItemDismiss(int position) {

    }*/

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textLabel;
        private CardView labelView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textLabel = itemView.findViewById(R.id.tv_label);
            labelView = itemView.findViewById(R.id.cv_select_label);
        }
    }
}
