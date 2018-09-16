package com.v5ent.xiubit.data;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.toobei.common.entity.Custom;
import com.toobei.common.utils.ToastUtil;
import com.v5ent.xiubit.R;

import org.xsl781.data.BaseCheckListAdapter;
import org.xsl781.ui.ViewHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 公司: tophlc
 * 类说明：新建会话时，添加客户
 * @date 2015-12-17
 */
public class ConverNewAddCustomListAdapter extends BaseCheckListAdapter<Custom> {

	private boolean isCheckVisible = true;
	private List<Custom> oldCheckedUsers;
	private List<Custom> newCheckedUsers = new ArrayList<Custom>();
	private Set<Integer> oldCheckedPosition;
	private OnMyCheckedListener checkedListener;

	public interface OnMyCheckedListener {
		void onCheckedChanged(int selectedCount, boolean isChecked);
	}

	public ConverNewAddCustomListAdapter(Context ctx) {
		super(ctx);
	}

	private int getPositionByData(Custom InviteCfpInListDatas) {
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
			if (oldCheckedPosition == null) {
				oldCheckedPosition = new HashSet<Integer>();
			}
			for (int i = 0; i < oldCheckedUsers.size(); i++) {
				int position = getPositionByData(oldCheckedUsers.get(i));
				if (position > -1) {
					oldCheckedPosition.add(position);

					oldCheckedUsers.remove(i);
					i--;
				}
			}
		}
		refreshCheckStates();
	}

	protected void refreshCheckStates() {
		if (datas == null || datas.size() == 0) {
			return;
		}
		int start = checkStates.size();
		for (int i = start; i < datas.size(); i++) {
			if (oldCheckedPosition != null && oldCheckedPosition.contains(i)) {
				checkStates.add(true);
			} else {
				checkStates.add(false);
			}
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = inflater.inflate(R.layout.item_invite_cfp_system, null);

		Custom data = getItem(position);
		//	CheckBox checkBox = ViewHolder.findViewById(convertView, R.id.checkbox);
		CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
		checkBox.setOnCheckedChangeListener(new MyCheckedListener(position));
		if (isCheckVisible) {
			setCheckBox(checkBox, position);
		} else {
			checkBox.setVisibility(View.INVISIBLE);
		}

		TextView name = ViewHolder.findViewById(convertView, R.id.text_customer_name);
		TextView phone = ViewHolder.findViewById(convertView, R.id.text_customer_phone);
		TextView nearInvest = ViewHolder.findViewById(convertView, R.id.text_customer_near_invest);
		TextView strokeCount = ViewHolder.findViewById(convertView,
				R.id.text_customer_invest_stroke_count);
		TextView totalMoney = ViewHolder.findViewById(convertView,
				R.id.text_customer_invest_total_money);
		TextView registerDate = ViewHolder.findViewById(convertView, R.id.text_customer_register);
		TextView endDate = ViewHolder.findViewById(convertView, R.id.text_customer_invest_enddate);
		ImageView imgImport = ViewHolder.findViewById(convertView, R.id.img_customer_import);

		registerDate.setText(data.getRegisterTime() + "注册");
		name.setText(data.getUserName());
		phone.setText(data.getMobile());
		if (data.getNearInvestAmt() != null && data.getNearInvestAmt().length() > 0) {
			nearInvest.setText("最近投资：" + data.getNearInvestAmt() + "元");
		} else {
			nearInvest.setText("最近投资：0.00 元");
		}
		if (data.getTotalInvestCount() != null && data.getTotalInvestCount().length() > 0) {
			strokeCount.setText("投资笔数：" + data.getTotalInvestCount());
		} else {
			strokeCount.setText("投资笔数：0");
		}

		if (data.getCurrInvestAmt() != null && data.getTotalInvestAmt().length() > 0) {
			totalMoney.setText("累计投资：" + data.getTotalInvestAmt() + "元");
		} else {
			totalMoney.setText("累计投资：0.00 元");
		}
		if (data.getNearEndDate() != null && data.getNearEndDate().length() > 0) {
			endDate.setText(data.getNearEndDate() + "有投资到期");
			endDate.setVisibility(View.VISIBLE);
		} else {
			endDate.setVisibility(View.GONE);
			endDate.setText("无投资到期");
		}
		if (data.getImportant() != null) {
			if (data.getImportant().equals("true")) {
				imgImport.setVisibility(View.VISIBLE);
			} else {
				imgImport.setVisibility(View.INVISIBLE);
			}
		} else {
			imgImport.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}

	@Override
	public void setDatas(List<Custom> datas) {
		this.datas = datas;
	}

	@Override
	public void refresh(List<Custom> datas) {
		this.datas = datas;
		initCheckedItems();
		notifyDataSetChanged();
	}

	@Override
	public void addAll(List<Custom> subDatas) {
		if (subDatas != null) {
			if (datas == null) {
				datas = new ArrayList<Custom>();
			}
			datas.addAll(subDatas);
			initCheckedItems();
			notifyDataSetChanged();
		}
	}

	private class MyCheckedListener extends CheckListener {
		int position;

		public MyCheckedListener(int position) {
			super(position);
			this.position = position;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			super.onCheckedChanged(buttonView, isChecked);
			if (isChecked) {
				Custom data = getItem(position);
				if (data.getEasemobAcct() == null || data.getEasemobAcct().length() == 0) {
					ToastUtil.showCustomToast(data.getMobile()
							+ "账号暂未激活，请联系该客户登录T呗App激活账号");
					buttonView.setChecked(false);
					setCheckState(position, false);
				//	buttonView.setChecked(false);
					return;
				}
			}
			if (checkedListener != null) {
				checkedListener.onCheckedChanged(getCheckedCount(), isChecked);
			}
		}

	}

	public void setCheckVisible(boolean isCheckVisible) {
		this.isCheckVisible = isCheckVisible;
	}

	public void setOldCheckedUsers(List<Custom> oldCheckedUsers) {
		this.oldCheckedUsers = oldCheckedUsers;
		initCheckedItems();
	}

	public List<Custom> getNewCheckedUsers() {
		return newCheckedUsers;
	}

	public void setCheckedListener(OnMyCheckedListener checkedListener) {
		this.checkedListener = checkedListener;
	}

}
