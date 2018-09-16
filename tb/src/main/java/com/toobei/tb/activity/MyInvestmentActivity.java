package com.toobei.tb.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.toobei.common.utils.StringUtil;
import com.toobei.common.view.ListBlankLayout;
import com.toobei.tb.MyApp;
import com.toobei.tb.MyNetworkBaseActivity;
import com.toobei.tb.R;
import com.toobei.tb.adapter.MyInvestmentAdapter;
import com.toobei.tb.entity.MyInvestDatasData;
import com.toobei.tb.entity.MyInvestEntity;
import com.toobei.tb.entity.MyInvestModel;
import com.umeng.analytics.MobclickAgent;

import org.xsl781.ui.xlist.XListView;
import org.xsl781.ui.xlist.XListView.IXListViewListener;

import java.util.ArrayList;
import java.util.List;

public class MyInvestmentActivity extends MyNetworkBaseActivity<MyInvestEntity> implements
		OnItemClickListener, IXListViewListener {

	private Context context;
	private TextView totalInvestAmountTv;
	private XListView xListView;
	private ListBlankLayout listBlankLayout;
	private MyInvestmentAdapter adapter;
	private List<MyInvestModel> AllList = new ArrayList<MyInvestModel>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView();
	}

	private void initView() {
		context = this;
		headerLayout.showTitle("我的投资");
		headerLayout.showLeftBackButton();

		totalInvestAmountTv = (TextView) findViewById(R.id.totalInvestAmountTv);
		listBlankLayout = (ListBlankLayout) findViewById(R.id.list_blank_layout);
		xListView = (XListView) listBlankLayout
				.initContentView(R.layout.list_blank_xlistview_layout);
		xListView.setXListViewListener(this);
		xListView.setAutoLoadMore(false);
		xListView.setPullLoadEnable(false);
		xListView.setOnItemClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onEvent(MyInvestmentActivity.this,"my_investment");
	}

	public void onLoad() {
		if (xListView != null) {
			xListView.stopRefresh();
			xListView.stopLoadMore();
		}
	}

	private void refreshView(MyInvestDatasData model) {
		String totalInvestAmount = model.getTotalInvestAmount();
		totalInvestAmountTv.setText(StringUtil.formatNumber(totalInvestAmount));
		List<MyInvestModel> list = model.getDatas();
		AllList.addAll(list);
		if (AllList != null || AllList.size() > 0) {
			listBlankLayout.showContentView();
			if (adapter == null) {
				adapter = new MyInvestmentAdapter(context, AllList);
				xListView.setAdapter(adapter);
			} else {
				adapter.notifyDataSetChanged();
			}
		} else {
			listBlankLayout.showBlank(R.string.blank_list_text_myaccount);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String productType = AllList.get(arg2 - 1).getProductType();
		String openH5LinkUrl = AllList.get(arg2 - 1).getOpenH5LinkUrl();
		if (!TextUtils.isEmpty(productType)) {
			if (productType.equals("2002")) {

			} else {
				String titleName = "";
				if (productType.equals("2001")) {
					//活期产品
					titleName = "活期产品";
				} else if (productType.equals("2003")) {
					//浮动收益产品
					titleName = "浮动收益产品";
				} else if (productType.equals("2006")) {
					//基金收益产品
					titleName = "基金收益产品";
				}
				WebActivityCommon.showThisActivity(ctx, openH5LinkUrl, titleName);
			}
		}

	}

	@Override
	public void onRefresh() {
		AllList.clear();
		loadData(false);
	}

	@Override
	public void onLoadMore() {

	}

	@Override
	protected int onGetContentViewLayout() {
		return R.layout.activity_my_investment;
	}

	@Override
	protected MyInvestEntity onLoadDataInBack() throws Exception {
		return MyApp.getInstance().getHttpService()
				.MyInvestHomepage(MyApp.getInstance().getLoginService().token);
	}

	@Override
	protected void onRefreshDataView(MyInvestEntity data) {
		refreshView(data.getData());
	}

	@Override
	protected void onRefreshDataViewCompleted() {
		onLoad();
	}
}
