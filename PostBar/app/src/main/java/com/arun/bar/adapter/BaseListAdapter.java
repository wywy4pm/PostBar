package com.arun.bar.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by wy on 2017/5/12.
 */

public abstract class BaseListAdapter<T> extends BaseAdapter {
    public Context context;
    private List<T> list;

    public BaseListAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
