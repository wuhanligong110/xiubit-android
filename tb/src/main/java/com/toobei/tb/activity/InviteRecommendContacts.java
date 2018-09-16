package com.toobei.tb.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopInviteContacts;
import com.toobei.common.entity.Contacts;
import com.toobei.common.entity.ContactsResponseEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.utils.Utils;
import com.toobei.tb.MyApp;
import com.toobei.tb.R;

/**
 * 公司: tophlc 类说明：通讯录 邀请 推荐
 * 
 * @date 2015-12-25
 */
public class InviteRecommendContacts extends TopInviteContacts {

	private List<String> list = new ArrayList<String>();

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(activity, GestureActivity.class);
	}

	protected void checkCustomerValid() {
		if (adapter == null || adapter.getCount() == 0) {
			ToastUtil.showCustomToast("数据为空，无法发送！");
			return;
		}
		final List<Contacts> checkedDatas = adapter.getCheckedDatas();
		if (checkedDatas == null || checkedDatas.size() == 0) {
			ToastUtil.showCustomToast("数据为空，无法发送！");
			return;
		}

		new MyNetAsyncTask(ctx, true) {
			ContactsResponseEntity response;

			@Override
			protected void doInBack() throws Exception {
				response = MyApp
						.getInstance()
						.getHttpService()
						.inviteContacts(MyApp.getInstance().getLoginService().token,
								getStringPhone(checkedDatas), getStringName(checkedDatas));
			}

			@Override
			protected void onPost(Exception e) {
				if (e == null && response != null) {
					if (response.getData() != null) {
						if (response.getData().getCustomers() != null
								&& response.getData().getCustomers().size() > 0) {
							// 发短信
							// 应该跳到发送短信页面
							Utils.doSendSMSTo(ctx, getSMSStringPhone(response.getData()
									.getCustomers()), response.getData().getContent());
						} else {
							ToastUtil.showCustomToast("没有符合条件的客户，可能是选择客户已经注册！");
						}
					} else {
						ToastUtil.showCustomToast("服务器异常");
					}
				} else {
					ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
				}
			}
		}.execute();
	}

	@Override
	protected List<String> getContactTitles() {
		list.add("邀请朋友");
		list.add("位朋友");
		return list;
	}
}
