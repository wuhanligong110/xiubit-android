package com.toobei.tb.service;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.UserInfo;
import com.toobei.common.entity.UserInfoEntity;
import com.toobei.common.service.TopLoginService;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.UpdateViewCallBack;
import com.toobei.tb.MyApp;

public class LoginService extends TopLoginService {

	private static LoginService service = null;
	private UserInfo myCfpUserInfo;

	private LoginService() {
		super();
	}

	public static LoginService getInstance() {
		if (service == null) {
			service = new LoginService();
		}
		return service;
	}

	@Override
	public UserInfo getMyCfpUserInfo() {
		return myCfpUserInfo;
	}

	@Override
	public void clearLoginCach() {
		super.clearLoginCach();
		myCfpUserInfo = null;
	}

	public void setMyCfpUserInfo(UserInfo myCfpUserInfo) {
		if (myCfpUserInfo != null) {
			this.myCfpUserInfo = myCfpUserInfo;
			MyApp.getInstance().getUserService().saveAndCache(myCfpUserInfo);
			if (myCfpUserInfo.getEasemobAcct() != null
					&& myCfpUserInfo.getEasemobAcct().length() > 0) {
				//存DB中的id
				MyApp.getInstance().getCurUserSp()
						.setLcsEasemobAcct(myCfpUserInfo.getEasemobAcct());
			}
		}
	}

	public void checkUserHasLcs(TopBaseActivity activity, boolean openDialog,
			final UpdateViewCallBack<String> callBack) {
		if (myCfpUserInfo != null && myCfpUserInfo.getEasemobAcct() != null) {
			if (callBack != null) {
				callBack.updateView(null, "true");
				return;
			}
		}
		String lcsEasemobAcct = MyApp.getInstance().getCurUserSp().getLcsEasemobAcct();
		if (lcsEasemobAcct != null) {
			UserInfo userInfo = MyApp.getInstance().getUserService()
					.getUserByEmIdAndSaveCache(lcsEasemobAcct);
			myCfpUserInfo = userInfo;
			if (callBack != null) {
				callBack.updateView(null, "true");
				return;
			}
		} else {
			checkUserHasLcsFromServer(activity, openDialog, callBack);
		}
	}

	public void checkUserHasLcsFromServer(TopBaseActivity activity, boolean openDialog,
			final UpdateViewCallBack<String> callBack) {
		new MyNetAsyncTask(activity, openDialog) {
			UserInfoEntity response;

			@Override
			protected void doInBack() throws Exception {
				response = MyApp.getInstance().getHttpService().interactMyCfp(token);
			}

			@Override
			protected void onPost(Exception e) {
				if (e == null && response != null) {
					if (response.getCode().equals("0") && response.getData() != null) {
						if (response.getData().getMobile() != null
								&& response.getData().getMobile().length() > 0) {
							// 有理财师
							setMyCfpUserInfo(response.getData());
							if (callBack != null) {
								callBack.updateView(null, "true");
								return;
							}
						} else {
							// 没有理财师
							if (callBack != null) {
								callBack.updateView(null, "false");
								return;
							}
						}
					} else {
						// ToastUtil.showCustomToast(response.getErrorsMsgStr());
					}
				} else {
					// com.tophlc.common.utils.ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
				}
				if (callBack != null) {
					callBack.updateView(e, null);
				}
			}
		}.execute();
	}

	/**
	 * 重写父类方法，如果是理财师则不登录环信
	 */
	//不再需要重写，环信账号实时登录
	/*	@Override
		public void initLoginEM() {
			if (curUser == null) {
				TopApp.getInstance().getUserService().getCurUser(new UpdateViewCallBack<UserInfo>() {

					@Override
					public void updateView(Exception e, UserInfo object) {
						if (object != null && object.getEasemobAcct().length() > 0) {
							Log.getLog(getClass()).d(
									"====== 本地object emId = " + object.getEasemobAcct() + ",emPwd = "
											+ object.getEasemobPassword());
							curUser = object;
							initLoginEM();
						}
					}
				});
			} else if (!curUser.isCfp()) {
				super.initLoginEM();
			}
		}*/
}
