package com.v5ent.xiubit.fragment.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.BaseResponseEntity;
import com.toobei.common.entity.BundBankcardData;
import com.toobei.common.entity.BundBankcardDataEntity;
import com.toobei.common.entity.ImageResponseEntity;
import com.toobei.common.entity.PersonInfoData;
import com.toobei.common.entity.PersonInfoEntity;
import com.toobei.common.entity.PersonInfoFundEntity;
import com.toobei.common.event.CardBindSuccessEvent;
import com.toobei.common.event.UpLoadHeadImageEvent;
import com.toobei.common.network.NetworkObserver;
import com.toobei.common.network.RetrofitHelper;
import com.toobei.common.network.httpapi.MineInfoApi;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.NetworkUtils;
import com.toobei.common.utils.PhotoUtil;
import com.toobei.common.utils.StringUtil;
import com.toobei.common.utils.SystemFunctionUtil;
import com.toobei.common.utils.UpdateViewCallBack;
import com.toobei.common.view.Util;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.activity.AboutUs;
import com.v5ent.xiubit.activity.AccountBookActivity;
import com.v5ent.xiubit.activity.AwardMoneyActivity;
import com.v5ent.xiubit.activity.BalanceActivity;
import com.v5ent.xiubit.activity.CardManagerAdd;
import com.v5ent.xiubit.activity.CfgMemberActivity;
import com.v5ent.xiubit.activity.CouponActivity;
import com.v5ent.xiubit.activity.CustomerMemberActivity;
import com.v5ent.xiubit.activity.InvestRecordCalendarActivity;
import com.v5ent.xiubit.activity.InviteCfpQr;
import com.v5ent.xiubit.activity.MainActivity;
import com.v5ent.xiubit.activity.MineFundActivity;
import com.v5ent.xiubit.activity.MineSetttingActivity;
import com.v5ent.xiubit.activity.MyAccountIncomeActivity;
import com.v5ent.xiubit.activity.MyCustomerService;
import com.v5ent.xiubit.activity.MyIncomeDetailActivity;
import com.v5ent.xiubit.activity.MyNetLoanActivity;
import com.v5ent.xiubit.activity.PaymentCalendarActivity;
import com.v5ent.xiubit.activity.WebActivityCommon;
import com.v5ent.xiubit.entity.SignStatisticsData;
import com.v5ent.xiubit.event.FreshMisssionEvent;
import com.v5ent.xiubit.event.InvestRecordRefreshEvent;
import com.v5ent.xiubit.event.RedPackageSendSucessEvent;
import com.v5ent.xiubit.event.WithDrawSuccessEvent;
import com.v5ent.xiubit.fragment.FragmentBase;
import com.v5ent.xiubit.network.httpapi.SignInApi;
import com.v5ent.xiubit.service.AccountService;
import com.v5ent.xiubit.service.JumpInsuranceService;
import com.v5ent.xiubit.service.LoginService;
import com.v5ent.xiubit.util.C;
import com.v5ent.xiubit.util.ParamesHelp;
import com.v5ent.xiubit.view.MyScrollView;
import com.v5ent.xiubit.view.MyTextView;
import com.v5ent.xiubit.view.loadstatue.LoadStatueView;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xsl781.ui.view.RoundImageView;
import org.xsl781.utils.Logger;
import org.xsl781.utils.SystemTool;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * 公司: tophlc 类说明：Fragment-我的
 *
 * @date 2015-9-16
 */
public class FragmentMine extends FragmentBase {


    @BindView(R.id.mine_img_user_face)
    RoundImageView mineImgUserFace; //头像
    @BindView(R.id.text_mine_user_phone)
    TextView textMineUserPhone;
    @BindView(R.id.msgPointIv)
    ImageView msgPointIv;

    //    @BindView(R.id.iv_mine_iv_hidden_income)
//    ImageView ivMineIvHiddenIncome;

