package com.toobei.common.data;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.entity.AccountType;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.ui.ViewHolder;

import java.util.List;

public class CategorySelectListAdapter extends BaseListAdapter<AccountType> {
	private AccountType selected;

	public CategorySelectListAdapter(Context ctx) {
		super(ctx);
	}

	public CategorySelectListAdapter(Context ctx, List<AccountType> datas) {
		super(ctx, datas);
	}

	public CategorySelectListAdapter(Context ctx, List<AccountType> datas, AccountType selected) {
		super(ctx, datas);
		this.selected = selected;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.category_select_list_item, null);
		}
		AccountType item = getItem(position);
		TextView textName = ViewHolder.findViewById(convertView, R.id.text_category_item_name);
		textName.setText(item.getTypeName());
		ImageView imgSelect = ViewHolder.findViewById(convertView, R.id.img_category_item_selected);
		if (item.getTypeValue().equals(selected.getTypeValue())) {
			imgSelect.setVisibility(View.VISIBLE);
			textName.setTextColor(ContextCompat.getColor(ctx,R.color.text_blue_common));
		} else {
			textName.setTextColor(ContextCompat.getColor(ctx,R.color.text_black_common));
			imgSelect.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}

	public AccountType getSelected() {
		return selected;
	}

	public void setSelected(AccountType selected) {
		this.selected = selected;
	}

}
