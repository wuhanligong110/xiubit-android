package com.toobei.tb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.HomePagerBanners;
import com.toobei.common.entity.HomePagerBannersDatasDataEntity;
import com.toobei.common.entity.ProductClassifyPreferenceDetail;
import com.toobei.common.entity.ProductClassifyPreferenceEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.NetCachAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.FloatImageView;
import com.toobei.common.view.loopviewpager.AutoScrollViewPager;
import com.toobei.common.view.loopviewpager.indicator.LinePageIndicator;
import com.toobei.tb.MyApp;
import com.toobei.tb.R;
import com.toobei.tb.activity.FreshStrategyWebActivity;
import com.toobei.tb.activity.LoginActivity;
import com.toobei.tb.activity.MainActivity;
import com.toobei.tb.activity.MyQRCodeActivity;
import com.toobei.tb.activity.WebActivityCommon;
import com.toobei.tb.adapter.HomeBannerAdapter;
import com.toobei.tb.adapter.ProductClassifyAdapter;
import com.toobei.tb.util.C;

import org.xsl781.ui.xlist.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司: tophlc 类说明：首页
 *
 * @date 2016-1-04
 */

public class FragmentHome extends FragmentBase implements XListView.IXListViewListener, View.OnClickListener, FloatImageView.OnSingleTouchListener{


    private AutoScrollViewPager viewPager;
    private XListView homeClassifyListView;
    private List<HomePagerBanners> homePagerBannerses;
    private LinePageIndicator indicator;

    private ScaleAnimation enterAnim;

    private int screenWidth;
    private int screenHeight;
    private MainActivity activity;
    private View homeHeadView;
    private ProductClassifyAdapter classifyAdapter;
    private FloatImageView imgFreshWelfare;


