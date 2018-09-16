package org.xsl781.data;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

public class BaseCheckListAdapter<T> extends BaseListAdapter<T> {
    public List<Boolean> checkStates = new ArrayList<Boolean>();
    public boolean defaultState = false;

    public BaseCheckListAdapter(Context ctx) {
        super(ctx);
    }

    public BaseCheckListAdapter(Context ctx, List<T> datas) {
        super(ctx, datas);
        initCheckStates(datas);
    }

    @Override
    public void setDatas(List<T> datas) {
        super.setDatas(datas);
        initCheckStates(datas);
    }

    protected void initCheckStates(List<T> datas) {
        if (datas == null) {
            return;
        }
        checkStates.clear();

        for (int i = 0; i < datas.size(); i++) {
            checkStates.add(defaultState);
        }
    }

    public List<Boolean> getCheckStates() {
        return checkStates;
    }

    @SuppressWarnings("unchecked")
    public List<T> getCheckedDatas() {
        List<T> checkedDatas = new ArrayList<T>();
        if (datas == null || datas.size() == 0) {
            return null;
        }
        for (int i = 0; i < checkStates.size(); i++) {
            if (checkStates.get(i)) {
                checkedDatas.add(datas.get(i));
            }
        }
        return checkedDatas;
    }

    public int getCheckedCount() {
        int count = 0;
        if (checkStates != null && checkStates.size() > 0) {
            for (int i = 0; i < checkStates.size(); i++) {
                if (checkStates.get(i)) {
                    count++;
                }
            }
        }
        return count;
    }

    void checkItem(int position) {
        assertSize(position);
        if (checkStates.get(position) == false) {
            checkStates.set(position, true);
        }
    }

    void uncheckItem(int position) {
        assertSize(position);
        if (checkStates.get(position)) {
            checkStates.set(position, false);
        }
    }

    protected void toggleItem(int position) {
        assertSize(position);
        checkStates.set(position, !checkStates.get(position));
    }

    protected void setCheckState(int position, boolean state) {
        assertSize(position);
        checkStates.set(position, state);
    }

    protected void assertSize(int position) {
        if (position >= checkStates.size()) {
            //throw new IllegalArgumentException("position is bigger than size");
            //	Logger.d("illegal " + position + checkStates.size());
        }
    }

    @Override
    public void addAll(List<T> subDatas) {
        if (subDatas != null) {
            if (datas == null) {
                datas = new ArrayList<T>();
            }
            for (int i = 0; i < subDatas.size(); i++) {
                checkStates.add(defaultState);
            }
            datas.addAll(subDatas);
            notifyDataSetChanged();
        }
    }

    @Override
    public void add(T object) {
        if (datas == null) {
            datas = new ArrayList<T>();
        }
        if (datas.contains(object)) {
            return;
        }
        checkStates.add(defaultState);
        datas.add(object);
        notifyDataSetChanged();
    }

    @Override
    public void remove(int position) {
        unsupport();
    }

    private void unsupport() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        if (datas == null) return;
        datas.clear();
        checkStates.clear();
        notifyDataSetChanged();
    }

    public void refresh(List<T> datas) {
        this.datas = datas;
        initCheckStates(datas);
        notifyDataSetChanged();
    }

    public void setCheckBox(CheckBox checkBox, int position) {
        assertSize(position);
        checkBox.setChecked(checkStates.get(position));
    }

    public class CheckListener implements CompoundButton.OnCheckedChangeListener {
        protected int position;

        public CheckListener(int position) {
            this.position = position;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            setCheckState(position, isChecked);
        }
    }
}
