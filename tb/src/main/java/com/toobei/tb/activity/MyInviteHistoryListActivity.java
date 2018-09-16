package com.toobei.tb.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.toobei.common.entity.Custom;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.SpannableStringUtils;
import com.toobei.common.view.ListBlankLayout;
import com.toobei.tb.MyApp;
import com.toobei.tb.MyNetworkBaseActivity;
import com.toobei.tb.R;
import com.toobei.tb.adapter.CustomListAdapter;
import com.toobei.tb.entity.CustomerListDatasDataEntity;
import com.toobei.tb.entity.InviteCustomerListStatisticsData;
import com.toobei.tb.entity.InviteCustomerListStatisticsEntity;

import org.xsl781.ui.xlist.XListView;
import org.xsl781.ui.xlist.XListView.IXListViewListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司: tophlc
 * 类说明：邀请记录
 * @date 2016-1-13
 */
public class MyInviteHistoryListActivity extends MyNetworkBaseActivity<CustomerListDatasDataEntity> implements
		View.OnClickListener, IXListViewListener {

	private XListView xListView;
	private CustomListAdapter adapter;
	private int pageIndex = 1;
	private ListBlankLayout listBlankLayout;
	private TextView totalInviteCount, registeredCount;
	private List<Custom> datas = new ArrayList<Custom>();
    private Button mInvite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initTopTitle();
        loadData(false);
        getHeadData();
	}

    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_custom_list;
    }

	private void initTopTitle() {
		headerLayout.showTitle(R.string.my_invite);
		headerLayout.showLeftBackButton();
	}

	private void initView() {
		initXListView();
		totalInviteCount = (TextView) findViewById(R.id.invest_total_person_count);
		registeredCount = (TextView) findViewById(R.id.registered_total_person_count);
        mInvite = (Button) findViewById(R.id.invite);
        mInvite.setOnClickListener(this);
    }

	private void initXListView() {
		listBlankLayout = (ListBlankLayout) findViewById(R.id.list_blank_layout);
		xListView = (XListView) listBlankLayout
				.initContentView(R.layout.list_blank_xlistview_layout);

		//	xListView = (XListView) findViewById(R.id.custom_pull_refresh_list);
		adapter = new CustomListAdapter(this);
		xListView.setAdapter(adapter);
		xListView.setXListViewListener(this);
        xListView.setDividerHeight((int) getResources().getDimension(R.dimen.divide_view_height_10));
        xListView.setBackgroundColor(ContextCompat.getColor(ctx,R.color.backgroud_common));
    }


	private void getHeadData() {

		new MyNetAsyncTask(ctx, false) {
			InviteCustomerListStatisticsEntity response;
			private InviteCustomerListStatisticsData data;

			@Override
			protected void doInBack() throws Exception {
				response = MyApp.getInstance().getHttpService()
						.inviteCustomerListStatistics(MyApp.getInstance().getLoginService().token);
			}

			@Override
			protected void onPost(Exception e) {
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {
						data = response.getData();
						if (data != null) {
                            totalInviteCount.setText(getSpanText("已投资", data.getInvestPersons(), R.color.text_red_common));
                            registeredCount.setText(getSpanText("已注册", data.getRegPersons(), R.color.text_red_common));
                        }
					} else {
						com.toobei.common.utils.ToastUtil.showCustomToast(response.getErrorsMsgStr());
					}
				} else {
					com.toobei.common.utils.ToastUtil
							.showCustomToast(getString(R.string.pleaseCheckNetwork));
				}
			}
		}.execute();
	}

    /**
     * 获得指定文本样式的SpannableStringBuilder
     *
     * @param first
     * @param count
     */
    private SpannableStringBuilder getSpanText(String first, String count, int colorRid) {
        return SpannableStringUtils.getBuilder(first).append(count).setForegroundColor(ContextCompat.getColor(ctx,colorRid)).append("人").create();
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
		loadData(false);
		getHeadData();
	}

	@Override
	public void onLoadMore() {
		pageIndex++;
		loadData(false);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
            case R.id.invite:
                finish();
                break;

		default:
			break;
		}

	}

	@Override
	protected CustomerListDatasDataEntity onLoadDataInBack() throws Exception {
		return MyApp.getInstance().getHttpService()
				.inviteCustomerListPage(MyApp.getInstance().getLoginService().token, pageIndex + "");
	}

	@Override
	protected void onRefreshDataView(CustomerListDatasDataEntity response) {
		xListView.setPullLoadEnable(true);
		datas = response.getData().getDatas();
		pageIndex = response.getData().getPageIndex();

		if (datas == null || datas.size() == 0) {
			listBlankLayout.showBlank(R.string.blank_list_text_customer_list);
		} else {
			listBlankLayout.showContentView();
		}

		if (pageIndex == 1) {
			adapter.refresh(datas);
		} else {
			adapter.addAll(datas);
		}
		if (response.getData().getPageCount() == response.getData().getPageIndex()
				|| response.getData().getPageCount() <= 1) {
			xListView.setPullLoadEnable(false);
		}
	}

	@Override
	protected void onResponseMsgError() {
		super.onResponseMsgError();
		xListView.setPullLoadEnable(false);
	}

	@Override
	protected void onRefreshDataViewCompleted() {
		super.onRefreshDataViewCompleted();
		onLoad();
	}

}
