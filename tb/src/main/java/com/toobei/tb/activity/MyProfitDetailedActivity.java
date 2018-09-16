package com.toobei.tb.activity;

import java.util.ArrayList;
import java.util.List;

import org.xsl781.ui.xlist.XListView;
import org.xsl781.ui.xlist.XListView.IXListViewListener;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.toobei.common.view.ListBlankLayout;
import com.toobei.tb.MyApp;
import com.toobei.tb.MyNetworkBaseActivity;
import com.toobei.tb.R;
import com.toobei.tb.adapter.MyProfitDetailedAdapter;
import com.toobei.tb.entity.MyProfitDetailedEntity;
import com.toobei.tb.entity.MyProfitDetailedListDatasData;
import com.toobei.tb.entity.MyProfitDetailedModel;
import com.toobei.tb.entity.MyProfitModel;

/**
 * 公司: tophlc
 * 类说明：收益统计详情
 * @date 2016-3-17
 */
public class MyProfitDetailedActivity extends MyNetworkBaseActivity<MyProfitDetailedEntity>
		implements IXListViewListener {

	private XListView xListView;
	private ListBlankLayout listBlankLayout;
	private Context context;
	private TextView totalAmountTv;
	private MyProfitDetailedAdapter adapter;
	private int pageIndex = 1;
	private final int pageSize = 10;
	private String key;
	private String profitType;
	private List<MyProfitDetailedModel> AllList = new ArrayList<MyProfitDetailedModel>();
	private MyProfitModel model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView();
	}

	private void initView() {
		context = this;

		headerLayout.showTitle(profitType);
		headerLayout.showLeftBackButton();

		totalAmountTv = (TextView) findViewById(R.id.totalAmountTv);
		listBlankLayout = (ListBlankLayout) findViewById(R.id.list_blank_layout);
		xListView = (XListView) listBlankLayout
				.initContentView(R.layout.list_blank_xlistview_layout);
		xListView.setXListViewListener(this);
		xListView.setAutoLoadMore(false);

		totalAmountTv.setText(model.getProfit());
		loadData(true);
	}

	@Override
	public void onRefresh() {
		pageIndex = 1;
		AllList.clear();
		loadData(false);
	}

	@Override
	public void onLoadMore() {
		loadData(false);
	}

	public void onLoad() {
		if (xListView != null) {
			xListView.stopRefresh();
			xListView.stopLoadMore();
		}
	}
	
	private void refreshView(MyProfitDetailedListDatasData model){
		List<MyProfitDetailedModel> list = model.getDatas();
		AllList.addAll(list);
		if (AllList != null && AllList.size() > 0) {
			listBlankLayout.showContentView();
			if (adapter == null) {
				adapter = new MyProfitDetailedAdapter(context, AllList);
				xListView.setAdapter(adapter);
			} else {
				adapter.notifyDataSetChanged();
			}
			pageIndex++;
		} else {
			listBlankLayout.showBlank(R.string.blank_list_text_myaccount);
		}

		if (list.size() >= 0 && list.size() < pageSize) {
			xListView.setPullLoadEnable(false);
		} else {
			xListView.setPullLoadEnable(true);
		}
	}

	@Override
	protected void onInitParamBeforeLoadData() {
		model = (MyProfitModel) getIntent().getSerializableExtra("MyProfitModel");
		profitType = model.getProfitType();
		key = model.getProfitTypeId();
	}
	
	@Override
	protected int onGetContentViewLayout() {
		return R.layout.activity_my_profit_detailed;
	}

	@Override
	protected MyProfitDetailedEntity onLoadDataInBack() throws Exception {
		return MyApp.getInstance().getHttpService()
				.MyInvestorProfitDetailed(MyApp.getInstance().getLoginService().token, key,pageIndex, pageSize);
	}

	@Override
	protected void onRefreshDataView(MyProfitDetailedEntity data) {
		refreshView(data.getData());
	}

	@Override
	protected void onRefreshDataViewCompleted() {
		onLoad();
	}
}
