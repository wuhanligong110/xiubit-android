package com.v5ent.xiubit.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.toobei.common.entity.BaseEntity;
import com.toobei.common.entity.BaseResponseEntity;
import com.toobei.common.entity.SignPushStatueEntity;
import com.toobei.common.event.CardBindSuccessEvent;
import com.toobei.common.network.NetworkObserver;
import com.toobei.common.network.RetrofitHelper;
import com.toobei.common.network.httpapi.SignPushStatusApi;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.dialog.PromptDialog;
import com.toobei.common.view.dialog.PromptDialogCalTel;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyTitleBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.entity.PersonalCenterData;
import com.v5ent.xiubit.entity.PersonalCenterEntity;
import com.v5ent.xiubit.util.C;
import com.v5ent.xiubit.util.ParamesHelp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xsl781.utils.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.toobei.common.utils.C.MY_PERMISSIONS_REQUEST_DIAL;

/**
 * 公司: tophlc
 * 类说明：Activity-个人中心
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


    @BindView(R.id.loginOutTv) //登出
            TextView loginOutTv;

    public boolean isBounded;
    @BindView(R.id.headImageRl)
    RelativeLayout mHeadImageRl;
    @BindView(R.id.headImageTv)
    TextView headImageTv;
    @BindView(R.id.phoneNumTv)
    TextView mPhoneNumTv;
    @BindView(R.id.phoneNumRl)
    RelativeLayout mPhoneNumRl;
    @BindView(R.id.nameAuthenticationGoRIv)
    ImageView mNameAuthenticationGoRIv;
    @BindView(R.id.nameAuthentStatuTv)
    TextView mNameAuthentStatuTv;
    @BindView(R.id.nameAuthenticationRl)
    RelativeLayout mNameAuthenticationRl;
    @BindView(R.id.password_manage_field)
    TextView mPasswordManageField;
    @BindView(R.id.mine_info_password_manage_content)
    TextView mMineInfoPasswordManageContent;
    @BindView(R.id.vg_content_view)
    LinearLayout mVgContentView;
    @BindView(R.id.jobGradeTv)
    TextView mJobGradeTv;
    @BindView(R.id.signPushSV)
    SwitchButton signPushSV;
    private String callPhoneNum;
    private String mJobGrade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJobGrade = getIntent().getStringExtra("jobGrade");
        ButterKnife.bind(this);
        initView();

        getInfoData();
        querySignPushStatue();
    }

    private void querySignPushStatue() {
        RetrofitHelper.getInstance().getRetrofit().create(SignPushStatusApi.class).querySignPushStatus(
                new ParamesHelp()
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<BaseResponseEntity<SignPushStatueEntity>> () {

                    @Override
                    public void bindViewWithDate(BaseResponseEntity<SignPushStatueEntity> response) {
                        boolean statue = true;
                        try {
                            if (!TextUtil.isEmpty(response.getData().signPushStatus)){
                                statue = "1".equals(response.getData().signPushStatus);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        signPushSV.setChecked(statue);
                    }
                });
    }

    private void getInfoData() {
        new MyNetAsyncTask(ctx, false) {
            PersonalCenterEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance()
                        .getHttpService()
                        .getPersonalCenterInfo();
            }

            @Override
            protected void onPost(Exception e) {

                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        PersonalCenterData data = response.getData();
                        mNameAuthentStatuTv.setText(TextUtils.isEmpty(data.getAuthenName()) ? "未认证" : data.getAuthenName());
                        mNameAuthentStatuTv.setTextColor(TextUtils.isEmpty(data.getAuthenName()) ? ContextCompat.getColor(ctx,R.color.text_blue_common) : ContextCompat.getColor(ctx,R.color.text_gray_common));
                        textBindCard.setText(TextUtils.isEmpty(data.getBankCard()) ? "未绑定" : data.getBankCard());
                        textBindCard.setTextColor(TextUtils.isEmpty(data.getBankCard()) ? ContextCompat.getColor(ctx,R.color.text_blue_common) : ContextCompat.getColor(ctx,R.color.text_gray_common));
                        isBounded = TextUtils.isEmpty(data.getBankCard()) ? false : true;
                        mPhoneNumTv.setText(data.getMobile());
                        if (TextUtils.isEmpty(data.getHeadImage())) {
                            headImageTv.setText("去设置");
                            headImageTv.setTextColor(ContextCompat.getColor(ctx,R.color.text_blue_common));
                        } else {
                            headImageTv.setText("已设置");
                            headImageTv.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_common));
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

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_mine_setting;
    }


    private void initView() {
        headerLayout.showTitle("账户设置");
        headerLayout.showLeftBackButton();
        setTranslucentStatus(true);
        headerLayout.setHeadViewCoverStateBar();
        String curPhone = MyApp.getInstance().getLoginService().curPhone;
        String hideCurPhone = curPhone.substring(0, 2) + "****" + curPhone.substring(6, 10);
        mPhoneNumTv.setText(hideCurPhone);
        mJobGradeTv.setText(TextUtils.isEmpty(mJobGrade) ? "" :mJobGrade);
        signPushSV.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setSignPush(isChecked);
            }
        });

    }


    private void setSignPush(final boolean statue){
        String signPushStatus = statue ? "1" :"0";
        RetrofitHelper.getInstance().getRetrofit().create(SignPushStatusApi.class).setSignPushStatus(
                new ParamesHelp()
                        .put("signPushStatus",signPushStatus)
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<BaseResponseEntity<BaseEntity>> () {
                    @Override
                    public void onNext(BaseResponseEntity<BaseEntity> response) {
                        if ("0".equals(response.getCode())) signPushSV.setCheckedNoEvent(statue);
                    }

                    @Override
                    public void bindViewWithDate(BaseResponseEntity<BaseEntity> response) {}
                });
    }


    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }


    @OnClick({R.id.jobGradeEntry,R.id.phoneNumRl,R.id.loginOutTv, R.id.headImageRl, R.id.nameAuthenticationRl, R.id.mine_info_bank_card_manage_rl, R.id.mine_info_password_manage_rl})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.jobGradeEntry:
                WebActivityCommon.showThisActivity(ctx, C.URL_mine_level_DEFAULT,"");
                break;
            case R.id.headImageRl:
                intent = new Intent(ctx, ImageSelectActivity.class);
                intent.putExtra(com.toobei.common.utils.C.TAG, com.toobei.common.utils.C.TAG_USER_FACE_CHANGED);
                startActivity(intent);
                break;

            case R.id.nameAuthenticationRl:
                intent = new Intent(this, NameAuthentActivity.class);
                intent.putExtra("hasAuthent",isBounded);
                showActivity(this,intent);
                break;
            case R.id.mine_info_bank_card_manage_rl:
                intent = new Intent(this, BankCardAuthentActivity.class);
                intent.putExtra("hasCard",isBounded);
                showActivity(ctx,intent);
                break;
            case R.id.mine_info_password_manage_rl:
                showActivity(this, PwdManager.class);
                break;
//            case R.id.customerServiceRl:
//                callPhoneNum = getString(R.string.customer_service_num);
//                callPhone();
//                break;
            case R.id.loginOutTv: //退出登录
                PromptDialog dialog3 = new PromptDialog(ctx, "确定要退出登录吗？","", "确定", "取消");
                dialog3.setListener(new PromptDialog.DialogBtnOnClickListener() {

                    @Override
                    public void onClicked(PromptDialog dialog, boolean isCancel) {
                        if (!isCancel) {
                            MyApp.getInstance().logOut();
                        }
                    }
                });
                dialog3.show();
           break;
            case R.id.phoneNumRl: //手机认证
                Intent intent1 = new Intent(ctx, PhoneAuthentActivity.class);
                intent1.putExtra("phoneNum",mPhoneNumTv.getText());
                showActivity(ctx,intent1);
                break;
        }
    }

    public void callPhone() {
        int hasCallPhonePermission = ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE);
        Logger.e("hasReadContact获取打电话权限sPermission===>" + hasCallPhonePermission);
        if (hasCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(ctx, Manifest.permission.CALL_PHONE)) {

                ActivityCompat.requestPermissions(ctx, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_DIAL);

            }
            Logger.e("hasReadContact获取打电话权限sPermission===2222222>" + hasCallPhonePermission);
            ActivityCompat.requestPermissions(ctx, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_DIAL);
            return;
        } else {
            PromptDialogCalTel dialog = new PromptDialogCalTel(ctx, false, getString(R.string.confirm_call_custom_servier_num), callPhoneNum);
            dialog.setBtnPositiveColor(R.color.text_blue_common);
            dialog.show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshInfo(CardBindSuccessEvent event) {
        getInfoData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_DIAL:

                Logger.e(permissions.toString());
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    PromptDialogCalTel dialog = new PromptDialogCalTel(ctx, false, getString(R.string.confirm_call_custom_servier_num), callPhoneNum);
                    dialog.setBtnPositiveColor(R.color.text_blue_common);
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
