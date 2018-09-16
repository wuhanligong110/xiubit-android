package com.toobei.common.activity;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopTitleBaseActivity;
import com.toobei.common.data.InviteRecommendContactsListAdapter;
import com.toobei.common.data.InviteRecommendContactsListAdapter.OnMyCheckedListener;
import com.toobei.common.entity.Contacts;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.PinyinComparator;
import com.toobei.common.view.ListBlankLayout;

import org.xsl781.ui.view.ClearEditText;
import org.xsl781.ui.view.EnLetterView;
import org.xsl781.ui.xlist.XListView;
import org.xsl781.utils.Logger;
import org.xsl781.utils.SimpleTextWatcher;
import org.xsl781.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.toobei.common.utils.C.MY_PERMISSIONS_REQUEST_READ_CONTACTS;

/**
 * 公司: tophlc
 * 类说明：通讯录 邀请 推荐
 *
 * @date 2015-12-25
 */
public abstract class TopInviteContacts extends TopTitleBaseActivity implements View.OnClickListener {
    protected XListView xListView;
    protected InviteRecommendContactsListAdapter adapter;
    protected ClearEditText cedtSearch;
    protected TextView textSelectedCount, textPrompt, textRightBtn;
    //	protected int pageIndex = 1;
    protected MySimpleTextWatcher textWatcher;
    protected String strSearch;
    protected ListBlankLayout listBlankLayout;
    protected EnLetterView rightLetter;
    protected TextView dialog;
    public TextView titleTv;

    protected boolean isRecommendCfp;
    protected BroadcastReceiver smsReceiver;
    protected String SENT_SMS_ACTION = "SENT_SMS_ACTION";
    protected List<Contacts> contactsData;
    protected List<Contacts> searchData;
    protected Button btnInvest;
    protected String textTitle = "邀请客户";//邀请客户

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isRecommendCfp = getIntent().getBooleanExtra("isRecommendCfp", true);

        smsReceiver = new SMSReceiver();
        //处理返回的发送状态
        registerReceiver(smsReceiver, new IntentFilter(SENT_SMS_ACTION));

