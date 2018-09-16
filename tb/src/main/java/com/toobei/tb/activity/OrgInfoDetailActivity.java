package com.toobei.tb.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.TopNetworkBaseActivity;
import com.toobei.common.entity.ProductDatasDataEntity;
import com.toobei.common.entity.ProductDetail;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.PhotoUtil;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.HeaderLayout;
import com.toobei.common.view.MyShowTipsView;
import com.toobei.common.view.listView.MyListView;
import com.toobei.common.view.loopviewpager.LoopViewPager;
import com.toobei.common.view.loopviewpager.indicator.CirclePageIndicator;
import com.toobei.common.view.photoview.Info;
import com.toobei.common.view.photoview.PhotoView;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.toobei.tb.MyApp;
import com.toobei.tb.R;
import com.toobei.tb.adapter.OrgInfoDetailDesPicAdapter;
import com.toobei.tb.adapter.OrgInfoNewsAdapter;
import com.toobei.tb.adapter.OrgInfoTeamAdapter;
import com.toobei.tb.adapter.OrginfoDetailBannerViewPagerAdapter;
import com.toobei.tb.adapter.ProductListAdapter;
import com.toobei.tb.entity.PlatFormDetailEntity;
import com.toobei.tb.entity.PlatformDetail;
import com.toobei.tb.entity.TeamInfosBean;
import com.toobei.tb.service.ProductService;
import com.umeng.analytics.MobclickAgent;

import org.xsl781.ui.xlist.XListView;
import org.xsl781.utils.PixelUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 平台详情   基本信息+产品列表
 */
public class OrgInfoDetailActivity extends TopNetworkBaseActivity<PlatFormDetailEntity> implements XListView.IXListViewListener, View.OnClickListener, OrgInfoDetailDesPicAdapter.MyItemClickListener {
    private AutoScrollHandler autoSkipHandler = new AutoScrollHandler();

    private View homeHeadView;
    private XListView orgSaleProcuctListView; // 机构销售产品


    //滑动的Tab
    private ImageView cursor;
    private RadioGroup radioGrop;
    private LinearLayout orginfoDetailTabRoot;//容器
    private ImageView tabHide;//点击收缩
    private TextView textDes; //简介
    private TextView textSafe;//安全保障
    private MyListView teamListView;//团队
    private MyListView newsListView; //平台动态

    //描述
    private TextView textorgAdvantage; //机构亮点
    //产品自定义标签	string	T呗/理财师1.2.1版本 (多个以英文逗号分隔)
    private TextView textTag01;
    private TextView textTag02;

    private TextView textTag04;
    private TextView textTag05;
    private TextView textTag06;

    //Banner
    private LinearLayout orgActivityAdvertiseLL;
    private LoopViewPager vPager;
    private CirclePageIndicator indicator;
    //新手引导
    private MyShowTipsView myShowTipsView;



    //机构销售产品列表数据
    private boolean hideInsFlage = true;
    public List<ProductDetail> orgDetailProductList = new ArrayList<ProductDetail>(); //机构产品数据
    private int currIndex = 0;
    private int pageSize = 10;
    private int pageIndex = 1;
    private String productSort = "1";//1-默认排序 2-年化收益 3-产品期限
    private String productOrder = "0";//	0-升序 1-降序
    private ProductListAdapter productAdapter;
    private PlatformDetail platformDetail;

    private int orginfoDetailTabRootHeight = 120; //收缩控件收缩后的高度
    private int measuredHeight;//测量的真是高度
    private int lastInfoHeight; //上次info容器的高度
    int checkPage = 0;




