package com.toobei.tb.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toobei.tb.R;
import com.toobei.tb.entity.IncomeDetail;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.ui.ViewHolder;

import java.util.List;

public class IncomeDetailAdapter extends BaseListAdapter<IncomeDetail> {

	public IncomeDetailAdapter(Context ctx, List<IncomeDetail> datas) {
		super(ctx, datas);
		this.datas = datas;
	}

	public IncomeDetailAdapter(Context ctx) {
		super(ctx);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(ctx, R.layout.layout_account_detail_list_item, null);
		}
		final IncomeDetail data = getItem(position);
		if(data == null){
			convertView.setVisibility(View.INVISIBLE);
			return convertView;
		}
		convertView.setVisibility(View.VISIBLE);
		TextView name = ViewHolder.findViewById(convertView, R.id.text_customer_name);
		TextView time = ViewHolder.findViewById(convertView, R.id.text_time);
		TextView count = ViewHolder.findViewById(convertView, R.id.text_account_count);
		TextView content = ViewHolder.findViewById(convertView, R.id.text_account_content);
		name.setText(data.getProfitType());
		time.setText(data.getTime());
		count.setText(data.getAmt());
		content.setText(data.getContent());

		return convertView;
	}

}