    //    @BindView(R.id.img_mine_account_question)
//    ImageView questionIV;// 我的账户

    @BindView(R.id.refreshView)
    SmartRefreshLayout mRefreshView;
    @BindView(R.id.status_bar)
    View mStatusBar;
    @BindView(R.id.infoIv)
    ImageView mInfoIv;
    @BindView(R.id.headRe)
    LinearLayout mHeadRe;
    @BindView(R.id.topTitleAndStatueLl)
    LinearLayout mTopTitleAndStatueLl;
    @BindView(R.id.mine_root_ll)
    LinearLayout mMineRootLl;
    @BindView(R.id.addRankCardTv)
    TextView mAddRankCardTv;
    @BindView(R.id.monthIncomeTv)
    MyTextView mMonthIncomeTv;
    @BindView(R.id.cfgLevelTv)
    TextView mCfgLevelTv;


    @BindView(R.id.text_mine_account)
    MyTextView mTextMineAccount;
    @BindView(R.id.mine_accout_ll)
    LinearLayout mMineAccoutLl;

    TextView redPacketText2;
    @BindView(R.id.bindCardRemindLl)
    LinearLayout bindCardRemindLl;
    @BindView(R.id.bind_card_remind_tv)
    TextView mBindCardRemindTv;
    @BindView(R.id.MsgRl)
    RelativeLayout mMsgRl;
    @BindView(R.id.totalIncomeTv)
    MyTextView mTotalIncomeTv;
    @BindView(R.id.newTranRecordStatusTv)
    TextView mNewTranRecordStatusTv;
    @BindView(R.id.newPaymentRecordStatusTv)
    TextView mNewPaymentRecordStatusTv;
    @BindView(R.id.teamMemberTv)
    TextView mTeamMemberTv;
    @BindView(R.id.customerMemberTv)
    TextView mCustomerMemberTv;
    @BindView(R.id.gradePriviTv)
    TextView mGradePriviTv;
    @BindView(R.id.netLoanTv)
    TextView mNetLoanTv;
    @BindView(R.id.myNetLoanLl)
    LinearLayout mMyNetLoanLl;
//    @BindView(R.id.myFundLl)
//    LinearLayout mMyFundLl;
//    @BindView(R.id.myInsuranceLl)
//    LinearLayout mMyInsuranceLl;
    @BindView(R.id.coupon)
    TextView mCoupon;
    @BindView(R.id.accountBookTv)
    TextView mAccountBookTv;
    @BindView(R.id.accountSettingLl)
    LinearLayout mAccountSettingLl;
    @BindView(R.id.helpCenterLl)
    LinearLayout mHelpCenterLl;
//    @BindView(R.id.mineFundTv)
//    TextView mineFundTv;
    @BindView(R.id.scrollV)
    MyScrollView scrollV;
    @BindView(R.id.headBottomLine)
    View headBottomLine;
    @BindView(R.id.customerServiceIv)
    ImageView customerServiceIv;
    @BindView(R.id.awardMoneyTv)
    TextView awardMoneyTv;
    private MainActivity activity;

    private View rootView;
    private PersonInfoData mineHome;

