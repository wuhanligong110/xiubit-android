package com.v5ent.xiubit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.activity.InviteCfpQr;
import com.v5ent.xiubit.activity.InviteCustomerQr;
import com.v5ent.xiubit.data.InvestedCfgRecordRecylerAdpter;
import com.v5ent.xiubit.data.InvestedCustomerRecordRecylerAdpter;
import com.v5ent.xiubit.entity.InvitationCfgRecordData;
import com.v5ent.xiubit.entity.InvitationCfgRecordEntity;
import com.v5ent.xiubit.entity.InvitationCustomerRecordData;
import com.v5ent.xiubit.entity.InvitationCustomerRecordEntity;
import com.v5ent.xiubit.view.EmptyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 公司: tophlc
 * 类说明: 推荐理财师记录
 *
 * @author hasee-pc
 * @time 2017/6/30
 */

public class InviteRecordFragment extends FragmentBase implements BaseQuickAdapter.RequestLoadMoreListener {
    public final static int TYPE_RECOMMEND_CFG = 0;
    public final static int TYPE_INVITE_CUSTOMER = 1;
    public static final String TYPE_KEY = "TYPE_KEY";
    private int type = 1;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshView)
    SmartRefreshLayout mRefreshView;
    @BindView(R.id.emptyView)
    EmptyView mEmptyView;
    private int pageIndex = 1;
    private int pageSize = 10;
    private List<InvitationCfgRecordData> mRecommendCfgRecordDatas = new ArrayList<>();
    private InvestedCfgRecordRecylerAdpter recommendCfgAdpter;
    private boolean loadMoreEnable;
    private List<InvitationCustomerRecordData> mInvitedCustomerDatas = new ArrayList<>();
    private InvestedCustomerRecordRecylerAdpter investedCustomerAdpter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_view_recycle_refresh_common, null);
        ButterKnife.bind(this, rootView);
        type = getArguments().getInt(TYPE_KEY);
        initRecycleView();
        initRefreshView();
        getNetData();
        mEmptyView.setContantView(mRefreshView);
        return rootView;
    }

    private void getNetData() {
        if (type == TYPE_RECOMMEND_CFG) {
            getRecommendCfgRecordData();
        } else {
            getInviteCustomerData();
        }
    }


    private void initRefreshView() {
        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshData();
                mRefreshView.finishRefresh();
            }
        });
    }

    private void initRecycleView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        if (type == TYPE_RECOMMEND_CFG) {
            recommendCfgAdpter = new InvestedCfgRecordRecylerAdpter();
            mRecyclerView.setAdapter(recommendCfgAdpter);
            recommendCfgAdpter.setOnLoadMoreListener(this,mRecyclerView);
        } else {
            investedCustomerAdpter = new InvestedCustomerRecordRecylerAdpter();
            mRecyclerView.setAdapter(investedCustomerAdpter);
            investedCustomerAdpter.setOnLoadMoreListener(this,mRecyclerView);
        }
        mRecyclerView.setNestedScrollingEnabled(true);

    }


    private void getRecommendCfgRecordData() {
        new MyNetAsyncTask(ctx, false) {
            InvitationCfgRecordEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().getInvitationCfgRecord(pageIndex + "", pageSize + "");
            }

            @Override
            protected void onPost(Exception e) {

                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        mRecommendCfgRecordDatas = response.getData().getDatas();
                        if (mRecommendCfgRecordDatas != null) {
                            if (response.getData().getPageIndex() == 1) {
                                //
                                if (mRecommendCfgRecordDatas.size() == 0) {
                                    mEmptyView.showEmpty(R.drawable.img_no_data_blank, getResources().getString(R.string.invite_cfp_his_empty_des), "立即推荐", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    startActivity(new Intent(ctx, InviteCfpQr.class));
                                                }
                                            }
                                    );
                                } else {
                                    mEmptyView.showContant();
                                }

                                recommendCfgAdpter.setNewData(mRecommendCfgRecordDatas);
                            } else {
                                recommendCfgAdpter.addData(mRecommendCfgRecordDatas);
                            }

                            if (response.getData().getPageCount() <= response.getData().getPageIndex() || response.getData().getPageCount() <= 1) {
                                recommendCfgAdpter.loadMoreEnd();
                            } else {
                                loadMoreEnable = true;
                                recommendCfgAdpter.loadMoreComplete();
                            }
                            pageIndex++;
                        } else {
                            //显示空试图
                            mEmptyView.showEmpty(R.drawable.img_no_data_blank, getResources().getString(R.string.invite_cfp_his_empty_des), "立即推荐", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startActivity(new Intent(ctx, InviteCfpQr.class));
                                        }
                                    }
                            );
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


    private void getInviteCustomerData() {
        new MyNetAsyncTask(ctx, false) {
            InvitationCustomerRecordEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().getInvitationCustomerRecord(pageIndex + "", pageSize + "");
            }

            @Override
            protected void onPost(Exception e) {

                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        mInvitedCustomerDatas = response.getData().getDatas();
                        if (mInvitedCustomerDatas != null) {
                            if (response.getData().getPageIndex() == 1) {
                                //
                                if (mInvitedCustomerDatas.size() == 0) {
                                    mEmptyView.showEmpty(R.drawable.img_no_data_blank, getResources().getString(R.string.invite_customer_his_empty_des), "立即邀请", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    startActivity(new Intent(ctx, InviteCustomerQr.class));
                                                }
                                            }
                                    );
                                }else {
                                    mEmptyView.showContant();
                                }
                                
                                investedCustomerAdpter.setNewData(mInvitedCustomerDatas);
                            } else {
                                investedCustomerAdpter.addData(mInvitedCustomerDatas);
                            }

                            if (response.getData().getPageCount() <= response.getData().getPageIndex() || response.getData().getPageCount() <= 1) {
                                investedCustomerAdpter.loadMoreEnd();
                            } else {
                                investedCustomerAdpter.loadMoreComplete();
                            }
                            pageIndex++;
                        } else {
                            mEmptyView.showEmpty(R.drawable.img_no_data_blank, getResources().getString(R.string.invite_customer_his_empty_des), "立即推荐", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startActivity(new Intent(ctx, InviteCustomerQr.class));
                                        }
                                    }
                            );
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


    private void refreshData() {
        pageIndex = 1;
        getNetData();
    }

    private void loadMoreData() {
        getNetData();
    }

    @Override
    public void onLoadMoreRequested() {
        loadMoreData();
    }
}
