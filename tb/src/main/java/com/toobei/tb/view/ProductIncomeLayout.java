package com.toobei.tb.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toobei.common.utils.StringUtil;
import com.toobei.tb.R;

/**
 * 公司: tophlc
 * 类说明：收益视图（区间）
 * @date 2015-10-28
 */
public class ProductIncomeLayout extends LinearLayout {
	private String isFlow;//1=固定利率|2=浮动利率
	private String fixRate;//固定利率
	private String flowMaxRate;//浮动最大利率
	private String flowMinRate;//浮动最小利率
	private int textColor = ContextCompat.getColor(getContext(),R.color.text_blue_common);
	private TextView minInt, minFloat, maxInt, maxFloat;
	private ViewGroup vgMax;

	public ProductIncomeLayout(Context context) {
		super(context);
	}

	public void initFixView(String fixRate) {
		isFlow = "1";
		this.fixRate = fixRate;
		init();
	}

	public void initFlowView(String flowMinRate, String flowMaxRate) {
		isFlow = "2";
		this.flowMinRate = flowMinRate;
		this.flowMaxRate = flowMaxRate;
		init();
	}

	private void init() {
		removeAllViews();
		View view = LayoutInflater.from(getContext()).inflate(R.layout.product_income_layout, null,
				false);
		minInt = (TextView) view.findViewById(R.id.income_min_int);
		minFloat = (TextView) view.findViewById(R.id.income_min_float);
		maxInt = (TextView) view.findViewById(R.id.income_max_int);
		maxFloat = (TextView) view.findViewById(R.id.income_max_float);
		vgMax = (ViewGroup) view.findViewById(R.id.income_max_rl);
		System.out.println("flowMinRate = " + flowMinRate + "  flowMaxRate = " + flowMaxRate);
		if (isFlow.equals("1")) {//1=固定利率
			String[] mins = StringUtil.getIntAndFloat(fixRate);
			minInt.setText(mins[0] + ".");
			if(mins.length > 1){
				minFloat.setText(mins[1] + "%");
			}
			vgMax.setVisibility(View.GONE);
		} else {
			String[] mins = StringUtil.getIntAndFloat(flowMinRate);
			minInt.setText(mins[0] + ".");
			if(mins.length > 1){
				minFloat.setText(mins[1] + "%");
			}

			String[] maxs = StringUtil.getIntAndFloat(flowMaxRate);
			maxInt.setText(maxs[0] + ".");
			if(maxs.length > 1){
				maxFloat.setText(maxs[1] + "%");
			}
		}
		addView(view);

	}

	public void setTextColor(int textColor) {
		this.textColor = ContextCompat.getColor(getContext(),textColor);
		if (minInt != null) {
			minInt.setTextColor(this.textColor);
			minFloat.setTextColor(this.textColor);
			maxInt.setTextColor(this.textColor);
			maxFloat.setTextColor(this.textColor);
		}
	}

}
