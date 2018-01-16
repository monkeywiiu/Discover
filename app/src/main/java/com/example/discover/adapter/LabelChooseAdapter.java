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
import com.example.discover.bean.LitePalBean.LabelType;
import com.example.discover.view.CustomView.LabelView;

import org.litepal.crud.DataSupport;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by monkeyWiiu on 2018/1/13.
 */

public class LabelChooseAdapter extends RecyclerView.Adapter<LabelChooseAdapter.MyViewHolder> {

    private List<LabelType> savedList;
    private HashMap mMap;
    private List<String> mKeyList;
    private Context mContext;
    private ItemClickListener mListener;
    public interface ItemClickListener {
        void itemClick(String labelType);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.mListener = listener;
    }
    public LabelChooseAdapter(List<String> list, HashMap map, Context context) {
        this.mKeyList = list;
        this.mMap = map;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.label_view, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.labelView.setCardBackgroundColor((int)mMap.get(mKeyList.get(position)));
        holder.textLabel.setText(mKeyList.get(position));

        holder.labelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*savedList = DataSupport.findAll(LabelType.class);
                for (LabelType label : savedList) {
                    if (mKeyList.get(position).equals(label.getType())) {
                        return;
                    }
                }
                LabelType label = new LabelType();
                label.setType(mKeyList.get(position));
                label.save();*/
                mListener.itemClick(mKeyList.get(position));
            }
        });
    }



    @Override
    public int getItemCount() {
        return mMap.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textLabel;
        private CardView labelView;
        private MyViewHolder(View itemView) {
            super(itemView);
            textLabel = itemView.findViewById(R.id.tv_label);
            labelView = itemView.findViewById(R.id.cv_choose_label);
        }
    }

}
