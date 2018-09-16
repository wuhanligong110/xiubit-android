package com.toobei.tb.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.toobei.common.utils.PhotoUtil;
import com.toobei.tb.R;
import com.toobei.tb.entity.ActivityDetail;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.ui.ViewHolder;

import java.util.List;

public class InvestActivityListAdapter extends BaseListAdapter<ActivityDetail> {

	public InvestActivityListAdapter(Context ctx, List<ActivityDetail> datas) {
		super(ctx, datas);
		this.datas = datas;
	}

	public InvestActivityListAdapter(Context ctx) {
		super(ctx);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(ctx, R.layout.layout_invest_activity_list_item, null);
		}
		final ActivityDetail data = getItem(position);
		View rootView = ViewHolder.findViewById(convertView, R.id.invest_activity_list_item_bg_ll);
		TextView date = ViewHolder.findViewById(convertView, R.id.text_invest_date);
		TextView name = ViewHolder.findViewById(convertView, R.id.text_invest_name);
		ImageView pic = ViewHolder.findViewById(convertView, R.id.img_invest_activity_pic);
		ImageView outDate = ViewHolder.findViewById(convertView, R.id.img_invest_activity_outdate);

		date.setText(data.getStartDate());
		name.setText(data.getActivityName());
		ImageLoader.getInstance()
				.displayImage(data.getActivityImg(), pic, PhotoUtil.normalImageOptions);
//		if (data.getEndFlag() != null && data.getEndFlag().equals("0")) {
//			outDate.setVisibility(View.INVISIBLE);
//			rootView.setBackgroundResource(R.drawable.img_invest_activity_bg);
//		} else {
//			rootView.setBackgroundResource(R.drawable.img_invest_activity_bg_outdate);
//			outDate.setVisibility(View.VISIBLE);
//		}

		return convertView;
	}

}
