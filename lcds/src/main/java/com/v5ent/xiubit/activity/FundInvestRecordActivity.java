package com.v5ent.xiubit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyTitleBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.holder.FundExceptionHolder;
import com.v5ent.xiubit.data.recylerapter.FundInvestRecordAdapter;
import com.toobei.common.entity.FundInvestorOrderInfoData;
import com.toobei.common.entity.FundInvestorOrderInfoEntiy;
import com.toobei.common.network.NetworkObserver;
import com.toobei.common.network.httpapi.ThirdPartApi;
import com.v5ent.xiubit.util.ParamesHelp;
import com.toobei.common.network.RetrofitHelper;
import com.v5ent.xiubit.view.EmptyView;

import org.xsl781.utils.SystemTool;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/22
 */

public class FundInvestRecordActivity extends MyTitleBaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshView)
    SmartRefreshLayout mRefreshView;
    private int pageIndex = 1;
    private FundInvestRecordAdapter mAdapter;
    private FundExceptionHolder mFundExceptionHolder;
    private EmptyView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        getNetData(true);
    }


    private void initView() {
        headerLayout.showTitle("基金交易明细");
        headerLayout.showLeftBackButton();

        mAdapter = new FundInvestRecordAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));

        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageIndex = 1;
                getNetData(false);
                mRefreshView.finishRefresh();
            }
        });

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getNetData(false);
            }
        },mRecyclerView);

        //空试图和错误视图
        int viewHeight = MyApp.displayHeight - SystemTool.getStatusBarHeight(ctx) - headerLayout.getHeight();
        mFundExceptionHolder = new FundExceptionHolder(ctx, viewHeight);

        mEmptyView = new EmptyView(ctx);
        mEmptyView.showEmpty(R.drawable.img_no_data_blank, getString(R.string.no_fund_trade_record), "购买基金", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, MainActivity.class);
                intent.putExtra("skipTab", "p1t2");
                startActivity(intent);
            }
        });
//        platformAdapter.setEmptyView(mEmptyView);
        mAdapter.setHeaderFooterEmpty(true, true);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_fund_invest_record;
    }

    private void getNetData(boolean showProgress) {
        if (showProgress) {
            showRefreshProgress();
        }
        RetrofitHelper.getInstance().getRetrofit().create(ThirdPartApi.class)
                .fundInvestorOrderInfo(new ParamesHelp()
                        .put("pageIndex", pageIndex + "")
                        .put("pageSize", "10").build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<FundInvestorOrderInfoEntiy>() {
                    @Override
                    public void onNext(@NonNull FundInvestorOrderInfoEntiy response) {
                        noMsgToast = true;
                        super.onNext(response);
                        
                    }

                    @Override
                    public void onNetSystemException(String errorCode) {
                        super.onNetSystemException(errorCode);
                        if (pageIndex != 1) return;
                        if ("-200000".equals(errorCode)) {   //账户不存在
                            mAdapter.setEmptyView(mEmptyView);
                        } else {
                            mAdapter.setEmptyView(mFundExceptionHolder.mRootView);
                        }
                    }

                    @Override
                    public void bindViewWithDate(FundInvestorOrderInfoEntiy response) {
                        List<FundInvestorOrderInfoData> datas = response.getData().getDatas();
                        if (datas.size() == 0 || datas == null) mAdapter.setEmptyView(mEmptyView);
                        if (response.getData().getPageIndex() == 1) {
                            mAdapter.setNewData(datas);
                        } else {
                            mAdapter.addData(datas);
                        }
                        bindLoadMoreView(response.getData(),mAdapter);
                        pageIndex ++;
                    }


                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissRefreshProgress();
                    }
                });

    }

}
