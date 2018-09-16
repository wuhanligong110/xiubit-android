package com.v5ent.xiubit.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.toobei.common.entity.BaseResponseEntity;
import com.v5ent.xiubit.MyNetworkBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.pageadapter.SelectedProductPageAdapter;
import com.v5ent.xiubit.entity.CommonTabEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/4
 */

public class SelectedProductActivity extends MyNetworkBaseActivity {
   
    @BindView(R.id.tabLayout)
    CommonTabLayout mTabLayout;
    @BindView(R.id.vPager)
    ViewPager mVPager;
    

    @Override
    protected void onInitParamBeforeLoadData() {
        super.onInitParamBeforeLoadData();
        loadDataInStartEnable = false;
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        headerLayout.showTitle("网贷精选");
        headerLayout.showLeftBackButton();
        
        mVPager.setAdapter(new SelectedProductPageAdapter(ctx,getSupportFragmentManager()));
        ArrayList<CustomTabEntity> tabTitles = new ArrayList<>();
        tabTitles.add(new CommonTabEntity("日进斗金"));
        tabTitles.add(new CommonTabEntity("年年有余"));
        mTabLayout.setTabData(tabTitles);

        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mVPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mVPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                
            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_selected_product;
    }

    @Override
    protected BaseResponseEntity onLoadDataInBack() throws Exception {
        return null;
    }

    @Override
    protected void onRefreshDataView(BaseResponseEntity data) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
