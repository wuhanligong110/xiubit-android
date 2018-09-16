package com.toobei.common.data;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.entity.MsgDetail;

import org.xsl781.data.BaseCheckListAdapter;
import org.xsl781.ui.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MineMsgPersonListAdapter extends BaseCheckListAdapter<MsgDetail> {

	private boolean isCheckVisible = false;
	private List<MsgDetail> oldCheckedUsers;
	private List<MsgDetail> newCheckedUsers = new ArrayList<MsgDetail>();
	private List<Integer> oldCheckedPosition;
	private OnMyCheckedListener checkedListener;
	private int drawableId;

	public interface OnMyCheckedListener {
		void onCheckedChanged(int position, boolean isChecked);
	}

	public MineMsgPersonListAdapter(Context ctx,int drawableId) {
		super(ctx);
		this.drawableId = drawableId;
	}

	public MineMsgPersonListAdapter(Context ctx, List<MsgDetail> datas) {
		super(ctx, datas);
	}

	public MineMsgPersonListAdapter(Context ctx, List<MsgDetail> datas,
			List<MsgDetail> oldCheckedUsers) {
		super(ctx, datas);
		setOldCheckedUsers(oldCheckedUsers);
	}

	private int getPositionByData(MsgDetail MsgDetail) {
		if (datas == null || datas.size() == 0) {
			return -1;
		}
		for (int i = 0; i < datas.size(); i++) {
			//			if (datas.get(i).getObjectId().equals(MsgDetail.getObjectId())) {
			//				return i;
			//			}
		}
		return -1;
	}

	private void initCheckedItems() {
		if (oldCheckedUsers != null && oldCheckedUsers.size() > 0) {
			oldCheckedPosition = new ArrayList<Integer>();
			for (MsgDetail MsgDetail : oldCheckedUsers) {
				int position = getPositionByData(MsgDetail);
				if (position > -1) {
					oldCheckedPosition.add(position);
				}
			}
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = inflater.inflate(R.layout.mine_msg_person_delete_list_item, null);
		MsgDetail data = getItem(position);
		CheckBox checkBox = ViewHolder.findViewById(convertView, R.id.checkbox);
		checkBox.setButtonDrawable(drawableId);
		checkBox.setOnCheckedChangeListener(new MyCheckedListener(position));
		if (isCheckVisible) {
			if (oldCheckedPosition != null && oldCheckedPosition.contains(position)) {
				checkBox.setEnabled(false);
				checkBox.setButtonDrawable(R.drawable.img_checkbox_unable_select);
			} else {
				checkBox.setButtonDrawable(drawableId);
				checkBox.setEnabled(true);
			}
			setCheckBox(checkBox, position);
			checkBox.setVisibility(View.VISIBLE);
		} else {
			checkBox.setVisibility(View.GONE);
		}

		TextView content = ViewHolder.findViewById(convertView, R.id.text_content);
		TextView textTime = ViewHolder.findViewById(convertView, R.id.text_time);
		LinearLayout itemLl = ViewHolder.findViewById(convertView, R.id.msg_person_del_item_ll);
		String contentStr = data.getContent();
		//android 4.4.4 下面代码无效？
//		float length = Float.valueOf(contentStr.length()) / 20;
//		itemLl.getLayoutParams().height = PixelUtil.dip2px(ctx, 70 + length * 20);
		content.setText(contentStr);
		textTime.setText(data.getStartTime());
		View rootView = ViewHolder.findViewById(convertView, R.id.msg_person_del_item_ll);
		if (data.getRead() != null && data.getRead().equals("1")) {//是否已读 1已读,0未读
			//rootView.setBackgroundColor(ContextCompat.getColor(ctx,R.color.item_clicked_bg));
			content.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_common));
			textTime.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_common));

		} else {
			rootView.setBackgroundResource(R.drawable.item_click_bg);
			textTime.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_common));
			content.setTextColor(ContextCompat.getColor(ctx,R.color.text_black_common));
		}
		return convertView;
	}

	private class MyCheckedListener extends CheckListener {

		public MyCheckedListener(int position) {
			super(position);
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			super.onCheckedChanged(buttonView, isChecked);
			if (checkedListener != null) {
				checkedListener.onCheckedChanged(position, isChecked);
			}
		}

	}

	public void setCheckVisible(boolean isCheckVisible) {
		this.isCheckVisible = isCheckVisible;
		notifyDataSetInvalidated();
	}

	public List<MsgDetail> getOldCheckedUsers() {
		return oldCheckedUsers;
	}

	public void setOldCheckedUsers(List<MsgDetail> oldCheckedUsers) {
		this.oldCheckedUsers = oldCheckedUsers;
		initCheckedItems();
	}

	public List<MsgDetail> getNewCheckedUsers() {
		return newCheckedUsers;
	}

	public void setCheckedListener(OnMyCheckedListener checkedListener) {
		this.checkedListener = checkedListener;
	}

	/**
	 * 功能：设置全选和全不选
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
