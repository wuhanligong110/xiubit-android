package com.toobei.tb.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopMainActivity;
import com.toobei.common.entity.AdvertisementOpeningDataEntity;
import com.toobei.common.entity.UserInfo;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.NetCachAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.ActionItem;
import com.toobei.common.view.popupwindow.TitlePopup;
import com.toobei.common.view.popupwindow.TitlePopup.OnItemOnClickListener;
import com.v5ent.xiubit.entity.UnReadMsgCount;
import com.v5ent.xiubit.entity.UnReadMsgCountEntity;
import com.toobei.tb.MyApp;
import com.toobei.tb.R;
import com.toobei.tb.fragment.FragmentHome;
import com.toobei.tb.fragment.FragmentMine;
import com.toobei.tb.fragment.FragmentOrg;
import com.toobei.tb.fragment.FragmentProduct;
import com.toobei.tb.service.ChatService;
import com.toobei.tb.util.C;
import com.toobei.tb.util.DynamicSkipManage;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;
import org.xsl781.utils.Logger;
import org.xsl781.utils.PixelUtil;
import org.xsl781.utils.SystemTool;

public class MainActivity extends TopMainActivity implements OnCheckedChangeListener, OnClickListener, OnItemOnClickListener {
    public static MainActivity activity;
    private Fragment[] fragments = new Fragment[4];

    public RadioGroup mRadioGroup;
    private int curIndex = -1;// 当前的选项卡
    //  private ImageView pointIv;

    private RelativeLayout headRe;
    private ImageView infoIv;
    private TextView mMsgCountTv;
    private TextView titleTv;
    private ImageView moreIv;
    private TitlePopup titlePopup;
    public RadioGroup bottomRe;
    private int unReadImMsgCount = 0;// IM未读消息数
    String [] title = {"T呗","产品","平台","我的"};
    int [] checkids = {R.id.main_btn_home,R.id.main_btn_financing,R.id.main_btn_interaction,R.id.main_btn_mine};


    @Override
    public void skipGestureSetActivity() {
        skipActivity(this, GestureSetActivity.class);
    }

