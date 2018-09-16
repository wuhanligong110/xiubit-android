package com.toobei.tb.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.toobei.tb.R;
import com.toobei.tb.entity.ProductBuyRecurdsModel;

public class ProductBuyRecurdsAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<ProductBuyRecurdsModel> list;

	public ProductBuyRecurdsAdapter(Context context, List<ProductBuyRecurdsModel> list) {
		super();
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	static class HolderView {
		TextView phoneTv;
		TextView moneyTv;
		TextView timeTv;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		HolderView mHolderView = null;
		if (arg1 == null) {
			arg1 = inflater.inflate(R.layout.item_product_buyrecurds, null);
			mHolderView = new HolderView();
			mHolderView.phoneTv = (TextView) arg1.findViewById(R.id.phoneTv);
			mHolderView.moneyTv = (TextView) arg1.findViewById(R.id.moneyTv);
			mHolderView.timeTv = (TextView) arg1.findViewById(R.id.timeTv);
			arg1.setTag(mHolderView);
		} else {
			mHolderView = (HolderView) arg1.getTag();
		}

		String mobile = list.get(arg0).getMobile();
		String investAmount = list.get(arg0).getInvestAmount();
		String createDate = list.get(arg0).getCreateDate();
		mHolderView.phoneTv.setText(mobile);
		mHolderView.moneyTv.setText(investAmount);
		mHolderView.timeTv.setText(com.toobei.common.utils.TimeUtils.millisecs2Date(createDate+"", "yyyy-MM-dd hh:mm") );
		return arg1;
	}
}
