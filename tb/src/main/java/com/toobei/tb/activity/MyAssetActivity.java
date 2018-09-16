package com.toobei.tb.activity;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.toobei.common.utils.StringUtil;
import com.toobei.tb.MyApp;
import com.toobei.tb.MyNetworkBaseActivity;
import com.toobei.tb.R;
import com.toobei.tb.entity.MyAssetEntity;
import com.toobei.tb.entity.MyAssetModel;
import com.umeng.analytics.MobclickAgent;

/**
 * 公司: tophlc
 * 类说明：资产统计
 * @date 2016-3-17
 */
public class MyAssetActivity extends MyNetworkBaseActivity<MyAssetEntity>{

	private TextView totalAmountTv;
	private TextView accountBalanceTv;
	private TextView currentAmountTv;
	private TextView fixedAmountTv;
	private TextView floatAmountTv;
	private PullToRefreshScrollView  mPullRefreshScrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initView();
	}
	
	private void initView() {
		headerLayout.showTitle("资产统计");
		headerLayout.showLeftBackButton();
		
		totalAmountTv = (TextView) findViewById(R.id.totalAmountTv);
		accountBalanceTv = (TextView) findViewById(R.id.accountBalanceTv);
		currentAmountTv = (TextView) findViewById(R.id.currentAmountTv);
		fixedAmountTv = (TextView) findViewById(R.id.fixedAmountTv);
		floatAmountTv = (TextView) findViewById(R.id.floatAmountTv);
		
		initScrollView();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onEvent(MyAssetActivity.this,"my_total_assets");
	}
	
	/*
	 * 初始化下拉刷新组建
	 */
	private void initScrollView() {
		mPullRefreshScrollView = (PullToRefreshScrollView)findViewById(R.id.pull_refresh_scrollview);
		mPullRefreshScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				loadData(false);
			}
		});
		mPullRefreshScrollView.setMode(Mode.PULL_FROM_START);
		ScrollView mScrollView = mPullRefreshScrollView.getRefreshableView();
		mScrollView.setVerticalScrollBarEnabled(false);
	}
	
	private void refreshView(MyAssetModel model){
		String totalAmount = model.getTotalAmount(); //总资产(元)
		String accountBalance = model.getAccountBalance();//账户余额
		String currentAmount = model.getCurrentAmount();//活期产品总额
		String fixedAmount = model.getFixedAmount();//固定收益产品总额
		String floatAmount = model.getFloatAmount();//浮动收益产品总额
		
		totalAmountTv.setText(StringUtil.formatNumber(totalAmount));
		accountBalanceTv.setText(StringUtil.formatNumber(accountBalance)+"元");
		currentAmountTv.setText(StringUtil.formatNumber(currentAmount)+"元");
		fixedAmountTv.setText(StringUtil.formatNumber(fixedAmount)+"元");
		floatAmountTv.setText(StringUtil.formatNumber(floatAmount)+"元");
	}

	@Override
	protected int onGetContentViewLayout() {
		return R.layout.activity_my_asset;
	}

	@Override
	protected MyAssetEntity onLoadDataInBack() throws Exception {
		return MyApp.getInstance().getHttpService()
				.userMyAsset(MyApp.getInstance().getLoginService().token);
	}

	@Override
	protected void onRefreshDataView(MyAssetEntity data) {
		refreshView(data.getData());
	}
	
	@Override
	protected void onRefreshDataViewCompleted() {
		mPullRefreshScrollView.onRefreshComplete();
	}
}
