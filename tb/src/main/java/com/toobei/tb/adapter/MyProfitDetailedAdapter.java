package com.toobei.tb.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.toobei.tb.R;
import com.toobei.tb.entity.MyProfitDetailedModel;

public class MyProfitDetailedAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<MyProfitDetailedModel> list;

	public MyProfitDetailedAdapter(Context context,List<MyProfitDetailedModel> list) {
		super();
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	static class HolderView {
		TextView dateTv;
		TextView profitTv;
		TextView contentTv;
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
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		HolderView mHolderView = null;
		if (arg1 == null) {
			arg1 = inflater.inflate(R.layout.item_my_profit_detailed, null);
			mHolderView = new HolderView();
			mHolderView.dateTv = (TextView) arg1.findViewById(R.id.dateTv);
			mHolderView.profitTv = (TextView) arg1.findViewById(R.id.profitTv);
			mHolderView.contentTv = (TextView) arg1.findViewById(R.id.contentTv);
			arg1.setTag(mHolderView);
		} else {
			mHolderView = (HolderView) arg1.getTag();
		}

		mHolderView.dateTv.setText(list.get(arg0).getDate());
		mHolderView.profitTv.setText(list.get(arg0).getProfit());
		mHolderView.contentTv.setText(list.get(arg0).getContent());
		return arg1;
	}
}
