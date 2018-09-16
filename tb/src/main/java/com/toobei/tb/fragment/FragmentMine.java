package com.toobei.tb.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.PhotoUtil;
import com.toobei.common.utils.StringUtil;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.MyShowTipsView;
import com.toobei.common.view.dialog.PromptDialogMsg;
import com.toobei.tb.MyApp;
import com.toobei.tb.R;
import com.toobei.tb.activity.MainActivity;
import com.toobei.tb.activity.MineHelpActivity;
import com.toobei.tb.activity.MineRedPacketsActivity;
import com.toobei.tb.activity.MineSetttingActivity;
import com.toobei.tb.activity.MyAccountAmountActivity;
import com.toobei.tb.activity.MyCfpActivity;
import com.toobei.tb.activity.MyInvestDetailActivity;
import com.toobei.tb.activity.MyInvestOrgActivity;
import com.toobei.tb.activity.MyQRCodeActivity;
import com.toobei.tb.activity.WebActivityCommon;
import com.toobei.tb.adapter.GuideViewType;
import com.toobei.tb.entity.UserEntity;
import com.toobei.tb.entity.UserHomeModel;
import com.toobei.tb.event.RefreshMineUiEvent;
import com.toobei.tb.util.C;
import com.toobei.tb.view.dialog.AuthenticationDialog;
import com.toobei.tb.view.popupwindow.FragmentMineLoginPopupWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xsl781.ui.view.RoundImageView;
import org.xsl781.utils.Logger;
import org.xsl781.utils.PixelUtil;
import org.xsl781.utils.SystemTool;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 公司: tophlc 类说明：首页
 *
 * @date 2016-1-04
 */
public class FragmentMine extends FragmentBase implements OnClickListener {


    @BindView(R.id.userIv)
    RoundImageView userIv;
    @BindView(R.id.nameTv)
    TextView nameTv;
    @BindView(R.id.mobileTv)
    TextView mobileTv;
    @BindView(R.id.authenticationTv)
    TextView authenticationTv;
    @BindView(R.id.torightIv)
    ImageView torightIv;
    @BindView(R.id.eyeIv)
    ImageView eyeIv;
    @BindView(R.id.totalProfitTv)
    TextView totalProfitTv;
    @BindView(R.id.totalProfitQuesIv)
    ImageView totalProfitQuesIv;
    @BindView(R.id.arrowMyInvestIv)
    ImageView arrowMyInvestIv;
    @BindView(R.id.accountBalanceTv)
    TextView accountBalanceTv;
    @BindView(R.id.accountBalanceQuesIv)
    ImageView accountBalanceQuesIv;
    @BindView(R.id.accountBalanceArrawIv)
    ImageView accountBalanceArrawIv;
    @BindView(R.id.mycfpArrawIv)
    ImageView mycfpArrawIv;
    @BindView(R.id.mycfpRe)
    RelativeLayout mycfpRe;
    @BindView(R.id.redpacketTv)
    TextView redpacketTv;
    @BindView(R.id.redpacketArrowIv)
    ImageView redpacketArrowIv;
    @BindView(R.id.redpacketRe)
    RelativeLayout redpacketRe;
    @BindView(R.id.inviteFriendTv)
    TextView inviteFriendTv;
    @BindView(R.id.inviteFriendArrowIv)
    ImageView inviteFriendArrowIv;
    @BindView(R.id.inviteFriendRe)
    RelativeLayout inviteFriendRe;
    @BindView(R.id.comQuestionTv)
    TextView comQuestionTv;
    @BindView(R.id.comQuestionIv)
    ImageView comQuestionIv;
    @BindView(R.id.comQuestionRe)
    RelativeLayout comQuestionRe;
    @BindView(R.id.moreTv)
    TextView moreTv;
    @BindView(R.id.moreIv)
    ImageView moreIv;
    @BindView(R.id.moreRe)
    RelativeLayout moreRe;
    @BindView(R.id.pull_refresh_scrollview)
    PullToRefreshScrollView pullRefreshScrollview;
    @BindView(R.id.gotoMyInvestLi)
    LinearLayout gotoMyInvestLi;
    @BindView(R.id.gotoWithDrawAmount)
    LinearLayout gotoWithDrawAmountLi;
    @BindView(R.id.totalProfitRl)
    RelativeLayout totalProfitRl;
    @BindView(R.id.accountBalanceRl)
    RelativeLayout accountBalanceRl;
    @BindView(R.id.MyInvestOrgRl)
    RelativeLayout MyInvestOrgRl;
    private MainActivity activity;
    private View rootView;
    private PullToRefreshScrollView mPullRefreshScrollView;
    //数据
    private String investAmount;
    private String mUserName;
    private String mMobile;
    private String mHeadImage;
    private boolean mAuthentication;
    private String mMsgCount;
    private String mTotalProfit;
    private String mAccountBalance;
    private String mHongbaoCount;
    private PromptDialogMsg mPromptDialogMsg;
    private String mWithdrawAmount;
    private String mInvestAmount;
    private String mOrgAccountCount;
    private FragmentMineLoginPopupWindow popipWindow;

