package com.toobei.common.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.ImageResponseEntity;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.utils.C;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.UpdateViewCallBack;
import com.toobei.common.view.HeaderLayout;
import com.toobei.common.view.MyShowTipsView;

import org.xsl781.ui.view.RoundImageView;

import java.io.File;

/**
 * 公司: tophlc
 * 类说明：个人设置
 * @date 2015-9-22
 */
public abstract class TopMineInfoActivity extends TopBaseActivity implements View.OnClickListener {

	private TextView textBankCardManageInfo;
	private int CHANGE_USER_FACE = 0x123;
	public static TopMineInfoActivity activity;
	private RelativeLayout msgRe;
	public String isBounded = null;

	protected RoundImageView avatarView;
	protected MyShowTipsView myShowTipsView;
	public ImageView infoIv; //消息开关
	public TextView textBindCard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_info);
		activity = this;
		findView();
		initTopTitle();


	}

	@Override
	protected void onResume() {
		super.onResume();
		EventCount();
		TopApp.getInstance().getAccountService()
				.checkBoundedCard(activity, new UpdateViewCallBack<String>() {

					@Override
					public void updateView(Exception e, String string) {
						if (string != null && string.equals("true")) {
							isBounded = "true";
							textBankCardManageInfo.setText(R.string.bounded);
						} else if (string != null && string.equals("false")) {
							isBounded = "false";
							textBankCardManageInfo.setText(R.string.unbound);
						} else {
							isBounded = null;
							textBankCardManageInfo.setText("");
						}
					}
				});
	}

	protected abstract Intent getImageSelectActivityIntent();

	protected abstract Intent getCardManagerAddSuccessIntent();

	protected abstract void showCardManagerAdd();

	protected abstract void showPwdManager();

	protected abstract void showMsgManagerActivity();

	protected abstract void refreshFaceView();


	private void initTopTitle() {
		headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
		headerLayout.showTitle(TopApp.getInstance().getString(R.string.person_setting));
		headerLayout.showLeftBackButton();
		setTranslucentStatus(true);
		headerLayout.setHeadViewCoverStateBar();
	}

	private void findView() {

		textBankCardManageInfo = (TextView) findViewById(R.id.mine_info_bank_card_manage_content);
		avatarView = (RoundImageView) findViewById(R.id.mine_img_user_face);

		findViewById(R.id.mine_info_avatar_rl).setOnClickListener(this);//头像编辑
		findViewById(R.id.mine_info_bank_card_manage_rl).setOnClickListener(this);
		findViewById(R.id.mine_info_password_manage_rl).setOnClickListener(this);

		infoIv = (ImageView) findViewById(R.id.infoIv);
		textBindCard = (TextView) findViewById(R.id.mine_info_bank_card_manage_content);
		infoIv.setOnClickListener(this);
		msgRe = (RelativeLayout) findViewById(R.id.msgRe);
		msgRe.setOnClickListener(this);
		refreshFaceView();


	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == CHANGE_USER_FACE) {
				String path = data.getStringExtra("path");
				uploadUserFace(path);
			}
		}
	}

	private void uploadUserFace(final String filePath) {
		new MyNetAsyncTask(ctx, true) {
			ImageResponseEntity response;
			String faceUrlMd5 = null;
			LoginResponseEntity loginResponseEntity;

			@Override
			protected void doInBack() throws Exception {
				response = TopApp.getInstance().getHttpService()
						.personcenterUploadImageFile(new File(filePath));
				if (response != null && response.getInfo() != null
						&& response.getInfo().getMd5() != null) {
					faceUrlMd5 = response.getInfo().getMd5();
					loginResponseEntity = TopApp
							.getInstance()
							.getHttpService()
							.personcenterUploadIcon(TopApp.getInstance().getLoginService().token,
									faceUrlMd5);
				}
			}

			@Override
			protected void onPost(Exception e) {
				if (e == null && loginResponseEntity != null) {
					if (loginResponseEntity.getCode().equals("0")) {
						TopApp.getInstance().getLoginService().getCurUser().setHeadImage(faceUrlMd5);
						TopApp.getInstance().getUserService()
								.saveAndCache(TopApp.getInstance().getLoginService().getCurUser());
						TopApp.getInstance()
								.getUserService()
								.displayUserFace(
										TopApp.getInstance().getLoginService().getCurUser()
												.getHeadImage(), avatarView, false);
					} else {
						com.toobei.common.utils.ToastUtil.showCustomToast(loginResponseEntity
								.getErrorsMsgStr());
					}
				} else {
					com.toobei.common.utils.ToastUtil
							.showCustomToast(getString(R.string.pleaseCheckNetwork));
				}

			}
		}.execute();
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		if (v.getId() == R.id.mine_info_avatar_rl) { //设置头像

			intent = getImageSelectActivityIntent();
			intent.putExtra(C.TAG, C.TAG_USER_FACE_CHANGED);
			startActivityForResult(intent, CHANGE_USER_FACE);
		} else if (v.getId() == R.id.mine_info_bank_card_manage_rl) {//银行卡管理

			if (isBounded != null && isBounded.equals("true")) {
				//	intent = new Intent(this, CardManagerAddSuccess.class);
				intent = getCardManagerAddSuccessIntent();
				intent.putExtra("isSetPayPwd", false);
				showActivity(this, intent);
			} else if (isBounded != null && isBounded.equals("false")) {
				//	showActivity(this, CardManagerAdd.class);
				showCardManagerAdd();
			}
		} else if (v.getId() == R.id.mine_info_password_manage_rl) {//密码管理
			showPwdManager();
		}  else if (v.getId() == R.id.infoIv) { //消息推送开关
			showMsgManagerActivity();
		}
	}

	@Override
	public void finish() {
		setResult(RESULT_OK);
		super.finish();
	}
	
	public abstract void EventCount();
















}
