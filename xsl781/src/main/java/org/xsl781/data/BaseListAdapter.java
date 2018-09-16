package org.xsl781.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class BaseListAdapter<T> extends BaseAdapter {
    protected Context ctx;
    protected LayoutInflater inflater;
    protected List<T> datas = new ArrayList<T>();

    public BaseListAdapter(Context ctx) {
        initWithContext(ctx);
    }

    public void initWithContext(Context ctx) {
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
    }

    public BaseListAdapter(Context ctx, List<T> datas) {
        initWithContext(ctx);
        this.datas = datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public List<T> getDatas() {
        return datas;
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

    public void remove(int position) {
        if (datas == null) return;
        datas.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (datas == null || datas.size() == 0) {
            return 0;
        }
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        if (position < 0 || position >= datas.size()) return null;
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public void clear() {
        if (datas == null) return;
        datas.clear();
        notifyDataSetChanged();
    }

    public void refresh(List<T> datas) {

        // TODO: 2017/2/10 0010
       // this.datas.clear();
        //this.datas.addAll(datas);
        this.datas=datas;
        notifyDataSetChanged();
    }
}
