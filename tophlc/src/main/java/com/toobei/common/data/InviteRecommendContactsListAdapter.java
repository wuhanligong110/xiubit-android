package com.toobei.common.data;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.entity.Contacts;

import org.xsl781.data.BaseCheckListAdapter;
import org.xsl781.ui.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司: tophlc
 * 类说明：通讯录 邀请 列表
 *
 * @date 2015-12-28
 */
public class InviteRecommendContactsListAdapter extends BaseCheckListAdapter<Contacts> {

    private boolean isCheckVisible = true;
    private List<Contacts> oldCheckedUsers;
    private List<Contacts> newCheckedUsers = new ArrayList<Contacts>();
    private List<Integer> oldCheckedPosition;
    private OnMyCheckedListener checkedListener;

    public interface OnMyCheckedListener {
        void onCheckedChanged(int selectedCount, boolean isChecked);
    }

    public InviteRecommendContactsListAdapter(Context ctx) {
        super(ctx);
    }

    public InviteRecommendContactsListAdapter(Context ctx, List<Contacts> datas) {
        super(ctx, datas);
    }

    public InviteRecommendContactsListAdapter(Context ctx, List<Contacts> datas, List<Contacts> oldCheckedUsers) {
        super(ctx, datas);
        setOldCheckedUsers(oldCheckedUsers);
    }

    private int getPositionByData(Contacts InviteCfpInListDatas) {
        if (datas == null || datas.size() == 0) {
            return -1;
        }
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getContactId().equals(InviteCfpInListDatas.getContactId())) {
                return i;
            }
        }
        return -1;
    }

    private void initCheckedItems() {
        if (oldCheckedUsers != null && oldCheckedUsers.size() > 0) {
            oldCheckedPosition = new ArrayList<Integer>();
            for (Contacts InviteCfpInListDatas : oldCheckedUsers) {
                int position = getPositionByData(InviteCfpInListDatas);
                if (position > -1) {
                    oldCheckedPosition.add(position);
                }
            }
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.invite_contacts_list_item, null);
        convertView.findViewById(R.id.text_invite_list_Recommend).setVisibility(View.GONE);
        Contacts data = getItem(position);
        CheckBox checkBox = ViewHolder.findViewById(convertView, R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new MyCheckedListener(position));
        if (isCheckVisible) {
            if (oldCheckedPosition != null && oldCheckedPosition.contains(position)) {
                checkBox.setEnabled(false);
                checkBox.setButtonDrawable(R.drawable.img_checkbox_unable_select);
            } else {
                checkBox.setButtonDrawable(R.drawable.checkbox);
                checkBox.setEnabled(true);
            }
            setCheckBox(checkBox, position);
        } else {
            checkBox.setVisibility(View.INVISIBLE);
        }
        TextView letter = ViewHolder.findViewById(convertView, R.id.alais_scort_letter);
        letter.setText(data.getSortLetters());
        if (position > 0 && !isLetterVisible(getItem(position - 1), data)) {
            letter.setVisibility(View.GONE);
        } else {
            letter.setVisibility(View.VISIBLE);
        }

        TextView name = ViewHolder.findViewById(convertView, R.id.username);
        TextView phone = ViewHolder.findViewById(convertView, R.id.user_phone);
        name.setText(data.getName());
        phone.setText(data.getMobile());

        return convertView;
    }

    private boolean isLetterVisible(Contacts _user1, Contacts _user2) {
        return !_user1.getSortLetters().equals(_user2.getSortLetters());
    }

    private class MyCheckedListener extends CheckListener {

        public MyCheckedListener(int position) {
            super(position);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            super.onCheckedChanged(buttonView, isChecked);
            if (checkedListener != null) {
                checkedListener.onCheckedChanged(getCheckedCount(), isChecked);
            }
        }

    }

    public void setCheckVisible(boolean isCheckVisible) {
        this.isCheckVisible = isCheckVisible;
    }

    public List<Contacts> getOldCheckedUsers() {
        return oldCheckedUsers;
    }

    public void setOldCheckedUsers(List<Contacts> oldCheckedUsers) {
        this.oldCheckedUsers = oldCheckedUsers;
        initCheckedItems();
    }

    public List<Contacts> getNewCheckedUsers() {
        return newCheckedUsers;
    }

    public void setCheckedListener(OnMyCheckedListener checkedListener) {
        this.checkedListener = checkedListener;
    }

}
