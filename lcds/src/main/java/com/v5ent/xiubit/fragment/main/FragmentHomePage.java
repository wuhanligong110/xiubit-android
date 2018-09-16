package com.v5ent.xiubit.fragment.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.BaseResponseEntity;
import com.toobei.common.entity.FundSiftEntiy;
import com.toobei.common.entity.HomePagerBanners;
import com.toobei.common.entity.HomePagerBannersDatasDataEntity;
import com.toobei.common.entity.InsuranceDetialData;
import com.toobei.common.entity.InsuranceSiftEntiy;
import com.toobei.common.entity.ProductDatasDataEntity;
import com.toobei.common.entity.ShareContent;
import com.toobei.common.network.NetworkObserver;
import com.toobei.common.network.RetrofitHelper;
import com.toobei.common.network.httpapi.ThirdPartApi;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.NetCachAsyncTask;
import com.toobei.common.utils.NetworkUtils;
import com.toobei.common.utils.PhotoUtil;
import com.toobei.common.utils.TextDecorator;
import com.toobei.common.utils.TextFormatUtil;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.MyShowTipsView;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.umeng.analytics.MobclickAgent;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.activity.CommonFragmentActivity;
import com.v5ent.xiubit.activity.HotfixTestActivity;
import com.v5ent.xiubit.activity.InviteCfpQr;
import com.v5ent.xiubit.activity.LoginActivity;
import com.v5ent.xiubit.activity.MainActivity;
import com.v5ent.xiubit.activity.RegisterPhone;
import com.v5ent.xiubit.activity.WebActivityCommon;
import com.v5ent.xiubit.data.recylerapter.FundHomeAdapter;
import com.v5ent.xiubit.data.recylerapter.ProductListRecycleAdapter;
import com.v5ent.xiubit.entity.HomeCfpBuyInfoData;
import com.v5ent.xiubit.entity.HomeHotNewsDetial;
import com.v5ent.xiubit.entity.LcdsAchievementData;
import com.v5ent.xiubit.fragment.FragmentBase;
import com.v5ent.xiubit.network.httpapi.LcdsDataApi;
import com.v5ent.xiubit.service.HomePageDialogManager;
import com.v5ent.xiubit.service.JumpInsuranceService;
import com.v5ent.xiubit.service.LoginService;
import com.v5ent.xiubit.util.C;
import com.v5ent.xiubit.util.ParamesHelp;
import com.v5ent.xiubit.view.MyScrollView;
import com.v5ent.xiubit.view.MyTextView;
import com.v5ent.xiubit.view.loadstatue.LoadStatueView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/6/22
 */

public class FragmentHomePage extends FragmentBase {


    @BindView(R.id.home_banner_viewpager)
    Banner mHomeBannerViewpager;
    @BindView(R.id.refreshView)
    SmartRefreshLayout mRefreshView;
    @BindView(R.id.rantCommissionAmountTv)
    MyTextView mRantCommissionAmountTv;
    @BindView(R.id.topTotalCommissionTv)
    MyTextView topTotalCommissionTv;
    @BindView(R.id.safeRunDayTv)
    TextView mSafeRunDayTv;
    @BindView(R.id.productListRv)
    RecyclerView mProductListRv;
    //    @BindView(R.id.hotPointImageRv)
//    RecyclerView mHotPointImageRv;