    /*----------------------------------------------------------------*/
    private LinearLayout strengthLL; //平台实力
    // private LinearLayout fileLL;//平台实力->平台档案
    private ImageView orginfoIV;
    private TextView textOrgRank;
    private TextView textOrgName;
    private TextView textDays;
    private TextView textCapital;
    private TextView textUpTime;
    private TextView textCity;
    private TextView textRate;
    private TextView textICP;
    private String orgName;
    private String orgNumber;
    private TextView textOrgBack;//平台背景
    private TextView textTrusteeship;//资金托管
    //
    private TextView textHonor;//平台实力->平台荣誉
    private RecyclerView orgEnvironmentRecyclerView; //简介
    private RecyclerView orgPapersRecyclerView;//基本信息
    private RecyclerView orgHonorRecyclerView;//荣誉
    //
    private View mParent;
    private View mBg;
    private PhotoView mPhotoView;
    AlphaAnimation in = new AlphaAnimation(0, 1);
    AlphaAnimation out = new AlphaAnimation(1, 0);
    private Info mInfo;
    private LinearLayout desLL;
    private LinearLayout fileLL;
    private LinearLayout teamLL;
    private LinearLayout honorLL;
    private LinearLayout productLL;
    private ImageView desIV;
    private ImageView fileIV;
    private ImageView honorIV;
    private List<PlatformDetail.OrgHonorListBean> orgHonorList;
    private List<PlatformDetail.OrgHonorListBean> orgEnvironmentList;
    private List<PlatformDetail.OrgHonorListBean> orgPapersList;
    private boolean hasInitPlatformStrengthData; //是否初始化了平台实力


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        orginfoDetailTabRootHeight = 100;
        orgName =  getIntent().getStringExtra("orgName");
        orgNumber =  getIntent().getStringExtra("orgNumber");
        super.onCreate(savedInstanceState);
        initData();
        initTopTitle();
        initView();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ProductService.removeList(orgDetailProductList);
        autoSkipHandler.stopLoop();
    }


    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_orginfo_detail;
    }

    /**
     * 获取平台详情数据
     * 接口名称 3.3.3 机构信息及详情 - 黄亚林(已实现)
     * 请求类型 post
     * 请求Url /platfrom/detail
     */
    @Override
    protected PlatFormDetailEntity onLoadDataInBack() throws Exception {
        PlatFormDetailEntity platFormDetailData = MyApp.getInstance().getHttpService().getPlatFormDetailData(orgNumber);
        return platFormDetailData;
    }

    @Override
    protected void onRefreshDataView(PlatFormDetailEntity data) {
        platformDetail = data.getData();
        headerLayout.showTitle(platformDetail.getOrgName());
        setdata(platformDetail);
    }

    private void initTopTitle() {
        headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
        headerLayout.showTitle(orgName);
        headerLayout.showLeftBackButton();
        setTranslucentStatus(true);
        headerLayout.setHeadViewCoverStateBar();
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    private void initView() {
        orgSaleProcuctListView = (XListView) findViewById(R.id.org_detail_xlistView);
        homeHeadView = LayoutInflater.from(ctx).inflate(R.layout.layout_orgdetail_headview, null);
        orginfoIV = (ImageView) homeHeadView.findViewById(R.id.orgLogoIV);


        findViewById(R.id.img_financial_guild).setOnClickListener(this); //猎财攻略

        textOrgName = (TextView) homeHeadView.findViewById(R.id.text_orginfo_detail_name);
        textOrgRank = (TextView) homeHeadView.findViewById(R.id.text_org_rank);
        textDays = (TextView) homeHeadView.findViewById(R.id.textDays);
        textCapital = (TextView) homeHeadView.findViewById(R.id.textCapital);
        textUpTime = (TextView) homeHeadView.findViewById(R.id.textUpTime);
        textCity = (TextView) homeHeadView.findViewById(R.id.textCity);
        textOrgBack = (TextView) homeHeadView.findViewById(R.id.textOrgBack);
        textTrusteeship = (TextView) homeHeadView.findViewById(R.id.textTrusteeship);
        textRate = (TextView) homeHeadView.findViewById(R.id.textRate);
        textICP = (TextView) homeHeadView.findViewById(R.id.textICP);

        textorgAdvantage = (TextView) homeHeadView.findViewById(R.id.text_orginfor_detail_orgAdvantage);//机构自定义tag
        textTag01 = (TextView) homeHeadView.findViewById(R.id.orgingo_detail_tag01);//机构自定义tag
        textTag02 = (TextView) homeHeadView.findViewById(R.id.orgingo_detail_tag02);//机构自定义tag
        textTag04 = (TextView) homeHeadView.findViewById(R.id.orgingo_detail_tag04);//机构等级
        textTag05 = (TextView) homeHeadView.findViewById(R.id.orgingo_detail_tag05);//机构等级
        textTag06 = (TextView) homeHeadView.findViewById(R.id.orgingo_detail_tag06);//机构等级

        //收缩控件 头radioGrop
        cursor = (ImageView) homeHeadView.findViewById(R.id.orginfo_detail_cursor);
        tabHide = (ImageView) homeHeadView.findViewById(R.id.orginfo_detail_tab_hide);
        tabHide.setOnClickListener(this);
        radioGrop = (RadioGroup) homeHeadView.findViewById(R.id.orginfo_detail_radioGroup);

        //banner
        orgActivityAdvertiseLL = (LinearLayout) homeHeadView.findViewById(R.id.org_detail_bannderLL); //机构活动宣传
        productLL = (LinearLayout) homeHeadView.findViewById(R.id.orginfo_detail_productLL); //机构活动宣传

        //机构滑动信息tab
        orginfoDetailTabRoot = (LinearLayout) homeHeadView.findViewById(R.id.orginfo_detail_tab_root);
        newsListView = (MyListView) homeHeadView.findViewById(R.id.orginfor_detail_tab_news_list);//平台动态
        newsListView.setDividerHeight(0);
        teamListView = (MyListView) homeHeadView.findViewById(R.id.orginfor_detail_tab_team_list); //团队
        teamListView.setDividerHeight(0);
        //平台实力
        strengthLL = (LinearLayout) homeHeadView.findViewById(R.id.orginfo_detail_strengthLL);
        textDes = (TextView) homeHeadView.findViewById(R.id.text_orginfor_detail_tab_des);
        textHonor = (TextView) homeHeadView.findViewById(R.id.text_orginfor_detail_tab_honor);
        //平台实力中的 动态
        desLL = (LinearLayout) homeHeadView.findViewById(R.id.instituion_desLL);
        fileLL = (LinearLayout) homeHeadView.findViewById(R.id.instituion_fileLL);
        teamLL = (LinearLayout) homeHeadView.findViewById(R.id.instituion_teamLL);
        honorLL = (LinearLayout) homeHeadView.findViewById(R.id.instituion_honorLL);
        //平台实力的图片

        desIV = (ImageView) homeHeadView.findViewById(R.id.orginfor_detail_des_img);
        fileIV = (ImageView) homeHeadView.findViewById(R.id.orginfor_detail_file_img);
        honorIV = (ImageView) homeHeadView.findViewById(R.id.orginfor_detail_honor_img);
        desIV.setOnClickListener(this);
        fileIV.setOnClickListener(this);
        honorIV.setOnClickListener(this);

        orgEnvironmentRecyclerView = (RecyclerView) homeHeadView.findViewById(R.id.orginfor_detail_des_recycleView);
        orgPapersRecyclerView = (RecyclerView) homeHeadView.findViewById(R.id.orginfor_detail_file_recycleView);
        orgHonorRecyclerView = (RecyclerView) homeHeadView.findViewById(R.id.orginfor_detail_honor_recycleView);

        //  orgEnvironmentRecyclerView.


        //安全保障
        textSafe = (TextView) homeHeadView.findViewById(R.id.text_orginfor_detail_tab_safe);
        productAdapter = new ProductListAdapter(ctx, orgDetailProductList);
        productAdapter.setNoOrgName(true);  //产品名不拼接机构名称
        orgSaleProcuctListView.addHeaderView(homeHeadView);
        orgSaleProcuctListView.setAdapter(productAdapter);
        ProductService.addProductListAndAdapter(orgDetailProductList, productAdapter);
        orgSaleProcuctListView.setDividerHeight(0);
        orgSaleProcuctListView.setXListViewListener(this);

        // PhotoView

        mParent = findViewById(R.id.parent);
        mBg = findViewById(R.id.bg);
        mPhotoView = (PhotoView) findViewById(R.id.img);
        mPhotoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        in.setDuration(300);
        out.setDuration(300);
        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mBg.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mPhotoView.enable();
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBg.startAnimation(out);
                mPhotoView.animaTo(mInfo, new Runnable() {
                    @Override
                    public void run() {
                        mParent.setVisibility(View.GONE);
                    }
                });
            }
        });


        initTab();

    }




    private void initTab() {
        final int bmpW = (TopApp.displayWidth) / 3 - PixelUtil.dip2px(ctx, 45);
        cursor.getLayoutParams().width = bmpW;
        final int offset = PixelUtil.dip2px(ctx, 45);// 偏移量


        radioGrop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radiobtn_orginfo_des:
                        checkPage = 0;
                        newsListView.setVisibility(View.VISIBLE);
                        textSafe.setVisibility(View.GONE);
                        strengthLL.setVisibility(View.GONE);
                        break;
                    case R.id.radiobtn_orginfo_team:

                        HashMap<String, String> map = new HashMap<String, String>();
                    //    map.put(ctx.getString(R.string.umeng_institution_detail_key), ctx.getString(R.string.umeng_institution_strength));
                        MobclickAgent.onEvent(ctx, "platform_module", map);
                        checkPage = 1;
                        newsListView.setVisibility(View.GONE);
                        textSafe.setVisibility(View.GONE);
                        strengthLL.setVisibility(View.VISIBLE);
                        setPlatformStrengthData(platformDetail);
                        break;
                    case R.id.radiobtn_orginfo_safe:
//                        HashMap<String, String> map01 = new HashMap<String, String>();
//                        map01.put(ctx.getString(R.string.umeng_institution_detail_key), ctx.getString(R.string.umeng_institution_safe));
//                        MobclickAgent.onEvent(ctx, "platform_module", map01);
                        checkPage = 2;
                        newsListView.setVisibility(View.GONE);
                        textSafe.setVisibility(View.VISIBLE);
                        strengthLL.setVisibility(View.GONE);
                        break;
                }


                int one = bmpW + offset;// 页卡1 -> 页卡2 偏移量
                Animation animation = new TranslateAnimation(one * currIndex, one * checkPage, 0, 0);//显然这个比较简洁，只有一行代码。
                currIndex = checkPage;
                animation.setFillAfter(true);// True:图片停在动画结束位置
                animation.setDuration(300);
                cursor.startAnimation(animation);


                // orginfoDetailTabRoot.measure(0, 0);
                // TODO: 2016/10/16 未指定宽度测量规范，它会忽略所有的布局PARAMS甚至屏幕尺寸，因此要获得正确的高度，首先获得最大宽度
                orginfoDetailTabRoot.measure(View.MeasureSpec.makeMeasureSpec(TopApp.displayWidth, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                if (currIndex == 1) { //因为在平台实力是显示不全,所以添加个高度50, 其他页显示没问题 不添加
                    measuredHeight = orginfoDetailTabRoot.getMeasuredHeight() + 60;
                } else {
                    measuredHeight = orginfoDetailTabRoot.getMeasuredHeight();
                }

                hideInsFlage = true;

                //   if (measuredHeight > PixelUtil.dip2px(ctx, 250)) {
                //  tabHide.setVisibility(View.VISIBLE);
                //tabHide.setEnabled(measuredHeight > PixelUtil.dip2px(ctx, orginfoDetailTabRootHeight)); //如果内容高度小于orginfoDetailTabRootHeightdp 不可以点击收缩
                tabHide.setVisibility(measuredHeight > PixelUtil.dip2px(ctx, orginfoDetailTabRootHeight) ? View.VISIBLE : View.INVISIBLE); //如果内容高度小于orginfoDetailTabRootHeightdp 不可以点击收缩
                hideTabAnimation(lastInfoHeight, PixelUtil.dip2px(ctx, orginfoDetailTabRootHeight));
                lastInfoHeight = PixelUtil.dip2px(ctx, orginfoDetailTabRootHeight);

                //    } else {
//                    tabHide.setVisibility(View.GONE);
//                    hideTabAnimation(lastInfoHeight, measuredHeight);
//                    lastInfoHeight = measuredHeight;
//
//                }
                tabHide.setImageResource(hideInsFlage ? R.drawable.img_arrow_down : R.drawable.img_arrow_up);
            }
        });
    }

    private void initData() {
        orgName = (String) getIntent().getSerializableExtra("orgName");
        orgNumber = (String) getIntent().getSerializableExtra("orgNumber");
        getplatformSaleProducts(false);
        // todo 初始化时XlistView如果添加了HeadView会自动调用OnLoadMore (pageIndex==1)
        // 如果这里调用getplatformSaleProducts(false) 会导致两次同时调用了pageIndex=1 时的数据
        // 然后下拉时由于pageIndex被调用了两次 会导致pageIndex 直接变成了3而 忽略了pageIndex=2 时的数据
    }


    private void setdata(PlatformDetail data) {
        ImageLoader.getInstance().displayImage(MyApp.getInstance().getHttpService().getImageServerBaseUrl() + data.getOrgLogo(), orginfoIV, PhotoUtil.normalImageOptions);
        textOrgRank.setText(data.getOrgLevel());
        textOrgName.setText(data.getOrgName());
        textorgAdvantage.setText(data.getOrgAdvantage());//机构亮点


        //产品tag
        String orgTag = platformDetail.getOrgTag();
        if (orgTag != null) {
            String[] split = orgTag.split(",");
            textTag04.setVisibility(View.VISIBLE);
            if (split != null && split.length > 0) {
                if (split[0] != null && !TextUtils.isEmpty(split[0].trim())) {
                    textTag01.setVisibility(View.VISIBLE);
                    textTag01.setText(split[0]);
                }
                if (split.length >= 2 && split[1] != null && !TextUtils.isEmpty(split[1].trim())) {
                    textTag02.setVisibility(View.VISIBLE);
                    textTag02.setText(split[1]);
                }
                if (split.length >= 3 && split[2] != null && !TextUtils.isEmpty(split[2].trim())) {
                    textTag04.setVisibility(View.GONE);
                    textTag05.setText(split[2]);
                    textTag05.setVisibility(View.VISIBLE);
                    textTag06.setText(getString(R.string.recommend_orginfo_detail_orglevel) + platformDetail.getOrgLevel());
                    textTag06.setVisibility(View.VISIBLE);
                }
            }


        }

        textTag04.setText(getString(R.string.recommend_orginfo_detail_orglevel) + platformDetail.getOrgLevel());


        vPager = (LoopViewPager) homeHeadView.findViewById(R.id.orginfo_detail_vpager); //机构活动宣传
        List<PlatformDetail.OrgActivitysBean> orgActivitys = platformDetail.getOrgActivitys();

        if (orgActivitys != null && orgActivitys.size() > 0) {
            orgActivityAdvertiseLL.setVisibility(View.VISIBLE);
            vPager.setAdapter(new OrginfoDetailBannerViewPagerAdapter(ctx, orgActivitys, orgName));
            if (orgActivitys.size() > 1) {
                indicator = (CirclePageIndicator) homeHeadView.findViewById(R.id.orginfo_detail_indicator); //机构活动宣传
                indicator.setViewPager(vPager, orgActivitys.size());
            }

        }


        if (orgActivitys != null && orgActivitys.size() > 1) {
            autoSkipHandler.startLoop();
        }
        //tab条中的数据-------------------------------------------

        //平台动态
        List<PlatformDetail.OrgDynamicListBean> orgDynamicList = platformDetail.getOrgDynamicList();
        if (orgDynamicList != null && orgDynamicList.size() > 0) {
            newsListView.setAdapter(new OrgInfoNewsAdapter(ctx, orgDynamicList));
        }
        setPlatformStrengthData(data);


        radioGrop.check(R.id.radiobtn_orginfo_des);
    }

    /**
     * 设置平台实力数据
     *
     * @param data
     */
    private void setPlatformStrengthData(PlatformDetail data) {
        if (hasInitPlatformStrengthData) return;
        //平台简介
        String orgProfile = data.getOrgProfile();
        textDes.setText(orgProfile);
        textDes.setVisibility(TextUtil.isEmpty(orgProfile) ? View.GONE : View.VISIBLE);
        if (TextUtil.isEmpty(orgProfile)) {
            desLL.setVisibility(View.GONE); //当平台描述不为空时显示
        }
        //平台档案
        String deadLineValueText = data.getDeadLineValueText();
        if (deadLineValueText != null && deadLineValueText.contains(",")) {
            String[] splitDatas = deadLineValueText.split(",");//deadLineValueText=30,天,2,个月
            StringBuilder sb = new StringBuilder();
            sb.append(splitDatas[0]).append(splitDatas[1]);
            if (splitDatas.length >= 4) {
                sb.append("~").append(splitDatas[2]).append(splitDatas[3]);
            }
            textDays.setText(sb);
        } else {
            textDays.setText(data.getProDaysMin() + "天~" + data.getProDaysMax() + "天");
        }
        textCapital.setText(data.getCapital());
        textUpTime.setText(data.getUpTime());
        textCity.setText(data.getCity());
        String feeRateMin = data.getFeeRateMin();
        String feeRateMax = data.getFeeRateMax();
        textRate.setText(feeRateMin != null && feeRateMin.equals(feeRateMax) ? feeRateMax + "%" : feeRateMin + "% ~ " + feeRateMax + "%");
        textICP.setText(data.getIcp());
        textOrgBack.setText(data.getOrgBack());
        textTrusteeship.setText(data.getTrusteeship());


        //平台荣誉
        orgHonorList = data.getOrgHonorList();
        //办公环境
        orgEnvironmentList = data.getOrgEnvironmentList();
        //机构证书
        orgPapersList = data.getOrgPapersList();

        OrgInfoDetailDesPicAdapter orgEnvironmentAdapter = new OrgInfoDetailDesPicAdapter(ctx, orgEnvironmentList);
        OrgInfoDetailDesPicAdapter orgPagersAdapter = new OrgInfoDetailDesPicAdapter(ctx, orgPapersList);
        OrgInfoDetailDesPicAdapter orgHonorAdapter = new OrgInfoDetailDesPicAdapter(ctx, orgHonorList);
        // GridLayoutManager gridLayoutManager = new GridLayoutManager(ctx, 3);
        //环境 证书 荣誉  照片
        orgEnvironmentRecyclerView.setLayoutManager(new GridLayoutManager(ctx, 3));
        orgPapersRecyclerView.setLayoutManager(new GridLayoutManager(ctx, 3));
        orgHonorRecyclerView.setLayoutManager(new GridLayoutManager(ctx, 3));


        orgEnvironmentRecyclerView.setAdapter(orgEnvironmentAdapter);
        orgPapersRecyclerView.setAdapter(orgPagersAdapter);
        orgHonorRecyclerView.setAdapter(orgHonorAdapter);

        //如果平台实力的简介,机构档案,荣誉只用一张图片时显示大图
        if (orgEnvironmentList.size() == 1) {
            PhotoUtil.loadImageByImageLoader(orgEnvironmentList.get(0).getOrgPicture(), desIV);
            desIV.setVisibility(View.VISIBLE);
            orgEnvironmentRecyclerView.setVisibility(View.GONE);

        } else {
            desIV.setVisibility(View.GONE);
            orgEnvironmentRecyclerView.setVisibility(View.VISIBLE);
        }
        if (orgPapersList.size() == 1) {
            PhotoUtil.loadImageByImageLoader(orgPapersList.get(0).getOrgPicture(), fileIV);
            fileIV.setVisibility(View.VISIBLE);
            orgPapersRecyclerView.setVisibility(View.GONE);

        } else {
            fileIV.setVisibility(View.GONE);
            orgPapersRecyclerView.setVisibility(View.VISIBLE);
        }
        if (orgHonorList.size() == 1) {
            PhotoUtil.loadImageByImageLoader(orgHonorList.get(0).getOrgPicture(), honorIV);
            honorIV.setVisibility(View.VISIBLE);
            orgHonorRecyclerView.setVisibility(View.GONE);

        } else {
            honorIV.setVisibility(View.GONE);
            orgHonorRecyclerView.setVisibility(View.VISIBLE);
        }


        String orgHonor = data.getOrgHonor();
        textHonor.setText(orgHonor);
        textHonor.setVisibility(TextUtil.isEmpty(orgHonor) ? View.GONE : View.VISIBLE);
        if (TextUtil.isEmpty(orgHonor) && (orgHonorList == null || orgHonorList.size() == 0)) {
            honorLL.setVisibility(View.GONE);
        }
        textSafe.setText(data.getOrgSecurity());
        //团队成员列表
        List<TeamInfosBean> teamList = data.getTeamInfos();
        if (teamList != null && teamList.size() > 0) {
            OrgInfoTeamAdapter teamAdapter = new OrgInfoTeamAdapter(ctx, teamList);
            teamListView.setAdapter(teamAdapter);
            teamLL.setVisibility(View.VISIBLE);
        }
        hasInitPlatformStrengthData = true;
    }


    /**
     * 获取平台销售的产品数据
     *
     * @param openDialog
     */
    private void getplatformSaleProducts(boolean openDialog) {
        new MyNetAsyncTask(ctx, openDialog) {
            ProductDatasDataEntity response;
            long loadSysTime;
            @Override
            protected void doInBack() throws Exception {
                loadSysTime = System.currentTimeMillis();
                response = MyApp.getInstance().getHttpService().getFinancingProduct( orgNumber, productOrder, pageIndex + "", pageSize + "", productSort);
            }

            @Override
            protected void onPost(Exception e) {

                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {

                        if (pageIndex == 1) {
                            orgDetailProductList.clear();
                        }
                        List<ProductDetail> datas = response.getData().getDatas();
                        orgDetailProductList.addAll(datas);
                        productAdapter.notifyDataSetChanged(loadSysTime);
//                        productLL.setVisibility(datas.size() > 0 ? View.VISIBLE : View.GONE);

                        if (response.getData().getPageCount() <= response.getData().getPageIndex() || response.getData().getPageCount() <= 1) {
                            orgSaleProcuctListView.setPullLoadEnable(false);
                        } else {
                            orgSaleProcuctListView.setPullLoadEnable(true);
                        }

                        pageIndex++;
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
                stopLoad();

            }
        }.execute();
    }

    /**
     * 停止刷新
     */
    public void stopLoad() {
        if (orgSaleProcuctListView != null) {
            orgSaleProcuctListView.stopRefresh();
            orgSaleProcuctListView.stopLoadMore();
        }
    }

    @Override
    public void onRefresh() {
        hasInitPlatformStrengthData = false;
        pageIndex = 1;
        getplatformSaleProducts(false);
        loadData(false);
    }

    @Override
    public void onLoadMore() {
        if(pageIndex<2){
//            getplatformSaleProducts(false);
        }else{
            getplatformSaleProducts(true);
        }

    }


    @Override
    protected Intent getGestureActivityIntent(TopBaseActivity activity) {
        return null;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {


            case R.id.orginfo_detail_tab_hide: //隐藏或者显示机构详情 (描述 团队 安全保障)

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, hideInsFlage ? orginfoDetailTabRootHeight : measuredHeight);
                orginfoDetailTabRoot.setLayoutParams(params);
                if (!hideInsFlage) {
                    hideTabAnimation(measuredHeight, PixelUtil.dip2px(ctx, orginfoDetailTabRootHeight));
                } else {
                    hideTabAnimation(PixelUtil.dip2px(ctx, orginfoDetailTabRootHeight), measuredHeight);
                }
                hideInsFlage = !hideInsFlage;
                tabHide.setImageResource(hideInsFlage ? R.drawable.img_arrow_down : R.drawable.img_arrow_up);
                break;
            case R.id.img_financial_guild: //猎财攻略
                if (platformDetail == null) break;

                WebActivityCommon.showThisActivity(this, MyApp.getInstance().getDefaultSp().getInvestmentStrategy() + "?orgCode=" + platformDetail.getOrgNo(), "");
                break;
            case R.id.orginfor_detail_des_img://显示平台实力->平台简介机构大图
                ArrayList<String> imgUrlstrings = new ArrayList<>();
                for (int i = 0; i < orgEnvironmentList.size(); i++) {
                    imgUrlstrings.add(orgEnvironmentList.get(i).getOrgPicture());

                }
                Intent intent1 = new Intent(ctx, ViewPagerActivity.class);
                intent1.putStringArrayListExtra("imgUrlstrings", imgUrlstrings);
                ctx.startActivity(intent1);
                break;
            case R.id.orginfor_detail_file_img://显示平台实力->机构信息大图
                ArrayList<String> imgUrlstrings02 = new ArrayList<>();
                for (int i = 0; i < orgPapersList.size(); i++) {
                    imgUrlstrings02.add(orgPapersList.get(i).getOrgPicture());

                }
                Intent intent02 = new Intent(ctx, ViewPagerActivity.class);
                intent02.putStringArrayListExtra("imgUrlstrings", imgUrlstrings02);
                ctx.startActivity(intent02);
                break;
            case R.id.orginfor_detail_honor_img://显示平台实力->机构荣誉大图
                ArrayList<String> imgUrlstrings03 = new ArrayList<>();
                for (int i = 0; i < orgHonorList.size(); i++) {
                    imgUrlstrings03.add(orgHonorList.get(i).getOrgPicture());

                }
                Intent intent03 = new Intent(ctx, ViewPagerActivity.class);
                intent03.putStringArrayListExtra("imgUrlstrings", imgUrlstrings03);
                ctx.startActivity(intent03);
                break;
        }
    }

    /**
     * 打开关闭机构的介绍
     *
     * @param start 开始控件的高度
     * @param end   结束控件的高度
     */
    private void hideTabAnimation(int start, int end) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, animatedValue);
                orginfoDetailTabRoot.setLayoutParams(params);

            }
        });
        valueAnimator.start();
    }

    @Override
    public void onItemClick(PhotoView orgDesIV, String imgUrl) {

        //判断是不是md5 如果是添加图片服务器的路径
        if (!TextUtils.isEmpty(imgUrl) && !imgUrl.startsWith("http")) {
            imgUrl = MyApp.getInstance().getHttpService().getImageServerBaseUrl() + imgUrl;
        }

        PhotoUtil.loadImageByGlide(this, imgUrl + "?f=png", mPhotoView);

        mInfo = orgDesIV.getInfo();
        mBg.startAnimation(in);
        mBg.setVisibility(View.VISIBLE);
        mParent.setVisibility(View.VISIBLE);
        mPhotoView.animaFrom(mInfo);
    }

    class AutoScrollHandler extends Handler {
        boolean pause = false;

        @Override
        public void handleMessage(Message msg) {
            if (!pause) {
                vPager.setCurrentItem(vPager.getCurrentItem() + 1);
            }
            sendEmptyMessageDelayed(msg.what, 4000);
        }

        void startLoop() {
            pause = false;
            removeCallbacksAndMessages(null);
            sendEmptyMessageDelayed(1, 4000);
        }

        void stopLoop() {
            removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onBackPressed() {
        if (mParent.getVisibility() == View.VISIBLE) {
            mBg.startAnimation(out);
            mPhotoView.animaTo(mInfo, new Runnable() {
                @Override
                public void run() {
                    mParent.setVisibility(View.GONE);
                }
            });
        } else {
            super.onBackPressed();
        }
    }
}