    boolean hasInitData = false; //是否获取了我的的数据了


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) ctx;
        EventBus.getDefault().register(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wd_fragment_mine, container, false);
        ButterKnife.bind(this, rootView);
        initScrollView();
        showLogin();
        showTipsView();
        initListener();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApp.getInstance().getAccountService().isMineNeedRefresh()) getUserHome(false);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
//            Logger.d("onHiddenChanged");
            if (mMobile != null && !mMobile.equals(MyApp.getInstance().getLoginService().curPhone)) { //本地缓存的电话号码与当前页面显示的号码不同
                getUserHome(false);
            }
            showLogin();
            showTipsView();
        } else {
            if (popipWindow != null) {
                popipWindow.dismiss();
            }
        }

    }

    /**
     * 新手引导
     */
    private void showTipsView() {
        if (MyApp.getInstance().getLoginService().isCachPhoneExist()) {
            MyInvestOrgRl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    initShowTipsView();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        MyInvestOrgRl.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }
    }

    private void showLogin() {
        if (MyApp.getInstance().getLoginService().isCachPhoneExist()) {
            if (!hasInitData) {
                getUserHome(false);
                //初始化用户绑卡信息
                MyApp.getInstance().getAccountService().accountGetUserBindCard(activity, null);
                hasInitData = true;
            }
            // 登录进入我的界面时才缓存用户数据
            //已改到登录成功后缓存
//            activity.initCurUserFromServer();
            if (popipWindow != null) {
                popipWindow.dismiss();
            }
        } else {
            if (popipWindow == null) {
                popipWindow = new FragmentMineLoginPopupWindow(ctx);
                if (Build.VERSION.SDK_INT >= 21) {
                    popipWindow.setHeight(TopApp.displayHeight - PixelUtil.dip2px(ctx, 47) - SystemTool.getStatusBarHeight(activity));
                } else {
                    popipWindow.setHeight(TopApp.displayHeight - PixelUtil.dip2px(ctx, 47));
                }
            }
            View decorView = activity.getWindow().getDecorView();
            popipWindow.showAtLocation(decorView, Gravity.TOP, 0, 0);

        }
    }

    /**
     * 新手引导图片
     */
    private void initShowTipsView() {
        MyShowTipsView myShowTipsView = new MyShowTipsView(ctx, MyInvestOrgRl, GuideViewType.FRAGMENTMINE_GUIDE_MYINVESTORG.getValue(), PixelUtil.dip2px(ctx, 45), PixelUtil.dip2px(ctx, 50), GuideViewType.FRAGMENTMINE_GUIDE_MYINVESTORG.toString());
//        myShowTipsView.setDisplayOneTime(false);
        myShowTipsView.show(ctx);
    }


    private void initListener() {
        userIv.setOnClickListener(this);
        totalProfitQuesIv.setOnClickListener(this);
        eyeIv.setOnClickListener(this);
//        arrowTotalProfitIv.setOnClickListener(this);
        gotoMyInvestLi.setOnClickListener(this);
        accountBalanceQuesIv.setOnClickListener(this);
//        accountBalanceArrawIv.setOnClickListener(this);
        gotoWithDrawAmountLi.setOnClickListener(this);
        mycfpRe.setOnClickListener(this);
        redpacketRe.setOnClickListener(this);
        inviteFriendRe.setOnClickListener(this);
        comQuestionRe.setOnClickListener(this);
        moreRe.setOnClickListener(this);
//        totalProfitRl.setOnClickListener(this);
//        accountBalanceRl.setOnClickListener(this);
        authenticationTv.setOnClickListener(this);
        MyInvestOrgRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.userIv: // 用户信息
                intent = new Intent(activity, MineSetttingActivity.class);
                activity.startActivityForResult(intent, C.REQUEST_FRAGENT_MINE_SETTING_INFO);
                break;
            case R.id.eyeIv:  //累计收益显示与隐藏
                boolean eyeFlag = MyApp.getInstance().getDefaultSp().getShowMyAccount();
                if (eyeFlag) {
                    MyApp.getInstance().getDefaultSp().setShowMyAccount(false);
                    totalProfitTv.setText("****");
                    eyeIv.setBackgroundResource(R.drawable.img_login_pwd_eye_close);
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) totalProfitQuesIv.getLayoutParams();
                    lp.topMargin = com.toobei.common.view.Util.dip2px(ctx, -5);
                    totalProfitQuesIv.setLayoutParams(lp);
                } else {
                    MyApp.getInstance().getDefaultSp().setShowMyAccount(true);
                    totalProfitTv.setText(StringUtil.formatNumber(mTotalProfit));
                    eyeIv.setBackgroundResource(R.drawable.img_login_pwd_eye_open);
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) totalProfitQuesIv.getLayoutParams();
                    lp.topMargin = com.toobei.common.view.Util.dip2px(ctx, 0);
                    totalProfitQuesIv.setLayoutParams(lp);
                }
                break;

            case R.id.gotoMyInvestLi:  // 我的投资
                intent = new Intent(activity, MyInvestDetailActivity.class);
                intent.putExtra("title", "我的投资");
                startActivity(intent);
                break;
            case R.id.gotoWithDrawAmount: // 提现
                //进入T呗奖励余额
                startActivity(new Intent(ctx, MyAccountAmountActivity.class));
                break;
            case R.id.MyInvestOrgRl:  //我的投资平台
                intent = new Intent(activity, MyInvestOrgActivity.class);
                startActivity(intent);
                break;
            case R.id.mycfpRe: //我的理财师
                intent = new Intent(activity, MyCfpActivity.class);
                startActivity(intent);
                break;
            case R.id.redpacketRe:  // 我的红包
                intent = new Intent(activity, MineRedPacketsActivity.class);
                startActivity(intent);
                break;
            case R.id.inviteFriendRe: // 邀请朋友
                activity.showActivity(activity, MyQRCodeActivity.class);
                break;
            case R.id.comQuestionRe:

                WebActivityCommon.showThisActivity((TopBaseActivity) ctx, MyApp.getInstance().getDefaultSp().getUrlQuestion(), "", true);
                break;

            case R.id.moreRe:  //更多
                // 帮助
                intent = new Intent(activity, MineHelpActivity.class);
                startActivity(intent);
                break;
            case R.id.totalProfitQuesIv:  //累计收益说明
                mPromptDialogMsg = new PromptDialogMsg(activity, getString(R.string.dialog_mine_totalProfitExplain), "关闭");
                mPromptDialogMsg.show();
                break;
            case R.id.accountBalanceQuesIv: //T呗奖励余额说明
                mPromptDialogMsg = new PromptDialogMsg(activity, getString(R.string.dialog_mine_accountBalanceExplain), "关闭");
                mPromptDialogMsg.show();
                break;
            case R.id.authenticationTv:
                new AuthenticationDialog(ctx).show();
                break;