    @BindView(R.id.choiceProductRl)
    RelativeLayout mChoiceProductRl;
    @BindView(R.id.scrollView)
    MyScrollView mScrollView;
    @BindView(R.id.status_bar)
    View mStatusBar;
    @BindView(R.id.infoIv)
    ImageView mInfoIv;
    @BindView(R.id.msgPointIv)
    ImageView msgPointIv;
    @BindView(R.id.headRe)
    RelativeLayout mHeadRe;
    @BindView(R.id.arrow1)
    ImageView mArrow1;
    @BindView(R.id.arrow3)
    ImageView mArrow3;
    @BindView(R.id.fundSiftTitleRl)
    RelativeLayout mFundSiftTitleRl;
    @BindView(R.id.home_page_tab1)
    LinearLayout mHomePageTab1;
    @BindView(R.id.home_page_tab2)
    LinearLayout mHomePageTab2;
    @BindView(R.id.home_page_tab3)
    LinearLayout mHomePageTab3;
    @BindView(R.id.home_page_tab4)
    LinearLayout mHomePageTab4;
    @BindView(R.id.home_page_tab1_iv)
    ImageView mHomePageTab1Iv;
    @BindView(R.id.home_page_tab2_iv)
    ImageView mHomePageTab2Iv;
    @BindView(R.id.home_page_tab3_iv)
    ImageView mHomePageTab3Iv;
    @BindView(R.id.home_page_tab4_iv)
    ImageView mHomePageTab4Iv;
    @BindView(R.id.newuser_tab)
    View mNewuserTab;
    @BindView(R.id.insurance_iv)
    ImageView mInsuranceIv;
    @BindView(R.id.insurance_name_tv)
    TextView mInsuranceNameTv;
    @BindView(R.id.insurance_description_tv)
    TextView mInsuranceDescriptionTv;
    @BindView(R.id.insurance_price_tv)
    MyTextView mInsurancePriceTv;
    @BindView(R.id.insuranceEntry)
    View mInsuranceEntry;
    @BindView(R.id.fund_list_rv)
    RecyclerView mFundListRv;
    @BindView(R.id.feeRatioTv)
    MyTextView feeRatioTv;
    @BindView(R.id.guideBgLl)
    View guideBgLl;
    @BindView(R.id.guideV1)
    View guideV1;
    @BindView(R.id.autoScrollVf)
    ViewFlipper autoScrollVf;
    @BindView(R.id.bottomStatisticsLl)
    View bottomStatisticsLl;
    @BindView(R.id.titleTv)
    View titleTv;