    @Override
    protected Intent getGestureActivityIntent(TopBaseActivity activity) {
        return new Intent(activity, GestureActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.e("msgPushJump==>onCreate");
        JumpIntent(getIntent());
        msgPushJump(getIntent());
        super.onCreate(savedInstanceState);

        activity = this;
        MyApp.getInstance().isLaunched = true;
        // initParams();

        // TODO: 2016/9/5 0005  获取用户绑卡
        //	MyApp.getInstance().getAccountService().accountGetUserBindCard(this, null);

        // TODO: 2016/9/5 0005   初始化本地数据
        //	initLocalDataAsyn();
        setContentView(R.layout.activity_main);
        initView();
        pushJump();
        initOpeningAdvertise();

    }

    private void JumpIntent(Intent intent) {
        int switchFragmentIndex = intent.getIntExtra("switchFragment",-1);
        if (switchFragmentIndex != -1) {
            bottomRe.check(checkids[switchFragmentIndex]);
        }
        String fromAppTag = intent.getStringExtra("FromAppTag");
        String productInfoUrl = intent.getStringExtra("ProductInfoUrl");
        String orgName = intent.getStringExtra("orgName");
        String orgNum = intent.getStringExtra("orgNum");
        String productId = intent.getStringExtra("ProductId");
        String lcdsToken = intent.getStringExtra("LcdsToken");
        String userPhoneNum = intent.getStringExtra("userPhoneNum");
//        ToastUtil.showCustomToast("fromAppTag="+ fromAppTag);
//        判断是否从猎才大师跳转过来
        if ("LCDS_TO_ORG_PRODECT".equals(fromAppTag)) {  //跳转第三方产品详情
            if (!MyApp.getInstance().getLoginService().isCachPhoneExist() || !userPhoneNum.equals(MyApp.getInstance().getLoginService().curPhone)) {
                //未登录或者登录用户不同
                UserInfo userInfo = new UserInfo();
                userInfo.setMobile(userPhoneNum);
                TopApp.getInstance().getLoginService().setCurUser(userInfo);
                MyApp.getInstance().getLoginService().cachLoginInfo(lcdsToken, userPhoneNum);
                initCurUserFromServer();
                MyApp.getInstance().getDefaultSp().setIsLcdsJumpToken(true);
                NoGestureSet = true;  //禁止设置手势页面弹出
            }

            Intent intent1 = new Intent(this, ProductInfoWebActivity.class);
            intent1.putExtra("url", productInfoUrl);
            intent1.putExtra("orgName", orgName);
            intent1.putExtra("orgNum", orgNum);
            intent1.putExtra("JumpToBuy", true);
            intent1.putExtra("productId", productId);
            startActivity(intent1);
        }

        if ("LCDS_TO_ORG_DETIAL".equals(fromAppTag)) { //跳转第三方机构详情
            MyApp.getInstance().getDefaultSp().setIsLcdsJumpToken(true);
            if (!MyApp.getInstance().getLoginService().isCachPhoneExist() || !userPhoneNum.equals(MyApp.getInstance().getLoginService().curPhone)) {
                //未登录或者登录用户不同
                UserInfo userInfo = new UserInfo();
                userInfo.setMobile(userPhoneNum);
                TopApp.getInstance().getLoginService().setCurUser(userInfo);
                MyApp.getInstance().getLoginService().cachLoginInfo(lcdsToken, userPhoneNum);
                initCurUserFromServer();
                MyApp.getInstance().getDefaultSp().setIsLcdsJumpToken(true);
                NoGestureSet = true;  //禁止设置手势页面弹出
            }
            Intent intent2 = new Intent(this, OrgInfoDetailActivity.class);
            intent2.putExtra("orgName", orgName);
            intent2.putExtra("orgNumber", orgNum);
            startActivity(intent2);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        NoGestureSet = false;
        String s = MyApp.getInstance().getDefaultSp().getSMS_jump_msg();
        if (!TextUtils.isEmpty(s)) {
            Logger.e("msgPushJump==>onResume");
            smsJump(s);
            MyApp.getInstance().getDefaultSp().setSMS_jump_msg("");
        }
    }

    private void msgPushJump(Intent intent) {
        // <!--  短信网页跳机构详情 toobei://agentDetailLinexxxxLine-->

        try {
            Intent i_getvalue = intent;
            String intentAction = i_getvalue.getAction();
            String sms_jum_msg = i_getvalue.getData().toString();
            Logger.e("msgPushJump_url===:" + sms_jum_msg);
            if  (!sms_jum_msg.contains("toobei://")){
                return;
            }
            MyApp.getInstance().getDefaultSp().setSMS_jump_msg(sms_jum_msg);
            //T呗允许不登录
//            if (!MyApp.getInstance().getLoginService().isCachPhoneExist()) {
//                startActivity(new Intent(this,LoginActivity.class));
//                finish();
//                MyApp.getInstance().getDefaultSp().setSMS_jump_msg(sms_jum_msg);
//            } else {
//                String s = MyApp.getInstance().getDefaultSp().getSMS_jump_msg();
//                if (!TextUtils.isEmpty(s)) {
//                    smsJump(s);
//                    MyApp.getInstance().getDefaultSp().setSMS_jump_msg("");
//                } else {
                    if (Intent.ACTION_VIEW.equals(intentAction)) {
                        String s1 = sms_jum_msg;
                        smsJump(s1);
                        MyApp.getInstance().getDefaultSp().setSMS_jump_msg("");
                    }
//                }

//            }
        } catch (Exception e) {
            Logger.d(e.toString());
            Logger.e("msgPushJump_error====>"+e.toString());
        }
    }
//toobei://agentDetailLineOPEN_XINYONGBAO_WEBLinetoken
    private void smsJump(String s){
        Logger.d(" MainActivity smsJump =====> :" + s);
        try {
            String[] split = s.split("/LINEAction=");
            String s1 = split[1];
            if (!TextUtils.isEmpty(s)) {
                if (s1.contains("NativeAppLINE")){
                    String[] dataString = s1.split("LINE");
                    String pageTag = dataString[1];
                    String params = dataString[2];
                    DynamicSkipManage.skipActivity(this,pageTag,params);
                } else if (s.contains("agentDetail")) {
                    String[] lines = s.split("Line");
                    String orgNum = lines[1];
                    if (!TextUtils.isEmpty(orgNum)) {
                        Intent intent = new Intent(this, OrgInfoDetailActivity.class);
                        intent.putExtra("orgName", "");
                        intent.putExtra("orgNumber", orgNum);
                        startActivity(intent);
                    }

                } else {
                    Logger.d(" MainActivity sms open web url =====> :" + s);
                    WebActivityCommon.showThisActivity(this, s, "");
                }
            }


        } catch (Exception e) {
            Logger.e(e.toString());
        }
    }

    /**
     * 缓存用户数据，登录成功后调用
     */
    public void initCurUserFromServer() {
        if (TopApp.getInstance().getLoginService().getCurUser() == null || TopApp.getInstance().getLoginService().getCurUser().getEasemobAcct() == null) {
            new MyNetAsyncTask(this, false) {
                @Override
                protected void doInBack() throws Exception {
                    UserInfo userInfo = TopApp.getInstance().getUserService().getCurUserByTokenFromMyServer();
                    if (userInfo != null) {
                        TopApp.getInstance().getLoginService().setCurUser(userInfo);
                        MyApp.getInstance().getDefaultSp().setCurUserId(""+userInfo.getUserId());
                        TopApp.getInstance().getUserService().saveAndCache(userInfo);
                        Log.e("easemobAcct", userInfo.getEasemobAcct());
                        Log.e("easemobPassword", userInfo.getEasemobPassword());
                    }
                }

                @Override
                protected void onPost(Exception e) {
                    if (e != null && TopApp.getInstance().getLoginService().getCurUser() != null) {
                    }
                    TopApp.getInstance().getLoginService().initLoginEM();
                }
            }.execute();
        } else {
            TopApp.getInstance().getLoginService().initLoginEM();
        }
    }

    /**
     * 初始化首页活动弹窗
     */
    @Override
    protected void initHomeFragmentDialog() {
    }

    /*
     * push信息，根据接收到的push信息进行相应跳转
     */
   /*
    * push信息，根据接收到的push信息进行相应跳转
    */

    private void pushJump() {

        try {
            String data = getIntent().getStringExtra("pushdata");
            if (TextUtils.isEmpty(data)) {
                return;
            }
            JSONObject json = new JSONObject(data);
            String key = json.getString("customUrlKey");
                  /*
                         * cusDetail:  客户详情页
						 * teamDetail:团队成员详情页
						 * cusDetail2:客户详情页（切换到到期日程）
						 * myAccount:  我的账户页面
						 * withdraw:  提现记录页面
						 * notification:  绑定理财师（理财师）
						 *  lsjappcs :  理财师升级成功
						 * contactCtmService  :客服消息
						 * declarationFormList  :报单
						 *   lcsSysNoticeRelease :公告详情 key：msgUrl
						 * lcsSysActivityRelease :活动详情 key：activityUrl
						 */

            if (!TextUtils.isEmpty(key)) {
                Intent intent = null;
                if (key.equals("withdrawRecord")) { //提现记录: withdrawRecord
                    intent = new Intent(this, WithdrawList.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.startActivity(intent);

                } else if (key.equals("myRedpacket")) {
                    intent = new Intent(this, MineRedPacketsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.startActivity(intent);

                } else if (key.equals("invitationRecord")) { //邀请记录
                    intent = new Intent(this, MyInviteHistoryListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.startActivity(intent);

                } else if (key.equals("productDtl")) {  //	产品详情：productDtl 对应传参：跳转链接地址－productDetailUrl 产品ID－productId
                    String productDetailUrl = json.getString("productDetailUrl");
                    String productId = json.getString("productId");
                    String orgName = json.getString("orgName ");
                    intent.putExtra("url", productDetailUrl);
                    intent.putExtra("productId", productId);
                    intent.putExtra("orgName ", orgName);
                    intent = new Intent(this, ProductInfoWebActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.startActivity(intent);

                } else if (key.equals("platformDtl")) { //	平台详情：platformDtl 对应传参：平台ID－orgNo
                    String orgNumber = json.getString("orgNo");
                    intent = new Intent(this, OrgInfoDetailActivity.class);
                    intent.putExtra("orgNumber", orgNumber);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.startActivity(intent);

                } else if (key.equals("investRecord")) { //我的投资明细：investRecord 对应参数：status（状态:1=募集中|2=回款中3=回款完成）
                    intent = new Intent(this, MyInvestDetailActivity.class);
                    String status = json.getString("status");
                    intent.putExtra("status", status);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.startActivity(intent);

                } else if (key.equals("rewardBalance")) { //	T呗奖励余额：rewardBalance 对应参数：typeValue（账户类型：1=全部明细|3=活动奖励|4=红包|5=其他）
                    intent = new Intent(this, MyAccountAmountActivity.class);
                    String typeValue = json.getString("typeValue");
                    intent.putExtra("typeValue", typeValue);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.startActivity(intent);

                } else if (key.equals("notification")) {
                    intent = new Intent(this, MineMsgCenter.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.startActivity(intent);

                } else if (key.equals("lcsSysActivityRelease")) {
                    intent = new Intent(this, WebActivityCommon.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    String activityUrl = json.getString("activityUrl");
                    intent.putExtra("url", activityUrl);
                    startActivity(intent);

                } else if (key.equals("lcsSysNoticeRelease")) {
                    intent = new Intent(this, WebActivityCommon.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    String msgUrl = json.getString("msgUrl");
                    intent.putExtra("url", msgUrl);
                    startActivity(intent);

                }
            }


        } catch (JSONException e) {
            // e.printStackTrace();
            Logger.e(e.toString());
        }
    }

    public void initView() {
        titlePopup = new TitlePopup(this, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 30);
        titlePopup.addAction(new ActionItem(this, "邀请朋友", R.drawable.iv_invite_friends));


        if (ChatService.getInstance().getAllUnReadMsgCount() > 0) { // 有新客服消息
            titlePopup.addAction(new ActionItem(this, "联系客服", R.drawable.iv_contact_service, R.drawable.iv_point));
        } else {
            titlePopup.addAction(new ActionItem(this, "联系客服", R.drawable.iv_contact_service));
        }
        //titlePopup.addAction(new ActionItem(this, "联系客服", R.drawable.iv_contact_service));
        titlePopup.addAction(new ActionItem(this, "常见问题", R.drawable.iv_common_problem));
        titlePopup.setItemOnClickListener(this);

        headRe = (RelativeLayout) findViewById(R.id.headRe);
        bottomRe = (RadioGroup) findViewById(R.id.mainActivity_RadioGroup);
        infoIv = (ImageView) findViewById(R.id.infoIv);
        mMsgCountTv = (TextView) findViewById(R.id.msgCountTv);
        titleTv = (TextView) findViewById(R.id.titleTv);
        moreIv = (ImageView) findViewById(R.id.moreIv);
        titleTv.setText("T呗");
        setTranslucentStatus(true);
        setHeadViewCoverStateBar(headRe);

        mRadioGroup = (RadioGroup) findViewById(R.id.mainActivity_RadioGroup);
        mRadioGroup.setOnCheckedChangeListener(this);
        mRadioGroup.check(R.id.main_btn_home);
        // pointIv = (ImageView) findViewById(R.id.pointIv);
        infoIv.setOnClickListener(this);
        moreIv.setOnClickListener(this);
    }

    public void setHeadViewCoverStateBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.getLayoutParams().height = PixelUtil.dip2px(activity, 40) + SystemTool.getStatusBarHeight(activity);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String index = intent.getStringExtra("index");
        if (!TextUtils.isEmpty(index)) {
            if (index.equals("1")) {
                mRadioGroup.check(R.id.main_btn_financing);
            }
        }
//Logger.e("onNewIntent");
        JumpIntent(intent);
        Logger.e("msgPushJump==>onResume");
        msgPushJump(intent);
    }

    @Override
    protected boolean isInvestor() {
        return true;
    }

    @Override
    protected void refreshIMUnReadCount() {

    }

    private void pressMenu(int index) {
        titleTv.setText(title [index]);
        cleanFragmentBackStack();
        if (index >= 0 && index < 4) {
            switchFragment(index);
        }
        curIndex = index;
    }

    /**
     * 功能： 获得四个主界面
     *
     * @param index 0 - 3
     * @return
     */
    private Fragment getMainFragment(int index) {
        Fragment f = fragmentManager.findFragmentByTag("main" + index);
        if (fragments[index] == null && f != null) {
            fragments[index] = f;
            return f;
        }

        switch (index) {
            case 0:
                if (fragments[0] == null) {
                    fragments[0] = new FragmentHome();
                    mFragmentTransaction.add(R.id.fragment_box, fragments[index], "main" + index);
                }
                break;
            case 1:
                if (fragments[1] == null) {
                    fragments[1] = new FragmentProduct();
                    mFragmentTransaction.add(R.id.fragment_box, fragments[index], "main" + index);
                }
                break;
            case 2:
                if (fragments[2] == null) {
                    fragments[2] = new FragmentOrg();
                    mFragmentTransaction.add(R.id.fragment_box, fragments[index], "main" + index);
                }
                break;
            case 3:
                if (fragments[3] == null) {
                    fragments[3] = new FragmentMine();
                    mFragmentTransaction.add(R.id.fragment_box, fragments[index], "main" + index);
                }
                break;

            default:
                break;
        }
        return fragments[index];
    }

    public void switchFragment(int index) {
        if (index == curIndex) return;

        ensureTransaction(false);
        mFragmentTransaction.show(getMainFragment(index));
        if (curIndex >= 0) {
            mFragmentTransaction.hide(getMainFragment(curIndex));
        }
        commitTransactions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case C.REFRESH_DATA:
            /*	boolean refreshFlag = MyApp.getInstance().getCurUserSp().getRefresh();
                if (refreshFlag) {
					if (fragments[3] != null) {
						FragmentMine fragment3 = (FragmentMine) fragments[3];
						fragment3.getUserHome(true);
					}
					MyApp.getInstance().getCurUserSp().setRefresh(false);
				}*/
                    break;
                case C.REQUEST_FRAGENT_MINE_SETTING_INFO://点个人设置回来
                    if (fragments[3] != null) {
                        FragmentMine fragmentMine = (FragmentMine) fragments[3];
                        fragmentMine.getUserHome(false);
                    }
                    break;
                case C.REQUEST_ACTIVITY_LOGIN://点个人设置回来
                    titleTv.setText("我的");
                    pressMenu(3);
                    break;
//                case C.REQUEST_ACTIVITY_LOGIN_AND_JUMP_ORG_PRODUCT: //跳转登录后回来并且需要自动跳转产品详情和机构产品详情
//                    Intent intent1 = new Intent(ctx, ProductInfoWebActivity.class);
//                    intent1.putExtra("url", productInfoUrl);
//                    intent1.putExtra("orgName", orgName);
//                    intent1.putExtra("orgNum", orgNum);
//                    intent1.putExtra("JumpToBuy", true);
//                    intent1.putExtra("productId", productId);
//                    startActivity(intent1);
//                    break;
            }
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {

            case R.id.main_btn_home:
                pressMenu(0);
                break;
            case R.id.main_btn_financing:
                pressMenu(1);
                break;
            case R.id.main_btn_interaction:
                pressMenu(2);
                // pointIv.setVisibility(View.GONE);
                break;
            case R.id.main_btn_mine:
//              //  String curUserPhone = TopApp.getInstance().getDefaultSp().getCurUserPhone();
//                if(MyApp.getInstance().getLoginService().isCachPhoneExist()){
                pressMenu(3);
//                }else{
//                    RadioButton radioButton = (RadioButton) mRadioGroup.getChildAt(curIndex);
//                    radioButton.setChecked(true);
//                    Intent intent = new Intent(activity, LoginActivity.class);
//                    intent.putExtra("isLoginFormMainActivity",true);
//                    startActivityForResult(intent,C.REQUEST_ACTIVITY_LOGIN);
//                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.infoIv:
//                if (!MyApp.getInstance().getLoginService().isCachPhoneExist()) {
//                    mRadioGroup.check(R.id.main_btn_mine);
//                    break;
//                }
                MobclickAgent.onEvent(MainActivity.this, "global_message");
                if (isLogined()) {
                    mMsgCountTv.setVisibility(View.GONE);
                    Intent intent8 = new Intent(activity, MineMsgCenter.class);
                    startActivity(intent8);
                } else {
                    showActivity(ctx, LoginActivity.class);
                }
                break;
            case R.id.moreIv:
                titlePopup.show(v);
                break;
        }
    }

    /**
     * 判断登录状态 true：已登录；false ：未登录
     *
     * @return
     */
    public boolean isLogined() {
        return MyApp.getInstance().getLoginService().isCachPhoneExist();
    }


    @Override
    public void onItemClick(ActionItem item, int position) {
//        if (!MyApp.getInstance().getLoginService().isCachPhoneExist()) {
//            mRadioGroup.check(R.id.main_btn_mine);
//            return;
//        }
        switch (position) {
            case 0://邀请朋友
                if (isLogined()) {
                    showActivity(ctx, MyQRCodeActivity.class);
                } else {
                    showActivity(ctx, LoginActivity.class);
                }
                break;
            case 1://联系客服
                MobclickAgent.onEvent(MainActivity.this, "contact_customer_service");
                if (isLogined()) {
                    Intent intent = new Intent(this, ChatActivity.class);
                    intent.putExtra("toChatUsername", MyApp.getInstance().getUserService().getCallServiceEMId());
                    startActivity(intent);
                } else {
                    showActivity(ctx, LoginActivity.class);
                }
                break;
            case 2://常见问题
                MobclickAgent.onEvent(MainActivity.this, "common_problems");
                WebActivityCommon.showThisActivity(ctx, MyApp.getInstance().getDefaultSp().getUrlQuestion(), MyApp.getInstance().getString(R.string.problem));
                break;
        }
    }

    /**
     * 获取消息数量 (公告和通知)
     */
    public void getTopMsgCount() {

        new MyNetAsyncTask(ctx, false) {
            UnReadMsgCountEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().unReadMsgCount(MyApp.getInstance().getLoginService().token);
            }

            @Override
            protected void onPost(Exception e) {
                // 登录成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        UnReadMsgCount data = response.getData();
                        if (data.getBulletinMsgCount() != null && data.getBulletinMsgCount().length() > 0) {
                            unReadImMsgCount = Integer.parseInt(data.getBulletinMsgCount()) + Integer.parseInt(data.getPersonMsgCount());
                            if (unReadImMsgCount > 0) {
                                unReadImMsgCount = unReadImMsgCount > 99 ? 99 : unReadImMsgCount;
                                mMsgCountTv.setText(unReadImMsgCount + "");
                                mMsgCountTv.setVisibility(View.VISIBLE);
                            } else {
                                mMsgCountTv.setVisibility(View.INVISIBLE);
                            }
                        } else {
                            mMsgCountTv.setVisibility(View.INVISIBLE);
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
    private void initOpeningAdvertise() {
        //新开一个线程获取网络数据广告数据并保存到缓存
        new NetCachAsyncTask<AdvertisementOpeningDataEntity>("advertisementopening", 60 * 24 * 30, false, true) {

            @Override
            protected AdvertisementOpeningDataEntity getData() throws Exception {

                return TopApp.getInstance().getHttpService().advertisementOpening();
            }

        }.execute();

    }
}