        findView();
        //	pageIndex = 1;
        Logger.e("hasReadContact获取通讯录权限sPermission");
        insertDummyContactWrapper();
        //  getData(null, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsReceiver);
    }

    protected void findView() {
        initTopTitle();
        cedtSearch = (ClearEditText) findViewById(R.id.cedt_invite_name_or_phone_search);
        textSelectedCount = (TextView) findViewById(R.id.text_customer_selected_count);
        textPrompt = (TextView) findViewById(R.id.text_prompt);
        btnInvest = (Button) findViewById(R.id.btn_invest);
        titleTv = (TextView) findViewById(R.id.titleTv);
        btnInvest.setOnClickListener(this);
        if (isRecommendCfp) { //推荐理财师
            textPrompt.setText(R.string.recommend_cfp_contacts_prompt);
            btnInvest.setText(R.string.recommend);
        } else { //邀请客户
            textPrompt.setText(R.string.invite_customer_contacts_prompt);
            btnInvest.setText(R.string.invite);
        }

        textWatcher = new MySimpleTextWatcher();
        cedtSearch.addTextChangedListener(textWatcher);
        initXListView();
        initRightLetterView();

        List<String> list = getContactTitles();
        if (list != null && list.size() > 0) {
            headerLayout.showTitle(list.get(0));
            titleTv.setText(list.get(1));
        }
    }

    protected void initTopTitle() {
        if (isRecommendCfp) {
            headerLayout.showTitle(R.string.activity_recommend_cfp);
        } else {
            headerLayout.showTitle(textTitle);
        }
        headerLayout.showLeftBackButton();
    }

    protected void initXListView() {
        //	xListView = (XListView) findViewById(R.id.invite_cfp_system_list);
        listBlankLayout = (ListBlankLayout) findViewById(R.id.list_blank_layout);
        xListView = (XListView) listBlankLayout.initContentView(R.layout.list_blank_xlistview_layout);
        xListView.setPullRefreshEnable(false);
        xListView.setPullLoadEnable(false);
        adapter = new InviteRecommendContactsListAdapter(this);
        textSelectedCount.setText("0");
        adapter.setCheckedListener(new OnMyCheckedListener() {

            @Override
            public void onCheckedChanged(int selectedCount, boolean isChecked) {
                if (selectedCount <= 0) {
                    selectedCount = 0;
                }
                textSelectedCount.setText(selectedCount + "");
            }
        });
        xListView.setAdapter(adapter);
    }

    protected void initRightLetterView() {
        rightLetter = (EnLetterView) ctx.findViewById(R.id.right_letter);
        dialog = (TextView) ctx.findViewById(R.id.dialog);
        rightLetter.setTextView(dialog);
        rightLetter.setOnTouchingLetterChangedListener(new LetterListViewListener());

    }

    protected class MySimpleTextWatcher extends SimpleTextWatcher {
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (contactsData == null || contactsData.size() == 0) {
                return;
            }
            strSearch = cedtSearch.getText().toString();
            textSelectedCount.setText(0 + "");
            if (strSearch.length() > 0) {
                if ((Utils.isNumeric(strSearch) && strSearch.length() > 3)) {
                    searchData = new ArrayList<Contacts>();
                    for (Contacts con : contactsData) {
                        if (con.getMobile().contains(strSearch)) {
                            searchData.add(con);
                        }
                    }
                    adapter.refresh(searchData);
                } else if (!Utils.isNumeric(strSearch)) {
                    searchData = new ArrayList<Contacts>();
                    for (Contacts con : contactsData) {
                        if (con.getName().contains(strSearch)) {
                            searchData.add(con);
                        }
                    }
                    adapter.refresh(searchData);
                }
            } else {
                strSearch = null;
                adapter.refresh(contactsData);
            }
        }
    }

    protected class LetterListViewListener implements EnLetterView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(String s) {
            if (adapter == null) return;

            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getDatas().get(i).getSortLetters().equals(s)) {
                    xListView.setSelection(i);
                }
            }
        }
    }

    // TODO: 2017/2/19  android6.0获取通讯录权限
    private void insertDummyContactWrapper() {
        int hasReadContactsPermission = ContextCompat.checkSelfPermission(TopInviteContacts.this,
                Manifest.permission.READ_CONTACTS);
        Logger.e("hasReadContact获取通讯录权限sPermission===>" + hasReadContactsPermission
        );
        if (hasReadContactsPermission != PackageManager.PERMISSION_GRANTED) {
            //   Logger.e("hasReadContactsPermission===>"+hasReadContactsPermission);
            if (!ActivityCompat.shouldShowRequestPermissionRationale(TopInviteContacts.this,
                    Manifest.permission.READ_CONTACTS)) {
//                showMessageOKCancel("You need to allow access to Contacts",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                ActivityCompat.requestPermissions(TopInviteContacts.this,
//                                        new String[]{Manifest.permission.READ_CONTACTS},
//                                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//                            }
//                        });
//                return;
//                PromptDialog dialog2 = new PromptDialog(ctx, "允许获取通讯录信录信息", "确定", "取消");
//                dialog2.setListener(new PromptDialog.DialogBtnOnClickListener() {
//
//                    @Override
//                    public void onClicked(PromptDialog dialog, boolean isCancel) {
//                        if (!isCancel) {
                ActivityCompat.requestPermissions(TopInviteContacts.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//                        }
//                    }
//                });
//                dialog2.show();
            }
            Logger.e("hasReadContact获取通讯录权限sPermission===2222222>" + hasReadContactsPermission);
            ActivityCompat.requestPermissions(TopInviteContacts.this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            return;
        } else {
            getData(null, true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:

                Logger.e(permissions.toString());
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    getData(null, true);
                    Logger.e("user granted the permission!");

                } else {

                    Logger.e("user denied the permission!");
                }
                break;
        }

        return;

    }

//    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder(TopInviteContacts.this)
//                .setMessage(message)
//                .setPositiveButton("OK", okListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }

    protected void getData(final String name, boolean openDialog) {

        new MyNetAsyncTask(ctx, openDialog) {

            @Override
            protected void doInBack() throws Exception {

                contactsData = com.toobei.common.utils.Utils.getPhoneContacts(ctx);
                contactsData.addAll(com.toobei.common.utils.Utils.getSIMContacts(ctx));
                if (contactsData != null && contactsData.size() > 0) {
                    List<String> mobiles = null;
                    if (isRecommendCfp) {
                        mobiles = getAllTeamPhones();
                    } else {
                        mobiles = getAllCustomerPhones();
                    }
                    if (mobiles != null && mobiles.size() > 0) {
                        for (int i = 0; i < contactsData.size(); i++) {
                            String phone = contactsData.get(i).getMobile().replace(" ", "");
                            if (mobiles.contains(phone) || phone.equals(TopApp.getInstance().getLoginService().curPhone)) {
                                contactsData.remove(i);
                                i--;
                            }
                        }
                    }
                }
            }

            @Override
            protected void onPost(Exception e) {
                if (contactsData == null || contactsData.size() == 0) {
                    listBlankLayout.showBlank(R.string.blank_list_text_no_contacts);
                } else {
                    listBlankLayout.showContentView();
                    Collections.sort(contactsData, new PinyinComparator());
                    adapter.refresh(contactsData);
                }
            }
        }.execute();
    }

    protected List<String> getAllCustomerPhones() throws Exception {
        return null;
    }

    /**
     * 功能：得到所有团队成员，取最多200人
     *
     * @return
     * @throws Exception
     */
    protected List<String> getAllTeamPhones() throws Exception {
        return null;
    }

    protected class SMSReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    //Toast.makeText(ctx, "短信发送成功", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    break;
            }
        }
    }

    protected PendingIntent getPendingIntent() {
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        return PendingIntent.getBroadcast(ctx, 0, sentIntent, 0);
    }

    protected void checkCustomerValid() {

    }

    protected String getSMSStringPhone(List<Contacts> datas) {
        if (datas == null || datas.size() == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < datas.size(); i++) {
            sb.append(datas.get(i).getMobile());
            if (i < datas.size() - 1) {
                sb.append(";");
            }
        }
        System.out.println(" phoneNumber = " + sb.toString());

        return sb.toString().replaceAll(" ", "");
    }

    protected String getStringPhone(List<Contacts> datas) {
        if (datas == null || datas.size() == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getMobile() == null || datas.get(i).getMobile().isEmpty()) {
                continue;
            }
            sb.append(datas.get(i).getMobile());
            if (i < datas.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString().replaceAll(" ", "");
    }

    protected String getStringName(List<Contacts> datas) {
        if (datas == null || datas.size() == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < datas.size(); i++) {
            sb.append(datas.get(i).getName());
            if (i < datas.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_invest) {
            if (adapter.getCheckedCount() == 0) {
                com.toobei.common.utils.ToastUtil.showCustomToast("请先选择客户");
                return;
            }
            //去服务器请求 判断过滤人员
            checkCustomerValid();
        }
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_invite_contacts;
    }

    protected abstract List<String> getContactTitles();
}