    private View mRootView;
    private List<HomeHotNewsDetial> mHomeHotNewsDetial = new ArrayList<>();
    private ProductListRecycleAdapter mProductListAdapter;
    //    private HotImgRecycleAdapter mHotPointImageAdapter;
    private String callPhoneNum;
    private MyShowTipsView mMyShowTipsView1;
    private MyShowTipsView mMyShowTipsView2;
    private FundHomeAdapter mFundHomeAdapter;
    private LoadStatueView mLoadStatueView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initBus();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(ctx).inflate(R.layout.fragment_home_page, null);
        mLoadStatueView = new LoadStatueView(ctx, mRootView, new LoadStatueView.ClickCallBack() {
            @Override
            public void onRefreshNetClick() {
                getNetDatas(false);
            }
        });
        ButterKnife.bind(this, mRootView);
        initView();
        getNetDatas(false);
        return mLoadStatueView.getRootView();

    }

    @Override
    public void refreshAfterLogin() {
        super.refreshAfterLogin();
        initTopBlock();
        getNetDatas(false);
    }

    private void getNetDatas(boolean isRefresh) {
        if (NetworkUtils.isConnected()) {
            mLoadStatueView.showContantView();
        } else {
            mLoadStatueView.showFailView();
            return;
        }
        mNewuserTab.setVisibility(LoginService.getInstance().isTokenExsit() ? View.GONE : View.VISIBLE);
        bottomStatisticsLl.setVisibility(LoginService.getInstance().isTokenExsit() ? View.VISIBLE : View.GONE);
        try {
            getBannersDatas(isRefresh);
            getLcdsAchieveData(); //平台统计数据
            getSelectProductData();
            getFundSiftData();
//            getHotPointNewsData();
            getCfgBuyInfoDatas();
            getInsuranceSiftData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getLcdsAchieveData() {
        RetrofitHelper.getInstance().getRetrofit().create(LcdsDataApi.class).getLcdsAchievement(new ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<BaseResponseEntity<LcdsAchievementData>>() {
                    @Override
                    public void bindViewWithDate(BaseResponseEntity<LcdsAchievementData> response) {

                        mSafeRunDayTv.setText(response.getData().safeOperationTime + "天");
                    }

                });

    }


    private void getInsuranceSiftData() {
        RetrofitHelper.getInstance().getRetrofit().create(ThirdPartApi.class).insuranceSift(new ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<InsuranceSiftEntiy>() {
                    @Override
                    public void bindViewWithDate(final InsuranceSiftEntiy response) {
                        mInsuranceEntry.setVisibility(View.VISIBLE);
                        InsuranceDetialData data = response.getData();
                        mInsuranceNameTv.setText(data.productName);
                        mInsuranceDescriptionTv.setText(data.fullDescription);

                        TextDecorator.decorate(mInsurancePriceTv, data.priceString + "元起")
                                .setAbsoluteSize((int) (ctx.getResources().getDimension(R.dimen.w12)), false, "元起")
                                .setTextColor(R.color.text_gray_common, "元起")
                                .build();

                        TextDecorator.decorate(feeRatioTv, data.feeRatio + "%佣金率")
                                .setAbsoluteSize((int) (ctx.getResources().getDimension(R.dimen.w21)), false, data.feeRatio + "%")
                                .setTextColor(R.color.text_red_common, data.feeRatio + "%")
                                .build();

                        int tagDrawableId = R.drawable.empty_photo;
                        switch (data.fristCategory) {
                            case 1:
                                tagDrawableId = R.drawable.icon_insurance_tag_1;
                                break;
                            case 2:
                                tagDrawableId = R.drawable.icon_insurance_tag_2;
                                break;
                            case 3:
                                tagDrawableId = R.drawable.icon_insurance_tag_3;
                                break;
                            case 4:
                                tagDrawableId = R.drawable.icon_insurance_tag_4;
                                break;
                            case 5:
                                tagDrawableId = R.drawable.icon_insurance_tag_5;
                                break;
                            case 6:
                                tagDrawableId = R.drawable.icon_insurance_tag_6;
                                break;
                        }
                        PhotoUtil.loadImageByGlide(ctx, data.productBakimg, mInsuranceIv, R.drawable.empty_photo);
                        mInsuranceEntry.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MobclickAgent.onEvent(ctx, "S_5_1");
                                new JumpInsuranceService(ctx, response.getData().caseCode, JumpInsuranceService.TAG_PRODUCT_DETIAL).run();
                            }
                        });

                    }
                });
    }


    private void getFundSiftData() throws Exception {
        //// 精选基金网络请求 
        MyApp.getInstance().getHttpService().fundSiftList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<FundSiftEntiy>() {
                    @Override
                    public void onNext(@NonNull FundSiftEntiy response) {
                        noMsgToast = true;
                        super.onNext(response);
                    }

                    @Override
                    public void bindViewWithDate(FundSiftEntiy response) {
                        mFundHomeAdapter.setNewData(response.getData().getDatas());
                    }

                });
    }

    private void getCfgBuyInfoDatas() {

        RetrofitHelper.getInstance().getRetrofit().create(LcdsDataApi.class)
                .cfpBuyInfostatistics(new ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<BaseResponseEntity<HomeCfpBuyInfoData>>() {


                    @Override
                    public void bindViewWithDate(BaseResponseEntity<HomeCfpBuyInfoData> response) {
                        String textAmount = TextFormatUtil.formatToWan(response.getData().commissionAmount.replace(",", ""));
                        mRantCommissionAmountTv.setText( textAmount);
                        topTotalCommissionTv.setText(textAmount);
                        List<HomeCfpBuyInfoData.HotInvestListBean> hotInvestList = response.getData().hotInvestList;
                        if (hotInvestList != null && hotInvestList.size() > 0) {
                            autoScrollVf.removeAllViews();
                            for (HomeCfpBuyInfoData.HotInvestListBean bean : hotInvestList) {

                                View view = LayoutInflater.from(ctx).inflate(R.layout.item_hot_invest, null);
                                TextView investTv = view.findViewById(R.id.investTv);
                                TextView dateTv = view.findViewById(R.id.dateTv);
                                ImageView tagIv = view.findViewById(R.id.tagIv);

                                String textTerm = ""; //期限
                                //类型 1：网贷 2：保险
                                switch (bean.type) {
                                    case "1":
                                        tagIv.setImageResource(R.drawable.tag_wd);
                                        textTerm = "(" + bean.term + "天标)";
                                        break;
                                    case "2":
                                        tagIv.setImageResource(R.drawable.tag_bx);
                                        break;
                                }
                                String textProductName = "";
                                if (!TextUtil.isEmpty(textProductName) && textProductName.length() > 10) {
                                    textProductName = bean.productName.substring(0, 10);
                                } else {
                                    textProductName = bean.productName;
                                }
                                String text = "" + bean.userName + "投资了" + textProductName + textTerm + bean.amount + "元";
                                investTv.setText(text);
                                dateTv.setText(bean.timeDesc);

                                autoScrollVf.addView(view);
                            }
                        }
                    }

                });
//        new MyNetAsyncTask(ctx, false) {
//            HomeCfpBuyInfoEntity response;
//
//            @Override
//            protected void doInBack() throws Exception {
//                response = MyApp.getInstance()
//                        .getHttpService()
//                        .getHomeCfpBuyInfo();
//            }
//
//            @Override
//            protected void onPost(Exception e) {
//                if (e == null && response != null) {
//                    if (response.getCode().equals("0") && response.getData() != null) {
//                        //取消新手福利六连送
////                        if (!LoginService.getInstance().isTokenExsit()) {
////                            mNewuserTab.setVisibility(View.VISIBLE);
////                        } else {
////                            mNewuserTab.setVisibility("0".equals(response.getData().getNewcomerTaskStatus()) ? View.GONE : View.VISIBLE);
////                        }
//
//                    } else {
//                        if (isAdded()) {
//                            ToastUtil.showCustomToast(response.getErrorsMsgStr());
//                        }
//                    }
//                } else {
//                    if (isAdded()) {
//                        ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
//                    }
//
//                }
//            }
//        }.execute();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            if (activity.cfpLevelWarnHasNotShow) {
                activity.showCfpLevelWarningDialog();
            }

            if (activity.HomeNewDiaHasNotShow) {
                activity.showHomeNewActDialogAndInitShowTip();
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            HomePageDialogManager.INSTANCE.show(ctx, new HomePageDialogManager.ControlCallBack() {
                @Override
                public boolean allowShow() {
                    return !isHidden();
                }
            });
        }
        super.onHiddenChanged(hidden);
    }

    private void initView() {
        titleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx, HotfixTestActivity.class));

            }
        });
        //初始化顶部
        setHeadViewCoverStateBar(mStatusBar);
        initTopBlock();
        //下划线