    @Override
    public void onResume() {
        super.onResume();
//        Logger.d("FragmentHome--onResume");
        //判断登录状态，显示新手福利图标
        boolean isLogined = MyApp.getInstance().getLoginService().isCachPhoneExist();
        if (isLogined) {
            activity.getTopMsgCount();
            imgFreshWelfare.stopFreshWelfare();
            imgFreshWelfare.setVisibility(View.GONE);
        } else {
            imgFreshWelfare.setVisibility(View.VISIBLE);
            imgFreshWelfare.startFreshWelfare();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) ctx;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.fragment_home, null);
        initView(rootView);
        getHomePagerBannersDatas(false);
        getProductClassifyDatas(true);
        return rootView;
    }

    /**
     * 获取首页热门产品
     *
     * @param isRefresh
     */
    private void getProductClassifyDatas(boolean isRefresh) {

        new MyNetAsyncTask(ctx, isRefresh) {
            long loadSystem;
            ProductClassifyPreferenceEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = TopApp.getInstance().getHttpService().productClassifyPreference(MyApp.getInstance().getLoginService().token, "");
                loadSystem = System.currentTimeMillis();
            }

            @Override
            protected void onPost(Exception e) {
                stopLoad();
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        List<ProductClassifyPreferenceDetail> datas = response.getData().getDatas();
                        if (datas != null && datas.size() > 0) {
                           classifyAdapter.clear();
                            classifyAdapter.addAll(datas,loadSystem);
                            classifyAdapter.notifyDataSetChanged();
//                            classifyAdapter.refresh(datas);
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


    @SuppressWarnings("unchecked")
    private void initView(final View rootView) {
        DisplayMetrics dm = getResources().getDisplayMetrics();

        homeClassifyListView = (XListView) rootView.findViewById(R.id.org_detail_xlistView);
        homeHeadView = LayoutInflater.from(activity).inflate(R.layout.layout_home_headview, null);

        viewPager = (AutoScrollViewPager) homeHeadView.findViewById(R.id.home_image_viewpager);
        indicator = (LinePageIndicator) homeHeadView.findViewById(R.id.home_indicator);
        // 首页热门产品列表
        homeClassifyListView.addHeaderView(homeHeadView);
        homeClassifyListView.setXListViewListener(this);
        homeClassifyListView.setPullLoadEnable(false);
        homeClassifyListView.setAutoLoadMore(false);
        classifyAdapter = new ProductClassifyAdapter((MainActivity) ctx, new ArrayList<ProductClassifyPreferenceDetail>());
        homeClassifyListView.setAdapter(classifyAdapter);
        homeHeadView.findViewById(R.id.home_head_fresh_strategyLL).setOnClickListener(this);//
        homeHeadView.findViewById(R.id.home_head_learn_about_us_productLL).setOnClickListener(this);//
        homeHeadView.findViewById(R.id.home_head_invite_friendLL).setOnClickListener(this);//

        imgFreshWelfare = (FloatImageView) rootView.findViewById(R.id.img_fresh_welfare);
        //在布局完成后获取fragment的高度
        final View fragmentRootView = rootView;
        rootView.post(new Runnable() {
            @Override
            public void run() {
                imgFreshWelfare.setViewHeight(fragmentRootView.getHeight());
            }
        });

        imgFreshWelfare.setOnSingleTouch(this);
    }

    /**
     * 获取banner 数据  2016/12/20 0020  t呗banner
     * advPlacement	广告位置描述	string   pc首页页中：pc_idx_middle (必填),pc端banner：pc_banner,平台banner:platform_banner,产品banner:product_banner
     * appType	端口	number                   理财师1，投资端2 （必填）
     */
    private void getHomePagerBannersDatas(final boolean isRefresh) {
        new NetCachAsyncTask<HomePagerBannersDatasDataEntity>("homePagerBanners", 60 * 24 * 1, false, isRefresh) {

            @Override
            protected HomePagerBannersDatasDataEntity getData() throws Exception {
                return MyApp.getInstance().getHttpService().homepageAdvs(MyApp.getInstance().getLoginService().token, "app_home_page", "2");
            }

            @Override
            protected void onPost(Exception e, HomePagerBannersDatasDataEntity response, boolean isDataFromServer) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        homePagerBannerses = response.getData().getDatas();
                        if (homePagerBannerses != null && homePagerBannerses.size() > 0) {
                            initBannersView(homePagerBannerses);
                        }
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(MyApp.getInstance().getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }


    @Override
    public void onRefresh() {
        if(isLogined()){
            activity.getTopMsgCount();
        }
        getHomePagerBannersDatas(true);
        getProductClassifyDatas(true);
    }

    @Override
    public void onLoadMore() {

    }

    private void stopLoad() {
        if (homeClassifyListView != null) {
            homeClassifyListView.stopLoadMore();
            homeClassifyListView.stopRefresh();
        }
    }

    /**
     * 显示banners
     *
     * @param homePagerBannerses banner
     */
    private void initBannersView(final List<HomePagerBanners> homePagerBannerses) {
        HomeBannerAdapter adapter = new HomeBannerAdapter(activity, homePagerBannerses, HomeBannerAdapter.BannerType.PRODUCT_BANNER_TYPE);
        viewPager.setAdapter(adapter);
        if (homePagerBannerses.size() > 1) {
            viewPager.startloop();
        }

//        if (indicator.getViewPager() == null) {
            //indicator修改过后可以给无限轮播的viewpager使用
            indicator.setViewPager(viewPager, homePagerBannerses.size());
//        }

        //避免indicator控件不靠右而居中
        indicator.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.home_head_fresh_strategyLL: // 新手攻略


                intent = new Intent(activity, FreshStrategyWebActivity.class);
                intent.putExtra("url", C.URL_NEWERSTRATEGY);
                activity.startActivity(intent);

                break;

            case R.id.home_head_learn_about_us_productLL: //了解我们
                WebActivityCommon.showThisActivity((TopBaseActivity) activity, C.URL_HOME_LEARN_ABOUT_US, "");
                break;

            case R.id.home_head_invite_friendLL: // 邀请朋友

                if (!MyApp.getInstance().getLoginService().isCachPhoneExist()) {
//                    ((MainActivity) activity).mRadioGroup.check(R.id.main_btn_mine);
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                    break;
                }
                intent = new Intent(activity, MyQRCodeActivity.class);
                activity.startActivity(intent);
                break;
//            case R.id.img_fresh_welfare: //新手福利
//                activity.startActivity(new Intent(activity, LoginActivity.class));
//                break;
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
    public void onSingleTouch(View v) {
        switch (v.getId()) {
            case R.id.img_fresh_welfare: //新手福利
                activity.startActivity(new Intent(activity, LoginActivity.class));
                break;
        }
    }
}