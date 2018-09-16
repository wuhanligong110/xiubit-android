package com.toobei.common.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopNetworkBaseActivity;
import com.toobei.common.data.WithdrawRecordAdapter;
import com.toobei.common.entity.WithdrawRecord;
import com.toobei.common.entity.WithdrawRecordDatasDataEntity;
import com.toobei.common.entity.WithdrawSummaryEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.view.ListBlankLayout;

import org.xsl781.ui.xlist.XListView;
import org.xsl781.ui.xlist.XListView.IXListViewListener;

import java.util.List;

/**
 * 公司: tophlc
 * 类说明：提现记录 基类
 * @date 2016-4-8
 */
public abstract class TopWithdrawList extends TopNetworkBaseActivity<WithdrawRecordDatasDataEntity>
		implements IXListViewListener {
	private XListView xListView;
	private WithdrawRecordAdapter adapter;
	private TextView textTotalWithdraw;
	private int pageIndex = 1;
	private ListBlankLayout listBlankLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		findView();
		initTopTitle();
		pageIndex = 1;
	}

	@Override
	public void refreshAfterLogin() {
		super.refreshAfterLogin();
		onRefresh();
	}

	private void findView() {
		textTotalWithdraw = (TextView) findViewById(R.id.text_withdraw_total_count);
		textTotalWithdraw.setTextColor(ContextCompat.getColor(ctx,getTextColor()));
		initXListView();
	}

	private void initTopTitle() {
		headerLayout.showTitle(R.string.withdraw_record);
		headerLayout.showLeftBackButton();
	}

	private void initXListView() {
		listBlankLayout = (ListBlankLayout) findViewById(R.id.list_blank_layout);
		xListView = (XListView) listBlankLayout
				.initContentView(R.layout.list_blank_xlistview_layout);

		adapter = new WithdrawRecordAdapter(this, getTextColor(),getImageResId());
		xListView.setAdapter(adapter);
		xListView.setXListViewListener(this);
	}



	private void getHeadData() {

		new MyNetAsyncTask(ctx, false) {
			WithdrawSummaryEntity response;

			@Override
			protected void doInBack() throws Exception {
				response = TopApp.getInstance().getHttpService()
						.accountGetWithdrawSummary(TopApp.getInstance().getLoginService().token);
			}

			@Override
			protected void onPost(Exception e) {
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {
						textTotalWithdraw.setText(response.getData().getOutTotalAmount());
					} else {
						com.toobei.common.utils.ToastUtil.showCustomToast(response
								.getErrorsMsgStr());
					}
				} else {
					com.toobei.common.utils.ToastUtil
							.showCustomToast(getString(R.string.pleaseCheckNetwork));
				}
			}
		}.execute();
	}

	public void onLoad() {
		if (xListView != null) {
			xListView.stopRefresh();
			xListView.stopLoadMore();
		}
	}

	@Override
	public void onRefresh() {
		pageIndex = 1;
		getHeadData();
		loadData(false);
	}

	@Override
	public void onLoadMore() {
		pageIndex++;
		loadData(false);
	}

	@Override
	protected int onGetContentViewLayout() {
		return R.layout.activity_withdraw_list;
	}

	@Override
	protected WithdrawRecordDatasDataEntity onLoadDataInBack() throws Exception {
		getHeadData();
		return TopApp
				.getInstance()
				.getHttpService()
				.accountQueryWithdrawLog(TopApp.getInstance().getLoginService().token,
						pageIndex + "");
	}

	@Override
	protected void onRefreshDataView(WithdrawRecordDatasDataEntity response) {
		xListView.setPullLoadEnable(true);
		List<WithdrawRecord> datas = response.getData().getDatas();
		pageIndex = response.getData().getPageIndex();

		if (pageIndex == 1 && (datas == null || datas.size() == 0)) {
			listBlankLayout.showBlankImageAndText(R.drawable.img_no_data_blank ,"暂无记录");
		} else {
			listBlankLayout.showContentView();
		}

		if (pageIndex == 1) {
			adapter.refresh(datas);
		} else {
			adapter.addAll(datas);
		}
		if (response.getData().getPageCount() <= response.getData().getPageIndex()
				|| response.getData().getPageCount() <= 1) {
			xListView.setPullLoadEnable(false);
		}
	}

	@Override
	protected void onResponseMsgError() {
		xListView.setPullLoadEnable(false);
	}

	@Override
	protected void onRefreshDataViewCompleted() {
		onLoad();
	}

	protected abstract int getTextColor();

	protected abstract int getImageResId();

}
