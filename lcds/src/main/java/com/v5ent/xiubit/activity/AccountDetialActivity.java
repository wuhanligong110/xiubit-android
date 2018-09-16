package com.v5ent.xiubit.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyNetworkBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.pageadapter.AccountDetailFragmentPageAdapter;
import com.v5ent.xiubit.entity.IncomeDetailType;
import com.v5ent.xiubit.entity.LieCaiBalanceEntity;
import com.v5ent.xiubit.view.MyPagerSlidingTabStrip;
import com.v5ent.xiubit.view.MyTextView;

import org.xsl781.utils.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hasee-pc on 2017/2/13.
 * Activity-资金明细
 */

public class AccountDetialActivity extends MyNetworkBaseActivity<LieCaiBalanceEntity> implements OnRefreshListener {
    @BindView(R.id.tabLayout)
    MyPagerSlidingTabStrip tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    List<IncomeDetailType> incomeDetailTypes = new ArrayList<>();
    @BindView(R.id.accountBalanceTv)
    MyTextView accountBalanceTv;
    @BindView(R.id.incomeSummaryTv)
    MyTextView incomeSummaryTv;
    @BindView(R.id.outSummaryTv)
    MyTextView outSummaryTv;
    @BindView(R.id.refreshView)
    SmartRefreshLayout mRefreshView;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    private AccountDetailFragmentPageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTitleView();
        initView();
    }

    private void initView() {
        incomeDetailTypes.add(new IncomeDetailType("0", "全部明细"));
        incomeDetailTypes.add(new IncomeDetailType("1", "收入明细"));
        incomeDetailTypes.add(new IncomeDetailType("2", "支出明细"));

        mAdapter = new AccountDetailFragmentPageAdapter(ctx, getSupportFragmentManager(), incomeDetailTypes);
        viewPager.setAdapter(mAdapter);
        tabLayout.setViewPager(viewPager);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                Logger.e("verticalOffset", "verticalOffset=====>" + Boolean.valueOf(verticalOffset >= 0));
                mRefreshView.setEnableRefresh(verticalOffset >= 0);


            }
        });

        mRefreshView.setOnRefreshListener(this);

    }

    @Override
    protected void initTitleView() {
        super.initTitleView();
        headerLayout.showLeftBackButton();
        headerLayout.showTitle("资金明细");
    }

    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_account_detial;
    }

    @Override
    protected LieCaiBalanceEntity onLoadDataInBack() throws Exception {
        return MyApp.getInstance().getHttpService().getLieCaiBalance(MyApp.getInstance().getLoginService().token);
    }

    @Override
    protected void onRefreshDataView(LieCaiBalanceEntity data) {
        accountBalanceTv.setText(data.getData().getAccountBalance());
        incomeSummaryTv.setText(data.getData().getIncomeSummary());
        outSummaryTv.setText(data.getData().getOutSummary());
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mRefreshView.finishRefresh();
        loadDataFromNet(false);
        mAdapter.refresh(incomeDetailTypes);
    }
}
