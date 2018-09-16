package com.v5ent.xiubit.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.BaseResponseEntity;
import com.toobei.common.entity.ProductDatasDataEntity;
import com.toobei.common.entity.ProductDetail;
import com.toobei.common.entity.ShareContent;
import com.toobei.common.network.NetworkObserver;
import com.toobei.common.network.RetrofitHelper;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.PhotoUtil;
import com.toobei.common.utils.TextDecorator;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.HeaderLayout;
import com.toobei.common.view.MyShowTipsView;
import com.toobei.common.view.Util;
import com.toobei.common.view.dialog.PromptDialogCalTel;
import com.toobei.common.view.listView.MyListView;
import com.toobei.common.view.loopviewpager.AutoScrollViewPager;
import com.toobei.common.view.loopviewpager.indicator.CirclePageIndicator;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.NetBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.OrgInfoDetailDesPicAdapter;
import com.v5ent.xiubit.data.OrgInfoNewsAdapter;
import com.v5ent.xiubit.data.OrginfoDetailBannerAdapter;
import com.v5ent.xiubit.data.recylerapter.ProductListRecycleAdapter;
import com.v5ent.xiubit.entity.CommonTabEntity;
import com.v5ent.xiubit.entity.PlatformDetail;
import com.v5ent.xiubit.event.BindThirdAccountSuccessEvent;
import com.v5ent.xiubit.network.httpapi.OrgInfoApi;
import com.v5ent.xiubit.service.CardCheckAndSetManager;
import com.v5ent.xiubit.service.JumpThirdPartyService;
import com.v5ent.xiubit.service.LoginService;
import com.v5ent.xiubit.util.C;
import com.v5ent.xiubit.util.ParamesHelp;
import com.v5ent.xiubit.view.EmptyView;
import com.v5ent.xiubit.view.popupwindow.RecommendSharePopupWindow;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xsl781.utils.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.toobei.common.utils.C.MY_PERMISSIONS_REQUEST_DIAL;
import static com.v5ent.xiubit.service.JumpThirdPartyService.JUMP_TYPE_USER_CENTER;


/**
 * Activity-平台详情   基本信息+产品列表
 */
public class OrgInfoDetailActivity extends NetBaseActivity implements View.OnClickListener, OnTabSelectListener {

    private RecyclerView orgDetialRv; // 机构销售产品

    private ImageView tabHide;//点击收缩
    private MyListView newsListView; //平台动态

    //描述
    private TextView textorgAdvantage; //机构亮点

    //新手引导
    private MyShowTipsView myShowTipsView;


    //机构销售产品列表数据
    private boolean hideInsFlage = true;
    //    public List<ProductDetail> orgDetailProductList = new ArrayList<ProductDetail>(); //机构产品数据
    private int pageSize = 10;
    private int pageIndex = 1;
    private String productSort = "1";//1-默认排序 2-年化收益 3-产品期限
    private String productOrder = "0";//	0-升序 1-降序
    private PlatformDetail platformDetail;


    /*----------------------------------------------------------------*/
    private ImageView orginfoIV;
    private String orgName;
    private String orgNumber;
    private boolean hasInitPlatformStrengthData; //是否初始化了平台实力
    //    private View btnBuySelf;
    private LinearLayout orgTagContant;
    private TextView orgSafeLevelTv;
    private View accountCentreEntry;
    private View safeLevelEntry;
    private TextView yearProfitTv;
    private TextView orgFeeRatioTv;

