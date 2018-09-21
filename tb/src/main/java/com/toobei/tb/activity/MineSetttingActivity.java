package com.toobei.tb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toobei.common.TopApp;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.entity.MsgResponseData;
import com.toobei.common.entity.MsgResponseEntity;
import com.toobei.common.utils.C;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.PhotoUtil;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.utils.UpdateViewCallBack;
import com.toobei.common.view.dialog.PromptDialog;
import com.toobei.tb.MyApp;
import com.toobei.tb.MyTitleBaseActivity;
import com.toobei.tb.R;
import com.toobei.tb.util.PrefDao;

import org.xsl781.ui.view.RoundImageView;
import org.xsl781.utils.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公司: tophlc
 * 类说明：个人设置
 *
 * @date 2016-5-23
 */
public class MineSetttingActivity extends MyTitleBaseActivity {


    @BindView(R.id.mine_info_bank_card_manage_content)
    TextView textBindCard; //显示绑卡状态
    @BindView(R.id.mine_info_bank_card_manage_rl)
    RelativeLayout mineInfoBankCardManageRl;
    @BindView(R.id.mine_info_password_manage_rl) //密码管理
            RelativeLayout mineInfoPasswordManageRl;
    @BindView(R.id.infoIv)
    ImageView infoIv; //消息开关()
    @BindView(R.id.msgRe)
    RelativeLayout msgRe;