    private Handler mHandler;
    private Runnable mShakeRunnable;
    private LoadStatueView mLoadStatueView;
    private String balanceMoney = "0.00" ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        initBus();
        super.onCreate(savedInstanceState);
        activity = (MainActivity) ctx;
        AccountService.getInstance().verifyPayPwdState(activity, null);//进入时检查支付密码设置状态


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, rootView);
        mLoadStatueView = new LoadStatueView(ctx, rootView, new LoadStatueView.ClickCallBack() {
            @Override
            public void onRefreshNetClick() {
                refreshData();
            }
        });
        initView();
        refreshData();
        MobclickAgent.onEvent(ctx, "W_0_0");
        return mLoadStatueView.getRootView();
    }

    @Override
    public void refreshAfterLogin() {
        super.refreshAfterLogin();
        refreshData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApp.getInstance().getAccountService().isMineNeedRefresh()) {
            refreshData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        setHeadViewCoverStateBar(mStatusBar);
        initRefreshView();
//        autoChangeTopHeadColor();

        mineImgUserFace.setBorderThickness(0);

    }

    private void autoChangeTopHeadColor() {
        final int headHeight = SystemTool.getStatusBarHeight(mAppContext) + Util.dip2px(mAppContext, 44);
        scrollV.setScrollViewListener(new MyScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
               if (y > 0 && y < headHeight) {
                    textMineUserPhone.setTextColor(Color.parseColor("#ccffff"));
                    mCfgLevelTv.setTextColor(Color.parseColor("#ccffff"));
                    float scale = (float) y / headHeight;
                    float alpha = (255 * scale);
                    mTopTitleAndStatueLl.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                    textMineUserPhone.setAlpha(alpha);
                    mCfgLevelTv.setAlpha(alpha);
                    mInfoIv.setImageResource(R.drawable.img_msg_icon_white);
                    headBottomLine.setVisibility(View.GONE);
                } else if (y>= headHeight) {
                    textMineUserPhone.setTextColor(ctx.getResources().getColor(R.color.text_black_common));
                    mCfgLevelTv.setTextColor(ctx.getResources().getColor(R.color.text_black_common));
                    mInfoIv.setImageResource(R.drawable.img_msg_icon);
                    mTopTitleAndStatueLl.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
                   headBottomLine.setVisibility(View.VISIBLE);
                } else {
                   textMineUserPhone.setTextColor(Color.parseColor("#ccffff"));
                   mCfgLevelTv.setTextColor(Color.parseColor("#ccffff"));
                   textMineUserPhone.setAlpha(1);
                   mCfgLevelTv.setAlpha(1);
                   mInfoIv.setImageResource(R.drawable.img_msg_icon_white);
                   mTopTitleAndStatueLl.setBackgroundColor(Color.argb(0, 255, 255, 255));
                   headBottomLine.setVisibility(View.GONE);
               }
            }
        });
    }


    private void initRefreshView() {
        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshData();
                mRefreshView.finishRefresh();
            }
        });
    }

    public void initShowTip() {
//        guideViewGr1.getViewTreeObserver()
//                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//
//                        initShowTipsView();
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                            guideViewGr1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                        }
//                    }
//                });
    }

    /**
     * 新手引导图片
     */
    private void initShowTipsView() {
        //引导1
//        MyShowTipsView myShowTipsView1 = new MyShowTipsView(ctx, guideViewGr1, GuideViewType.FRAGMENT_MINE_GUIDE1
//                .getValue(),//
//                PixelUtil.dip2px(ctx, 10), PixelUtil.dip2px(ctx, -90), GuideViewType.FRAGMENT_MINE_GUIDE1
//                .toString());
//        myShowTipsView1.setDisplayOneTime(false);

        //引导2
//        final MyShowTipsView myShowTipsView2 = new MyShowTipsView(ctx, guideViewGr2, GuideViewType.FRAGMENT_MINE_GUIDE2
//                .getValue(),//
//                PixelUtil.dip2px(ctx, 20), PixelUtil.dip2px(ctx, -90), GuideViewType.FRAGMENT_MINE_GUIDE2
//                .toString());
//        myShowTipsView2.setDisplayOneTime(false);
//
//
//        myShowTipsView1.setOnShowTipHideListener(new MyShowTipsView.onShowTipHideListener() {
//            @Override
//            public void onShowTipHide() {
//                myShowTipsView2.show(ctx);
//            }
//        });
//
//        if (activity.curIndex == 3 && MyApp.getInstance().getLoginService().isTokenExsit()) {
//            myShowTipsView1.show(ctx);
//        }

    }

    private void refreshView() {
        balanceMoney = mineHome.totalAmount;
        textMineUserPhone.setText(StringUtil.formatPhone(mineHome.mobile));

        if (mineHome == null) {
            return;
        }
        String headImage = mineHome.headImage;
        PhotoUtil.displayUserFace(headImage, mineImgUserFace);
        try {
            TopApp.getInstance().getLoginService().getCurUser().setHeadImage(headImage);
        } catch (Exception e) {

            Logger.e(e.toString());
        }
        mCfgLevelTv.setText("(" + mineHome.grade + ")");

        mTextMineAccount.setText(mineHome.totalAmount);//余额
        mMonthIncomeTv.setText(mineHome.monthIncome);

        mTeamMemberTv.setText(mineHome.teamMember);
        mNewPaymentRecordStatusTv.setText(mineHome.paymentDate);
        mNetLoanTv.setText(mineHome.netLoan);
        mGradePriviTv.setText(mineHome.gradePrivi);
        mCoupon.setText(mineHome.coupon);
        mTotalIncomeTv.setText(mineHome.totalIncome);
        mNewTranRecordStatusTv.setText(mineHome.tranRecordDate);
        mCustomerMemberTv.setText(mineHome.customerMember);
        mAccountBookTv.setText(mineHome.accountBook);


    }

    /**
     * 判断是否有未读投资记录
     *
     * @return
     */
    public boolean hasNoReadInvestRecord() {
        return !("0".equals(mineHome.msgCount));
    }


    public void refreshData() {
        if (NetworkUtils.isConnected() || !LoginService.getInstance().isTokenExsit()) {
            mLoadStatueView.showContantView();
        } else {
            mLoadStatueView.showFailView();
            return;
        }
        if (TextUtil.isEmpty(MyApp.getInstance().getLoginService().token)) {

            return;
        }

//        checkBoundCard();   // 4.5.4 取消绑卡入口和红包提示
        activity.getTopMsgCount();
        getMineInfo();
        getAwardMoneyInfo();
    }

    private void getAwardMoneyInfo() {
        RetrofitHelper.getInstance().getRetrofit().create(SignInApi.class)
                .signStatistics(new ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new  NetworkObserver<BaseResponseEntity<SignStatisticsData>>() {
                    @Override
                    public void bindViewWithDate(BaseResponseEntity<SignStatisticsData> response) {
                        awardMoneyTv.setText(response.getData().leftBouns + "元");
                    }


                });
    }

    /**
     * 检查绑卡
     */
    private void checkBoundCard() {
        try {
            TopApp.getInstance().getHttpService().bindCardStatue()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new NetworkObserver<BundBankcardDataEntity>() {
                        @Override
                        public void bindViewWithDate(BundBankcardDataEntity response) {
                            BundBankcardData data = response.getData();
                            bindCardRemindLl.setVisibility(data.isBundBankcard() ? View.GONE : View.VISIBLE);
                            if (!data.isBundBankcard()) {             //绑卡
                                MyApp.getInstance().getAccountService().setIsBounded("false");
                            } else {
                                MyApp.getInstance().getAccountService().setIsBounded("true");
                            }
                            if (data.onceMoreBindCard) {
                                mBindCardRemindTv.setText("为了您的资金安全，请先绑定银行卡");
                            } else {
                                mBindCardRemindTv.setText("绑定银行卡送20元理财红包");
                            }
                            initShowTipsView();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void getMineInfo() {
        RetrofitHelper.getInstance()
                .getRetrofit()
                .create(MineInfoApi.class)
                .personInfo(new ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<PersonInfoEntity>() {
                    @Override
                    public void bindViewWithDate(@NonNull PersonInfoEntity response) {
                        mineHome = response.getData();
                        refreshView();
                    }
                });
   /*     RetrofitHelper.getInstance()
                .getRetrofit()
                .create(MineInfoApi.class)
                .personInfoFund(new ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<PersonInfoFundEntity>() {
                    @Override
                    public void bindViewWithDate(@NonNull PersonInfoFundEntity response) {
                        mineFundTv.setText(response.getData().fundAmount);
                    }
                });*/

    }


    @OnClick({R.id.aboutLiecai,R.id.headRe,R.id.customerServiceIv,R.id.awardMoneyEntry, R.id.PaymentCalendarEntry, R.id.inviteCfgEntry, R.id.jobGradeEntry, R.id.couponEntry, R.id.accountBookEntry, R.id.investRecordEntry, R.id.cfgTeamEntry, R.id.customerMemberEntry, R.id.accountSettingLl, R.id.bindCardRemindLl, R.id.helpCenterLl, R.id.infoIv,
            R.id.myNetLoanLl, /*R.id.myFundLl, R.id.myInsuranceLl,*/R.id.totalIncomeTab,R.id.monthIncomeTab,R.id.balanceTab})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.aboutLiecai:  //关于貅比特
                ctx.startActivity(new Intent(ctx, AboutUs.class));
                break;
            case R.id.awardMoneyEntry: //奖励金
                MobclickAgent.onEvent(ctx, "W_10_3");
                ctx.startActivity(new Intent(ctx, AwardMoneyActivity.class));
                break;
            case R.id.PaymentCalendarEntry: //回款日历
                MobclickAgent.onEvent(ctx, "W_3_3");
                ctx.startActivity(new Intent(ctx, PaymentCalendarActivity.class));
                break;
            case R.id.inviteCfgEntry:  //邀请理财师
                MobclickAgent.onEvent(ctx, "W_6_6");
                intent = new Intent(ctx, InviteCfpQr.class);
                ctx.startActivity(intent);
                break;
            case R.id.jobGradeEntry: //职级特权
                MobclickAgent.onEvent(ctx, "W_6_5");
                WebActivityCommon.showThisActivity((TopBaseActivity) ctx, C.URL_mine_level_DEFAULT, "");
                break;
            case R.id.couponEntry: //优惠券
                MobclickAgent.onEvent(ctx, "W_6_1");
                startActivity(new Intent(ctx, CouponActivity.class));
                break;
            case R.id.accountBookEntry:  //记账本
                MobclickAgent.onEvent(ctx, "W_6_4");
                startActivity(new Intent(ctx, AccountBookActivity.class));
                break;
            case R.id.investRecordEntry: //投资记录
                MobclickAgent.onEvent(ctx, "W_3_2");
                startActivity(new Intent(ctx, InvestRecordCalendarActivity.class));
                break;
            case R.id.cfgTeamEntry: //理财师团队
                MobclickAgent.onEvent(ctx, "W_8_1");

                startActivity(new Intent(ctx, CfgMemberActivity.class));
                break;
            case R.id.customerMemberEntry: //客户成员
                MobclickAgent.onEvent(ctx, "W_8_1");
                startActivity(new Intent(ctx, CustomerMemberActivity.class));
                break;
            case R.id.myNetLoanLl:  //网贷
                MobclickAgent.onEvent(ctx, "W_9_1");
                startActivity(new Intent(ctx, MyNetLoanActivity.class));
                break;
/*            case R.id.myInsuranceLl:  //保险
                new JumpInsuranceService(ctx, "", JumpInsuranceService.TAG_USER_CENTER).run();
                break;*/

            case R.id.bindCardRemindLl:// 绑卡认证

                startActivity(new Intent(ctx, CardManagerAdd.class));
                break;

            case R.id.balanceTab: //余额
                intent = new Intent(ctx,BalanceActivity.class);
                intent.putExtra("balanceMoney",balanceMoney) ;
                ctx.startActivity(intent);
                break;
            case R.id.monthIncomeTab:  //本月收益
                intent = new Intent(ctx, MyIncomeDetailActivity.class);
                String date = new SimpleDateFormat("yyyy-MM").format(new Date());
                intent.putExtra("date", date);  //时间 格式 2015-10
                intent.putExtra("monthDesc", "本月收益");
                ctx.startActivity(intent);
                break;

            case R.id.totalIncomeTab:// 累计收益
                MobclickAgent.onEvent(ctx, "W_2_1");
                intent = new Intent(activity, MyAccountIncomeActivity.class);
//                intent.putExtra("timeType", DateShowTimeType.MONTH.getValue());
//                intent.putExtra("time", TimeUtils.getXnMonthTimeStr(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1));
                activity.showActivity(activity, intent);
                break;

            case R.id.helpCenterLl:  //帮助中心
                MobclickAgent.onEvent(ctx, "W_6_2");
                intent = new Intent(ctx, MyCustomerService.class);
                startActivity(intent);
                break;
            case R.id.accountSettingLl:
                MobclickAgent.onEvent(ctx, "W_6_3");
//            case R.id.mine_img_user_face: //个人中心
            case R.id.headRe:
                MobclickAgent.onEvent(ctx, "W_1_2");
                intent = new Intent(activity, MineSetttingActivity.class);
                if (mineHome != null) {
                    intent.putExtra("jobGrade", mineHome.grade);
                }
                activity.startActivityForResult(intent, C.REQUEST_FRAGENT_MINE_SETTING_INFO);
                break;

            case R.id.infoIv:
                MobclickAgent.onEvent(ctx, "W_1_1");
                activity.skipToMineMsgCenter();
                if (mHandler != null) {
                    mHandler.removeCallbacks(mShakeRunnable);
                }
                break;
/*            case R.id.myFundLl:
                MobclickAgent.onEvent(ctx, "W_4_1");
                ctx.startActivity(new Intent(ctx, MineFundActivity.class));
                break;*/
            case R.id.customerServiceIv:
                String callPhoneNum = getString(R.string.customer_service_num);
                SystemFunctionUtil.INSTANCE.CallServicePhone(ctx, callPhoneNum);
                break;
            default:
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)  // 刷新未读小红点
    public void refreshMsgRedPoint(MainActivity.OnMsgHasNewEvent event) {
        msgPointIv.setVisibility(event.hasNew ? View.VISIBLE : View.INVISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)  // 完成新手任务后更新
    public void refreshInvestRecord(final InvestRecordRefreshEvent event) {
        refreshData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)  // 完成新手任务后更新
    public void refreshMissionState(final FreshMisssionEvent event) {
        if (event.taskType == -1) {
            return;
        }
        refreshData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)  // 发送红包后更新
    public void refreshRedPackage(RedPackageSendSucessEvent event) {
        refreshData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)  //重设头像
    public void reSetHeadImage(final UpLoadHeadImageEvent event) {

        TopApp.getInstance().getUserService().displayUserFace(TopApp.getInstance().getLoginService().getCurUser().getHeadImage(), mineImgUserFace, false);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void bankAddSuccessRefresh(CardBindSuccessEvent event) {
        refreshData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void withDrawSuccessEvent( WithDrawSuccessEvent event) {
        refreshData();
    }


    /**
     * 上传头像到服务器 返回一个md5
     *
     * @param event    UpLoadHeadImageEvent事件
     * @param callBack 更新头像接口
     */
    private void uploadHeadImage(final UpLoadHeadImageEvent event, final UpdateViewCallBack<ImageResponseEntity> callBack) {
        new MyNetAsyncTask(ctx, false) {
            ImageResponseEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = TopApp.getInstance().getHttpService().personcenterUploadImageFile(new File(event.imgPath));
            }

            @Override
            protected void onPost(Exception e) {
                if (response != null && response.getInfo() != null && response.getInfo().getMd5() != null) {
                    callBack.updateView(null, response);
                }
            }
        }.execute();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    static class MyHandle extends Handler {

    }


}
