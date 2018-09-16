package com.v5ent.xiubit.fragment.main;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.toobei.common.view.MyShowTipsView;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.activity.MainActivity;
import com.v5ent.xiubit.data.pageadapter.OrgAndProductPagerAdapter;
import com.v5ent.xiubit.event.FundListBackRefreshEvent;
import com.v5ent.xiubit.event.OpenFundSearchEvent;
import com.v5ent.xiubit.fragment.FragmentBase;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.xsl781.utils.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/6/21
 */

public class FragmentOrgAndProduct extends FragmentBase {

    @BindView(R.id.tabLayout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.vPager)
    ViewPager mVPager;
    @BindView(R.id.headViewLi)
    RelativeLayout headViewLi;
    @BindView(R.id.status_bar)
    View mStatusBar;
    @BindView(R.id.searchIv)
    ImageView mSearchIv;
    private int defaultTabIndex;
    private MyShowTipsView mMyShowTipsView2;
    private MainActivity mMainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(ctx)
                .inflate(R.layout.fragment_org_and_product, null);
        ButterKnife.bind(this, rootView);
        mMainActivity = (MainActivity)ctx;
        initView();
        return rootView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (mVPager == null) return;
        if (!hidden && mVPager.getCurrentItem() == 2){
            EventBus.getDefault().post(new FundListBackRefreshEvent(true));
            initShowTip();
        }
    }

    private void initView() {
        setHeadViewCoverStateBar(mStatusBar);
        FragmentManager fragmentManager = getChildFragmentManager();
        mVPager.setAdapter(new OrgAndProductPagerAdapter(ctx, fragmentManager));
        mVPager.setOffscreenPageLimit(1);
//        String[] title = {"网贷" ,"超级返","基金","保险"};
        String[] title = {"网贷" ,"超级返"};
        mTabLayout.setViewPager(mVPager, title);
        mVPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        MobclickAgent.onEvent(ctx,"T_2_1");
                        break;
                    case 1:
                        MobclickAgent.onEvent(ctx,"T_5_1");
                        break;
                    case 2:
                        MobclickAgent.onEvent(ctx,"T_3_1");
                        break;
                    case 3:
                        MobclickAgent.onEvent(ctx,"T_4_1");
                        break;
                }
                    mSearchIv.setVisibility(position == 2 ?View.VISIBLE:View.INVISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mVPager.setCurrentItem(defaultTabIndex);
        initShowTip();
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

    /**
     * 新手引导图片
     */
    private void initShowTipsView() {
//        MyShowTipsView  myShowTipsView1 = new MyShowTipsView(ctx, mTabLayout, GuideViewType.FRAGMENT_HOME_INVEST_GUIDE1
//                .getValue(),
//                DisplayUtil.dip2px(ctx, -5), DisplayUtil.dip2px(ctx, 0), GuideViewType.FRAGMENT_HOME_INVEST_GUIDE1
//                .toString());
//
//        myShowTipsView1.setLocationOffset(DisplayUtil.dip2px(ctx, 190), 0);
//        myShowTipsView1.setExpandSize(-DisplayUtil.dip2px(ctx, 190), 0);
////        myShowTipsView1.setDisplayOneTime(false);
//        myShowTipsView1.show(ctx);
//
//        //
//        mMyShowTipsView2 = new MyShowTipsView(ctx, mTabLayout, GuideViewType.FRAGMENT_HOME_INVEST_GUIDE1
//                .getValue(),//
//                PixelUtil.dip2px(ctx, -5), PixelUtil.dip2px(ctx, 0), GuideViewType.FRAGMENT_HOME_INVEST_GUIDE1
//                .toString());
////                        mMyShowTipsView2.setDisplayOneTime(false);
//        mMyShowTipsView2.setLocationOffset(PixelUtil.dip2px(ctx, 190), 0);
//        mMyShowTipsView2.setExpandSize(-PixelUtil.dip2px(ctx, 190), 0);
//
//        mMyShowTipsView2.setOnShowTipHideListener(new MyShowTipsView.onShowTipHideListener() {
//            @Override
//            public void onShowTipHide() {
//
//                mMyShowTipsView1.show(ctx);
//            }
//        });
////         mMyShowTipsView1.setDisplayOneTime(false);
//        if (mMainActivity.curIndex == 1) {  // 当前在当前才显示新手引导
//            mMyShowTipsView2.show(ctx);
//        }
    }



    @OnClick(R.id.searchIv)
    public void onViewClicked() {
        Logger.e("OpenFundSearchEvent-post");
        //弹出搜索页面
        EventBus.getDefault().post(new OpenFundSearchEvent());
    }

    public void skipTab(int tabIndext) {
        // 0 、1网贷 2基金  3保险 4限时抢
        int pageIndex = 0;
        switch (tabIndext){
            case 0:
            case 1:
                pageIndex = 0;
                break;
            case 2:
                pageIndex = 2;
                break;
            case 3:
                pageIndex = 3;
                break;
            case 4:
                pageIndex = 1;
                break;
        }
        if (mVPager != null){
            mVPager.setCurrentItem(pageIndex);
        }else {
            defaultTabIndex = pageIndex;
        }
    }
}