    public String isBounded = null;
    @BindView(R.id.mine_img_user_face)
    RoundImageView mineImgUserFace;
    @BindView(R.id.mine_info_avatar_rl)
    RelativeLayout mineInfoAvatarRl;
    @BindView(R.id.mine_info_phone)
    TextView mineInfoPhone;
    @BindView(R.id.mine_info_prompt_phone)
    TextView mineInfoPromptPhone;
    @BindView(R.id.mine_info_password_manage_content)
    TextView mineInfoPasswordManageContent;
    @BindView(R.id.btn_setting_logout)
    Button btnSettingLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //   EventBus.getDefault().register(this);
        initTopTitle();
        refreshFaceView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        TopApp.getInstance().getAccountService().checkBoundedCard(this, new UpdateViewCallBack<String>() {

            @Override
            public void updateView(Exception e, String string) {
                Logger.d("onResume ==>bindCardState=== " + string);
                if (string != null && string.equalsIgnoreCase("true")) {
                    isBounded = "true";
                    textBindCard.setText(R.string.bounded);
                } else if (string != null && string.equalsIgnoreCase("false")) {
                    isBounded = "false";
                    textBindCard.setText(R.string.unbound);
                } else {
                    isBounded = null;
                    textBindCard.setText("");
                }
            }
        });
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_mine_setting;
    }

    protected void refreshFaceView() {
        TopApp.getInstance().getAccountService().checkBoundedCard(this, new UpdateViewCallBack<String>() {

            @Override
            public void updateView(Exception e, String string) {
                if (string != null && string.equals("true")) {
                    isBounded = "true";
                    textBindCard.setText(com.toobei.common.R.string.bounded);
                } else if (string != null && string.equals("false")) {
                    isBounded = "false";
                    textBindCard.setText(com.toobei.common.R.string.unbound);
                } else {
                    isBounded = null;
                    textBindCard.setText("");
                }
            }
        });
        mineInfoPhone.setText(TopApp.getInstance().getLoginService().curPhone);
        if (TopApp.getInstance().getLoginService().getCurUser() != null) {
            TopApp.getInstance().getUserService().displayUserFace(TopApp.getInstance().getLoginService().getCurUser().getHeadImage(), mineImgUserFace, true);
        }
        PrefDao curUserSp = MyApp.getInstance().getCurUserSp();
        if (curUserSp == null) {
            return;
        }
        String platformflag = curUserSp.getPlatformflag();

        if (TextUtils.isEmpty(platformflag) || TextUtils.isEmpty(platformflag)) {
            queryMsgPushSet(MyApp.getInstance().getLoginService().token);
        } else {
            updateUI(platformflag);
        }
    }

    private void initTopTitle() {
        headerLayout.showTitle(TopApp.getInstance().getString(R.string.person_info));
        headerLayout.showLeftBackButton();
        setTranslucentStatus(true);
        headerLayout.setHeadViewCoverStateBar();
    }


    @OnClick({R.id.mine_info_bank_card_manage_rl, R.id.mine_info_password_manage_rl,//
            R.id.infoIv, R.id.btn_setting_logout, R.id.mine_info_avatar_rl, R.id.mine_info_qr_rl})
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {

            case R.id.mine_info_avatar_rl:
                intent = new Intent(this, ImageSelectActivity.class);
                intent.putExtra(C.TAG, C.TAG_USER_FACE_CHANGED);
                startActivityForResult(intent, C.REQUEST_PIC_CHANGED);
                //  showActivity(this, intent);
                break;
            case R.id.mine_info_bank_card_manage_rl: //银行卡管理
                if (isBounded != null && isBounded.equals("true")) {
                    intent = new Intent(this, CardManagerAddSuccess.class);
                    intent.putExtra("isSetPayPwd", false);
                    showActivity(this, intent);
                } else if (isBounded != null && isBounded.equals("false")) {
                    showActivity(this, CardManagerAdd.class);
                }
                break;
            case R.id.mine_info_password_manage_rl: //密码管理
                showActivity(this, PwdManager.class);
                break;
            case R.id.infoIv: //消息推送开关
                showMsgManagerActivity();
                break;
            case R.id.mine_info_qr_rl:
                showActivity(ctx, MyQRCodeActivity.class);
                break;
            case R.id.btn_setting_logout:
                PromptDialog dialog3 = new PromptDialog(ctx, "确定要退出登录吗？", "确定", "取消");
                dialog3.setListener(new PromptDialog.DialogBtnOnClickListener() {

                    @Override
                    public void onClicked(PromptDialog dialog, boolean isCancel) {
                        if (!isCancel) {
                            if (MyApp.getInstance().getDefaultSp().getIsLcdsJumpToken()) {
                                //当前token是貅比特跳过来的话就不做服务器登出
                                MyApp.getInstance().logOut(false);
                            }else {
                                MyApp.getInstance().logOut();
                            }
                        }
                    }
                });
                dialog3.show();
                break;

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ",data" + data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == C.REQUEST_PIC_CHANGED) {
                if (data != null) {
//                    ToastUtil.showCustomToast("shang==");
                    PhotoUtil.displayUserFace(data.getStringExtra("faceUrlMd5"), mineImgUserFace);
                    // TopApp.getInstance().getUserService().displayUserFace(data.getStringExtra("faceUrlMd5"), mineImgUserFace, true);
                }
            }
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)  //重设头像
//    public void reSetHeadImage(UpLoadHeadImageEvent event) {
//       // TopApp.getInstance().getUserService().displayUserFace(event.imgPath, mineImgUserFace, true);
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //   EventBus.getDefault().unregister(this);
    }

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }

    /*
        * 查询消息设置
        */
    private void queryMsgPushSet(final String token) {
        new MyNetAsyncTask(ctx, true) {
            MsgResponseEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().msgQueryMsgPushSet(token);

            }

            @Override
            protected void onPost(Exception e) {
                // 登录成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {

                        MsgResponseData data = response.getData();
                        if (data == null) {
                            return;
                        }
                        String platformflag = data.getPlatformflag();
                        MyApp.getInstance().getCurUserSp().setPlatformflag(platformflag);
                        updateUI(platformflag);
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }

    /*
     * 更新UI
     */
    private void updateUI(String platformflag) {
        if (platformflag.equals("0")) {
            infoIv.setBackgroundResource(R.drawable.btn_notification_unselected);
        } else {
            infoIv.setBackgroundResource(R.drawable.btn_notification_selected);
        }
    }

    /*
     * 设置消息
     * 0关闭
     * 1开启
     */
    private void setMsgPush(final String token, final String platformflag) {
        new MyNetAsyncTask(ctx, false) {
            LoginResponseEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().msgSetMsgPush(token, platformflag);

            }

            @Override
            protected void onPost(Exception e) {
                // 登录成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {

                        if (platformflag.equals("0")) {
                            infoIv.setBackgroundResource(R.drawable.btn_notification_unselected);
                            MyApp.getInstance().getCurUserSp().setPlatformflag("0"); // 不设置消息免打扰

                        } else {
                            infoIv.setBackgroundResource(R.drawable.btn_notification_selected);
                            MyApp.getInstance().getCurUserSp().setPlatformflag("1");//设置消息免打扰

                        }
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }


    protected void showMsgManagerActivity() {  // 点击消息免打扰开关
        PrefDao curUserSp = MyApp.getInstance().getCurUserSp();
        if (curUserSp == null) {
            return;
        }
        String platformflag = curUserSp.getPlatformflag();

        if (!TextUtils.isEmpty(platformflag) && !TextUtils.isEmpty(platformflag)) {
            if (platformflag.equals("0")) {
                setMsgPush(MyApp.getInstance().getLoginService().token, "1");
            } else {
                setMsgPush(MyApp.getInstance().getLoginService().token, "0");
            }
        }
    }

}