//            case R.id.qrcodeIv:  //消息
//                getMyQRCode(MyApp.getInstance().getLoginService().token, true);
//                break;
//		case R.id.questionRe:
//			WebActivityCommon.showThisActivity((TopBaseActivity) getActivity(), MyApp.getInstance()
//					.getDefaultSp().getUrlQuestion(),
//					MyApp.getInstance().getString(R.string.problem));
//			break;
        }
    }


    /*
     * 初始化下拉刷新组建
     */
    private void initScrollView() {
        mPullRefreshScrollView = (PullToRefreshScrollView) rootView.findViewById(R.id.pull_refresh_scrollview);
        mPullRefreshScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                activity.getTopMsgCount();
                getUserHome(true);
            }
        });
        mPullRefreshScrollView.setMode(Mode.PULL_FROM_START);
        ScrollView mScrollView = mPullRefreshScrollView.getRefreshableView();
        mScrollView.setVerticalScrollBarEnabled(false);
    }


    /*
     * 请求我的信息
     */
    public void getUserHome(final boolean flag) {
        new MyNetAsyncTask(ctx, flag) {
            UserEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().userHome(MyApp.getInstance().getLoginService().token);
            }

            @Override
            protected void onPost(Exception e) {
                mPullRefreshScrollView.onRefreshComplete();
                // 登录成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        MyApp.getInstance().getAccountService().setMineNeedRefresh(false);
                        UserHomeModel data = response.getData();
                        if (isAdded()) {
                            refreshUI(data);
                        }
                    } else {
                        if (isAdded()) {
                            ToastUtil.showCustomToast(response.getErrorsMsgStr());
                        }
                    }
                } else {
                    if (isAdded()) {
                        ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                    }
                }
            }


        }.execute();
    }

    private void refreshUI(UserHomeModel data) {

        mUserName = data.getUserName(); // 用户名称
        mMobile = data.getMobile();  // 手机号码
        mHeadImage = data.getHeadImage();  //头像
        mAuthentication = data.getBindBankCard(); // 是否实名验证()
        mMsgCount = data.getMsgCount();  // 消息数
        mTotalProfit = data.getTotalProfit();  // 总收益(元)
        mAccountBalance = data.getAccountBalance();  //账户余额
        mHongbaoCount = data.getHongbaoCount();  // 可用红包数
        // 提现中金额
        mWithdrawAmount = data.getWithdrawAmount();
        // 投资总额
        mInvestAmount = data.getInvestAmount();
        //机构账户数量
        mOrgAccountCount = data.getOrgAccountCount();


        //头像
        PhotoUtil.displayUserFace(mHeadImage, userIv);
        try {
            TopApp.getInstance().getLoginService().getCurUser().setHeadImage(mHeadImage);
        } catch (Exception e) {

            Logger.e(e.toString());
        }

        boolean eyeFlag = MyApp.getInstance().getDefaultSp().getShowMyAccount();

        if (!TextUtils.isEmpty(mMobile)) {
            StringBuilder sb = new StringBuilder(mMobile);
            String phoneStr = sb.replace(3, 7, "****") + "";
            mobileTv.setText(phoneStr);
        }
        if (mAuthentication) {
            authenticationTv.setText("已认证");
            TopApp.getInstance().getCurUserSp().setBoundedBankCard("true");
            authenticationTv.setBackgroundResource(R.drawable.iv_smrz_bg);
            torightIv.setVisibility(View.GONE);
            authenticationTv.setClickable(false);
        } else {
            authenticationTv.setText("未认证");
            TopApp.getInstance().getCurUserSp().setBoundedBankCard("false");
            authenticationTv.setBackgroundResource(R.drawable.iv_smrz_bg_gray);
            torightIv.setVisibility(View.VISIBLE);
            authenticationTv.setClickable(true);
        }

        if (TextUtils.isEmpty(mUserName)) {
            nameTv.setVisibility(View.GONE);
        } else {
            nameTv.setVisibility(View.VISIBLE);
            nameTv.setText(mUserName);
        }
        //累计收益
        if (eyeFlag) {
            totalProfitTv.setText(StringUtil.formatNumber(mTotalProfit));
        } else {
            totalProfitTv.setText("****");
        }
        if (!TextUtils.isEmpty(mTotalProfit)) {
            totalProfitQuesIv.setVisibility(View.VISIBLE);
        }
        accountBalanceTv.setText(StringUtil.formatNumber(mAccountBalance));
//        if (!TextUtils.isEmpty(mTotalProfit)) {
//            accountBalanceQuesIv.setVisibility(View.VISIBLE);
//        }
        if ("0".equals(mHongbaoCount)) {
            String format = String.format(ctx.getString(R.string.fragment_mine_redpackage_des), "无");
            redpacketTv.setText(format);
        } else {
            String format = String.format(ctx.getString(R.string.fragment_mine_redpackage_des), mHongbaoCount + "个");
            redpacketTv.setText(format);
        }
    }

    /**
     * 登录或者退出后隐藏或者显示登录窗口
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUI(RefreshMineUiEvent event) {
        showLogin();
        if (event.isLogin) {
            activity.getTopMsgCount();
            if (isAdded()) {
                getUserHome(false);  //避免登录完成后回来界面没有刷新
            }
        }

    }
    /*
     * 请求我的二维码
     */
//    private void getMyQRCode(final String token, final boolean flag) {
//        new MyNetAsyncTask(ctx, flag) {
//            MyQRCodeEntity response;
//
//            @Override
//            protected void doInBack() throws Exception {
//                response = MyApp.getInstance().getHttpService().getMyQRCode(token);
//
//            }
//
//            @Override
//            protected void onPost(Exception e) {
//                if (e == null && response != null) {
//                    if (response.getCode().equals("0")) {
//                        MobclickAgent.onEvent(getActivity(), "my_qrcode");
//
//                        url = response.getData().getUrl();
//                        if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(phoneStr)) {
//                            if (myQRdialog == null) {
//                                if (!url.startsWith("http"))
//                                    url = MyApp.getInstance().getHttpService().getImageServerBaseUrl() + url;
//                                myQRdialog = new MyQRCodeDialog(activity, url);
//                            }
//                            myQRdialog.show();
//                            myQRdialog.setBtnBgResourceId(R.drawable.btn_common_round_selector);
//                        } else {
//                            ToastUtil.showCustomToast("加载失败，请重试");
//                        }
//                    } else {
//                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
//                    }
//                } else {
//                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
//                }
//            }
//        }.execute();
//    }
}
