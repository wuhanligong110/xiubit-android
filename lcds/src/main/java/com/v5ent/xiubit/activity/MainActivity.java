package com.v5ent.xiubit.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopMainActivity;
import com.toobei.common.entity.AdvertisementOpeningDataEntity;
import com.toobei.common.entity.BaseResponseEntity;
import com.toobei.common.entity.FreshMissionDetail;
import com.toobei.common.entity.FreshMissionEntity;
import com.toobei.common.entity.UserInfo;
import com.toobei.common.network.NetworkObserver;
import com.toobei.common.network.RetrofitHelper;
import com.toobei.common.service.MsgListener;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.NetCachAsyncTask;
import com.toobei.common.utils.TimeUtils;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.entity.CfpLevelWarningData;
import com.v5ent.xiubit.entity.CfpLevelWarningEntity;
import com.v5ent.xiubit.entity.HomepageNewActivityData;
import com.v5ent.xiubit.entity.HomepageNewActivityEntity;
import com.v5ent.xiubit.entity.SignInfoData;
import com.v5ent.xiubit.entity.UnReadMsgCount;
import com.v5ent.xiubit.entity.UnReadMsgCountEntity;
import com.v5ent.xiubit.event.SignStatueEvent;
import com.v5ent.xiubit.fragment.main.FragmentDiscover;
import com.v5ent.xiubit.fragment.main.FragmentHomePage;
import com.v5ent.xiubit.fragment.main.FragmentMine;
import com.v5ent.xiubit.fragment.main.FragmentOrgAndProduct;
import com.v5ent.xiubit.network.httpapi.SignInApi;
import com.v5ent.xiubit.service.CacheJsonLocalManager;
import com.v5ent.xiubit.service.HomePageDialogManager;
import com.v5ent.xiubit.service.LoginService;
import com.v5ent.xiubit.service.ProductService;
import com.v5ent.xiubit.service.UserService;
import com.v5ent.xiubit.util.C;
import com.v5ent.xiubit.util.DynamicSkipManage;
import com.v5ent.xiubit.util.ParamesHelp;
import com.v5ent.xiubit.view.dialog.CfpLevelWarningDialog;
import com.v5ent.xiubit.view.dialog.HomePageNewActivityDialog;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.xsl781.utils.Logger;
import org.xsl781.utils.StringUtils;
import org.xsl781.utils.SystemTool;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends TopMainActivity implements OnCheckedChangeListener, OnClickListener, MsgListener {
    public static boolean isAlive;
    public Fragment[] fragments = new Fragment[4];
    // public MyPagerSlidingTabStrip titleTL;
    public RadioGroup mRadioGroup;

    public int curIndex = -1;// 当前的选项卡
    //    private TextView mTitleTv;
//    private RelativeLayout mHeadRe;
//    private ImageView ivInfo;
    private int unReadImMsgCount = 0;//消息中心未读消息数
    private ImageView hasNoSignIv; //签到标记
    private HomepageNewActivityData mHomepageNewActivityData;
    public boolean HomeNewDiaHasNotShow;  //在首页下次出现时展示首页活动弹窗
    public boolean cfpLevelWarnHasNotShow;  //在首页下次出现时展示首页职级弹窗
    private CfpLevelWarningData mCfpLevelWarningData;
    private int CfpLevelWarningTimeStage;
    private CfpLevelWarningDialog mCfpLevelWarningDialog;
    private View mLoginRl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isAlive = true;
        setContentView(R.layout.activity_main);

        initView();
        getFreshMissionData();//获取新手任务数据
        getTopMsgCount(); //消息中心数量
        // 缓存所有客户基本资料
        initLocalDataAsyn();
        ProductService.getInstance();
        pushJump(getIntent());
        initOpeningAdvertise();

//        }
        msgPushJump(getIntent());
        skipTab(getIntent());
        Logger.d("mainActivity", "oncreate");
        //缓存理财师和客户列表
        if (LoginService.getInstance().isTokenExsit())
            CacheJsonLocalManager.INSTANCE.saveAllCfgList(null, null).saveAllCustomerList(null, null);

    }

    @Override
    protected void initStatusBarStyle() {
        super.initStatusBarStyle();
//        StatusBarUtil.StatusBarLightMode(this);
    }

    @Override
    public void refreshAfterLogin() {
        super.refreshAfterLogin();
        getFreshMissionData();//获取新手任务数据
        getTopMsgCount(); //消息中心数量
        // 缓存所有客户基本资料
        initLocalDataAsyn();
        initHomeFragmentDialog();
        if (LoginService.getInstance().isTokenExsit())
            CacheJsonLocalManager.INSTANCE.saveAllCfgList(null, null).saveAllCustomerList(null, null);
        mLoginRl.setVisibility(View.GONE);
    }

    private void skipTab(Intent intent) {
        try {
            String skipTab = intent.getStringExtra("skipTab"); //skipTab = p1t2 表示第二页，第3个tab
            switch (skipTab.substring(0, 2)) {
                case "p0": //首页
                    mRadioGroup.check(R.id.main_btn_home);
                    break;
                case "p1": //投资
                    mRadioGroup.check(R.id.main_btn_product);
                    if (skipTab.length() == 4) {
                        String tabIndext = skipTab.substring(3, 4);
                        FragmentOrgAndProduct fragment = (FragmentOrgAndProduct) getMainFragment(1);
                        fragment.skipTab(Integer.parseInt(tabIndext));
                    }
                    break;
                case "p2": //理财
                    mRadioGroup.check(R.id.main_btn_liecai);
                    break;
                case "p3": //我的
                    mRadioGroup.check(R.id.main_btn_mine);
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        msgPushJump(intent);
        skipTab(intent);
        pushJump(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String s = MyApp.getInstance().getDefaultSp().getSMS_jump_msg();
        if (!TextUtils.isEmpty(s)) {
            smsJump(s);
            MyApp.getInstance().getDefaultSp().setSMS_jump_msg("");
        }

        checkSignStatu();

    }

    /**
     * 检查是否签到状态，更新底部tab和发现页面标记
     */
    private void checkSignStatu(){
        if (!LoginService.getInstance().isTokenExsit()) {
            hasNoSignIv.setVisibility(View.VISIBLE);
            EventBus.getDefault().post(new SignStatueEvent(false));
            return;
        }
        //检查是否签到
        RetrofitHelper.getInstance().getRetrofit().create(SignInApi.class)
                .signInfo(new ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<BaseResponseEntity<SignInfoData>> () {
            public void bindViewWithDate(BaseResponseEntity<SignInfoData> response) {
                hasNoSignIv.setVisibility(response.getData().hasSigned?  View.GONE : View.VISIBLE);
                EventBus.getDefault().post(new SignStatueEvent(response.getData().hasSigned));
            }
        });

    }

    public void initView() {
        mLoginRl = findViewById(R.id.loginRL);
        findViewById(R.id.loginTv).setOnClickListener(this);
        findViewById(R.id.registerTv).setOnClickListener(this);
        //修改状态栏颜色为黑色
//        StatusBarUtilSty.StatusBarLightMode(this);

        mRadioGroup = (RadioGroup) findViewById(R.id.mainActivity_RadioGroup);
        mRadioGroup.setOnCheckedChangeListener(this);
//        Logger.d("isNewUser", "mainAct" + MyApp.getInstance().isNewUser);
//        V4.0.0新用户判断跳转首页或理财
//        if (MyApp.getInstance().isNewUser) {
//            mRadioGroup.check(R.id.main_btn_liecai);
//        } else {
        mRadioGroup.check(R.id.main_btn_home);
//        }
        hasNoSignIv = (ImageView) findViewById(R.id.hasNoSignIv); // 客户对话消息

    }


    @Override
    public void setHeadViewCoverStateBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            view.getLayoutParams().height = PixelUtil.dip2px(this, 44) + SystemTool.getStatusBarHeight(this);
            view.getLayoutParams().height = SystemTool.getStatusBarHeight(this);
        }
    }

    @Override
    protected boolean isInvestor() {
        return false;
    }

    @Override
    public void refreshIMUnReadCount() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void pressMenu(int index) {
        cleanFragmentBackStack();
        if (index >= 0 && index < 4) {
            switchFragment(index);
        }
        curIndex = index;
    }


    /**
     * 功能： 获得五个主界面
     *
     * @param index 0 - 4
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
                    fragments[0] = new FragmentHomePage();
                    mFragmentTransaction.add(R.id.fragment_box, fragments[index], "main" + index);
                }
                break;
            case 1:
                if (fragments[1] == null) {
                    fragments[1] = new FragmentOrgAndProduct();
                    mFragmentTransaction.add(R.id.fragment_box, fragments[index], "main" + index);
                }

                break;
            case 2:
                if (fragments[2] == null) {
                    fragments[2] = new FragmentDiscover();
                    mFragmentTransaction.add(R.id.fragment_box, fragments[index], "main" + index);
                }
                break;
//            case 3:
//                if (fragments[3] == null) {
//                    fragments[3] = new FragmentCustomer();
//                    mFragmentTransaction.add(R.id.fragment_box, fragments[index], "main" + index);
//                }
//                break;
            case 3:
                if (fragments[3] == null) {
                    fragments[3] = new FragmentMine();
                    mFragmentTransaction.add(R.id.fragment_box, fragments[index], "main" + index);
                }

                break;

        }
        return fragments[index];
    }

    public void switchFragment(int index) {
        if (index == curIndex) return;
        if (index == 3) {
            if (MyApp.getInstance().getLoginService().isTokenExsit()) {
                mLoginRl.setVisibility(View.GONE);
            } else {
                mLoginRl.setVisibility(View.VISIBLE);
            }
        } else {
            mLoginRl.setVisibility(View.GONE);
        }
        ensureTransaction(false);
        mFragmentTransaction.show(getMainFragment(index));
        if (curIndex >= 0) {
            mFragmentTransaction.hide(getMainFragment(curIndex));
        }
        mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); //fragment 过场动画
        commitTransactions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {


                case C.REQUEST_FRAGENT_MINE_SETTING_INFO:// 点个人设置回来
                    if (fragments[3] != null) {
                        FragmentMine fragmentMine = (FragmentMine) fragments[3];
                        fragmentMine.refreshData();
                    }
                    break;


                case C.REQUEST_FRAGENT_MINE_MSG: // 刷新消息数量
                    getTopMsgCount();
                    break;
                case C.REQUEST_FRAGMENT_DEAL_DYNAMIC:
                    break;
                default:

            }
        }
    }

    /**
     * 异步初始化本地数据 初始化所有客户数据
     */
    public void initLocalDataAsyn() {
        new MyNetAsyncTask(this) {

            @Override
            protected void doInBack() throws Exception {
                if (!MyApp.getInstance().getCurUserSp().isCustomLoaded()) {
                    UserService.getInstance().getAllCustomerDataFromServer(false);
                }
            }

            @Override
            protected void onPost(Exception e) {
                if (e != null) {
                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();

        initCurUserFromServer();

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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {


            case R.id.main_btn_home:
                MobclickAgent.onEvent(ctx, "S_0_1");
                pressMenu(0);
                break;
            case R.id.main_btn_product:
                MobclickAgent.onEvent(ctx, "T_0_0");
                pressMenu(1);
                break;
            case R.id.main_btn_liecai:
                pressMenu(2);
                //刷新理财未读消息
                FragmentDiscover fragmentLiecai = (FragmentDiscover) fragments[2];
                break;
            case R.id.main_btn_mine:
                pressMenu(3);
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.loginTv:
                MobclickAgent.onEvent(ctx, "Q_1_1");
                TopApp.loginAndStay = true;
                showActivity(ctx, LoginActivity.class);
                break;
            case R.id.registerTv:
                MobclickAgent.onEvent(ctx, "Q_2_1");
                showActivity(ctx, RegisterPhone.class);
                break;
        }
    }


    public void skipToMineMsgCenter() {
        Intent intent = new Intent(this, MineMsgCenter.class);
        startActivityForResult(intent, C.REQUEST_FRAGENT_MINE_MSG);
    }

    /**
     * 获取消息数量 (公告和通知)
     */
    public void getTopMsgCount() {
        if (TextUtil.isEmpty(MyApp.getInstance().getLoginService().token)) return;
        new MyNetAsyncTask(ctx, false) {
            UnReadMsgCountEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance()
                        .getHttpService()
                        .unReadMsgCount(MyApp.getInstance().getLoginService().token);
            }

            @Override
            protected void onPost(Exception e) {
                // 登录成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        UnReadMsgCount data = response.getData();
                        unReadImMsgCount = Integer.parseInt(data.getBulletinMsgCount()) + Integer
                                .parseInt(data.getPersonMsgCount());
                        if (unReadImMsgCount > 0) {
                            EventBus.getDefault().post(new OnMsgHasNewEvent(true));
                        } else {
                            EventBus.getDefault().post(new OnMsgHasNewEvent(false));
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


    /**
     * 初始化首页弹窗 包括活动弹窗和职级提醒弹窗
     */
    @Override
    protected void initHomeFragmentDialog() {
        getCfpLevelWarningData();

    }

    /**
     * 请求首页职级弹窗数据
     * <p>
     * 以下页面为用户在进入APP时，首页职级弹窗提醒的内容，全局规则如下:
     * • 1、出现时间：20号2:00—25号2:00期间、25号2:00—(本月天数)号2:00期间
     * • 2、出现次数：一个月共出现两次，且这两个时间段期间各出现一次（多次启动，仅在第一次启动时显示）。若用户未在以上时间段期间启动过APP，在其他时间段启动APP时，则不显示。
     * • 3、在以上会出现职级弹窗期间内，首页恰巧也设置了活动弹窗时，优先弹出职级弹窗，活动弹窗顺延至下一次APP启动时弹出。
     */
    private void getCfpLevelWarningData() {
        if (TextUtil.isEmpty(MyApp.getInstance().getLoginService().token)) return;
        new MyNetAsyncTask(ctx, false) {
            CfpLevelWarningEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().getCfpLevelWarning(MyApp.getInstance().getLoginService().token);
                hasGetNewActivityDialogData = true;
            }

            @Override
            protected void onPost(Exception e) {
                // 登录成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        mCfpLevelWarningData = response.getData();
                        //只保留 日 时 分 例如 2017-05-08 11:38:30  ==>  081138
                        String formatDate = mCfpLevelWarningData.getNow().substring(8, 16).replace(" ", "").replace(":", "");
                        //年月拼接
                        String time_yearAndmouth = mCfpLevelWarningData.getNow().substring(0, 7);

                        if (TextUtils.isEmpty(formatDate)) {
                            getNewActivityDialogData();
                            return;
                        }

                        Logger.d("CfpLevelWarningData_time", "formatDate==" + formatDate);

                        if (Integer.parseInt(formatDate) > 200200 && Integer.parseInt(formatDate) < 250200) {
                            //20号2:00—25号2:00期间 第一阶段
                            CfpLevelWarningTimeStage = 1;

                        } else if (Integer.parseInt(formatDate) > 250200 && Integer.parseInt(formatDate) < 310200) {
//                                25号2:00—(本月天数)号2:00期间 第二阶段
                            CfpLevelWarningTimeStage = 2;

                        } else {  //不是指定时间段
                            getNewActivityDialogData();
                            return;
                        }

                        if (CfpLevelWarningTimeStage != 0 && !MyApp.getInstance().getDefaultSp()
                                .getCfpLevelWarningDialogHasShowStatus(time_yearAndmouth, CfpLevelWarningTimeStage)) {
                            if (curIndex == 0) {
                                showCfpLevelWarningDialog();
                            } else {
                                cfpLevelWarnHasNotShow = true;
                            }
                        } else { //此时间段职级弹窗已经弹出过
                            getNewActivityDialogData();
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

    /**
     * 展示首页职级弹窗
     */
    public void showCfpLevelWarningDialog() {
        String time_yearAndmouth = mCfpLevelWarningData.getNow().substring(0, 7);

        cfpLevelWarnHasNotShow = false;
        if (mCfpLevelWarningData != null) {
            mCfpLevelWarningDialog = new CfpLevelWarningDialog(ctx, mCfpLevelWarningData);
            mCfpLevelWarningDialog.show();
            MyApp.getInstance()
                    .getDefaultSp()
                    .setCfpLevelWarningDialogHasShowStatus(time_yearAndmouth, CfpLevelWarningTimeStage, true);
//            }
        }

    }

    /**
     * 请求首页活动弹窗数据
     */
    private void getNewActivityDialogData() {
        new MyNetAsyncTask(ctx, false) {
            HomepageNewActivityEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().getHomepageNewActivity();
            }

            @Override
            protected void onPost(Exception e) {
                // 登录成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        mHomepageNewActivityData = response.getData();
                        if (curIndex == 0) { //当前在首页,展示首页活动弹窗
                            showHomeNewActDialogAndInitShowTip();
                        } else { //当前不在首页 ，置标志位，等待首页显示时再展示弹窗
                            HomeNewDiaHasNotShow = true;
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

    /**
     * 展示首页活动弹窗和处理新手指引
     */
    public void showHomeNewActDialogAndInitShowTip() {
        Logger.d("HomeNewActDialog", "show");
        HomeNewDiaHasNotShow = false;
        if (mHomepageNewActivityData != null && MyApp.getInstance()
                .getDefaultSp()
                .getHomePageNewActivityId() != mHomepageNewActivityData.getId()) {
            //id不同，则说明此活动从未展示过，需要弹窗
            HomePageNewActivityDialog homePageNewActivityDialog = new HomePageNewActivityDialog(ctx, mHomepageNewActivityData);
            homePageNewActivityDialog.show();
            MyApp.getInstance()
                    .getDefaultSp()
                    .setHomePageNewActivityId(mHomepageNewActivityData.getId());
            homePageNewActivityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    FragmentHomePage fragment = (FragmentHomePage) fragments[0];
                    if (fragment != null) {
                        HomePageDialogManager.INSTANCE.show(MainActivity.this, new HomePageDialogManager.ControlCallBack() {
                            @Override
                            public boolean allowShow() {
                                return curIndex == 0;
                            }
                        });
                        fragment.initShowTip();
                    }
                }
            });
        } else {
            FragmentHomePage fragment = (FragmentHomePage) fragments[0];
            if (fragment != null) {
                HomePageDialogManager.INSTANCE.show(MainActivity.this, new HomePageDialogManager.ControlCallBack() {
                    @Override
                    public boolean allowShow() {
                        return curIndex == 0;
                    }
                });
                fragment.initShowTip();
            }
        }
    }


    private void initOpeningAdvertise() {
        //新开一个线程获取网络数据广告数据并保存到缓存
        new NetCachAsyncTask<AdvertisementOpeningDataEntity>("advertisementopening", 60 * 24 * 30, false, true) {

            @Override
            protected AdvertisementOpeningDataEntity getData() throws Exception {

                return TopApp.getInstance()
                        .getHttpService()
                        .advertisementOpening();
            }

        }.execute();

    }

    @Override
    public void skipGestureSetActivity() {
        startActivity(new Intent(this, GestureSetActivity.class));
    }

    @Override
    protected Intent getGestureActivityIntent(TopBaseActivity activity) {
        return new Intent(activity, GestureActivity.class);
    }

    /*
    * push信息，根据接收到的push信息进行相应跳转
    */
    private void pushJump(Intent pIntent) {

        String pushKey = pIntent.getStringExtra("pushKey");
        String data = pIntent.getStringExtra("data");
        String key;
        try {
            if (!TextUtils.isEmpty(data)) {
                JSONObject json = new JSONObject(data);
                key = json.getString("customUrlKey");
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
                if (!TextUtils.isEmpty(pushKey) && pushKey.equals("push")) {
                    if (!TextUtils.isEmpty(key)) {
                        Intent intent = null;
                        if (key.startsWith("signRemind")){  //签到
                            if (!MyApp.getInstance().getLoginService().isTokenExsit()) {
                                MyApp.loginAndStay = true;
                            }
                            intent = new Intent(MainActivity.this, SignInActivity.class);
                            startActivity(intent);
                        } else if (key.startsWith("cusDetai")) {//客户详情页
                            if (!MyApp.getInstance().getLoginService().isTokenExsit()) {
                                MyApp.loginAndStay = true;
                            }
                            String customerId = json.getString("customerId");
                            intent = new Intent(MainActivity.this, CustomerMemberDetialActivity.class);
                            intent.putExtra("userId", customerId);
                            startActivity(intent);
                        } else if (key.equals("myAccount")) {//我的账户页面
                            if (!MyApp.getInstance().getLoginService().isTokenExsit()) {
                                MyApp.loginAndStay = true;
                            }
                            //  2016/12/16 0016 我的账户消息推送
                            String month = json.getString("month");
                            String profitType = json.getString("profitType");
                            intent = new Intent(this, MyIncomeDetailActivity.class);
                            intent.putExtra("date", month);
                            intent.putExtra("monthDesc", StringUtils.toInt(TimeUtils.getMonth(month)) + "月收益");
                            intent.putExtra("intentProfixType", profitType);
                            startActivity(intent);
                        } else if (key.equals("withdraw")) {//提现记录页面
                            if (!MyApp.getInstance().getLoginService().isTokenExsit()) {
                                MyApp.loginAndStay = true;
                            }
                            intent = new Intent(MainActivity.this, WithdrawList.class);
                            startActivity(intent);
                        } else if (key.equals("notification")) { //消息中心
                            if (!MyApp.getInstance().getLoginService().isTokenExsit()) {
                                MyApp.loginAndStay = true;
                            }
                            intent = new Intent(MainActivity.this, MineMsgCenter.class);
                            startActivity(intent);
                        } else if (key.equals("lsjappcs")) { //理财师升级成功  职级
                            intent = new Intent(MainActivity.this, WebActivityCommon.class);
                            intent.putExtra("url", C.URL_mine_level_DEFAULT);
                            intent.putExtra("title", MainActivity.this.getResources()
                                    .getString(R.string.my_rank));
                            startActivity(intent);

                        } else if (key.equals("contactCtmService")) { //客服消息
                            if (!MyApp.getInstance().getLoginService().isTokenExsit()) {
                                MyApp.loginAndStay = true;
                            }
                            intent = new Intent(MainActivity.this, ChatActivity.class);
                            intent.putExtra("toChatUsername", MyApp.getInstance()
                                    .getUserService()
                                    .getCallServiceEMId());
                            startActivity(intent);

                        } else if (key.equals("lcsSysNoticeRelease")) { //消息中心->公告详情 跳轉web
                            String msgUrl = json.getString("msgUrl");
                            intent = new Intent(MainActivity.this, WebActivityCommon.class);
                            intent.putExtra("url", msgUrl);
                            intent.putExtra("title", "");
                            startActivity(intent);

                        } else if (key.equals("lcsSysActivityRelease")) { //活动详情 跳轉web
                            String activityUrl = json.getString("activityUrl");
                            intent = new Intent(MainActivity.this, WebActivityCommon.class);
                            intent.putExtra("url", activityUrl);
                            intent.putExtra("title", "");
                            startActivity(intent);


                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void msgPushJump(Intent intent) {
        // 短信跳转 lcds://lcdsApp/LINEAction=agentDetailLINEOPEN_HEXINDAI_WEB---本地页面跳转 lcds://lcdsApp/LINEAction=http://xxxxxxxxx---本地网页跳转
//        lcds://lcdsApp?bmFtZT1PcmdJbmZvRGV0YWlsQWN0aXZpdHkmb3JnTnVtYmVyPU9QRU5fR0FPU09VWUlfV0VC
//        lcds://lcdsApp?name=OrgInfoDetailActivity&orgNumber=OPEN_GAOSOUYI_WEB
        try {
            Intent i_getvalue = intent;
            String intentAction = i_getvalue.getAction();
            String sms_jum_msg = i_getvalue.getData().toString();
            if (!sms_jum_msg.contains("lcdsApp")) return;  //有风险 需要测试短信跳转
            Logger.e(" msgAndWebJump =====> :" + sms_jum_msg);
            if (Intent.ACTION_VIEW.equals(intentAction)) {
                String s1 = sms_jum_msg;
                smsJump(s1);
            }
//                }

//            }
        } catch (Exception e) {
            Logger.d(e.toString());
        }
    }

    private void smsJump(String s) {
        //lcds://lcdsApp?bmFtZT1PcmdJbmZvRGV0YWlsQWN0aXZpdHkmb3JnTnVtYmVyPU9QRU5fR0FPU09VWUlfV0VC
        Logger.d(" MainActivity smsJump =====> :" + s);
        try {
            //新方案
            if (s.contains("lcds://lcdsApp?")) {
                String paramsStr = s.replace("lcds://lcdsApp?", "");
                DynamicSkipManage.skipActivityFromWeb(ctx, paramsStr);
                return;
            }

            //老的方案
            String[] split = s.split("/LINEAction=");
            String s1 = split[1];
            if (!TextUtils.isEmpty(s1)) {
                if (s1.contains("agentDetail")) {
                    String[] lines = s1.split("LINE");
                    String orgNum = lines[1];
                    if (!TextUtils.isEmpty(orgNum)) {
                        Intent intent = new Intent(this, OrgInfoDetailActivity.class);
                        intent.putExtra("orgName", "");
                        intent.putExtra("orgNumber", orgNum);
                        ctx.showActivity(ctx, intent);
                    }

                } else {
                    Logger.d(" MainActivity sms open web url =====> :" + s1);
                    WebActivityCommon.showThisActivity(ctx, s1, "");
                }
            }


        } catch (Exception e) {
            Logger.e(e.toString());
        }
    }



    /**
     * 获取新手任务数据
     */
    private void getFreshMissionData() {
        if (!MyApp.getInstance().getLoginService().isCachPhoneExist()) return;
        new MyNetAsyncTask(ctx, false) {

            private FreshMissionEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance()
                        .getHttpService()
                        .freshMission(MyApp.getInstance().getLoginService().token);
            }

            @Override
            protected void onPost(Exception e) {
                if (response != null && e == null) {
                    FreshMissionDetail data = response.getData();
                    if (data == null) {
                        return;
                    }
                    String finishAll = data.getFinishAll();
                    //设置新手任务的完成状态
                    MyApp.getInstance()
                            .getDefaultSp()
                            .setFinishFreshMissionState("1".equals(finishAll) ? true : false);
                }

            }
        }.execute();

    }


    public static class OnMsgHasNewEvent {

        public OnMsgHasNewEvent(boolean hasNew) {
            this.hasNew = hasNew;
        }

        public boolean hasNew;

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAlive = false;
    }
}
