package com.v5ent.xiubit.activity;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopInviteContacts;
import com.toobei.common.entity.Contacts;
import com.toobei.common.entity.ContactsResponseEntity;
import com.toobei.common.entity.UserInfo;
import com.toobei.common.utils.MyNetAsyncTask;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.entity.MyTeamListDatasDataEntity;
import com.v5ent.xiubit.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司: tophlc
 * 类说明：Activity-通讯录 邀请 推荐
 * @date 2015-12-25
 */
public class InviteRecommendContacts extends TopInviteContacts {

	@Override
	protected void onResume() {

		super.onResume();
	}

	protected List<String> getAllCustomerPhones() throws Exception {
		if (!MyApp.getInstance().getCurUserSp().isCustomLoaded()) {
			UserService.getInstance().getAllCustomerDataFromServer(false);
		}
		List<UserInfo> users = UserService.getInstance().getAllCustomerPhones();
		List<String> phones = new ArrayList<String>();
		for (UserInfo user : users) {
			phones.add(user.getMobile());
		}
		return phones;
	}

	/**
	 * 功能：得到所有团队成员，取最多200人
	 * @return
	 * @throws Exception
	 */
	protected List<String> getAllTeamPhones() throws Exception {
		List<String> phones = new ArrayList<String>();
		MyTeamListDatasDataEntity entity = MyApp
				.getInstance()
				.getHttpService()
				.personcenterMyTeamList(MyApp.getInstance().getLoginService().token, null, "1",
						"200", null, null);
		if (entity != null && entity.getData() != null && entity.getData().getDatas() != null) {
			List<com.v5ent.xiubit.entity.MyTeamDetail> teams = entity.getData().getDatas();
			for (com.v5ent.xiubit.entity.MyTeamDetail myTeamDetail : teams) {
				phones.add(myTeamDetail.getMobile());
			}
		}
		return phones;
	}

	protected void checkCustomerValid() {
		if (adapter == null || adapter.getCount() == 0) {
			com.toobei.common.utils.ToastUtil.showCustomToast("数据为空，无法发送！");
			return;
		}
		final List<Contacts> checkedDatas = adapter.getCheckedDatas();
		if (checkedDatas == null || checkedDatas.size() == 0) {
			com.toobei.common.utils.ToastUtil.showCustomToast("数据为空，无法发送！");
			return;
		}

		new MyNetAsyncTask(ctx, true) {
			ContactsResponseEntity response;

			@Override
			protected void doInBack() throws Exception {
				String type = "1";
				if (isRecommendCfp) {
					type = "2";
				}
				response = MyApp
						.getInstance()
						.getHttpService()
						.inviteContacts(MyApp.getInstance().getLoginService().token, type,
								getStringPhone(checkedDatas), getStringName(checkedDatas));
			}

			@Override
			protected void onPost(Exception e) {
				if (e == null && response != null) {
					if (response.getData() != null) {
						if (response.getData().getCustomers() != null
								&& response.getData().getCustomers().size() > 0) {
							//发短信
							// 应该跳到发送短信页面
							com.toobei.common.utils.Utils.doSendSMSTo(ctx,
									getSMSStringPhone(response

									.getData().getCustomers()), response.getData().getContent());
							/*	sendSMS(response.getData().getCustomers(), response.getData()
										.getRemark());*/
							//	callbackContactsCustomer(getStringUserId(response.getData()
							//			.getCustomers()));
						} else {
							com.toobei.common.utils.ToastUtil
									.showCustomToast("没有符合条件的客户，可能是选择客户已经注册！");
						}
					} else {
						com.toobei.common.utils.ToastUtil.showCustomToast("服务器异常");
					}
				} else {
					com.toobei.common.utils.ToastUtil
							.showCustomToast(getString(R.string.pleaseCheckNetwork));
				}
			}
		}.execute();
	}

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(activity, GestureActivity.class);
	}

	@Override
	protected List<String> getContactTitles() {
		return null;
	}

}
