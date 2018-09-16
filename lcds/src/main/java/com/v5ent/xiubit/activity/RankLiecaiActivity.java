package com.v5ent.xiubit.activity;



import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.v5ent.xiubit.MyTitleBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.fragment.FragmentRank;
import com.v5ent.xiubit.util.C;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity-理财排行榜
 */
public class RankLiecaiActivity extends MyTitleBaseActivity {
    @BindView(R.id.fragmentFl)
    FrameLayout mFragmentFl;
    private String mMonth = "本月";


//    @BindView(R.id.tl_1)
//    com.flyco.tablayout.SegmentTabLayout tl1;
//    @BindView(R.id.vPager)
//    ViewPager vPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mMonth = getIntent().getStringExtra(C.ActivityIntent.MONTH);
        initView();

    }

    private void initView() {
        headerLayout.showLeftBackButton();
        headerLayout.showTitle("理财"+mMonth+"月收益榜");
        FragmentManager fm = getFragManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragmentFl,new FragmentRank(0,mMonth));
        transaction.commit();
         /* V2.3.0==============================================================
        headerLayout.showTitle(R.string.ativity_rank);
        vPager.setInvestRecordAdapter(new RankPagerAdapter(ctx, getSupportFragmentManager()));
        String[] title = {"收益榜", "leader奖励榜"};
//        ArrayList<Fragment> fragments = new ArrayList<>(2);
//        RankPagerAdapter investRecordAdapter = (RankPagerAdapter) vPager.getInvestRecordAdapter();
//        fragments.add(investRecordAdapter.fragments[0]);
//        fragments.add(investRecordAdapter.fragments[1]);
//        tl1.setTabData(title, this, R.id.vPager, fragments);
        tl1.setTabData(title);
        tl1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        vPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tl1.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_rank_liecai;
    }
}
