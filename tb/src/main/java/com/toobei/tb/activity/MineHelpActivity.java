package com.toobei.tb.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.toobei.common.utils.ToastUtil;
import com.toobei.common.utils.Utils;
import com.toobei.common.view.HeaderLayout;
import com.toobei.common.view.dialog.PromptDialog;
import com.toobei.common.view.dialog.PromptDialogCalTel;
import com.toobei.tb.MyApp;
import com.toobei.tb.MyBaseActivity;
import com.toobei.tb.R;
import com.umeng.analytics.MobclickAgent;

import org.xsl781.utils.Logger;
import org.xsl781.utils.SystemTool;

import static com.toobei.common.utils.C.MY_PERMISSIONS_REQUEST_DIAL;

/**
 * 公司: tophlc
 * 类说明：更多
 * @date 2015-10-12
 */
public class MineHelpActivity extends MyBaseActivity implements View.OnClickListener {

	TextView textVersion, textServiceTel, text_mine_wechat_we_content;
	private TextView textMail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_more);
		findView();
		initTopTitle();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onEvent(MineHelpActivity.this,"my_more");
	}

	private void initTopTitle() {
		headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
		headerLayout.showTitle(MyApp.getInstance().getString(R.string.more));
		headerLayout.showLeftBackButton();
		setTranslucentStatus(true);
		headerLayout.setHeadViewCoverStateBar();
	}

	private void findView() {
		findViewById(R.id.btn_setting_logout).setOnClickListener(this);
		findViewById(R.id.mine_about_rl).setOnClickListener(this);
		findViewById(R.id.mine_feedback_rl).setOnClickListener(this);
		findViewById(R.id.mine_service_tel_rl).setOnClickListener(this);
		findViewById(R.id.mine_wechat_we_rl).setOnClickListener(this);
		findViewById(R.id.mine_email_rl).setOnClickListener(this);

		text_mine_wechat_we_content = (TextView) findViewById(R.id.text_mine_wechat_we_content);
		textMail = (TextView) findViewById(R.id.text_service_mail);
		textVersion = (TextView) findViewById(R.id.text_version);
		textServiceTel = (TextView) findViewById(R.id.text_mine_service_tel_content);
		textMail.setText(MyApp.getInstance().getDefaultSp().getServiceMail());
		textVersion.setText("版本 V" + Utils.getAppVersion(ctx));
		textServiceTel.setText(MyApp.getInstance().getDefaultSp().getServiceTelephone());

		text_mine_wechat_we_content.setText(MyApp.getInstance().getDefaultSp().getWechatNumber());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_setting_logout:
			PromptDialog dialog3 = new PromptDialog(ctx, "确定要退出登录吗？", "确定", "取消");
			dialog3.setListener(new PromptDialog.DialogBtnOnClickListener() {

				@Override
				public void onClicked(PromptDialog dialog, boolean isCancel) {
					if (!isCancel) {
						//用户账号为空，清空缓存信息，重新登录
						if (MyApp.getInstance().getDefaultSp().getIsLcdsJumpToken()) {
							//当前token是猎财跳过来的话就不做服务器登出
							MyApp.getInstance().logOut(false);
						}else {
							MyApp.getInstance().logOut();
						}

					}
				}
			});
			dialog3.show();
			break;
		case R.id.mine_about_rl:
			WebActivityCommon.showThisActivity(ctx, MyApp.getInstance().getDefaultSp()
					.getUrlAboutMe(), MyApp.getInstance().getString(R.string.about_we));
			break;
		case R.id.mine_email_rl:
			SystemTool.copy(MyApp.getInstance().getDefaultSp().getServiceMail(), ctx);
			ToastUtil.showCustomToast("客服邮箱已复制到您的剪贴板");
			//			WebActivityCommon.showThisActivity(ctx, MyApp.getInstance().getDefaultSp()
			//					.getUrlQuestion(), MyApp.getInstance().getString(R.string.problem));
			break;
		case R.id.mine_feedback_rl:
			showActivity(ctx, Feedback.class);
			break;
		case R.id.mine_service_tel_rl:


			callPhone();


			break;
		case R.id.mine_wechat_we_rl:
			SystemTool.copy(MyApp.getInstance().getDefaultSp().getWechatNumber(), ctx);
            PromptDialog dialog2 = new PromptDialog(ctx, "T呗公众号已复制到您的剪贴板\n您可以在微信-公众号中搜索并关注",
                    "去关注", "取消");
			dialog2.setListener(new PromptDialog.DialogBtnOnClickListener() {

				@Override
				public void onClicked(PromptDialog dialog, boolean isCancel) {
					if (!isCancel) {
						SystemTool.launchApp(ctx, "com.tencent.mm");//启动微信
					}
				}
			});
			dialog2.show();
			break;

		default:
			break;
		}
	}


	// TODO: 2017/2/19  android6.0获取打电话权限
	private void callPhone() {
		int hasCallPhonePermission = ContextCompat.checkSelfPermission(
				ctx,
				Manifest.permission.CALL_PHONE);
		Logger.e("hasReadContact获取打电话权限sPermission===>" + hasCallPhonePermission
		);
		if (hasCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
			if (!ActivityCompat.shouldShowRequestPermissionRationale(ctx,
					Manifest.permission.CALL_PHONE)) {

				ActivityCompat.requestPermissions(ctx,
						new String[]{Manifest.permission.CALL_PHONE},
						MY_PERMISSIONS_REQUEST_DIAL);

			}
			Logger.e("hasReadContact获取打电话权限sPermission===2222222>" + hasCallPhonePermission);
			ActivityCompat.requestPermissions(ctx,
					new String[]{Manifest.permission.CALL_PHONE},
					MY_PERMISSIONS_REQUEST_DIAL);
			return;
		} else {
			PromptDialogCalTel dialog = new PromptDialogCalTel(ctx, true, "确定拨打客服电话", MyApp
					.getInstance().getDefaultSp().getServiceTelephone());
			dialog.show();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case MY_PERMISSIONS_REQUEST_DIAL:

				Logger.e(permissions.toString());
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {

					PromptDialogCalTel dialog = new PromptDialogCalTel(ctx, true, "确定拨打客服电话", MyApp
							.getInstance().getDefaultSp().getServiceTelephone());
					dialog.show();
					Logger.e("user granted the permission!");

				} else {
					Logger.e("user denied the permission!");
				}
				break;
		}

		return;

	}
}
