package com.toobei.tb.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.toobei.tb.entity.MyProfitDatasData;
import com.toobei.tb.entity.MyProfitEntity;
import com.toobei.tb.entity.MyProfitModel;
import com.umeng.analytics.MobclickAgent;

import java.util.List;
/**
 * 公司: tophlc
 * 类说明：收益统计
 * @date 2016-3-17
 */
public class MyProfitActivity extends MyNetworkBaseActivity<MyProfitEntity>{

	private LinearLayout fatherLl;
	private TextView totalAmountTv;
	private PullToRefreshScrollView  mPullRefreshScrollView;
	 private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initView();
	}
	
	private void initView() {
		context = this;
		headerLayout.showTitle("收益统计");
		headerLayout.showLeftBackButton();
		
		fatherLl = (LinearLayout) findViewById(R.id.fatherLl);
		totalAmountTv = (TextView) findViewById(R.id.totalAmountTv);
		
		initScrollView();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onEvent(MyProfitActivity.this,"my_total_earnings");
	}
	/*
	 * 初始化下拉刷新组建
	 */
	private void initScrollView() {
		mPullRefreshScrollView = (PullToRefreshScrollView)findViewById(R.id.pull_refresh_scrollview);
		mPullRefreshScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				fatherLl.removeAllViews();
				loadData(false);
			}
		});
		mPullRefreshScrollView.setMode(Mode.PULL_FROM_START);
		ScrollView mScrollView = mPullRefreshScrollView.getRefreshableView();
		mScrollView.setVerticalScrollBarEnabled(false);
	}

	/*
	 * 动态加载页面
	 */
	@SuppressLint("NewApi") 
	private void DynamicLoadingView(final List<MyProfitModel> list){
		for(int i=0;i<list.size();i++){
			final int indext = i;
			RelativeLayout re = new RelativeLayout(context);
			RelativeLayout.LayoutParams LP_FW = new RelativeLayout.LayoutParams(  
	                LinearLayout.LayoutParams.FILL_PARENT, 150); 
			re.setPadding(36, 0, 36, 0);
			re.setLayoutParams(LP_FW);
			re.setBackgroundResource(R.drawable.item_click_bg);
			re.setClickable(true);
			
			TextView profitNameTv = new TextView(context);
			RelativeLayout.LayoutParams LP_WW = new RelativeLayout.LayoutParams(  
					RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); 
			LP_WW.addRule(RelativeLayout.CENTER_VERTICAL);
			profitNameTv.setTextSize(16);
			profitNameTv.setTextColor(ContextCompat.getColor(ctx,R.color.BLACK));
			profitNameTv.setLayoutParams(LP_WW);
			profitNameTv.setText(list.get(i).getProfitType());
			re.addView(profitNameTv);
			
			TextView profitTv = new TextView(context);
			RelativeLayout.LayoutParams LP_WW2 = new RelativeLayout.LayoutParams(  
					RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); 
			LP_WW2.addRule(RelativeLayout.CENTER_VERTICAL);
			LP_WW2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			LP_WW2.rightMargin = 55;
			profitTv.setTextSize(16);
			profitTv.setTextColor(ContextCompat.getColor(ctx,R.color.btn_common_orange));
			profitTv.setLayoutParams(LP_WW2);
			profitTv.setText(StringUtil.formatNumber(list.get(i).getProfit())+"元");
			re.addView(profitTv);
			
			ImageView toRightIv = new ImageView(context);
			RelativeLayout.LayoutParams LP_WW3 = new RelativeLayout.LayoutParams(  
					RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); 
			LP_WW3.addRule(RelativeLayout.CENTER_VERTICAL);
			LP_WW3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			LP_WW3.rightMargin = 8;
			toRightIv.setImageResource(R.drawable.img_arrow_right);
			toRightIv.setLayoutParams(LP_WW3);
			re.addView(toRightIv);
			
			ImageView iv = new ImageView(context);
			RelativeLayout.LayoutParams LP_WW4 = new RelativeLayout.LayoutParams(  
					RelativeLayout.LayoutParams.FILL_PARENT, 1); 
			LP_WW4.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			iv.setLayoutParams(LP_WW4);
			iv.setBackgroundColor(ContextCompat.getColor(ctx,R.color.line_common));
			
			re.setTag(list.get(i).getProfitTypeId());
			re.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(MyProfitActivity.this,MyProfitDetailedActivity.class);
					intent.putExtra("MyProfitModel", list.get(indext));
					startActivity(intent);
				}
			});
			
			fatherLl.addView(re);
			fatherLl.addView(iv);
		}
	}
	
	private void refreshView(MyProfitDatasData model){
		String totalProfit = model.getTotalProfit();
		List<MyProfitModel> list = model.getDatas();
		
		totalAmountTv.setText(StringUtil.formatNumber(totalProfit));
		DynamicLoadingView(list);
	}

	@Override
	protected int onGetContentViewLayout() {
		return R.layout.activity_my_profit;
	}

	@Override
	protected MyProfitEntity onLoadDataInBack() throws Exception {
		return MyApp.getInstance().getHttpService()
				.userMyProfit(MyApp.getInstance().getLoginService().token);
	}

	@Override
	protected void onRefreshDataView(MyProfitEntity data) {
		refreshView(data.getData());
	}
	
	@Override
	protected void onRefreshDataViewCompleted() {
		mPullRefreshScrollView.onRefreshComplete();
	}
}