    private ShareContent shareContent; //分享数据
    //    private ImageView shareImage;
    private TextView mTopRedTv;
    private SmartRefreshLayout mRefreshView;
    private TextView mOrgNameTv;
    private ProductListRecycleAdapter mProductRecycleAdapter;
    private TextView mProductSoldNumTv;
    private EmptyView mEmptyView;
    private View mHomeHeadView;
    private ArrayList<CustomTabEntity> tabDataList = new ArrayList<>();
    private CommonTabLayout mTabLayout;
    private LayoutInflater mInflater;
    private TabViewHolder1 mTabViewHolder1;
    private LinearLayout mTabContentLl;
    private TabViewHolder4 mTabViewHolder4;
    private TabViewHolder3 mTabViewHolder3;
    private List<PlatformDetail.OrgImageBean> mOrgCertificatesList = new ArrayList<>();
    private OrgInfoDetailDesPicAdapter mOrgTabImageAdapter;
    private String callPhoneNum;
    private TabViewHolder2 mTabViewHolder2;
    private String mRechargeLimitLinkUrl;
    private OrgInfoNewsAdapter mOrgInfoNewsAdapter;
    private RelativeLayout mBannerRl;
    private AutoScrollViewPager mBannerVp;
    private CirclePageIndicator mBannerIndicator;
    private TextView recomendBtn;
    private TextView myAccountTv;
    private boolean skipNeedBindCard = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        orgName = getIntent().getStringExtra("orgName");
        orgNumber = getIntent().getStringExtra("orgNumber");
        Logger.e("ONCREATE  orgNumber==" + orgNumber);
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(ctx);
        initTopTitle();
        initView();
        initData();

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_orginfo_detail;
    }


    @Override
    public void refreshAfterLogin() {
        super.refreshAfterLogin();
        checkThirdAccoundBindStatur();
    }

    protected void refreshDataView(PlatformDetail data) {
        platformDetail = data;
        try {
            String orgProductUrlSkipBindType = platformDetail.orgProductUrlSkipBindType;
            skipNeedBindCard = !"0".equals(orgProductUrlSkipBindType);
            orgName = platformDetail.getOrgName();
            if (TextUtils.isEmpty(this.orgName)) {
                headerLayout.showTitle(orgName);
            }
            if (!TextUtils.isEmpty(platformDetail.getOrgFeeRatioLimit())) {
                mTopRedTv.setText(platformDetail.getOrgFeeRatioLimit());
                mTopRedTv.setVisibility(View.VISIBLE);
            } else {
                mTopRedTv.setVisibility(View.GONE);
            }

            setdata(platformDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTopTitle() {

        headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
        headerLayout.showTitle(orgName);
        headerLayout.showLeftBackButton();
        setTranslucentStatus(true);
        headerLayout.setHeadViewCoverStateBar();
    }

    /**
     * 新手引导图片
     */
    private void initShowTipsView() {
//        MyShowTipsView myShowTipsView1 = new MyShowTipsView(ctx, recomendBtn, GuideViewType.ORG_DETIAL_GUIDE1.getValue()
//                , PixelUtil.dip2px(ctx, -220), PixelUtil.dip2px(ctx, 40), GuideViewType.ORG_DETIAL_GUIDE1.toString());
//
//        final MyShowTipsView myShowTipsView2 = new MyShowTipsView(ctx, accountCentreEntry, GuideViewType.ORG_DETIAL_GUIDE2.getValue()
//                , PixelUtil.dip2px(ctx, 10), PixelUtil.dip2px(ctx, -100), GuideViewType.ORG_DETIAL_GUIDE2.toString());
//        myShowTipsView1.setExpandSize(0, recomendBtn.getHeight() );
//        myShowTipsView1.setLocationOffset(PixelUtil.dip2px(ctx, -7),PixelUtil.dip2px(ctx, -10));
////        myShowTipsView1.setDisplayOneTime(false);
////        myShowTipsView2.setDisplayOneTime(false);
//
//        myShowTipsView1.show(ctx);
//        myShowTipsView1.setOnShowTipHideListener(new MyShowTipsView.onShowTipHideListener() {
//            @Override
//            public void onShowTipHide() {
//                myShowTipsView2.show(ctx);
//            }
//        });

    }

    @SuppressWarnings({"unchecked", "deprecation"})
    private void initView() {
        //                        intent.putExtra("product", product);
//                        intent.putExtra("product", product);
        recomendBtn = headerLayout.showRightTextButton("推荐", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(ctx, "T_1_5");
                if (TextUtil.isEmpty(MyApp.getInstance().getLoginService().token)) {
                    MyApp.loginAndStay = true;
                    showActivity(ctx, LoginActivity.class);
                    return;
                }
                Intent intent = null;
                if (platformDetail == null) return;
                shareContent = new ShareContent("给您推荐一个平台:" + platformDetail.getOrgName()
                        , platformDetail.getOrgAdvantage().replace(",", " | ") + "。该平台已通过貅比特36道风控流程筛选"
                        , C.ORGDETIAL_H5 + platformDetail.getOrgNo()
                        , platformDetail.getPlatformIco());
                RecommendSharePopupWindow popuWindow = new RecommendSharePopupWindow(ctx, shareContent, RecommendSharePopupWindow.RECOMMENDTYPE_ORG, new RecommendSharePopupWindow.RecommendShareListener() {
                    @Override
                    public void onRecommendCfg() {
                        Intent intent = new Intent(ctx, RecommendToCfgActivity.class);
                        intent.putExtra("recommendType", C.RECOMMEND_TYPE_ORG);
                        intent.putExtra("productOrgId", orgNumber);
//                        intent.putExtra("product", product);
                        startActivity(intent);
                    }

                    @Override
                    public void onRecommendCustomer() {
                        Intent intent = new Intent(ctx, RecommendToCustomerActivity.class);
                        intent.putExtra("recommendType", C.RECOMMEND_TYPE_ORG);
                        intent.putExtra("productOrgId", orgNumber);
//                        intent.putExtra("product", product);
                        startActivity(intent);
                    }
                });
                popuWindow.showPopupWindow(headerLayout);
            }

        });

        orgDetialRv = (RecyclerView) findViewById(R.id.orgDetialRv);
        //head=========================================================
        mHomeHeadView = LayoutInflater.from(ctx).inflate(R.layout.layout_orgdetail_headview, null);
        mTopRedTv = (TextView) mHomeHeadView.findViewById(R.id.topRedTv);
        orginfoIV = (ImageView) mHomeHeadView.findViewById(R.id.orgInfoIV);
        textorgAdvantage = (TextView) mHomeHeadView.findViewById(R.id.text_orginfor_detail_orgAdvantage);//机构自定义tag
        orgTagContant = (LinearLayout) mHomeHeadView.findViewById(R.id.org_tag_contant_ll);
        mOrgNameTv = (TextView) mHomeHeadView.findViewById(R.id.orgNameTv);
        orgSafeLevelTv = (TextView) mHomeHeadView.findViewById(R.id.orgSafeLevelTv);  //安全等级
        accountCentreEntry = mHomeHeadView.findViewById(R.id.accountCentreEntry);
        safeLevelEntry = mHomeHeadView.findViewById(R.id.safeLevelEntry);
        myAccountTv = mHomeHeadView.findViewById(R.id.myAccountTv);
//        accountIsOpenTv = mHomeHeadView.findViewById(R.id.accountIsOpenTv);

        yearProfitTv = (TextView) mHomeHeadView.findViewById(R.id.yearProfitTv);     //预期年化
        orgFeeRatioTv = (TextView) mHomeHeadView.findViewById(R.id.orgFeeRatioTv);   //年化佣金
        mProductSoldNumTv = (TextView) mHomeHeadView.findViewById(R.id.productSoldNumTv);

        //banner
        mBannerRl = (RelativeLayout) mHomeHeadView.findViewById(R.id.bannerRl);
        mBannerVp = (AutoScrollViewPager) mHomeHeadView.findViewById(R.id.bannerVp);
        mBannerIndicator = (CirclePageIndicator) mHomeHeadView.findViewById(R.id.bannerIndicator);
        //我的账户
        accountCentreEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(ctx, "T_1_4");
                new JumpThirdPartyService(JUMP_TYPE_USER_CENTER
                        , skipNeedBindCard, ctx, orgName, orgNumber, "").run();
            }
        });

        //安全评级
        safeLevelEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivityCommon.showThisActivity(ctx, C.SAFE_LEVEL_REMIND + orgNumber, "");
            }
        });


        //头tab
        mTabLayout = (CommonTabLayout) mHomeHeadView.findViewById(R.id.tabLayout);
        tabDataList.add(new CommonTabEntity("简介"));
        tabDataList.add(new CommonTabEntity("投资相关"));
        tabDataList.add(new CommonTabEntity("档案"));
        tabDataList.add(new CommonTabEntity("动态"));
        mTabLayout.setTabData(tabDataList);
        mTabLayout.setOnTabSelectListener(this);

        //收缩控件
        tabHide = (ImageView) mHomeHeadView.findViewById(R.id.orginfo_detail_tab_hide);
        tabHide.setOnClickListener(this);

        //四个tab内容统一容器
        mTabContentLl = (LinearLayout) mHomeHeadView.findViewById(R.id.tabContentLl);
        mTabViewHolder1 = new TabViewHolder1();
        mTabViewHolder2 = new TabViewHolder2();
        mTabViewHolder3 = new TabViewHolder3();
        mTabViewHolder4 = new TabViewHolder4();


        mOrgTabImageAdapter = new OrgInfoDetailDesPicAdapter(ctx, mOrgCertificatesList);
        mTabViewHolder3.mCertifImagesListRv.setLayoutManager(new GridLayoutManager(ctx, 3));
        mTabViewHolder3.mCertifImagesListRv.setAdapter(mOrgTabImageAdapter);

        //平台动态
        mOrgInfoNewsAdapter = new OrgInfoNewsAdapter(ctx);
        mTabViewHolder4.mOrgDetialNewsLv.setAdapter(mOrgInfoNewsAdapter);

        //平台产品列表
        orgDetialRv.setLayoutManager(new LinearLayoutManager(ctx));
        mProductRecycleAdapter = new ProductListRecycleAdapter(ctx);
        mProductRecycleAdapter.addHeaderView(mHomeHeadView);
        orgDetialRv.setAdapter(mProductRecycleAdapter);
        orgDetialRv.setNestedScrollingEnabled(false);
        orgDetialRv.setFocusable(false);
        mEmptyView = new EmptyView(ctx);
        mEmptyView.showEmpty("平台暂无可投产品");
        mProductRecycleAdapter.addFooterView(mEmptyView);
        mEmptyView.getLayoutParams().height = Util.dip2px(ctx, 50);

        onTabSelect(0);
        initRefreshView();
    }

    public void initShowTip() {
        mTabLayout.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        initShowTipsView();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            mTabLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
    }

    private void initRefreshView() {
        mRefreshView = (SmartRefreshLayout) findViewById(R.id.refreshView);
        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                onRefreshData();
                mRefreshView.finishRefresh();
            }
        });
        mProductRecycleAdapter.setEnableLoadMore(true);
        mProductRecycleAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                onLoadMoreData();
            }
        }, orgDetialRv);
    }


    private void initData() {
        orgName = (String) getIntent().getSerializableExtra("orgName");
        orgNumber = (String) getIntent().getSerializableExtra("orgNumber");
        getOrgDetail();
        getplatformSaleProducts(false);

        checkThirdAccoundBindStatur();
    }

    //检查第三方账户绑定状态
    private void checkThirdAccoundBindStatur(){
        if (LoginService.getInstance().isTokenExsit()) {
            CardCheckAndSetManager.INSTANCE.isBindOrgAcc(orgNumber,new CardCheckAndSetManager.CheckCallBack() {
                @Override
                public void result(boolean statue) {
                    myAccountTv.setText(statue ? "我的账户" : "去绑定");
                }
            });
        }else {
            myAccountTv.setText("去绑定");
        }
    }

    private void getOrgDetail() {
        RetrofitHelper.getInstance().getRetrofit().create(OrgInfoApi.class)
                .getPlatfromDetial(new ParamesHelp().put("orgNo", orgNumber).build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<BaseResponseEntity<PlatformDetail>>() {
                               public void bindViewWithDate(BaseResponseEntity<PlatformDetail> response) {
                                   refreshDataView(response.getData());
                               }
                           }
                );
    }


    private void setdata(PlatformDetail data) {
        setShareContent(data);
        Glide.with(ctx).load(MyApp.getInstance().getHttpService().getImageUrlFormMd5(data.getPlatformIco())).into(orginfoIV);
//        ImageLoader.getInstance().displayImage(MyApp.getInstance().getHttpService().getImageServerBaseUrl() + data.getOrgLogo(), orginfoIV, PhotoUtil.normalImageOptions);
        mOrgNameTv.setText(data.getOrgName());
        textorgAdvantage.setText(data.getOrgAdvantage().replace(",", " | "));//机构亮点


        //产品tag
        String orgTag = platformDetail.getOrgTag();
        orgTagContant.removeAllViews();
        if (orgTag != null) {
            String[] split = orgTag.split(",");
            for (String str : split) {
                TextView tv = (TextView) View.inflate(this, R.layout.text_org_detial_tager, null);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 27, 0);//4个参数按顺序分别是左上右下
                tv.setLayoutParams(layoutParams);
                tv.setText(str);
                orgTagContant.addView(tv);
            }
        }

        orgSafeLevelTv.setText("安全评级： " + platformDetail.getOrgLevel());
        yearProfitTv.setText(platformDetail.getFeeRateMin() + "%~" + platformDetail.getFeeRateMax() + "%");
        orgFeeRatioTv.setText(platformDetail.getOrgFeeRatio() + "%");

        setTabContentData(data);

        List<PlatformDetail.OrgActivitysBean> orgActivitys = platformDetail.getOrgActivitys();
        if (orgActivitys != null && orgActivitys.size() > 0) {
            mBannerRl.setVisibility(View.VISIBLE);
            mBannerVp.setAdapter(new OrginfoDetailBannerAdapter(ctx, orgActivitys, orgName));
            if (orgActivitys.size() > 1) {
                mBannerIndicator.setViewPager(mBannerVp, orgActivitys.size());
                mBannerVp.startloop();
            }

        } else {
            mBannerRl.setVisibility(View.GONE);
        }
    }

    /**
     * 配置分享数据
     *
     * @param data
     */
    private void setShareContent(PlatformDetail data) {
        shareContent = new ShareContent();
        shareContent.setShareDesc(data.getShareDesc());
        shareContent.setShareImgurl(data.getShareIcon());
        shareContent.setShareTitle(data.getShareTitle());
        shareContent.setLink(data.getShareLink());
    }

    /**
     * 设置平台实力数据
     *
     * @param data
     */
    private void setTabContentData(PlatformDetail data) {
        if (hasInitPlatformStrengthData) return;
        //平台简介
        String orgProfile = data.getOrgProfile();
        mTabViewHolder1.mOrgSempleInfoTv.setText(orgProfile);
        mTabViewHolder1.mOrgSempleInfoTv.setVisibility(TextUtil.isEmpty(orgProfile) ? View.GONE : View.VISIBLE);

        mTabViewHolder1.mRootView.post(new Runnable() {
            @Override
            public void run() {

                if (mTabViewHolder1.mOrgSempleInfoTv.getLineCount() >= 4) {
                    tabHide.setVisibility(View.VISIBLE);
                    tabHide.setImageResource(hideInsFlage ? R.drawable.img_arrow_down : R.drawable.img_arrow_up);
                } else {
                    tabHide.setVisibility(View.GONE);
                }
                initShowTip();
            }
        });


        //投资相关
        mRechargeLimitLinkUrl = data.getRechargeLimitLinkUrl();
        String rechargeLimitTitle = data.getRechargeLimitTitle();
        TextDecorator.decorate(mTabViewHolder2.mRechargeLimitTv, data.getRechargeLimitDescription() + rechargeLimitTitle)
                .makeTextClickable(new TextDecorator.OnTextClickListener() {
                    @Override

                    public void onClick(View view, String text) {
                        WebActivityCommon.showThisActivity(ctx, mRechargeLimitLinkUrl + "?orgNo=" + orgNumber + "&orgName=" + orgName, "");
                    }
                }, true, rechargeLimitTitle)
                .setTextColor(R.color.text_blue_common, rechargeLimitTitle)
                .build();
        mTabViewHolder2.mRechargeLimitLl.setVisibility(TextUtil.isEmpty(data.getRechargeLimitDescription() + rechargeLimitTitle) ? View.GONE : View.VISIBLE);

        mTabViewHolder2.mCashInTimeTv.setText(data.getCashInTime());
        mTabViewHolder2.mCashInTimeLl.setVisibility(TextUtil.isEmpty(data.getCashInTime()) ? View.GONE : View.VISIBLE);

        mTabViewHolder2.mInterestTimeTv.setText(data.getInterestTime());
        mTabViewHolder2.mInterestTimeLl.setVisibility(TextUtil.isEmpty(data.getInterestTime()) ? View.GONE : View.VISIBLE);

        mTabViewHolder2.mInvestOthersTv.setText(data.getInvestOthers());
        mTabViewHolder2.mInvestOthersLl.setVisibility(TextUtil.isEmpty(data.getInvestOthers()) ? View.GONE : View.VISIBLE);

        mTabViewHolder2.mProductReleaseTimeTv.setText(data.getProductReleaseTime());
        mTabViewHolder2.mProductReleaseTimeLl.setVisibility(TextUtil.isEmpty(data.getProductReleaseTime()) ? View.GONE : View.VISIBLE);

        mTabViewHolder2.mWithdrawalChargesTv.setText(data.getWithdrawalCharges());
        mTabViewHolder2.mWithdrawalChargesLl.setVisibility(TextUtil.isEmpty(data.getWithdrawalCharges()) ? View.GONE : View.VISIBLE);


        //公司荣誉
        mTabViewHolder3.honorTitleTv.setVisibility(TextUtil.isEmpty(data.getOrgHonor()) ? View.GONE : View.VISIBLE);
        mTabViewHolder3.honorTv.setText(data.getOrgHonor());
        //基本信息
        String deadLineValueText = data.getDeadLineValueText();
        if (deadLineValueText != null && deadLineValueText.contains(",")) {
            String[] splitDatas = deadLineValueText.split(",");//deadLineValueText=30,天,2,个月
            StringBuilder sb = new StringBuilder();
            sb.append(splitDatas[0]).append(splitDatas[1]);
            if (splitDatas.length >= 4) {
                sb.append("~").append(splitDatas[2]).append(splitDatas[3]);
            }
            mTabViewHolder3.mDeadLineTv.setText(sb);
        } else {
            mTabViewHolder3.mDeadLineTv.setText(data.getProDaysMin() + "天~" + data.getProDaysMax() + "天");
        }
        mTabViewHolder3.mOrgLevelTv.setText(data.getOrgLevel());
        mTabViewHolder3.mCapitalTv.setText(data.getCapital());
        mTabViewHolder3.mUpTimeTv.setText(data.getUpTime());
        mTabViewHolder3.mCityTv.setText(data.getCity());
        mTabViewHolder3.mIcpTv.setText(data.getIcp());
        mTabViewHolder3.mContactTv.setText(data.getContact());
        //机构证件及证书
        List<PlatformDetail.OrgImageBean> certificatesList = data.getOrgCertificatesList();
        mTabViewHolder3.mCertifTitleTv.setVisibility((certificatesList.size() == 0) ?
                View.GONE : View.VISIBLE);
        if (certificatesList.size() == 1) {
            mTabViewHolder3.certifImageSingleIv.setVisibility(View.VISIBLE);
            mTabViewHolder3.mCertifImagesListRv.setVisibility(View.GONE);
            PhotoUtil.loadImageByGlide(ctx, certificatesList.get(0).getOrgPicture(), mTabViewHolder3.certifImageSingleIv);
        } else {
            mTabViewHolder3.certifImageSingleIv.setVisibility(View.GONE);
            mOrgCertificatesList.clear();
            mOrgCertificatesList.addAll(certificatesList);
            mOrgTabImageAdapter.notifyDataSetChanged();
        }


        List<PlatformDetail.OrgDynamicListBean> orgDynamicList = platformDetail.getOrgDynamicList();
        if (orgDynamicList != null && orgDynamicList.size() > 0) {
            mOrgInfoNewsAdapter.refresh(orgDynamicList);
        }

    }


    /**
     * 获取平台销售的产品数据
     *
     * @param openDialog
     */
    private void getplatformSaleProducts(boolean openDialog) {
        new MyNetAsyncTask(ctx, openDialog) {
            ProductDatasDataEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance()
                        .getHttpService()
                        .getProductPageList("dLa", "fRa", "0", productOrder, orgNumber, pageIndex + "", pageSize + "", "2");
            }

            @Override
            protected void onPost(Exception e) {
                mProductRecycleAdapter.removeFooterView(mEmptyView);
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {

                        List<ProductDetail> datas = response.getData().getDatas();
                        mProductSoldNumTv.setText("(" + response.getData().getTotalCount() + ")");
                        if (pageIndex == 1) {
                            if (datas == null || datas.size() == 0) {
                                mProductRecycleAdapter.addFooterView(mEmptyView);
                            } else {
                                mProductRecycleAdapter.removeFooterView(mEmptyView);
                            }
                            mProductRecycleAdapter.setNewData(datas);
                        } else {
                            mProductRecycleAdapter.addData(datas);
                        }

                        if (response.getData().getPageCount() <= response.getData().getPageIndex() || response.getData().getPageCount() <= 1) {
                            mProductRecycleAdapter.loadMoreEnd();
                        } else {
                            mProductRecycleAdapter.loadMoreComplete();
                        }

                        pageIndex++;
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                    mProductRecycleAdapter.loadMoreFail();
                }

            }
        }.execute();
    }


    public void onRefreshData() {
        hasInitPlatformStrengthData = false;
        pageIndex = 1;
        getOrgDetail();
        getplatformSaleProducts(false);
        orgTagContant.removeAllViews();
    }

    public void onLoadMoreData() {
        getplatformSaleProducts(false);
    }


    @Override
    protected Intent getGestureActivityIntent(TopBaseActivity activity) {
        return new Intent(ctx, GestureActivity.class);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.orginfo_detail_tab_hide: //隐藏或者显示机构简介

                hideInsFlage = !hideInsFlage;
                mTabViewHolder1.mOrgSempleInfoTv.setMaxLines(hideInsFlage ? 4 : Integer.MAX_VALUE);
                tabHide.setImageResource(hideInsFlage ? R.drawable.img_arrow_down : R.drawable.img_arrow_up);
                break;
        }
    }


    @Override
    public void onTabSelect(int position) {
        mTabContentLl.removeAllViews();
        tabHide.setVisibility(View.GONE);
        switch (position) {
            case 0:

                mTabContentLl.addView(mTabViewHolder1.mRootView);
                if (mTabViewHolder1.mOrgSempleInfoTv.getLineCount() >= 4) {
                    tabHide.setVisibility(View.VISIBLE);
                    tabHide.setImageResource(hideInsFlage ? R.drawable.img_arrow_down : R.drawable.img_arrow_up);
                } else {
                    tabHide.setVisibility(View.GONE);
                }
                break;
            case 1:
                mTabContentLl.addView(mTabViewHolder2.mRootView);
                break;
            case 2:
                mTabContentLl.addView(mTabViewHolder3.mRootView);
                break;
            case 3:
                mTabContentLl.addView(mTabViewHolder4.mRootView);
                break;
        }

    }


    @Override
    public void onTabReselect(int position) {

    }


    boolean isInstallApp(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    // 打电话权限
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

    /*
      * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cardBindSuccess(BindThirdAccountSuccessEvent event) {
        checkThirdAccoundBindStatur();
    }


    class TabViewHolder1 {

        @BindView(R.id.orgSempleInfoTv)
        TextView mOrgSempleInfoTv;
        View mRootView;

        TabViewHolder1() {
            //tab简介
            mRootView = mInflater.inflate(R.layout.orgdetial_tab_view1, null);
            ButterKnife.bind(this, mRootView);
        }
    }

    class TabViewHolder2 {

        View mRootView;
        @BindView(R.id.productReleaseTimeTv)
        TextView mProductReleaseTimeTv;
        @BindView(R.id.rechargeLimitTv)
        TextView mRechargeLimitTv;
        @BindView(R.id.interestTimeTv)
        TextView mInterestTimeTv;
        @BindView(R.id.withdrawalChargesTv)
        TextView mWithdrawalChargesTv;
        @BindView(R.id.cashInTimeTv)
        TextView mCashInTimeTv;
        @BindView(R.id.investOthersTv)
        TextView mInvestOthersTv;
        @BindView(R.id.productReleaseTimeLl)
        LinearLayout mProductReleaseTimeLl;
        @BindView(R.id.rechargeLimitLl)
        LinearLayout mRechargeLimitLl;
        @BindView(R.id.interestTimeLl)
        LinearLayout mInterestTimeLl;
        @BindView(R.id.withdrawalChargesLl)
        LinearLayout mWithdrawalChargesLl;
        @BindView(R.id.cashInTimeLl)
        LinearLayout mCashInTimeLl;
        @BindView(R.id.investOthersLl)
        LinearLayout mInvestOthersLl;

        TabViewHolder2() {
            //tab简介
            mRootView = mInflater.inflate(R.layout.orgdetial_tab_view2, null);
            ButterKnife.bind(this, mRootView);
        }
    }

    class TabViewHolder3 {

        View mRootView;
        @BindView(R.id.honorTitleTv)
        TextView honorTitleTv;
        @BindView(R.id.honorTv)
        TextView honorTv;
        @BindView(R.id.certifImagesListRv)
        RecyclerView mCertifImagesListRv;
        @BindView(R.id.capitalTv)
        TextView mCapitalTv;
        @BindView(R.id.capitalLl)
        LinearLayout mCapitalLl;
        @BindView(R.id.upTimeTv)
        TextView mUpTimeTv;
        @BindView(R.id.upTimeLl)
        LinearLayout mUpTimeLl;
        @BindView(R.id.cityTv)
        TextView mCityTv;
        @BindView(R.id.cityLl)
        LinearLayout mCityLl;
        @BindView(R.id.deadLineTv)
        TextView mDeadLineTv;
        @BindView(R.id.deadLineLl)
        LinearLayout mDeadLineLl;
        @BindView(R.id.orgLevelTv)
        TextView mOrgLevelTv;
        @BindView(R.id.orgLevelLl)
        LinearLayout mOrgLevelLl;
        @BindView(R.id.icpTv)
        TextView mIcpTv;
        @BindView(R.id.icpLl)
        LinearLayout mIcpLl;
        @BindView(R.id.contactTv)
        TextView mContactTv;
        @BindView(R.id.contactLl)
        LinearLayout mContact;
        @BindView(R.id.hightTeamAndEviShootTv)
        TextView hightTeamAndEviShootTv;
        @BindView(R.id.certifImageSingleIv)
        ImageView certifImageSingleIv;
        @BindView(R.id.certifTitleTv)
        TextView mCertifTitleTv;
        @BindView(R.id.baseInfoTitleTv)
        TextView mBaseInfoTitleTv;

        TabViewHolder3() {
            //tab简介
            mRootView = mInflater.inflate(R.layout.orgdetial_tab_view3, null);
            ButterKnife.bind(this, mRootView);
        }


        @OnClick({R.id.contactTv, R.id.hightTeamAndEviShootTv})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.contactTv:
                    callPhoneNum = mContactTv.getText().toString();
                    break;
                case R.id.hightTeamAndEviShootTv:
                    if (platformDetail == null) return;
                    Intent intent = new Intent(ctx, OrgTeamAndEnvimentActivity.class);
                    intent.putExtra("data", platformDetail);
                    ctx.startActivity(intent);
                    break;
                case R.id.certifImageSingleIv:
                    ArrayList<String> imgUrlstrings = new ArrayList<>();
                    for (int i = 0; i < mOrgCertificatesList.size(); i++) {
                        imgUrlstrings.add(mOrgCertificatesList.get(i).getOrgPicture());

                    }
                    Intent intent1 = new Intent(ctx, ViewPagerActivity.class);
                    intent1.putStringArrayListExtra("imgUrlstrings", imgUrlstrings);
                    ctx.startActivity(intent1);
                    break;
            }
        }
    }


    class TabViewHolder4 {

        View mRootView;
        @BindView(R.id.orgDetialNewsLv)
        MyListView mOrgDetialNewsLv;

        TabViewHolder4() {
            //tab简介
            mRootView = mInflater.inflate(R.layout.orgdetial_tab_view4, null);
            ButterKnife.bind(this, mRootView);
        }
    }


}