//        knowAboutUsTv.setText("一分钟了解我们>>");

//        initScrollView();
        initRefreshView();
        iniFundRecycleView();
        initProductRecycleView();
//        initHotPointNewsRecycleView();
    }


    private void initTopBlock() {
        if (LoginService.getInstance().isTokenExsit()) {
            guideBgLl.setVisibility(View.GONE);
            guideV1.setVisibility(View.VISIBLE);
            mHomeBannerViewpager.setVisibility(View.VISIBLE);
        } else {
            guideBgLl.setVisibility(View.VISIBLE);
            guideV1.setVisibility(View.GONE);
            mHomeBannerViewpager.setVisibility(View.GONE);
        }
    }


    private void iniFundRecycleView() {
        mFundListRv.setLayoutManager(new LinearLayoutManager(ctx));
        mFundHomeAdapter = new FundHomeAdapter();
        mFundListRv.setAdapter(mFundHomeAdapter);
        mFundListRv.setNestedScrollingEnabled(false);
        mFundListRv.setFocusable(false);
    }


//    private void initScrollView() {
//        mIsblack = false;
//        bannerHeight = Util.dip2px(ctx, 200);  //banner由于是动态测量高度的，就不取固定值了
//        int headHeight = SystemTool.getStatusBarHeight(mAppContext) + Util.dip2px(mAppContext, 44);
//        final int relativeHeight = bannerHeight - headHeight;
//
//        mScrollView.setScrollViewListener(new MyScrollView.ScrollViewListener() {
//            @Override
//            public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
////                Logger.d("scroll-y====>" + y + "//" + oldy);
//                if (y >= 0 && y <= relativeHeight) {
//                    if (mIsblack) {
//                        mTitleTv.setText("首页");
//                        mTitleTv.setVisibility(View.INVISIBLE);
//                        mTopShapeCircle.setVisibility(View.VISIBLE);
//                        mIsblack = false;
//                    }
//                    float scale = (float) y / relativeHeight;
//                    float alpha = (255 * scale);
//                    // 只是layout背景透明(仿知乎滑动效果)
//                    mTopTitleAndStatueLl.setBackgroundColor(Color.argb((int) alpha, 78, 140, 239));
//                    mTitleTv.setAlpha(alpha);
//                    mTitleTv.setVisibility(View.VISIBLE);
//                } else {
//
//                    mTopTitleAndStatueLl.setBackgroundColor(Color.argb((int) 255, 78, 140, 239));
//                    if (!mIsblack) {
//                        mTitleTv.setText("首页");
//                        mTitleTv.setVisibility(View.VISIBLE);
//                        mTopShapeCircle.setVisibility(View.INVISIBLE);
//                        mIsblack = true;
//                    }
//                }
//            }
//        });
//    }

