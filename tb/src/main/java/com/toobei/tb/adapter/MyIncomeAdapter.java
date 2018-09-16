package com.toobei.tb.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.toobei.tb.R;
import com.toobei.tb.entity.IncomeDetail;
import com.toobei.tb.view.HorizontalRatioView;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.ui.ViewHolder;

import java.util.List;

public class MyIncomeAdapter extends BaseListAdapter<IncomeDetail> {

	private View headView;

	public MyIncomeAdapter(Context ctx) {
		super(ctx);
	}

	public MyIncomeAdapter(Context ctx, List<IncomeDetail> datas) {
		super(ctx, datas);
	}

	@Override
	public long getItemId(int position) {
		if (position == 0)
			return 0;
		else
			return position--;
	}

	@Override
	public int getCount() {
		if (datas == null || datas.size() == 0) {
			return 0;
		}
		return datas.size() + 1;
	}

	@Override
	public int getItemViewType(int position) {
		return position > 0 ? 0 : 1;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		if (position == 0) {
			return getMyincomeHeadView();
		} else {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.layout_myincome_listview_item, null);
				//	headView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT,
				//		PixelUtil.dip2px(ctx, 30)));
			}
			HorizontalRatioView view = ViewHolder.findViewById(convertView,
					R.id.myincome_horizontal_view);
			view.initParam(20, position * 2, "2015-09-" + position);

			return convertView;
		}
	}

	private View getMyincomeHeadView() {
		if (headView == null) {
			headView = inflater.inflate(R.layout.layout_myincome_listview_headview, null);
			//float i = ctx.getResources().getDimension(R.dimen.mine_face_ll_height);
			//	headView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT,
			//			PixelUtil.dip2px(ctx, 120)));
			//	headView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT,
			//			PixelUtil.dip2px(ctx, 120)));
			RelativeLayout chartRl = (RelativeLayout) headView.findViewById(R.id.rl_chart);
//			chartRl.addView(new DountChart01View(ctx), LayoutParams.MATCH_PARENT,
//					LayoutParams.MATCH_PARENT);
		}

		return headView;
	}

}
