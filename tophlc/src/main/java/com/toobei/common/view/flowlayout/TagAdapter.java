package com.toobei.common.view.flowlayout;

import android.util.Log;
import android.view.View;


import com.toobei.common.view.timeselector.Utils.TextUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class TagAdapter<T> {
    protected List<T> mTagDatas;
    private OnDataChangedListener mOnDataChangedListener;
    @Deprecated
    private HashSet<Integer> mCheckedPosList = new HashSet<Integer>();
    protected T noCoExistStr;  //不能与其他选项同时选中的条目
    public int noCoExistStrPos = -1;

    public TagAdapter(List<T> datas) {
        mTagDatas = datas;
        noCoExistStr = getNoCoExistStr();
        if (noCoExistStr != null) {
            mTagDatas.add(noCoExistStr);
            noCoExistStrPos = mTagDatas.size()-1;
        } ;
        initCheckedPosList();
    }

    public TagAdapter(T[] datas) {
        mTagDatas = new ArrayList<T>(Arrays.asList(datas));
        if (noCoExistStr != null) {
            mTagDatas.add(noCoExistStr);
            noCoExistStrPos = mTagDatas.size()-1;
        } ;
        initCheckedPosList();
    }

    interface OnDataChangedListener {
        void onChanged();
    }

    private void initCheckedPosList() {
        HashSet<Integer>  foreverChecked = getForeverChecked();
        if (foreverChecked != null&& !foreverChecked.isEmpty()) {
            mCheckedPosList.addAll(foreverChecked);
        }
    }

    void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    @Deprecated
    public void setSelectedList(int... poses) {
        Set<Integer> set = new HashSet<>();
        for (int pos : poses) {
            set.add(pos);
        }
        setSelectedList(set);
    }

    @Deprecated
    public void setSelectedList(Set<Integer> set) {
        mCheckedPosList.clear();
        if (set != null) {
            mCheckedPosList.addAll(set);
        }
        notifyDataChanged();
    }

    @Deprecated
    HashSet<Integer> getPreCheckedList() {
        return mCheckedPosList;
    }


    public int getCount() {
        return mTagDatas == null ? 0 : mTagDatas.size();
    }

    public void notifyDataChanged() {
        if (mOnDataChangedListener != null)
            mOnDataChangedListener.onChanged();
    }

    public T getItem(int position) {
        return mTagDatas.get(position);
    }

    public abstract View getView(FlowLayout parent, int position, T t);


    public void onSelected(int position, View view) {
        Log.d("zhy", "onSelected " + position);
    }

    public void unSelected(int position, View view) {
        Log.d("zhy", "unSelected " + position);
    }

    public boolean setSelected(int position, T t) {
        return false;
    }

    protected HashSet<Integer> getForeverChecked() {
        return new HashSet<Integer>();
    }

    protected T getNoCoExistStr(){
        return null;
    }


}
