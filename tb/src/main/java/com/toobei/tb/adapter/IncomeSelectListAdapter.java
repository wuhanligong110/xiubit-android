package com.toobei.tb.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.toobei.tb.R;
import com.toobei.tb.entity.IncomeType;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.ui.ViewHolder;

import java.util.List;

public class IncomeSelectListAdapter extends BaseListAdapter<IncomeType> {
	private IncomeType selected;

	public IncomeSelectListAdapter(Context ctx) {
		super(ctx);
	}

	public IncomeSelectListAdapter(Context ctx, List<IncomeType> datas) {
		super(ctx, datas);
	}

	public IncomeSelectListAdapter(Context ctx, List<IncomeType> datas, IncomeType selected) {
		super(ctx, datas);
		this.selected = selected;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.category_select_list_item, null);
		}
		IncomeType item = getItem(position);
		TextView textName = ViewHolder.findViewById(convertView, R.id.text_category_item_name);
		textName.setText(item.getProfitType());
		ImageView imgSelect = ViewHolder.findViewById(convertView, R.id.img_category_item_selected);
		
		if (item.getProfitTypeId().equals(selected.getProfitTypeId())) {
			imgSelect.setVisibility(View.VISIBLE);
		} else {
			imgSelect.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}

	public IncomeType getSelected() {
		return selected;
	}

	public void setSelected(IncomeType selected) {
		this.selected = selected;
	}

}
