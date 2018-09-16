package com.v5ent.xiubit.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.toobei.common.TopApp;
import com.toobei.common.utils.PathUtils;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.utils.Utils;
import com.toobei.common.view.HeaderLayout;
import com.toobei.common.view.dialog.PromptDialog;
import com.toobei.common.view.dialog.PromptDialogCalTel;
import com.toobei.common.view.dialog.PromptDialogProgressBar;
import com.v5ent.xiubit.BuildConfig;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.util.C;

import org.xsl781.http.HttpCallBack;
import org.xsl781.utils.Logger;
import org.xsl781.utils.SystemTool;

import java.io.File;

import tech.linjiang.pandora.Pandora;

import static com.toobei.common.utils.C.MY_PERMISSIONS_REQUEST_DIAL;
import static com.umeng.analytics.MobclickAgent.onEvent;

/**
 * 公司: tophlc
 * 类说明：Activity-更多
 *
 * @date 2015-10-12
 */
public class AboutUs extends MyBaseActivity implements View.OnClickListener {
    TextView textVersion;
    TextView textWechatWeContent;
    TextView textMailWeContent;
    TextView textUpdataVersion;
    PromptDialog dialog1;
    private int mSecretNumber = 0;
    private static final long MIN_CLICK_INTERVAL = 600;
    private long mLastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        findView();
        initTopTitle();
    }

    @Override
    protected void onResume() {
        onEvent(AboutUs.this, "my_more");
        super.onResume();
    }

    private void initTopTitle() {
        headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
        headerLayout.showTitle("关于貅比特");
        headerLayout.showLeftBackButton();
        setTranslucentStatus(true);
        headerLayout.setHeadViewCoverStateBar();
    }

    private void findView() {

        findViewById(R.id.mine_mail_we_rl).setOnClickListener(this);//邮箱
        findViewById(R.id.mine_about_rl).setOnClickListener(this);
        findViewById(R.id.mine_wechat_we_rl).setOnClickListener(this);
        findViewById(R.id.wechatCommunicateQun).setOnClickListener(this);
        findViewById(R.id.mine_version_updata_rl).setOnClickListener(this);
        findViewById(R.id.mine_version_update_info_rl).setOnClickListener(this);//更新日志
        textVersion = (TextView) findViewById(R.id.text_version);
        textMailWeContent = (TextView) findViewById(R.id.text_mine_mail_we_content);
        textWechatWeContent = (TextView) findViewById(R.id.text_mine_wechat_we_content);
        textUpdataVersion = (TextView) findViewById(R.id.text_mine_version_update);
        textVersion.setText("版本  V" + Utils.getAppVersion(ctx));
//        if (TopApp.getInstance().appVersion == null || "0".equals(TopApp.getInstance().appVersion.getUpgrade())) {
//            textUpdataVersion.setText("当前版本: " + Utils.getAppVersionName(getApplicationContext()));
//            textUpdataVersion.setBackgroundResource(R.color.WHITE);
//        } else {
//            textUpdataVersion.setText(TopApp.getInstance().appVersion.getVersion()); //获取线上最新版本名
//            textUpdataVersion.setBackgroundResource(R.drawable.text_bg_red_shape);
//            textUpdataVersion.setTextColor(Color.WHITE);
//        }

        textWechatWeContent.setText("tsteven520"/*MyApp.getInstance().getDefaultSp().getWechatNumber()*/);  //微信公众号
        textMailWeContent.setText("tongqingshang@163.com"/*MyApp.getInstance().getDefaultSp().getServiceMail()*/); //客服邮箱
        if (BuildConfig.LogOpen) {
            textVersion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long currentClickTime = SystemClock.uptimeMillis();
                    long elapsedTime = currentClickTime - mLastClickTime;
                    mLastClickTime = currentClickTime;

                    if (elapsedTime < MIN_CLICK_INTERVAL) {
                        ++mSecretNumber;
                        if (9 == mSecretNumber) {
                            try {
                                ToastUtil.showCustomToast("开启UI调试模块");
                                Pandora.get().open();
                            } catch (Exception e) {
                                Log.i(TAG, e.toString());
                            }

                        }
                    } else {
                        mSecretNumber = 0;
                    }

            }
        });
    }
}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_mail_we_rl: //客服邮箱


                SystemTool.copy(MyApp.getInstance().getDefaultSp().getServiceMail(), ctx);
                ToastUtil.showCustomToast("已复制");
            /*	PromptDialogEmail dialog4 = new PromptDialogEmail(ctx, "客服邮箱已复制到您的剪贴板", MyApp
                        .getInstance().getDefaultSp().getServiceMail());
				dialog4.show();

				dialog4.setOnShowListener(new OnShowListener() {

					@Override
					public void onShow(DialogInterface dialog) {
						Uri uri = Uri.parse("mailto:3802**92@qq.com");

						String[] email = { "3802**92@qq.com" };

						Intent intent = new Intent(Intent.ACTION_SENDTO, uri);

						intent.putExtra(Intent.EXTRA_CC, email); // 抄送人  

						intent.putExtra(Intent.EXTRA_SUBJECT, "这是邮件的主题部分"); // 主题  

						intent.putExtra(Intent.EXTRA_TEXT, "这是邮件的正文部分"); // 正文  

						startActivity(intent);

					}
				});*/

                break;

            case R.id.wechatCommunicateQun: //复制微信群号

                SystemTool.copy("linghuikeji88", ctx);
                ToastUtil.showCustomToast("已复制");

                break;


            case R.id.mine_about_rl: // 关于貅比特

                WebActivityCommon.showThisActivity(ctx, MyApp.getInstance().getDefaultSp().getUrlAboutMe(), "貅比特是什么");
                break;
            case R.id.mine_version_update_info_rl: // 更新日志

                WebActivityCommon.showThisActivity(ctx, C.UPDATE_LOG, "");
                break;

