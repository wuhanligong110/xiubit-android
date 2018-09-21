package com.v5ent.xiubit.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.toobei.common.view.MyWebView;
import com.toobei.common.view.OnMyWebViewListener;
import com.toobei.common.view.dialog.PromptDialogCalTel;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyTitleBaseActivity;
import com.v5ent.xiubit.R;

import org.xsl781.utils.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.toobei.common.utils.C.MY_PERMISSIONS_REQUEST_DIAL;

/**
 * Activity-帮助中心 界面( bang)
 */
public class MyCustomerService extends MyTitleBaseActivity implements OnMyWebViewListener {


    @BindView(R.id.webview)
    MyWebView mWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mWebView.setRedirectUsable(false);
        mWebView.setOnMyWebViewListener(this);
        mWebView.loadUrl(MyApp.getInstance().getDefaultSp().getUrlQuestion());
        initTopTitle();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_customer_service;
    }


    private void initTopTitle() {
        headerLayout.showTitle("帮助与反馈");
        headerLayout.showLeftBackButton();
//        textPhone.setText(MyApp.getInstance().getDefaultSp().getServiceTelephone());
    }

    @OnClick({R.id.onlineConsultTv, R.id.customerServiceTv,R.id.quesBackTv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.onlineConsultTv:
                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra("toChatUsername", MyApp.getInstance()
                        .getUserService()
                        .getCallServiceEMId());
                startActivity(intent);
                break;
            case R.id.customerServiceTv:
                callPhone();
                break;
            case R.id.quesBackTv:
                showActivity(ctx, Feedback.class);
                break;
        }
    }

    @Override
    public void onReceivedTitle( String title) {
    }

    @Override
    public void onUrlRedirectCallBack(boolean isRedirectUsable, String url) {
        
    }

    @Override
    public void onUrlLoading( boolean isRedirectUsable, String url) {
        //只有false 不重定向时，才进行新起界面跳转
        if (!isRedirectUsable) {
            WebActivityCommon.showThisActivity(this, url, "");
        }
    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onPageStart(String url) {

    }

    @Override
    public void onLoadError(String url) {

    }


    //   android6.0获取打电话权限
    private void callPhone() {
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
            String serviceTelephone = MyApp.getInstance().getDefaultSp().getServiceTelephone();
            if (TextUtil.isEmpty(serviceTelephone)) serviceTelephone = "0755-86725461";
            PromptDialogCalTel dialog = new PromptDialogCalTel(ctx, true, "致电"+serviceTelephone+"？", serviceTelephone);
            dialog.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_DIAL:

                Logger.e(permissions.toString());
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String serviceTelephone = MyApp.getInstance().getDefaultSp().getServiceTelephone();
                    if (TextUtil.isEmpty(serviceTelephone)) serviceTelephone = "0755-86725461";
                    PromptDialogCalTel dialog = new PromptDialogCalTel(ctx, true, "致电"+serviceTelephone+"？", MyApp
                            .getInstance()
                            .getDefaultSp()
                            .getServiceTelephone());
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
