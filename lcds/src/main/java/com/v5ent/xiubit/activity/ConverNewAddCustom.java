package com.v5ent.xiubit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.toobei.common.entity.Custom;
import com.toobei.common.view.ListBlankLayout;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyNetworkBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.ConverNewAddCustomListAdapter;
import com.v5ent.xiubit.data.ConverNewAddCustomListAdapter.OnMyCheckedListener;
import com.v5ent.xiubit.entity.CustomerListDatasDataEntity;
import com.v5ent.xiubit.service.UserService;

import org.xsl781.ui.view.ClearEditText;
import org.xsl781.ui.xlist.XListView;
import org.xsl781.ui.xlist.XListView.IXListViewListener;
import org.xsl781.utils.SimpleTextWatcher;
import org.xsl781.utils.Utils;

import java.io.Serializable;
import java.util.List;

/**
 * 公司: tophlc
 * 类说明：Activity-新建会话时，选择客户界面
 * @date 2015-12-17
 */
public class ConverNewAddCustom extends MyNetworkBaseActivity<CustomerListDatasDataEntity>
		implements View.OnClickListener, IXListViewListener {
	private XListView xListView;
	private ConverNewAddCustomListAdapter adapter;

	private ClearEditText cedtSearch;
	private TextView textSelectedCount;
	private int pageIndex = 1;
	private MySimpleTextWatcher textWatcher;
	private String strSearch;
	private Button btnConfirm;
	private ListBlankLayout listBlankLayout;
	private List<Custom> checkedUsers;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		checkedUsers = (List<Custom>) getIntent().getBundleExtra("data").getSerializable(
				"checkedUsers");
		findView();
		pageIndex = 1;

	}

	private void findView() {
		initTopTitle();
		cedtSearch = (ClearEditText) findViewById(R.id.cedt_invite_name_or_phone_search);
		textSelectedCount = (TextView) findViewById(R.id.text_customer_selected_count);
		textWatcher = new MySimpleTextWatcher();
		cedtSearch.addTextChangedListener(textWatcher);
		btnConfirm = (Button) findViewById(R.id.btn_confirm);
		btnConfirm.setOnClickListener(this);
		btnConfirm.setEnabled(false);
		initXListView();
	}

	private void initTopTitle() {
		headerLayout.showTitle(R.string.select_custom);
		headerLayout.showLeftBackButton();
	}

	private void initXListView() {
		//	xListView = (XListView) findViewById(R.id.invite_cfp_system_list);
		listBlankLayout = (ListBlankLayout) findViewById(R.id.list_blank_layout);
		xListView = (XListView) listBlankLayout
				.initContentView(R.layout.list_blank_xlistview_layout);
		adapter = new ConverNewAddCustomListAdapter(this);
		adapter.setOldCheckedUsers(checkedUsers);
		textSelectedCount.setText("0");
		adapter.setCheckedListener(new OnMyCheckedListener() {

			@Override
			public void onCheckedChanged(int selectedCount, boolean isChecked) {
				if (selectedCount <= 0) {
					selectedCount = 0;
					btnConfirm.setEnabled(false);
				} else {
					btnConfirm.setEnabled(true);
				}
				textSelectedCount.setText(selectedCount + "");
			}
		});

		xListView.setAutoLoadMore(false);
		xListView.setAdapter(adapter);
		xListView.setXListViewListener(this);
	}

	private class MySimpleTextWatcher extends SimpleTextWatcher {
		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
			strSearch = cedtSearch.getText().toString();
			if (strSearch.length() > 0) {
				if ((Utils.isNumeric(strSearch) && strSearch.length() > 4)
						|| !Utils.isNumeric(strSearch)) {
					pageIndex = 1;
					loadData(true);
				}
			} else {
				pageIndex = 1;
				strSearch = null;
				loadData(true);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm:
			List<Custom> checkedUsers = adapter.getCheckedDatas();
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putSerializable("checkedUsers", (Serializable) checkedUsers);
			intent.putExtra("data", bundle);
			setResult(RESULT_OK, intent);
			finish();
			break;

		default:
			break;
		}

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
		strSearch = null;
		loadData(false);
	}

	@Override
	public void onLoadMore() {
		pageIndex++;
		strSearch = null;
		loadData(false);
	}

	@Override
	protected int onGetContentViewLayout() {
		return R.layout.activity_conver_new_add_custom;
	}

	@Override
	protected CustomerListDatasDataEntity onLoadDataInBack() throws Exception {
		CustomerListDatasDataEntity response = MyApp
				.getInstance()
				.getHttpService()
				.customerGetMyCustomers(MyApp.getInstance().getLoginService().token, strSearch, null,
						pageIndex + "", null, null, null);
		if (response != null && response.getData() != null) {
			List<Custom> datas = response.getData().getDatas();
			if (datas != null && datas.size() > 0) {
				// 拿到客户数据后，在本地进行缓存
				int count = datas.size();
				for (int i = 0; i < count; i++) {
					Custom inviteCfpInListDatas = datas.get(i);
					UserService.getInstance()
							.saveAndCache(inviteCfpInListDatas.toUserInfo("false"));
					if (MyApp.getInstance().getLoginService().getCurUser().getEasemobAcct()
							.equals(inviteCfpInListDatas.getEasemobAcct())
							|| MyApp.getInstance().getLoginService().getCurUser().getMobile()
									.equals(inviteCfpInListDatas.getMobile())) {
						datas.remove(i);
						count--;
					}
				}

			}
		}
		return response;
	}

	@Override
	protected void onRefreshDataView(CustomerListDatasDataEntity response) {
		xListView.setPullLoadEnable(true);
		List<Custom> datas = response.getData().getDatas();
		pageIndex = response.getData().getPageIndex();
		if (datas == null || datas.size() == 0) {
			listBlankLayout.showBlank(R.string.blank_list_text_no_custom);
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
	
	
}
