package com.toobei.common.data;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qingTX007 on 2016/9/20.
 */
public class MyBasePagerAdapter<T> extends FragmentPagerAdapter {
    public Activity activity;
    public List<T> datas;
    public Fragment[] fragments;

    public MyBasePagerAdapter(Activity activity, FragmentManager fm, List<T> datas) {
        super(fm);
        this.datas = datas;
        this.activity = activity;
        fragments = new Fragment[getCount()];

    }


    @Override
    public int getCount() {

        return datas == null ? 0 : datas.size();
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

    @Override
    public Fragment getItem(int position) {
        return null;
    }
}
