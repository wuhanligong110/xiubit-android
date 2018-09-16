package com.v5ent.xiubit.data;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.v5ent.xiubit.entity.RecommendProductData;

import org.xsl781.data.BaseCheckListAdapter;
import org.xsl781.ui.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司: tophlc
 * 类说明：推荐产品Adapter
 *
 * @date 2015-12-28
 */
public class RecommendProductListAdapter extends BaseCheckListAdapter<RecommendProductData> {

    private boolean isCheckVisible = true;
    private List<RecommendProductData> oldCheckedUsers;
    private List<RecommendProductData> newCheckedUsers = new ArrayList<RecommendProductData>();
    private List<Integer> oldCheckedPosition;
    private OnMyCheckedListener checkedListener;

    public interface OnMyCheckedListener {
        void onCheckedChanged(int selectedCount, boolean isChecked);
    }

    public RecommendProductListAdapter(Context ctx) {
        super(ctx);
    }

    public RecommendProductListAdapter(Context ctx, List<RecommendProductData> datas) {
        super(ctx, datas);
    }

    public RecommendProductListAdapter(Context ctx, List<RecommendProductData> datas, List<RecommendProductData> oldCheckedUsers) {
        super(ctx, datas);
        setOldCheckedUsers(oldCheckedUsers);
    }

    private int getPositionByData(RecommendProductData InviteCfpInListDatas) {
        if (datas == null || datas.size() == 0) {
            return -1;
        }
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getUserId().equals(InviteCfpInListDatas.getUserId())) {
                return i;
            }
        }
        return -1;
    }

    private void initCheckedItems() {
        if (oldCheckedUsers != null && oldCheckedUsers.size() > 0) {
            oldCheckedPosition = new ArrayList<Integer>();
            for (RecommendProductData InviteCfpInListDatas : oldCheckedUsers) {
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

        RecommendProductData data = getItem(position);
        final CheckBox checkBox = ViewHolder.findViewById(convertView, R.id.checkbox);
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
        TextView textRecommend = ViewHolder.findViewById(convertView, R.id.text_invite_list_Recommend); //显示已推荐
        textRecommend.setVisibility("0".equals(data.getIfRecommend())?View.GONE:View.VISIBLE);  //ifRecommend	是否理财师推荐	number	0-否 其他-是
        String userName = data.getUserName();
        userName= TextUtil.isEmpty(userName.trim())?"未认证":userName;
        name.setText(userName);
        phone.setText(data.getMobile());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(!checkBox.isChecked());
            }
        });

        return convertView;
    }

    private boolean isLetterVisible(RecommendProductData _user1, RecommendProductData _user2) {
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

    public List<RecommendProductData> getOldCheckedUsers() {
        return oldCheckedUsers;
    }

    public void setOldCheckedUsers(List<RecommendProductData> oldCheckedUsers) {
        this.oldCheckedUsers = oldCheckedUsers;
        initCheckedItems();
    }

    public List<RecommendProductData> getNewCheckedUsers() {
        return newCheckedUsers;
    }

    public void setCheckedListener(OnMyCheckedListener checkedListener) {
        this.checkedListener = checkedListener;
    }

    @Override
    protected void initCheckStates(List<RecommendProductData> datas) {
        if (datas == null) {
            return;
        }
        checkStates.clear();

        for (int i = 0; i < datas.size(); i++) {
            String ifRecommend = datas.get(i).getIfRecommend();
            checkStates.add("0".equals(ifRecommend)?defaultState:true);
        }
    }


    /**
     * 功能：设置全选和全不选
     *
     * @param checked
     */
    public void setAllItemChecked(boolean checked) {
        checkStates.clear();
        for (int i = 0; i < getDatas().size(); i++) {
            checkStates.add(checked);
        }
        notifyDataSetChanged();
    }

}
