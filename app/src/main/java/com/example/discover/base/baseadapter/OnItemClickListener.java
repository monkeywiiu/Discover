package com.example.discover.base.baseadapter;

import android.view.View;

/**
 * Created by jingbin on 2016/3/2.
 */
public interface OnItemClickListener<T> {
    public void onClick(View view, T t, int position);
}
