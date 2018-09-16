package com.v5ent.xiubit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jungly.gridpasswordview.Util;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.toobei.common.entity.FundHoldingsStatisticData;
import com.toobei.common.entity.FundHoldingsStatisticEntiy;
import com.toobei.common.entity.FundInvestorHoldingsData.FundInvestorDetialDataBean;
import com.toobei.common.entity.FundInvestorHoldingsEntiy;
import com.toobei.common.network.NetworkObserver;
import com.toobei.common.network.httpapi.ThirdPartApi;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.NetBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.holder.FundExceptionHolder;
import com.v5ent.xiubit.data.recylerapter.MineFundListAdapter;
import com.v5ent.xiubit.service.JumpFundService;
import com.v5ent.xiubit.util.ParamesHelp;
import com.toobei.common.network.RetrofitHelper;
import com.v5ent.xiubit.view.EmptyView;
import com.v5ent.xiubit.view.MyTextView;

import org.xsl781.utils.SystemTool;

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
 * @time 2017/8/21
 */

public class MineFundActivity extends NetBaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshView)
    SmartRefreshLayout mRefreshView;
    private HeadViewHolder mHeadViewHolder;
    private MineFundListAdapter mAdapter;
    private EmptyView mEmptyView;
    private FundExceptionHolder mFundExceptionHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        getNetData(true);
    }


    private void initView() {
        headerLayout.showLeftBackButton();
        headerLayout.showTitle("我的基金");
        //header
        mHeadViewHolder = new HeadViewHolder();

        mAdapter = new MineFundListAdapter();
        mAdapter.addHeaderView(mHeadViewHolder.mRootView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        mRecyclerView.setAdapter(mAdapter);
        //空视图和错误视图
        mHeadViewHolder.mRootView.measure(0, 0);
        int height = MyApp.displayHeight - SystemTool.getStatusBarHeight(ctx)
                - Util.dp2px(ctx, 44) //顶部标题栏
                - mHeadViewHolder.mRootView.getMeasuredHeight();
        mEmptyView = new EmptyView(ctx).setHeight(height).showEmpty(R.drawable.img_no_data_blank, "目前暂未持有基金");
        mFundExceptionHolder = new FundExceptionHolder(ctx, height);
//        platformAdapter.addFooterView(mEmptyView);
//        platformAdapter.addFooterView(mFundExceptionHolder.mRootView);
        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getNetData(false);
                mRefreshView.finishRefresh();
            }
        });
//        platformAdapter.setEmptyView(mEmptyView);
        mAdapter.setHeaderFooterEmpty(true, true);
    }


    @Override
    protected int getContentLayout() {
        return R.layout.activity_mine_fund;
    }

    private void getNetData(boolean showProgress) {
        if (showProgress) {
            showLoadProgress(false);
        }
        getHeadData();
        getListData();
    }

    @Override
    public void onLoadFailRefresh() {
        getNetData(true);
        
    }

    private void getListData() {
        RetrofitHelper.getInstance().getRetrofit().create(ThirdPartApi.class)
                .fundInvestorHoldings(new ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<FundInvestorHoldingsEntiy>() {

                    @Override
                    public void onNext(@NonNull FundInvestorHoldingsEntiy response) {
                        noMsgToast = true;
                        super.onNext(response);
                    }

                    @Override
                    public void onNetSystemException(String errorCode) {
                        super.onNetSystemException(errorCode);
                        if ("-200000".equals(errorCode)) {
                            mAdapter.setEmptyView(mEmptyView);
                        } else {
                            mAdapter.setEmptyView(mFundExceptionHolder.mRootView);
                        }
                    }

                    @Override
                    public void bindViewWithDate(FundInvestorHoldingsEntiy response) {
                        mAdapter.setEmptyView(mEmptyView);
                        try {
                            List<FundInvestorDetialDataBean> data = response.getData().datas;
                            mAdapter.setNewData(data);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        showLoadContent();
                    }
                });
    }

    private void getHeadData() {
        RetrofitHelper.getInstance().getRetrofit().create(ThirdPartApi.class)
                .fundHoldingsStatistic(new ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<FundHoldingsStatisticEntiy>() {

                    @Override
                    public void onNext(@NonNull FundHoldingsStatisticEntiy response) {
                        noMsgToast = true;
                        super.onNext(response);
                    }

                    @Override
                    public void onNetSystemException(String error) {
                        super.onNetSystemException(error);
                        if (error.equals("-999999")){
                            mHeadViewHolder.mInvestmentAmountTv.setText("--");
                            mHeadViewHolder.mProfitLossDailyTv.setText("--");
                            mHeadViewHolder.mProfitLossTv.setText("--");
                        }
                    }

                    @Override
                    public void bindViewWithDate(FundHoldingsStatisticEntiy response) {
                        FundHoldingsStatisticData data = response.getData();
                        mHeadViewHolder.mInvestmentAmountTv.setText(data.currentAmount);
                        mHeadViewHolder.mProfitLossDailyTv.setText(data.profitLossDaily);
                        mHeadViewHolder.mProfitLossTv.setText(data.profitLoss);
                    }
                });
    }


    class HeadViewHolder {
        @BindView(R.id.profitLossDailyTv)
        MyTextView mProfitLossDailyTv;
        @BindView(R.id.investmentAmountTv)
        TextView mInvestmentAmountTv;
        @BindView(R.id.profitLossTv)
        TextView mProfitLossTv;
        @BindView(R.id.gotoTradeDetialLl)
        LinearLayout mGotoTradeDetialLl;
        @BindView(R.id.gotoInvest)
        LinearLayout mGotoInvest;
        @BindView(R.id.gotoGetbackLl)
        LinearLayout mGotoGetbackLl;
        @BindView(R.id.rootView)
        LinearLayout mRootView;

        public HeadViewHolder() {
            View view = LayoutInflater.from(ctx).inflate(R.layout.head_mine_fund, null);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.gotoTradeDetialLl, R.id.gotoInvest, R.id.gotoGetbackLl})
        public void onViewClicked(View view) {
            Intent intent;
            switch (view.getId()) {
                case R.id.gotoTradeDetialLl: //基金交易明细
                    startActivity(new Intent(ctx, FundInvestRecordActivity.class));
                    break;
                case R.id.gotoInvest:  //基金列表
                    intent = new Intent(ctx, MainActivity.class);
                    intent.putExtra("skipTab", "p1t2");
                    startActivity(intent);
                    break;
                case R.id.gotoGetbackLl: //基金H5我的账户
                    try {
                        new JumpFundService(JumpFundService.JUMP_TYPE_USER_CENTER
                                , ctx, "").jump();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

    }


}
