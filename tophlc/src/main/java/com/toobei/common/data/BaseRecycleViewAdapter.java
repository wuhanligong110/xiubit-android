package com.toobei.common.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/18 0018.
 */
public abstract class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter {

    public Context context;
    protected LayoutInflater inflater;
    public List<T> datas = new ArrayList<>();

    public BaseRecycleViewAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public BaseRecycleViewAdapter(Context context, List<T> datas) {
        this.context = context;
        this.datas = datas;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getItemCount() {
        if (datas == null) return 0;
        return datas.size();
    }


    public void add(T object) {
        if (datas == null) {
            datas = new ArrayList<T>();
        }
        if (datas.contains(object)) {
            return;
        }
        datas.add(object);
        notifyDataSetChanged();
    }

    public void addAll(List<T> subDatas) {
        if (subDatas != null) {
            if (datas == null) {
                datas = new ArrayList<T>();
            }
            datas.addAll(subDatas);
            notifyDataSetChanged();
        }
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }


    public List<T> getDatas() {
        return datas;

    }

    public void remove(int position) {
        if (datas == null) return;
        datas.remove(position);
        notifyDataSetChanged();
    }

    public void clear() {
        if (datas != null) {
            datas.clear();
            notifyDataSetChanged();
        }
    }

    public void refresh(List<T> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }
}
