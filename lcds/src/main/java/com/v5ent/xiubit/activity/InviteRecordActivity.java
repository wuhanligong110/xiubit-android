package com.v5ent.xiubit.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.data.MyBasePagerAdapter;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyNetworkBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.entity.InvitationNumEntity;
import com.v5ent.xiubit.fragment.InviteRecordFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.v5ent.xiubit.fragment.InviteRecordFragment.TYPE_KEY;
import static com.v5ent.xiubit.fragment.InviteRecordFragment.TYPE_INVITE_CUSTOMER;
import static com.v5ent.xiubit.fragment.InviteRecordFragment.TYPE_RECOMMEND_CFG;

/**
 * 公司: tophlc
 * 类说明:邀请记录
 *
 * @author hasee-pc
 * @time 2017/6/30
 */

public class InviteRecordActivity extends MyNetworkBaseActivity<InvitationNumEntity>{

    @BindView(R.id.cfgNumTv)
    TextView mCfgNumTv;
    @BindView(R.id.redLine1)
    View mRedLine1;
    @BindView(R.id.tabRl1)
    RelativeLayout mTabRl1;
    @BindView(R.id.customerNumTv)
    TextView mCustomerNumTv;
    @BindView(R.id.redLine2)
    View mRedLine2;
    @BindView(R.id.tabRl2)
    RelativeLayout mTabRl2;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tabTextv1)
    TextView mTabTextv1;
    @BindView(R.id.tabTextv2)
    TextView mTabTextv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        headerLayout.showTitle("邀请记录");
        headerLayout.showLeftBackButton();
        initViewPage();
    }



    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_invite_record;
    }

    @Override
    protected InvitationNumEntity onLoadDataInBack() throws Exception {
        return MyApp.getInstance().getHttpService().getInvitationNum();
    }

    @Override
    protected void onRefreshDataView(InvitationNumEntity data) {
        mCfgNumTv.setText("("+data.getData().getCfpNum()+")");
        mCustomerNumTv.setText("("+data.getData().getInvestorNum()+")");
    }


    private void initViewPage() {
        mViewPager.setAdapter(new InviteRecordActivity.MyPagerAdapter(ctx, getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                onTabClickChangeView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(0);
    }


    private void onTabClickChangeView(int i) {
        switch (i) {
            case 0:
                mTabTextv2.setTextColor(ContextCompat.getColor(ctx,R.color.text_black_common));
                mCustomerNumTv.setTextColor(ContextCompat.getColor(ctx,R.color.text_black_common));
                mRedLine2.setVisibility(View.INVISIBLE);

                mTabTextv1.setTextColor(ContextCompat.getColor(ctx,R.color.text_blue_common));
                mCfgNumTv.setTextColor(ContextCompat.getColor(ctx,R.color.text_blue_common));
                mRedLine1.setVisibility(View.VISIBLE);
                break;
            case 1:
                mTabTextv1.setTextColor(ContextCompat.getColor(ctx,R.color.text_black_common));
                mCfgNumTv.setTextColor(ContextCompat.getColor(ctx,R.color.text_black_common));
                mRedLine1.setVisibility(View.INVISIBLE);

                mTabTextv2.setTextColor(ContextCompat.getColor(ctx,R.color.text_blue_common));
                mCustomerNumTv.setTextColor(ContextCompat.getColor(ctx,R.color.text_blue_common));
                mRedLine2.setVisibility(View.VISIBLE);
                break;

        }
    }


    @OnClick({R.id.tabRl1, R.id.tabRl2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tabRl1:
                onTabClickChangeView(0);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tabRl2:
                onTabClickChangeView(1);
                mViewPager.setCurrentItem(1);
                break;
        }
    }

    public class MyPagerAdapter extends MyBasePagerAdapter {

        public MyPagerAdapter(TopBaseActivity ctx, FragmentManager supportFragmentManager) {
            super(ctx,supportFragmentManager,null);
            fragments[0] = new InviteRecordFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putInt(TYPE_KEY, TYPE_RECOMMEND_CFG);
            fragments[0].setArguments(bundle1);

            fragments[1] = new InviteRecordFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putInt(TYPE_KEY, TYPE_INVITE_CUSTOMER);
            fragments[1].setArguments(bundle2);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }
    }
}
