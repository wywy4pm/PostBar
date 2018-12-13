package com.arun.bar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by wy on 2017/6/8.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {
    protected List<T> list;
    protected WeakReference<Context> contexts;

    public BaseRecyclerAdapter(Context context, List<T> list) {
        this.list = list;
        contexts = new WeakReference<>(context);
    }

    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