//            case R.id.mine_service_tel_rl: //客服电话
//                callPhone();
////                PromptDialogCalTel dialog = new PromptDialogCalTel(ctx, true, "确定拨打客服电话？", MyApp.getInstance().getDefaultSp().getServiceTelephone());
////                dialog.show();
//                break;
            case R.id.mine_version_updata_rl: //检查更新
                initUpdate();
                break;

            case R.id.mine_wechat_we_rl: //点击官方微信
                SystemTool.copy(MyApp.getInstance().getDefaultSp().getWechatNumber(), ctx);
                PromptDialog dialog2 = new PromptDialog(ctx, "貅比特公众号已复制到您的剪贴板\n您可以在微信-公众号中搜索并关注", "去关注", "取消");
                dialog2.setListener(new PromptDialog.DialogBtnOnClickListener() {

                    @Override
                    public void onClicked(PromptDialog dialog, boolean isCancel) {
                        if (!isCancel) {
                            SystemTool.launchApp(ctx, "com.tencent.mm");// 启动微信
                        }
                    }
                });
                dialog2.show();
                break;


        }
    }

    private void initUpdate() {
        if (TopApp.getInstance().appVersion == null || "0".equals(TopApp.getInstance().appVersion.getUpgrade())) {
//            PromptDialogMsg promptDialogMsg = new PromptDialogMsg(ctx, "当前已是最新版本", "知道了");
//            promptDialogMsg.show();
            ToastUtil.showCustomToast("当前已是最新版本");
            return;
        } else if (TopApp.getInstance().appVersion.getUpgrade().equals("1")) {
            //提示升级
            if (dialog1 == null) {
                //更新内容提示 -> 通过 ";"换行  "&" 区分标题和内容
                String[] split = TopApp.getInstance().appVersion.getUpdateHints().replace("；", "\n").replace(";", "\n").replace(" ", "").split("&");
                if (split.length > 1) {
                    dialog1 = new PromptDialog(ctx, split[0].toString(), split[1].toString(), "更新", "取消");
                } else {
                    dialog1 = new PromptDialog(ctx, "检查到有更新，是否现在升级软件\n" + TopApp.getInstance().appVersion.getUpdateHints(), "更新", "取消");
                }
            }
            dialog1.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    TopApp.getInstance().getLoginService().setUpgrade(false);
                }
            });
            dialog1.setCanceledOnTouchOutside(false);
            dialog1.setListener(new PromptDialog.DialogBtnOnClickListener() {

                @Override
                public void onClicked(PromptDialog dialog, boolean isCancel) {
                    if (!isCancel) {
                        //确定
                        try {
                            TopApp.getInstance().getHttp().download(TopApp.getInstance().appVersion.getDownloadUrl(), new File(PathUtils.getChatFileDir() + "/" + TopApp.getInstance().appVersion.getVersion() + ".apk"), new ApkDownloadCallback());
                        } catch (Exception e) {
                            PathUtils.resetAppPath();
                            ToastUtil.showCustomToast("下载失败，请重试");
                        }
                    } else {
                        //取消
                        TopApp.getInstance().getLoginService().setUpgrade(false);
                    }
                }
            });
            dialog1.show();
        }
    }

private class ApkDownloadCallback extends HttpCallBack {
    private PromptDialogProgressBar downloadDialog;

    @Override
    public void onPreStart() {
        super.onPreStart();
        downloadDialog = new PromptDialogProgressBar(ctx);
        downloadDialog.setCanceledOnTouchOutside(false);
        downloadDialog.setForceUpdate(false);
        downloadDialog.show();
    }

    @Override
    public void onLoading(long count, long current) {
        super.onLoading(count, current);
        downloadDialog.setProgress((int) (current * 1000 / count));
    }

    @Override
    public void onSuccess(File file) {
        super.onSuccess(file);
        downloadDialog.setProgress(1000);
        downloadDialog.dismiss();
        SystemTool.installApk(ctx, file);
    }

    @Override
    public void onFailure(Throwable t, int errorNo, String strMsg) {
        super.onFailure(t, errorNo, strMsg);
        downloadDialog.dismiss();
        t.printStackTrace();
        System.out.println(" onFailure + errorNo" + errorNo + ",strMsg = " + strMsg);
    }

}

    // 2017/2/19  android6.0获取打电话权限
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
            PromptDialogCalTel dialog = new PromptDialogCalTel(ctx, true, "确定拨打客服电话？", MyApp.getInstance().getDefaultSp().getServiceTelephone());
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

                    PromptDialogCalTel dialog = new PromptDialogCalTel(ctx, true, "确定拨打客服电话？", MyApp.getInstance().getDefaultSp().getServiceTelephone());
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
