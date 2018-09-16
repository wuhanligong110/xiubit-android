package com.toobei.tb.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.toobei.common.utils.StringUtil;
import com.toobei.tb.R;
import com.toobei.tb.entity.MyInvestModel;

public class MyInvestmentAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<MyInvestModel> list;

	public MyInvestmentAdapter(Context context, List<MyInvestModel> list) {
		super();
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	static class HolderView {
		TextView nameTv;
		TextView investAmountTv;
		TextView profitTv;
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
		/*
		 * 2001 活期产品
		 * 2002固定收益产品
		 * 2003浮动收益产品
		 * 2004活动奖励
		 * 2005邀请红包
		 * 2006基金收益产品
		 * 2999其他
		 */
		HolderView mHolderView = null;
		if (arg1 == null) {
			arg1 = inflater.inflate(R.layout.item_my_investment, null);
			mHolderView = new HolderView();

			mHolderView.nameTv = (TextView) arg1.findViewById(R.id.nameTv);
			mHolderView.investAmountTv = (TextView) arg1.findViewById(R.id.investAmountTv);
			mHolderView.profitTv = (TextView) arg1.findViewById(R.id.profitTv);
			arg1.setTag(mHolderView);
		} else {
			mHolderView = (HolderView) arg1.getTag();
		}
		String productType = list.get(arg0).getProductType();
		if (productType.equals("2001")) {
			mHolderView.nameTv.setText("活期产品");
		}else if (productType.equals("2002")) {
			mHolderView.nameTv.setText("固定收益产品");
		}else if (productType.equals("2003")) {
			mHolderView.nameTv.setText("浮动收益产品");
		}else if (productType.equals("2006")) {
			mHolderView.nameTv.setText("基金收益产品");
		}
		String investAmount = list.get(arg0).getInvestAmount();
		String profit = list.get(arg0).getProfit();

		mHolderView.investAmountTv.setText(StringUtil.formatNumber(investAmount));
		mHolderView.profitTv.setText(StringUtil.formatNumber(profit));

		return arg1;
	}
}