//    private void initHotPointNewsRecycleView() {
//        mHotPointImageRv.setLayoutManager(new LinearLayoutManager(ctx));
//        mHotPointImageAdapter = new HotImgRecycleAdapter(ctx, mHomeHotNewsDetial);
//        mHotPointImageRv.setAdapter(mHotPointImageAdapter);
//        mHotPointImageRv.setNestedScrollingEnabled(false);
//        mHotPointImageRv.setFocusable(false);
//    }

    private void initProductRecycleView() {
        mProductListRv.setLayoutManager(new LinearLayoutManager(ctx));
        mProductListAdapter = new ProductListRecycleAdapter(ctx);
        mProductListAdapter.setFromTag(ProductListRecycleAdapter.FROM_HOME_PAGE);
        mProductListRv.setAdapter(mProductListAdapter);
        mProductListRv.setNestedScrollingEnabled(false);
        mProductListRv.setFocusable(false);
    }

    private void initRefreshView() {
        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getNetDatas(true);
                mRefreshView.finishRefresh();
            }
        });
    }

//    private void getHotPointNewsData() {
//        new MyNetAsyncTask(ctx, false) {
//            HomeHotNewsEntity response;
//
//            @Override
//            protected void doInBack() throws Exception {
//                response = MyApp.getInstance()
//                        .getHttpService()
//                        .getHomeHotNews();
//            }
//
//            @Override
//            protected void onPost(Exception e) {
//                if (e == null && response != null) {
//                    if (response.getCode().equals("0") && response.getData() != null) {
//                        List<HomeHotNewsDetial> datas = response.getData().getDatas();
//                        mHotPointImageAdapter.refresh(datas);
//                    } else {
//
//                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
//                    }
//                } else {
//                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
//
//                }
//            }
//        }.execute();
//    }

    /**
     * 获取banner 数据
     * advPlacement	广告位置描述	string   pc首页页中：pc_idx_middle (必填),pc端banner：pc_banner,平台banner:platform_banner,产品banner:product_banner
     * appType	端口	number                   理财师1，投资端2 （必填）
     *
     * @param isRefresh
     */
    private void getBannersDatas(final boolean isRefresh) {
        new NetCachAsyncTask<HomePagerBannersDatasDataEntity>("homePagerBanners", 60 * 24 * 1, false, isRefresh) {

            @Override
            protected HomePagerBannersDatasDataEntity getData() throws Exception {
                return MyApp.getInstance()
                        .getHttpService()
                        .homepageAdvs(MyApp.getInstance()
                                .getLoginService().token, "product_banner", "1");
            }

            @Override
            protected void onPost(Exception e,
                                  HomePagerBannersDatasDataEntity response,
                                  boolean isDataFromServer) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        List<HomePagerBanners> homePagerBannerses = response.getData().getDatas();
                        if (homePagerBannerses != null && homePagerBannerses.size() > 0) {
                            initBannersView(homePagerBannerses);

                        }
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(MyApp.getInstance()
                            .getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }


    /**
     * 显示banners
     *
     * @param homePagerBannerses
     */
    private void initBannersView(final List<HomePagerBanners> homePagerBannerses) {
        mHomeBannerViewpager.setImages(homePagerBannerses).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(final Context context, Object path, ImageView imageView) {
                final HomePagerBanners data = (HomePagerBanners) path;
                PhotoUtil.loadImageByGlide(ctx, data.getImgUrl()
                        + "?h=300&w=750"
                        , imageView, C.DefaultResId.BANNER_PLACT_HOLD_IMG_750x300_);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MobclickAgent.onEvent(ctx, "S_0_4");

                        Intent intent = new Intent(ctx, WebActivityCommon.class);
                        intent.putExtra("url", data.getLinkUrl());
                        intent.putExtra("title", data.getShareTitle());
                        intent.putExtra("shareContent", new ShareContent(data.getShareTitle(), data.getShareDesc(), data.getLinkUrl(), data.getShareIcon()));
                        ctx.startActivity(intent);
                    }
                });
            }
        }).start();
        initShowTip();
    }

    public void initShowTip() {
//        if (hasShowGuild) return;
//        mChoiceProductRl.getViewTreeObserver()
//                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//
//                        initShowTipsView();
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                            mChoiceProductRl.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                        }
//                    }
//                });
    }

    /**
     * 新手引导图片
     */
    private void initShowTipsView() {
//        mMyShowTipsView1 = new MyShowTipsView(ctx, mInvestCfgLl, GuideViewType.FRAGMENT_HOME_GUIDE2
//                .getValue(),//
//                PixelUtil.dip2px(ctx, -50), PixelUtil.dip2px(ctx, -80), GuideViewType.FRAGMENT_HOME_GUIDE2
//                .toString());

        //
//        mMyShowTipsView2 = new MyShowTipsView(ctx, signCalendarEntry, GuideViewType.FRAGMENT_HOME_GUIDE1
//                .getValue(),//
//                PixelUtil.dip2px(ctx, -70), PixelUtil.dip2px(ctx, -100), GuideViewType.FRAGMENT_HOME_GUIDE1
//                .toString());
//                        mMyShowTipsView2.setDisplayOneTime(false);
//        mMyShowTipsView2.setLocationOffset(-PixelUtil.dip2px(ctx, 5), -PixelUtil.dip2px(ctx, 0));
//        mMyShowTipsView1.setLocationOffset(-PixelUtil.dip2px(ctx, 5), -PixelUtil.dip2px(ctx, 0));
//        mMyShowTipsView2.setExpandSize(PixelUtil.dip2px(ctx, 5), PixelUtil.dip2px(ctx, 0));
//        mMyShowTipsView1.setExpandSize(PixelUtil.dip2px(ctx, 5), PixelUtil.dip2px(ctx, 0));

//        mMyShowTipsView2.setOnShowTipHideListener(new MyShowTipsView.onShowTipHideListener() {
//            @Override
//            public void onShowTipHide() {
//
//                mMyShowTipsView1.show(ctx);
//            }
//        });
//         mMyShowTipsView2.setDisplayOneTime(false);
//        if (mMainActivity.curIndex == 0) {  // 当前在首页才显示新手引导
//            mMyShowTipsView2.show(ctx);
//            hasShowGuild = true;
//        }
    }

    private void getSelectProductData() {
        new MyNetAsyncTask(ctx, false) {
            ProductDatasDataEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance()
                        .getHttpService()
                        .getSelectedProductsList();
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0") && response.getData() != null) {
                        mProductListAdapter.setNewData(response.getData().getDatas());
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
    public void onDestroy() {
        super.onDestroy();
        if (mHomeBannerViewpager != null) {
            mHomeBannerViewpager.stopAutoPlay();
            mHomeBannerViewpager = null;
        }
    }

    @OnClick({R.id.headRe, R.id.guideBgLl, R.id.knowAboutUsEntry, R.id.insurance_title_rl, R.id.home_page_tab1, R.id.home_page_tab2, R.id.home_page_tab3, R.id.home_page_tab4, R.id.newuser_tab, R.id.fundSiftTitleRl, R.id.infoIv, R.id.choiceProductRl})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.guideBgLl:
            case R.id.knowAboutUsEntry:
                MobclickAgent.onEvent(ctx, "S_2_1");
                WebActivityCommon.showThisActivity((TopBaseActivity) getActivity(), C.URL_KNOW_US, "");
                break;
//            case R.id.safeProtectLl:
//                WebActivityCommon.showThisActivity(mMainActivity, C.URL_SAFESHIELD, "");
//                break;
//            case R.id.liecaitrategySLl:
//                WebActivityCommon.showThisActivity(mMainActivity, C.URL_RANK_CALC, "");
//                break;

            case R.id.choiceProductRl:
//                intent = new Intent(ctx, CommonFragmentActivity.class);
//                intent.putExtra(C.FragmentTag.KEY_TAG, C.FragmentTag.PRODUCT_LIST);
//                startActivity(intent);

//                MobclickAgent.onEvent(ctx, "S_3_1");
//                startActivity(new Intent(ctx, SelectedProductActivity.class));
                intent = new Intent(ctx, CommonFragmentActivity.class);
                intent.putExtra(C.FragmentTag.KEY_TAG, C.FragmentTag.PRODUCT_LIST);
                startActivity(intent);
                break;
            case R.id.infoIv:
                MobclickAgent.onEvent(ctx, "S_0_3");

                if (LoginService.getInstance().isTokenExsit()) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.skipToMineMsgCenter();
                } else {
                    startActivity(new Intent(ctx, LoginActivity.class));
                }
//                if (mShakeRunnable != null) {
//                    mHandler.removeCallbacks(mShakeRunnable);
//                }
                break;
            case R.id.fundSiftTitleRl:  //更多基金
                MobclickAgent.onEvent(ctx, "S_4_2");
                intent = new Intent(ctx, MainActivity.class);
                intent.putExtra("skipTab", "p1t2");
                startActivity(intent);
                break;

            case R.id.home_page_tab1:
//                //网贷
//                MobclickAgent.onEvent(ctx, "S_1_1");
//                intent = new Intent(ctx, MainActivity.class);
//                intent.putExtra("skipTab", "p1t1");
//                startActivity(intent);
//安全保障跳转
                intent = new Intent(ctx, WebActivityCommon.class);
                intent.putExtra("isRedirectUsable", false);
                intent.putExtra("url", C.SAFEPROTECT);
                startActivity(intent);
                break;

            case R.id.newuser_tab:  // 新手福利
////                MobclickAgent.onEvent(ctx, "S_2_3");
////                intent = new Intent(ctx, WebActivityCommon.class);
////                intent.putExtra("url", C.NEWER_WELFARE);
////                intent.putExtra("title", "新手福利");
////                intent.putExtra("AutoRefresh", true);
////                startActivity(intent);
//                if (!LoginService.getInstance().isTokenExsit())
//                    startActivity(new Intent(ctx, LoginActivity.class));
//                break;
                startActivity(new Intent(ctx, RegisterPhone.class));
            break;
            case R.id.home_page_tab2:  //邀请理财师
                MobclickAgent.onEvent(ctx, "S_2_2");
                startActivity(new Intent(ctx, InviteCfpQr.class));
                break;
            case R.id.home_page_tab3:   // 抽奖
                MobclickAgent.onEvent(ctx, "S_8_1");
                WebActivityCommon.showThisActivity((TopBaseActivity) ctx, C.BOUNTY, "");
                break;
            case R.id.home_page_tab4:
                //公益
//                MobclickAgent.onEvent(ctx, "S_1_4");
////                ShareContent shareContent = new ShareContent("大爱公益"
////                        , "貅比特携手芒果v基金，为爱前行！"
////                        , TopApp.getInstance().getHttpService().getBaseH5urlByAppkind() + C.PUBLIC_FUND
////                        , "https://image.toobei.com/0366808ad267b591aabdd83d6640a625?f=png");
//                intent = new Intent(ctx, WebActivityCommon.class);
//                intent.putExtra("url", C.PUBLIC_FUND);
//                intent.putExtra("title", "公益");
//                intent.putExtra("AutoRefresh", true);
////                intent.putExtra("shareContent", shareContent);
//                startActivity(intent);
////                WebActivityCommon.showThisActivity(mMainActivity, C.PUBLIC_FUND, "公益");
                //投资攻略
                WebActivityCommon.showThisActivity((TopBaseActivity) ctx, C.INVEST_STRATEGY, "");
                break;


            case R.id.insurance_title_rl://更多保险
                MobclickAgent.onEvent(ctx, "S_5_2");
                intent = new Intent(ctx, MainActivity.class);
                intent.putExtra("skipTab", "p1t3");
                startActivity(intent);
                break;
//            case R.id.signCalendarEntry: //签到
//                MobclickAgent.onEvent(ctx, "S_7_1");
//                if (LoginService.getInstance().isTokenExsit())
//                    startActivity(new Intent(ctx, SignInActivity.class));
//                else startActivity(new Intent(ctx, LoginActivity.class));
//                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)  // 刷新未读小红点
    public void refreshMsgRedPoint(MainActivity.OnMsgHasNewEvent event) {
        msgPointIv.setVisibility(event.hasNew ? View.VISIBLE : View.INVISIBLE);
//        mInfoIv.setImageResource(event.hasNew ? R.drawable.img_mine_has_msg_icon : R.drawable.img_mine_msg_icon);
//        msgRl.setVisibility(event.hasNew ? View.VISIBLE : View.GONE);
//        if (event.hasNew) {
//            mHandler = new MyHandle();
//            if (mShakeRunnable == null) {
//                mShakeRunnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        ObjectAnimator tada = shakeAnimationUtil.tada(mInfoIv, 1.2f, 1000);
//                        tada.start();
//                        mHandler.postDelayed(this, 3000);
//                    }
//                };
//            }
//            mHandler.post(mShakeRunnable);
//        } else if (mShakeRunnable != null) {
//
//            mHandler.removeCallbacks(mShakeRunnable);
//        }


    }


    static class MyHandle extends Handler {

    }
}
